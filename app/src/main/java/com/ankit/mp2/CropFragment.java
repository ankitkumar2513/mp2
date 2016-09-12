package com.ankit.mp2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CropFragment extends Fragment implements View.OnTouchListener {

    private int cropWidth, cropHeight;
    private Bitmap bitmap;
    private ImageView imageView;
    private Canvas canvas;
    private Paint paint;

    private float downX, downY;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crop_fragment, container, false);

        imageView = (ImageView) view.findViewById(R.id.iv_crop_fragment_image_view);

        if(bitmap!=null) {
            cropWidth = bitmap.getWidth();
            cropHeight = bitmap.getHeight();
            canvas = new Canvas(bitmap);
            paint = new Paint();
        } else {
            Log.e("ERROR", "onCreateView: bitmap not initialized");
        }

        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = motionEvent.getX();
                downY = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                paint.setColor(Color.RED);

                canvas.drawLine(0, 0, motionEvent.getX(), motionEvent.getY(), paint);
                canvas.drawLine(motionEvent.getX(), motionEvent.getY(), motionEvent.getX(), canvas.getHeight(), paint);
                imageView.invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
