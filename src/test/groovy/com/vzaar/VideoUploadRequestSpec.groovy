package com.vzaar

import spock.lang.Specification

class VideoUploadRequestSpec extends Specification {

    def "I can set and get properties"() {
        given:
        File aFile = new File(getClass().classLoader.getResource("com/vzaar/VideoUploadRequest.class").getFile());

        when:
        VideoUploadRequest request = new VideoUploadRequest()
            .withUploader("ninelives")
            .withFile(aFile)
            .withDescription("something moved")
            .withIngestRecipeId(123)
            .withTitle("my video")

        then:
        with(request) {
            uploader == 'ninelives'
            file == aFile
            description == 'something moved'
            ingestRecipeId == 123
            title == 'my video'
        }
    }
}
