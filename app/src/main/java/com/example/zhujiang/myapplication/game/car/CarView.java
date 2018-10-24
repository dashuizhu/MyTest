package com.example.zhujiang.myapplication.game.car;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.game.SoundManager;
import com.example.zhujiang.myapplication.utils.DensityUtil;
import com.example.zhujiang.myapplication.utils.ToastUtils;
import com.example.zhujiang.myapplication.utils.ZipResUtils;
import java.io.File;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author zhuj 2018/10/19 下午2:44.
 */
public class CarView extends RelativeLayout {

    @BindView(R.id.gl_box) GridLayout mGlBox;
    @BindView(R.id.iv_window) ImageView mIvWindow;
    @BindView(R.id.iv_trie_front) ImageView mIvTrieFront;
    @BindView(R.id.iv_trie_before) ImageView mIvTrieBefore;
    @BindView(R.id.iv_car) ImageView mIvCar;

    private boolean mWindowExpand;
    private boolean isReady;

    Subscription mSubscription;

    private int mAnimationDuration = 2000;

    int mCarStopRing;

    private int itemWidth;

    private float moveX;

    public CarView(Context context) {
        super(context);
        initViews();
    }

    public CarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_car, this);
        ButterKnife.bind(this, view);
        //mCarStopRing =
        //        SoundManager.getInstance(getContext()).loadRing(getContext(), R.raw.car_stop);

        try {
            Bitmap bitmap = ZipResUtils.readGuidePic(
                    Environment.getExternalStorageDirectory().getPath() + "/Download/car.zip",
                    "carbg.jpg");
            //Drawable drawable = new BitmapDrawable(bitmap);
            mIvCar.setImageBitmap(bitmap);
            //bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File downFile = Environment.getExternalStorageDirectory();

            try {
                ZipResUtils.UnZipFolder(downFile.getPath()+"/Download/car.zip", downFile.getPath()+"/Download/car", "car_stop.mp3");
                mCarStopRing  = SoundManager.getInstance(getContext()).loadRing(getContext(), downFile.getPath()+"/Download/car/car_stop.mp3");

            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void moveIn() {

        if (moveX == 0) {
            int phonwWidth = DensityUtil.getPhoneScreenWidth(getContext());
            int width = getWidth();
            float mPaddingPhone = (phonwWidth - getMeasuredWidth()) / 2;
            moveX = (getWidth() + mPaddingPhone);
        }

        //减速度 先快后慢
        Interpolator interpolator = new DecelerateInterpolator();

        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        //跟刹车音效,晚一点点
        rotate.setDuration(1500);
        //rotate.setRepeatCount(3);
        rotate.setInterpolator(interpolator);

        mIvTrieBefore.startAnimation(rotate);
        mIvTrieFront.startAnimation(rotate);

        //ViewPropertyAnimator view = animate();
        //view.setDuration(1000);
        //view.setInterpolator(interpolator);
        //view.translationX(getWidth() + moveX);

        TranslateAnimation translateAnimation = new TranslateAnimation(moveX, 0, 0, 0);
        translateAnimation.setDuration(mAnimationDuration);
        translateAnimation.setInterpolator(interpolator);
        startAnimation(translateAnimation);

        //相当于是1200毫秒的时候开始播放刹车音效
        mSubscription =
                Observable.interval(300, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        //7
                        if (aLong < 3) {
                            //SoundManager.getInstance(getContext()).play(SoundManager.VIDEO_MOVE);
                        } else {
                            isReady = true;
                            SoundManager.getInstance(getContext()).play(mCarStopRing);
                            mSubscription.unsubscribe();
                        }
                    }
                });
    }

    public void moveOut() {
        isReady = false;
        //减速度 先慢后快
        Interpolator interpolator = new AccelerateInterpolator();

        Animation rotate = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        rotate.setDuration(mAnimationDuration);
        //rotate.setRepeatCount(3);
        rotate.setInterpolator(interpolator);

        mIvTrieBefore.startAnimation(rotate);
        mIvTrieFront.startAnimation(rotate);

        //int phonwWidth = DensityUtil.getPhoneScreenWidth(getContext());
        //int moveX = (phonwWidth - getWidth()) / 2;
        //ViewPropertyAnimator view = animate();
        //int  carWidth = getWidth();
        //int  getX = (int) getX();
        //view.setDuration(1000);
        //view.setInterpolator(interpolator);
        //view.translationX(-getWidth() - moveX);

        TranslateAnimation translateAnimation = new TranslateAnimation(0f, -moveX, 0, 0);
        translateAnimation.setDuration(mAnimationDuration);
        translateAnimation.setInterpolator(interpolator);
        startAnimation(translateAnimation);

        //mSubscription =
        //        Observable.interval(250, TimeUnit.MILLISECONDS).subscribe(new Action1<Long>() {
        //            @Override
        //            public void call(Long aLong) {
        //                if (aLong < 3) {
        //                    //SoundManager.getInstance(getContext()).play(SoundManager.VIDEO_MOVE);
        //                } else {
        //                    //SoundManager.getInstance(getContext()).play(mCarStopRing);
        //                    mSubscription.unsubscribe();
        //                }
        //            }
        //        });
    }

    public void release() {
        removeAllViews();
        clearAnimation();
        if (mSubscription != null) {
            if (!mSubscription.isUnsubscribed()) {
                mSubscription.unsubscribe();
            }
            mSubscription = null;
        }
    }

    @OnClick(R.id.iv_window)
    public void onViewClicked() {
        if (!isReady()) {
            return;
        }
        if (!mWindowExpand) {
            mWindowExpand = true;

            Animation scalAnimation = new ScaleAnimation(1, 1, 1, 0.2f);
            scalAnimation.setDuration(500);
            Interpolator interpolator = new DecelerateInterpolator();
            scalAnimation.setInterpolator(interpolator);
            scalAnimation.setFillAfter(true);
            mIvWindow.startAnimation(scalAnimation);
        }
    }

    public void initCount(final int count) {
        ImageView iv;
        for (int i = 0; i < count; i++) {
            iv = new ImageView(getContext());
            iv.setBackgroundResource(R.mipmap.ic_launcher);
            iv.setLayoutParams(mGlBox.getLayoutParams());

            mGlBox.addView(iv);
        }
    }

    public void resetGame(final int count) {

        //int phoneWindow = DensityUtil.getPhoneScreenWidth(getContext());
        //layout(100, getTop(), phoneWindow + getWidth(), getBottom());

        //Observable.timer(1, TimeUnit.SECONDS)
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .subscribe(new Action1<Long>() {
        //            @Override
        //            public void call(Long aLong) {

        mGlBox.removeAllViews();

        if (itemWidth == 0) {
            itemWidth = mGlBox.getWidth() / mGlBox.getColumnCount();
        }

        ImageView iv;
        int width = mGlBox.getWidth();
        int height = mGlBox.getHeight();
        int column = mGlBox.getColumnCount();
        for (int i = 0; i < count; i++) {
            iv = new ImageView(getContext());
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            layoutParams.columnSpec = GridLayout.spec(i % column);// 1, GridLayout.UNDEFINED);
            //从底部往上排列, 9是最大值
            layoutParams.rowSpec =
                    GridLayout.spec((9 - i) / column);// 1, GridLayout.UNDEFINED);
            layoutParams.width = itemWidth;
            layoutParams.height = itemWidth;
            iv.setLayoutParams(layoutParams);
            //iv.setScaleType(ImageView.ScaleType.CENTER);
            //iv.setImageResource(R.mipmap.ic_launcher);

            Bitmap bitmap = null;
            try {
                bitmap = ZipResUtils.readGuidePic(
                        Environment.getExternalStorageDirectory().getPath() + "/Download/car.zip",
                        "car" + (i % 3 + 1) + ".jpg");
                //Drawable drawable = new BitmapDrawable(bitmap);
                iv.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                iv.setImageResource(R.mipmap.ic_launcher);
            }
            iv.setBackgroundResource(R.color.colorPrimaryDark);

            mGlBox.addView(iv, layoutParams);
        }
        mWindowExpand = false;
        mIvWindow.clearAnimation();

        moveIn();
        //}
        //});
    }

    public boolean isReady() {
        return isReady;
    }

    public boolean isExpand() {
        return mWindowExpand;
    }
}
