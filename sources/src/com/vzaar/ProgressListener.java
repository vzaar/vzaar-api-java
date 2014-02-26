package com.vzaar;

public interface ProgressListener {
    /**
     * This method will be called when upload is in progress.
     *
     * @param pBytesRead Number of bytes read.
     */
    void update(long pBytesRead);
}
