package edu.sti.bloggingapp;

import java.io.Serializable;

public class AllPost implements Serializable {

    private String userID;
    private String fullName;
    private String title;
    private String content;

    public AllPost(String userID, String fullName, String title, String content) {
        this.userID = userID;
        this.fullName = fullName;
        this.title = title;
        this.content = content;
    }

    public AllPost(){}

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
}
