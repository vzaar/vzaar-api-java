package com.vzaar;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;

public class VideoDetails {

    private User user;
    private String description;
    private boolean borderless;
    private String duration;
    private String type;
    private String html;
    private String provider_url;
    private String video_status_description;
    private String thumbnail_url;
    private Integer thumbnail_width;
    private String author_name;
    private String play_count;
    private Integer thumbnail_height;
    private String video_url;
    private String author_url;
    private Integer framegrab_width;
    private Integer framegrab_height;
    private String framegrab_url;
    private String version;
    private Integer height;
    private Integer width;
    private Integer video_status_id;
    private Integer author_account;
    private String provider_name;
    private String title;

    public String duration() {
        return duration;
    }

    public void duration(String duration) {
        this.duration = duration;
    }

    public String description() {
        return description;
    }

    public void description(String description) {
        this.description = description;
    }

    public String type() {
        return type;
    }

    public void type(String type) {
        this.type = type;
    }

    public String html() {
        return html;
    }

    public void html(String html) {
        this.html = html;
    }

    public String provider_url() {
        return provider_url;
    }

    public void provider_url(String provider_url) {
        this.provider_url = provider_url;
    }

    public String video_status_description() {
        return video_status_description;
    }

    public void video_status_description(String video_status_description) {
        this.video_status_description = video_status_description;
    }

    public String thumbnail_url() {
        return thumbnail_url;
    }

    public void thumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public Integer thumbnail_width() {
        return thumbnail_width;
    }

    public void thumbnail_width(Integer thumbnail_width) {
        this.thumbnail_width = thumbnail_width;
    }

    public String author_name() {
        return author_name;
    }

    public void author_name(String author_name) {
        this.author_name = author_name;
    }

    public String play_count() {
        return play_count;
    }

    public void play_count(String play_count) {
        this.play_count = play_count;
    }

    public Integer thumbnail_height() {
        return thumbnail_height;
    }

    public void thumbnail_height(Integer thumbnail_height) {
        this.thumbnail_height = thumbnail_height;
    }

    public String video_url() {
        return video_url;
    }

    public void video_url(String video_url) {
        this.video_url = video_url;
    }

    public String author_url() {
        return author_url;
    }

    public void author_url(String author_url) {
        this.author_url = author_url;
    }

    public Integer framegrab_width() {
        return framegrab_width;
    }

    public void framegrab_width(Integer framegrab_width) {
        this.framegrab_width = framegrab_width;
    }

    public Integer framegrab_height() {
        return framegrab_height;
    }

    public void framegrab_height(Integer framegrab_height) {
        this.framegrab_height = framegrab_height;
    }

    public String framegrab_url() {
        return framegrab_url;
    }

    public void framegrab_url(String framegrab_url) {
        this.framegrab_url = framegrab_url;
    }

    public String version() {
        return version;
    }

