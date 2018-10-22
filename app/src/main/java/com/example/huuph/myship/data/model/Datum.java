package com.example.huuph.myship.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("updated_time")
    @Expose
    private String updatedTime;

    public Datum(String name, String message, String updatedTime) {
        this.id = name;
        this.message = message;
        this.updatedTime = updatedTime;
    }

    public String getName() {
        return id;
    }

    public void setName(String name) {
        this.id = name;
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
