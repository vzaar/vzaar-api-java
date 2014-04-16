package com.vzaar;

import org.codehaus.jackson.annotate.JsonProperty;

public class VideoDetailsProvider {
    @JsonProperty("provider_name")
    public String name;
    @JsonProperty("provider_url")
    public String url;

    @Override
    public String toString() {
        return "VideoDetailsProvider{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
