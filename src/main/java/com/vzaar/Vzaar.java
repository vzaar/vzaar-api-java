package com.vzaar;

import com.vzaar.client.RestClient;
import com.vzaar.client.RestClientConfiguration;

public final class Vzaar {
    private final RestClient client;

    private Vzaar(RestClientConfiguration configuration) {
        this.client = new RestClient(configuration);
    }

    public static Vzaar make(String clientId, String authToken) {
        return new Vzaar(new RestClientConfiguration()
                .setClientId(clientId)
                .setAuthToken(authToken));
    }

    public static Vzaar make(String endpoint, String clientId, String authToken) {
        return new Vzaar(new RestClientConfiguration()
                .setEndpoint(endpoint)
                .setClientId(clientId)
                .setAuthToken(authToken));
    }

    public static Vzaar make(RestClientConfiguration configuration) {
        return new Vzaar(configuration);
    }

    public boolean isApiVersionDeprecated() {
        return client.getLastResponseHeaders().isApiVersionDeprecated();
    }

    public String getApiVersionSunsetDate() {
        return client.getLastResponseHeaders().getApiVersionSunsetDate();
    }

    public int getRateLimit() {
        return client.getLastResponseHeaders().getRateLimit();
    }

    public int getRateLimitRemaining() {
        return client.getLastResponseHeaders().getRateLimitRemaining();
    }

    public long getRateLimitWindowResetTimestamp() {
        return client.getLastResponseHeaders().getRateLimitWindowResetTimestamp();
    }

    public String getRateLimitWindowResetIn() {
        return client.getLastResponseHeaders().getRateLimitWindowResetIn();
    }

    public Page<Video> videos(VideoListRequest request) {
        return client.resource("videos", Video.class).page(request);
    }

    public Video video(int videoId) {
        return client.resource("videos", Video.class).lookup(videoId);
    }

    public Page<Category> categories(CategoryListRequest request) {
        return client.resource("categories", Category.class).page(request);
    }

    public Page<Category> categories(int categoryId, CategoryListRequest request) {
        return client.resource("categories", Category.class).id(categoryId).action("subtree").page(request);
    }

    public Category category(int categoryId) {
        return client.resource("categories", Category.class).lookup(categoryId);
    }

    public Page<EncodingPreset> encodingPresets(EncodingPresetListRequest request) {
        return client.resource("encoding_presets", EncodingPreset.class).page(request);
    }

    public EncodingPreset encodingPreset(int encodingPresetId) {
        return client.resource("encoding_presets", EncodingPreset.class).lookup(encodingPresetId);
    }

    public static void main(String[] args) {
        try {
            Vzaar vzaar = Vzaar.make("java-equals-pain", "PwKjaD45J9d2hwjc9rFp");
            Page<Category> list = vzaar.categories(3446, new CategoryListRequest().withLevels(2));
            System.out.println(list.getMeta().getTotalCount());
            for (Category category : list.getData()) {
                System.out.println(category.getName() + " " + category.getId());
            }

            Category category = vzaar.category(3446);
            System.out.println(category.getName());

        } catch (VzaarServerException e) {
            System.out.println(e.getStatusCode() + ": " + e.getMessage());
            for (VzaarError vzaarError : e.getErrors()) {
                System.out.println(vzaarError.getMessage());
                System.out.println(vzaarError.getDetail());
            }
        }

//        try {
//            ObjectMapperFactory.setFailOnUnknownProperties(true);
//            Vzaar vzaar = Vzaar.make("java-equals-pain", "PwKjaD45J9d2hwjc9rFp");
////            Page<EncodingPreset> list = vzaar.encodingPresets(new EncodingPresetListRequest().withResultsPerPage(2));
////            System.out.println(list.getMeta().getTotalCount());
////            for (EncodingPreset preset : list.getData()) {
////                System.out.println(preset.getName() + " " + preset.getId());
////            }
//
//            EncodingPreset preset = vzaar.encodingPreset(2);
//            System.out.println("Preset = " + preset.getName());
//
//            System.out.println(vzaar.getRateLimit());
//            System.out.println(vzaar.getRateLimitRemaining());
//            System.out.println(vzaar.getRateLimitWindowResetTimestamp());
//            System.out.println(vzaar.getRateLimitWindowResetIn());
//        } catch (VzaarServerException e) {
//            System.out.println(e.getStatusCode() + ": " + e.getMessage());
//            for (VzaarError vzaarError : e.getErrors()) {
//                System.out.println(vzaarError.getMessage());
//                System.out.println(vzaarError.getDetail());
//            }
//        }
    }
}
