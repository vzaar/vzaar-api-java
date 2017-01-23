package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IngestRecipe {
    private int id;
    private String name;
    private String recipeType;
    private String description;
    private int accountId;
    private int userId;
    @JsonProperty("default")
    private boolean isDefault;
    private boolean multipass;
    private String frameGrabTime;
    private boolean generateAnimatedThumb;
    private boolean generateSprite;
    private boolean useWatermark;
    private boolean sendToYoutube;
    private boolean notifyByEmail;
    private boolean notifyByPingback;
    private List<EncodingPreset> encodingPresets;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public String getDescription() {
        return description;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isMultipass() {
        return multipass;
    }

    public String getFrameGrabTime() {
        return frameGrabTime;
    }

    public boolean isGenerateAnimatedThumb() {
        return generateAnimatedThumb;
    }

    public boolean isGenerateSprite() {
        return generateSprite;
    }

    public boolean isUseWatermark() {
        return useWatermark;
    }

    public boolean isSendToYoutube() {
        return sendToYoutube;
    }

    public boolean isNotifyByEmail() {
        return notifyByEmail;
    }

    public boolean isNotifyByPingback() {
        return notifyByPingback;
    }

    public List<EncodingPreset> getEncodingPresets() {
        return encodingPresets;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Set<Integer> getEncodingPresetsIds() {
        return encodingPresets.stream()
                .map(EncodingPreset::getId)
                .collect(Collectors.toSet());
    }
}