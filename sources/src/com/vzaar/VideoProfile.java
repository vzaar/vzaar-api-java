package com.vzaar;

public enum VideoProfile {
    SMALL(1),
    MEDIUM(2),
    LARGE(3),
    HIGH_DEFINITION(4),
    ORIGINAL(5),
    CUSTOM(6);

    private int value;

    VideoProfile(int value) {
        this.value = value;
    }
}
