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

    public VideoUploadRequest withFile(File file) {
        this.file = file;
        return this;
    }

    public VideoUploadRequest withUploader(String uploader) {
        this.uploader = uploader;
        return this;
    }

    public VideoUploadRequest withTitle(String title) {
        this.title = title;
        return this;
    }

    public VideoUploadRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    public VideoUploadRequest withIngestRecipeId(Integer ingestRecipeId) {
        this.ingestRecipeId = ingestRecipeId;
        return this;
    }

    public Video result() throws IOException {
        CustomUploader service = new CustomUploader(client);
        RestClientConfiguration configuration = client.getConfiguration();
        UploadType type = file.length() > configuration.getUseMultipartWhenFileSizeOver()
                ? UploadType.multipart
                : UploadType.single;

        UploadRequest uploadRequest = service.signature(type, new CreateSignatureRequest()
                .withFile(file)
                .withUploader(uploader)
                .withDesiredPartSizeInMb(type == UploadType.multipart ? configuration.getDefaultDesiredChunkSizeInMb() : null));

        service.upload(uploadRequest, file);

        return service.createVideo(new VideoCreateRequest()
                .withGuid(uploadRequest.getUploadSignature().getGuid())
                .withTitle(title)
                .withDescription(description)
                .withIngestRecipeId(ingestRecipeId));
    }
}
