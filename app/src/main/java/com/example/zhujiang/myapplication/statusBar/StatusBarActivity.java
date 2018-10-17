package com.example.zhujiang.myapplication.statusBar;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.view.MyPathView;

public class StatusBarActivity extends AppCompatActivity {

  private MyPathView mMyPathView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_status_bar);
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      //透明状态栏
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      //透明导航栏 一些手机如果有虚拟键盘的话,虚拟键盘就会变成透明的,挡住底部按钮点击事件所以,最后不要用
      //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
    mMyPathView = (MyPathView) findViewById(R.id.pathView);
  }

  @Override protected void onDestroy() {
    if (mMyPathView != null) {
      mMyPathView.onStop();
    }
    super.onDestroy();
  }
}
