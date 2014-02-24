package com.vzaar;

import java.math.BigInteger;

import com.google.gson.Gson;

public class User {
    private String author_account_title;
    private BigInteger video_count;
    private String author_name;
    private BigInteger play_count;
    private BigInteger author_id;
    private String author_url;
    private String version;
    private Integer author_account;
    private String created_at;

    public String authorAccountTitle() {
        return author_account_title;
    }

    public void authorAccountTitle(String authorAccountTitle) {
        this.author_account_title = authorAccountTitle;
    }

    public BigInteger videoCount() {
        return video_count;
    }

    public void videoCount(BigInteger videoCount) {
        this.video_count = videoCount;
    }

    public String authorName() {
        return author_name;
    }

    public void authorName(String authorName) {
        this.author_name = authorName;
    }

    public BigInteger playCount() {
        return play_count;
    }

    public void playCount(BigInteger playCount) {
        this.play_count = playCount;
    }

    public BigInteger authorId() {
        return author_id;
    }

    public void authorId(BigInteger authorId) {
        this.author_id = authorId;
    }

    public String authorUrl() {
        return author_url;
    }

    public void authorUrl(String authorUrl) {
        this.author_url = authorUrl;
    }

    public String version() {
        return version;
    }

    public void version(String version) {
        this.version = version;
    }

    public Integer authorAccount() {
        return author_account;
    }

    public void authorAccount(Integer authorAccount) {
        this.author_account = authorAccount;
    }

    public String createdAt() {
        return created_at;
    }

    public void createdAt(String createdAt) {
        this.created_at = createdAt;
    }

    public User() {

    }

    public User(String version, String authorName, BigInteger authorId,
                String authorUrl, Integer authorAccount, String createdAt,
                BigInteger videoCount, BigInteger playCount) {
        this.author_account_title = "";
        this.video_count = videoCount;
        this.author_name = authorName;
        this.play_count = playCount;
        this.author_id = authorId;
        this.author_url = authorUrl;
        this.version = version;
        this.author_account = authorAccount;
        this.created_at = createdAt;
    }

    public User(String version, String authorName, BigInteger authorId,
                String authorUrl, Integer authorAccount, String createdAt,
                BigInteger videoCount, BigInteger playCount,
                String authorAccountTitle) {
        this.author_account_title = authorAccountTitle;
        this.video_count = videoCount;
        this.author_name = authorName;
        this.play_count = playCount;
        this.author_id = authorId;
        this.author_url = authorUrl;
        this.version = version;
        this.author_account = authorAccount;
        this.created_at = createdAt;
    }

    public static User fromJson(String data) {
        User user = null;

        Gson gson = new Gson();
        user = gson.fromJson(data, User.class);

        return user;
    }

    @Override
    public String toString() {
        return "version=" + version + ", authorName=" + author_name
                + ", authorId=" + author_id + ", authorUrl=" + author_url
                + ", authorAccount=" + author_account + ", createdAt="
                + created_at + ", videoCount=" + video_count + ", playCount="
                + play_count + ", authorAccountTitle=" + author_account_title;
    }


}
