package com.vzaar

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.vzaar.util.ObjectMapperFactory
import spock.lang.Specification

class UploadSignatureSpec extends Specification {
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
                "access_key_id": "<access-key-id>",
                "key": "vzaar/vz9/1e8/source/vz91e80db09a494467b265f0c327950825/${filename}",
                "acl": "private",
                "policy": "<signed-policy-string>",
                "signature": "vzaar/vz9/1e8/source/vz91e80db09a494467b265f0c327950825/${filename}",
                "success_action_status": "201",
                "content_type": "binary/octet-stream",
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
        Lookup<UploadSignature> entity = mapper.readValue(payload, new TypeReference<Lookup<UploadSignature>>() {});

        then:
        with(entity.data) {
            accessKeyId == '<access-key-id>'
            key == 'vzaar/vz9/1e8/source/vz91e80db09a494467b265f0c327950825/${filename}'
            acl == 'private'
            policy == '<signed-policy-string>'
            signature == 'vzaar/vz9/1e8/source/vz91e80db09a494467b265f0c327950825/${filename}'
            successActionStatus == '201'
            contentType == 'binary/octet-stream'
            guid == 'vz91e80db09a494467b265f0c327950825'
            bucket == 'vzaar-upload-development'
            uploadHostname == 'https://vzaar-upload-development.s3.amazonaws.com'
            partSize == '16mb'
            partSizeInBytes == 16777216l
            parts == 4
        }
    }
}
