package com.vzaar;

public class PageableRequest<T> {
    private final transient Class<T> type;
    private String sort;
    private SortDirection order;
    private Integer page;
    private Integer perPage;

    protected PageableRequest(Class<T> type) {
        this.type = type;
    }

    public T withSortByAttribute(String sort) {
        this.sort = sort;
        return type.cast(this);
    }

    public T withSortDirection(SortDirection order) {
        this.order = order;
        return type.cast(this);
    }

    public T withPage(int page) {
        this.page = page;
        return type.cast(this);
    }

    public T withResultsPerPage(int perPage) {
        this.perPage = perPage;
        return type.cast(this);
    }
}
