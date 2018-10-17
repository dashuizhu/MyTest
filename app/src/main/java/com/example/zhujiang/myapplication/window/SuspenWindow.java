package com.example.zhujiang.myapplication.window;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.example.zhujiang.myapplication.BuildConfig;
import com.example.zhujiang.myapplication.R;

/**
 * @author zhuj 2018/4/12 下午2:11.
 */
public class SuspenWindow {

  public void addWindow(Context context) {
    WindowManager windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    WindowManager.LayoutParams params = new WindowManager.LayoutParams();
    LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
    LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.view_keyboard_xhs, null);
    params.width = WindowManager.LayoutParams.MATCH_PARENT;
    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.gravity = Gravity.CENTER_VERTICAL | Gravity.TOP;

    params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

    //WindowManager.LayoutParams.hideTimeoutMilliseconds = 20000;
    //设置window type
    //if (BuildConfig.VERSION_CODE >= 25) {
    //  params.type = WindowManager.LayoutParams.TYPE_PHONE;
    //} else {
      params.type = WindowManager.LayoutParams.TYPE_TOAST;
    //}

    windowManager.addView(linearLayout, params);
  }
}
