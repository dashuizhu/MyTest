package com.example.zhujiang.myapplication.emoji;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import com.example.zhujiang.myapplication.R;

public class EmojiActivity extends FragmentActivity {

  private LinearLayout mLayout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_emoji);
    mLayout = (LinearLayout) findViewById(R.id.layout);


    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction ft = manager.beginTransaction();
    ft.add(R.id.layout, new EmotiomComplateFragment());
    ft.commitAllowingStateLoss();

  }
}
