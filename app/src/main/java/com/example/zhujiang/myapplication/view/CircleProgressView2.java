package com.example.zhujiang.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author zhuj 2018/2/8 上午11:10.
 */
public class CircleProgressView2 extends View {

  //进度颜色
  private int firstColor = Color.parseColor("#1C425B");
  private int secondColor = Color.parseColor("#2C6A93");
  private int thirdColor = Color.parseColor("#A7D5F5");

  //圆环宽度
  private int firstRadis = 22;
  private int secondRadis = 18;
  private int thirdRadis = 14;

  //进度值
  private int firstProgress = 20;
  private int secondProgress = 30;
  private int thirdProgress = 40;

  private String firstText = "测试1";
  private String secondText = "测试2";
  private String thirdText = "测试3";

  private int circleInnerRadius = 100;

  /**
   * 内圆环 与 外圆弧的距离
   */
  private int circleBorder = 25;

  /**
   * 引线的宽度
   */
  private int lineWidth = 50;

  /**
   * 折线的宽度
   */
  private int lineHorWidth = 30;

  private int textSize = 20;

  /**
   * view的中心点
   */
  private int centerX, centerY;

  /**
   * 圆弧画笔
   */
  Paint paint = new Paint();

  /**
   * 文字画笔
   */
  private Paint textPaint = new Paint();

  private int mWidth, mHeight;

  /**
   * 三个进度条的角度
   */
  private int firstAngle, secondAngle, thirdAngle;

  public CircleProgressView2(Context context) {
    super(context);
    init(context);
  }

  public CircleProgressView2(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public CircleProgressView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }


  private void init(Context context) {
    float density = context.getResources().getDisplayMetrics().density;
    circleInnerRadius = (int) (density * 60);
    lineWidth = (int) (density * 25);
    lineHorWidth = (int) (density * 15);
    textSize = (int) (density *20);

  }


  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
    mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

    //setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
    //        getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec) / 3);

    int specModeWidth = MeasureSpec.getMode(widthMeasureSpec);
    int specModeHeight = MeasureSpec.getMode(heightMeasureSpec);
    int height = 0;
    switch (specModeHeight) {
      case MeasureSpec.AT_MOST:

        break;
      case MeasureSpec.EXACTLY:
      case MeasureSpec.UNSPECIFIED:
        height = MeasureSpec.getSize(heightMeasureSpec);
        break;
    }
    mHeight = height;
    setMeasuredDimension(mWidth, mHeight);
    centerX = mWidth / 2;
    centerY = mHeight / 2;
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    /** 默认画笔颜色，黄色 */
    paint.setColor(firstColor);
    /** 结合处为圆弧*/
    //paint.setStrokeJoin(Paint.Join.ROUND);
    /** 设置画笔的样式 Paint.Cap.Round ,Cap.SQUARE等分别为圆形、方形*/
    //paint.setStrokeCap(Paint.Cap.ROUND);
    /** 设置画笔的填充样式 Paint.Style.FILL  :填充内部;Paint.Style.FILL_AND_STROKE  ：填充内部和描边;  Paint.Style.STROKE  ：仅描边*/
    paint.setStyle(Paint.Style.STROKE);
    /**抗锯齿功能*/
    paint.setAntiAlias(true);

    //外圆弧
    paint.setStrokeWidth(2);
    RectF bigRectF = new RectF(centerX - circleInnerRadius - circleBorder,
            centerY - circleInnerRadius - circleBorder, centerX + circleInnerRadius + circleBorder,
            centerY + circleInnerRadius + circleBorder);
    canvas.drawArc(bigRectF, 0, 360, false, paint);

    RectF rectF = new RectF(centerX - circleInnerRadius, centerY - circleInnerRadius,
            centerX + circleInnerRadius, centerY + circleInnerRadius);

    int totalProgress = 0;
    totalProgress += firstProgress;
    totalProgress += secondProgress;
    totalProgress += thirdProgress;


    /**设置画笔宽度*/
    paint.setStrokeWidth(firstRadis);
    if (totalProgress>0) {

      firstAngle = firstProgress * 360 / totalProgress;
      secondAngle = secondProgress * 360 / totalProgress;
      thirdAngle = thirdProgress * 360 / totalProgress;

      /**绘制圆弧的方法
       * drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)//画弧，
       参数一是RectF对象，一个矩形区域椭圆形的界限用于定义在形状、大小、电弧，
       参数二是起始角(度)在电弧的开始，圆弧起始角度，单位为度。
       参数三圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
       参数四是如果这是true(真)的话,在绘制圆弧时将圆心包括在内，通常用来绘制扇形；如果它是false(假)这将是一个弧线,
       参数五是Paint对象；
       */
      //上是 270  下90   右边是0  左边是180 ，弧线是顺时针的， 这里逆时针 所以用负数
      canvas.drawArc(rectF, 270, -firstAngle, false, paint);

      //第二进度
      paint.setColor(secondColor);
      paint.setStrokeWidth(secondRadis);
      canvas.drawArc(rectF, 270 - firstAngle, -secondAngle, false, paint);

      //第三进度条
      paint.setColor(thirdColor);
      paint.setStrokeWidth(thirdRadis);
      canvas.drawArc(rectF, 270 - firstAngle - secondAngle, -thirdAngle, false, paint);

    } else { //三个进度都为0
      paint.setColor(Color.DKGRAY);
      canvas.drawArc(rectF, 0, 360, false, paint);
    }

    //paint.setColor(Color.rgb(23, 91, 55));
    //float  textWidth = textPaint.measureText("1 2 3");
    textPaint.setColor(firstColor);
    textPaint.setTextSize(textSize);
    textPaint.setAntiAlias(true);
    //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
    textPaint.setTextAlign(Paint.Align.CENTER);

