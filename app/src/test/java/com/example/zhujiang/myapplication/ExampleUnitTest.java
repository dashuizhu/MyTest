package com.example.zhujiang.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        //assertEquals(4, 2 + 2);
        //System.out.printf(""+Math.sin(Math.toRadians(90)))
        System.out.println("xxxa".startsWith("xxxaee"));
        System.out.println("xxxxxa".startsWith("xxx",1));

        long value = 0;
        long time1 =System.currentTimeMillis();
        for (int i=0; i<10000* 10000; i++) {
            value +=i;
        }
        System.out.println(" " + (System.currentTimeMillis() - time1));

    }

    @Test
    public void climbStairs() {
        int n = 25;
        //2的最大个数
        int max2Size = n/2;
        int value =0, newV;
        for (int i=0; i<=max2Size; i++) {
            System.out.println();

            newV = getValue(n-i , i);
            value += newV;
            System.out.println(String.format("%d - %d --> %d  value=%d", (n-i), i, newV, value));
        }
    }

    private int getValue(int n, int count) {
        if (count == 0) {
            return 1;
        }
        if (count > n/2) {
            count = (n-count);
        }

        int value = 1, sum = 1;
        for (int i=0;i<count; i++) {
            value = value * (n -i);
            sum = sum * (count -i);
        }
        return value / sum;
    }
}