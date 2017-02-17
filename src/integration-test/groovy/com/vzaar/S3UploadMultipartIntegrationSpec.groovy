package com.vzaar;

public class S3UploadMultipartIntegrationSpec extends BaseIntegrationSpec {

    def "I can upload a video in multiple part"() {
        given:
        Signature signature = vzaar.videos().customUploader.signature()
                .withUploadType(UploadType.multipart)
                .withFile(mediumVideo)
                .withUploader("java-integration-test")
                .withDesiredPartSizeInMb(5)
                .withFilesize(mediumVideo.size())
                .result()

        when:
        vzaar.videos().customUploader.upload(signature, mediumVideo);
        Video entity = vzaar.videos().customUploader.createVideo()
                .withGuid(signature.guid)
                .withTitle("Integration tester ${signature.guid}")
                .withDescription("From the java sdk integration tests")
                .result()

        then:
        entity.title == "Integration tester ${signature.guid}".toString()
        entity.description == "From the java sdk integration tests"
    }
}
