package com.example.zhujiang.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;
import cn.jpush.im.android.api.JMessageClient;
import com.example.zhujiang.myapplication.floatview.permission.FloatWindowManager;
import com.example.zhujiang.myapplication.utils.DensityHelp;
import com.example.zhujiang.myapplication.utils.DensityUtil;
import com.iflytek.cloud.SpeechUtility;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * @author zhuj 2017/11/15 下午4:31.
 */
public class MyApplication extends Application {

  {

    PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
    PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
  }

  private int count = 0;

  public static boolean isNightMode = false;

  public static ActivityBean sBean;

  @Override public void onCreate() {
    super.onCreate();
    UMShareAPI.get(this);
    Log.i("JMessage", "init");
    JMessageClient.setDebugMode(true);
    JMessageClient.init(this);
    SpeechUtility.createUtility(this, "appid=58c8e767");
    DensityHelp.setDensity(this);
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override
      public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

      }

      @Override
      public void onActivityStarted(Activity activity) {
        if (count==0) {
          //FloatWindowManager.getInstance().resume(activity);
        }
          count++;
      }

      @Override
      public void onActivityResumed(Activity activity) {

      }

      @Override
      public void onActivityPaused(Activity activity) {

      }

      @Override
      public void onActivityStopped(Activity activity) {
        count--;
        if (count == 0) {
          //FloatWindowManager.getInstance().pause();
        }
      }

      @Override
      public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

      }

      @Override
      public void onActivityDestroyed(Activity activity) {

      }
    });

  }

  static {
    //设置全局的Header构建器
    SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
      @Override
      public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
        return new ClassicsHeader(context).setTextSizeTime(12)
                .setTextSizeTitle(13)
                .setSpinnerStyle(SpinnerStyle.Translate);
      }
    });
    //设置全局的Footer构建器
    SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
      @Override
      public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
        //指定为经典Footer，默认是 BallPulseFooter
        return new ClassicsFooter(context).setTextSizeTitle(13).setSpinnerStyle(SpinnerStyle.Translate);
      }
    });
  }


  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }
}
