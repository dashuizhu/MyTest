package com.example.zhujiang.myapplication.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.utils.DensityHelp;
import com.example.zhujiang.myapplication.utils.ToastUtils;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class GameActivity2 extends AppCompatActivity {

    @BindView(R.id.gameView) GameBgView mGameBgView;
    @BindView(R.id.tv1) TextView mTv1;
    @BindView(R.id.tv2) TextView mTv2;
    @BindView(R.id.tv3) TextView mTv3;
    @BindView(R.id.ll_question) LinearLayout mLlQuestion;
    @BindView(R.id.btn_confirm) Button mBtnConfirm;

    private View.OnTouchListener mOnTouchListener;

    private Subscription mTimeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DensityHelp.setOrientationHeight(this, true);
        setContentView(R.layout.activity_game2);
        ButterKnife.bind(this);
        initViews();
        SoundManager.getInstance(this);
    }

    private void initViews() {
        mGameBgView.post(new Runnable() {
            @Override
            public void run() {
                restartGame();
                mLlQuestion.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        mBtnConfirm.setEnabled(false);
        boolean isRight = mGameBgView.isRight();
        ToastUtils.toast(this, "结果 " + isRight);

        if (isRight) {
            SoundManager.getInstance(this).play(SoundManager.VIDEO_SUCCESS);
        } else {
            SoundManager.getInstance(this).play(SoundManager.VIDEO_FAIL);
        }

        Animation in = AnimationUtils.loadAnimation(this, R.anim.push_top_in);
        mLlQuestion.startAnimation(in);

        mGameBgView.hideAnimation();

        mTimeSubscription = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (aLong == 0) {
                            restartGame();
                        } else if (aLong == 1) {
                            mBtnConfirm.setEnabled(true);
                            mTimeSubscription.unsubscribe();
                            mTimeSubscription = null;
                        }
                    }
                });
    }

    private void restartGame() {
        SoundManager.getInstance(this).play(SoundManager.VIDEO_GAME_START);
        Random random = new Random();

        int middleValue = random.nextInt(3) + 2;
        //boolean isAdd = random.nextBoolean();
        //是否顺序 或者逆序
        boolean isAdd = false;

        int[] value = new int[3];
        value[0] = isAdd ? middleValue + 1 : middleValue - 1;
        value[2] = isAdd ? middleValue - 1 : middleValue + 1;
        value[1] = middleValue;

        mTv1.setText(String.valueOf(value[0]));
        mTv2.setText(String.valueOf(value[1]));
        mTv3.setText(String.valueOf(value[2]));

        //随机生成 空的
        //int emptyIndex = random.nextInt(2);
        //if (emptyIndex==0) {
        //    mTv1.setText("?");
        //} else if  (emptyIndex == 1) {
        //    mTv2.setText("?");
        //} else {
        mTv3.setText("?");
        //}

        mGameBgView.initBox(value, 2);

        Animation out = AnimationUtils.loadAnimation(GameActivity2.this, R.anim.push_top_out);
        mLlQuestion.startAnimation(out);
    }
}
