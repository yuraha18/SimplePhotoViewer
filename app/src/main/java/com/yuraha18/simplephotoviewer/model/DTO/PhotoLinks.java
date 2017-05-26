package com.yuraha18.simplephotoviewer.model.DTO;

/**
 * Created by yuraha18 on 5/23/2017.
 * class has 2 fields: link on full and small photo. Small uses in list, ful when user open full photo clicked on list
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
