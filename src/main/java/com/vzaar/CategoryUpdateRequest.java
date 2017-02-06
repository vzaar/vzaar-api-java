package com.vzaar;

public class CategoryUpdateRequest {
    private String name;
    private Integer parentId;
    private Boolean moveToRoot;

    /**
     * Set the name of the category. Not setting the value will leave the
     * current value unchanged. Optional.
     * @param name the category name
     * @return this instance
     */
    public CategoryUpdateRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the parent id of the category. Not setting the value will leave the
     * current value unchanged. Optional.
     *
     * If moveToRoot is set then parentId should not be set
     * @param parentId the category id of the new parent for this category
     * @return this instance
     */
    public CategoryUpdateRequest withParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    /**
     * Move the category to the root. Not setting the value will leave the
     * current value unchanged. Optional.
     *
     * If parentId is set on the request then moveToRoot should not be set
     * @param moveToRoot set to true to move a child node to a root category
     * @return this instance
     */
    public CategoryUpdateRequest withMoveToRoot(boolean moveToRoot) {
        this.moveToRoot = moveToRoot ? true : null;
        return this;
    }
}
