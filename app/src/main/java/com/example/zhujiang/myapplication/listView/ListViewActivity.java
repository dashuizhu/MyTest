package com.example.zhujiang.myapplication.listView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import com.example.zhujiang.myapplication.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    SmartRefreshLayout mRefreshLayout;
    ListView mListView;
    private List<String> list;
    private StringAdapter mStringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mListView = (ListView) findViewById(R.id.listView);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("test " + i);
        }

        mStringAdapter = new StringAdapter(this, list);
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
                list.add(0, " add 0");
                for (int i = 20; i < 30; i++) {
                    list.add("add " + i);
                }
                mRefreshLayout.finishLoadmore();
                mStringAdapter.notifyDataSetChanged();
            }
        });
    }
}
