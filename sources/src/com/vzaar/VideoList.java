package com.vzaar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;

public class VideoList {
    private List<Video> videoList;

    public VideoList() {
    }

    public VideoList(Video[] videos) {
        if (videos.length > 0)
            this.videoList = new ArrayList<Video>(Arrays.asList(videos));
        else
            this.videoList = new ArrayList<Video>();
    }

    public List<Video> videoList() {
        return videoList;
    }

    public void videoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public static VideoList fromJson(String data) throws VzaarException {
        VideoList videoList = null;
        JSONObject parsed = null;
        if (data.length() > 0) {
            Object obj = JSONValue.parse(data);
            if (obj.getClass().getSimpleName().equalsIgnoreCase("JSONObject")) {
                parsed = (JSONObject) obj;
                if (parsed.containsKey("error")) {
                    throw new VzaarException((String) parsed.get("error"));
                }
            } else {
                Gson gson = new Gson();
                videoList = new VideoList(gson.fromJson(data, Video[].class));
            }
        }

        return videoList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VideoList [");
        if (videoList != null) {
            builder.append("videoList=");
            builder.append(videoList);
        }
        builder.append("]");
        return builder.toString();
    }


}
