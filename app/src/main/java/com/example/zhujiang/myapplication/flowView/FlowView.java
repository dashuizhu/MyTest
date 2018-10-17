package com.example.zhujiang.myapplication.flowView;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

/**
 * @author zhuj 2017/9/13 下午3:46.
 */
public class FlowView extends View {

  List<FlowItem> mFlowItemList;

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  public FlowView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (mFlowItemList != null && mFlowItemList.size()>0) {
      for(FlowItem item : mFlowItemList) {
      }
    }
  }
}
