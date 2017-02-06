package com.vzaar;

public class CategoryCreateRequest {
    private String name;
    private Integer parentId;

    /**
     * Set the name of the category. Mandatory.
     * @param name the name
     * @return this instance
     */
    public CategoryCreateRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the parent category if this is to be a child category. Optional.
     * @param parentId this parent category id
     * @return this instance
     */
    public CategoryCreateRequest withParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
}
