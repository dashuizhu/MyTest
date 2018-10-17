package com.example.zhujiang.myapplication.changeImage;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.zhujiang.myapplication.R;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import me.iwf.photopicker.PhotoPicker;
import rx.functions.Action1;

public class ChagneImageActivity extends AppCompatActivity {

  private static String PATH = "";

  @BindView(R.id.btn_choise) Button mBtnChoise;
  @BindView(R.id.btn_start) Button mBtnStart;
  @BindView(R.id.tv_size) EditText mEvSize;
  @BindView(R.id.tv_result) TextView mTvResult;

  private ArrayList<String> mStringList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chagne_image);
    ButterKnife.bind(this);

    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
    PATH = Environment.getExternalStorageDirectory() + "/apic/" + sdf.format(new Date()) + "/";

    new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe(new Action1<Boolean>() {
              @Override
              public void call(Boolean aBoolean) {
                if (aBoolean) {
                  File file = new File(PATH);
                  if (!file.exists()) {
                    file.mkdirs();
                  }
                }
              }
            });
  }

  @OnClick({ R.id.btn_choise, R.id.btn_start })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_choise:
        PhotoPicker.builder()
                .setPhotoCount(9)
                .setShowCamera(false)
                .setPreviewEnabled(false)
                .setSelected(mStringList)
                .start(this);

        break;
      case R.id.btn_start:
        start();
        break;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
      if (data != null) {
        mStringList = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
      }
    }
  }

  private void start() {
    String sizeAll = mEvSize.getText().toString();
    if (TextUtils.isEmpty(sizeAll)) {
      return;
    }
    //if (!sizeAll.contains(",")) {
    //  return;
    //}

    int count = 0;
    for (String size : sizeAll.split(",")) {

      int width = 0, height = 0;
      try {
        width = Integer.valueOf(size.substring(0, size.indexOf("x")));
        height = Integer.valueOf(size.substring(size.indexOf("x") + 1));
      } catch (Exception e) {
        continue;
      }

      String fileType;
      String newName;
      String fileName;
      for (String s : mStringList) {
        fileName = s.substring(s.lastIndexOf("/"), s.lastIndexOf("."));
        fileType = s.substring(s.lastIndexOf("."), s.length());
        newName = fileName + "_" + size + fileType;
        if (createFile(s, PATH + newName, width, height)) {
          count++;
        }
      }
    }

    mTvResult.setText("生成文件在" + PATH + count + "张");
  }

  private boolean createFile(String src, String tag, int width, int height) {
    try {
      //BitmapFactory.Options options = new BitmapFactory.Options();
      int quality = 100;
      if (height > 1660) {
        quality = 95;
      //  //options.inSampleSize = 2;
      //} else {
      //  options.inSampleSize = 1;
      }

      Bitmap bitmap = BitmapFactory.decodeFile(src);

      //Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
      Bitmap resizeBitmap = zoomImage(bitmap, width, height);


      FileOutputStream fos = new FileOutputStream(tag);
      resizeBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
      //fos.write(stream.toByteArray());
      fos.flush();
      fos.close();
      if (bitmap != null) {
        bitmap.recycle();
      }
      resizeBitmap.recycle();
      //stream.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }


  public static Bitmap zoomImage(Bitmap bgimage, float newWidth,
          float newHeight) {
    // 获取这个图片的宽和高
    float width = bgimage.getWidth();
    float height = bgimage.getHeight();
    // 创建操作图片用的matrix对象
    Matrix matrix = new Matrix();
    // 计算宽高缩放率
    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;
    // 缩放图片动作
    matrix.postScale(scaleWidth, scaleHeight);
    Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
            (int) height, matrix, true);
    return bitmap;
  }

  private int computeSize(BitmapFactory.Options options, int tagWidth, int tagHeight) {
    int height = options.outHeight;
    int width = options.outWidth;

    int inSampleSize = 1;

    int sizeHeight = height / tagHeight;
    int sizeWid = width / tagWidth;

    inSampleSize = Math.min(sizeHeight, sizeWid);
    if (inSampleSize < 1) {
      inSampleSize = 1;
    }
    return inSampleSize;
  }
}
