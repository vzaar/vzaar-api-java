package com.vzaar;

public class CategoryCreateRequest {
    private String name;
    private Integer parentId;

    public CategoryCreateRequest withName(String name) {
        this.name = name;
        return this;
    }

    public CategoryCreateRequest withParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
}
