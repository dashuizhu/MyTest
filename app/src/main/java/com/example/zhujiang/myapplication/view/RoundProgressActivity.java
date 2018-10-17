package com.example.zhujiang.myapplication.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.view.progressRound.StepRoundView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import java.util.concurrent.TimeUnit;
import rx.functions.Action1;

public class RoundProgressActivity extends AppCompatActivity {

    private StepRoundView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_progress);
        mView = findViewById(R.id.stepView);

        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mView.setNumList(aLong.intValue());
                        mView.invalidate();
                    }
                });
    }
}
