package com.example.zhujiang.myapplication;

import android.graphics.Color;
import android.util.ArraySet;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.Test;

/**
 * @author zhuj 2019-10-21 20:23.
 */
public class ListTest {


    @Test
    public void onTextColor() {
        System.out.println(" " + 0xFF000000);
        System.out.println(" " + (0xFF0000FF - 0xFF000000));
        System.out.println(" " + 0x7eFFFFFF);
        System.out.println(" " + Integer.MAX_VALUE);
    }


    @Test
    public void onTest() {

        List<Integer> list = new ArrayList();
        list.add(0);
        list.add(1);
        list.add(2);


        Iterator it = list.iterator();
        if (it.hasNext()) {
            it.next();
            it.remove();

        }
        for (int a : list) {
            System.out.println(""+a);
        }
    }


    @Test
    public void test2() {
        List<Integer> list = new ArrayList();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(1);
        list.add(1);
        list.add(3);

        HashSet set = new HashSet();


        for (int i=0;i<list.size(); i++) {
            if (set.contains(list.get(i))) {
                list.remove(i);
                i --;
            } else {
                set.add(list.get(i));
            }
        }
        for (int a : list) {
            System.out.println(""+a);
        }

    }
}
