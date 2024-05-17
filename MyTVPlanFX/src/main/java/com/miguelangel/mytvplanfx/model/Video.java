package com.miguelangel.mytvplanfx.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Video{
    @SerializedName("_id")
    private String id;
    public String title;
    public String type;
    public String platform;
    public String category;
    public int rating;
    public Date limitDate = null;

    public Video(String title, String type, String platform, String category, int rating) {
        this.title = title;
        this.type = type;
        this.platform = platform;
        this.category = category;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }


    public String getPlatform() {
        return platform;
    }


    public String getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }
    public Date getLimitDate() {
        return limitDate;
    }

    @Override
    public String toString() {
        return title;
    }
}
