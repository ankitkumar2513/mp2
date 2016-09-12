package com.ankit.mp2.Filters;

import android.graphics.Bitmap;

public abstract class Filter {
    int thumbId;
    String title;

    public Filter(int id, String name) {
        thumbId = id;
        title = name;
    }

    public abstract Bitmap perform(Bitmap src);

    public int getThumbId() {
        return thumbId;
    }

    public String getTitle() {
        return title;
    }
}
