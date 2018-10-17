package com.example.zhujiang.myapplication.game.GameWalkActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.game.BitmapUtil;
import com.example.zhujiang.myapplication.game.MyDrawable;
import com.example.zhujiang.myapplication.game.MyValueAnimator;
import com.example.zhujiang.myapplication.game.SoundManager;
import com.example.zhujiang.myapplication.utils.DensityUtil;
import com.example.zhujiang.myapplication.utils.ToastUtils;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class WalkActivity extends AppCompatActivity {

    @BindView(R.id.btn_start) Button mBtnStart;
    @BindView(R.id.rl) RelativeLayout mRl;
    @BindView(R.id.tv_tree) TextView mTvTree;
    @BindView(R.id.walkView) WalkViewBG mWalkView;
    @BindView(R.id.iv_bug) ImageView mIvBug;
    @BindView(R.id.iv_successs) ImageView mIvSuccess;
    //ImageView mSuccessPostionView;

    //private int[] lastLocation = new int[2];

    private int mLastMoveY;

    private final int HIDDEN_TIME = 1500;

    private final int ITEM_SIZE = 100;

    private ValueAnimator bugAlphaAnimation;

    private MyDrawable mMyDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_walk);
        ButterKnife.bind(this);
        SoundManager.getInstance(this);
        mWalkView.setWalkGameListener(new WalkGameListener() {
            @Override
            public void onWalkResult(boolean isOver) {
                onHideAnimation();
                ToastUtils.toast(WalkActivity.this, isOver ? "成功" : "失败");
                Observable.timer(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                ToastUtils.toast(WalkActivity.this, "重新开始游戏");
                                mWalkView.resetAll();
                                onShowAnimation();

                                //原来位置固定的时候，的写法
                                //int[] newLocation = mWalkView.getEndPosition();
                                //
                                //int moveY = newLocation[1] - lastLocation[1];
                                //
                                //TranslateAnimation translateAnimation =
                                //        new TranslateAnimation(0, 0, mLastMoveY, moveY);
                                //translateAnimation.setDuration(HIDDEN_TIME);
                                //translateAnimation.setFillAfter(true);
                                //
                                //mLastMoveY = moveY;
                                ////lastLocation = newLocation;
                                //mIvSuccess.startAnimation(translateAnimation);

                                //高度会发生变的时候的写法
                                //这个是现对于原来的位置的
                                ViewPropertyAnimator view = mIvSuccess.animate();
                                float y = mWalkView.getSuccessY();
                                view.translationY(y);


                            }
                        });
            }

            @Override
            public void onWalkBack(String str) {
                SoundManager.getInstance(WalkActivity.this).play(SoundManager.VIDEO_FORBID);
                ToastUtils.toast(WalkActivity.this, str);
                onDrawing(false);
            }

            @Override
            public void onWalkRounter(List<PositionBean> list) {
                mIvBug.setEnabled(false);
                if (bugAlphaAnimation != null && bugAlphaAnimation.isRunning()) {
                    bugAlphaAnimation.cancel();
                }

                mIvBug.setAlpha(1f);
                Observable.from(list)
                        .map(new Func1<PositionBean, PositionBean>() {
                            @Override
                            public PositionBean call(PositionBean positionBean) {
                                try {
                                    Thread.sleep(650);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return positionBean;
                            }
                        })

                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<PositionBean>() {
                            @Override
                            public void call(final PositionBean positionBean) {

                                if (positionBean.isEnd()) {
                                    if (positionBean.isSuccess()) {
                                        SoundManager.getInstance(WalkActivity.this)
                                                .play(SoundManager.VIDEO_SUCCESS);
                                    } else {
                                        SoundManager.getInstance(WalkActivity.this)
                                                .play(SoundManager.VIDEO_FAIL);
                                    }
                                } else {
                                    SoundManager.getInstance(WalkActivity.this)
                                            .play(SoundManager.VIDEO_EAT);
                                }
                                TranslateAnimation trans;
                                float x = mIvBug.getX();
                                float y = mIvBug.getY();
                                float top = mIvBug.getTop();
                                float left = mIvBug.getLeft();
                                final float newX = positionBean.getX() + mWalkView.getLeft();
                                final float newY = positionBean.getY() + mWalkView.getTop();
                                final float moveX = newX - mIvBug.getX();
                                final float moveY = newY - mIvBug.getY();


                                //mIvBug.animate().translationX(newX).translationY(newY).setDuration(500).start();
                                Log.w("test", "startMove from "
                                        + x
                                        + ":"
                                        + y
                                        + "  new : "
                                        + newX
                                        + ":"
                                        + newY);
                                trans = new TranslateAnimation(0, moveX, 0, moveY);
                                trans.setDuration(600);
                                trans.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {

                                        Log.w("test", "start onlayout "
                                                + newX
                                                + ":"
                                                + newY
                                                + "  "
                                                + mIvBug.getWidth()
                                                + " "
                                                + mIvBug.getHeight()
                                                + " "
                                                + mIvBug.getLeft()
                                                + " top: "
                                                + mIvBug.getTop());
                                        mIvBug.layout(mIvBug.getLeft() + (int) moveX,
                                                mIvBug.getTop() + (int) moveY,
                                                (int) mIvBug.getLeft() + (int) moveX + ITEM_SIZE,
                                                (int) mIvBug.getTop() + (int) moveY + ITEM_SIZE);
                                        //mIvBug.layout(260, ITEM_SIZE, 380, 240);
                                        mIvBug.clearAnimation();

                                        if (positionBean.isEnd()) {
                                            btnClick();
                                        }
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                                mIvBug.startAnimation(trans);
                            }
                        });
            }

            @Override
            public void onDrawing(boolean isDrawing) {
                Log.e("test", " isDrawing" + isDrawing);
                onDrawingAnimation(isDrawing);
            }
        });

        mRl.post(new Runnable() {
            @Override
            public void run() {

                //mWalkView = new WalkViewBG(WalkActivity.this);
                //RelativeLayout.LayoutParams layoutParams =
                //        new RelativeLayout.LayoutParams(mWalkView.getMyWidth(),
                //                mWalkView.getMyHeight());
                //mWalkView.setLayoutParams(layoutParams);
                //mWalkView.setX(mTvTree.getX() - mWalkView.getMyWidth());
                //mWalkView.setY(mTvTree.getHeight() / 2 - mWalkView.getMyHeight() / 2);
                //mRl.addView(mWalkView);
                //mWalkView.requestLayout();

                //mIvBug = new ImageView(WalkActivity.this);
                //RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ITEM_SIZE, ITEM_SIZE);
                //mIvBug.setBackgroundResource(R.mipmap.ssdk_oks_classic_wechat);
                //lp.leftMargin = -ITEM_SIZE;
                //lp.topMargin = mTvTree.getHeight() / 2 - mWalkView.getHeight() / 2;
                //mIvBug.setLayoutParams(lp);
                ////mIvBug.setX(ITEM_SIZE);
                ////mIvBug.setY(mTvTree.getHeight() / 2 - mWalkView.getHeight() / 2);
                ////int top = mTvTree.getHeight() / 2 - mWalkView.getHeight() / 2;
                //mBtnStart.getWidth();
                ////mIvBug.layout(-ITEM_SIZE , top, 0, top+ITEM_SIZE);
                //mRl.addView(mIvBug);
                //mIvBug.requestLayout();

                //mSuccessPostionView = new ImageView(WalkActivity.this);
                //RelativeLayout.LayoutParams lp2 =
                //        new RelativeLayout.LayoutParams(ITEM_SIZE, ITEM_SIZE);
                //mSuccessPostionView.setBackgroundResource(R.drawable.ic_launcher);
                //
                //int[] position = mWalkView.getEndPosition();
                //lp2.leftMargin = position[0];
                //lp2.topMargin = position[1];
                ////mSuccessPostionView.setX(position[0]);
                ////mSuccessPostionView.setY(position[1]);
                //mSuccessPostionView.setLayoutParams(lp2);
                //mRl.addView(mSuccessPostionView);
                //mSuccessPostionView.requestLayout();

                //lastLocation = position;

                onShowAnimation();

                ViewPropertyAnimator animator = mIvSuccess.animate();
                float y = mWalkView.getSuccessY();
                animator.setDuration(1000);
                animator.translationY(y);

                //mSuccessPostionView.setOnClickListener(new View.OnClickListener() {
                //    @Override
                //    public void onClick(View v) {
                //        ToastUtils.toast(WalkActivity.this, "onClick");
                //    }
                //});
            }
        });

        //Observable.timer(3,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
        //        .subscribe(new Action1<Long>() {
        //            @Override
        //            public void call(Long aLong) {
        //                mSuccessPostionView = new ImageView(WalkActivity.this);
        //                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ITEM_SIZE, ITEM_SIZE);
        //                mSuccessPostionView.setBackgroundResource(R.drawable.ic_launcher);
        //                mSuccessPostionView.setLayoutParams(lp);
        //
        //                int[] position = mWalkView.getEndPosition();
        //                mSuccessPostionView.setX(position[0]);
        //                mSuccessPostionView.setY(position[1]);
        //
        //                lastLocation = position;
        //                mRl.addView(mSuccessPostionView);
        //
        //            }
        //        });



    }

    @OnClick(R.id.iv_bug)
    public void btnBugClick() {
        SoundManager.getInstance(this).play(SoundManager.VIDEO_BEGIN);
        mWalkView.cleanMoveRouter();
    }

    @OnClick(R.id.btn_start)
    public void btnClick() {

        onHideAnimation();
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mWalkView.resetAll();
                        onShowAnimation();

                        //int[] newLocation = mWalkView.getEndPosition();
                        //
                        //int moveY = newLocation[1] - lastLocation[1];
                        ////
                        ////TranslateAnimation translateAnimation =
                        ////        new TranslateAnimation(0, 0, mLastMoveY, moveY);
                        ////translateAnimation.setDuration(HIDDEN_TIME);
                        ////translateAnimation.setFillAfter(true);
                        //////MyValueAnimator.create( newLocation[0], newLocation[0], mSuccessPostionView.getY(), lastLocation[1], mSuccessPostionView)
                        //////        .setDuration(2000).start();
                        ////mLastMoveY = moveY;
                        ////lastLocation = newLocation;
                        ////mSuccessPostionView.startAnimation(translateAnimation);
                        //
                        ////这种
                        //ViewPropertyAnimator view = mSuccessPostionView.animate();
                        //float y = mSuccessPostionView.getY();
                        //view.translationY(newLocation[1] - lastLocation[1]);

                        //这个是现对于原来的位置的
                        ViewPropertyAnimator view = mIvSuccess.animate();
                        float y = mWalkView.getSuccessY();
                        view.translationY(y);
                        //mLastMoveY = (int) y;

                    }
                });
    }

    private void onHideAnimation() {
        int phoneWidth = DensityUtil.getPhoneScreenWidth(this);
        int phoneHeight = DensityUtil.getPhoneScreenHeight(this);
        int x = (int) mWalkView.getX();
        int y = (int) mWalkView.getY();
        TranslateAnimation translateAnimation =
                new TranslateAnimation(0, mWalkView.getMeasuredWidth(), 0, 0);
        Log.w("test", "  startHide  " + System.currentTimeMillis());
        translateAnimation.setDuration(HIDDEN_TIME);
        //translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mWalkView.layout(mWalkView.getLeft() + mWalkView.getWidth(), mWalkView.getTop(),
                        mWalkView.getLeft() + mWalkView.getWidth() * 2, mWalkView.getBottom());
                mWalkView.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mWalkView.startAnimation(translateAnimation);

        //AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0f);
        //alphaAnimation.setDuration(HIDDEN_TIME);
        //alphaAnimation.setFillAfter(true);
        //mIvBug.startAnimation(alphaAnimation);
        mIvBug.layout(-ITEM_SIZE, mWalkView.getTop(), 0, mWalkView.getTop() + ITEM_SIZE);

        Log.w("test", "  GONE  " + System.currentTimeMillis());
    }

    private void onShowAnimation() {
        SoundManager.getInstance(this).play(SoundManager.VIDEO_GAME_START);
        int phoneWidth = DensityUtil.getPhoneScreenWidth(this);
        int phoneHeight = DensityUtil.getPhoneScreenHeight(this);
        int x = (int) mWalkView.getX();
        int y = (int) mWalkView.getY();
        int wid = mWalkView.getMeasuredWidth();
        TranslateAnimation translateAnimation =
                new TranslateAnimation(0, -mWalkView.getMeasuredWidth(), 0, 0);
        translateAnimation.setDuration(1800);
        //translateAnimation.setFillAfter(true);
        mWalkView.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.w("test", "now "
                        + mWalkView.getLeft()
                        + " "
                        + mWalkView.getTop()
                        + " "
                        + mWalkView.getTranslationX()
                        + " "
                        + mWalkView.getX());
                mWalkView.clearAnimation();
                mWalkView.layout(mWalkView.getLeft() - mWalkView.getWidth(), mWalkView.getTop(),
                        mWalkView.getLeft(), mWalkView.getBottom());
                onDrawingAnimation(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        float bugX = mIvBug.getX();
        float bugWidth = mIvBug.getWidth();
        float bugWidth2 = mIvBug.getMeasuredWidth();
        final float toX = mWalkView.getX() - mWalkView.getMeasuredWidth() - ITEM_SIZE;
        TranslateAnimation bugTranslate = new TranslateAnimation(0, toX - mIvBug.getX(), 0, 0);
        bugTranslate.setDuration(HIDDEN_TIME);
        //bugTranslate.setFillAfter(true);
        bugTranslate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = mIvBug.getLeft();
                int top = mIvBug.getTop();
                int right = mIvBug.getRight();
                int y = (int) mIvBug.getY();

                ViewParent vp = mIvBug.getParent();
                RelativeLayout.LayoutParams lp =
                        (RelativeLayout.LayoutParams) mIvBug.getLayoutParams();
                int leftMargin = lp.leftMargin;
                int paddingTop = mIvBug.getPaddingTop();
                int paddingLeft = mIvBug.getPaddingLeft();
                mIvBug.layout((int) (mIvBug.getLeft() + toX) + ITEM_SIZE, mIvBug.getTop(),
                        (int) (mIvBug.getRight() + toX) + ITEM_SIZE, mIvBug.getBottom());
                //mIvBug.layout(140, mIvBug.get, 260, 0);
                mIvBug.clearAnimation();

                int paddingTop2 = mIvBug.getPaddingTop();
                int paddingLeft2 = mIvBug.getPaddingLeft();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mIvBug.startAnimation(bugTranslate);
    }

    private void onDrawingAnimation(boolean isDrawing) {
        if (isDrawing) {
            mIvBug.setEnabled(false);
            return;
        }

        if (bugAlphaAnimation == null) {
            bugAlphaAnimation = ObjectAnimator.ofFloat(0, 2);
            bugAlphaAnimation.setDuration(2000);
            bugAlphaAnimation.setRepeatCount(Animation.INFINITE);
            bugAlphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float f = (float) animation.getAnimatedValue();
                    float alpha = Math.abs(1f - f);
                    mIvBug.setAlpha(alpha);

                    //Log.e("test", " isDrawing " + mIvBug.isEnabled() + " alpha " + f);
                    //alpha是个小数，不一定正好1f整
                    if (alpha >= 0.95f && !mIvBug.isEnabled()) {
                        bugAlphaAnimation.cancel();
                        mIvBug.setAlpha(1f);
                    }
                }
            });
        }
        mIvBug.setEnabled(true);
        if (!bugAlphaAnimation.isRunning()) {
            bugAlphaAnimation.start();
        }
    }
}