    public void version(String version) {
        this.version = version;
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

    public Integer video_status_id() {
        return video_status_id;
    }

    public void video_status_id(Integer video_status_id) {
        this.video_status_id = video_status_id;
    }

    public Integer author_account() {
        return author_account;
    }

    public void author_account(Integer author_account) {
        this.author_account = author_account;
    }

    public String provider_name() {
        return provider_name;
    }

    public void provider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String title() {
        return title;
    }

    public void title(String title) {
        this.title = title;
    }

    public boolean borderless() {
        return borderless;
    }

    public void borderless(boolean borderless) {
        this.borderless = borderless;
    }

    public VideoDetails() {
    }

    public VideoDetails(String type, String version, String title, String description, String authorName, String authorUrl,
                        Integer authorAccount, String providerName, String providerUrl, String thumbnailUrl, Integer thumbnailWidth,
                        Integer thumbnailHeight, String framegrabUrl, Integer framegrabWidth, Integer framegrabHeight, String html,
                        Integer height, Integer width, boolean borderless, String duration, Integer videoStatus) {
        this.type = type;
        this.version = version;
        this.title = title;
        this.description = description;
        this.user = new User();
        this.user.authorName(authorName);
        this.user.authorUrl(authorUrl);
        this.user.authorAccount(authorAccount);

        this.author_name = authorName;
        this.author_url = authorUrl;
        this.author_account = authorAccount;

        this.provider_name = providerName;
        this.provider_url = providerUrl;

        this.thumbnail_url = thumbnailUrl;
        this.thumbnail_width = thumbnailWidth;
        this.thumbnail_height = thumbnailHeight;

        this.framegrab_width = framegrabWidth;
        this.framegrab_height = framegrabHeight;
        this.framegrab_url = framegrabUrl;

        this.html = html;
        this.height = height;
        this.width = width;

        this.borderless = borderless;
        this.duration = duration;
        this.video_status_id = videoStatus;

    }

    public static VideoDetails fromJson(String data) throws VzaarException {
        VideoDetails vd = new VideoDetails();
        Object parsed = null;
        if (data.length() > 0)
            parsed = JSONValue.parse(data);
        if (parsed != null) {
            JSONObject map = (JSONObject) parsed;

            if (map.containsKey("error")) {
                JSONObject errorMap = (JSONObject) map.get("error");
                if (errorMap.containsKey("progress")) {
                    vd.type = "video";
                    vd.video_status_id = VideoStatus.PROCESSING;
                    vd.video_status_description = VideoStatusDescriptions.PROCESSING;
                }
            } else {
                if (map.containsKey("vzaar_api")) {
                    JSONObject vzaarApiMap = (JSONObject) map.get("vzaar_api");
                    if (vzaarApiMap.containsKey("error")) {
                        JSONObject errorMap = (JSONObject) vzaarApiMap.get("error");
                        throw new VzaarException((String) errorMap.get("type"));
                    } else {
                        vd.type = (String) vzaarApiMap.get("type");
                        vd.video_status_id = (Integer) vzaarApiMap.get("video_status_id");
                        vd.video_status_description = (String) vzaarApiMap.get("state");
                    }
                } else {
                    Gson gson = new Gson();
                    vd = gson.fromJson(data, VideoDetails.class);
                }
            }
        }

        return vd;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("VideoDetails [");
        if (version != null)
            builder.append("version=").append(version).append(", ");
        if (type != null)
            builder.append("type=").append(type).append(", ");
        if (title != null)
            builder.append("title=").append(title);
        if (description != null)
            builder.append("description=").append(description).append(", ");
        builder.append("borderless=").append(borderless).append(", ");
        if (duration != null)
            builder.append("duration=").append(duration).append(", ");
        if (html != null)
            builder.append("html=").append(html).append(", ");
        if (provider_name != null)
            builder.append("provider_name=").append(provider_name).append(", ");
        if (provider_url != null)
            builder.append("provider_url=").append(provider_url).append(", ");
        if (video_url != null)
            builder.append("video_url=").append(video_url).append(", ");
        if (height != null)
            builder.append("height=").append(height).append(", ");
        if (width != null)
            builder.append("width=").append(width).append(", ");
        if (play_count != null)
            builder.append("play_count=").append(play_count).append(", ");
        if (video_status_id != null)
            builder.append("video_status_id=").append(video_status_id)
                    .append(", ");
        if (video_status_description != null)
            builder.append("video_status_description=")
                    .append(video_status_description).append(", ");
        if (thumbnail_url != null)
            builder.append("thumbnail_url=").append(thumbnail_url).append(", ");
        if (thumbnail_width != null)
            builder.append("thumbnail_width=").append(thumbnail_width)
                    .append(", ");
        if (thumbnail_height != null)
            builder.append("thumbnail_height=").append(thumbnail_height)
                    .append(", ");
        if (author_name != null)
            builder.append("author_name=").append(author_name).append(", ");
        if (author_url != null)
            builder.append("author_url=").append(author_url).append(", ");
        if (author_account != null)
            builder.append("author_account=").append(author_account)
                    .append(", ");
        if (framegrab_url != null)
            builder.append("framegrab_url=").append(framegrab_url).append(", ");
        if (framegrab_width != null)
            builder.append("framegrab_width=").append(framegrab_width)
                    .append(", ");
        if (framegrab_height != null)
            builder.append("framegrab_height=").append(framegrab_height)
                    .append(", ");
        if (user != null)
            builder.append("user=").append(user).append(", ");

        builder.append("]");
        return builder.toString();
    }


}
