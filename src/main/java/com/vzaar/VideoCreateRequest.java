package com.vzaar;

import com.vzaar.client.Resource;

public class VideoCreateRequest {
    private transient Resource<Video> resource;

    private String guid;
    private Integer ingestRecipeId;
    private String title;
    private String description;

    VideoCreateRequest(Resource<Video> resource) {
        this.resource = resource;
    }

    /**
     * Set the guid provided in signature. Required.
     *
     * @param guid the guid
     * @return this instance
     */
    public VideoCreateRequest withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    /**
     * The ingest recipes to use. The default ingest recipes will be used
     * if not provided. Optional.
     *
     * @param ingestRecipeId the ingest recipes id
     * @return this instance
     */
    public VideoCreateRequest withIngestRecipeId(Integer ingestRecipeId) {
        this.ingestRecipeId = ingestRecipeId;
        return this;
    }

    /**
     * Set the title of the video. If not provided this will default to
     * your source filename. Optional.
     *
     * @param title the video title
     * @return this instance
     */
    public VideoCreateRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set the description of the video. Optional.
     *
     * @param description the description
     * @return this instance
     */
    public VideoCreateRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Send the request to create the video
     * @return the created video
     */
    public Video result() {
        return resource.create(this);
    }
}
