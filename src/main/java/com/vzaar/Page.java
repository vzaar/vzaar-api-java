package com.vzaar;

import java.util.List;

public class Page<T> {
    private List<T> data;
    private PageMetadata meta;

    public List<T> getData() {
        return data;
    }

    public PageMetadata getMeta() {
        return meta;
    }
}
