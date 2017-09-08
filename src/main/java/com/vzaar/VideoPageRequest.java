package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.util.QueryEscaper;

public class VideoPageRequest extends PageableRequest<VideoPageRequest, Video> {
    private String q;
    private Integer categoryId;
    private Boolean categorised;
    private VideoState state;

    VideoPageRequest() {
        this(null);
    }

    VideoPageRequest(Resource<Video> resource) {
        super(VideoPageRequest.class, resource);
    }

    /**
     * Query for finding videos. Optional.
     * @param q the query
     * @return this instance
     */
    public VideoPageRequest withQuery(String q) {
        this.q = q;
        return this;
    }

    /**
     * Equivalent of VideoPageRequest.withQuery(QueryEscaper.escape(q)). This escapes characters
     * in the complete query string. Optional.
     * @param q the query
     * @return this instance
     */
    public VideoPageRequest withEscapedQuery(String q) {
        return withQuery(QueryEscaper.escape(q));
    }

    /**
     * Search for videos belonging to the specified categories. Note that there is a short
     * delay between setting a categories on a video and it being indexed so the video
     * may not appear in the search results immediately. Optional.
     * @param categoryId the categories id
     * @return this instance
     */
    public VideoPageRequest withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    /**
     * If supplied searches for videos that are categorised or uncategorised exclusively. Optional
     * @param categorised true for categorised, false for uncategorised, null for either
     * @return this instance
     */
    public VideoPageRequest withIsCategorised(Boolean categorised) {
        this.categorised = categorised;
        return this;
    }

    /**
     * By default, only videos in a ready state are returned in the video list. Any videos that
     * are busy processing or have failed during processing are excluded from the video list.
     * You can however still retrieve these videos by filtering on the video state. Optional.
     * @param state the state
     * @return this instance
     */
    public VideoPageRequest withState(VideoState state) {
        this.state = state;
        return this;
    }
}
