package com.vzaar;

public class CreateLinkUploadRequest {
    private String url;
    private Integer ingestRecipeId;
    private String title;
    private String description;
    private String uploader;

    public CreateLinkUploadRequest withUrl(String url) {
        this.url = url;
        return this;
    }

    public CreateLinkUploadRequest withIngestRecipeId(Integer ingestRecipeId) {
        this.ingestRecipeId = ingestRecipeId;
        return this;
    }

    public CreateLinkUploadRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    public CreateLinkUploadRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    public CreateLinkUploadRequest withUploader(String uploader) {
        this.uploader = uploader;
        return this;
    }
}
