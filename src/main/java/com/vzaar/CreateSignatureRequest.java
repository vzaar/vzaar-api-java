package com.vzaar;

import java.io.File;

public class CreateSignatureRequest {

    private String filename;
    private long filesize;
    private String uploader;
    private String desiredPartSize;

    public CreateSignatureRequest withFile(File file) {
        this.filename = file.getName();
        this.filesize = file.length();
        return this;
    }

    public CreateSignatureRequest withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public CreateSignatureRequest withFilesize(long filesize) {
        this.filesize = filesize;
        return this;
    }

    public CreateSignatureRequest withUploader(String uploader) {
        this.uploader = uploader;
        return this;
    }

    public CreateSignatureRequest withDesiredPartSizeInMb(Integer mebibytes) {
        this.desiredPartSize = mebibytes == null ? null : String.format("%sMB", mebibytes);
        return this;
    }

    public String getFilename() {
        return filename;
    }

    public long getFilesize() {
        return filesize;
    }

    public String getUploader() {
        return uploader;
    }
}
