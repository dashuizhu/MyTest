package com.example.zhujiang.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * @author zhuj 2019/1/23 下午6:25.
 */
public class MyImageView extends android.support.v7.widget.AppCompatImageView {
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setScaleX(0.6f);
                setScaleY(0.6f);
                setAlpha(0.3f);
                setColorFilter(Color.parseColor("#77893215")); // 设置滤镜效果
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setScaleX(1f);
                setScaleY(1f);
                setAlpha(1f);
                clearColorFilter();
                break;
        }
        return super.onTouchEvent(event);
    }
}
