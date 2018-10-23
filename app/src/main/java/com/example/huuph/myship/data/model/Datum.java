package com.example.huuph.myship.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("nameUserPost")
    @Expose
    private String nameUserPost;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;

    public Datum(String nameUserPost, String message, String updatedTime) {
        this.nameUserPost = nameUserPost;
        this.message = message;
        this.updatedTime = updatedTime;
    }

    public String getNameUserPost() {
        return nameUserPost;
    }

    public void setNameUserPost(String nameUserPost) {
        this.nameUserPost = nameUserPost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
