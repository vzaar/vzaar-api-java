package com.vzaar

import com.vzaar.client.RestClientConfiguration
import spock.lang.Specification

class VzaarSpec extends Specification {

    def "I can use the clientId and authToken constructor to create the api"() {
        when:
        Vzaar vzaar = Vzaar.make("clientId", "authToken")

        then:
        RestClientConfiguration config = vzaar.client.configuration;
        config.authToken == "authToken"
        config.clientId == "clientId"
        config.defaultDesiredChunkSizeInMb == 128
        config.endpoint == 'https://api.vzaar.com/api/v2'
        config.maxConnectionsPerRoute == 20
        config.useMultipartWhenFileSizeInMbOver == 1024
        config.useMultipartWhenFileSizeOver == 1073741824l
    }

    def "I can use the configuration object to create the api"() {
        given:
        RestClientConfiguration config = new RestClientConfiguration()
                .withAuthToken("secret")
                .withClientId("user")
                .withEndpoint("https://bpi.vzaar.com/")

        when:
        Vzaar vzaar = Vzaar.make(config)

        then:
        config == vzaar.client.configuration;
        config.endpoint == "https://bpi.vzaar.com/"
    }
}
