package com.example.zhujiang.myapplication.game;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author zhuj 2018/9/12 下午3:04.
 */
public class ItemView extends AppCompatImageView {

    private boolean isBox;
    private int myId;

    private int srcX;
    private int srcY;

    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMyId(int id) {
        myId = id;
    }

    public int getMyId() {
        return myId;
    }

    public void setSrcX(int srcX) {
        this.srcX = srcX;
    }

    public void setSrcY(int srcY) {
        this.srcY = srcY;
    }

    public int getSrcX() {
        return srcX;
    }

    public int getSrcY() {
        return srcY;
    }
}
