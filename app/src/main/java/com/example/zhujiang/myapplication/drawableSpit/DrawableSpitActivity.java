package com.example.zhujiang.myapplication.drawableSpit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.utils.DensityHelp;
import com.example.zhujiang.myapplication.utils.DensityUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import me.iwf.photopicker.PhotoPicker;

public class DrawableSpitActivity extends AppCompatActivity {

    @BindView(R.id.iv_src) ImageView mIvSrc;
    @BindView(R.id.iv_1) ImageView mIv1;
    @BindView(R.id.iv_2) ImageView mIv2;
    @BindView(R.id.iv_3) ImageView mIv3;
    @BindView(R.id.iv_4) ImageView mIv4;
    //@BindView(R.id.line_center) View mLineCenter;

    private String mString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DensityHelp.setOrientationWidth(this, false);
        setContentView(R.layout.activity_drawable_spit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_src)
    public void onViewClicked() {

        int ivHegiht = mIvSrc.getHeight();

        int dp = DensityUtil.dip2px(this, 4);

        int iv1t = mIv1.getTop();
        int iv1b = mIv1.getBottom();
        int iv1r = mIv1.getRight();

        int iv2b = mIv2.getBottom();
        int iv2l = mIv2.getLeft();

        int le3b = mIv3.getBottom();
        int iv3t = mIv3.getTop();
        int iv3r = mIv3.getRight();

        int iv4t = mIv4.getTop();
        int iv4l = mIv4.getLeft();

        int height = mIv3.getHeight();
        int width = mIv3.getWidth();

        //float viewX = mLineCenter.getX();
        //float viewY = mLineCenter.getY();
        //int viewL = mLineCenter.getLeft();
        //int viewt = mLineCenter.getTop();
        //int viewb = mLineCenter.getBottom();
        //int viewr = mLineCenter.getRight();

        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(false)
                .setPreviewEnabled(false)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                List<String> str = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                RequestOptions options = new RequestOptions()
                        //.override(480)
                        .centerCrop().dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL);

                mString = str.get(0);

                //File file = new File(str.get(0));
                //
                //Glide.with(this).load(file)
                //        //.thumbnail(0.2f)
                //        //.transition(new DrawableTransitionOptions().crossFade())
                //        .apply(options).into(mIvSrc);

                spitDrawable();
            }
        }
    }

    private void spitDrawable() {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(mString);
            //Bitmap bitmap = BitmapFactory.decodeStream(fis);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.yugang);

            mIvSrc.setImageBitmap(bitmap);

            int w = bitmap.getWidth(); // 得到图片的宽，高
            int h = bitmap.getHeight();

            int size = Math.min(w, h) / 2;

            int middleW = w / 2;
            int middleH = h / 2;

            //int cropWidth = w >= h ? h : w;// 裁切后所取的正方形区域边长
            //cropWidth /= 2;
            //int cropHeight = (int) (cropWidth / 1.2);
            Bitmap bm1 =
                    Bitmap.createBitmap(bitmap, middleW - size, middleH - size, size, size, null,
                            false);

            Bitmap bm2 =
                    Bitmap.createBitmap(bitmap, middleW, middleH - size, size, size, null, false);
            Bitmap bm3 =
                    Bitmap.createBitmap(bitmap, middleW - size, middleH, size, size, null, false);
            Bitmap bm4 = Bitmap.createBitmap(bitmap, middleW, middleH, size, size, null, false);

            //Bitmap bm3 = Bitmap.createBitmap(bitmap, 0, middleH, middleW, (h-middleH), null, false);
            //
            //Bitmap bm2 = Bitmap.createBitmap(bitmap, 0, 0, middleW, middleH+2, null, false);
            //Bitmap bm4 = Bitmap.createBitmap(bitmap, 0, middleH-2, middleW, (h-middleH), null, false);

            mIv1.setImageDrawable(new BitmapDrawable(getResources(), bm1));
            mIv2.setImageDrawable(new BitmapDrawable(getResources(), bm2));
            mIv3.setImageDrawable(new BitmapDrawable(getResources(), bm3));

            mIv4.setImageDrawable(new BitmapDrawable(getResources(), bm4));

            bitmap.recycle();
            //bm1.recycle();
            //bm2.recycle();
            //bm3.recycle();
            //bm4.recycle();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
