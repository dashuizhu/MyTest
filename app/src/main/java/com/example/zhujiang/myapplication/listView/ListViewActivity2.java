package com.example.zhujiang.myapplication.listView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.zhujiang.myapplication.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import java.util.ArrayList;
import java.util.List;

public class ListViewActivity2 extends AppCompatActivity {

    SmartRefreshLayout mRefreshLayout;
    RecyclerView mListView;
    private List<String> list;
    private StringAdapter2 mStringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mListView = (RecyclerView) findViewById(R.id.listView);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("test " + i);
        }

        mStringAdapter = new StringAdapter2(mListView);
        mStringAdapter.setData(list);
        mListView.setLayoutManager(new LinearLayoutManager(this));
        mListView.setNestedScrollingEnabled(false);
        mListView.setAdapter(mStringAdapter);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 20; i < 30; i++) {
                    list.add("add " + i);
                }
                mStringAdapter.notifyDataSetChanged();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //list.add(0, " add 0");
                List<String> newList = new ArrayList<>();
                for (int i = 20; i < 30; i++) {
                    newList.add("add " + i);
                }
                mRefreshLayout.finishLoadmore();
                int oldSize = list.size();
                list.addAll(newList);
                mStringAdapter.notifyItemRangeInserted(oldSize, newList.size());
                mStringAdapter.addMoreData(newList);
            }
        });
    }
}
