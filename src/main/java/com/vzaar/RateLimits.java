package com.vzaar;

import java.time.Clock;
import java.util.Map;

public class RateLimits {
    public static final String HEADER_RATELIMIT_LIMIT = "X-RateLimit-Limit";
    private static final String HEADER_RATELIMIT_REMAINING = "X-RateLimit-Remaining";
    private static final String HEADER_RATELIMIT_RESET = "X-RateLimit-Reset";
    private static final String HEADER_RATELIMIT_RESET_IN = "X-RateLimit-Reset-In";

    private final Map<String, String> headers;

    public RateLimits(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean hasRateLimit() {
        return headers != null && headers.containsKey(HEADER_RATELIMIT_LIMIT);
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

    public long getRateLimitWindowResetInMillis() {
        return (getRateLimitWindowResetTimestamp() * 1000) - Clock.systemUTC().millis();
    }

    public String getRateLimitWindowResetIn() {
        return headers.get(HEADER_RATELIMIT_RESET_IN);
    }

    public void blockTillRateLimitReset() {
        if (hasRateLimit() && getRateLimitRemaining() == 0) {
            synchronized (headers) {
                long blockFor = getRateLimitWindowResetInMillis() + 10000;
                try {
                    headers.wait(blockFor);
                } catch (InterruptedException ignore) {
                }
            }
        }
    }
}
