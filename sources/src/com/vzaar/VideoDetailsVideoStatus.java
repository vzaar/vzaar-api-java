package com.vzaar;

import org.codehaus.jackson.annotate.JsonProperty;

public class VideoDetailsVideoStatus {
    @JsonProperty("video_status_id")
    public int id;
    @JsonProperty("video_status_description")
    public String description;

    @Override
    public String toString() {
        return "VideoDetailsVideoStatus{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
