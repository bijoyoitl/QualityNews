package com.optimalbd.qualitynews.NewsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ripon on 3/30/2017.
 */

public class Attachment {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("images")
    @Expose
    private Images images;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }
}
