package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class VzaarErrorListSpec extends Specification {

    private ObjectMapper mapper

    def setup() {
        mapper = ObjectMapperFactory.make()
    }

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "errors": [
                {
                  "message": "Authentication failed",
                  "detail": "Invalid credentials"
                }
              ]
            }
       '''

        when:
        VzaarErrorList entity = mapper.readValue(payload, VzaarErrorList);

        then:
        entity.errors.size() == 1
        entity.errors[0].message == 'Authentication failed'
        entity.errors[0].detail == 'Invalid credentials'
    }
}
