package com.vzaar.client;

import org.apache.http.entity.mime.content.InputStreamBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class FileStreamingBody extends InputStreamBody {
    private static final int BUFFER_SIZE = 8192;
    private final long contentLength;
    private final byte[] buffer;
    private long left;

    FileStreamingBody(InputStream in, String filename, long contentLength) {
        this(in, filename, contentLength, BUFFER_SIZE);
    }

    FileStreamingBody(InputStream in, String filename, long contentLength, int bufferSize) {
        super(in, filename);
        this.contentLength = contentLength;
        this.buffer = new byte[bufferSize];
        this.left = contentLength;
    }

    @Override
    public long getContentLength() {
        return contentLength;
    }

    @Override
    public void writeTo(OutputStream out) throws java.io.IOException {
        try {
            int read = read();
            while (read != -1 && left > 0) {
                out.write(buffer, 0, read);
                left -= read;
                read = read();
            }
            out.flush();
        } finally {
            getInputStream().close();
        }
    }

    private int read() throws IOException {
        return getInputStream().read(buffer, 0, (int) Math.min(buffer.length, left));
    }
}