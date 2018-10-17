package com.example.zhujiang.myapplication.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class RecyclerTest extends Activity {

  @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recycler_test);
    ButterKnife.bind(this);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    mRecyclerView.setLayoutManager(layoutManager);

    List<String> list  = new ArrayList<String>();
    list.add("111");
    list.add("222");
    list.add("333");
    list.add("444");

    StringAdapter adapter = new StringAdapter(this, list);
    mRecyclerView.setAdapter(adapter);
  }
}
