package com.example.zhujiang.myapplication.runText;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;
import java.util.ArrayList;

/**
 * @author zhuj 2018/1/3 下午2:41.
 */
public class RunTextView extends RelativeLayout {

  @BindView(R.id.tv_run) RxTextViewVertical mTvRun;
  @BindView(R.id.layout) LinearLayout mLayout;
  @BindView(R.id.cb_box) CheckBox mCbBox;

  private ArrayList<String> titleList = new ArrayList<>();
  private Context mContext;

  public RunTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    mContext = context;

    View view = LayoutInflater.from(context).inflate(R.layout.view_runtext, this);
    //以下两句的顺序不能调换，要先addView，然后才能通过findViewById找到该TextView
    ButterKnife.bind(view);

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
        Toast.makeText(mContext, ""+ titleList.get(position), Toast.LENGTH_SHORT).show();
      }
    });

    mCbBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        if (isFirst) {
          isFirst = false;

          max = mLayout.getWidth();
        }

        initAnimation(isChecked);

      }
    });

  }

  private boolean isFirst = true;
  ValueAnimator mValueAnimator;

  private int max;

  private void initAnimation(boolean isExpand) {
    if (isExpand) {
      mValueAnimator = ValueAnimator.ofInt(0 , max);
      mTvRun.startAutoScroll();
    } else {
      mValueAnimator = ValueAnimator.ofInt(max , 0);
      mTvRun.stopAutoScroll();
    }
    mValueAnimator.setDuration(1000);
    mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
    //mValueAnimator.setRepeatCount(ValueAnimator.REVERSE);
    mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        int offset = (int) animation.getAnimatedValue();
        Log.w("test", "  " + offset);
        //invalidate();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLayout.getLayoutParams();
        params.width = offset;
        mLayout.setLayoutParams(params);
      }
    });
    mValueAnimator.start();

  }
}
