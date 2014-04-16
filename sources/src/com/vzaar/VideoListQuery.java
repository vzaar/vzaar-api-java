package com.vzaar;

/**
 * Created by umashankar on 12/04/2014.
 */
public class VideoListQuery {
    public String username = "";
    public boolean includePrivateVideos = false;
    public int count = 20;
    public int page = 1;
    public VideoListSorting sort = VideoListSorting.DESCENDING;
    public String[] labels = new String[] { };
    public String status = "";
    public String title = "";
}
