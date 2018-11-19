package com.vzaar;

import com.vzaar.client.Resource;

public class SubtitleUpdateRequest extends SubtitleStoreRequest<SubtitleUpdateRequest> {
    private transient Resource<Subtitle> resource;

    SubtitleUpdateRequest(Resource<Subtitle> resource) {
        super(SubtitleUpdateRequest.class);
        this.resource = resource;
    }

    /**
     * Update the subtitle for this request
     * @return the created subtitle entity
     */
    public Subtitle result() {
        return hasFile()
                ? resource.updateWithUpload(asMap())
                : resource.update(this);
    }
}
