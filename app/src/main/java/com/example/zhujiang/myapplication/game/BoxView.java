package com.example.zhujiang.myapplication.game;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author zhuj 2018/9/12 下午3:04.
 */
public class BoxView extends AppCompatImageView {

    private int BOX_SIZE = 9;

    private int[] boxArray = new int[BOX_SIZE];

    public BoxView(Context context) {
        super(context);
    }

    public BoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //表示没有位置占位
        for (int i=0;i<BOX_SIZE; i++) {
            boxArray[i] = -1;
        }
    }

    /**
     * 是否为空盒子，true 可以放入东西
     */
    private boolean mEmptyBox;

    /**
     * 传入id
     * @param itemId
     * @return
     */
    public int getEmptyPosition(int itemId) {
        for (int i=0; i< BOX_SIZE; i++) {
            if (boxArray[i] == itemId) {
                return i;
            }
        }

        int middle = BOX_SIZE /2;
        int position;
        for (int i= 0; i<= middle; i++) {
            position = middle +i;
            if (checkPosition(position)) {
                return position;
            }
            position = middle -i;
            if (checkPosition(position)) {
                return position;
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
        StringBuffer sb = new StringBuffer();
        for (Integer value : boxArray) {
            sb.append(value + ",");
        }
        return sb.toString();
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

    private boolean checkPosition(int position) {
        if (position <0 || position >=BOX_SIZE) {
            return false;
        }
        return (boxArray[position] == -1);
    }

    public int getLayoutX(int postion) {
        int width  = getMeasuredWidth() / BOX_SIZE;
        float  x = getX();
        //从中间开始，左右20个单位
       return (int) (getX() +  postion * width);
    }
}
