package com.vzaar;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VideoDetails {
    public String version;
    public String type;
    public String title;
    public String description;
    public boolean borderless;
    public BigDecimal duration;
    public int height;
    public int width;
    public String url;
    public String poster;
    public String html;
    public long playCount;
    public long totalSize;

    public VideoDetailsAuthor author;
    public VideoDetailsProvider provider;
    public VideoDetailsThumbnail thumbnail;
    public VideoDetailsFramegrab framegrab;
    public VideoDetailsVideoStatus videoStatus;
	public List<VideoRendition> renditions = new ArrayList<>();

    public VideoDetails() {
        author = new VideoDetailsAuthor();
        provider = new VideoDetailsProvider();
        thumbnail = new VideoDetailsThumbnail();
        videoStatus = new VideoDetailsVideoStatus();
    }

    public static VideoDetails fromJson(String json) {
        VideoDetails videoDetails = new VideoDetails();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            videoDetails = objectMapper.readValue(json, VideoDetails.class);
            //poster = "http://view.vzaar.com/" + videoId + "/image",

            videoDetails.author = objectMapper.readValue(json, VideoDetailsAuthor.class);
            videoDetails.provider = objectMapper.readValue(json, VideoDetailsProvider.class);
            videoDetails.thumbnail = objectMapper.readValue(json, VideoDetailsThumbnail.class);
            videoDetails.framegrab = objectMapper.readValue(json, VideoDetailsFramegrab.class);
            videoDetails.videoStatus = objectMapper.readValue(json, VideoDetailsVideoStatus.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoDetails;
    }

    @Override
    public String toString() {
        return "VideoDetails{" +
                "version='" + version + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", borderless=" + borderless +
                ", duration=" + duration +
                ", height=" + height +
                ", width=" + width +
                ", url='" + url + '\'' +
                ", poster='" + poster + '\'' +
                ", html='" + html + '\'' +
                ", playCount=" + playCount +
                ", totalSize=" + totalSize +
                ", author=" + author +
                ", provider=" + provider +
                ", thumbnail=" + thumbnail +
                ", framegrab=" + framegrab +
                ", videoStatus=" + videoStatus +
				", renditions=" + renditions +
                '}';
    }
}
