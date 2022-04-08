package com.vzaar.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vzaar.RateLimits;
import com.vzaar.Signature;
import com.vzaar.UploadType;
import com.vzaar.VzaarErrorList;
import com.vzaar.VzaarException;
import com.vzaar.VzaarServerException;
import com.vzaar.util.ObjectMapperFactory;
import com.vzaar.util.RequestParameterMapper;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class RestClient {
    private static final String HEADER_CLIENT_ID = "X-Client-Id";
    private static final String HEADER_AUTH_TOKEN = "X-Auth-Token";
    private static final String HEADER_USER_AGENT = "User-Agent";

    private final RequestParameterMapper parameterMapper = new RequestParameterMapper();
    private final ObjectMapper objectMapper = ObjectMapperFactory.make();
    private final RestClientConfiguration configuration;
    private final CloseableHttpClient httpClient;
    private final ThreadLocal<HttpClientContext> httpContext = new ThreadLocal<>();
    private final AtomicReference<Map<String, String>> responseHeadersReference = new AtomicReference<>();

    public RestClient(RestClientConfiguration configuration) {
        this.configuration = configuration;
        this.httpClient = makeHttpClient(configuration);
    }

    public RestClientConfiguration getConfiguration() {
        return configuration;
    }

    public Map<String, String> getLastResponseHeaders() {
        return responseHeadersReference.get();
    }

    public <T> Resource<T> resource(Class<T> type) {
        return new Resource<>(this, type);
    }

    public void s3(InputStream in, Signature signature, int part) throws IOException {
        HttpPost request = new HttpPost(signature.getUploadHostname());
        String fileSuffix = signature.getType() == UploadType.multipart ? "." + part : "";
        ContentBody body = new FileStreamingBody(in, signature.getFilename(), signature.getType() == UploadType.multipart
                ? signature.getPartSizeInBytes()
                : signature.getFilesize());

        request.addHeader("User-agent", configuration.getUserAgent());
        request.setEntity(MultipartEntityBuilder.create()
                .addTextBody("acl", signature.getAcl())
                .addTextBody("bucket", signature.getBucket())
                .addTextBody("policy", signature.getPolicy())
                .addTextBody("success_action_status", signature.getSuccessActionStatus())
                .addTextBody("key", signature.getKey() + fileSuffix)
                .addTextBody("x-amz-meta-uploader", signature.getUploader())
                .addTextBody("x-amz-credential", signature.getCredential())
                .addTextBody("x-amz-algorithm", signature.getAlgorithm())
                .addTextBody("x-amz-date", signature.getDate())
                .addTextBody("x-amz-signature", signature.getSignature())
                .addPart("file", body)
                .build());
        HttpResponse response = httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() >= 202) {
            throw new VzaarException(EntityUtils.toString(response.getEntity()));
        }
        EntityUtils.consume(response.getEntity());
    }

    String getEndpoint() {
        return configuration.getEndpoint();
    }

    <T> Resource<T> get(Resource<T> resource, String url) {
        try {
            return execute(resource, new HttpGet(new URI(url)));
        } catch (IOException | URISyntaxException e) {
            throw new VzaarException(e);
        }
    }

    <T> Resource<T> get(Resource<T> resource) {
        try {
            return execute(resource, new HttpGet(resource.getUri()));
        } catch (IOException | URISyntaxException e) {
            throw new VzaarException(e);
        }
    }

    <T> Resource<T> post(Resource<T> resource, Object payload) {
        try {
            return execute(resource, setPayload(new HttpPost(resource.getUri()), payload));
        } catch (IOException | URISyntaxException e) {
            throw new VzaarException(e);
        }
    }

    <T> Resource<T> postUpload(Resource<T> resource, Map<String, Object> payload) {
        try {
            return execute(resource, setPayload(new HttpPost(resource.getUri()), payload));
        } catch (IOException | URISyntaxException e) {
            throw new VzaarException(e);
        }
    }

    <T> Resource<T> patch(Resource<T> resource, Object payload) {
        try {
            return execute(resource, setPayload(new HttpPatch(resource.getUri()), payload));
        } catch (IOException | URISyntaxException e) {
            throw new VzaarException(e);
        }
    }

    <T> Resource<T> patchUpload(Resource<T> resource, Map<String, Object> payload) {
        try {
            return execute(resource, setPayload(new HttpPatch(resource.getUri()), payload));
        } catch (IOException | URISyntaxException e) {
            throw new VzaarException(e);
        }
    }

    <T> Resource<T> delete(Resource<T> resource) {
        try {
            return execute(resource, new HttpDelete(resource.getUri()));
        } catch (IOException | URISyntaxException e) {
            throw new VzaarException(e);
        }
    }

    RequestParameterMapper getParameterMapper() {
        return parameterMapper;
    }

    ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private <T> Resource<T> execute(Resource<T> resource, HttpUriRequest request) throws IOException {
        blockTillRateLimitReset();

        request.addHeader(HEADER_CLIENT_ID, configuration.getClientId());
        request.addHeader(HEADER_AUTH_TOKEN, configuration.getAuthToken());
        request.addHeader(HEADER_USER_AGENT, configuration.getUserAgent());

        try (CloseableHttpResponse response = httpClient.execute(request, getHttpContext())) {
            if (response.getStatusLine().getStatusCode() >= 400) {
                throwError(response);
            }

            if (response.containsHeader(RateLimits.HEADER_RATELIMIT_LIMIT)) {
                responseHeadersReference.set(buildHeaderMap(response));
            }
            return resource.body(readEntity(response));
        }
    }

    private void blockTillRateLimitReset() {
        if (configuration.isBlockTillRateLimitReset() && getLastResponseHeaders() != null) {
            RateLimits limits = new RateLimits(getLastResponseHeaders());
            limits.blockTillRateLimitReset();
        }
    }

    private <T extends HttpEntityEnclosingRequest> T setPayload(T request, Object payload) throws JsonProcessingException, UnsupportedEncodingException {
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(payload), "UTF-8");
        entity.setContentType("application/json");
        request.setEntity(entity);
        return request;
    }

    private <T extends HttpEntityEnclosingRequest> T setPayload(T request, Map<String, Object> payload) throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            if (entry.getValue() instanceof File) {
                File file = (File) entry.getValue();
                ContentBody body = new FileStreamingBody(new FileInputStream(file), file.getName(), file.length());
                builder.addPart(entry.getKey(), body);
            } else {
                builder.addTextBody(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        request.setEntity(builder.build());
        return request;
    }

    private Map<String, String> buildHeaderMap(CloseableHttpResponse response) {
        Map<String, String> headers = new HashMap<>();
        for (Header header : response.getAllHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        return headers;
    }

    private void throwError(CloseableHttpResponse response) {
        try {
            byte[] content = readEntity(response);
            throw new VzaarServerException(
                    response.getStatusLine().getStatusCode(),
                    response.getStatusLine().getReasonPhrase(),
                    objectMapper.readValue(content, VzaarErrorList.class));
        } catch (IOException ignore) {
            throw new VzaarServerException(
                    response.getStatusLine().getStatusCode(),
                    response.getStatusLine().getReasonPhrase(),
                    new VzaarErrorList());
        }
    }

    private byte[] readEntity(CloseableHttpResponse response) throws IOException {
        if (response.getEntity() == null) {
            return null;
        }
        return EntityUtils.toByteArray(response.getEntity());
    }


    private CloseableHttpClient makeHttpClient(RestClientConfiguration configuration) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(configuration.getMaxConnectionsPerRoute() * 2);
        connectionManager.setDefaultMaxPerRoute(configuration.getMaxConnectionsPerRoute());
        return HttpClients.custom().setConnectionManager(connectionManager).build();
    }

    private HttpClientContext getHttpContext() {
        HttpClientContext context = httpContext.get();
        if (context == null) {
            context = HttpClientContext.create();
            httpContext.set(context);
        }
        return context;
    }
}
