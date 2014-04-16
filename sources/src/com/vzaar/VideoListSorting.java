package com.vzaar;

public enum VideoListSorting {
    ASCENDING(0),
    DESCENDING(1);

    private int value;
    VideoListSorting(int value) {
        this.value = value;
    }
}
