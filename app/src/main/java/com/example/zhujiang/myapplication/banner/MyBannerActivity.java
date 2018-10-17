package com.example.zhujiang.myapplication.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MyBannerActivity extends AppCompatActivity {

    @BindView(R.id.image3) ImageView mImage3;
    @BindView(R.id.image2) ImageView mImage2;
    @BindView(R.id.image1) ImageView mImage1;

    GestureDetector mDetector;
    private boolean isScroll;
    private int delay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_banner);
        ButterKnife.bind(this);

        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                delay = 0;
                isScroll = false;
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                    float distanceY) {
                Log.w("test", "onScroll " + distanceX + " total " + delay);
                if (isScroll) {
                    return false;
                }
                delay += distanceX;

                if (delay < -150) {
                    onRight();
                    isScroll = true;
                } else if (delay > 150) {
                    onLeft();
                    isScroll = true;
                }

                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                    float velocityY) {
                Log.w("test", "onFling " + velocityX);
                if (!isScroll) {
                    if (velocityX > 250) {
                        onLeft();
                    } else if (velocityX < -250) {
                        onRight();
                    }
                }

                return false;
            }
        });

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        onLeft();
                    }
                });
    }

    private void onLeft() {

        int width1 = mImage1.getWidth();
        int height1 = mImage1.getHeight();
        int x1 = (int) mImage1.getX();

        int width2 = mImage2.getWidth();
        int height2 = mImage2.getHeight();
        int x2 = (int) mImage2.getX();

        int x3 = (int) mImage3.getX();

        mImage1.animate().translationXBy(-width1).setDuration(1000).start();

        mImage2.animate()
                .translationXBy(x1 - x2)
                .scaleX(1.25f)
                .scaleY(1.25f)
                .setDuration(1000)
                .start();
        //
        //
        mImage3.animate()
                .translationXBy(x2 - x3)
                .scaleX(1.25f)
                .scaleY(1.25f)
                .setDuration(1000)
                .start();
    }

    private void onRight() {

    }

    @OnClick(R.id.image1)
    public void onViewClicked() {
    }
}
