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
}
