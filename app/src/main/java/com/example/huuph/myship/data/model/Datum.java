package com.example.huuph.myship.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;

    @SerializedName("postid")
    @Expose
    private String postid;

    public Datum() {
    }

    public Datum(String message, String updatedTime, String postid) {
        this.message = message;
        this.updatedTime = updatedTime;
        this.postid = postid;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
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
