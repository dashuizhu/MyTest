package com.example.zhujiang.myapplication.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import com.example.zhujiang.myapplication.R;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

@Deprecated
public class GameActivity extends AppCompatActivity {

    @BindView(R.id.fl) FrameLayout mFl;
    @BindView(R.id.tv_1) Button mTv1;
    @BindView(R.id.tv_2) Button mTv2;
    @BindView(R.id.tv_3) Button mTv3;
    @BindView(R.id.ll_top) LinearLayout mLlTop;
    @BindView(R.id.ll_box1) LinearLayout mLlBox1;
    @BindView(R.id.ll_box2) LinearLayout mLlBox2;
    @BindView(R.id.ll_box3) LinearLayout mLlBox3;
    @BindView(R.id.btn_confirm) Button mBtnConfirm;
    @BindView(R.id.ll_bottom) LinearLayout mLlBottom;
    private int mOldX, mOldY;

    private View.OnTouchListener mOnTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {

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




}
