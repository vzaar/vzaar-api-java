package com.vzaar;

import com.vzaar.client.RestClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;

public class CustomUploader {
    private final RestClient client;

    public CustomUploader(RestClient client) {
        this.client = client;
    }

    public UploadRequest signature(UploadType type, CreateSignatureRequest request) {
        return new UploadRequest()
                .withType(type)
                .withCreateSignatureRequest(request)
                .withUploadSignature(client.resource(UploadSignature.class).action(type.name()).create(request));
    }


    public void upload(UploadRequest request, File file) throws IOException {
        if (request.getType() == UploadType.single) {
            client.s3(new FileInputStream(file), request, 0);
        } else {
            for (int i = 0; i < request.getUploadSignature().getParts(); ++i) {
                uploadPart(request, file, i);
            }
        }
    }

    public void uploadPart(UploadRequest request, File file, int part) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        long pos = part * request.getUploadSignature().getPartSizeInBytes();
        randomAccessFile.seek(pos);
        client.s3(
                Channels.newInputStream(randomAccessFile.getChannel()),
                request,
                part);
    }

    public Video createVideo(CreateVideoRequest request) {
        return client.resource(Video.class).create(request);
    }
}
