package com.example.zhujiang.myapplication.game.car;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.game.SoundManager;
import com.example.zhujiang.myapplication.utils.ToastUtils;
import com.example.zhujiang.myapplication.utils.ZipResUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class CarGameActivity extends AppCompatActivity {

    @BindView(R.id.tv1) TextView mTv1;
    @BindView(R.id.tv2) TextView mTv2;
    @BindView(R.id.tv3) TextView mTv3;
    @BindView(R.id.ll_question) LinearLayout mLlQuestion;
    @BindView(R.id.carView) CarView mCarView;
    private int mSuccessCount;

    int ringId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_game);
        ButterKnife.bind(this);

        mCarView.post(new Runnable() {
            @Override
            public void run() {
                restartGame();
            }
        });
    }

    private void restartGame() {
        //SoundManager.getInstance(this).play(SoundManager.VIDEO_GAME_START);
        Random random = new Random();

        boolean[] temp = new boolean[10];

        List<Integer> coutList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            //就相当于是 1-9
            int num = random.nextInt(9) +1;
            //如果对应位置已经是true ，就表明已生成这个数字， -- 就是要重新生成
            if (temp[num]) {
                i--;
            } else {
                temp[num] = true;
                coutList.add(num);
            }
        }

        int successIndex = random.nextInt(3);

        mTv1.setText(String.valueOf(coutList.get(0)));
        mTv2.setText(String.valueOf(coutList.get(1)));
        mTv3.setText(String.valueOf(coutList.get(2)));

        mTv1.setTag(successIndex == 0);
        mTv2.setTag(successIndex == 1);
        mTv3.setTag(successIndex == 2);

        mSuccessCount = coutList.get(successIndex);

        //mCarView.layout(100, 20, 700, 420);
        mCarView.resetGame(coutList.get(successIndex));

        Animation out = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        mLlQuestion.startAnimation(out);
    }

    @OnClick({ R.id.tv1, R.id.tv2, R.id.tv3 })
    public void onViewClicked(View view) {
        if (!mCarView.isReady()) {
            return;
        }
        if (!mCarView.isExpand()) {
            ToastUtils.toast(this, "请先打开窗帘");
            return;
        }

        boolean isSuccess = (boolean) view.getTag();
        if (isSuccess) {
            SoundManager.getInstance(this).play(SoundManager.VIDEO_SUCCESS);
        } else {
            SoundManager.getInstance(this).play(SoundManager.VIDEO_FAIL);

            ToastUtils.toast(this, "正确个数 " + mSuccessCount);
        }

        Animation in = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
        in.setFillAfter(true);
        mLlQuestion.startAnimation(in);

        mCarView.moveOut();

        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        restartGame();
                    }
                });
    }


}
