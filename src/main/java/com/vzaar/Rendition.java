package com.vzaar;

public class Rendition implements Identifiable  {
    private int id;
    private Integer width;
    private Integer height;
    private Integer bitrate;
    private String framerate;
    private String status;
    private Long sizeInBytes;

    public int getId() {
        return id;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public String getFramerate() {
        return framerate;
    }

    public String getStatus() {
        return status;
    }

    public Long getSizeInBytes() {
        return sizeInBytes;
    }
}
