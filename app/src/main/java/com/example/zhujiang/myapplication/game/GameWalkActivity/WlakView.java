package com.example.zhujiang.myapplication.game.GameWalkActivity;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * @author zhuj 2018/9/12 下午3:04.
 */
public class WlakView extends AppCompatTextView {

    private int value;
    private int step;

    public WlakView(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
    }

    public WlakView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        setText(String.valueOf(value));
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

}
