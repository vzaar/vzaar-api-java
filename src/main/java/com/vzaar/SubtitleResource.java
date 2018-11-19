package com.vzaar;

import com.vzaar.client.Resource;
import com.vzaar.client.RestClient;

import java.util.List;

public class SubtitleResource {
    private final RestClient client;

    SubtitleResource(RestClient client) {
        this.client = client;
    }

    /**
     * Get the list of subtitles for a video.
     * @param videoId the id of a video
     * @return the list of subtitles
     */
    public List<Subtitle> list(int videoId) {
        return id(videoId).action("subtitles").list();
    }

    /**
     * Add subtitles to a video request
     * @return the request
     */
    public SubtitleCreateRequest create(int videoId) {
        return new SubtitleCreateRequest(id(videoId).action("subtitles"));
    }

    /**
     * Update subtitles to a video request
     * @param videoId the id of a video
     * @param subtitleId the id of the subtitle
     * @return the request
     */
    public SubtitleUpdateRequest update(int videoId, int subtitleId) {
        return new SubtitleUpdateRequest(id(videoId).action("subtitles/" + subtitleId));
    }

    /**
     * Delete a video subtitle
     * @param videoId the id of a video
     * @param subtitleId the id of the subtitle
     */
    public void delete(int videoId, int subtitleId) {
        id(videoId).action("subtitles/" + subtitleId).delete();
    }


    private Resource<Subtitle> resource() {
        return client.resource(Subtitle.class);
    }

    private Resource<Subtitle> id(int id) {
        return resource().id(id);
    }
}
