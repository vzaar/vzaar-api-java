package com.vzaar;

import com.vzaar.client.ResourcePath;

import java.time.ZonedDateTime;

@ResourcePath(path = "videos")
public class Subtitle implements Identifiable {
    private int id;
    private String title;
    private String code;
    private String language;
    private String url;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @Override
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getLanguage() {
        return language;
    }

    public String getUrl() {
        return url;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
}
