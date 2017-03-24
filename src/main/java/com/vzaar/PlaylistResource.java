package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.client.RestClient;

public class PlaylistResource {
    private final RestClient client;

    PlaylistResource(RestClient client) {
        this.client = client;
    }

    /**
     * Start a playlist search / listing request.
     * @return the request
     */
    public PlaylistPageRequest list() {
        return new PlaylistPageRequest(resource());
    }

    /**
     * Get a playlist by id.
     * @param playlistId the id of the playlist
     * @return the playlist
     */
    public Playlist get(int playlistId) {
        return id(playlistId).lookup();
    }

    /**
     * Start a playlist create request
     * @return the request
     */
    public PlaylistCreateRequest create() {
        return new PlaylistCreateRequest(resource());
    }

    /**
     * Start a playlist update request
     * @param playlistId the id
     * @return the request
     */
    public PlaylistUpdateRequest update(int playlistId) {
        return new PlaylistUpdateRequest(id(playlistId));
    }

    /**
     * Delete a video
     * @param videoId the id of a video
     */
    public void delete(int videoId) {
        id(videoId).delete();
    }


    private Resource<Playlist> resource() {
        return client.resource(Playlist.class);
    }

    private Resource<Playlist> id(int id) {
        return resource().id(id);
    }
}
