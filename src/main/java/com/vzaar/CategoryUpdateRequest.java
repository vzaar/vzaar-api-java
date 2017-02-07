package com.vzaar;

import com.vzaar.client.Resource;

public class CategoryUpdateRequest {
    private transient Resource<Category> resource;
    private String name;
    private Integer parentId;
    private Boolean moveToRoot;

    private CategoryUpdateRequest() {
        this(null);
    }

    CategoryUpdateRequest(Resource<Category> resource) {
        this.resource = resource;
    }

    /**
     * Set the name of the categories. Not setting the value will leave the
     * current value unchanged. Optional.
     * @param name the categories name
     * @return this instance
     */
    public CategoryUpdateRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the parent id of the categories. Not setting the value will leave the
     * current value unchanged. Optional.
     *
     * If moveToRoot is set then parentId should not be set
     * @param parentId the categories id of the new parent for this categories
     * @return this instance
     */
    public CategoryUpdateRequest withParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    /**
     * Move the categories to the root. Not setting the value will leave the
     * current value unchanged. Optional.
     *
     * If parentId is set on the request then moveToRoot should not be set
     * @param moveToRoot set to true to move a child node to a root categories
     * @return this instance
     */
    public CategoryUpdateRequest withMoveToRoot(boolean moveToRoot) {
        this.moveToRoot = moveToRoot ? true : null;
        return this;
    }

    public Category result() {
        return resource.update(this);
    }
}
