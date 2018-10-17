package com.example.zhujiang.myapplication.view.progressRound;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.example.zhujiang.myapplication.R;

public class StepRoundView extends View {

    private Paint mBgPaint;//背景圆环
    private Paint mProgressPaint;//进度圆环
    public static final int MAX_PROGRESS = 36;

    private int nowProgress;

    private int angle;

    public void setNumList(int progress) {
        nowProgress = progress;
    }

    public StepRoundView(Context context) {
        super(context);
    }

    public StepRoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    private void initPaint(Context mContext) {

        angle = 360 / MAX_PROGRESS;

        mBgPaint = new Paint();
        mBgPaint.setColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setStrokeWidth(1);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setStrokeWidth(1);
        mProgressPaint.setTextSize(55);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.translate(0, 0);

        String text = String.valueOf(MAX_PROGRESS - nowProgress);

        //绘制文本
        Rect mBound = new Rect();
        mProgressPaint.getTextBounds(text, 0, text.length(), mBound);
        int centerX = getWidth() / 2;
        int centerY = getWidth() / 2;
        int textHalfWidth = mBound.width() / 2;
        int textHalfHeight = mBound.height() / 2;
        canvas.drawText(text, centerX - textHalfWidth, centerY + textHalfHeight, mProgressPaint);

        //以圆心绘画，默认角度是从右边开始，
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.rotate(270);
        RectF rectF = new RectF(getWidth() / 3, 0, getWidth() / 3 + 30, 8);
        //绘画背景
        for (int i = 0; i < 12; i++) {
            canvas.drawRoundRect(rectF, 3, 3, mBgPaint);
            canvas.rotate(angle);
        }

        //绘画进度
        for (int i = 0; i < nowProgress; i++) {
            canvas.drawRoundRect(rectF, 3, 3, mProgressPaint);
            canvas.rotate(angle);
        }
    }
}