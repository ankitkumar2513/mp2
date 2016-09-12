package com.ankit.mp2.Filters;

import android.graphics.Bitmap;

public class Grayscale extends Filter {

    public Grayscale(int id, String name) {
        super(id, name);
    }

    @Override
    public Bitmap perform(Bitmap src) {
        Bitmap result = src;
        return result;
    }
}
