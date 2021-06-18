package com.example.zhujiang.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.zhujiang.myapplication.animotion.AnimActivity;
import com.example.zhujiang.myapplication.banner.MyBannerActivity;
import com.example.zhujiang.myapplication.bottomnav.BottomNavActivity;
import com.example.zhujiang.myapplication.changeImage.ChagneImageActivity;
import com.example.zhujiang.myapplication.changeImage.WebDataActivity;
import com.example.zhujiang.myapplication.collaspingLayout.CollaspingActivity;
import com.example.zhujiang.myapplication.collaspingLayout.TabLayoutTestActivity;
import com.example.zhujiang.myapplication.drawableSpit.DrawableSpitActivity;
import com.example.zhujiang.myapplication.emoji.EmojiActivity;
import com.example.zhujiang.myapplication.faceonline.BaseVideoActivity;
import com.example.zhujiang.myapplication.faceonline.OnlineFaceDemo;
import com.example.zhujiang.myapplication.fillblack.FillBlackActivity;
import com.example.zhujiang.myapplication.game.GameActivity2;
import com.example.zhujiang.myapplication.game.GameWalkActivity.WalkActivity;
import com.example.zhujiang.myapplication.game.car.CarGameActivity;
import com.example.zhujiang.myapplication.game.textDraw.TextDrawActivity;
import com.example.zhujiang.myapplication.game.wheel.FerrisGameActivity;
import com.example.zhujiang.myapplication.jchat.LoginActivity;
import com.example.zhujiang.myapplication.lifecycle.LifecycleActivity;
import com.example.zhujiang.myapplication.likeView.LikeActivity;
import com.example.zhujiang.myapplication.listView.ListViewActivity2;
import com.example.zhujiang.myapplication.lottie.LottieActivity;
import com.example.zhujiang.myapplication.mask.MaskActivity;
import com.example.zhujiang.myapplication.ontouch.OntouchActivity;
import com.example.zhujiang.myapplication.parent.ParentViewActivity;
import com.example.zhujiang.myapplication.phone.GetPhoneActivity;
import com.example.zhujiang.myapplication.qmui.QmuiWebActivity;
import com.example.zhujiang.myapplication.qrcode.ActivityScanerCode;
import com.example.zhujiang.myapplication.recordmp3.Mp3recordActivity;
import com.example.zhujiang.myapplication.recycler.RecyclerTest;
import com.example.zhujiang.myapplication.runText.RunTextActivity;
import com.example.zhujiang.myapplication.shared.SharedActivity;
import com.example.zhujiang.myapplication.statusBar.StatusBarActivity;
import com.example.zhujiang.myapplication.svg.SvgActivity;
import com.example.zhujiang.myapplication.theme.ThemeChangeActivity;
import com.example.zhujiang.myapplication.utils.DensityHelp;
import com.example.zhujiang.myapplication.utils.SharedPreUser;
import com.example.zhujiang.myapplication.view.RoundProgressActivity;
import com.example.zhujiang.myapplication.view.ViewActivity;
import com.example.zhujiang.myapplication.viewShare.ViewShare1Activity;
import com.example.zhujiang.myapplication.wifi.WifiActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView           mListView;
    private ActivityAdapter    mSimpleAdapter;
    private List<ActivityBean> mMapList = new ArrayList<>();

    private boolean isNight;

    private DrawerLayout   mLayout;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //transitionSystemBar();
        DensityHelp.setOrientationWidth(this, false);
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

                String activityName = mMapList.get(i).getActivityName();
                goActivity(activityName);

                MyApplication.sBean = mMapList.get(i);
                acb = MyApplication.sBean;

                SharedPreUser.getInstance().put(MainActivity.this, "activity", activityName);
            }
        });

        String activityName =
                (String) SharedPreUser.getInstance().get(MainActivity.this, "activity", "");
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
    }

    private void initData() {
        mMapList.add(new ActivityBean(StatusBarActivity.class, "渐变状态栏"));
        mMapList.add(new ActivityBean(GetWheelActivity.class, "时间选择器"));
        mMapList.add(new ActivityBean(WheelActivity.class, "时间选择器2"));
        //mMapList.add(new ActivityBean(PageActivity.class, "recyclerView分页"));
        mMapList.add(new ActivityBean(RecyclerTest.class, "recyclerView"));
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
        mMapList.add(new ActivityBean(WebDataActivity.class, "抓取数据"));

        mMapList.add(new ActivityBean(FillBlackActivity.class, "图片拖拽重叠"));
        mMapList.add(new ActivityBean(BottomNavActivity.class, "底部按钮"));
        mMapList.add(new ActivityBean(WalkActivity.class, "游戏-虫子回家"));
        mMapList.add(new ActivityBean(GameActivity2.class, "游戏-识数水果"));
        mMapList.add(new ActivityBean(CarGameActivity.class, "游戏-汽车识数"));
        mMapList.add(new ActivityBean(FerrisGameActivity.class, "游戏-摩天轮"));
        mMapList.add(new ActivityBean(MyBannerActivity.class, "banner"));
        mMapList.add(new ActivityBean(ViewShare1Activity.class, "共享view"));
        mMapList.add(new ActivityBean(TextActivity.class, "TextView文字"));
        mMapList.add(new ActivityBean(TextDrawActivity.class, "文字绘画"));
        mMapList.add(new ActivityBean(LottieActivity.class, "lottie动画"));
        mMapList.add(new ActivityBean(DrawableSpitActivity.class, "分割图片"));
        mMapList.add(new ActivityBean(Mp3recordActivity.class, "mp3录音"));
        mMapList.add(new ActivityBean(ParentViewActivity.class, "父布局"));
        mMapList.add(new ActivityBean(GetPhoneActivity.class, "获取手机联系人"));
        mMapList.add(new ActivityBean(QmuiWebActivity.class, "qmui WEB"));
        mMapList.add(new ActivityBean(LifecycleActivity.class, "lifecycle"));
        mMapList.add(new ActivityBean(MaskActivity.class, "mask遮罩"));
        mMapList.add(new ActivityBean(OntouchActivity.class, "事件分发"));
        mMapList.add(new ActivityBean(SvgActivity.class, "svg动画"));
        mSimpleAdapter.notifyDataSetChanged();


        int r = 0, g, b;
        int color;
        for (int i =0; i<10000 ; i+=10)  {
            g = i /255;
            b = i%255;
            color = Color.rgb(r, i /255, i%255);
            System.out.println(String.format("颜色值 #%02d%02d%02d   %d ", r, g, b , color));
        }


    }


    ActivityBean acb ;
    @Override
    protected void onStart() {
        super.onStart();

        if (acb != null) {
            Log.w("test", " acb " + acb.getName()  + MyApplication.sBean.getName());
        }

    }
}
