package com.optimalbd.qualitynews.NewsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ripon on 3/30/2017.
 */

public class NewsMain {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("pages")
    @Expose
    private Integer pages;

    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
