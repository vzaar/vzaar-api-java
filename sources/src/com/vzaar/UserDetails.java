package com.vzaar;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDetails {

    public String version;
    public String authorName;
    public int authorId;
    public String authorUrl;
    public int authorAccount;
    public String authorAccountTitle;
    public Date createdAt;
    public long videoCount;
    public long playCount;
    public long bandwidthThisMonth;
    public List<UserBandwidthDetails> bandwidth = new ArrayList<>();
    public long videoTotalSize;
    public long maxFileSize;

    public static UserDetails fromJson(String json) {
        UserDetails userDetails = new UserDetails();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        try {
            userDetails = objectMapper.reader(UserDetails.class).readValue(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userDetails;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "version='" + version + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorId=" + authorId +
                ", authorUrl='" + authorUrl + '\'' +
                ", authorAccount=" + authorAccount +
                ", authorAccountTitle='" + authorAccountTitle + '\'' +
                ", createdAt=" + createdAt +
                ", videoCount=" + videoCount +
                ", playCount=" + playCount +
                ", bandwidthThisMonth=" + bandwidthThisMonth +
                ", bandwidth=" + bandwidth +
                ", videoTotalSize=" + videoTotalSize +
                ", maxFileSize=" + maxFileSize +
                '}';
    }
}
