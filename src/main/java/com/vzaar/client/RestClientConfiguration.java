package com.vzaar.client;

public class RestClientConfiguration {
    private String endpoint = "https://api.vzaar.com/api/v2";
    private String clientId;
    private String authToken;
    private String userAgent = "vzaar-sdk-java 2.0.0";
    private int maxConnectionsPerRoute = 20;
    private int useMultipartWhenFileSizeInMbOver = 1024;
    private int defaultDesiredChunkSizeInMb = 128;
    private boolean blockTillRateLimitReset;

    public String getEndpoint() {
        return endpoint;
    }

    public RestClientConfiguration withEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public RestClientConfiguration withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getAuthToken() {
        return authToken;
    }

    public RestClientConfiguration withAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public int getMaxConnectionsPerRoute() {
        return maxConnectionsPerRoute;
    }

    public RestClientConfiguration withMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public RestClientConfiguration withUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public int getUseMultipartWhenFileSizeInMbOver() {
        return useMultipartWhenFileSizeInMbOver;
    }

    public long getUseMultipartWhenFileSizeOver() {
        return 1024L * 1024L * useMultipartWhenFileSizeInMbOver;
    }

    public RestClientConfiguration withUseMultipartWhenFileSizeInMbOver(int useMultipartWhenFileSizeInMbOver) {
        if (useMultipartWhenFileSizeInMbOver < 5 || useMultipartWhenFileSizeInMbOver >= 5 * 1024) {
            throw new IllegalArgumentException("Single/Multipart file size boundary must be between 5MB and 5GB");
        }

        this.useMultipartWhenFileSizeInMbOver = useMultipartWhenFileSizeInMbOver;
        return this;
    }

    public int getDefaultDesiredChunkSizeInMb() {
        return defaultDesiredChunkSizeInMb;
    }

    public RestClientConfiguration withDefaultDesiredChunkSizeInMb(int defaultDesiredChunkSizeInMb) {
        this.defaultDesiredChunkSizeInMb = defaultDesiredChunkSizeInMb;
        return this;
    }

    public boolean isBlockTillRateLimitReset() {
        return blockTillRateLimitReset;
    }

    public RestClientConfiguration withBlockTillRateLimitReset(boolean blockTillRateLimitReset) {
        this.blockTillRateLimitReset = blockTillRateLimitReset;
        return this;
    }
}
