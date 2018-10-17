package com.example.zhujiang.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BinaryOperator;
import org.junit.Test;

/**
 * @author zhuj 2018/5/2 下午5:22.
 */
public class HuiwenTest {

  @Test
  public void testMain() {
    String[] array = new String[] { "asdf", "sdfa", "wea", "awe", "waw", "bac", "babbc", "bcbab", "b", "a", "b" };

    List<String> list = new ArrayList();

    list.add(array[0]);


    int count = 0;

    "asdf".substring(1, 3);


    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt(); //第一行为单词个数n(1 ≤ n ≤ 50)
    System.out.println("xxx " +n);

    //String str = sc.next();

    //for (int i = 1; i < array.length; i++) {
    //
    //  String str = array[i];

    //  int listSize = list.size();
    //  for (int k = 0; k < listSize; k++) {
    //    String ss = list.get(k);
    //    if (ss.length() == str.length()) {
    //
    //      //长度一直，就循环位，比较
    //      for (int j = 0; j < ss.length(); j++) {
    //        String newS = ss.substring(j, ss.length()) + ss.substring(0, j);
    //        if (newS.equals(str)) {
    //          count++;
    //          k = listSize;
    //          break;
    //        }
    //        if (j == ss.length() - 1 && k==listSize-1) {
    //          //没有匹配上的，就重新加入到匹配池
    //          list.add(str);
    //          break;
    //        }
    //      }
    //    } else if (k == listSize - 1) {
    //      //没有匹配上的，就重新加入到匹配池
    //      list.add(str);
    //      break;
    //    }
    //  }
    ////}
    //
    //for (String s : list) {
    //  System.out.println(s);
    //}
    System.out.println("count " + count);
  }
}
