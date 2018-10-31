package com.example.zhujiang.myapplication.game.GameWalkActivity;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;
import com.example.zhujiang.myapplication.R;

/**
 * @author zhuj 2018/9/12 下午3:04.
 */
public class WlakView extends AppCompatImageView {

    private int value;
    private int step;

    public WlakView(Context context) {
        super(context);
        //setGravity(Gravity.CENTER);
    }

    public WlakView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;

        //setText(String.valueOf(value));
        drawView();
    }

    public void drawView() {
        int resId;
        switch (value) {
            case 0:
                resId = R.mipmap.fruit_1;
                break;
            case 1:
                resId = R.mipmap.fruit_2;
                break;
            case 2:
                resId = R.mipmap.fruit_3;
                break;
            case 3:
            default:
                resId = R.mipmap.fruit_4;
                break;

        }
        setImageResource(resId);
    }

    public int getStep() {
        return step;
    }

    /**
     * 0表示正常，  1表示走过， 2表示当前
     * @param step
     */
    public void setStep(int step) {
        this.step = step;
        //int resId;
        //switch (value) {
        //    case 0:
        //    default:
        //        resId = R.drawable.bg_game_walk_step0;
        //        break;
        //    case 1:
        //        resId = R.drawable.bg_game_walk_step1;
        //        break;
        //    case 2:
        //        resId = R.drawable.bg_game_walk_step2;
        //        break;
        //
        //}
        //setBackgroundResource(resId);
    }

}
