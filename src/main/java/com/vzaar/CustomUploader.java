package com.vzaar;

import com.vzaar.client.RestClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;

/**
 * Custom uploader to allow finer grained control of the video
 * upload process
 */
public class CustomUploader {
    private final RestClient client;

    CustomUploader(RestClient client) {
        this.client = client;
    }

    /**
     * Create an upload signature to allow the video file to be
     * uploaded
     * @param type the upload type for the signature
     * @param request the request details for the upload signature
     * @return an upload request to be used to upload the file to s3
     */
    public UploadRequest signature(UploadType type, CreateSignatureRequest request) {
        return new UploadRequest()
                .withType(type)
                .withCreateSignatureRequest(request)
                .withUploadSignature(client.resource(UploadSignature.class).action(type.name()).create(request));
    }

    /**
     * Upload the file. This method will determine from the upload request if it
     * should be uploaded as a single or multipart upload. For multipart uploads
     * it will upload all the parts.
     * @param request the upload request returned from the signature call
     * @param file the file to upload
     * @throws IOException exception if error transmitting file
     */
    public void upload(UploadRequest request, File file) throws IOException {
        if (request.getType() == UploadType.single) {
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                client.s3(in, request, 0);
            }
        } else {
            for (int i = 0; i < request.getUploadSignature().getParts(); ++i) {
                uploadPart(request, file, i);
            }
        }
    }

    /**
     * Upload a single part for multipart uploads.
     * @param request the upload request returned from the signature call
     * @param file the file to upload
     * @param part the part to upload which should be between 0 and less than request.getUploadSignature().getParts()
     * @throws IOException exception if error transmitting file
     */
    public void uploadPart(UploadRequest request, File file, int part) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            long pos = part * request.getUploadSignature().getPartSizeInBytes();
            randomAccessFile.seek(pos);
            client.s3(
                    Channels.newInputStream(randomAccessFile.getChannel()),
                    request,
                    part);
        }
    }

    /**
     * Create video request which should be called after the file has been successfully to
     * s3 using upload or uploadPart
     * @param request the create video request
     * @return the video that was created
     */
    public Video createVideo(VideoCreateRequest request) {
        return client.resource(Video.class).create(request);
    }
}
