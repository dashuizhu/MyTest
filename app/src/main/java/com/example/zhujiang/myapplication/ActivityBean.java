package com.example.zhujiang.myapplication;

import android.app.Activity;
import lombok.Data;

/**
 * @author zhuj 2017/8/24 上午11:18.
 */
@Data
public class ActivityBean {

  private String name;
  private String activityName;

  public ActivityBean(Class<? extends Activity> activity, String name ) {
    this.name = name;
    activityName = activity.getName();
  }
}
