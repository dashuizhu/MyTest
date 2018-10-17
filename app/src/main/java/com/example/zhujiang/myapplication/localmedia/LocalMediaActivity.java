package com.example.zhujiang.myapplication.localmedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;

public class LocalMediaActivity extends AppCompatActivity {

  @BindView(R.id.recyclerView)  RecyclerView mRecyclerView;
  @BindView(R.id.btn_preview)   Button mBtnPreview;
  @BindView(R.id.cb_all)        CheckBox mCbAll;
  @BindView(R.id.btn_send)      Button mBtnSend;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_local_media);
    ButterKnife.bind(this);
  }



}
