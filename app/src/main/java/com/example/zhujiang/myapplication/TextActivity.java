package com.example.zhujiang.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        ActivityBean ac = new ActivityBean(MainActivity.class, "main");
        MyApplication.sBean = ac;
    }
}
