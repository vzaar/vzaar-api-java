package com.vzaar;

public class S3UploadMultipartIntegrationSpec extends BaseIntegrationSpec {

    def "I can upload a video in multiple part"() {
        given:
        File file = new File(getClass().classLoader.getResource("videos/video.mp4").getFile());
        UploadRequest uploadRequest = vzaar.uploadService.signature(UploadType.multipart, new CreateSignatureRequest()
                .withFile(file)
                .withUploader("java-integration-test")
                .withDesiredPartSizeInMb(5)
                .withFilesize(file.size()))


        when:
        vzaar.uploadService.upload(uploadRequest, file);
        Video entity = vzaar.uploadService.createVideo(new CreateVideoRequest()
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
