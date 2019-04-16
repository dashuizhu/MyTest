package com.example.zhujiang.myapplication.game.textDraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.utils.DensityUtil;

/**
 * @author zhuj 2018/11/5 下午6:29.
 */
public class MyTextView extends View {

    Paint mPaint;
    Paint mDrawPaint;


    Path mPath;
    private String mText;

    private int mX, mY;

    private Canvas mCanvas;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initViews();
    }


    private void  initViews() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mCanvas = canvas;
        if (mPaint == null) {
            mPaint = new Paint();

            mDrawPaint = new Paint();
            mDrawPaint.setTextSize(DensityUtil.sp2px(getContext(), 80));
            mDrawPaint.setStrokeWidth(15);
            mDrawPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            mDrawPaint.setStyle(Paint.Style.FILL);

        }

        mPaint.setTextSize(DensityUtil.sp2px(getContext(), 120));
        mPaint.setStrokeWidth(15);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawText(mText, getWidth()/2 , getHeight(), mPaint);

        //mPaint.setTextSize(DensityUtil.sp2px(getContext(), 80));
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawText(mText, getWidth()/2 , getHeight(), mPaint);

    }

    public void setText(String text) {
        mText = text;
        invalidate();
        if (mCanvas != null) {
            mCanvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://手指开始按压屏幕，这个动作包含了初始化位置
                //onTouchDown(x , y);
                //invalidate();//刷新画布，重新运行onDraw（）方法
                break;
            case MotionEvent.ACTION_MOVE://手指按压屏幕时，位置的改变触发，这个方法在ACTION_DOWN和ACTION_UP之间。
                mCanvas.drawOval(x -40, y-40, x + 40, y +40, mPaint);
                invalidate();
                break;
            case MotionEvent.ACTION_UP://手指离开屏幕，不再按压屏幕
                //mPath.lineTo(mX, mY);//从最后一个指定的xy点绘制一条线，如果没有用moveTo方法，那么起始点表示（0，0）点。
                //// commit the path to our offscreen
                //mCanvas.drawPath(mPath, mPaint);//手指离开屏幕后，绘制创建的“所有”路径。
                //// kill this so we don't double draw
                //mPath.reset();
                //invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    //private void onTouchDown(float x , float y){
    //    mPath.reset();//将上次的路径保存起来，并重置新的路径。
    //    mPath.moveTo(x, y);//设置新的路径“轮廓”的开始
    //    mX = x;
    //    mY = y;
    //}
    //private void onTouchMove(float x , float y){
    //    float dx = Math.abs(x - mX);
    //    float dy = Math.abs(y - mY);
    //    if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
    //        mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
    //        mX = x;
    //        mY = y;
    //    }
    //}
    //private void onTouchUp(float x , float y){
    //    mPath.lineTo(mX, mY);//从最后一个指定的xy点绘制一条线，如果没有用moveTo方法，那么起始点表示（0，0）点。
    //    // commit the path to our offscreen
    //    mCanvas.drawPath(mPath, mPaint);//手指离开屏幕后，绘制创建的“所有”路径。
    //    // kill this so we don't double draw
    //    mPath.reset();
    //}
}
