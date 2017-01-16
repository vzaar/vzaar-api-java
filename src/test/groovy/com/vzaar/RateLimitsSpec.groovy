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
        entity.rateLimit == 200
        entity.rateLimitRemaining == 199
        entity.rateLimitWindowResetTimestamp == 1414622019L
        entity.rateLimitWindowResetIn == '60 seconds'
    }
}
