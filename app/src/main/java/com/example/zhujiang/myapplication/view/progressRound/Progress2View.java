package com.example.zhujiang.myapplication.view.progressRound;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.example.zhujiang.myapplication.R;

/**
 * @author zhuj 2019-10-08 10:22.
 */
public class Progress2View extends View {

    public Progress2View(Context context) {
        super(context);
    }

    public Progress2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Progress2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int startAngle = -90;
    private int strokeWidth = 32;

    private int progress = 33;
    private int nowProgress = 0;

    private boolean isAnimationEnd;
    private boolean isScroll = true;

    private Paint mPaint;
    private Paint mPointPaint;


    RectF mRectF;
    RectF mBgRectf;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(strokeWidth );
        }

        if (mPointPaint == null) {
            mPointPaint = new Paint();
            mPointPaint.setAntiAlias(true);
            mPointPaint.setStyle(Paint.Style.FILL);
            mPointPaint.setStrokeWidth(24 );
        }
        if (mBgRectf == null) {
            mBgRectf = new RectF( strokeWidth, strokeWidth, getWidth() - strokeWidth,
                    getHeight() - strokeWidth);
        }
        //绘画中间空缺的圆环
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        canvas.drawArc(mBgRectf, 0, 360, false, mPaint);

        if (mRectF == null) {
            //除以一半，是因为 笔宽的一半超出View宽高了， 这里就原型往里挪动半个 笔宽， 这样整个圆就是 内切view宽高了
            int halfWid = strokeWidth/2;
            mRectF = new RectF(halfWid, halfWid, getWidth() - halfWid,
                    getHeight() - halfWid);
        }
        //绘画进度
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));


        //if (isAnimationEnd) {

            canvas.drawArc(mRectF, startAngle, 360 * progress / 100, false, mPaint);
        //} else {
        //    canvas.drawArc(mRectF, 0, 360 * nowProgress / 100, false, mPaint);
        //    if (nowProgress < progress) {
        //        if (isScroll) {
        //            return;
        //        }
        //        nowProgress++;
        //        postInvalidateDelayed(20);
        //    } else {
        //        isAnimationEnd = false;
        //    }
        //}


        canvas.drawCircle(getWidth()/2, strokeWidth/2 , 16, mPointPaint);
        float angle = 360 * progress/100 + startAngle ;
        int currAdius = (getWidth()-strokeWidth)/2;
        float dx = (float) (currAdius * Math.cos(Math.toRadians(angle)));
        float dy = (float) (currAdius * Math.sin(Math.toRadians(angle)));
        float cx =  (getWidth()/2 + dx);
        float cy =  (getHeight()/2 + dy);
        canvas.drawCircle(cx, cy , strokeWidth/2, mPointPaint);
    }

    public void startAnimation(int progress) {
        isAnimationEnd = false;
        this.progress = progress;
        this.nowProgress = 0;
        postInvalidateDelayed(20);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        isAnimationEnd = true;
        invalidate();
    }

}
