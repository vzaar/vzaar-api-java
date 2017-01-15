package com.vzaar;

import java.time.ZonedDateTime;
import java.util.Map;

class ApiVersioning {
    private static final String HEADER_DEPRECATED = "X-vzaar-Deprecated";
    private static final String HEADER_SUNSET = "X-vzaar-Sunset-Date";

    private final Map<String, String> headers;

    ApiVersioning(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isApiVersionDeprecated() {
        return "TRUE".equalsIgnoreCase(headers.get(HEADER_DEPRECATED));
    }

    public ZonedDateTime getApiVersionSunsetDate() {
        return headers.containsKey(HEADER_SUNSET) ? ZonedDateTime.parse(headers.get(HEADER_SUNSET)) : null;
    }

}
