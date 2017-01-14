package com.vzaar;

public class VideoListRequest extends PageableRequest<VideoListRequest> {
    private String q;
    private Integer categoryId;
    private VideoState state;

    public VideoListRequest() {
        super(VideoListRequest.class);
    }

    public VideoListRequest withQuery(String q) {
        this.q = q;
        return this;
    }

    public VideoListRequest withCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public VideoListRequest withState(VideoState state) {
        this.state = state;
        return this;
    }
}
