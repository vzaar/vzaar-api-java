package com.vzaar;

import com.vzaar.util.QueryEscaper;

public class VideoPageRequest extends PageableRequest<VideoPageRequest> {
    private String q;
    private Integer categoryId;
    private Boolean categorised;
    private VideoState state;

    public VideoPageRequest() {
        super(VideoPageRequest.class);
    }

    public VideoPageRequest withQuery(String q) {
        this.q = q;
        return this;
    }

    public VideoPageRequest withEscapedQuery(String q) {
        return withQuery(QueryEscaper.escape(q));
    }

    public VideoPageRequest withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public VideoPageRequest withIsCategorised(Boolean categorised) {
        this.categorised = categorised;
        return this;
    }

    public VideoPageRequest withState(VideoState state) {
        this.state = state;
        return this;
    }
}
