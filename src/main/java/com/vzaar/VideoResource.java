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
        return id(videoId).lookup();
    }

    public VideoUpdateRequest update(int videoId) {
        return new VideoUpdateRequest(id(videoId));
    }

    public void delete(int videoId) {
        id(videoId).delete();
    }

    public VideoUploadRequest uploadWithFile() {
        return new VideoUploadRequest(client);
    }

    public VideoLinkUploadRequest uploadWithLink() {
        return new VideoLinkUploadRequest(client.resource(Video.class));
    }

    public CustomUploader getCustomUploader() {
        return new CustomUploader(client);
    }

    private Resource<Video> resource() {
        return client.resource(Video.class);
    }

    private Resource<Video> id(int id) {
        return resource().id(id);
    }
}
