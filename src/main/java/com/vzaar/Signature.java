package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vzaar.client.ResourcePath;

@ResourcePath(path = "signature")
public class Signature {
    private transient SignatureRequest request;
    @JsonProperty("x-amz-credential")
    private String xAmzCredential;
    @JsonProperty("x-amz-algorithm")
    private String xAmzAlgorithm;
    @JsonProperty("x-amz-date")
    private String xAmzDate;
    @JsonProperty("x-amz-signature")
    private String xAmzSignature;

    private String key;
    private String acl;
    private String policy;
    private String successActionStatus;
    private String guid;
    private String bucket;
    private String uploadHostname;
    private String partSize;
    private long partSizeInBytes;
    private int parts;


    public String getCredential() {
        return xAmzCredential;
    }

    public String getAlgorithm() {
        return xAmzAlgorithm;
    }

    public String getDate() {
        return xAmzDate;
    }

    public String getSignature() {
        return xAmzSignature;
    }

    public String getKey() {
        return key;
    }

    public String getAcl() {
        return acl;
    }

    public String getPolicy() {
        return policy;
    }

    public String getSuccessActionStatus() {
        return successActionStatus;
    }

    public String getGuid() {
        return guid;
    }

    public String getBucket() {
        return bucket;
    }

    public String getUploadHostname() {
        return uploadHostname;
    }

    public String getPartSize() {
        return partSize;
    }

    public long getPartSizeInBytes() {
        return partSizeInBytes;
    }

    public int getParts() {
        return parts;
    }

    Signature withSignatureRequest(SignatureRequest request) {
        this.request = request;
        return this;
    }

    public UploadType getType() {
        return request.getType();
    }

    public String getFilename() {
        return request.getFilename();
    }

    public long getFilesize() {
        return request.getFilesize();
    }

    public String getUploader() {
        return request.getUploader();
    }
}
