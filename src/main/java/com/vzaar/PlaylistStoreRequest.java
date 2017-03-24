package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaylistStoreRequest<T> {
    private final transient Class<T> type;
    private Integer categoryId;
    private String title;
    private String sortBy;
    private SortDirection sortOrder;
    @JsonProperty(value = "private")
    private Boolean isPrivate;
    private String dimensions;
    private Integer maxVids;
    private ControlsPosition position;
    private Boolean autoplay;
    private Boolean continuousPlay;

    PlaylistStoreRequest(Class<T> type) {
        this.type = type;
    }

    /**
     * Set the category id. Mandatory
     * @param categoryId the category id
     * @return this instance
     */
    public T withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return type.cast(this);
    }

    /**
     * Set the title. Mandatory
     * @param title the category id
     * @return this instance
     */
    public T withTitle(String title) {
        this.title = title;
        return type.cast(this);
    }

    /**
     * Sort field for the playlist videos. Must be one of 'title' or 'created_at'. Optional.
     * @param sortBy the field to sort the play list by
     * @return this instance
     */
    public T withSortBy(String sortBy) {
        this.sortBy = sortBy;
        return type.cast(this);
    }

    /**
     * Sort order for the playlist videos. Optional
     * @param sortOrder the sort order
     * @return this instance
     */
    public T withSortOrder(SortDirection sortOrder) {
        this.sortOrder = sortOrder;
        return type.cast(this);
    }

    /**
     * Set if the playlist is private, it cannot be viewed by others on the vzaar website. Optional
     * @param isPrivate the playlist privacy
     * @return this instance
     */
    public T withPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
        return type.cast(this);
    }

    /**
     * Set the width and height for the playlist, must be in the format '780x340'. Optional
     * @param dimensions the playlist dimensions
     * @return this instance
     */
    public T withDimensions(String dimensions) {
        this.dimensions = dimensions;
        return type.cast(this);
    }

    /**
     * Set the maximum number of videos in the playlist. Optional
     * @param maxVids the maximum number of videos in the playlist
     * @return this instance
     */
    public T withMaxVids(Integer maxVids) {
        this.maxVids = maxVids;
        return type.cast(this);
    }

    /**
     * Set the side the playlist controls show. Must be one of 'top', 'right', 'bottom', 'left'. Optional
     * @param position the position
     * @return this instance
     */
    public T withPosition(ControlsPosition position) {
        this.position = position;
        return type.cast(this);
    }

    /**
     * Set should the first video in the playlist autoplay. Optional
     * @param autoplay set if first video should autoplay
     * @return this instance
     */
    public T withAutoplay(Boolean autoplay) {
        this.autoplay = autoplay;
        return type.cast(this);
    }

    /**
     * Set should all the videos autoplay after each finishes. Optional
     * @param continuousPlay set if continous play
     * @return this instance
     */
    public T withContinuousPlay(Boolean continuousPlay) {
        this.continuousPlay = continuousPlay;
        return type.cast(this);
    }
}
