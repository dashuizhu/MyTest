package com.example.zhujiang.myapplication.game.wheel;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.game.SoundManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class FerrisGameActivity extends AppCompatActivity {

    @BindView(R.id.ferrisView) Ferris mFerrisView;
    @BindView(R.id.iv_bg) ImageView mIvBg;
    @BindView(R.id.tv_1) TextView mTv1;
    @BindView(R.id.tv_2) TextView mTv2;
    @BindView(R.id.drawerLayout) DragerViewLayout mDragerViewLayout;

    private List<Integer> mHideSet = new ArrayList<>();
    /**
     * 掉落音频
     */
    private int RING_DROP;
    private int RING_RIGHT;
    private int RING_ROTATE;

    private int mSuccessCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ferris_game);
        ButterKnife.bind(this);

        RING_ROTATE = SoundManager.getInstance(this).loadRing(this, R.raw.ferris_rotate2);
        RING_DROP = SoundManager.getInstance(this).loadRing(this, R.raw.ferris_drop);
        RING_RIGHT = SoundManager.getInstance(this).loadRing(this, R.raw.alarm_right);

        //应为摩天轮 一进入就直接旋转， 这时候等待加载完毕，开始
        SoundManager.getInstance(this)
                .getSoundPool()
                .setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        if (sampleId == RING_ROTATE) {
                            startGame();
                        }
                    }
                });

        mDragerViewLayout.addOnViewChangeListener(new DragerViewLayout.OnViewChangeListener() {
            @Override
            public void onViewChagne(View view) {
                TextView tv = (TextView) view;
                String tvStr = tv.getText().toString();

                boolean isEqu1 = Integer.parseInt(tvStr) == mHideSet.get(0) + 1;
                boolean isEqu2 = Integer.parseInt(tvStr) == mHideSet.get(1) + 1;

                //Log.w("drager",  tvStr+ " " +isEqu1 +" " + isEqu2);

                if (isEqu1 && onOver(mFerrisView.getChildAt(mHideSet.get(0)), view)) {
                    mDragerViewLayout.getViewBean(view.getId()).setMove(false);
                    mSuccessCount++;
                    SoundManager.getInstance(FerrisGameActivity.this).play(RING_RIGHT);

                    View view1 = mFerrisView.getChildAt(mHideSet.get(0));
                    view1.setAlpha(1f);
                    view1.clearAnimation();
                    //int left = mFerrisView.getLeft() + view1.getLeft();
                    //int top = mFerrisView.getTop() + view1.getTop();
                    //view.layout(left, top, left+ view.getWidth(), top+view.getHeight());
                    view.setVisibility(View.GONE);
                } else if (isEqu2 && onOver(mFerrisView.getChildAt(mHideSet.get(1)), view)) {
                    mDragerViewLayout.getViewBean(view.getId()).setMove(false);
                    mSuccessCount++;
                    SoundManager.getInstance(FerrisGameActivity.this).play(RING_RIGHT);

                    View view1 = mFerrisView.getChildAt(mHideSet.get(1));
                    //int left = mFerrisView.getLeft() + view1.getLeft();
                    //int top = mFerrisView.getTop() + view1.getTop();
                    //view.layout(left, top, left+ view.getWidth(), top+view.getHeight());
                    view1.setAlpha(1f);
                    view1.clearAnimation();
                    view.setVisibility(View.GONE);
                }

                if (mSuccessCount == mHideSet.size()) { //成功
                    SoundManager.getInstance(FerrisGameActivity.this)
                            .play(SoundManager.VIDEO_SUCCESS);
                    Observable.timer(2, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Long>() {
                                @Override
                                public void call(Long aLong) {
                                    startGame();
                                }
                            });
                }
            }

            @Override
            public void onViewRelease(View view) {
                if (mDragerViewLayout.getViewBean(view.getId()).isMove()) {
                    SoundManager.getInstance(FerrisGameActivity.this)
                            .play(SoundManager.VIDEO_FORBID);
                }
            }
        });
    }

    private void toAlpha(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

    private boolean onOver(View iv1, View iv2) {
        Rect currentViewRect = new Rect();
        iv1.getGlobalVisibleRect(currentViewRect);

        Rect secondRect = new Rect();
        iv2.getGlobalVisibleRect(secondRect);
        boolean onOver = Rect.intersects(currentViewRect, secondRect);
        return onOver;
    }

    private void startGame() {
        mSuccessCount = 0;
        mDragerViewLayout.isDrager(false);
        mDragerViewLayout.resetAll();
        mHideSet.clear();

        //注意 oncreate 里load 异步的， 立即调用播放没用。
        SoundManager.getInstance(FerrisGameActivity.this).play(RING_ROTATE);

        //摩天轮 旋转角度
        ValueAnimator animator = ObjectAnimator.ofInt(0, 720);
        animator.setDuration(8000);
        //animator.setRepeatCount(1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int angle = (int) animation.getAnimatedValue();
                Log.w("test", "angle " + angle);
                mFerrisView.setRotateAngle(-angle);
            }
        });
        animator.start();

        //背景圈旋转
        RotateAnimation rotateAnimation =
                new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F,
                        Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(4000);
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        mIvBg.startAnimation(rotateAnimation);

        //延迟4秒， 开始隐藏2个位置
        Observable.timer(4, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                        Random random = new Random();

                        int first = random.nextInt(12);
                        //if (!mHideSet.contains(first)) {
                        mHideSet.add(first);
                        toAlpha(mFerrisView.getChildAt(first));
                        //}

                        int second = random.nextInt(12);
                        while (first == second) {
                            second = random.nextInt(12);
                        }
                        mHideSet.add(second);
                        toAlpha(mFerrisView.getChildAt(second));
                    }
                });
        //延迟开始掉落动画
        Observable.timer(8, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mDragerViewLayout.isDrager(true);
                        mTv1.setText("" + (mHideSet.get(0) + 1));
                        mTv2.setText("" + (mHideSet.get(1) + 1));
                        //Animation in = AnimationUtils.loadAnimation(FerrisGameActivity.this, R.anim.push_top_out);
                        //mLayout.startAnimation(in);
                        //mLayout.setVisibility(View.VISIBLE);

                        //东西掉落
                        TranslateAnimation translateAnimation =
                                new TranslateAnimation(0, 0, -1000, 0);
                        translateAnimation.setDuration(2000);

                        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1f);
                        alphaAnimation.setDuration(2000);
                        alphaAnimation.setFillAfter(true);

                        AnimationSet set = new AnimationSet(false);
                        set.addAnimation(translateAnimation);
                        set.addAnimation(alphaAnimation);

                        mTv1.startAnimation(set);
                        mTv2.startAnimation(set);
                        mTv1.setVisibility(View.VISIBLE);
                        mTv2.setVisibility(View.VISIBLE);

                        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                SoundManager.getInstance(FerrisGameActivity.this).play(RING_DROP);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                });
    }
}
