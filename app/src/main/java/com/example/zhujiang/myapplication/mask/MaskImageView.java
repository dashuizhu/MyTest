package com.example.zhujiang.myapplication.mask;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.example.zhujiang.myapplication.R;

/**
 * @author zhuj 2019/4/20 上午11:38.
 */
public class MaskImageView extends View {

    private int drawRes;
    private int maskRes;

    private Bitmap bgBitmap;

    public MaskImageView(Context context) {
        super(context);
    }

    public MaskImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MaskImageView);
        drawRes = a.getResourceId(R.styleable.MaskImageView_drawable, 0);
        maskRes = a.getResourceId(R.styleable.MaskImageView_maskDrawable, 0);
        if (drawRes == 0 || maskRes ==0) {
            throw new IllegalArgumentException(" drawable or maskDrawable is required and must refer a drawableRes ");
        }
        a.recycle();

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

    }

    public MaskImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap original = BitmapFactory.decodeResource(getResources(), drawRes);
        //获取遮罩层图片
        Bitmap mask = BitmapFactory.decodeResource(getResources(), maskRes);
        //Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setARGB(233,123,78,211);
        bgBitmap = Bitmap.createBitmap(original.getWidth()+40, original.getHeight()+40, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bgBitmap);
        c.drawRect(0, 0, original.getWidth()+40, original.getHeight()+40, bgPaint);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //paint.setARGB(233,123,78,211);
        //叠加重复部分 ，显示下面的。
        //canvas.drawRect(20, 20 ,original.getWidth()+20, original.getHeight()+20, paint);

        canvas.drawBitmap(bgBitmap, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(original, 20 , 20, paint);
        paint.setXfermode(null);
    }
}
