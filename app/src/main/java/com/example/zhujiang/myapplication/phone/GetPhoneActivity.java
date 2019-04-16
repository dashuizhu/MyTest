package com.example.zhujiang.myapplication.phone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.example.zhujiang.myapplication.R;

public class GetPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone);

        //String str = UserPhone.inPhone(this);
        //Log.i("test", " --> " + str);

        String str2 = UserPhone.getDataList(this);
        Log.w("test", " --> " + str2);

    }
}
