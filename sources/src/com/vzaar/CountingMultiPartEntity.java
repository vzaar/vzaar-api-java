package com.vzaar;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;


public class CountingMultiPartEntity extends MultipartEntity {
    private ProgressListener listener;
    private CountingOutputStream outputStream;
    private OutputStream lastOutputStream;

    public CountingMultiPartEntity(ProgressListener listener) {
        super(HttpMultipartMode.BROWSER_COMPATIBLE);        
        this.listener = listener;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        // If we have yet to create the CountingOutputStream, or the
        // OutputStream being passed in is different from the OutputStream used
        // to create the current CountingOutputStream
        if ((lastOutputStream == null) || (lastOutputStream != out)) {
            lastOutputStream = out;
            outputStream = new CountingOutputStream(out);
        }

        super.writeTo(outputStream);
    }

    private class CountingOutputStream extends FilterOutputStream {

        private long transferred = 0;
        private OutputStream wrappedOutputStream_;

        public CountingOutputStream(final OutputStream out) {
            super(out);
            wrappedOutputStream_ = out;
        }

        public void write(byte[] b, int off, int len) throws IOException {
            wrappedOutputStream_.write(b, off, len);
            transferred += len;
            if (null != listener)
                listener.update(transferred);
        }

        public void write(int b) throws IOException {
            super.write(b);
        }
    }

}
