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

    @SerializedName("name")
    @Expose
    private String name;

    public Datum() {
    }


    public Datum(String message, String updatedTime, String postid, String name) {
        this.message = message;
        this.updatedTime = updatedTime;
        this.postid = postid;
        this.name = name;
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

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
