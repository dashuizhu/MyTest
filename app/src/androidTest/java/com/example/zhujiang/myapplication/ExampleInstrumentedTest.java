package com.example.zhujiang.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        for (int i=0;i<255;i++) {
            for (int j =0; j <255; j++) {
                System.out.println(""+ Color.parseColor("#6bbbec"));
            }
        }


        assertEquals("com.example.zhujiang.myapplication", appContext.getPackageName());
    }
}
