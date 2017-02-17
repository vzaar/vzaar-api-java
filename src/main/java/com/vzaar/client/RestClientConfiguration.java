package com.vzaar.client;

import java.io.IOException;
import java.util.Properties;

public class RestClientConfiguration {
    private final String sdkUserAgent;
    private String userAgent;
    private String endpoint = "https://api.vzaar.com/api/v2";
    private String clientId;
    private String authToken;
    private int maxConnectionsPerRoute = 20;
    private int useMultipartWhenFileSizeInMbOver = 1024;
    private int defaultDesiredChunkSizeInMb = 128;
    private boolean blockTillRateLimitReset;

    public RestClientConfiguration() {
        sdkUserAgent = "vzaar-java-sdk/" + getVersion();
        userAgent = sdkUserAgent;
    }

    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Set the base api url. Defaults to https://api.vzaar.com/api/v2
     *
     * @param endpoint the base api url
     * @return this instance
     */
    public RestClientConfiguration withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Get the client id
     *
     * @return the client id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Set the client id used for authentication. Required.
     *
     * @param clientId the client
     * @return this instance
     */
    public RestClientConfiguration withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Get teh authentication token
     *
     * @return the authentication token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Set the authentication token. Required.
     *
     * @param authToken the authentication token
     * @return this instance
     */
    public RestClientConfiguration withAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    /**
     * Get the max connections per route
     *
     * @return the max connections per route
     */
    public int getMaxConnectionsPerRoute() {
        return maxConnectionsPerRoute;
    }

    /**
     * Set the effective maximum number of concurrent connections in the pool. Connections try to make use of the
     * keep-alive directive. Defaults to 20
     *
     * @param maxConnectionsPerRoute the max connections per router
     * @return this instance
     */
    public RestClientConfiguration withMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
        return this;
    }

    /**
     * Get the user agent string being to send in the request headers
     *
     * @return the user agent string
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Set the user agent string sent in the request
     *
     * @param userAgent the user agent string
     * @return this instance
     */
    public RestClientConfiguration withUserAgent(String userAgent) {
        if (userAgent == null || userAgent.trim().isEmpty()) {
            this.userAgent = sdkUserAgent;
        } else {
            this.userAgent = userAgent.trim() + " " + sdkUserAgent;
        }
        return this;
    }

    /**
     * Get the boundary in MB used to determine if a file should be sent as multipart or single file
     *
     * @return the boundary in MB
     */
    public int getUseMultipartWhenFileSizeInMbOver() {
        return useMultipartWhenFileSizeInMbOver;
    }

    /**
     * Get the boundary in bytes used to determine if a file should be sent as multipart or single file
     *
     * @return the boundary in bytes
     */
    public long getUseMultipartWhenFileSizeOver() {
        return 1024L * 1024L * useMultipartWhenFileSizeInMbOver;
    }

    /**
     * Set the boundary condition of file size to determine when multipart upload should be used. Defaults to 1024MB
     *
     * @return this instance
     */
    public RestClientConfiguration withUseMultipartWhenFileSizeInMbOver(int useMultipartWhenFileSizeInMbOver) {
        if (useMultipartWhenFileSizeInMbOver < 5 || useMultipartWhenFileSizeInMbOver >= 5 * 1024) {
            throw new IllegalArgumentException("Single/Multipart file size boundary must be between 5MB and 5GB");
        }

        this.useMultipartWhenFileSizeInMbOver = useMultipartWhenFileSizeInMbOver;
        return this;
    }

    /**
     * Get the default desired chunk size in MB. This is just a hint for the server
     *
     * @return the size in MB
     */
    public int getDefaultDesiredChunkSizeInMb() {
        return defaultDesiredChunkSizeInMb;
    }

    /**
     * Set the default desired chunk size for multipart uploads when upload type auto-selection is being used. Defaults to 128MB
     *
     * @param defaultDesiredChunkSizeInMb the default desired chunk size
     * @return this instance
     */
    public RestClientConfiguration withDefaultDesiredChunkSizeInMb(int defaultDesiredChunkSizeInMb) {
        this.defaultDesiredChunkSizeInMb = defaultDesiredChunkSizeInMb;
        return this;
    }

    /**
     * Will the client block until the rate limit reset window is reached if the rate limit is set
     *
     * @return true if it will block
     */
    public boolean isBlockTillRateLimitReset() {
        return blockTillRateLimitReset;
    }

    /**
     * Set whether the client should block if the rate limit has been reached until the reset timestamp has
     * elapsed. Defaults to false
     *
     * @param blockTillRateLimitReset true to block, false otherwise
     * @return this instance
     */
    public RestClientConfiguration withBlockTillRateLimitReset(boolean blockTillRateLimitReset) {
        this.blockTillRateLimitReset = blockTillRateLimitReset;
        return this;
    }

    private String getVersion() {
        try {
            Properties versionProperties = new Properties();
            versionProperties.load(RestClientConfiguration.class.getClassLoader().getResourceAsStream("version.properties"));
            return versionProperties.getProperty("version");
        } catch (IOException ignore) {
            return "2.0.x";
        }
    }
}
