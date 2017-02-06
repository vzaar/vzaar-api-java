package com.vzaar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
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
    private Boolean notifyByEmail;
    private Boolean notifyByPingback;

    /**
     * Prepopulate the store request with the details from an existing recipe
     *
     * @param recipe the recipe whose details are to be copied
     * @return this instance
     */
    public IngestRecipeStoreRequest withIngestRecipe(IngestRecipe recipe) {
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
        return this;
    }

    /**
     * Set the name of the recipe. Mandatory for creating a recipe.
     *
     * @param name the name of the recipe
     * @return this instance
     */
    public IngestRecipeStoreRequest withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set the encoding preset id values to associate with this recipe.
     * Mandatory for creating a recipe
     *
     * @param encodingPresetIds the encoding preset id values
     * @return this instance
     */
    public IngestRecipeStoreRequest withEncodingPresetIds(Set<Integer> encodingPresetIds) {
        this.encodingPresetIds = encodingPresetIds;
        return this;
    }

    /**
     * Set the description
     *
     * @param description the description
     * @return this instance
     */
    public IngestRecipeStoreRequest withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Flag this recipe as my default recipe. You can only have one default
     * recipe. Optional.
     *
     * @param isDefault true to set this as the default recipe
     * @return this instance
     **/
    public IngestRecipeStoreRequest withDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    /**
     * Perform multipass encoding (this will slow down your video
     * processing but yield better quality results). Optional.
     *
     * @param multipass use multipass encoding
     * @return this instance
     */
    public IngestRecipeStoreRequest withMultipass(Boolean multipass) {
        this.multipass = multipass;
        return this;
    }

    /**
     * Generate an animated gif thumbnail. Optional
     *
     * @param generateAnimatedThumb generate an animated gif thumbnail.
     * @return this instance
     */
    public IngestRecipeStoreRequest withGenerateAnimatedThumb(Boolean generateAnimatedThumb) {
        this.generateAnimatedThumb = generateAnimatedThumb;
        return this;
    }

    /**
     * Generate sprites (used for scrubbing thumbnails in the vzaar player). Optional.
     *
     * @param generateSprite generate sprites
     * @return this instance
     */
    public IngestRecipeStoreRequest withGenerateSprite(Boolean generateSprite) {
        this.generateSprite = generateSprite;
        return this;
    }

    /**
     * Burn your watermark into the transcoded video (requires additional watermark
     * setup). Optional.
     *
     * @param useWatermark burn your watermark into the transcoded video.
     * @return this instance
     */
    public IngestRecipeStoreRequest withUseWatermark(Boolean useWatermark) {
        this.useWatermark = useWatermark;
        return this;
    }

    /**
     * Send your video to your associated YouTube account (requires YouTube syndication
     * access and additional setup). Optional
     *
     * @param sendToYoutube send your video to your associated YouTube account
     * @return this instance
     */
    public IngestRecipeStoreRequest withSendToYoutube(Boolean sendToYoutube) {
        this.sendToYoutube = sendToYoutube;
        return this;
    }

    /**
     * Send email notification after video processing. Optional
     *
     * @param notifyByEmail send email notification after video processing
     * @return this instance
     */
    public IngestRecipeStoreRequest withNotifyByEmail(Boolean notifyByEmail) {
        this.notifyByEmail = notifyByEmail;
        return this;
    }

    /**
     * Send http pingback after video processing. Optional
     *
     * @param notifyByPingback send http pingback after video processing
     * @return this instance
     */
    public IngestRecipeStoreRequest withNotifyByPingback(Boolean notifyByPingback) {
        this.notifyByPingback = notifyByPingback;
        return this;
    }
}
