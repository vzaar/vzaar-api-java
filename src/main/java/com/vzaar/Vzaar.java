package com.vzaar;

import com.vzaar.client.RestClient;
import com.vzaar.client.RestClientConfiguration;

import java.io.IOException;

public final class Vzaar {
    private final RestClient client;

    private Vzaar(RestClientConfiguration configuration) {
        this.client = new RestClient(configuration);
    }

    public static Vzaar make(String clientId, String authToken) {
        return new Vzaar(new RestClientConfiguration()
                .withClientId(clientId)
                .withAuthToken(authToken));
    }

    public static Vzaar make(RestClientConfiguration configuration) {
        return new Vzaar(configuration);
    }

    public ApiVersioning getApiVersioning() {
        return new ApiVersioning(client.getLastResponseHeaders());
    }

    public RateLimits getRateLimits() {
        return new RateLimits(client.getLastResponseHeaders());
    }

    public VideoResource videos() {
        return new VideoResource(client);
    }

    public CategoryResource categories() {
        return new CategoryResource(client);
    }

    public IngestRecipeResource recipes() {
        return new IngestRecipeResource(client);
    }

    public EncodingPresetResource encodingPresets() {
        return new EncodingPresetResource(client);
    }


    public Video upload(VideoUploadRequest request) throws IOException {
        CustomUploader service = getCustomUploader();

        RestClientConfiguration configuration = client.getConfiguration();
        UploadType type = request.getFile().length() > configuration.getUseMultipartWhenFileSizeOver()
                ? UploadType.multipart
                : UploadType.single;

        UploadRequest uploadRequest = service.signature(type, new CreateSignatureRequest()
                .withFile(request.getFile())
                .withUploader(request.getUploader())
                .withDesiredPartSizeInMb(type == UploadType.multipart ? configuration.getDefaultDesiredChunkSizeInMb() : null));

        service.upload(uploadRequest, request.getFile());

        return service.createVideo(new VideoCreateRequest()
                .withGuid(uploadRequest.getUploadSignature().getGuid())
                .withTitle(request.getTitle())
                .withDescription(request.getDescription())
                .withIngestRecipeId(request.getIngestRecipeId()));
    }

    public Video upload(VideoLinkUploadRequest request) {
        return client.resource(Video.class).path("link_uploads").create(request);
    }

    public CustomUploader getCustomUploader() {
        return new CustomUploader(client);
    }
}
