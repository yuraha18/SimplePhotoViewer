package com.yuraha18.simplephotoviewer.DTO;

/**
 * Created by User on 5/23/2017.
 */

public class PhotoLinks {
    private final String full;
    private final String small;

    public PhotoLinks(String full, String small) {
        this.full = full;
        this.small = small;
    }

    public String getFull() {
        return full;
    }

    public String getSmall() {
        return small;
    }

    @Override
    public String toString() {
        return "ProtoLinks{" +
                "full='" + full + '\'' +
                ", small='" + small + '\'' +
                '}';
    }
}
