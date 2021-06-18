package com.example.zhujiang.myapplication.window;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.zhujiang.myapplication.R;

/**
 * @author zhuj 2018/4/12 下午2:25.
 */
 class CustomToast extends Toast {


  /**
   * Construct an empty Toast object.  You must call {@link #setView} before you
   * can call {@link #show}.
   *
   * @param context The context to use.  Usually your {@link Application}
   * or {@link Activity} object.
   */
  public CustomToast(Context context) {
    super(context);
  }


}
