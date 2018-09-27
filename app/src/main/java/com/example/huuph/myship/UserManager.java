package com.example.huuph.myship;

public class UserManager {
    public String name;
    public String email;

    public UserManager() {
    }

    public UserManager(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
