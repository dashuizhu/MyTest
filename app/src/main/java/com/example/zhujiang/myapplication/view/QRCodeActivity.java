package com.example.zhujiang.myapplication.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.statusBar.StatusBarActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QRCodeActivity extends AppCompatActivity {
    private TextView mTv;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏 一些手机如果有虚拟键盘的话,虚拟键盘就会变成透明的,挡住底部按钮点击事件所以,最后不要用
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_q_r_code);
        mTv= findViewById(R.id.tv);


        mTv.setText(sdf.format(new Date())+"更新");
    }
}
