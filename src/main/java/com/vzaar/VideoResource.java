package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.client.RestClient;

public class VideoResource {
    private final RestClient client;

    VideoResource(RestClient client) {
        this.client = client;
    }

    public VideoPageRequest list() {
        return new VideoPageRequest(resource());
    }

    public Video get(int videoId) {
        return resource().lookup(videoId);
    }

    public VideoUpdateRequest update(int videoId) {
        return new VideoUpdateRequest(id(videoId));
    }

    public void delete(int categoryId) {
        id(categoryId).delete();
    }

    private Resource<Video> resource() {
        return client.resource(Video.class);
    }

    private Resource<Video> id(int id) {
        return resource().id(id);
    }
}
