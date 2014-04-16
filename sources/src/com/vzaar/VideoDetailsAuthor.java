package com.vzaar;

import org.codehaus.jackson.annotate.JsonProperty;

public class VideoDetailsAuthor {
    @JsonProperty("author_name")
    public String name;
    @JsonProperty("author_url")
    public String url;

    @Override
    public String toString() {
        return "VideoDetailsAuthor{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
