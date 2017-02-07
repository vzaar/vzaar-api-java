package com.vzaar;

import com.vzaar.client.Resource;

public class IngestRecipeUpdateRequest extends IngestRecipeStoreRequest<IngestRecipeUpdateRequest> {
    private transient Resource<IngestRecipe> resource;

    private IngestRecipeUpdateRequest() {
        this(null);
    }

    IngestRecipeUpdateRequest(Resource<IngestRecipe> resource) {
        super(IngestRecipeUpdateRequest.class);
        this.resource = resource;
    }

    public IngestRecipe result() {
        return resource.update(this);
    }
}
