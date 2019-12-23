package com.example.drawingapp;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Paint;
import android.content.Context;

import java.util.ArrayList;

public class PaintView extends View{

    private Paint mPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;

    public PaintView(Context context, AttributeSet attrs){
        super(context,attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }

    public void init(DisplayMetrics metrics){
       int height = (int)(metrics.heightPixels*0.8);
       int width = metrics.widthPixels;

       mBitmap= Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
       mCanvas = new Canvas(mBitmap);
    }

    private ArrayList<MainActivity.FingerPath> paths = new ArrayList<>();
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    @Override
    protected void onDraw(Canvas canvas){
        canvas.save();
        mCanvas.drawColor(Color.WHITE);

        for(MainActivity.FingerPath fp : paths){
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mPaint.setMaskFilter(null);
            mCanvas.drawPath(fp.path,mPaint);
        }

        canvas.drawBitmap(mBitmap, 0, 0 ,mBitmapPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x=event.getX();
        float y=event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }

        return true;
    }

    private void touchUp(){
        mPath.lineTo(mX,mY);
    }

    private static final float TOUCH_TOLERANCE =4;

    private void touchMove(float x,float y){
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);

        if(dx>=TOUCH_TOLERANCE||dy>=TOUCH_TOLERANCE){
            mPath.quadTo(mX,mY,(x+mX)/2,(y+mY)/2);
            mX= x;
            mY= y;
        }
    }
    private Path mPath;
    private float mX;
    private float mY;

    public int brushColor = Color.BLACK;
    public int brushSize = 20;

    private void touchStart(float x,float y){
        mPath = new Path();
        MainActivity.FingerPath fp = new MainActivity.FingerPath(brushColor,brushSize,mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x,y);

        mX = x;
        mY = y;

    }

    public void clear(){
        paths.clear();
        invalidate();
    }


}
