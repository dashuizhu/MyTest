package com.example.zhujiang.myapplication.animotion;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;

public class AnimActivity extends AppCompatActivity {

  @BindView(R.id.tv_1) TextView mTv1;
  @BindView(R.id.tv_2) TextView mTv2;
  @BindView(R.id.tv_3) TextView mTv3;
  @BindView(R.id.layout) RelativeLayout mLayout;
  @BindView(R.id.cb_box) CheckBox mCbBox;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_anim);
    ButterKnife.bind(this);
    initViews();
  }

  private void initViews() {
    mCbBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        startAnim(isChecked);
      }
    });
  }

  private void startAnim(final boolean isShow) {
    ObjectAnimator objectAnimator = isShow ? big() : small();

    objectAnimator.setDuration(1000);
    //objectAnimator.setRepeatCount(ValueAnimator.RESTART);
    objectAnimator.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {
        Log.w("aa", "动画开始");
        //动画开始，不许在点击
        mCbBox.setClickable(false);
        //mLayout.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationEnd(Animator animation) {
        //动画结束，可以点击控件，触发动画
        Log.w("aa", "动画结束");
        mCbBox.setClickable(true);
        //mLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
      }

      @Override public void onAnimationCancel(Animator animation) {

      }

      @Override public void onAnimationRepeat(Animator animation) {

      }
    });
    objectAnimator.start();
  }

  private ObjectAnimator big() {
    PropertyValuesHolder pvhScaleX =
            PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 0.55f),
                    Keyframe.ofFloat(1f, 1f));

    PropertyValuesHolder pvhScaleY =
            PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 0.55f),
                    Keyframe.ofFloat(1f, 1f));

    return ObjectAnimator.ofPropertyValuesHolder(mLayout, pvhScaleX, pvhScaleY);
  }

  private ObjectAnimator small() {
    PropertyValuesHolder pvhScaleX =
            PropertyValuesHolder.ofKeyframe(View.SCALE_X, Keyframe.ofFloat(0f, 1f),
                    Keyframe.ofFloat(1f, 0.55f));

    PropertyValuesHolder pvhScaleY =
            PropertyValuesHolder.ofKeyframe(View.SCALE_Y, Keyframe.ofFloat(0f, 1f),
                    Keyframe.ofFloat(1f, 0.55f));

    return ObjectAnimator.ofPropertyValuesHolder(mLayout, pvhScaleX, pvhScaleY);
  }
}
