package com.vzaar.client

import spock.lang.Specification

class RestClientConfigurationSpec extends Specification {

    def "The defaults are the defaults"() {
        when:
        RestClientConfiguration config = new RestClientConfiguration()

        then:
        config.authToken == null
        config.clientId == null
        config.defaultDesiredChunkSizeInMb == 128
        config.endpoint == 'https://api.vzaar.com/api/v2'
        config.maxConnectionsPerRoute == 20
        config.useMultipartWhenFileSizeInMbOver == 1024
        config.useMultipartWhenFileSizeOver == 1073741824l
        config.userAgent == 'vzaar-sdk-java 2.0.0'
        !config.blockTillRateLimitReset
    }

    def "I can set configuration values"() {
        when:
        RestClientConfiguration config = new RestClientConfiguration()
            .withAuthToken("secret")
            .withClientId("user")
            .withEndpoint("https://bpi.vzaar.com/")
            .withDefaultDesiredChunkSizeInMb(98)
            .withMaxConnectionsPerRoute(22)
            .withUseMultipartWhenFileSizeInMbOver(5)
            .withUserAgent("ninelives 9.0.0")
            .withBlockTillRateLimitReset(true)

        then:
        config.authToken == 'secret'
        config.clientId == 'user'
        config.defaultDesiredChunkSizeInMb == 98
        config.endpoint == 'https://bpi.vzaar.com/'
        config.maxConnectionsPerRoute == 22
        config.useMultipartWhenFileSizeInMbOver == 5
        config.useMultipartWhenFileSizeOver == 5242880l
        config.userAgent == 'ninelives 9.0.0'
        config.blockTillRateLimitReset
    }

    def "The lower boundary condition for auto selecting multipart are checked"() {
        when:
        new RestClientConfiguration()
                .withUseMultipartWhenFileSizeInMbOver(4)

        then:
        IllegalArgumentException e = thrown(IllegalArgumentException)
        e.message == 'Single/Multipart file size boundary must be between 5MB and 5GB'
    }

    def "The upper boundary condition for auto selecting multipart are checked"() {
        when:
        new RestClientConfiguration()
                .withUseMultipartWhenFileSizeInMbOver(5 * 1024)

        then:
        IllegalArgumentException e = thrown(IllegalArgumentException)
        e.message == 'Single/Multipart file size boundary must be between 5MB and 5GB'
    }
}
