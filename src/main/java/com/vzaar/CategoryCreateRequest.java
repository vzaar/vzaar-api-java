package com.vzaar;

import com.vzaar.client.Resource;

public class CategoryCreateRequest {
    private transient Resource<Category> resource;
    private String name;
    private Integer parentId;

    private CategoryCreateRequest() {
        this(null);
    }

    CategoryCreateRequest(Resource<Category> resource) {
        this.resource = resource;
    }

    /**
     * Set the name of the categories. Mandatory.
     * @param name the name
     * @return this instance
     */
    public CategoryCreateRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the parent categories if this is to be a child categories. Optional.
     * @param parentId this parent categories id
     * @return this instance
     */
    public CategoryCreateRequest withParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public Category result() {
        return resource.create(this);
    }
}
