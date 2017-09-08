package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class CategoryUpdateRequestSpec extends Specification {
    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can make the request to the expected JSON payload"() {
        given:
        CategoryUpdateRequest request = new CategoryUpdateRequest()
                .withName("My name")
                .withParentId(100)
                .withMoveToRoot(true)

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("name") == 'My name'
        result.get("parent_id") == 100
        result.get("move_to_root") == true
        result.size() == 3
    }

    def "I only send attributes that are set"() {
        given:
        CategoryUpdateRequest request = new CategoryUpdateRequest()
                .withMoveToRoot(false)

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.size() == 0
    }
}
