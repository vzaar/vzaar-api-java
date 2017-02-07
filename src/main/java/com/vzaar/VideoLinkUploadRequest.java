package com.vzaar;

public class VideoLinkUploadRequest {
    private String url;
    private Integer ingestRecipeId;
    private String title;
    private String description;
    private String uploader;

    /**
     * The URL to a video that is to be uploaded to vzaar. Mandatory.
     * @param url the video url
     * @return this instance
     */
    public VideoLinkUploadRequest withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The ingest recipes to use for the video. If missing then the default
     * ingest recipes will be used. Optional.
     * @param ingestRecipeId the ingest reciped id
     * @return this instance
     */
    public VideoLinkUploadRequest withIngestRecipeId(Integer ingestRecipeId) {
        this.ingestRecipeId = ingestRecipeId;
        return this;
    }

    /**
     * The title for the video. If not provided this will default to your source filename.
     * Optional.
     * @param title the video title
     * @return this instance
     */
    public VideoLinkUploadRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * The description for the video. Optional.
     * @param description the video descriptio
     * @return this instance
     */
    public VideoLinkUploadRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Uploader description used for metadata, analytics and support. Mandatory.
     * @param uploader the uploader description
     * @return this instance
     */
    public VideoLinkUploadRequest withUploader(String uploader) {
        this.uploader = uploader;
        return this;
    }
}