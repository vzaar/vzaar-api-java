package com.vzaar;

import com.vzaar.client.Resource;

/**
 * Base class for pageable requests
 *
 * @param <R> the pageable request type
 * @param <T> the entity type
 */
public class PageableRequest<R, T> {
    private final transient Class<R> type;
    private final transient Resource<T> resource;
    private String sort;
    private SortDirection order;
    private Integer page;
    private Integer perPage;

    protected PageableRequest(Class<R> type, Resource<T> resource) {
        this.type = type;
        this.resource = resource;
    }

    /**
     * Entity attribute to sort by. Attribute should be in snake case. For
     * example Video.createdAt would be "created_at". Optional.
     *
     * @param sort the attribute in snake case
     * @return this instance
     */
    public R withSortByAttribute(String sort) {
        this.sort = sort;
        return type.cast(this);
    }

    /**
     * Set sort order direction. Optional
     *
     * @param order the sort order direction
     * @return this instance
     */
    public R withSortDirection(SortDirection order) {
        this.order = order;
        return type.cast(this);
    }

    /**
     * Set the page number to retrieve. Page numbers start from 1. Optional.
     *
     * @param page the page 1 based page index
     * @return this instance
     */
    public R withPage(int page) {
        this.page = page;
        return type.cast(this);
    }

    /**
     * The number of results per page. Optional.
     *
     * @param perPage the results per pages
     * @return this instance
     */
    public R withResultsPerPage(int perPage) {
        this.perPage = perPage;
        return type.cast(this);
    }

    /**
     * Send the search / listing request
     * @return the results for the request
     */
    public Page<T> results() {
        return resource.page(this);
    }
}
