package com.vzaar;

import java.io.File;

public class VideoUploadRequest {
    private File file;
    private String uploader;
    private String title;
    private String description;
    private int ingestRecipeId;

    public File getFile() {
        return file;
    }

    public String getUploader() {
        return uploader;
    }

    public VideoUploadRequest withFile(File file) {
        this.file = file;
        return this;
    }

    public VideoUploadRequest withUploader(String uploader) {
        this.uploader = uploader;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public VideoUploadRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public VideoUploadRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    public int getIngestRecipeId() {
        return ingestRecipeId;
    }

    public VideoUploadRequest withIngestRecipeId(int ingestRecipeId) {
        this.ingestRecipeId = ingestRecipeId;
        return this;
    }
}
