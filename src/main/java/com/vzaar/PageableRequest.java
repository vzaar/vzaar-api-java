package com.vzaar;

/**
 * Base class for pageable requests
 * @param <T>
 */
public class PageableRequest<T> {
    private final transient Class<T> type;
    private String sort;
    private SortDirection order;
    private Integer page;
    private Integer perPage;

    protected PageableRequest(Class<T> type) {
        this.type = type;
    }

    /**
     * Entity attribute to sort by. Attribute should be in snake case. For
     * example Video.createdAt would be "created_at". Optional.
     * @param sort the attribute in snake case
     * @return this instance
     */
    public T withSortByAttribute(String sort) {
        this.sort = sort;
        return type.cast(this);
    }

    /**
     * Set sort order direction. Optional
     * @param order the sort order direction
     * @return this instance
     */
    public T withSortDirection(SortDirection order) {
        this.order = order;
        return type.cast(this);
    }

    /**
     * Set the page number to retrieve. Page numbers start from 1. Optional.
     * @param page the page 1 based page index
     * @return this instance
     */
    public T withPage(int page) {
        this.page = page;
        return type.cast(this);
    }

    /**
     * The number of results per page. Optional.
     * @param perPage the results per pages
     * @return this instance
     */
    public T withResultsPerPage(int perPage) {
        this.perPage = perPage;
        return type.cast(this);
    }
}
