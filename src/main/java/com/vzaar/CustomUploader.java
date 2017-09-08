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
     *
     * @return an signature request to be used to upload the file to s3
     */
    public SignatureRequest signature() {
        return new SignatureRequest(client);
    }

    /**
     * Upload the file. This method will determine from the upload request if it
     * should be uploaded as a single or multipart upload. For multipart uploads
     * it will upload all the parts.
     *
     * @param signature the upload request signature
     * @param file      the file to upload
     * @throws IOException exception if error transmitting file
     */
    public void upload(Signature signature, File file) throws IOException {
        if (signature.getType() == UploadType.single) {
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
                client.s3(in, signature, 0);
            }
        } else {
            for (int i = 0; i < signature.getParts(); ++i) {
                uploadPart(signature, file, i);
            }
        }
    }

    /**
     * Upload a single part for multipart uploads.
     *
     * @param signature the upload request signature
     * @param file      the file to upload
     * @param part      the part to upload which should be between 0 and less than request.getUploadSignature().getParts()
     * @throws IOException exception if error transmitting file
     */
    public void uploadPart(Signature signature, File file, int part) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            long pos = part * signature.getPartSizeInBytes();
            randomAccessFile.seek(pos);
            client.s3(
                    Channels.newInputStream(randomAccessFile.getChannel()),
                    signature,
                    part);
        }
    }

    /**
     * Create video request which should be called after the file has been successfully to
     * s3 using upload or uploadPart
     *
     * @return the video create request
     */
    public VideoCreateRequest createVideo() {
        return new VideoCreateRequest(client.resource(Video.class));
    }
}
