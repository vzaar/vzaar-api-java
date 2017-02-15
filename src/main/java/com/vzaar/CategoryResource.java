package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.client.RestClient;

public class CategoryResource {
    private final RestClient client;

    CategoryResource(RestClient client) {
        this.client = client;
    }

    public CategoryPageRequest list() {
        return new CategoryPageRequest(resource());
    }

    public CategoryPageRequest subtree(int categoryId) {
        return new CategoryPageRequest(id(categoryId).action("subtree"));
    }

    public Category get(int categoryId) {
        return id(categoryId).lookup();
    }

    public CategoryCreateRequest create() {
        return new CategoryCreateRequest(resource());
    }

    public CategoryUpdateRequest update(int categoryId) {
        return new CategoryUpdateRequest(id(categoryId));
    }

    public void delete(int categoryId) {
        id(categoryId).delete();
    }

    private Resource<Category> resource() {
        return client.resource(Category.class);
    }

    private Resource<Category> id(int id) {
        return resource().id(id);
    }
}
