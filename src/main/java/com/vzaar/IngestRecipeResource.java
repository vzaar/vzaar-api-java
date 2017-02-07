package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.client.RestClient;

public class IngestRecipeResource {
    private final RestClient client;

    IngestRecipeResource(RestClient client) {
        this.client = client;
    }

    public IngestRecipePageRequest list() {
        return new IngestRecipePageRequest(resource());
    }

    public IngestRecipe get(int ingestRecipeId) {
        return resource().lookup(ingestRecipeId);
    }

    public IngestRecipeCreateRequest create() {
        return new IngestRecipeCreateRequest(resource());
    }

    public IngestRecipeUpdateRequest update(int ingestRecipeId) {
        return new IngestRecipeUpdateRequest(id(ingestRecipeId));
    }

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
