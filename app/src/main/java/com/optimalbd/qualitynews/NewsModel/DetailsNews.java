package com.optimalbd.qualitynews.NewsModel;

/**
 * Created by ripon on 3/30/2017.
 */

public class DetailsNews {

    private String id;
    private String title;
    private String content;
    private String img;
    private String time;

    public DetailsNews(String id, String title, String content, String img, String time) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.img = img;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImg() {
        return img;
    }

    public String getTime() {
        return time;
    }
}
