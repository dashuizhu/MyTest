package com.example.zhujiang.myapplication.floatview.permission;

import android.content.Context;
import android.widget.Toast;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuj 2018/4/16 上午9:37.
 */
public class MyManager {

  private List<String> mList = new ArrayList<>();

  private final int mDelayTime = 20;

  private static volatile MyManager sManager;

  private MyManager() {}

  public static MyManager getInstance() {
    if (sManager == null) {
      synchronized (MyManager.class) {
        if (sManager == null) {
          sManager = new MyManager();
        }
      }
    }
    return sManager;
  }

  public void addShow(String list) {
    FloatWindowManager.getInstance().addShowText(list);
  }

  public void show(final Context context) {
    FloatWindowManager.getInstance().applyOrShowFloatWindow(context, "xxx");
    FloatWindowManager.getInstance().setOnClickListener(new FloatViewLayout.OnMyViewClickListener() {
      @Override
      public void onClick(String str) {
        Toast.makeText(context, "点击"+str, Toast.LENGTH_LONG).show();
        //mList.remove(0);
        //FloatWindowManager.getInstance().dismissWindow();
        //if (mList.size()>0) {
        //  Observable.timer(2600, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
        //          .subscribe(new Consumer<Long>() {
        //            @Override
        //            public void accept(Long aLong) throws Exception {
                      FloatWindowManager.getInstance().showNext();
        //            }
        //          });
        //
        //  //FloatWindowManager.getInstance().setText(mList.get(0));
        //} else {
        //}
      }
    });
  }



}
