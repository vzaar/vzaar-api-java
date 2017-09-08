package com.vzaar;

import com.vzaar.client.Resource;

public class PlaylistCreateRequest extends PlaylistStoreRequest<PlaylistCreateRequest> {
    private transient Resource<Playlist> resource;

    PlaylistCreateRequest(Resource<Playlist> resource) {
        super(PlaylistCreateRequest.class);
        this.resource = resource;
    }

    /**
     * Create and get the new playlist for this request
     * @return the created ingest recipe
     */
    public Playlist result() {
        return resource.create(this);
    }
}
