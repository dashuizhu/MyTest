package com.example.zhujiang.myapplication;

import android.util.ArraySet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author zhuj 2018/7/31 下午5:17.
 */
public class MainActivityTest {


    private void test() {
        List list = new ArrayList();
        list.add(0);
        list.add(1);
        list.add(2);


        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (1 == (int)it.next()) {
                it.remove();
            }
        }
        System.out.println(list.size());

    }

}