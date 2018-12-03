package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

import java.time.ZonedDateTime

class SignatureSpec extends Specification {
    private ObjectMapper mapper;

    def setup() {
        ObjectMapperFactory.setFailOnUnknownProperties(true)
        mapper = ObjectMapperFactory.make()
    }

    def "I can covert a JSON payload to the entity"() {
        given:
        String payload = '''
            {
              "data": {
                "x-amz-credential": "AKIAJ74MFWNVAFH6P7FQ/20181101/us-east-1/s3/aws4_request",
                "x-amz-algorithm": "AWS4-HMAC-SHA256",
                "x-amz-date": "20181101T151558Z",
                "x-amz-signature": "<signature-string>",
                "key": "vzaar/th2/Z_1/source/th2Z_17QvJwE/${filename}",
                "acl": "private",
                "policy": "<signed-policy-string>",
                "success_action_status": "201",
                "guid": "vz91e80db09a494467b265f0c327950825",
                "bucket": "vzaar-upload-development",
                "upload_hostname": "https://vzaar-upload-development.s3.amazonaws.com",
                "part_size": "16mb",
                "part_size_in_bytes": 16777216,
                "parts": 4
              }
            }
       '''

        when:
        Lookup<Signature> entity = mapper.readValue(payload, new TypeReference<Lookup<Signature>>() {});

        then:
        with(entity.data) {
            credential == 'AKIAJ74MFWNVAFH6P7FQ/20181101/us-east-1/s3/aws4_request'
            algorithm == 'AWS4-HMAC-SHA256'
            date == '20181101T151558Z'
            signature == '<signature-string>'
            key == 'vzaar/th2/Z_1/source/th2Z_17QvJwE/${filename}'
            acl == 'private'
            policy == '<signed-policy-string>'
            successActionStatus == '201'
            guid == 'vz91e80db09a494467b265f0c327950825'
            bucket == 'vzaar-upload-development'
            uploadHostname == 'https://vzaar-upload-development.s3.amazonaws.com'
            partSize == '16mb'
            partSizeInBytes == 16777216l
            parts == 4
        }
    }

    def "I can delegate the SignatureRequest fields"() {
        given:
        Signature signature = new Signature();
        SignatureRequest request = new SignatureRequest()
            .withFilename("filename.mp4")
            .withFilesize(12345)
            .withUploader("uploader")
            .withUploadType(UploadType.single)

        when:
        signature.withSignatureRequest(request)

        then:
        signature.filename == 'filename.mp4'
        signature.filesize == 12345
        signature.uploader == 'uploader'
        signature.type == UploadType.single
    }
}
