package com.vzaar.client;

import com.fasterxml.jackson.databind.JavaType;
import com.vzaar.Lookup;
import com.vzaar.Page;
import com.vzaar.VzaarException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Resource<T> {
    private final RestClient client;
    private final Class<T> type;
    private String path;
    private Integer id;
    private String action;
    private Object params;
    private byte[] body;

    Resource(RestClient client, Class<T> type) {
        this.client = client;
        this.type = type;
        ResourcePath resourcePath = Objects.requireNonNull(
                type.getAnnotation(ResourcePath.class),
                "Resource missing @ResourcePath annotation");
        this.path = resourcePath.path();
    }

    public Resource<T> id(Integer id) {
        this.id = id;
        return this;
    }

    public Resource<T> action(String action) {
        this.action = action;
        return this;
    }

    public Page<T> page(Object params) {
        this.params = params;
        return client.get(this).getPage();
    }

    public Page<T> pageWithUrl(String url) {
        return client.get(this, url).getPage();
    }

    public Resource<T> path(String path) {
        this.path = path;
        return this;
    }

    public List<T> list() {
        return client.get(this).getPage().getData();
    }

    public T lookup() {
        Objects.requireNonNull(id, "resource id has not been set");
        return client.get(this).getData();
    }

    public T create(Object request) {
        return client.post(this, request).getData();
    }

    public T update(Object request) {
        return client.patch(this, request).getData();
    }

    public T createWithUpload(Map<String, Object> request) {
        return client.postUpload(this, request).getData();
    }

    public T updateWithUpload(Map<String, Object> request) {
        return client.patchUpload(this, request).getData();
    }

    public void delete() {
        Objects.requireNonNull(id, "resource id has not been set");
        client.delete(this);
    }

    Resource<T> body(byte[] body) {
        this.body = body;
        return this;
    }

    URI getUri() throws URISyntaxException {
        StringBuilder uri = new StringBuilder(client.getEndpoint())
                .append("/")
                .append(path);

        if (id != null) {
            uri.append("/").append(id);
        }

        if (action != null) {
            uri.append("/").append(action);
        }

        if (params != null) {
            uri.append(client.getParameterMapper().write(params));
        }

        return new URI(uri.toString());
    }

    private JavaType wrapper(Class wrapperType) {
        return client.getObjectMapper().getTypeFactory().constructParametricType(wrapperType, type);
    }

    private T getData() {
        try {
            Lookup<T> data = client.getObjectMapper().readValue(body, wrapper(Lookup.class));
            return data.getData();
        } catch (IOException e) {
            throw new VzaarException(e);
        }
    }

    private Page<T> getPage() {
        try {
            Page<T> page = client.getObjectMapper().readValue(body, wrapper(Page.class));
            page.setResource(this);
            return page;
        } catch (IOException e) {
            throw new VzaarException(e);
        }
    }
}
