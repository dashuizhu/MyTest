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
        //System.out.printf(""+Math.sin(Math.toRadians(90)));


        long value = 0;
        long time1 =System.currentTimeMillis();
        for (int i=0; i<10000* 10000; i++) {
            value +=i;
        }
        System.out.println(" " + (System.currentTimeMillis() - time1));

    }
}