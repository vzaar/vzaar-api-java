package com.vzaar

import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class SignatureRequestSpec extends Specification {

    private ObjectMapper mapper;

    def setup() {
        mapper = ObjectMapperFactory.make()
    }

    def "I can make the request to the expected JSON payload"() {
        given:
        SignatureRequest request = new SignatureRequest()
            .withUploadType(UploadType.multipart)
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


    def "I can access the getters"() {
        when:
        SignatureRequest request = new SignatureRequest()
                .withUploadType(UploadType.multipart)
                .withDesiredPartSizeInMb(7)
                .withFilename("my_video")
                .withFilesize(123456l)
                .withUploader("nine-lives 1.0")

        then:
        request.getFilename() == "my_video"
        request.getFilesize() == 123456l
        request.getUploader() == "nine-lives 1.0"
        request.getType() == UploadType.multipart
    }

    def "I can use a file object to set the name and size"() {
        given:
        File file = new File(getClass().classLoader.getResource("com/vzaar/SignatureRequest.class").getFile());

        when:
        SignatureRequest request = new SignatureRequest()
                .withDesiredPartSizeInMb(7)
                .withFile(file)
                .withUploader("nine-lives 1.0")

        then:
        file.exists()
        request.getFilename() == file.name
        request.getFilesize() == file.length()
        request.getUploader() == "nine-lives 1.0"
    }
}
