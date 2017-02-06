package com.vzaar;

public class CategoryUpdateRequest {
    private String name;
    private Integer parentId;
    private Boolean moveToRoot;

    public CategoryUpdateRequest withName(String name) {
        this.name = name;
        return this;
    }

    public CategoryUpdateRequest withParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public CategoryUpdateRequest withMoveToRoot(boolean moveToRoot) {
        this.moveToRoot = moveToRoot ? true : null;
        return this;
    }
}
