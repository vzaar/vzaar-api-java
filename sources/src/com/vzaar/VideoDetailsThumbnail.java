package com.vzaar;

import org.codehaus.jackson.annotate.JsonProperty;

public class VideoDetailsThumbnail {
    @JsonProperty("thumbnail_width")
    public int width;
    @JsonProperty("thumbnail_height")
    public int height;
    @JsonProperty("thumbnail_url")
    public String url;

    @Override
    public String toString() {
        return "VideoDetailsThumbnail{" +
                "width=" + width +
                ", height=" + height +
                ", url='" + url + '\'' +
                '}';
    }
}
