package com.vzaar;

import com.vzaar.client.Resource;

public class IngestRecipePageRequest extends PageableRequest<IngestRecipePageRequest, IngestRecipe>  {
    IngestRecipePageRequest() {
        this(null);
    }
    IngestRecipePageRequest(Resource<IngestRecipe> resource) {
        super(IngestRecipePageRequest.class, resource);
    }
}
