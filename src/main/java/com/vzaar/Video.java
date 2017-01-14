package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public class Video {
    private int id;
    private String title;
    private Integer userId;
    private Integer accountId;
    private String description;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    @JsonProperty(value = "private")
    private boolean isPrivate;
    private String seoUrl;
    private String url;
    private VideoState state;
    private String thumbnailUrl;
    private String embedCode;
    private List<Rendition> renditions;
    private List<LegacyRendition> legacyRenditions;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getDescription() {
        return description;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getSeoUrl() {
        return seoUrl;
    }

    public String getUrl() {
        return url;
    }

    public VideoState getState() {
        return state;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getEmbedCode() {
        return embedCode;
    }

    public List<Rendition> getRenditions() {
        return renditions;
    }

    public List<LegacyRendition> getLegacyRenditions() {
        return legacyRenditions;
    }
}
