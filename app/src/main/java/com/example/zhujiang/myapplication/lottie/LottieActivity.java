package com.example.zhujiang.myapplication.lottie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.airbnb.lottie.ImageAssetDelegate;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.example.zhujiang.myapplication.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class LottieActivity extends AppCompatActivity {

    @BindView(R.id.animation_view) LottieAnimationView mAnimationView;
    @BindView(R.id.btn) Button mBtn;

    Subscription mSubscription;
    private Drawable mDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        ButterKnife.bind(this);
        //mAnimationView.setani
        //mDrawable  = getDrawable(R.mipmap.bg_default_img);
        //mSubscription = Observable.interval(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
        //    @Override
        //    public void call(Long aLong) {
        //        Log.w("test", LottieActivity.this + " " + aLong + " ");
        //    }
        //});
        //
        //File json = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "buddyParent/imagesfire/huo.json");
        //FileInputStream inputStream = null;
        //try {
        //    inputStream = new FileInputStream(json);
        //    LottieComposition.Factory.fromInputStream(inputStream, new OnCompositionLoadedListener() {
        //        @Override
        //        public void onCompositionLoaded(@Nullable LottieComposition composition) {
        //            mAnimationView.setComposition(composition);
        //            //mAnimationView.setImageAssetDelegate(new ImageAssetDelegate() {
        //            //    @Override
        //            //    public Bitmap fetchBitmap(LottieImageAsset asset) {
        //            //        Bitmap bitmap = null;
        //            //        try {
        //            //            FileInputStream fileInputStream = new FileInputStream(
        //            //                    Environment.getExternalStorageDirectory().getPath() + File.separator + "fire");
        //            //            bitmap = BitmapFactory.decodeStream(fileInputStream);
        //            //        }catch (Exception e){
        //            //            e.printStackTrace();
        //            //        }finally {
        //            //            if (bitmap == null) {
        //            //                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        //            //            }
        //            //        }
        //            //        return bitmap;
        //            //    }
        //            //});
        //        }
        //    });
        //} catch (FileNotFoundException e) {
        //    e.printStackTrace();
        //}
        //
        //mAnimationView.setImageAssetDelegate(new ImageAssetDelegate() {
        //    @Override
        //    public Bitmap fetchBitmap(LottieImageAsset asset) {
        //        Bitmap bitmap = null;
        //        try {
        //            FileInputStream fileInputStream = new FileInputStream(
        //                    Environment.getExternalStorageDirectory().getPath() + File.separator + "buddyParent/imagesfire"+"/"+asset.getFileName());
        //            bitmap = BitmapFactory.decodeStream(fileInputStream);
        //        }catch (Exception e){
        //            e.printStackTrace();
        //        }finally {
        //            if (bitmap == null) {
        //                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8);
        //            }
        //        }
        //        return bitmap;
        //    }
        //});

    }

    @Override
    protected void onDestroy() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
        mDrawable = null;
        super.onDestroy();
    }

    @OnClick(R.id.btn)
    public void onViewClicked() {

        //LottieComposition lottieComposition = new LottieComposition();

        //LottieComposition.Factory.fromFileSync()

        mAnimationView.playAnimation();

        //ScaleAnimation animation = new ScaleAnimation(0.1f, 1f, 0.1f, 1f, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
        //animation.setDuration(1000);
        //mAnimationView.startAnimation(animation);
    }
}
