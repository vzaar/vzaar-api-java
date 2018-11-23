package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vzaar.client.ResourcePath;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ResourcePath(path = "feeds/playlists")
public class Playlist implements Identifiable {
    private int id;
    private int categoryId;
    private String title;
    private String sortBy;
    private SortDirection sortOrder;
    @JsonProperty(value = "private")
    private boolean isPrivate;
    private String dimensions;
    private int maxVids;
    private ControlsPosition position;
    private boolean autoplay;
    private boolean continuousPlay;
    private String embedCode;
    private List<Video> videos = new ArrayList<>();
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title;
    }

    public String getSortBy() {
        return sortBy;
    }

    public SortDirection getSortOrder() {
        return sortOrder;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getDimensions() {
        return dimensions;
    }

    public int getMaxVids() {
        return maxVids;
    }

    public ControlsPosition getPosition() {
        return position;
    }

    public boolean isAutoplay() {
        return autoplay;
    }

    public boolean isContinuousPlay() {
        return continuousPlay;
    }

    public String getEmbedCode() {
        return embedCode;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
}
