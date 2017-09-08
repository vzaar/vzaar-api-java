package com.vzaar;

import com.vzaar.client.Resource;

public class EncodingPresetPageRequest extends PageableRequest<EncodingPresetPageRequest, EncodingPreset> {
    EncodingPresetPageRequest() {
        this(null);
    }
    EncodingPresetPageRequest(Resource<EncodingPreset> resource) {
        super(EncodingPresetPageRequest.class, resource);
    }
}
