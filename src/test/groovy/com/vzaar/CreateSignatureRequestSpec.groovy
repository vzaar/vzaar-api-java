package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class CreateSignatureRequestSpec extends Specification {

    private ObjectMapper mapper;

    def setup() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        mapper = ObjectMapperFactory.make()
    }

    def "I can make the request to the expected JSON payload"() {
        given:
        CreateSignatureRequest request = new CreateSignatureRequest()
            .withDesiredPartSizeInMb(7)
            .withFilename("my_video")
            .withFilesize(123456l)
            .withUploader("nine-lives 1.0")

        when:
        Map<String, Object> result = mapper.readValue(mapper.writeValueAsString(request), Map.class)

        then:
        result.get("filename") == 'my_video'
        result.get("filesize") == 123456l
        result.get("uploader") == 'nine-lives 1.0'
        result.get("desired_part_size") == '7MB'
    }
}
