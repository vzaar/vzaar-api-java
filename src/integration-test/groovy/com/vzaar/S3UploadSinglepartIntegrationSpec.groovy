package com.vzaar;

class S3UploadSinglepartIntegrationSpec extends BaseIntegrationSpec {

    def "I can upload a video in a single part"() {
        given:
        Signature signature = vzaar.videos().customUploader.signature()
                .withUploadType(UploadType.single)
                .withFile(smallVideo)
                .withUploader("java-integration-test")
                .withFilesize(smallVideo.size())
                .result()

        println (signature.filesize)

        when:
        vzaar.videos().customUploader.upload(signature, smallVideo);
        Video entity = vzaar.videos().customUploader.createVideo()
                .withGuid(signature.guid)
                .withTitle("Integration test ${signature.guid}")
                .withDescription("From the java sdk integration tests")
                .result()

        then:
        entity.title == "Integration test ${signature.guid}".toString()
        entity.description == "From the java sdk integration tests"
    }
}
