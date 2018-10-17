package com.example.zhujiang.myapplication;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * 多人过河， 只有一条船，不同的人过河时间不一致，
 * 船一次只能坐两个人， 时间按 大的那个人算。
 * 过河后，必须再回来一个人，搭下个人一起过河
 * 求过河的最短时间
 *
 * @author zhuj 2018/4/3 下午2:14.
 */
public class Run {

  @Test
  public void main() {
    //start(2, 1, 3, 5, 6, 7, 8);
    //start(1, 8, 9, 10, 11, 12,13);
    start(1, 8, 9, 10, 11);
    //start(2, 1, 3, 5, 6, 7);
    //start(2, 1, 3, 5, 6, 7);
  }

  private <T extends Integer> int start(T... object) {

    int total = 0;

    //进行排序， 从低到高排序
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < object.length; i++) {

      int now = object[i];
      add(list, now);
    }
    //list.parallelStream().forEach((s)->System.out.print("x " +s));

    //判断算法方案
    if (list.get(0) + list.get(list.size() - 2) <= 2 * list.get(1)) {
      System.out.println("方案 1来回传递");
      for (int i = list.size() - 1; i >= 0; i--) {
        total += list.get(i);
        total += list.get(0);
        if (i > 1) {
          times(list.get(0), list.get(i), list.get(0));
        } else {
          times(list.get(0), list.get(i), null);
          break;
        }
      }
    } else {

      //2 1 4 2 2
      //2 1 5 2 3 1 2
      //2 1 6 2 2 1 4 2 2
      //2 1 7 2 2 1 5 2 3 1 2
      //2 1 8 2 2 1 6 2 2 1 4 2 2
      System.out.println("方案 最大的一起过去， 1、2来回传递");
      int size = list.size();
      while (size >= 4) {
        if (size == 4) {
          times(list.get(0), list.get(1), list.get(0));
          times(list.get(3), list.get(2), list.get(1));
          times(list.get(0), list.get(1), null);
          total += 3 * list.get(1) + list.get(0) + list.get(3);
          break;
        } else if (size == 5) {
          times(list.get(0), list.get(1), list.get(0));
          times(list.get(4), list.get(3), list.get(1));
          times(list.get(0), list.get(2), list.get(0));
          times(list.get(0), list.get(1), null);
          total += 3 * list.get(1) + 2 * list.get(0) + list.get(2) + list.get(4);
          break;
        } else {
          times(list.get(0), list.get(1), list.get(0));
          times(list.get(size - 1), list.get(size - 2), list.get(1));
          total += 2 * list.get(1) + list.get(0) + list.get(list.size() - 1);
          size -= 2;
        }
      }
    }

    System.out.println("总时间" + total);
    return total;
  }

  private void add(List<Integer> list, int value) {
    for (int j = 0; j < list.size(); j++) {
      if (value >= list.get(j)) {
        list.add(value);
        return;
      }
    }
    list.add(0, value);
  }

  //private class Person {
  //  private String name;
  //  private int time;
  //
  //  public Person(String name, int time) {
  //    this.name = name;
  //    this.time = time;
  //  }
  //
  //  public String getName() {
  //    return name;
  //  }
  //
  //  public int {
  //    return time;
  //  }
  //}

  private void times(int p1, int p2, Integer back) {
    System.out.println("过去 " + p1 + "," + p2 + "   时间:" + Math.max(p1, p2));
    if (back != null) {
      System.out.println("回来 " + back + "     时间:" + back);
    }
  }
}
