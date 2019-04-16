package com.example.zhujiang.myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuj 2018/5/2 下午6:25.
 */
public class MyTest  {

  public  static void main(String[] args) {
    System.out.println("asdf");
    MyTest test = new MyTest();
    List list1 = test.fizzBuzz2(19);
    List list2 = test.fizzBuzz(19);
    boolean isRight;
    for (int i=0; i<19; i++) {
      if (!list1.get(i).equals(list2.get(i))) {
        System.out.println(list1.get(i) + " " +i+" " + list2.get(i));
      }
    }
  }

  public List<String> fizzBuzz(int n) {
    // write your code here
    String[] arr = new String[]{null, null,"fizz",null,"buzz","fizz",null,null,"fizz","buzz",null,"fizz",null,null,"fizz buzz"};
    List<String> list = new ArrayList<String>();
    for (int i=0; i<n; i++) {
      String str = arr[i%arr.length];
      if(str == null) {
        list.add(""+(i+1));
      } else {
        list.add( str);
      }
    }
    return list;
  }

  public List<String> fizzBuzz2(int n) {
     List<String> list = new ArrayList<String>();
    for (int i=0; i<n; i++) {
     list.add(String.valueOf(i+1));
    }
    for (int i=1; i <= (n/3); i++) {
      //list.remove(i*3);
      list.set(i*3 - 1,  "fizz");
    }
    for (int i=1; i <= n/5; i++) {
      //list.remove(i*5);
      list.set(i*5 -1, "buzz");
    }
    for (int i=1; i <= n/15; i++) {
      //list.remove(i*15);
      list.set(i*15 -1, "fizz buzz");
    }
    return list;
  }


}
