package com.vzaar;

import java.math.BigInteger;

import com.google.gson.Gson;

public class Video {

    private String status;
    private String duration;
    private String play_count;
    private String url;
    private String thumbnail;
    private Integer height;
    private Integer width;
    private User user;
    private String version;
    private Integer status_id;
    private BigInteger id;
    private String created_at;
    private String title;
    private String description;
    private String framegrabUrl;

    public String status() {
        return status;
    }

    public void status(String status) {
        this.status = status;
    }

    public String duration() {
        return duration;
    }

    public void duration(String duration) {
        this.duration = duration;
    }

    public String play_count() {
        return play_count;
    }

    public void play_count(String play_count) {
        this.play_count = play_count;
    }

    public String url() {
        return url;
    }

    public void url(String url) {
        this.url = url;
    }

    public String thumbnail() {
        return thumbnail;
    }

    public void thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer height() {
        return height;
    }

    public void height(Integer height) {
        this.height = height;
    }

    public Integer width() {
        return width;
    }

    public void width(Integer width) {
        this.width = width;
    }

    public User user() {
        return user;
    }

    public void user(User user) {
        this.user = user;
    }

    public String version() {
        return version;
    }

    public void version(String version) {
        this.version = version;
    }

    public Integer status_id() {
        return status_id;
    }

    public void status_id(Integer status_id) {
        this.status_id = status_id;
    }

    public BigInteger id() {
        return id;
    }

    public void id(BigInteger id) {
        this.id = id;
    }

    public String created_at() {
        return created_at;
    }

    public void created_at(String created_at) {
        this.created_at = created_at;
    }

    public String title() {
        return title;
    }

    public void title(String title) {
        this.title = title;
    }

    public String description() {
        return description;
    }

    public void description(String description) {
        this.description = description;
    }

    public String framegrabUrl() {
        return framegrabUrl;
    }

    public void framegrabUrl(String framegrabUrl) {
        this.framegrabUrl = framegrabUrl;
    }

    public Video() {
    }

    public Video(String version, BigInteger id, String title,
                 String description, String created_at, String url,
                 String thumbnail, String play_count, String author_name, String author_url,
                 Integer author_account, BigInteger video_count, String duration,
                 String status, Integer width, Integer height) {
        super();
        this.version = version;
        this.id = id;
        this.title = title;
        this.description = description;
        this.created_at = created_at;
        this.url = url;
        this.thumbnail = thumbnail;
        this.play_count = play_count;
        this.duration = duration;
        this.status = status;
        this.width = width;
        this.height = height;
        this.user = new User();
        this.framegrabUrl = "http://vzaar.com/videos/" + id + ".frame"; //Vzaar.URL_LIVE can be used here
        user.authorName(author_name);
        user.authorUrl(author_url);
        user.authorName(author_name);
        user.authorAccount(author_account);
        user.videoCount(video_count);
    }

    public static Video fromJson(String data) {
        Video video = null;
        Gson gson = new Gson();
        video = gson.fromJson(data, Video.class);
        video.framegrabUrl("http://vzaar.com/videos/" + video.id() + ".frame"); //Vzaar.URL_LIVE can be used here
        return video;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Video [");
        if (status != null) {
            builder.append("status=");
            builder.append(status);
            builder.append(", ");
        }
        if (duration != null) {
            builder.append("duration=");
            builder.append(duration);
            builder.append(", ");
        }
        if (play_count != null) {
            builder.append("play_count=");
            builder.append(play_count);
            builder.append(", ");
        }
        if (url != null) {
            builder.append("url=");
            builder.append(url);
            builder.append(", ");
        }
        if (thumbnail != null) {
            builder.append("thumbnail=");
            builder.append(thumbnail);
            builder.append(", ");
        }
        if (height != null) {
            builder.append("height=");
            builder.append(height);
            builder.append(", ");
        }
        if (width != null) {
            builder.append("width=");
            builder.append(width);
            builder.append(", ");
        }
        if (user != null) {
            builder.append("user=");
            builder.append(user);
            builder.append(", ");
        }
        if (version != null) {
            builder.append("version=");
            builder.append(version);
            builder.append(", ");
        }
        if (status_id != null) {
            builder.append("status_id=");
            builder.append(status_id);
            builder.append(", ");
        }
        if (id != null) {
            builder.append("id=");
            builder.append(id);
            builder.append(", ");
        }
        if (created_at != null) {
            builder.append("created_at=");
            builder.append(created_at);
            builder.append(", ");
        }
        if (title != null) {
            builder.append("title=");
            builder.append(title);
            builder.append(", ");
        }
        if (description != null) {
            builder.append("description=");
            builder.append(description);
            builder.append(", ");
        }
        if (framegrabUrl != null) {
            builder.append("framegrabUrl=");
            builder.append(framegrabUrl);
        }
        builder.append("]");
        return builder.toString();
    }


}
