package com.vzaar

import spock.lang.Specification

class UploadRequestSpec extends Specification {
    def "I can set and fetch properties"() {
        given:
        CreateSignatureRequest createSignatureRequest = new CreateSignatureRequest()
            .withUploader("uploader")
        UploadSignature signature = new UploadSignature();
        signature.bucket = "mybucket"

        when:
        UploadRequest request = new UploadRequest()
            .withCreateSignatureRequest(createSignatureRequest)
            .withType(UploadType.multipart)
            .withUploadSignature(signature)

        then:
        request.createSignatureRequest.uploader == 'uploader'
        request.type == UploadType.multipart
        request.uploadSignature.bucket == 'mybucket'
    }
}
