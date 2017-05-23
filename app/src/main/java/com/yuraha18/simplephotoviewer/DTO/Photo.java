package com.yuraha18.simplephotoviewer.DTO;

/**
 * Created by User on 5/23/2017.
 */

public class Photo {
    private final String id;
    private final String created_at;
    private final String updated_at;
    private final int width;
    private final int height;
    private final int likes;
    private final User user;
    private final PhotoLinks urls;

    public Photo(String id, String created_at, String updated_at, int width, int height, int likes, User user, PhotoLinks urls) {
        this.id = id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.width = width;
        this.height = height;
        this.likes = likes;
        this.user = user;
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", likes=" + likes +
                ", user=" + user +
                ", urls=" + urls +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

