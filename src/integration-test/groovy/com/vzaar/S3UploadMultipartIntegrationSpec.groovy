package com.vzaar;

public class S3UploadMultipartIntegrationSpec extends BaseIntegrationSpec {

    def "I can upload a video in multiple part"() {
        given:
        UploadRequest uploadRequest = vzaar.customUploader.signature(UploadType.multipart, new CreateSignatureRequest()
                .withFile(mediumVideo)
                .withUploader("java-integration-test")
                .withDesiredPartSizeInMb(5)
                .withFilesize(mediumVideo.size()))


        when:
        vzaar.customUploader.upload(uploadRequest, mediumVideo);
        Video entity = vzaar.customUploader.createVideo(new CreateVideoRequest()
                .withGuid(uploadRequest.uploadSignature.guid)
                .withTitle("Integration tester ${uploadRequest.uploadSignature.guid}")
                .withDescription("From the java sdk integration tests"))
        println entity.state
        println uploadRequest.uploadSignature.guid
        println entity.duration
        println entity.id

        then:
        entity.title == "Integration tester ${uploadRequest.uploadSignature.guid}"
        entity.description == "From the java sdk integration tests"
    }
}
