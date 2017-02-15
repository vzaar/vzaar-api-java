package com.vzaar;

public class S3UploadSinglepartIntegrationSpec extends BaseIntegrationSpec {

    def "I can upload a video in a single part"() {
        given:
        UploadRequest uploadRequest = vzaar.videos().customUploader.signature(UploadType.single, new CreateSignatureRequest()
                .withFile(smallVideo)
                .withUploader("java-integration-test")
                .withFilesize(smallVideo.size()))

        when:
        vzaar.videos().customUploader.upload(uploadRequest, smallVideo);
        Video entity = vzaar.videos().customUploader.createVideo(new VideoCreateRequest()
                .withGuid(uploadRequest.uploadSignature.guid)
                .withTitle("Integration test ${uploadRequest.uploadSignature.guid}")
                .withDescription("From the java sdk integration tests"))


        then:
        entity.title == "Integration test ${uploadRequest.uploadSignature.guid}"
        entity.description == "From the java sdk integration tests"
    }
}
