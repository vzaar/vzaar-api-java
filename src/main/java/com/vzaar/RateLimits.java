package com.vzaar;

import java.time.Clock;
import java.util.Map;

/**
 * Rate limits utility class
 */
public class RateLimits {
    public static final String HEADER_RATELIMIT_LIMIT = "X-RateLimit-Limit";
    private static final String HEADER_RATELIMIT_REMAINING = "X-RateLimit-Remaining";
    private static final String HEADER_RATELIMIT_RESET = "X-RateLimit-Reset";
    private static final String HEADER_RATELIMIT_RESET_IN = "X-RateLimit-Reset-In";

    private final Map<String, String> headers;

    public RateLimits(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * Do the headers used to build this instance have any rate limit information.
     * You should check this in case the server didn't send the correct headers
     * @return true if rate limit information is available
     */
    public boolean hasRateLimit() {
        return headers != null && headers.containsKey(HEADER_RATELIMIT_LIMIT);
    }

    /**
     * Get the rate limit.  The rate limit is your total request limit during the 5 minute window
     * (e.g. requests/min * 5 min).
     * @return the rate limit
     */
    public int getRateLimit() {
        return Integer.parseInt(headers.get(HEADER_RATELIMIT_LIMIT));
    }

    /**
     * Get the remaining requests in the window before requests will be denied.
     * @return the number of requests remaining until your requests will be denied.
     */
    public int getRateLimitRemaining() {
        return Integer.parseInt(headers.get(HEADER_RATELIMIT_REMAINING));
    }

    /**
     * The timestamp in seconds for when the current window will completely reset assuming no further
     * API requests are made.
     * @return the timestamp in seconds
     */
    public long getRateLimitWindowResetTimestamp() {
        return Long.parseLong(headers.get(HEADER_RATELIMIT_RESET));
    }

    /**
     * The number of milliseconds before the current window will completely reset assuming no further
     * API requests are made.
     * @return the time in milliseconds
     */
    public long getRateLimitWindowResetInMillis() {
        return Math.max((getRateLimitWindowResetTimestamp() * 1000) - Clock.systemUTC().millis(), 0);
    }

    public String getRateLimitWindowResetIn() {
        return headers.get(HEADER_RATELIMIT_RESET_IN);
    }

    /**
     * If the rate limit remaining is 0 then this will block until the rate limit
     * window is reset otherwise it will return immediately.
     */
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
