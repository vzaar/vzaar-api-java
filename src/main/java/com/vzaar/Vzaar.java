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

    public Page<Video> videos(VideoPageRequest request) {
        return client.resource(Video.class).page(request);
    }

    public Video video(int videoId) {
        return client.resource(Video.class).lookup(videoId);
    }

    public Video videoUpdate(int videoId, VideoUpdateRequest request) {
        return client.resource(Video.class).id(videoId).update(request);
    }

    public void videoDelete(int videoId) {
        client.resource(Video.class).id(videoId).delete();
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

    public Page<Category> categories(CategoryPageRequest request) {
        return client.resource(Category.class).page(request);
    }

    public Page<Category> categories(int categoryId, CategoryPageRequest request) {
        return client.resource(Category.class).id(categoryId).action("subtree").page(request);
    }

    public Category category(int categoryId) {
        return client.resource(Category.class).lookup(categoryId);
    }

    public Category categoryCreate(CategoryCreateRequest request) {
        return client.resource(Category.class).create(request);
    }

    public Category categoryUpdate(int categoryId, CategoryUpdateRequest request) {
        return client.resource(Category.class).id(categoryId).update(request);
    }

    public void categoryDelete(int categoryId) {
        client.resource(Category.class).id(categoryId).delete();
    }

    public Page<IngestRecipe> recipes(IngestRecipePageRequest request) {
        return client.resource(IngestRecipe.class).page(request);
    }

    public IngestRecipe recipe(int recipeId) {
        return client.resource(IngestRecipe.class).lookup(recipeId);
    }

    public IngestRecipe recipeCreate(IngestRecipeStoreRequest request) {
        return client.resource(IngestRecipe.class).create(request);
    }

    public IngestRecipe recipeUpdate(int recipeId, IngestRecipeStoreRequest request) {
        return client.resource(IngestRecipe.class).id(recipeId).update(request);
    }

    public void recipeDelete(int recipeId) {
        client.resource(IngestRecipe.class).id(recipeId).delete();
    }

    public Page<EncodingPreset> encodingPresets(EncodingPresetPageRequest request) {
        return client.resource(EncodingPreset.class).page(request);
    }

    public EncodingPreset encodingPreset(int encodingPresetId) {
        return client.resource(EncodingPreset.class).lookup(encodingPresetId);
    }

    public CustomUploader getCustomUploader() {
        return new CustomUploader(client);
    }
}
