package com.vzaar;

import java.util.List;

public class CategoryPageRequest extends PageableRequest<CategoryPageRequest> {

    private Integer levels;
    private List<Integer> ids;

    public CategoryPageRequest() {
        super(CategoryPageRequest.class);
    }

    /**
     * Set the depth of the category tree to return. For example setting it to 1
     * will return only root categories. Optional.
     * @param levels the number of levels of categories to retrieve
     * @return this instance
     */
    public CategoryPageRequest withLevels(Integer levels) {
        if (levels != null && levels < 1) {
            throw new IllegalArgumentException(String.format("levels must be 1 or greater, received=[%s]", levels));
        }

        this.levels = levels;
        return this;
    }

    /**
     * Select the exact categories to return. This will not return child categories
     * @param ids the category ids to return
     * @return this instance
     */
    public CategoryPageRequest withIds(List<Integer> ids) {
        this.ids = ids;
        return this;
    }
}
