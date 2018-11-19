package com.vzaar;

import java.time.ZonedDateTime;

public class Advert {
    private int advertId;
    private String name;
    private String tag;
    private String position;
    private String time;
    private String placementScope;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public int getAdvertId() {
        return advertId;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getPosition() {
        return position;
    }

    public String getTime() {
        return time;
    }

    public String getPlacementScope() {
        return placementScope;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
}
