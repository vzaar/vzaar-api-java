package com.vzaar;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * API versioning information.
 */
class ApiVersioning {
    private static final String HEADER_DEPRECATED = "X-vzaar-Deprecated";
    private static final String HEADER_SUNSET = "X-vzaar-Sunset-Date";

    private final Map<String, String> headers;

    ApiVersioning(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * Is the API version used by the SDK marked for deprecation
     * @return true if the API version is deprecated or being sunsetted
     */
    public boolean isApiVersionDeprecated() {
        return "TRUE".equalsIgnoreCase(headers.get(HEADER_DEPRECATED));
    }

    /**
     * The date the API version will be deprecated
     * @return the sunset date or null if the api version is not marked for deprecation
     */
    public ZonedDateTime getApiVersionSunsetDate() {
        return headers.containsKey(HEADER_SUNSET) ? ZonedDateTime.parse(headers.get(HEADER_SUNSET)) : null;
    }

}
