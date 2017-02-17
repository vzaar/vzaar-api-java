package com.vzaar;

import com.vzaar.client.Resource;

public class IngestRecipeCreateRequest extends IngestRecipeStoreRequest<IngestRecipeCreateRequest> {
    private transient Resource<IngestRecipe> resource;

    IngestRecipeCreateRequest(Resource<IngestRecipe> resource) {
        super(IngestRecipeCreateRequest.class);
        this.resource = resource;
    }

    /**
     * Create and get the new ingest recipe for this request
     * @return the created ingest recipe
     */
    public IngestRecipe result() {
        return resource.create(this);
    }
}
