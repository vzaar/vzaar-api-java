package com.vzaar;

public class Rendition implements Identifiable  {
    private String code;
    private Integer width;
    private Integer height;
    private Integer bitrate;
    private String framerate;
    private String status;
    private Long sizeInBytes;
    private String url;

    public int getId() {
        return 0;
    }

    public String getCode() {
        return code;
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

    public String getUrl() {
        return url;
    }
}
