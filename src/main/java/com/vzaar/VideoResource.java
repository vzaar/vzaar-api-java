package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.client.RestClient;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VideoResource {
    private final RestClient client;

    VideoResource(RestClient client) {
        this.client = client;
    }

    /**
     * Start a video search / listing request.
     * @return the request
     */
    public VideoPageRequest list() {
        return new VideoPageRequest(resource());
    }

    /**
     * Get a video by id.
     * @param videoId the id of the video
     * @return the video
     */
    public Video get(int videoId) {
        return id(videoId).lookup();
    }

    /**
     * Update a video's attributes
     * @param videoId the id of the to update
     * @return the request
     */
    public VideoUpdateRequest update(int videoId) {
        return new VideoUpdateRequest(id(videoId));
    }

    /**
     * Delete a video
     * @param videoId the id of a video
     */
    public void delete(int videoId) {
        id(videoId).delete();
    }

    /**
     * Set the still image for the video from a video frame
     * @param videoId the id of a video
     * @param time seconds into video to generate poster frame from
     */
    public Video updateImageFrame(int videoId, double time) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("time", time);
        return id(videoId).action("image").update(payload);
    }

    /**
     * Set the still image for the video from a video frame
     * @param videoId the id of a video
     * @param file seconds into video to generate poster frame from
     */
    public Video uploadImageFrame(int videoId, File file, boolean optimize) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("optimize", optimize);
        payload.put("image", file);
        return id(videoId).action("image").createWithUpload(payload);
    }

    /**
     * Upload a video located on the local file system
     * @return the request
     */
    public VideoUploadRequest uploadWithFile() {
        return new VideoUploadRequest(client);
    }

    /**
     * Upload a video from using a URL
     * @return the request
     */
    public VideoLinkUploadRequest uploadWithLink() {
        return new VideoLinkUploadRequest(client.resource(Video.class));
    }

    /**
     * Get the customer uploader for finer-grained local uploads
     * @return the custom upload service
     */
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
