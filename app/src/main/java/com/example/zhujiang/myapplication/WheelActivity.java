package com.example.zhujiang.myapplication;

import android.app.Activity;
import android.os.Bundle;
import com.example.zhujiang.myapplication.wheel.ArrayWheelAdapter;
import com.example.zhujiang.myapplication.wheel.NumericWheelAdapter;
import com.example.zhujiang.myapplication.wheel.WheelView;

public class WheelActivity extends Activity {

  private WheelView hourWheel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wheel);
    hourWheel = (WheelView) findViewById(R.id.wheelView);


    String[] hourArray = new String[6];
    for (int i=8; i<13; i++) {
      hourArray[i-8] = ""+i;
    }
    hourArray[5] = "111";
    hourWheel.setAdapter(new NumericWheelAdapter());
    hourWheel.setAdapter(new ArrayWheelAdapter<String>(hourArray));
    hourWheel.setLabel("年");
    hourWheel.setCurrentItem(0);
    hourWheel.setCyclic(true);
  }
}
