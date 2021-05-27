package com.example.capstone;

import java.util.ArrayList;
import java.util.Date;

public class ReviewInfo {
    private String uid;
    private String name;
    private String title;
    private String contents;
    private ArrayList<String> post;
    private String address_gu;
    private int likes;
    private Date createdAt;

    public ReviewInfo(String uid, String name, String title, String contents, ArrayList<String> post, String address_gu, int likes, Date createdAt) {
        this.uid = uid;
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.post = post;
        this.address_gu = address_gu;
        this.likes = likes;
        this.createdAt = createdAt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getAddress_gu() {
        return address_gu;
    }

    public void setAddress_gu(String address_gu) {
        this.address_gu = address_gu;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<String> getPost() {
        return post;
    }

    public void setPost(ArrayList<String> post) {
        this.post = post;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

