package com.example.zhujiang.myapplication.ontouch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author zhuj 2019-12-10 23:32.
 */
public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.w("test", " myView ontouch " + event.getAction());
        boolean ret = super.onTouchEvent(event);
        Log.w("test", " myView ontouch return " + ret);
        //if (event.getAction() == MotionEvent.ACTION_DOWN) {
        //    return true;
        //}
        return ret;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w("test","myView dispatch " + ev.getAction() );
        boolean ret = super.dispatchTouchEvent(ev);
        Log.w("test","myView dispatch return" + ret);
        return ret;
    }

}
