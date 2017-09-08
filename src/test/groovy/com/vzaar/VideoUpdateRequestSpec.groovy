package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class VideoUpdateRequestSpec extends Specification {

    private ObjectMapper mapper = ObjectMapperFactory.make()

    def "I can make the request to the expected JSON payload"() {
        given:
        VideoUpdateRequest request = new VideoUpdateRequest()
                .withTitle("My new title")
                .withDescription("My new description")
                .withCategoryIds([2, 3] as Set)
                .withPrivate(true)
                .withSeoUrl("https://url")

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("title") == 'My new title'
        result.get("description") == 'My new description'
        result.get("category_ids") as Set == [2, 3] as Set
        result.get("private") == true
        result.get("seo_url") == "https://url"
        result.size() == 5
    }

    def "I only send attributes that are set"() {
        given:
        VideoUpdateRequest request = new VideoUpdateRequest()
                .withTitle("My new title")

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("title") == 'My new title'
        result.size() == 1
    }
}
