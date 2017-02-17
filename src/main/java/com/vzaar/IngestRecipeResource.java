package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.client.RestClient;

public class IngestRecipeResource {
    private final RestClient client;

    IngestRecipeResource(RestClient client) {
        this.client = client;
    }

    /**
     * Start an ingest recipe search / listing request.
     * @return the request
     */
    public IngestRecipePageRequest list() {
        return new IngestRecipePageRequest(resource());
    }

    /**
     * Get an ingest recipe by id
     * @param ingestRecipeId the id
     * @return the ingest recipe
     */
    public IngestRecipe get(int ingestRecipeId) {
        return id(ingestRecipeId).lookup();
    }

    /**
     * Start an ingest recipe create request
     * @return the request
     */
    public IngestRecipeCreateRequest create() {
        return new IngestRecipeCreateRequest(resource());
    }

    /**
     * Start an ingest recipe update request
     * @param ingestRecipeId the id
     * @return the request
     */
    public IngestRecipeUpdateRequest update(int ingestRecipeId) {
        return new IngestRecipeUpdateRequest(id(ingestRecipeId));
    }

    /**
     * Delete an ingest recipe. If you try to delete your default ingest recipe you will receive an error response.
     * In this case you will first need to update another ingest recipe as your new default, and then proceed to delete
     * the original ingest recipe.
     * @param ingestRecipeId the id
     */
    public void delete(int ingestRecipeId) {
        id(ingestRecipeId).delete();
    }

    private Resource<IngestRecipe> resource() {
        return client.resource(IngestRecipe.class);
    }

    private Resource<IngestRecipe> id(int id) {
        return resource().id(id);
    }
}
