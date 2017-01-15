package com.vzaar;

public class VideoPageRequest extends PageableRequest<VideoPageRequest> {
    private String q;
    private Integer categoryId;
    private VideoState state;

    public VideoPageRequest() {
        super(VideoPageRequest.class);
    }

    public VideoPageRequest withQuery(String q) {
        this.q = q;
        return this;
    }

    public VideoPageRequest withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public VideoPageRequest withState(VideoState state) {
        this.state = state;
        return this;
    }
}
