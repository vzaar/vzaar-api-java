package com.vzaar;

public class S3UploadSinglepartIntegrationSpec extends BaseIntegrationSpec {

    def "I can upload a video in a single part"() {
        given:
        File file = new File(getClass().classLoader.getResource("videos/video.mp4").getFile());
        UploadRequest uploadRequest = vzaar.signature(UploadType.single, new CreateSignatureRequest()
                .withFile(file)
                .withUploader("java-integration-test")
                .withFilesize(file.size()))

        when:
        vzaar.s3UploadSingle(uploadRequest, file);
        Video entity = vzaar.createVideo(new CreateVideoRequest()
                .withGuid(uploadRequest.uploadSignature.guid)
                .withTitle("Integration test ${uploadRequest.uploadSignature.guid}")
                .withDescription("From the java sdk integration tests"))


        then:
        entity.title == "Integration test ${uploadRequest.uploadSignature.guid}"
        entity.description == "From the java sdk integration tests"
    }
}
