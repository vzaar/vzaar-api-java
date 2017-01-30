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

    public static Vzaar make(String endpoint, String clientId, String authToken) {
        return new Vzaar(new RestClientConfiguration()
                .withEndpoint(endpoint)
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

    public Page<Video> videos(VideoPageRequest request) {
        return client.resource("videos", Video.class).page(request);
    }

    public Video video(int videoId) {
        return client.resource("videos", Video.class).lookup(videoId);
    }

    public Video upload(VideoUploadRequest request) throws IOException {
        UploadService service = getUploadService();

        RestClientConfiguration configuration = client.getConfiguration();
        UploadType type = request.getFile().length() > configuration.getUseMultipartWhenFileSizeOver()
                ? UploadType.multipart
                : UploadType.single;

        UploadRequest uploadRequest = service.signature(type, new CreateSignatureRequest()
                .withFile(request.getFile())
                .withUploader(request.getUploader())
                .withDesiredPartSizeInMb(type == UploadType.multipart ? configuration.getDefaultDesiredChunkSizeInMb() : null));

        service.upload(uploadRequest, request.getFile());

        return service.createVideo(new CreateVideoRequest()
                .withGuid(uploadRequest.getUploadSignature().getGuid())
                .withTitle(request.getTitle())
                .withDescription(request.getDescription())
                .withIngestRecipeId(request.getIngestRecipeId()));
    }

    public Page<Category> categories(CategoryPageRequest request) {
        return client.resource("categories", Category.class).page(request);
    }

    public Page<Category> categories(int categoryId, CategoryPageRequest request) {
        return client.resource("categories", Category.class).id(categoryId).action("subtree").page(request);
    }

    public Category category(int categoryId) {
        return client.resource("categories", Category.class).lookup(categoryId);
    }

    public Page<IngestRecipe> recipes(IngestRecipePageRequest request) {
        return client.resource("ingest_recipes", IngestRecipe.class).page(request);
    }

    public IngestRecipe recipe(int recipeId) {
        return client.resource("ingest_recipes", IngestRecipe.class).lookup(recipeId);
    }

    public IngestRecipe createRecipe(IngestRecipeStoreRequest request) {
        return client.resource("ingest_recipes", IngestRecipe.class).create(request);
    }

    public IngestRecipe updateRecipe(int recipeId, IngestRecipeStoreRequest request) {
        return client.resource("ingest_recipes", IngestRecipe.class).id(recipeId).update(request);
    }

    public void deleteRecipe(int recipeId) {
        client.resource("ingest_recipes", IngestRecipe.class).id(recipeId).delete();
    }

    public Page<EncodingPreset> encodingPresets(EncodingPresetPageRequest request) {
        return client.resource("encoding_presets", EncodingPreset.class).page(request);
    }

    public EncodingPreset encodingPreset(int encodingPresetId) {
        return client.resource("encoding_presets", EncodingPreset.class).lookup(encodingPresetId);
    }

    public UploadService getUploadService() {
        return new UploadService(client);
    }
}
