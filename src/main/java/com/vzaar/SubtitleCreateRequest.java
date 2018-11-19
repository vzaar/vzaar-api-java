package com.vzaar;

import com.vzaar.client.Resource;

public class SubtitleCreateRequest extends SubtitleStoreRequest<SubtitleCreateRequest> {
    private transient Resource<Subtitle> resource;

    SubtitleCreateRequest(Resource<Subtitle> resource) {
        super(SubtitleCreateRequest.class);
        this.resource = resource;
    }

    /**
     * Create and get the new subtitle for this request
     *
     * @return the created subtitle entity
     */
    public Subtitle result() {
        return hasFile()
                ? resource.createWithUpload(asMap())
                : resource.create(this);
    }
}
