package com.example.capstone;

import java.util.Date;

public class CommentInfo {
    String uid;
    String user_name;
    String comment;
    private Date createdAt;

    public CommentInfo(String uid, String user_name, String comment,  Date createdAt) {
        this.uid = uid;
        this.user_name = user_name;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

