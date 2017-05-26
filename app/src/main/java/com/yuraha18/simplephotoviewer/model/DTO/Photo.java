package com.yuraha18.simplephotoviewer.model.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yuraha18 on 5/23/2017.
 */

public class Photo {
    private  String id;
    private  int likes;
    private  User user;
    private  PhotoLinks urls;

    @SerializedName("liked_by_user")
    private boolean likedByUser;

    public Photo(String id, int likes, User user, PhotoLinks urls, boolean likedByUser) {
        this.id = id;
        this.likes = likes;
        this.user = user;
        this.urls = urls;
        this.likedByUser = likedByUser;
    }


    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", likes=" + likes +
                ", user=" + user +
                ", urls=" + urls +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PhotoLinks getUrls() {
        return urls;
    }

    public void setUrls(PhotoLinks urls) {
        this.urls = urls;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }
}

