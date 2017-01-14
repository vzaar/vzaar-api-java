package com.vzaar.client;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {
    private static final String HEADER_DEPRECATED = "X-vzaar-Deprecated";
    private static final String HEADER_SUNSET = "X-vzaar-Sunset-Date";
    private static final String HEADER_RATELIMIT_LIMIT = "X-RateLimit-Limit";
    private static final String HEADER_RATELIMIT_REMAINING = "X-RateLimit-Remaining";
    private static final String HEADER_RATELIMIT_RESET = "X-RateLimit-Reset";
    private static final String HEADER_RATELIMIT_RESET_IN = "X-RateLimit-Reset-In";


    private final Map<String, String> headers;

    public ResponseHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isApiVersionDeprecated() {
        return "TRUE".equalsIgnoreCase(headers.get(HEADER_DEPRECATED));
    }

    public String getApiVersionSunsetDate() {
        return headers.get(HEADER_SUNSET);
    }

    public int getRateLimit() {
        return Integer.parseInt(headers.get(HEADER_RATELIMIT_LIMIT));
    }

    public int getRateLimitRemaining() {
        return Integer.parseInt(headers.get(HEADER_RATELIMIT_REMAINING));
    }

    public long getRateLimitWindowResetTimestamp() {
        return Long.parseLong(headers.get(HEADER_RATELIMIT_RESET));
    }

    public String getRateLimitWindowResetIn() {
        return headers.get(HEADER_RATELIMIT_RESET_IN);
    }

    static ResponseHeaders build(HttpResponse response) {
        Map<String, String> headers = new HashMap<>();
        addIfPresent(response, HEADER_DEPRECATED, headers);
        addIfPresent(response, HEADER_SUNSET, headers);
        addIfPresent(response, HEADER_RATELIMIT_LIMIT, headers);
        addIfPresent(response, HEADER_RATELIMIT_REMAINING, headers);
        addIfPresent(response, HEADER_RATELIMIT_RESET, headers);
        addIfPresent(response, HEADER_RATELIMIT_RESET_IN, headers);
        return new ResponseHeaders(headers);
    }

    private static void addIfPresent(HttpResponse response, String name, Map<String, String> headers) {
        Header header = response.getFirstHeader(name);
        if (header != null) {
            headers.put(name, header.getValue());
        }
    }
}
