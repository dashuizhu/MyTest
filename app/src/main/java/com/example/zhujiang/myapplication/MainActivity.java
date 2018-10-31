package com.example.zhujiang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.zhujiang.myapplication.animotion.AnimActivity;
import com.example.zhujiang.myapplication.banner.MyBannerActivity;
import com.example.zhujiang.myapplication.bottomnav.BottomNavActivity;
import com.example.zhujiang.myapplication.changeImage.ChagneImageActivity;
import com.example.zhujiang.myapplication.collaspingLayout.CollaspingActivity;
import com.example.zhujiang.myapplication.collaspingLayout.TabLayoutTestActivity;
import com.example.zhujiang.myapplication.emoji.EmojiActivity;
import com.example.zhujiang.myapplication.faceonline.BaseVideoActivity;
import com.example.zhujiang.myapplication.faceonline.OnlineFaceDemo;
import com.example.zhujiang.myapplication.fillblack.FillBlackActivity;
import com.example.zhujiang.myapplication.game.GameActivity;
import com.example.zhujiang.myapplication.game.GameActivity2;
import com.example.zhujiang.myapplication.game.GameWalkActivity.WalkActivity;
import com.example.zhujiang.myapplication.game.car.CarGameActivity;
import com.example.zhujiang.myapplication.game.wheel.FerrisGameActivity;
import com.example.zhujiang.myapplication.jchat.LoginActivity;
import com.example.zhujiang.myapplication.likeView.LikeActivity;
import com.example.zhujiang.myapplication.listView.ListViewActivity;
import com.example.zhujiang.myapplication.listView.ListViewActivity2;
import com.example.zhujiang.myapplication.pageRecycler.PageActivity;
import com.example.zhujiang.myapplication.qrcode.ActivityScanerCode;
import com.example.zhujiang.myapplication.runText.RunTextActivity;
import com.example.zhujiang.myapplication.shared.SharedActivity;
import com.example.zhujiang.myapplication.statusBar.StatusBarActivity;
import com.example.zhujiang.myapplication.theme.ThemeChangeActivity;
import com.example.zhujiang.myapplication.utils.SharedPreUser;
import com.example.zhujiang.myapplication.view.RoundProgressActivity;
import com.example.zhujiang.myapplication.view.ViewActivity;
import com.example.zhujiang.myapplication.viewShare.ViewShare1Activity;
import com.example.zhujiang.myapplication.wifi.WifiActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private ListView mListView;
  private ActivityAdapter mSimpleAdapter;
  private List<ActivityBean> mMapList = new ArrayList<>();

  private boolean isNight;

  private DrawerLayout mLayout;
  private NavigationView mNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    //transitionSystemBar();
    setContentView(R.layout.activity_main);
    initViews();

    initData();
    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      /**
       *
       * @param adapterView
       * @param view
       * @param i
       * @param l
       */
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //long value = 0;
        //long time1 =System.currentTimeMillis();
        //for (int j=0; j<1* 10000; j++) {
        //  value +=j;
        //}
        //System.out.println(" " + (System.currentTimeMillis() - time1));
        //Toast.makeText(MainActivity.this, " position " + i, Toast.LENGTH_LONG).show();
        String activityName = mMapList.get(i).getActivityName();
        goActivity(activityName);

        SharedPreUser.getInstance().put(MainActivity.this, "activity", activityName);
        //Snackbar.make(view, "那些让你相见恨晚的方法、类或接口"+i, Snackbar.LENGTH_LONG).show();
      }
    });

    String activityName = (String) SharedPreUser.getInstance().get(MainActivity.this, "activity", "");
    goActivity(activityName);

  }

  private void goActivity(String activityName) {
    Class send = null;
    try {
      send = Class.forName(activityName);
      Intent intent = new Intent();
      intent.setClass(MainActivity.this, send);
      startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initViews() {
    mListView = (ListView) findViewById(R.id.listView);
    mSimpleAdapter = new ActivityAdapter(this, mMapList);
    mListView.setAdapter(mSimpleAdapter);

    mLayout = findViewById(R.id.layout_drawer);
    //mNavigationView = findViewById(R.id.navigationView);

    //mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    //  @Override
    //  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    //    mLayout.closeDrawers();
    //    return false;
    //  }
    //});

    //mLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
    //  @Override
    //  public void onDrawerSlide(View drawerView, float slideOffset) {
    //
    //  }
    //
    //  @Override
    //  public void onDrawerOpened(View drawerView) {
    //
    //  }
    //
    //  @Override
    //  public void onDrawerClosed(View drawerView) {
    //
    //  }
    //
    //  @Override
    //  public void onDrawerStateChanged(int newState) {
    //
    //  }
    //});

  }

  private void initData() {
    mMapList.add(new ActivityBean(StatusBarActivity.class, "状态栏"));
    mMapList.add(new ActivityBean(GetWheelActivity.class, "时间选择器"));
    mMapList.add(new ActivityBean(WheelActivity.class, "时间选择器2"));
    mMapList.add(new ActivityBean(PageActivity.class, "recyclerView分页"));
    mMapList.add(new ActivityBean(EmojiActivity.class, "emoji"));
    mMapList.add(new ActivityBean(CollaspingActivity.class, "折叠"));
    mMapList.add(new ActivityBean(WifiActivity.class, "wifi"));
    mMapList.add(new ActivityBean(SharedActivity.class, "分享"));
    mMapList.add(new ActivityBean(ActivityScanerCode.class, "二维码扫描"));
    mMapList.add(new ActivityBean(LikeActivity.class, "点赞效果"));
    mMapList.add(new ActivityBean(LoginActivity.class, "极光推送登入"));
    mMapList.add(new ActivityBean(RunTextActivity.class, "滚动字幕"));
    mMapList.add(new ActivityBean(ListViewActivity2.class, "ListView"));
    mMapList.add(new ActivityBean(TabLayoutTestActivity.class, "TabLayout"));
    mMapList.add(new ActivityBean(AnimActivity.class, "anim动画"));
    mMapList.add(new ActivityBean(ViewActivity.class, "自定义统计图"));
    mMapList.add(new ActivityBean(RoundProgressActivity.class, "自定义进度图"));
    mMapList.add(new ActivityBean(ThemeChangeActivity.class, "日夜间模式"));
    mMapList.add(new ActivityBean(OnlineFaceDemo.class, "人脸识别"));
    mMapList.add(new ActivityBean(BaseVideoActivity.class, "人脸验证"));
    mMapList.add(new ActivityBean(TestView.class, "测试阴影内容"));
    mMapList.add(new ActivityBean(ChagneImageActivity.class, "生成图片"));
    mMapList.add(new ActivityBean(FillBlackActivity.class, "图片拖拽重叠"));
    mMapList.add(new ActivityBean(BottomNavActivity.class, "底部按钮"));
    mMapList.add(new ActivityBean(WalkActivity.class, "游戏-虫子回家"));
    mMapList.add(new ActivityBean(GameActivity2.class, "游戏-识数水果"));
    mMapList.add(new ActivityBean(CarGameActivity.class, "游戏-汽车识数"));
    mMapList.add(new ActivityBean(FerrisGameActivity.class, "游戏-摩天轮"));
    mMapList.add(new ActivityBean(MyBannerActivity.class, "banner"));
    mMapList.add(new ActivityBean(ViewShare1Activity.class, "共享view"));
    mMapList.add(new ActivityBean(TextActivity.class, "TextView文字"));
    mSimpleAdapter.notifyDataSetChanged();
  }

  /**
   * 透明状态栏
   * 必须在setContentView下面调用
   */
  protected void transitionSystemBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      //透明状态栏
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      //透明导航栏 一些手机如果有虚拟键盘的话,虚拟键盘就会变成透明的,挡住底部按钮点击事件所以,最后不要用
    }
  }

  protected void setStatusBarPaddingAndHeight(View toolBar) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (toolBar != null) {
        int statusBarHeight = (int) getStatusBarHeight(this);
        toolBar.setPadding(toolBar.getPaddingLeft(), statusBarHeight, toolBar.getPaddingRight(),
                toolBar.getPaddingBottom());
        toolBar.getLayoutParams().height =
                statusBarHeight + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45,
                        getResources().getDisplayMetrics());
      }
    }
  }

  private double getStatusBarHeight(Context context) {
    double statusBarHeight = Math.ceil(25 * context.getResources().getDisplayMetrics().density);
    return statusBarHeight;
  }

}
