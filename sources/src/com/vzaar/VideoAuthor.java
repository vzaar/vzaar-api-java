package com.vzaar;

import org.codehaus.jackson.annotate.JsonProperty;

public class VideoAuthor {
    @JsonProperty("author_name")
    public String name;
    @JsonProperty("author_url")
    public String url;
    @JsonProperty("author_account")
    public int account;
    public long videoCount;

    @Override
    public String toString() {
        return "VideoAuthor{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", account=" + account +
                ", videoCount=" + videoCount +
                '}';
    }
}
