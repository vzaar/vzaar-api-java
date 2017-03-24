package com.vzaar;

import com.vzaar.client.Resource;

public class PlaylistPageRequest extends PageableRequest<PlaylistPageRequest, Playlist> {
    PlaylistPageRequest() {
        this(null);
    }
    PlaylistPageRequest(Resource<Playlist> resource) {
        super(PlaylistPageRequest.class, resource);
    }
}
