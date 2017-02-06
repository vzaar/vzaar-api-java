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

    /**
     * Set the title of the video. Optional.
     * @param title the video title
     * @return this instance
     */
    public VideoUpdateRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set the description of the video. Optional.
     * @param description the description
     * @return this instance
     */
    public VideoUpdateRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set the privacy of the video. Private videos cannot be publicly
     * viewed on vzaar.com. Optional.
     * @param isPrivate set video privacy
     * @return this instance
     */
    public VideoUpdateRequest withPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }

    /**
     * Set the SEO URL. URL for SEO purposes. Optional.
     * @param seoUrl the SEO URL
     * @return this instance
     */
    public VideoUpdateRequest withSeoUrl(String seoUrl) {
        this.seoUrl = seoUrl;
        return this;
    }

    /**
     * Set the categories the video belongs to. List of category
     * id values to associate with this video. Optional.
     * @param categoryIds list of category id values
     * @return this instance
     */
    public VideoUpdateRequest withCategoryIds(Set<Integer> categoryIds) {
        this.categoryIds = categoryIds;
        return this;
    }
}
