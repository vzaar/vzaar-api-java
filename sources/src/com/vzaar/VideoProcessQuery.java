package com.vzaar;

public class VideoProcessQuery {
    public String guid = "";
    public String title = "";
    public String description = "";
    public String[] labels = new String[] { };
    public VideoProfile profile = VideoProfile.ORIGINAL;
    public boolean transcode = false;
    public String replaceId = "";
    public int chunks = 0;
}
