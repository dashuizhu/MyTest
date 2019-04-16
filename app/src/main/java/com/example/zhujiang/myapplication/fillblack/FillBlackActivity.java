package com.example.zhujiang.myapplication.fillblack;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.utils.DensityUtil;

public class FillBlackActivity extends AppCompatActivity {

    @BindView(R.id.iv_chat_normal) ImageView mIvChatNormal;
    @BindView(R.id.iv_chat_press) ImageView mIvChatPress;
    @BindView(R.id.iv1) ImageView mIv1;

    int maxX, maxY;
    private int lastX;
    private int lastY;

    private boolean mMove = true;
    private int oldX, oldY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fill_black);
        ButterKnife.bind(this);

        initViews();
    }

    private void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mIv1.setOutlineProvider(viewOutlineProvider);//把自定义的轮廓提供者设置给imageView
        }

        //mIvChatNormal.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //  public void onClick(View v) {
        //
        //    //int width = mIvChatNormal.getWidth();
        //    //int height = mIvChatNormal.getHeight();
        //    //int[] position2 = new int[2];
        //    //mIvChatNormal.getLocationInWindow(position2);
        //    //
        //    //int[] position = new int[2];
        //    //mIvChatPress.getLocationOnScreen(position);
        //    ////相对父布局layout，考虑是否要减去状态栏高度
        //    //mIvChatNormal.layout(position[0], position[1], position[0] + mIvChatNormal.getWidth(),
        //    //        position[1] + mIvChatNormal.getHeight());
        //    //mIvChatNormal.layout();
        //
        //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //      if (mIv1.getClipToOutline()) {
        //        mIv1.setClipToOutline(false);//关闭裁剪
        //      } else {
        //        mIv1.setClipToOutline(true);//开启裁剪
        //      }
        //    }
        //  }
        //});
        mIvChatNormal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (!mMove) {
                    return false;
                }
                //得到事件的坐标
                final int eventX = (int) event.getRawX();
                final int eventY = (int) event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //第一次记录lastX/lastY
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        getXy();
                        Log.w("test", " oldx: " + oldX + " y:" + oldY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //计算事件的偏移
                        int dx = eventX - lastX;
                        int dy = eventY - lastY;
                        //根据事件的偏移来移动imageView
                        int left = mIvChatNormal.getLeft() + dx;
                        int top = mIvChatNormal.getTop() + dy;
                        Log.w("test", " move: " + mIvChatNormal.getLeft() + " x:" + dx);

                        if (left < 0) {
                            left = 0;
                        }
                        if (top < 0) {
                            top = 0;
                        }
                        if (maxX == 0) {
                            maxX = DensityUtil.getPhoneScreenWidth(FillBlackActivity.this);
                            maxY = DensityUtil.getPhoneScreenHeight(FillBlackActivity.this);
                        }

                        if (left > (maxX - mIvChatNormal.getWidth())) {
                            left = maxX - mIvChatNormal.getWidth();
                        }
                        if (top > (maxY - mIvChatNormal.getHeight())) {
                            top = maxY - mIvChatNormal.getHeight();
                        }
                        Log.w("test", left + " : " + top);
                        //layout是相对父布局的， 是否要考虑 减去44的状态栏高度
                        mIvChatNormal.layout(left, top, left + mIvChatNormal.getWidth(),
                                top + mIvChatNormal.getHeight());
                        //再次记录lastX/lastY
                        lastX = eventX;
                        lastY = eventY;

                        if (onOver(mIvChatNormal, mIvChatPress)) {
                            mMove = false;
                            int[] position = new int[2];
                            mIvChatPress.getLocationOnScreen(position);

                            mIvChatNormal.layout(position[0], position[1],
                                    position[0] + mIvChatNormal.getWidth(),
                                    position[1] + mIvChatNormal.getHeight());
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        //oldY = oldY - 44;
                        Log.w("test", " up " + oldX + " y:" + oldY);
                        //mIvChatNormal.layout(oldX, oldY, oldX + mIvChatNormal.getWidth(),
                        //        oldY + mIvChatNormal.getHeight());

                        //MyValueAnimator animator = MyValueAnimator.create(eventX, oldX, eventY, oldY, v);
                        //animator.setDuration(1500).start();

                        ValueAnimator animator1 = ValueAnimator.ofInt(0, 100).setDuration(1000);
                        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int value = (int) animation.getAnimatedValue();

                                int x = eventX + (oldX - eventX) * value / 100;
                                int y = eventY + (oldY - eventY) * value / 100;
                                Log.w("test", value + " anim " + x + " y:" + y);
                                mIvChatNormal.layout(x, y, x + mIvChatNormal.getWidth(),
                                        y + mIvChatNormal.getHeight());
                            }
                        });
                        animator1.start();

                        break;
                    default:
                }

                //FrameLayout.LayoutParams layoutParams =
                //        (FrameLayout.LayoutParams) mIvChatNormal.getLayoutParams();
                //
                //layoutParams.leftMargin = (int) x;
                //layoutParams.topMargin = (int) y;
                //mIvChatNormal.setLayoutParams(layoutParams);

                return true;
            }
        });
    }

    private boolean onOver(ImageView iv1, ImageView iv2) {
        Rect currentViewRect = new Rect();
        iv1.getGlobalVisibleRect(currentViewRect);

        Rect secondRect = new Rect();
        iv2.getGlobalVisibleRect(secondRect);

        return (Rect.intersects(currentViewRect, secondRect));
    }

    //1. 自定义一个轮廓提供者
    private ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void getOutline(View view, Outline outline) {
            //裁剪成一个圆形
            outline.setOval(0, 0, view.getWidth(), view.getHeight());
        }
    };

    private void startViewMove(ImageView iv, float nowX, float nowY, float destX, float destY) {

    }

    private void getXy() {
        int[] position = new int[2];
        mIvChatNormal.getLocationInWindow(position);
        oldX = position[0];
        oldY = position[1];
    }

    int x = 100;

    @OnClick(R.id.iv1)
    public void onViewClicked() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mIv1.getLayoutParams();
        String str = " " + mIv1.getX() +":"+mIv1.getY() + " left: "+mIv1.getLeft() +" top:" + mIv1.getTop()
                +" " + layoutParams.leftMargin ;
        Log.w("test", str);
        x+=100;
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", x);
        //PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 100f);

        ObjectAnimator.ofPropertyValuesHolder(mIv1, pvhX)
                .setDuration(1000).start();

    }

    @OnClick(R.id.iv2)
    public void onViewClicked2() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mIv1.getLayoutParams();
        x = 100;
        String str = " " + mIv1.getX() +":"+mIv1.getY() + " left: "+mIv1.getLeft() +" top:" + mIv1.getTop()
                +" " + layoutParams.leftMargin ;
        Log.w("test", str);

        mIv1.invalidate(250, 400, 250+mIv1.getWidth(), 400+mIv1.getHeight());
        //mIv1.requestLayout();g
        //mIv1.invalidate();

    }
}
