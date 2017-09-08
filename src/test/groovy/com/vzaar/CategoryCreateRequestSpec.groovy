package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class CategoryCreateRequestSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can make the request to the expected JSON payload"() {
        given:
        CategoryCreateRequest request = new CategoryCreateRequest()
                .withName("My name")
                .withParentId(100)

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("name") == 'My name'
        result.get("parent_id") == 100
        result.size() == 2
    }

    def "I only send attributes that are set"() {
        given:
        CategoryCreateRequest request = new CategoryCreateRequest()

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.size() == 0
    }
}
