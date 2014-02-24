package com.vzaar;

import java.io.InputStream;

import org.apache.http.entity.mime.content.InputStreamBody;

public class FileStreamingBody extends InputStreamBody {
    private final long contentLength;

    public FileStreamingBody(InputStream in, String filename, final long contentLength) {
        super(in, filename);
        this.contentLength = contentLength;
    }

    @Override
    public long getContentLength() {
        return contentLength;
    }


}
