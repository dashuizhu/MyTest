package com.example.zhujiang.myapplication.game;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;
import java.util.concurrent.TimeUnit;
import rx.Observable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        mGameBgView.startShow();
        //mOnTouchListener = new View.OnTouchListener() {
        //    @Override
        //    public boolean onTouch(View v, MotionEvent event) {
        //
        //        switch (event.getAction()) {
        //            case MotionEvent.ACTION_UP:
        //                break;
        //            case MotionEvent.ACTION_MOVE:
        //                int nowX = (int) (event.getRawX() - mOldX);
        //                int nowY = (int) (event.getRawY() - mOldY);
        //                mOldX = (int) event.getRawX();
        //                mOldY = (int) event.getRawY();
        //                Log.w("test", "move raw " + event.getRawX() + " " + event.getRawY()
        //                 +" left:" + v.getLeft() + " right:"+v.getTop()
        //                +" nowx:" + nowX + " nowY:"+nowY);
        //
        //                v.layout(v.getLeft()+ nowX  , v.getTop() + nowY, v.getRight()+nowX, (int)nowY + v.getBottom());
        //                break;
        //            case MotionEvent.ACTION_DOWN:
        //                mOldX = (int) event.getRawX();
        //                mOldY = (int) event.getRawY();
        //                Log.w("test", "down " + event.getRawX() + " " + event.getRawY());
        //                break;
        //            default:
        //        }
        //        return true;
        //    }
        //};
        //
        //Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(
        //        new Action1<Long>() {
        //            @Override
        //            public void call(Long aLong) {
        //
        //
        //            }
        //        });

    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        Animation in = AnimationUtils.loadAnimation(this, R.anim.push_top_in);

        mLlQuestion.startAnimation(in);

        Observable.timer(1000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Animation out = AnimationUtils.loadAnimation(GameActivity2.this, R.anim.push_top_out);
                        mLlQuestion.startAnimation(out);
                    }
                });

    }
}
