package com.vzaar;

public class CreateVideoRequest {
    private String guid;
    private Integer ingestRecipeId;
    private String title;
    private String description;

    public CreateVideoRequest withGuid(String guid) {
        this.guid = guid;
        return this;
    }

    public CreateVideoRequest withIngestRecipeId(int ingestRecipeId) {
        this.ingestRecipeId = ingestRecipeId;
        return this;
    }

    public CreateVideoRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    public CreateVideoRequest withDescription(String description) {
        this.description = description;
        return this;
    }
}
