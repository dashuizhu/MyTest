package com.example.zhujiang.myapplication.floatview.permission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zhujiang.myapplication.R;

/**
 * @author zhuj 2018/4/16 上午10:21.
 */
public class FloatViewLayout extends LinearLayout {

  @BindView(R.id.iv) ImageView mIv;
  @BindView(R.id.tv_content) TextView mTvContent;

  private Context mContext;

  private TranslateAnimation mShowAnim;
  private TranslateAnimation mHiddenAnim;

  private String mText;

  public FloatViewLayout(Context context) {
    super(context);
    mContext = context;
    initViews();
  }

  private void initViews() {
    View view = LayoutInflater.from(mContext).inflate(R.layout.view_float, this);
    ButterKnife.bind(this, view);

    view.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mListener != null) {
          mListener.onClick(mText);
        }
      }
    });
  }

  public void show() {
    if (mShowAnim == null) {
      //控件显示的动画
      mShowAnim =
              new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                      0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.05f);
      mShowAnim.setDuration(2000);
    }
    //调用语句
    startAnimation(mShowAnim);

    //setVisibility(View.VISIBLE);
    mTvContent.setText(mText);
  }

  public void dismiss() {
    if (mHiddenAnim == null) {
      //控件隐藏的动画
      mHiddenAnim =
              new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                      0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0f);
      mHiddenAnim.setDuration(2000);
    }
    startAnimation(mHiddenAnim);
  }

  public void setContetn(String str) {
    mText = str;
    mTvContent.setText(str);
  }

  OnMyViewClickListener mListener;

  public void setOnMyViewClickListener(OnMyViewClickListener listener) {
    mListener = listener;
  }

  public interface OnMyViewClickListener {
    void onClick(String str);
  }
}
