package com.example.zhujiang.myapplication.collaspingLayout;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class TabLayoutTestActivity extends AppCompatActivity {

  @BindView(R.id.tablayout) MyTabLayout mTablayout;
  @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tab_layout_test);
    ButterKnife.bind(this);

    mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override public void onTabSelected(TabLayout.Tab tab) {
        tab.getPosition();
      }

      @Override public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override public void onTabReselected(TabLayout.Tab tab) {

      }
    });

    List<String> list = new ArrayList<>();
    for (int i=0; i<10 ; i++) {
      list.add("xxx"+i);
    }
    TabStringAdapter adapter  = new TabStringAdapter(this, list);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    mRecyclerView.setAdapter(adapter);
    adapter.setCategoryClickListener(new TabStringAdapter.CategoryClickListener() {
      @Override public void onItemClick(String string, int position) {
        mRecyclerView.scrollToPosition(position-1);
      }
    });
    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
      }
    });

  }
}
