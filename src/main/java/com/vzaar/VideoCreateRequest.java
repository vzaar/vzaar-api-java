package com.vzaar;

public class VideoCreateRequest {
    private String guid;
    private Integer ingestRecipeId;
    private String title;
    private String description;

    /**
     * Set the guid provided in signature. Mandatory.
     * @param guid the guid
     * @return this instance
     */
    public VideoCreateRequest withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    /**
     * The ingest recipe to use. The default ingest recipe will be used
     * if not provided. Optional.
     * @param ingestRecipeId the ingest recipe id
     * @return this instance
     */
    public VideoCreateRequest withIngestRecipeId(Integer ingestRecipeId) {
        this.ingestRecipeId = ingestRecipeId;
        return this;
    }

    /**
     * Set the title of the video. If not provided this will default to
     * your source filename. Optional.
     * @param title the video title
     * @return this instance
     */
    public VideoCreateRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set the description of the video. Optional.
     * @param description the description
     * @return this instance
     */
    public VideoCreateRequest withDescription(String description) {
        this.description = description;
        return this;
    }
}
