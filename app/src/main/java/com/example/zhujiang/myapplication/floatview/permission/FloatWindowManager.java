/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.example.zhujiang.myapplication.floatview.permission;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import com.example.zhujiang.myapplication.floatview.permission.rom.HuaweiUtils;
import com.example.zhujiang.myapplication.floatview.permission.rom.MeizuUtils;
import com.example.zhujiang.myapplication.floatview.permission.rom.MiuiUtils;
import com.example.zhujiang.myapplication.floatview.permission.rom.OppoUtils;
import com.example.zhujiang.myapplication.floatview.permission.rom.QikuUtils;
import com.example.zhujiang.myapplication.floatview.permission.rom.RomUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author zhaozp
 * @since 2016-10-17
 */

public class FloatWindowManager {
  private static final String TAG = "FloatWindowManager";

  private static volatile FloatWindowManager instance;

  private boolean isWindowDismiss = true;
  private WindowManager windowManager = null;
  private WindowManager.LayoutParams mParams = null;
  private FloatViewLayout floatView = null;
  private Dialog dialog;

  public static FloatWindowManager getInstance() {
    if (instance == null) {
      synchronized (FloatWindowManager.class) {
        if (instance == null) {
          instance = new FloatWindowManager();
        }
      }
    }
    return instance;
  }

  private final int DELAY_TIME = 10;

  public void applyOrShowFloatWindow(Context context, String text) {
    if (checkPermission(context)) {
      showWindow(context, text);
      startAnim();
    } else {
      applyPermission(context);
    }
  }

  private boolean checkPermission(Context context) {
    //6.0 版本之后由于 google 增加了对悬浮窗权限的管理，所以方式就统一了
    if (Build.VERSION.SDK_INT < 23) {
      if (RomUtils.checkIsMiuiRom()) {
        return miuiPermissionCheck(context);
      } else if (RomUtils.checkIsMeizuRom()) {
        return meizuPermissionCheck(context);
      } else if (RomUtils.checkIsHuaweiRom()) {
        return huaweiPermissionCheck(context);
      } else if (RomUtils.checkIs360Rom()) {
        return qikuPermissionCheck(context);
      } else if (RomUtils.checkIsOppoRom()) {
        return oppoROMPermissionCheck(context);
      }
    }
    return commonROMPermissionCheck(context);
  }

  private boolean huaweiPermissionCheck(Context context) {
    return HuaweiUtils.checkFloatWindowPermission(context);
  }

  private boolean miuiPermissionCheck(Context context) {
    return MiuiUtils.checkFloatWindowPermission(context);
  }

  private boolean meizuPermissionCheck(Context context) {
    return MeizuUtils.checkFloatWindowPermission(context);
  }

  private boolean qikuPermissionCheck(Context context) {
    return QikuUtils.checkFloatWindowPermission(context);
  }

  private boolean oppoROMPermissionCheck(Context context) {
    return OppoUtils.checkFloatWindowPermission(context);
  }

  private boolean commonROMPermissionCheck(Context context) {
    //最新发现魅族6.0的系统这种方式不好用，天杀的，只有你是奇葩，没办法，单独适配一下
    if (RomUtils.checkIsMeizuRom()) {
      return meizuPermissionCheck(context);
    } else {
      Boolean result = true;
      if (Build.VERSION.SDK_INT >= 23) {
        try {
          Class clazz = Settings.class;
          Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
          result = (Boolean) canDrawOverlays.invoke(null, context);
        } catch (Exception e) {
          Log.e(TAG, Log.getStackTraceString(e));
        }
      }
      return result;
    }
  }

  private void applyPermission(Context context) {
    if (Build.VERSION.SDK_INT < 23) {
      if (RomUtils.checkIsMiuiRom()) {
        miuiROMPermissionApply(context);
      } else if (RomUtils.checkIsMeizuRom()) {
        meizuROMPermissionApply(context);
      } else if (RomUtils.checkIsHuaweiRom()) {
        huaweiROMPermissionApply(context);
      } else if (RomUtils.checkIs360Rom()) {
        ROM360PermissionApply(context);
      } else if (RomUtils.checkIsOppoRom()) {
        oppoROMPermissionApply(context);
      }
    }
    commonROMPermissionApply(context);
  }

