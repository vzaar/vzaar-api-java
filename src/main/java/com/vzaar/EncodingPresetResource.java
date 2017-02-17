package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.client.RestClient;

public class EncodingPresetResource {
    private final RestClient client;

    EncodingPresetResource(RestClient client) {
        this.client = client;
    }

    /**
     * Start an encoding preset search / listing request.
     * @return the request
     */
    public EncodingPresetPageRequest list() {
        return new EncodingPresetPageRequest(resource());
    }

    /**
     * Get an encodingPresetId by id
     * @param encodingPresetId the id
     * @return the encodingPresetId
     */
    public EncodingPreset get(int encodingPresetId) {
        return id(encodingPresetId).lookup();
    }

    private Resource<EncodingPreset> resource() {
        return client.resource(EncodingPreset.class);
    }

    private Resource<EncodingPreset> id(int id) {
        return resource().id(id);
    }
}