    String str = totalProgress + "%";
    //中间文字
    Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
    float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
    float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
    int baseLineY = (int) (centerY - top / 2 - bottom / 2);//基线中间点的y轴计算公式

    canvas.drawText(str, centerX, baseLineY, textPaint);

    drawProgressText(canvas);


    //测试画矩形
    //textPaint.setStyle(Paint.Style.FILL);//充满
    //textPaint.setColor(Color.LTGRAY);
    //textPaint.setAntiAlias(true);// 设置画笔的锯齿效果
    //canvas.drawText("画圆角矩形:", 10, 260, textPaint);
    //RectF oval3 = new RectF(0, 60, 400, 260);// 设置个新的长方形
    //canvas.drawRoundRect(oval3, 50, 100, textPaint);//第二
  }

  private void drawProgressText(Canvas canvas) {

    //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setTextSize(textSize/2);
    textPaint.setColor(firstColor);

    Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
    float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
    float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
    //float textHeight = bottom - top;
    int baseLineY = (int) (centerY - top / 2 - bottom / 2);//基线中间点的y轴计算公式

    String showStr = firstText + firstProgress + "%";
    float textWidth = textPaint.measureText(showStr);

    canvas.drawText(showStr, centerX - textWidth / 2 - circleInnerRadius - circleBorder - lineWidth,
            baseLineY - circleInnerRadius, textPaint);

    //如果第一进度 大于0 ，划线
    if (firstProgress > 0) {
      textPaint.setTextAlign(Paint.Align.LEFT);
      canvas.drawLine(centerX - circleInnerRadius - circleBorder - lineWidth,
              centerY - circleInnerRadius, centerX - circleInnerRadius - circleBorder - lineHorWidth,
              centerY - circleInnerRadius, textPaint);

      //第一进度条，中间点的角度
      int cencerAngle = 270 - (firstAngle / 2);

      //通过三角函数， 获得对应角度， 相对半径radius的偏移量x 和 y
      float x = (float) (circleInnerRadius * Math.cos(Math.toRadians(cencerAngle)));
      float y = (float) (circleInnerRadius * Math.sin(Math.toRadians(cencerAngle)));

      canvas.drawLine(centerX - circleInnerRadius - circleBorder - lineHorWidth, centerY - circleInnerRadius,
              centerX + x, centerY + y, textPaint);
    }

    //绘画第二个文本
    showStr = secondText + secondProgress + "%";
    textWidth = textPaint.measureText(showStr);
    textPaint.setColor(secondColor);
    textPaint.setTextAlign(Paint.Align.CENTER);

    canvas.drawText(showStr, centerX + textWidth / 2 + circleInnerRadius + circleBorder + lineWidth,
            baseLineY + circleInnerRadius, textPaint);

    //如果进度 大于0 ，划线
    if (secondProgress > 0) {
      textPaint.setTextAlign(Paint.Align.LEFT);
      canvas.drawLine(centerX + circleInnerRadius + circleBorder + lineWidth,
              centerY + circleInnerRadius, centerX + circleInnerRadius + circleBorder + lineHorWidth,
              centerY + circleInnerRadius, textPaint);

      //第二进度条，中间点 ，角度
      int cencerAngle = 270 - firstAngle - (secondAngle / 2);

      //通过三角函数， 获得对应角度， 相对半径radius的偏移量x 和 y
      float x = (float) (circleInnerRadius * Math.cos(Math.toRadians(cencerAngle)));
      float y = (float) (circleInnerRadius * Math.sin(Math.toRadians(cencerAngle)));

      canvas.drawLine(centerX + circleInnerRadius + circleBorder + lineHorWidth, centerY + circleInnerRadius,
              centerX + x, centerY + y, textPaint);
    }

    //绘画第三个文本
    showStr = thirdText + thirdProgress + "%";
    textWidth = textPaint.measureText(showStr);
    textPaint.setColor(thirdColor);
    textPaint.setTextAlign(Paint.Align.CENTER);

    canvas.drawText(showStr, centerX + textWidth / 2 + circleInnerRadius + circleBorder + lineWidth,
            baseLineY - circleInnerRadius, textPaint);

    //如果进度 大于0 ，划线
    if (thirdProgress > 0) {
      textPaint.setTextAlign(Paint.Align.LEFT);
      canvas.drawLine(centerX + circleInnerRadius + circleBorder + lineWidth,
              centerY - circleInnerRadius, centerX + circleInnerRadius + circleBorder + lineHorWidth,
              centerY - circleInnerRadius, textPaint);

      //第三进度条，中间点 ，角度
      int cencerAngle = 270 - firstAngle - secondAngle - (thirdAngle / 2);

      //通过三角函数， 获得对应角度， 相对半径radius的偏移量x 和 y
      float x = (float) (circleInnerRadius * Math.cos(Math.toRadians(cencerAngle)));
      float y = (float) (circleInnerRadius * Math.sin(Math.toRadians(cencerAngle)));

      canvas.drawLine(centerX + circleInnerRadius + circleBorder + 30, centerY - circleInnerRadius,
              centerX + x, centerY + y, textPaint);
    }
  }


  public void setProgress(String firstStr, int firstProgress, String secondStr, int secondProgress,
          String thirdStr, int thirdProgress) {
    this.firstText = firstStr;
    this.secondText = secondStr;
    this.thirdText = thirdStr;
    this.firstProgress = firstProgress;
    this.secondProgress = secondProgress;
    this.thirdProgress = thirdProgress;
    invalidate();
  }
}
