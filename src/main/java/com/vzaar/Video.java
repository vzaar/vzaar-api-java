package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vzaar.client.ResourcePath;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ResourcePath(path = "videos")
public class Video implements Identifiable {
    private int id;
    private String title;
    private Integer userId;
    private String userLogin;
    private Integer accountId;
    private String accountName;
    private String description;
    private Double duration;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    @JsonProperty(value = "private")
    private boolean isPrivate;
    private VideoState state;
    private String url;
    private String seoUrl;
    private String thumbnailUrl;
    private String assetUrl;
    private String posterUrl;
    private String embedCode;
    private List<Category> categories = new ArrayList<>();
    private List<Advert> adverts = new ArrayList<>();
    private List<Rendition> renditions = new ArrayList<>();
    private List<LegacyRendition> legacyRenditions = new ArrayList<>();
    private List<Subtitle> subtitles = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getDescription() {
        return description;
    }

    public Double getDuration() {
        return duration;
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

    public VideoState getState() {
        return state;
    }

    public String getUrl() {
        return url;
    }

    public String getSeoUrl() {
        return seoUrl;
    }

    public String getAssetUrl() {
        return assetUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
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

    public List<Category> getCategories() {
        return categories;
    }

    public List<Advert> getAdverts() {
        return adverts;
    }

    public List<Subtitle> getSubtitles() {
        return subtitles;
    }
}
