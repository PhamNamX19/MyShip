package com.example.huuph.myship.uis;

public class FakeDataNew {
    private String name;
    private String post;
    private String time;

    public FakeDataNew(String name, String post, String time) {
        this.name = name;
        this.post = post;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
