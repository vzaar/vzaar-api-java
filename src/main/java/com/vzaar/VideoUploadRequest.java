package com.vzaar;

import com.vzaar.client.RestClient;
import com.vzaar.client.RestClientConfiguration;

import java.io.File;
import java.io.IOException;

public class VideoUploadRequest {
    private final transient RestClient client;

    private File file;
    private String uploader;
    private String title;
    private String description;
    private Integer ingestRecipeId;

    VideoUploadRequest(RestClient client) {
        this.client = client;
    }

    /**
     * The video file to upload. Required.
     * @param file the file
     * @return this instance
     */
    public VideoUploadRequest withFile(File file) {
        this.file = file;
        return this;
    }

    /**
     * Uploader description used for metadata, analytics and support. Required.
     * @param uploader the uploader
     * @return this instance
     */
    public VideoUploadRequest withUploader(String uploader) {
        this.uploader = uploader;
        return this;
    }

    /**
     * The video title. If not provided this will default to your source filename. Optional.
     * @param title the video title
     * @return this instance
     */
    public VideoUploadRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * The video description. Optional.
     * @param description the video description
     * @return this instance
     */
    public VideoUploadRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The ingest recipe id to use if you do not want to use the default. Optional.
     * @param ingestRecipeId the ingest recipe id
     * @return this instance
     */
    public VideoUploadRequest withIngestRecipeId(Integer ingestRecipeId) {
        this.ingestRecipeId = ingestRecipeId;
        return this;
    }

    /**
     * Upload and create the video from the request
     * @return the created video
     * @throws IOException if there was an issue reading the file
     */
    public Video result() throws IOException {
        CustomUploader service = new CustomUploader(client);
        RestClientConfiguration configuration = client.getConfiguration();
        UploadType type = file.length() > configuration.getUseMultipartWhenFileSizeOver()
                ? UploadType.multipart
                : UploadType.single;

        Signature signature = service.signature()
                .withUploadType(type)
                .withFile(file)
                .withUploader(uploader)
                .withDesiredPartSizeInMb(configuration.getDefaultDesiredChunkSizeInMb())
                .result();

        service.upload(signature, file);

        return service.createVideo()
                .withGuid(signature.getGuid())
                .withTitle(title)
                .withDescription(description)
                .withIngestRecipeId(ingestRecipeId)
                .result();
    }
}
