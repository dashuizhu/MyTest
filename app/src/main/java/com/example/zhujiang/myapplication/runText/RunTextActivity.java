package com.example.zhujiang.myapplication.runText;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;
import java.util.ArrayList;
import java.util.Observable;

public class RunTextActivity extends AppCompatActivity {

  @BindView(R.id.tv_run) RxTextViewVertical mTvRun;
  @BindView(R.id.cb_box) CheckBox mCbBox;
  @BindView(R.id.layout) LinearLayout mLayout;

  private ArrayList<String> titleList = new ArrayList<>();

  int[] locations = new int[2];
  private boolean isFirst = true;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_run_text);
    ButterKnife.bind(this);

    titleList.add("你是天上最受宠的一架钢琴");
    titleList.add("我是丑人脸上的鼻涕");
    titleList.add("你发出完美的声音");
    titleList.add("我被默默揩去");
    titleList.add("你冷酷外表下藏着诗情画意");
    titleList.add("我已经够胖还吃东西");
    titleList.add("你踏着七彩祥云离去");
    titleList.add("我被留在这里");
    mTvRun.setTextList(titleList);
    mTvRun.setText(20, 5, 0xff766156);//设置属性
    mTvRun.setTextStillTime(3000);//设置停留时长间隔
    mTvRun.setAnimTime(300);//设置进入和退出的时间间隔
    mTvRun.setOnItemClickListener(new RxTextViewVertical.OnItemClickListener() {
      @Override public void onItemClick(int position) {
        Snackbar.make(mTvRun, "那 " + titleList.get(position), Snackbar.LENGTH_LONG).show();
        //Toast.makeText(RunTextActivity, "点击了 : " + titleList.get(position), Toast.LENGTH_SHORT, true).show();
      }
    });


    //mTvRun.setInAnimation(AnimationUtils.loadAnimation(RunTextActivity.this, R.anim.push_left_in));
    //mTvRun.setOutAnimation(AnimationUtils.loadAnimation(RunTextActivity.this, R.anim.push_left_in));

    //mLayout.setAnimTime(2000);

    mCbBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isFirst) {
          isFirst = false;
          mCbBox.getLocationOnScreen(locations);
          Log.w("test", " " + locations[0] + " y:" +locations[1]);
        }

        Animation animation;
        TranslateAnimation animation2;
        if (isChecked) {
          animation = AnimationUtils.loadAnimation(RunTextActivity.this, R.anim.push_right_in);
          animation2 = new TranslateAnimation(0 , locations[0], 0, 0);
        } else {
          animation = AnimationUtils.loadAnimation(RunTextActivity.this, R.anim.push_left_out);
          animation2 = new TranslateAnimation(0 , -locations[0], 0, 0);
        }


        animation.setAnimationListener(new Animation.AnimationListener() {
          @Override public void onAnimationStart(Animation animation) {
            Log.w("test", "动画onAnimationStart");
          }

          @Override public void onAnimationEnd(Animation animation) {
            Log.w("test", "动画结束");
            mLayout.clearAnimation();
            if (mCbBox.isChecked()) {
              mLayout.setVisibility(View.VISIBLE);
            } else {
              mLayout.setVisibility(View.GONE);
            }
          }

          @Override public void onAnimationRepeat(Animation animation) {

          }
        });
        animation2.setAnimationListener(new Animation.AnimationListener() {
          @Override public void onAnimationStart(Animation animation) {

          }

          @Override public void onAnimationEnd(Animation animation) {
            //避免闪烁
            mCbBox.clearAnimation();
          }

          @Override public void onAnimationRepeat(Animation animation) {

          }
        });
        animation.setDuration(2000);
        //animation.setFillAfter(true);
        animation2.setDuration(2000);
        //animation2.setFillAfter(true);

        mCbBox.startAnimation(animation2);
        mLayout.startAnimation(animation);
      }
    });


    //TranslateAnimation animation = new TranslateAnimation(0, 10, 0, -2);
    //animation.setInterpolator(new OvershootInterpolator());
    //animation.setDuration(200);
    //animation.setRepeatCount(5);
    ////animation.setStartOffset(3000);
    //animation.setRepeatMode(Animation.INFINITE);
    //findViewById(R.id.iv_face).startAnimation(animation);

    //Animation animation = AnimationUtils.loadAnimation(RunTextActivity.this, R.anim.shake);
    //findViewById(R.id.iv_face).startAnimation(animation);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        ObjectAnimator animator = tada(findViewById(R.id.iv_face), 1L);
        //animator.setStartDelay(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();

      }
    }, 1000);

  }

  @Override protected void onResume() {
    super.onResume();
    mTvRun.startAutoScroll();
  }

  @Override protected void onPause() {
    super.onPause();
    mTvRun.stopAutoScroll();
  }

  public static ObjectAnimator tada(View view, float shakeFactor) {

    PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
            Keyframe.ofFloat(0f, 1f),
            Keyframe.ofFloat(.7f, .9f),
            Keyframe.ofFloat(.8f, 1.1f),
            Keyframe.ofFloat(1f, 1f)
    );

    PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
            Keyframe.ofFloat(0f, 1f),
            Keyframe.ofFloat(.7f, .9f),
            Keyframe.ofFloat(.8f, 1.1f),
            Keyframe.ofFloat(1f, 1f)
    );

    PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.7f, 0f),
            Keyframe.ofFloat(.73f, -30f * shakeFactor),
            Keyframe.ofFloat(.76f, -30f * shakeFactor),
            Keyframe.ofFloat(.79f, 30f * shakeFactor),
            Keyframe.ofFloat(.82f, -30f * shakeFactor),
            Keyframe.ofFloat(.85f, 30f * shakeFactor),
            Keyframe.ofFloat(.88f, -20f * shakeFactor),
            Keyframe.ofFloat(.92f, 20f * shakeFactor),
            Keyframe.ofFloat(.94f, -10f * shakeFactor),
            Keyframe.ofFloat(.97f, 10f * shakeFactor),
            Keyframe.ofFloat(1f, 0)
    );

    return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).
            setDuration(3000);
  }
}
