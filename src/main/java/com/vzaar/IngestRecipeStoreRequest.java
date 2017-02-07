package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class IngestRecipeStoreRequest<T> {
    private final transient Class<T> type;
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
    private Boolean notifyByEmail;
    private Boolean notifyByPingback;

    protected IngestRecipeStoreRequest(Class<T> type) {
        this.type = type;
    }

    /**
     * Prepopulate the store request with the details from an existing recipes
     *
     * @param recipe the recipes whose details are to be copied
     * @return this instance
     */
    public T withIngestRecipe(IngestRecipe recipe) {
        this.name = recipe.getName();
        this.description = recipe.getDescription();
        this.encodingPresetIds = new HashSet<>(recipe.getEncodingPresetsIds());
        this.isDefault = recipe.isDefault();
        this.multipass = recipe.isMultipass();
        this.generateAnimatedThumb = recipe.isGenerateAnimatedThumb();
        this.generateSprite = recipe.isGenerateSprite();
        this.useWatermark = recipe.isUseWatermark();
        this.sendToYoutube = recipe.isSendToYoutube();
        this.notifyByEmail = recipe.isNotifyByEmail();
        this.notifyByPingback = recipe.isNotifyByPingback();
        return type.cast(this);
    }

    /**
     * Set the name of the recipes. Mandatory for creating a recipes.
     *
     * @param name the name of the recipes
     * @return this instance
     */
    public T withName(String name) {
        this.name = name;
        return type.cast(this);
    }

    /**
     * Set the encoding preset id values to associate with this recipes.
     * Mandatory for creating a recipes
     *
     * @param encodingPresetIds the encoding preset id values
     * @return this instance
     */
    public T withEncodingPresetIds(Set<Integer> encodingPresetIds) {
        this.encodingPresetIds = encodingPresetIds;
        return type.cast(this);
    }

    /**
     * Set the description
     *
     * @param description the description
     * @return this instance
     */
    public T withDescription(String description) {
        this.description = description;
        return type.cast(this);
    }

    /**
     * Flag this recipes as my default recipes. You can only have one default
     * recipes. Optional.
     *
     * @param isDefault true to set this as the default recipes
     * @return this instance
     **/
    public T withDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        return type.cast(this);
    }

    /**
     * Perform multipass encoding (this will slow down your video
     * processing but yield better quality results). Optional.
     *
     * @param multipass use multipass encoding
     * @return this instance
     */
    public T withMultipass(Boolean multipass) {
        this.multipass = multipass;
        return type.cast(this);
    }

    /**
     * Generate an animated gif thumbnail. Optional
     *
     * @param generateAnimatedThumb generate an animated gif thumbnail.
     * @return this instance
     */
    public T withGenerateAnimatedThumb(Boolean generateAnimatedThumb) {
        this.generateAnimatedThumb = generateAnimatedThumb;
        return type.cast(this);
    }

    /**
     * Generate sprites (used for scrubbing thumbnails in the vzaar player). Optional.
     *
     * @param generateSprite generate sprites
     * @return this instance
     */
    public T withGenerateSprite(Boolean generateSprite) {
        this.generateSprite = generateSprite;
        return type.cast(this);
    }

    /**
     * Burn your watermark into the transcoded video (requires additional watermark
     * setup). Optional.
     *
     * @param useWatermark burn your watermark into the transcoded video.
     * @return this instance
     */
    public T withUseWatermark(Boolean useWatermark) {
        this.useWatermark = useWatermark;
        return type.cast(this);
    }

    /**
     * Send your video to your associated YouTube account (requires YouTube syndication
     * access and additional setup). Optional
     *
     * @param sendToYoutube send your video to your associated YouTube account
     * @return this instance
     */
    public T withSendToYoutube(Boolean sendToYoutube) {
        this.sendToYoutube = sendToYoutube;
        return type.cast(this);
    }

    /**
     * Send email notification after video processing. Optional
     *
     * @param notifyByEmail send email notification after video processing
     * @return this instance
     */
    public T withNotifyByEmail(Boolean notifyByEmail) {
        this.notifyByEmail = notifyByEmail;
        return type.cast(this);
    }

    /**
     * Send http pingback after video processing. Optional
     *
     * @param notifyByPingback send http pingback after video processing
     * @return this instance
     */
    public T withNotifyByPingback(Boolean notifyByPingback) {
        this.notifyByPingback = notifyByPingback;
        return type.cast(this);
    }
}
