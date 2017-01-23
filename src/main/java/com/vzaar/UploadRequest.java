package com.vzaar;

public class UploadRequest {
    private UploadType type;
    private CreateSignatureRequest createSignatureRequest;
    private UploadSignature uploadSignature;

    public UploadType getType() {
        return type;
    }

    UploadRequest withType(UploadType type) {
        this.type = type;
        return this;
    }

    public CreateSignatureRequest getCreateSignatureRequest() {
        return createSignatureRequest;
    }

    UploadRequest withCreateSignatureRequest(CreateSignatureRequest createSignatureRequest) {
        this.createSignatureRequest = createSignatureRequest;
        return this;
    }

    public UploadSignature getUploadSignature() {
        return uploadSignature;
    }

    UploadRequest withUploadSignature(UploadSignature uploadSignature) {
        this.uploadSignature = uploadSignature;
        return this;
    }
}
