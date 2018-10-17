package com.example.zhujiang.myapplication.shared;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.zhujiang.myapplication.R;
import java.util.HashMap;

/**
 * 分享页面
 * @author zhuj 2017/11/15 上午10:23
 */
public class SharedActivity extends Activity {

  private Button mBtnShared;
  SharedPopupWindow mPopupWindow;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shared);

    mBtnShared = (Button) findViewById(R.id.btn_shared);

    mBtnShared.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        if (mPopupWindow == null) {
          mPopupWindow = new SharedPopupWindow(SharedActivity.this);
        }
        mPopupWindow.showAsDropDown(mBtnShared, 0, 0);
        //showShare();
      }
    });
  }


}
