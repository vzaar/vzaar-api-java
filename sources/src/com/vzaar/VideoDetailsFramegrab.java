package com.vzaar;

import org.codehaus.jackson.annotate.JsonProperty;

public class VideoDetailsFramegrab {
    @JsonProperty("framegrab_width")
    public int width;
    @JsonProperty("framegrab_height")
    public int height;
    @JsonProperty("framegrab_url")
    public String url;

    @Override
    public String toString() {
        return "VideoDetailsFramegrab{" +
                "width=" + width +
                ", height=" + height +
                ", url='" + url + '\'' +
                '}';
    }
}
