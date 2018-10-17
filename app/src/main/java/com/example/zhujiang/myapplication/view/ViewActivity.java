package com.example.zhujiang.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.theme.ThemeChangeActivity;

public class ViewActivity extends AppCompatActivity {

  private CircleProgressView mProgressView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view);

    mProgressView = (CircleProgressView) findViewById(R.id.circleView);

    mProgressView.setProgress("个人资料", 11, "银行资料", 0,
            "汽车资料", 33);


    //startActivity(new Intent(this, ThemeChangeActivity.class));
  }
}