  private void ROM360PermissionApply(final Context context) {
    showConfirmDialog(context, new OnConfirmResult() {
      @Override
      public void confirmResult(boolean confirm) {
        if (confirm) {
          QikuUtils.applyPermission(context);
        } else {
          Log.e(TAG, "ROM:360, user manually refuse OVERLAY_PERMISSION");
        }
      }
    });
  }

  private void huaweiROMPermissionApply(final Context context) {
    showConfirmDialog(context, new OnConfirmResult() {
      @Override
      public void confirmResult(boolean confirm) {
        if (confirm) {
          HuaweiUtils.applyPermission(context);
        } else {
          Log.e(TAG, "ROM:huawei, user manually refuse OVERLAY_PERMISSION");
        }
      }
    });
  }

  private void meizuROMPermissionApply(final Context context) {
    showConfirmDialog(context, new OnConfirmResult() {
      @Override
      public void confirmResult(boolean confirm) {
        if (confirm) {
          MeizuUtils.applyPermission(context);
        } else {
          Log.e(TAG, "ROM:meizu, user manually refuse OVERLAY_PERMISSION");
        }
      }
    });
  }

  private void miuiROMPermissionApply(final Context context) {
    showConfirmDialog(context, new OnConfirmResult() {
      @Override
      public void confirmResult(boolean confirm) {
        if (confirm) {
          MiuiUtils.applyMiuiPermission(context);
        } else {
          Log.e(TAG, "ROM:miui, user manually refuse OVERLAY_PERMISSION");
        }
      }
    });
  }

  private void oppoROMPermissionApply(final Context context) {
    showConfirmDialog(context, new OnConfirmResult() {
      @Override
      public void confirmResult(boolean confirm) {
        if (confirm) {
          OppoUtils.applyOppoPermission(context);
        } else {
          Log.e(TAG, "ROM:miui, user manually refuse OVERLAY_PERMISSION");
        }
      }
    });
  }

