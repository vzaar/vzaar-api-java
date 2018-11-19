package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class SubtitleUpdateRequestSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can make the request to the expected JSON payload"() {
        given:
        SubtitleUpdateRequest request = new SubtitleUpdateRequest(null)
                .withTitle("Subtitle 1")
                .withCode("en")
                .withContent("content")
                .withFile(File.createTempFile("vzaar", ".temp"))

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.size() == 3
        result.get("title") == 'Subtitle 1'
        result.get("code") == 'en'
        result.get("content") == 'content'
    }
}
