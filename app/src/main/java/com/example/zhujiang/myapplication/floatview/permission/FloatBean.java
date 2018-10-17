package com.example.zhujiang.myapplication.floatview.permission;

import lombok.Data;

/**
 * @author zhuj 2018/4/17 下午5:46.
 */
@Data
public class FloatBean {

  public FloatBean(String name, int delay) {
    this.name = name;
    this.delay = delay;
  }

  private String name;
  private int delay;

}
