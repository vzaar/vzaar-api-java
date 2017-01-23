package com.vzaar;

public class UploadSignature {
    private String accessKeyId;
    private String key;
    private String acl;
    private String policy;
    private String signature;
    private String successActionStatus;
    private String contentType;
    private String guid;
    private String bucket;
    private String uploadHostname;
    private String partSize;
    private long partSizeInBytes;
    private int parts;

    public String getAccessKeyId() {
        return accessKeyId;
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

    public String getSignature() {
        return signature;
    }

    public String getSuccessActionStatus() {
        return successActionStatus;
    }

    public String getContentType() {
        return contentType;
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
}