  /**
   * 通用 rom 权限申请
   */
  private void commonROMPermissionApply(final Context context) {
    //这里也一样，魅族系统需要单独适配
    if (RomUtils.checkIsMeizuRom()) {
      meizuROMPermissionApply(context);
    } else {
      if (Build.VERSION.SDK_INT >= 23) {
        showConfirmDialog(context, new OnConfirmResult() {
          @Override
          public void confirmResult(boolean confirm) {
            if (confirm) {
              try {
                commonROMPermissionApplyInternal(context);
              } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
              }
            } else {
              Log.d(TAG, "user manually refuse OVERLAY_PERMISSION");
              //需要做统计效果
            }
          }
        });
      }
    }
  }

  public static void commonROMPermissionApplyInternal(Context context)
          throws NoSuchFieldException, IllegalAccessException {
    Class clazz = Settings.class;
    Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");

    Intent intent = new Intent(field.get(null).toString());
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setData(Uri.parse("package:" + context.getPackageName()));
    context.startActivity(intent);
  }

  private void showConfirmDialog(Context context, OnConfirmResult result) {
    showConfirmDialog(context, "您的手机没有授予悬浮窗权限，请开启后再试", result);
  }

  private void showConfirmDialog(Context context, String message, final OnConfirmResult result) {
    if (dialog != null && dialog.isShowing()) {
      dialog.dismiss();
    }

    dialog = new AlertDialog.Builder(context).setCancelable(true)
            .setTitle("")
            .setMessage(message)
            .setPositiveButton("现在去开启", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                result.confirmResult(true);
                dialog.dismiss();
              }
            })
            .setNegativeButton("暂不开启", new DialogInterface.OnClickListener() {

              @Override
              public void onClick(DialogInterface dialog, int which) {
                result.confirmResult(false);
                dialog.dismiss();
              }
            })
            .create();
    dialog.show();
  }

  public void setOnClickListener(FloatViewLayout.OnMyViewClickListener onClickListener) {
    floatView.setOnMyViewClickListener(onClickListener);
  }

  public void setText(String text) {
    floatView.setContetn(text);
  }

  private interface OnConfirmResult {
    void confirmResult(boolean confirm);
  }

  private void showWindow(Context context, String text) {
    if (!isWindowDismiss) {
      Log.e(TAG, "view is already added here");
      return;
    }

    isWindowDismiss = false;
    if (windowManager == null) {
      windowManager = (WindowManager) context.getApplicationContext()
              .getSystemService(Context.WINDOW_SERVICE);
    }
    if (floatView == null) {

      Point size = new Point();
      windowManager.getDefaultDisplay().getSize(size);
      int screenWidth = size.x;
      int screenHeight = size.y;

      mParams = new WindowManager.LayoutParams();
      mParams.packageName = context.getPackageName();
      mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
      mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
      mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
              | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
              | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
              | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
      mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
      mParams.format = PixelFormat.RGBA_8888;
      mParams.gravity = Gravity.TOP;
      //mParams.x = screenWidth - dp2px(context, 100);
      //mParams.y = screenHeight - dp2px(context, 171);

      //        ImageView imageView = new ImageView(mContext);
      //        imageView.setImageResource(R.drawable.app_icon);
      floatView = new FloatViewLayout(context);
      //floatView.setParams(mParams);
      //floatView.setIsShowing(true);
    }
    windowManager.addView(floatView, mParams);
    floatView.setContetn(text);
    floatView.show();
    floatView.setClickable(true);
  }

  public void dismissWindow() {
    if (isWindowDismiss) {
      Log.e(TAG, "window can not be dismiss cause it has not been added");
      return;
    }

    isWindowDismiss = true;
    //floatView.setIsShowing(false);
    if (windowManager != null && floatView != null) {
      windowManager.removeViewImmediate(floatView);
    }
    isShow = false;
    //floatView.setClickable(false);
    //floatView.dismiss();
    //Observable.timer(2500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
    //        .subscribe(new Consumer<Long>() {
    //            @Override
    //            public void accept(Long aLong) throws Exception {
    //                if (windowManager != null && floatView != null) {
    //                    windowManager.removeViewImmediate(floatView);
    //                }
    //            }
    //        });
  }

  public void showNext() {
    startAnim();
    Observable.timer(1500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Long>() {
              @Override
              public void onSubscribe(Disposable d) {
                //mDelayDisposable = d;
              }

              @Override
              public void onNext(Long aLong) {
                list.remove(0);
                if (list.size() > 0) {
                  floatView.setContetn(list.get(0).getName());
                  startAnim();
                }
              }

              @Override
              public void onError(Throwable e) {

              }

              @Override
              public void onComplete() {

              }
            });
  }

  private int dp2px(Context context, float dp) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  private boolean isShow = false;

  public void startAnim() {
    if (floatView != null) {
      if (isShow) {
        ObjectAnimator.ofFloat(floatView, "translationY", 0, -500).setDuration(1500).start();
      } else {
        startDelay();
        ObjectAnimator.ofFloat(floatView, "translationY", -500, 0).setDuration(1500).start();
      }
      isShow = !isShow;
    }
  }

  List<FloatBean> list = new ArrayList<>();

  public void addShowText(String text) {
    list.add(new FloatBean(text, 10));
  }
  public void addShowText(String text, int time) {
    list.add(new FloatBean(text, time));
  }

  Disposable mDelayDisposable;

  private void disDelay() {
    if (mDelayDisposable != null) {
      if (!mDelayDisposable.isDisposed()) {
        mDelayDisposable.dispose();
      }
      mDelayDisposable = null;
    }
  }

  private void startDelay() {
    disDelay();
    FloatBean floatBean = list.get(0);
    list.remove(0);
    mDelayDisposable = Observable.timer(floatBean.getDelay(), TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Long>() {
              @Override
              public void accept(Long aLong) throws Exception {
                showNext();
              }
            });
  }

  public void pause() {
    //disDelay();
    //if (list.size()>0) {
    //  list.remove(0);
    //}
    //dismissWindow();
    floatView.setVisibility(View.GONE);
  }

  public void resume(Context context) {
    //if (list.size() > 0 && isWindowDismiss) {
    //  showWindow(context, list.get(0));
    //  startAnim();
    //}
    floatView.setVisibility(View.VISIBLE);
  }
}
