package com.example.zhujiang.myapplication.game;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author zhuj 2018/9/12 下午3:04.
 */
public class BoxView extends AppCompatImageView {

    private int[] boxArray = new int[]{-1,-1,-1, -1};

    public BoxView(Context context) {
        super(context);
    }

    public BoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 是否为空盒子，true 可以放入东西
     */
    private boolean mEmptyBox;

    public int getEmptyPosition(int item) {
        for (int i=0; i< boxArray.length; i++) {
            if (boxArray[i] == item) {
                return i;
            }
        }
        for (int i= 0; i< boxArray.length; i++) {
            if (boxArray[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    public int getBoxPostion(int itemId) {
        for (int i=0; i< boxArray.length; i++) {
            if (boxArray[i] == itemId) {
                return i;
            }
        }
        return -1;
    }

    public int[] getBoxArray() {
        return boxArray;
    }

    public String toBoxString() {
        return boxArray[0] +","+boxArray[1]+"," + boxArray[2];
    }

    public void setEmptyBox(boolean emptyBox) {
        mEmptyBox = emptyBox;
    }

    public boolean isEmptyBox() {
        return mEmptyBox;
    }

    public void init() {
        for(int i=0; i<boxArray.length; i++) {
            boxArray[i] = -1;
        }
    }

    public int getSize() {
        int size = 0;
        for (int i=0; i< boxArray.length; i++) {
            if (boxArray[i] != -1) {
                size++;
            }
        }
        return size;
    }
}
