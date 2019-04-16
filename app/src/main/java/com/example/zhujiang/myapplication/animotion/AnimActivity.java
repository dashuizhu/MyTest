package com.example.zhujiang.myapplication.animotion;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;

public class AnimActivity extends AppCompatActivity {

  @BindView(R.id.tv_1) TextView mTv1;
  @BindView(R.id.tv_2) TextView mTv2;
  @BindView(R.id.tv_3) TextView mTv3;
  @BindView(R.id.iv) ImageView mIv;
  @BindView(R.id.iv2) ImageView mIv2;
  @BindView(R.id.layout) RelativeLayout mLayout;
  @BindView(R.id.cb_box) CheckBox mCbBox;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_anim);
    ButterKnife.bind(this);
    initViews();
  }

  My3dAnimation my3dAnimation;
  private final static int ROTATE_X = 0;
  private final static int ROTATE_Y = 1;

  private void initViews() {
    mCbBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        startAnim(isChecked);
      }
    });

    findViewById(R.id.layout_window).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        float centerX = v.getWidth() / 2.0f;
        float centerY = v.getHeight() / 2.0f;
        centerX = 0;
        my3dAnimation = new My3dAnimation(ROTATE_Y, 0, -60, centerX,
                  centerY, 310f);
        my3dAnimation.setDuration(1000);
        my3dAnimation.setInterpolator(new LinearInterpolator());
        my3dAnimation.setFillAfter(true);
        mIv.startAnimation(my3dAnimation);


        My3dAnimation my3dAnimationRight = new My3dAnimation(ROTATE_Y, 0, 60, mIv2.getWidth(),
                centerY, 310f);
        my3dAnimationRight.setDuration(1000);
        my3dAnimationRight.setInterpolator(new LinearInterpolator());
        my3dAnimationRight.setFillAfter(true);
        mIv2.startAnimation(my3dAnimationRight);



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
