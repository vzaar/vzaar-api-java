package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class VideoUpdateRequest {
    private String title;
    private String description;
    @JsonProperty(value = "private")
    private Boolean isPrivate;
    private String seoUrl;
    private Set<Integer> categoryIds;

    public VideoUpdateRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    public VideoUpdateRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    public VideoUpdateRequest withPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
        return this;
    }

    public VideoUpdateRequest withSeoUrl(String seoUrl) {
        this.seoUrl = seoUrl;
        return this;
    }

    public VideoUpdateRequest withCategoryIds(Set<Integer> categoryIds) {
        this.categoryIds = categoryIds;
        return this;
    }
}
