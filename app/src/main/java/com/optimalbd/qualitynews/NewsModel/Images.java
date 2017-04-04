package com.optimalbd.qualitynews.NewsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ripon on 3/30/2017.
 */

public class Images {
    @SerializedName("full")
    @Expose
    private Full full;
    @SerializedName("thumbnail")
    @Expose
    private Thumbnail thumbnail;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    @SerializedName("medium_large")
    @Expose
    private MediumLarge mediumLarge;
    @SerializedName("large")
    @Expose
    private Large large;

    public Full getFull() {
        return full;
    }

    public void setFull(Full full) {
        this.full = full;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public MediumLarge getMediumLarge() {
        return mediumLarge;
    }

    public void setMediumLarge(MediumLarge mediumLarge) {
        this.mediumLarge = mediumLarge;
    }

    public Large getLarge() {
        return large;
    }

    public void setLarge(Large large) {
        this.large = large;
    }
}
