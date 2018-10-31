package com.example.zhujiang.myapplication.game.wheel;

import lombok.Data;

/**
 * @author zhuj 2018/10/29 下午4:13.
 */
public class ViewBean {

    private int srcX, srcY;
    private int id;

    private boolean isMove = true;
    /**
     * 是否在拖动过程中
     */
    private boolean isDragering;

    public ViewBean(int id, int srcX, int srcY) {
        this.id = id;
        this.srcX = srcX;
        this.srcY = srcY;
    }

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public int getSrcX() {
        return srcX;
    }

    public int getSrcY() {
        return srcY;
    }

    public void setSrc(int left, int top) {
        this.srcX = left;
        this.srcY = top;
    }

    public boolean isDragering() {
        return isDragering;
    }

    public void setDragering(boolean dragering) {
        isDragering = dragering;
    }
}

