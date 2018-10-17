package com.example.zhujiang.myapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

public class MyPathView extends View implements View.OnClickListener {
  private Paint mPaint;
  private Paint mPaintPoint;
  private int mWidth;
  private int mHeight;
  private int mCenter;
  private Path mPath;

  private ValueAnimator mValueAnimatior;

  private float offset;

  public MyPathView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mPaint = new Paint();
    mPaint.setColor(Color.rgb(160, 160, 160));
    mPaint.setStyle(Paint.Style.FILL);
    mPaint.setStrokeWidth(5);
    mPaint.setTextSize(50);
    mPath = new Path();

    mPaintPoint = new Paint();
    mPaintPoint.setColor(Color.RED);
    //mPaintPoint.setStrokeWidth(10);
    mPaintPoint.setStyle(Paint.Style.FILL_AND_STROKE);
  }

  public MyPathView(Context context) {
    super(context);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
    mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec) / 3;

    mCenter = mWidth / 2;

    setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
            getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec) / 3);
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    setOnClickListener(this);
    super.onSizeChanged(w, h, oldw, oldh);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    int bHeight = 100;
    double dou = Math.sin(Math.toRadians(offset));
    double cos = Math.cos(Math.toRadians(offset));
    int wid = mWidth / 4;
    //mPaint.setAlpha(30);
    float x1, y1, x2, y2, x3;

    mPaint.setARGB(75, 203, 229, 245);

    //第一条线 跟主线类似
    //mPath.moveTo(0, bHeight -20 );//贝塞尔曲线开始点的坐标
    ////mPath.quadTo(mCenter, 300, mWidth *3/4, 350);
    //mPath.cubicTo(mWidth/4 -50 , bHeight -100,  mWidth*3/4  , bHeight+80  , mWidth, bHeight -20);//前两个为控制点坐标，后两个是结束点坐标
    //mPath.lineTo(mWidth, mHeight);
    //mPath.lineTo(0, mHeight);
    //canvas.drawPath(mPath, mPaint);

    //动画版， 动画跟主的 正好错过波峰
    mPath.moveTo(0, bHeight - (int) (50 * dou));
    x1 = wid + (float) (wid * dou);
    y1 = bHeight + (float) (dou * 50);
    x2 = 3 * wid + (float) (wid * dou);
    y2 = bHeight - (float) (dou * 50);

    x3 = mWidth + (float) (wid * dou);
    if (x3 < mWidth) {
      x3 += wid;
    }
    Log.d("test",
            "offset" + offset + " x1: " + x1 + "," + y1 + "   " + x2 + " ," + y2 + "  x3:" + x3);
    mPath.cubicTo(x1, y1, x2, y2, x3, bHeight);
    mPath.lineTo(mWidth, mHeight);
    mPath.lineTo(0, mHeight);
    canvas.drawPath(mPath, mPaint);

    //下降 然后上升
    mPath = new Path();
    bHeight += 30 * cos;
    mPath.moveTo(0, bHeight);//贝塞尔曲线开始点的坐标
    //mPath.quadTo(mCenter, 300, mWidth *3/4, 350);
    mPath.cubicTo(mWidth / 4, bHeight + 50, mWidth * 3 / 4, bHeight - 100, mWidth,
            bHeight - 10);//前两个为控制点坐标，后两个是结束点坐标
    mPath.lineTo(mWidth, mHeight);
    mPath.lineTo(0, mHeight);
    canvas.drawPath(mPath, mPaint);
    //动画版
    //wid = mWidth /2;
    //bHeight = 70;
    //mPath.moveTo(0, bHeight - (int) (80 * dou));
    //x1 = wid + (float) (wid *dou);
    //y1 = bHeight +(float) (dou *80);
    //x2 =  3*wid + (float) (wid * dou);
    //y2 = bHeight -(float) (dou *80);
    //
    //x3 = mWidth + (float) (wid * dou);
    //if (x3 <mWidth) {
    //  x3 += wid;
    //}
    //Log.d("test", "offset" + offset + " x1: " +  x1 + ","+ y1 + "   " + x2 +" ," + y2  + "  x3:" + x3);
    //mPath.cubicTo(x1, y1, x2 , y2, x3, bHeight);
    //mPath.lineTo(mWidth, mHeight);
    //mPath.lineTo(0, mHeight);
    //canvas.drawPath(mPath, mPaint);

    //上升 然后下降
    mPath = new Path();
    bHeight = (int) (100 + 40 * dou);
    mPath.moveTo(0, bHeight);//贝塞尔曲线开始点的坐标
    mPath.quadTo(mCenter, bHeight - 120, mWidth, bHeight + 90);
    //mPath.cubicTo(mWidth/2-100 , bHeight -150, mWidth, bHeight +60,  mWidth+300, bHeight +150);//前两个为控制点坐标，后两个是结束点坐标
    mPath.lineTo(mWidth, mHeight);
    mPath.lineTo(0, mHeight);
    canvas.drawPath(mPath, mPaint);

    bHeight = 150;
    mPath = new Path();
    mPath.moveTo(0, bHeight);//贝塞尔曲线开始点的坐标
    //mPath.quadTo(mCenter, 300, mWidth *3/4, 350);

    //原版
    //mPath.cubicTo(mWidth/4, bHeight -50,  mWidth*3/4-100 , bHeight+80  , mWidth, bHeight+30);//前两个为控制点坐标，后两个是结束点坐标
    //mPath.lineTo(mWidth, mHeight);
    //mPath.lineTo(0, mHeight);
    //mPaint.setColor(Color.rgb(243, 243, 243));
    //canvas.drawPath(mPath, mPaint);

    //动画版
    mPath.moveTo(0, bHeight - (int) (50 * dou));//贝塞尔曲线开始点的坐标
    wid = mWidth / 4;
    x1 = wid + (float) (wid * dou);
    y1 = bHeight - (float) (dou * 50);
    x2 = 3 * wid + (float) (wid * dou);
    y2 = bHeight + (float) (dou * 50);

    x3 = mWidth + (float) (wid * dou);
    if (x3 < mWidth) {
      x3 += wid;
    }
    Log.d("test",
            "offset" + offset + " x1: " + x1 + "," + y1 + "   " + x2 + " ," + y2 + "  x3:" + x3);
    mPath.cubicTo(x1, y1, x2, y2, x3, bHeight);
    mPath.lineTo(mWidth, mHeight);
    mPath.lineTo(0, mHeight);
    mPaint.setColor(Color.rgb(243, 243, 243));
    canvas.drawPath(mPath, mPaint);

    canvas.drawCircle(mWidth / 2, bHeight - 20 + (float) (cos * 50), 70, mPaintPoint);





    //-----

    //
    //
    ////mPaint.setAlpha(30);
    //mPath.moveTo(0, bHeight +30 );//贝塞尔曲线开始点的坐标
    ////mPath.quadTo(mCenter, 300, mWidth *3/4, 350);
    //mPath.cubicTo(mWidth/4 + 100, bHeight -100,  mWidth*3/4 +100 , bHeight+50  , mWidth, bHeight);//前两个为控制点坐标，后两个是结束点坐标
    //mPath.lineTo(mWidth, bHeight +50);
    //mPath.lineTo(0, bHeight +50);
    //canvas.drawPath(mPath, mPaint);

    //canvas.drawPoint(100, 100, mPaintPoint);
    //canvas.drawPoint(100, 400, mPaintPoint);
    //canvas.drawPoint(300, 300, mPaintPoint);
  }

  @Override public void onClick(View v) {
    mValueAnimatior = ValueAnimator.ofInt(0, 360);
    mValueAnimatior.setDuration(5000);
    mValueAnimatior.setInterpolator(new LinearInterpolator());
    mValueAnimatior.setRepeatCount(ValueAnimator.INFINITE);
    mValueAnimatior.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        offset = (int) animation.getAnimatedValue();
        //Log.w("test", "  " + offset);
        invalidate();
      }
    });
    mValueAnimatior.start();
  }

  public void onStop() {
    if (mValueAnimatior != null) {
      mValueAnimatior.cancel();
    }
  }
}