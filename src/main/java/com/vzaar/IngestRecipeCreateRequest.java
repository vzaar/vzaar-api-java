package com.vzaar;

import com.vzaar.client.Resource;

public class IngestRecipeCreateRequest extends IngestRecipeStoreRequest<IngestRecipeCreateRequest> {
    private transient Resource<IngestRecipe> resource;

    private IngestRecipeCreateRequest() {
        this(null);
    }

    IngestRecipeCreateRequest(Resource<IngestRecipe> resource) {
        super(IngestRecipeCreateRequest.class);
        this.resource = resource;
    }

    public IngestRecipe result() {
        return resource.create(this);
    }
}
