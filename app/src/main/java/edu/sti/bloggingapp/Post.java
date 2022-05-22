package edu.sti.bloggingapp;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Post implements Serializable {
    private String userID;
    @Exclude
    private String key;
    private String title;
    private String content;

    public Post(){}

    public Post(String userID, String title, String content) {
        this.userID = userID;
        this.title = title;
        this.content = content;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
