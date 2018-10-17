package com.example.zhujiang.myapplication.game.GameWalkActivity;

import java.util.List;

/**
 * @author zhuj 2018/9/12 下午8:36.
 */
public interface WalkGameListener {
    void onWalkResult(boolean isOver);
    void onWalkBack(String string);
    void onWalkRounter(List<PositionBean> list);

    void onDrawing(boolean isDrawing);

}
