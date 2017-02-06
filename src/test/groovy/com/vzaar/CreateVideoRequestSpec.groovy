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
        CreateLinkUploadRequest request = new CreateLinkUploadRequest()
                .withUrl("http://mydomain.com/video.mp4")
                .withIngestRecipeId(1234)
                .withTitle("my video")
                .withDescription("which shows things")
                .withUploader("nine-lives")

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("url") == 'http://mydomain.com/video.mp4'
        result.get("ingest_recipe_id") == 1234
        result.get("title") == 'my video'
        result.get("description") == 'which shows things'
        result.get("uploader") == 'nine-lives'
    }
}
