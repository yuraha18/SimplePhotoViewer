package com.yuraha18.simplephotoviewer.model.DTO;

/**
 * Created by User on 5/23/2017.
 */

public class User {
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
