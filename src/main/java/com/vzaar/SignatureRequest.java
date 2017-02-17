package com.vzaar;

import com.vzaar.client.RestClient;

import java.io.File;

public class SignatureRequest {
    private transient RestClient client;
    private transient UploadType type;

    private String filename;
    private long filesize;
    private String uploader;
    private String desiredPartSize;

    SignatureRequest(RestClient client) {
        this.client = client;
    }

    /**
     * Set the upload type
     *
     * @param type the upload type
     * @return this instance
     */
    public SignatureRequest withUploadType(UploadType type) {
        this.type = type;
        return this;
    }

    /**
     * The file to upload. This is a shortcut to setting filename and filesize
     *
     * @param file the file that is to be uploaded
     * @return this instance
     */
    public SignatureRequest withFile(File file) {
        this.filename = file.getName();
        this.filesize = file.length();
        return this;
    }

    /**
     * The base name of your video file. Required for multipart.
     *
     * @param filename base name of your video file
     * @return this instance
     */
    public SignatureRequest withFilename(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * The size in bytes of your video file. Required for multipart
     *
     * @param filesize size in bytes of your video file
     * @return this instance
     */
    public SignatureRequest withFilesize(long filesize) {
        this.filesize = filesize;
        return this;
    }

    /**
     * Set the uploader description used for metadata, analytics and support. Required
     *
     * @param uploader uploader description
     * @return this instance
     */
    public SignatureRequest withUploader(String uploader) {
        this.uploader = uploader;
        return this;
    }

    /**
     * Desired part size specified as a mebibytes. This is a hint to the server, the chunk
     * size when sending should adhere to the size received in the response
     *
     * @param mebibytes the desired part / chunk size
     * @return this instance
     */
    public SignatureRequest withDesiredPartSizeInMb(Integer mebibytes) {
        this.desiredPartSize = mebibytes == null ? null : String.format("%sMB", mebibytes);
        return this;
    }

    /**
     * Get the upload type
     *
     * @return the upload type
     */
    public UploadType getType() {
        return type;
    }

    /**
     * Get the file name
     *
     * @return the file name
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Get the file size
     *
     * @return the file size
     */
    public long getFilesize() {
        return filesize;
    }

    /**
     * Get the uploader
     *
     * @return the uploader
     */
    public String getUploader() {
        return uploader;
    }

    /**
     * Get the signature
     *
     * @return the signature
     */
    public Signature result() {
        if (type == UploadType.single) {
            desiredPartSize = null;
        }
        return client.resource(Signature.class).action(type.name()).create(this).withSignatureRequest(this);
    }
}
