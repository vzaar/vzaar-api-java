package com.vzaar;

import com.vzaar.client.Resource;

public class PlaylistUpdateRequest extends PlaylistStoreRequest<PlaylistUpdateRequest> {
    private transient Resource<Playlist> resource;

    PlaylistUpdateRequest(Resource<Playlist> resource) {
        super(PlaylistUpdateRequest.class);
        this.resource = resource;
    }

    /**
     * Update and get the updated playlist for this request
     * @return the updated ingest recipe
     */
    public Playlist result() {
        return resource.update(this);
    }
}
