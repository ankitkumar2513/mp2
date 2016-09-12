package com.ankit.mp2;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private SurfaceViewEX surfaceViewEX;
    private boolean run = false;

    public CanvasThread(SurfaceHolder surfaceHolder, SurfaceViewEX surfaceViewEX) {
        this.surfaceHolder = surfaceHolder;
        this.surfaceViewEX = surfaceViewEX;
    }

    public void setRunning(boolean value) {
        this.run = value;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas c;
        while (run) {
            c = null;
            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    surfaceViewEX.onDraw(c);
                }
            } finally {
                if(c!=null)
                    surfaceHolder.unlockCanvasAndPost(c);
            }
        }
    }
}
