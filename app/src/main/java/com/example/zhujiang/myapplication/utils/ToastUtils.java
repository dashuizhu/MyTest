package com.example.zhujiang.myapplication.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * toast 工具
 *
 * @author zhuj 2018/8/1 下午2:28
 */
public class ToastUtils extends Toast {
    private volatile static Toast mToast;

    private ToastUtils(Context context) {
        super(context);
    }

    private static Toast getToast(Context context) {
        if (mToast == null) {
            synchronized (ToastUtils.class) {
                mToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_LONG);
            }
        }
        return mToast;
    }

    public static void toast(Context context, @StringRes int message) {
        toast(context, context.getString(message));
    }

    public static void toast(Context context, String str) {
        Toast toast = getToast(context);
        toast.setText(str);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}