package com.example.zhujiang.myapplication.collaspingLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;
import com.example.zhujiang.myapplication.R;
import lombok.experimental.Tolerate;

/**
 * @author zhuj 2017/10/11 下午5:35.
 */
public class CollaspingActivity extends AppCompatActivity {

  AppBarLayout mAppBarLayout;

  SwipeRefreshLayout mRefreshLayout;
  RecyclerView mRecyclerView;
  TabLayout mTabLayout;

  Toolbar mToolbar;
  private int mVertical;

  private TextView mTvTitle;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_collasping);

    mAppBarLayout = (AppBarLayout) findViewById(R.id.appBar_layout);
    //mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
    //mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mTvTitle = (TextView) findViewById(R.id.tv_title);

    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);



    mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
      @Override public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mToolbar.getMenu() == null) {
          return;
        }
        int size =  mToolbar.getMenu().size();
        if (size ==0 ) {
          return;
        }

        //这里滑动的是负数
        mVertical = -verticalOffset;

        int scrollRange = appBarLayout.getTotalScrollRange();
        float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
        //滑动比例，达到比例，就收起 或隐藏
        Log.w("test", " ver " + verticalOffset + " " + fraction);
        if (fraction < 0.3) {
          mToolbar.getMenu().findItem(R.id.nav_camera).setVisible(true);
          mToolbar.getMenu().findItem(R.id.nav_gallery).setVisible(true);
          mTvTitle.setVisibility(View.VISIBLE);
        } else  {
          mToolbar.getMenu().findItem(R.id.nav_camera).setVisible(false);
          mToolbar.getMenu().findItem(R.id.nav_gallery).setVisible(false);
          mTvTitle.setVisibility(View.GONE);
          //mRefreshLayout.setEnabled(false);
        }
      }
    });

    //mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
    //  @Override
    //  public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    //    switch (newState) {
    //      case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://空闲状态
    //        int max = mAppBarLayout.getTotalScrollRange();
    //        //滑动不够一半， 添加惯性， 只能完全展开 或者 完全折叠
    //        if (mVertical < max / 2) {
    //          mAppBarLayout.setExpanded(true, true);
    //        } else {
    //          mAppBarLayout.setExpanded(false, true);
    //        }
    //
    //
    //        break;
    //      case AbsListView.OnScrollListener.SCROLL_STATE_FLING://滚动状态
    //      case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸后滚动
    //        break;
    //    }
    //    super.onScrollStateChanged(recyclerView, newState);
    //  }
    //});

    //mTabLayout = (TabLayout) findViewById(R.id.tablayout);
    //mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    //  @Override public void onTabSelected(TabLayout.Tab tab) {
    //    Snackbar.make(mAppBarLayout, " " + tab.getText(), Snackbar.LENGTH_LONG).show();
    //  }
    //
    //  @Override public void onTabUnselected(TabLayout.Tab tab) {
    //
    //  }
    //
    //  @Override public void onTabReselected(TabLayout.Tab tab) {
    //
    //  }
    //});
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.tool_menu, menu);
    return true;
  }
}
