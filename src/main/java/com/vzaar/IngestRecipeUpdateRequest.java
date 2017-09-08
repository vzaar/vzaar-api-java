package com.vzaar;

import com.vzaar.client.Resource;

public class IngestRecipeUpdateRequest extends IngestRecipeStoreRequest<IngestRecipeUpdateRequest> {
    private transient Resource<IngestRecipe> resource;

    IngestRecipeUpdateRequest(Resource<IngestRecipe> resource) {
        super(IngestRecipeUpdateRequest.class);
        this.resource = resource;
    }

    /**
     * Update and get the updated ingest recipe for this request
     * @return the updated ingest recipe
     */
    public IngestRecipe result() {
        return resource.update(this);
    }
}
