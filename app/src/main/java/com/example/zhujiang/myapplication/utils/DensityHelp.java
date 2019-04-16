package com.example.zhujiang.myapplication.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * 修改density 屏幕适配方案
 *
 * @author zhuj 2018/8/22 上午11:13
 */
public class DensityHelp {

    /**
     * 设计图的宽度， 6sp尺寸
     */
    private final static float DESIGN_WIDTH = 375f;
    /**
     * 设计图的高度, 6sp尺寸
     */
    private final static float DESIGN_HEIGHT = 667f;

    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;
    private static int barHeight;

    public static void setDensity(@NonNull final Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        //获取状态栏高度
        //barHeight = getStatusBarHeight(application);

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后,将appScaledDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaledDensity =
                                application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {
                }
            });
        }
    }

    /**
     * 此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好了)
     * 默认适配方向 宽度
     * @param activity
     */
    //public static void setDefault(Activity activity) {
    //    setAppOrientation(activity, "width");
    //}

    /**
     * 更改适配方向
     * @param activity
     */
    public static void setOrientationWidth(Activity activity, boolean isLand) {
        setAppOrientation(activity, "width", isLand);
    }

    public static void setOrientationHeight(Activity activity, boolean isLand) {
        setAppOrientation(activity, "height", isLand);
    }

    /**
     * targetDensity
     * targetScaledDensity
     * targetDensityDpi
     * 这三个参数是统一修改过后的值
     * <p>
     * orientation:方向值,传入width或height
     */
    private static void setAppOrientation(@Nullable Activity activity, String orientation, boolean isLand) {

        float targetDensity;


        if (orientation.equals("height")) {
            float tager = isLand ? DESIGN_WIDTH : DESIGN_HEIGHT;
            targetDensity = (appDisplayMetrics.heightPixels - barHeight) / tager;
        } else {
            float tager = isLand ? DESIGN_HEIGHT : DESIGN_WIDTH;
            targetDensity = appDisplayMetrics.widthPixels / tager;
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        /**
         *
         * 最后在这里将修改过后的值赋给系统参数
         *
         * 只修改Activity的density值
         */

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId =
                context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
