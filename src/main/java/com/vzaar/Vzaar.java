package com.vzaar;

import com.vzaar.client.RestClient;
import com.vzaar.client.RestClientConfiguration;

/**
 * Vzaar SDK entry point
 */
public final class Vzaar {
    private final RestClient client;

    private Vzaar(RestClientConfiguration configuration) {
        this.client = new RestClient(configuration);
    }

    /**
     * Get a vzaar instance for your given client id and authentication token.  To manage your API tokens login to
     * https://app.vzaar.com/settings/api.
     * @param clientId your client id
     * @param authToken your authentication token
     * @return a Vzaar instance
     */
    public static Vzaar make(String clientId, String authToken) {
        return new Vzaar(new RestClientConfiguration()
                .withClientId(clientId)
                .withAuthToken(authToken));
    }

    /**
     * Get a vzaar instance using finer grained control over configuration. To manage your API tokens login to
     * https://app.vzaar.com/settings/api.
     * @param configuration your configuration
     * @return a Vzaar instance
     */
    public static Vzaar make(RestClientConfiguration configuration) {
        return new Vzaar(configuration);
    }

    /**
     * Get versioning information. Note that this will only return data after at least one call has been made to the
     * server and providing the version of the API is being deprecated.
     * @return the versioning information
     */
    public ApiVersioning getApiVersioning() {
        return new ApiVersioning(client.getLastResponseHeaders());
    }

    /**
     * Get the rate limit data. Note that this will only return data after at least one call has been made to the
     * server.
     * @return the rate limit information
     */
    public RateLimits getRateLimits() {
        return new RateLimits(client.getLastResponseHeaders());
    }

    /**
     * Get the video resource to search, upload, update and delete videos.
     * @return a videos resource
     */
    public VideoResource videos() {
        return new VideoResource(client);
    }

    /**
     * Get the category resource to search, fetch, create, update and delete categories.
     * @return a category resource
     */
    public CategoryResource categories() {
        return new CategoryResource(client);
    }

    /**
     * Get the ingest recipe resource to list, fetch, create, update and delete recipes.
     * @return an ingest recipe resource
     */
    public IngestRecipeResource recipes() {
        return new IngestRecipeResource(client);
    }

    /**
     * Get the encoding preset recipe resource to list and fetch encoding presets.
     * @return an encoding preset resource
     */
    public EncodingPresetResource encodingPresets() {
        return new EncodingPresetResource(client);
    }

    /**
     * Get the playlist resource to list and fetch playlists.
     * @return a playlist resource
     */
    public PlaylistResource playlists() {
        return new PlaylistResource(client);
    }

}
