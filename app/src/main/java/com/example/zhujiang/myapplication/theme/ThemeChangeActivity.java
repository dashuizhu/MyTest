package com.example.zhujiang.myapplication.theme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.example.zhujiang.myapplication.MyApplication;
import com.example.zhujiang.myapplication.R;

public class ThemeChangeActivity extends AppCompatActivity   {

  boolean isNight = false;
  private CheckBox mCheckBox;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_theme_change);

    mCheckBox= (CheckBox) findViewById(R.id.cb_box);
    mCheckBox.setChecked(MyApplication.isNightMode);

    mCheckBox.setOnCheckedChangeListener(
            new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == MyApplication.isNightMode) {
                  return;
                }

                MyApplication.isNightMode = isChecked;
                if (isChecked) {
                  //getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                recreate();
              }
            });
  }

  //@Override
  //public void recreate() {
  //  super.recreate();
  //}

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }
}
