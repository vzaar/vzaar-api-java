package com.vzaar;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class Video {
    public String version;
    public long id;
    public String title;
    public String description;
    public Date createdAt;
    public BigDecimal duration;
    public String status;
    public int statusId;
    public String url;
    public String thumbnail;
    public int height;
    public int width;
    public long playCount;
    public VideoAuthor user;

    public static <T> T fromJson(TypeReference<T> typeReference, String json) {
        T data = null;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        try {
            data = objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public String toString() {
        return "Video{" +
                "version='" + version + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", duration=" + duration +
                ", status='" + status + '\'' +
                ", statusId=" + statusId +
                ", url='" + url + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", playCount=" + playCount +
                ", user=" + user +
                '}';
    }
}
