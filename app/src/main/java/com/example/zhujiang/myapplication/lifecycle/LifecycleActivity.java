package com.example.zhujiang.myapplication.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.zhujiang.myapplication.R;

public class LifecycleActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        BasePresenter pre = new BasePresenter();
        getLifecycle().addObserver(pre);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("test " , "ondestroy " + getClass().getSimpleName() );

    }
}
