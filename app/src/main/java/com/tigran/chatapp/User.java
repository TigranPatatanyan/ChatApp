package com.tigran.chatapp;

public class User {
    private String uid;
    private String name;
    private String imageURL;

    public User() {
    }

    public User(String uid, String name, String imageURL) {
        this.uid = uid;
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }
}
