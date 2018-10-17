package com.example.zhujiang.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zhuj 2017/9/21 上午9:16.
 */
public class GuideView extends View {

  private View targetView;

  public GuideView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }


  public void setTargetView(View view) {

  }

  public void setDrawableRes(@DrawableRes int drawableRes) {

  }

  @Override protected void onDraw(Canvas canvas) {


    super.onDraw(canvas);
  }
}
