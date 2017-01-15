package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class IngestRecipeStoreRequest {
    private String name;
    private Set<Integer> encodingPresetIds;
    private String description;
    @JsonProperty("default")
    private Boolean isDefault;
    private Boolean multipass;
    private Boolean generateAnimatedThumb;
    private Boolean generateSprite;
    private Boolean useWatermark;
    private Boolean sendToYoutube;

    public IngestRecipeStoreRequest withIngestRecipe(IngestRecipe recipe) {
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.encodingPresetIds = recipe.getEncodingPresetsIds();
        this.isDefault = recipe.isDefault();
        this.multipass = recipe.isMultipass();
        this.generateAnimatedThumb = recipe.isGenerateAnimatedThumb();
        this.generateSprite = recipe.isGenerateSprite();
        this.useWatermark = recipe.isUseWatermark();
        this.sendToYoutube = recipe.isSendToYoutube();
        return this;
    }

    public IngestRecipeStoreRequest withName(String name) {
        this.name = name;
        return this;
    }

    public IngestRecipeStoreRequest withEncodingPresetIds(Set<Integer> encodingPresetIds) {
        this.encodingPresetIds = encodingPresetIds;
        return this;
    }

    public IngestRecipeStoreRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    public IngestRecipeStoreRequest withDefault(Boolean aDefault) {
        isDefault = aDefault;
        return this;
    }

    public IngestRecipeStoreRequest withMultipass(Boolean multipass) {
        this.multipass = multipass;
        return this;
    }

    public IngestRecipeStoreRequest withGenerateAnimatedThumb(Boolean generateAnimatedThumb) {
        this.generateAnimatedThumb = generateAnimatedThumb;
        return this;
    }

    public IngestRecipeStoreRequest withGenerateSprite(Boolean generateSprite) {
        this.generateSprite = generateSprite;
        return this;
    }

    public IngestRecipeStoreRequest withUseWatermark(Boolean useWatermark) {
        this.useWatermark = useWatermark;
        return this;
    }

    public IngestRecipeStoreRequest withSendToYoutube(Boolean sendToYoutube) {
        this.sendToYoutube = sendToYoutube;
        return this;
    }
}
