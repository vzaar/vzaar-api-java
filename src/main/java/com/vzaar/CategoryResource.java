package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.client.RestClient;

public class CategoryResource {
    private final RestClient client;

    CategoryResource(RestClient client) {
        this.client = client;
    }


    /**
     * Start category search / listing request.
     * @return the request
     */
    public CategoryPageRequest list() {
        return new CategoryPageRequest(resource());
    }

    /**
     * Start category search / listing for a subtree request. Listing a category's subtree will always include the
     * category itself, plus any descendants that may exist.
     * @param categoryId the parent category id
     * @return the request
     */
    public CategoryPageRequest subtree(int categoryId) {
        return new CategoryPageRequest(id(categoryId).action("subtree"));
    }

    /**
     * Get an category by id
     * @param categoryId the id
     * @return the category
     */
    public Category get(int categoryId) {
        return id(categoryId).lookup();
    }

    /**
     * Start a category create request
     * @return the request
     */
    public CategoryCreateRequest create() {
        return new CategoryCreateRequest(resource());
    }

    /**
     * Start a category update request
     * @param categoryId the id
     * @return the request
     */
    public CategoryUpdateRequest update(int categoryId) {
        return new CategoryUpdateRequest(id(categoryId));
    }

    /**
     * Delete a category
     * @param categoryId the id
     */
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
