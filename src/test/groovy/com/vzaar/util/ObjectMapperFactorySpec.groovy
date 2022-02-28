package com.vzaar.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class ObjectMapperFactorySpec extends Specification {

    def "The constructor is private"() {
        when:
        ObjectMapperFactory factory = new ObjectMapperFactory()

        then:
        factory
    }

}
