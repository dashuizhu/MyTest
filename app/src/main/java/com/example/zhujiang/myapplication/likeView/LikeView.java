package com.example.zhujiang.myapplication.likeView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import com.example.zhujiang.myapplication.R;

/**
 * @author zhuj 2017/11/28 上午11:27.
 */
public class LikeView extends View implements View.OnClickListener{

  private Context mContext;

  private boolean isCheck ;

  private Paint mPaint;

  private int number = 999;

  private int mWidth, mHeight;

  public LikeView(Context context) {
    super(context);
    mContext = context;
    setOnClickListener(this);
  }

  public LikeView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    setOnClickListener(this);
    if (mPaint == null) {
      mPaint = new Paint();
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
    mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec)/3;
    int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
    int specModeHeight = MeasureSpec.getMode(heightMeasureSpec);
    int height = 0;
    switch (specModeHeight) {
      case MeasureSpec.AT_MOST:

        Bitmap bitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.thumbs_on);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.thumbs_decoration);
        float textWidth = mPaint.measureText("1"+number);
        mWidth = (int) (bitmap2.getWidth() + textWidth);
        height = bitmap.getHeight() + bitmap2.getHeight();

        break;
      default:
      case MeasureSpec.EXACTLY:
      case MeasureSpec.UNSPECIFIED:
        height = MeasureSpec.getSize(heightMeasureSpec);
        break;
    }
    mHeight = height;
    setMeasuredDimension(mWidth, mHeight);
  }

  @Override protected void onDraw(Canvas canvas) {


    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.thumbs_decoration);

    float textHeight = mPaint.ascent()+ mPaint.descent();

    if (isCheck) {
      canvas.drawBitmap(bitmap, 0 ,0 , mPaint);

      Bitmap bitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.thumbs_on);
      canvas.drawBitmap(bitmap2, 0 , 0+bitmap.getHeight() , mPaint);

      canvas.drawText("" + number, 0 + bitmap2.getWidth(), bitmap.getHeight() + (bitmap2.getHeight() - textHeight)/2, mPaint);
    } else {



      Bitmap bitmap3 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.thumbs_off);
      canvas.drawBitmap(bitmap3, 0 ,0 +bitmap.getHeight() , mPaint);
      canvas.drawText("" + number, 0 + bitmap3.getWidth(), bitmap.getHeight() + (bitmap3.getHeight() - textHeight)/2, mPaint);
    }

    super.onDraw(canvas);
  }


  @Override public void onClick(View v) {
    Log.e("likeView", "click " + isCheck);
    isCheck = !isCheck;
    if (isCheck) {
      number ++;
    } else {
      number --;
    }
    invalidate();
    test();
  }

  private void test() {
    /*
                AnimationSet相当于一个动画的集合，true表示使用Animation的interpolator
                false则是使用自己的。
                Interpolator 被用来修饰动画效果，定义动画的变化率，可以使存在的动画效果
                accelerated(加速)，decelerated(减速),repeated(重复),bounced(弹跳)等。
             */

    AnimationSet animationSet = new AnimationSet(true);
            /*
                参数解释：
                    第一个参数：X轴水平缩放起始位置的大小（fromX）。1代表正常大小
                    第二个参数：X轴水平缩放完了之后（toX）的大小，0代表完全消失了
                    第三个参数：Y轴垂直缩放起始时的大小（fromY）
                    第四个参数：Y轴垂直缩放结束后的大小（toY）
                    第五个参数：pivotXType为动画在X轴相对于物件位置类型
                    第六个参数：pivotXValue为动画相对于物件的X坐标的开始位置
                    第七个参数：pivotXType为动画在Y轴相对于物件位置类型
                    第八个参数：pivotYValue为动画相对于物件的Y坐标的开始位置

                   （第五个参数，第六个参数），（第七个参数,第八个参数）是用来指定缩放的中心点
                    0.5f代表从中心缩放
             */
    ScaleAnimation scaleAnimation = new ScaleAnimation(1,1.2f,1,1.2f,
            Animation.RELATIVE_TO_SELF,0,Animation.RELATIVE_TO_SELF,0);
    //3秒完成动画
    scaleAnimation.setDuration(200);
    //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
    animationSet.addAnimation(scaleAnimation);

    this.startAnimation(animationSet);
  }
}
