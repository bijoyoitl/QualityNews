package com.optimalbd.qualitynews.NewsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ripon on 4/4/2017.
 */

public class CustomFields {

    @SerializedName("news_source")
    @Expose
    private List<String> newsSource = null;

    public List<String> getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(List<String> newsSource) {
        this.newsSource = newsSource;
    }

}
