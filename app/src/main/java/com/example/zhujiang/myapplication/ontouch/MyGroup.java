package com.example.zhujiang.myapplication.ontouch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * @author zhuj 2020/5/25 10:56 AM.
 */
public class MyGroup extends LinearLayout {

    public MyGroup(Context context) {
        super(context);
    }

    public MyGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("test","Linear dispatch " + ev.getAction() );
        boolean ret = super.dispatchTouchEvent(ev);
        Log.i("test","Linear dispatch  return " + ret);
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("test","Linear ontouch " + ev.getAction() );
        boolean ret = super.onTouchEvent(ev);
        Log.i("test","Linear ontouch return " + ret );
        return ret;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("test","Linear onIntercept " + ev.getAction() );
        boolean ret = super.onInterceptTouchEvent(ev);
        Log.i("test","Linear onIntercept return " + ret );
        return ret;
    }
}
