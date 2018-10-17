package com.example.zhujiang.myapplication.shared;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * @author zhuj 2017/11/15 下午2:15.
 */
public class SharedPopupWindow extends PopupWindow implements UMShareListener {

  @BindView(R.id.tv_shared_sina) TextView mTvSharedSina;
  @BindView(R.id.tv_shared_wechat_circle) TextView mTvSharedWechatCircle;
  @BindView(R.id.tv_shared_wechat) TextView mTvSharedWechat;
  @BindView(R.id.tv_shared_qq) TextView mTvSharedQq;
  @BindView(R.id.tv_shared_qq_zone) TextView mTvSharedQqZone;
  @BindView(R.id.btn_cancel) Button mBtnCancel;

  private Activity mContex;

  public SharedPopupWindow(Activity context) {
    super(context);
    mContex = context;
    setWindow(context);
    initView();
  }

  private void setWindow(Context mContext) {
    View mPopView = LayoutInflater.from(mContext).inflate(R.layout.pop_shared, null);
    // 设置SelectPicPopupWindow的View
    setContentView(mPopView);
    ButterKnife.bind(this, mPopView);
    // 设置SelectPicPopupWindow弹出窗体的宽
    setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    // 设置SelectPicPopupWindow弹出窗体的高
    setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    // 设置外部点击隐藏popwindow
    //setOutsideTouchable(true);
    // 设置SelectPicPopupWindow弹出窗体可点击
    setFocusable(true);
    // 设置SelectPicPopupWindow弹出窗体动画效果
    //setAnimationStyle(R.style.pop_anim_style);
    // 实例化一个ColorDrawable颜色为半透明
    ColorDrawable dw = new ColorDrawable(0xb0000000);
    // 设置SelectPicPopupWindow弹出窗体的背景
    setBackgroundDrawable(dw);
    mPopView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        dismiss();
        return false;
      }
    });
    setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    update();
  }

  private void initView() {
  }

  @OnClick(R.id.btn_cancel) public void onViewClicked() {
    dismiss();
  }

  @OnClick({
          R.id.tv_shared_sina, R.id.tv_shared_wechat_circle, R.id.tv_shared_wechat,
          R.id.tv_shared_qq, R.id.tv_shared_qq_zone
  }) public void onViewClicked(View view) {
    SHARE_MEDIA platform ;
    switch (view.getId()) {
      case R.id.tv_shared_sina:
        platform = SHARE_MEDIA.SINA;
        break;
      case R.id.tv_shared_wechat_circle:
        platform = SHARE_MEDIA.WEIXIN_CIRCLE;
        sharedWechatMoment();
        break;
      case R.id.tv_shared_wechat:
        platform = SHARE_MEDIA.WEIXIN;
        sharedWechat();
        break;
      case R.id.tv_shared_qq:
        platform = SHARE_MEDIA.QQ;
        sharedQQ();
        break;
      case R.id.tv_shared_qq_zone:
      default:
        platform = SHARE_MEDIA.QZONE;
        break;
    }
    UMWeb web = new UMWeb("http://www.baidu.com");
    web.setTitle("This is music title");//标题
    web.setDescription("my description");//描述

    UMImage thumb = new UMImage(mContex, R.mipmap.ic_launcher);
    web.setThumb(thumb);  //缩略图

    new ShareAction(mContex)
            .setListenerList(this)
            .setPlatform(platform)
            .withMedia(web)
            .share();

    dismiss();
  }

  private void sharedQQ() {


  }

  private void sharedWechat() {
  }


  private void sharedWechatMoment() {
  }

  @Override public void onStart(SHARE_MEDIA share_media) {
      Log.e("test", " onStart ");
  }

  @Override public void onResult(SHARE_MEDIA share_media) {
    Log.e("test", " onResult   ");
  }

  @Override public void onError(SHARE_MEDIA share_media, Throwable throwable) {
    throwable.printStackTrace();
    Log.e("test", " onError ");
  }

  @Override public void onCancel(SHARE_MEDIA share_media) {
    Log.e("test", " onCancel ");
  }
}
