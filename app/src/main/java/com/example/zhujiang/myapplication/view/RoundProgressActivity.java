package com.example.zhujiang.myapplication.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.stepView.StepArcView;
import com.example.zhujiang.myapplication.view.progressRound.Progress2View;
import com.example.zhujiang.myapplication.view.progressRound.StepRoundView;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import java.util.concurrent.TimeUnit;
import rx.functions.Action1;

public class RoundProgressActivity extends AppCompatActivity {

    private StepRoundView mView;

    private Progress2View mProgress2View;

    private StepArcView mStepArcView;

    Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_progress);
        mView = findViewById(R.id.stepView);
        mProgress2View = findViewById(R.id.progress);
        mStepArcView = findViewById(R.id.stepArcView);
        //mProgress2View.setProgress(8);
        //mSubscription = Observable.interval(1, TimeUnit.SECONDS)
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .subscribe(new Action1<Long>() {
        //            @Override
        //            public void call(Long aLong) {
        //                mView.setNumList(aLong.intValue());
        //                mProgress2View.setProgress(aLong.intValue());
        //                mProgress2View.invalidate();
        //                mView.invalidate();
        //            }
        //        });


        mStepArcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RoundProgressActivity.this, ""+ mStepArcView.isStart(), Toast.LENGTH_LONG).show();
                mStepArcView.click();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
}
