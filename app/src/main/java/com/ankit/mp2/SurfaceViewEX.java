package com.ankit.mp2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewEX extends SurfaceView implements SurfaceHolder.Callback {

    private CanvasThread canvasThread;
    private Paint paint = new Paint();

    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    void PanZoomWithTouch(MotionEvent event){
        if(event.getY() > 100)
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = PAN;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == PAN) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                }
                else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    static final int NONE = 0;
    static final int PAN = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    public SurfaceViewEX(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        canvasThread = new CanvasThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        canvasThread.setRunning(true);
        canvasThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        canvasThread.setRunning(false);
        while(retry) {
            try {
                canvasThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.d("Interrupt", "surfaceDestroyed: error");
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PanZoomWithTouch(event);
        invalidate();
        return true;
    }

    Bitmap test = BitmapFactory.decodeResource(getResources(),R.drawable.test1);
    boolean firstDraw = true;

    @Override
    protected void onDraw(Canvas canvas) {
        if(canvas!= null) {

            if(firstDraw) {
                firstDraw = false;
                RectF imageRect = new RectF(0, 0, test.getWidth(), test.getHeight());
                int imageHeight = canvas.getWidth()*test.getHeight()/test.getWidth();
                RectF viewRect = new RectF(0, 0, canvas.getWidth(), imageHeight);
                matrix.setRectToRect(imageRect, viewRect, Matrix.ScaleToFit.CENTER);
                matrix.postTranslate(0, 300);
                Log.d("canvas height", "onDraw: " + canvas.getHeight() + " " + imageHeight);
            }

            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(test, matrix, paint);
        }
    }
}
