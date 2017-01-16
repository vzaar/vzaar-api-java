package com.vzaar;

import java.util.Map;

public class RateLimits {
    private static final String HEADER_RATELIMIT_LIMIT = "X-RateLimit-Limit";
    private static final String HEADER_RATELIMIT_REMAINING = "X-RateLimit-Remaining";
    private static final String HEADER_RATELIMIT_RESET = "X-RateLimit-Reset";
    private static final String HEADER_RATELIMIT_RESET_IN = "X-RateLimit-Reset-In";

    private final Map<String, String> headers;

    public RateLimits(Map<String, String> headers) {
        this.headers = headers;
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
}
