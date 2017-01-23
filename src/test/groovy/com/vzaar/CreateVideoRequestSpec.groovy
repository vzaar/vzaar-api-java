package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class CreateVideoRequestSpec extends Specification {

    private ObjectMapper mapper;

    def setup() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        mapper = ObjectMapperFactory.make()
    }

    def "I can make the request to the expected JSON payload"() {
        given:
        CreateVideoRequest request = new CreateVideoRequest()
                .withGuid("abcdefg")
                .withIngestRecipeId(1234)
                .withTitle("my video")
                .withDescription("which shows things")

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("guid") == 'abcdefg'
        result.get("ingest_recipe_id") == 1234
        result.get("title") == 'my video'
        result.get("description") == 'which shows things'
    }
}
