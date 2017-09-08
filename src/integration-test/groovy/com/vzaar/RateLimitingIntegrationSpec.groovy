package com.vzaar

import spock.lang.Unroll

import java.time.ZonedDateTime

public class RateLimitingIntegrationSpec extends BaseIntegrationSpec {

    def "I can check the rate limit"() {
        when:
        vzaar.videos().list().withResultsPerPage(1).results()
        RateLimits limits = vzaar.getRateLimits()

        then:
        limits.hasRateLimit()
        limits.rateLimit >= 200
        limits.rateLimitRemaining < limits.rateLimit
        limits.rateLimitRemaining >= 0
        limits.rateLimitWindowResetInMillis > 0
        limits.rateLimitWindowResetInMillis <= 75000

        when:
        vzaar.videos().list().withResultsPerPage(1).results()
        RateLimits limits2 = vzaar.getRateLimits()

        then:
        limits2.hasRateLimit()
        limits2.rateLimit == limits.rateLimit
        limits2.rateLimitRemaining in [limits.rateLimitRemaining - 1, limits2.rateLimit - 1]
        limits2.rateLimitWindowResetInMillis > 0
        limits2.rateLimitWindowResetInMillis <= 75000
        limits2.rateLimitWindowResetTimestamp >= limits.rateLimitWindowResetTimestamp

        when:
        ApiVersioning version = vzaar.getApiVersioning()

        then:
        !version.apiVersionDeprecated || version.apiVersionDeprecated
        !version.apiVersionDeprecated || version.apiVersionSunsetDate.isAfter(ZonedDateTime.now())
    }
}
