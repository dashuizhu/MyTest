package com.example.zhujiang.myapplication.svg;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.zhujiang.myapplication.R;

public class SvgActivity extends AppCompatActivity {

    ImageView mIv;
    TextView  mTvColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        mIv = findViewById(R.id.iv_svg);
        mTvColor = findViewById(R.id.tv_color);
        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((Animatable) mIv.getDrawable()).start();

                onbgColorChange();


            }
        });
        //((Animatable)iv.getDrawable()).start();
    }

    private void onbgColorChange() {
        ValueAnimator animator = ObjectAnimator.ofInt(mIv, "backgroundColor", 0xff000000, 0xff4488FF, 0xffffffff, 0xFFA1238);//对背景色颜色进行改变，操作的属性为"backgroundColor",此处必须这样写，不能全小写,后面的颜色为在对应颜色间进行渐变
        animator.setDuration(20000);
        animator.setEvaluator(new ArgbEvaluator());//如果要颜色渐变必须要ArgbEvaluator，来实现颜色之间的平滑变化，否则会出现颜色不规则跳动
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                String str = String.format("%06x ", value) + " " + value;
                //mIv.setBackgroundColor(value);
                Log.w("test", str);
                mTvColor.setText(str);
        //        //mTvColor.setTextColor(value);
            }
        });
    }
}
