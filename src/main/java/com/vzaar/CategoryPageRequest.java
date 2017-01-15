package com.vzaar;

import java.util.List;

public class CategoryPageRequest extends PageableRequest<CategoryPageRequest> {

    private Integer levels;
    private List<Integer> ids;

    public CategoryPageRequest() {
        super(CategoryPageRequest.class);
    }

    public CategoryPageRequest withLevels(Integer levels) {
        if (levels != null && levels < 1) {
            throw new IllegalArgumentException(String.format("levels must be 1 or greater, received=[%s]", levels));
        }

        this.levels = levels;
        return this;
    }

    public CategoryPageRequest withIds(List<Integer> ids) {
        this.ids = ids;
        return this;
    }
}
