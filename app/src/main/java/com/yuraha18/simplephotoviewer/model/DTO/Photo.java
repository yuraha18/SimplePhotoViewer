package com.yuraha18.simplephotoviewer.model.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 5/23/2017.
 */

public class Photo {
    private final String id;
    private final int likes;
    private final User user;
    private final PhotoLinks urls;

    public Photo(String id, int likes, User user, PhotoLinks urls) {
        this.id = id;
        this.likes = likes;
        this.user = user;
        this.urls = urls;
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

    public int getLikes() {
        return likes;
    }

    public User getUser() {
        return user;
    }

    public PhotoLinks getUrls() {
        return urls;
    }
}

