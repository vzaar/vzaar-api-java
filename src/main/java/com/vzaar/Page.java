package com.vzaar;

import com.vzaar.client.Resource;

import java.util.List;

public class Page<T> {
    private List<T> data;
    private PageMetadata meta;
    private transient Resource<T> resource;

    public List<T> getData() {
        return data;
    }

    public int getTotalCount() {
        return meta.getTotalCount();
    }

    public Page<T> getFirst() {
        return resource.pageWithUrl(meta.getFirstLink());
    }

    public boolean hasNext() {
        return meta.hasNextLink();
    }

    public Page<T> getNext() {
        return resource.pageWithUrl(meta.getNextLink());
    }

    public boolean hasPrevious() {
        return meta.hasPreviousLink();
    }

    public Page<T> getPrevious() {
        return resource.pageWithUrl(meta.getPreviousLink());
    }

    public boolean hasLast() {
        return meta.hasLastLink();
    }

    public Page<T> getLast() {
        return resource.pageWithUrl(meta.getLastLink());
    }

    public PageMetadata getMeta() {
        return meta;
    }

    public void setResource(Resource<T> resource) {
        this.resource = resource;
    }
}
