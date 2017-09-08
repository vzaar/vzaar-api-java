package com.vzaar;

import com.vzaar.client.Resource;

import java.util.List;

public class Page<T> {
    private List<T> data;
    private PageMetadata meta;
    private transient Resource<T> resource;

    /**
     * Get the results of the query
     * @return a list of results for the given page
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Get the total number of results for the query
     * @return the total count
     */
    public int getTotalCount() {
        return meta.getTotalCount();
    }

    /**
     * Request the first page for the request used to generate this page
     * @return the first page
     */
    public Page<T> getFirst() {
        return resource.pageWithUrl(meta.getFirstLink());
    }

    /**
     * Is there a page after the current one for the request used to generate this page
     * @return true if there is
     */
    public boolean hasNext() {
        return meta.hasNextLink();
    }

    /**
     * Request the next page for the request used to generate this page
     * @return the next page
     */
    public Page<T> getNext() {
        return resource.pageWithUrl(meta.getNextLink());
    }

    /**
     * Is there a page before the current one for the request used to generate this page
     * @return true if there is
     */
    public boolean hasPrevious() {
        return meta.hasPreviousLink();
    }

    /**
     * Request the previous page for the request used to generate this page
     * @return the previous page
     */
    public Page<T> getPrevious() {
        return resource.pageWithUrl(meta.getPreviousLink());
    }

    /**
     * Is there a last page for the request used to generate this page
     * @return true if there is
     */
    public boolean hasLast() {
        return meta.hasLastLink();
    }

    /**
     * Request the last page for the request used to generate this page
     * @return the last page
     */
    public Page<T> getLast() {
        return resource.pageWithUrl(meta.getLastLink());
    }

    /**
     * For internal use. Do not call this method.
     * @param resource the resource
     */
    public void setResource(Resource<T> resource) {
        this.resource = resource;
    }
}
