package com.example.zhujiang.myapplication.game.wheel;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author zhuj 2018/10/27 下午2:21.
 */
public class Ferris extends ViewGroup {

    private int COUNT;

    /**
     * r 是0 或360， t方向是 270， 1点钟方向，就是300度
     */
    private int angle = 300;

    private int radius;

    public Ferris(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        radius = Math.min(getWidth(), getHeight()) / 2  - 50;

        int count = getChildCount();

        int width = getWidth();
        int height = getHeight();

        int delayAngle = 360 / count;

        View view;
        int x, y;
        float an;


        int viewwidth = getChildAt(0).getWidth();
        int viewHeight = getChildAt(0).getMeasuredHeight();

        for (int i=0; i<count; i++) {
            view = getChildAt(i);
            an = (float) ((angle + i * delayAngle) * Math.PI / 180);
            x  = width/ 2 + (int) (radius * Math.cos( an)) - view.getMeasuredWidth()/2;
            y  = height /2 + (int) (radius * Math.sin( an)) - view.getMeasuredHeight()/ 2;


            view.layout(x, y, x + view.getMeasuredWidth(), y + view.getMeasuredHeight());

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int mWidthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);//初始化所有子View的宽高

        if (mWidthMeasureMode == MeasureSpec.AT_MOST) {//Wrap_content的情况
            //测量子View的宽  怎么测量子View的宽
            //由于这里只有一个控件，暂时从这个一个控件开始学习
            View childView = getChildAt(0);//获取到这个控件
            width = childView.getMeasuredWidth();
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }

        int mHeightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        if (mHeightMeasureMode == MeasureSpec.AT_MOST) {
            View childView = getChildAt(0);
            height = childView.getMeasuredHeight();

        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        int size = Math.min(width, height);
        setMeasuredDimension(size, size);
    }

    public void setRotateAngle(int rotateAngle) {
        angle = 300 - rotateAngle;
        requestLayout();
    }
}
