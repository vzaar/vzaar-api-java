package com.vzaar

import spock.lang.Specification

class RateLimitsSpec extends Specification {

    def "I can get the rate limit information"() {
        when:
        RateLimits entity = new RateLimits([
                ('X-RateLimit-Limit')    : '200',
                ('X-RateLimit-Remaining'): '199',
                ('X-RateLimit-Reset')    : '1414622019',
                ('X-RateLimit-Reset-In') : '60 seconds'])

        then:
        entity.hasRateLimit()
        entity.rateLimit == 200
        entity.rateLimitRemaining == 199
        entity.rateLimitWindowResetTimestamp == 1414622019L
        entity.rateLimitWindowResetIn == '60 seconds'
    }

    def "I can check that there is no rate limit information if headers are missing"() {
        when:
        RateLimits entity = new RateLimits([:])

        then:
        !entity.hasRateLimit()
    }

    def "I can check that there is no rate limit information if headers is null"() {
        when:
        RateLimits entity = new RateLimits(null)

        then:
        !entity.hasRateLimit()
    }
}
