package com.vzaar.client;

public class RestClientConfiguration {
    private String endpoint = "https://api.vzaar.com/api/v2";
    private String clientId;
    private String authToken;
    private int maxConnectionsPerRoute = 20;

    public String getEndpoint() {
        return endpoint;
    }

    public RestClientConfiguration setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public RestClientConfiguration setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getAuthToken() {
        return authToken;
    }

    public RestClientConfiguration setAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public int getMaxConnectionsPerRoute() {
        return maxConnectionsPerRoute;
    }

    public RestClientConfiguration setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
        return this;
    }
}
