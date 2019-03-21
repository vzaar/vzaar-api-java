package com.vzaar;

import com.vzaar.client.ResourcePath;

import java.time.ZonedDateTime;

@ResourcePath(path = "encoding_presets")
public class EncodingPreset implements Identifiable  {
    private int id;
    private String name;
    private String description;
    private String outputFormat;
    private int bitrateKbps;
    private int maxBitrateKbps;
    private int longDimension;
    private String videoCodec;
    private String profile;
    private String frameRateUpperThreshold;
    private int audioBitrateKbps;
    private int audioChannels;
    private int audioSampleRate;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String code;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public int getBitrateKbps() {
        return bitrateKbps;
    }

    public int getMaxBitrateKbps() {
        return maxBitrateKbps;
    }

    public int getLongDimension() {
        return longDimension;
    }

    public String getVideoCodec() {
        return videoCodec;
    }

    public String getProfile() {
        return profile;
    }

    public String getFrameRateUpperThreshold() {
        return frameRateUpperThreshold;
    }

    public int getAudioBitrateKbps() {
        return audioBitrateKbps;
    }

    public int getAudioChannels() {
        return audioChannels;
    }

    public int getAudioSampleRate() {
        return audioSampleRate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getCode() {
        return code;
    }
}
