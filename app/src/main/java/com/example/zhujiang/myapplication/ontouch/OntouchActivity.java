package com.example.zhujiang.myapplication.ontouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import com.example.zhujiang.myapplication.R;

public class OntouchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ontouch);

        findViewById(R.id.myView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OntouchActivity.this, "my view", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void btnClick(View v) {
        Toast.makeText(this, "2", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("test", " actiivty dispatch " + ev.getAction());
        boolean ret = super.dispatchTouchEvent(ev);
        Log.d("test", " actiivty dispatch return " + ret);
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("test", " actiivty onTouchEvent " + event.getAction());
        boolean ret = super.onTouchEvent(event);
        Log.d("test", " actiivty onTouchEvent return " + ret);
        return ret;
    }
}
