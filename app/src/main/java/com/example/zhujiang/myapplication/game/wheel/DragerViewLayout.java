package com.example.zhujiang.myapplication.game.wheel;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.HashMap;
import java.util.Map;

public class DragerViewLayout extends RelativeLayout {

    private final static String TAG = DragerViewLayout.class.getSimpleName();

    private ViewDragHelper viewDragHelper;
    private boolean drager = true;
    private Context mContext;

    private Map<Integer, ViewBean> mBeanMap = new HashMap<>();

    public DragerViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (drager) {
            viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    //让视图回到顶层
                    //child.bringToFront();
                    boolean enableDrager = mBeanMap.get(child.getId()).isMove();
                    if (enableDrager) {
                        mBeanMap.get(child.getId()).setDragering(true);
                    }
                    return enableDrager;
                }

                @Override
                public int clampViewPositionHorizontal(View child, int left, int dx) {
                    /**
                     * 如果横向滑动超过右面边界的时候，控制子视图不能够越界
                     */
                    if (left + child.getMeasuredWidth() >= getMeasuredWidth()) {
                        return getMeasuredWidth() - child.getMeasuredWidth();
                    }
                    /**
                     * 如果横向滑动超过左面边界的时候，控制子视图不能够越界
                     */
                    if (left <= 0) {
                        return 0;
                    }
                    return left;
                }

                @Override
                public int clampViewPositionVertical(View child, int top, int dy) {
                    /**
                     * 控制下边界，子视图不能够越界
                     */
                    if (child.getMeasuredHeight() + top > getMeasuredHeight()) {
                        return getMeasuredHeight() - child.getMeasuredHeight();
                    }
                    /**
                     * 控制上边界，子视图不能够越界
                     */
                    if (top <= 0) {
                        return 0;
                    }
                    return top;
                }

                @Override
                public void onViewPositionChanged(View changedView, int left, int top, int dx,
                        int dy) {
                    super.onViewPositionChanged(changedView, left, top, dx, dy);
                    //SharedPreferencesUtil.saveData(mContext,(String) changedView.getTag(), left + "#" + top);

                    boolean isMove = mBeanMap.get(changedView.getId()).isMove();
                    boolean isDragering = mBeanMap.get(changedView.getId()).isDragering();
                    Log.d(TAG, left + " " + top + " " + dx + " " + dy + isMove);

                    if (mListener != null && isMove && isDragering) {
                        mListener.onViewChagne(changedView);
                    }
                }

                //当手指释放的时候回调
                @Override
                public void onViewReleased(View releasedChild, float xvel, float yvel) {
                    Log.d(TAG, "onViewReleased:  " + xvel + " " + yvel);
                    //调用这个方法,就可以设置releasedChild回弹得位置.
                    ViewBean viewBean = mBeanMap.get(releasedChild.getId());
                    viewBean.setDragering(false);
                    //if (viewBean.isMove()) {
                        viewDragHelper.settleCapturedViewAt(viewBean.getSrcX(),
                                viewBean.getSrcY());//参数就是x,y的坐标
                        postInvalidate();//注意一定要调用这个方法,否则没效果.
                    //}
                    if (mListener != null) {
                        mListener.onViewRelease(releasedChild);
                    }
                }

                //如果你拖动View添加了clickable = true 或者为 button 会出现拖不动的情况，原因是拖动的时候onInterceptTouchEvent方法，
                // 判断是否可以捕获，而在判断的过程中会去判断另外两个回调的方法getViewHorizontalDragRange和getViewVerticalDragRange，
                // 只有这两个方法返回大于0的值才能正常的捕获。如果未能正常捕获就会导致手势down后面的move以及up都没有进入到onTouchEvent
                @Override
                public int getViewHorizontalDragRange(View child) {
                    return getMeasuredWidth() - child.getMeasuredWidth();
                }

                @Override
                public int getViewVerticalDragRange(View child) {
                    return getMeasuredHeight() - child.getMeasuredHeight();
                }
            });
            viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (drager) {
            return viewDragHelper.shouldInterceptTouchEvent(event);
        } else {
            return super.onInterceptTouchEvent(event);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (drager) {
            viewDragHelper.processTouchEvent(event);
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (drager) {
            if (viewDragHelper.continueSettling(true)) {
                invalidate();
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Log.w(TAG, i + " onlayout  " + child.getLeft() + " " + child.getTop());

            if (!mBeanMap.containsKey(child.getId())) {
                ViewBean viewBean = new ViewBean(child.getId(), child.getLeft(), child.getTop());
                mBeanMap.put(child.getId(), viewBean);
            } else {
                mBeanMap.get(child.getId()).setSrc(child.getLeft(), child.getTop());
            }

            //    child.layout(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
            ////    String xy = (String) SharedPreferencesUtil.getData(mContext, (String) child.getTag(), "0");
            ////    if (!xy.equals("0")) {
            ////        String[] xys = xy.split("#");
            ////        if (xys.length == 2) {
            ////            child.layout(Integer.parseInt(xys[0]), Integer.parseInt(xys[1]), child.getMeasuredWidth() + Integer.parseInt(xys[0]), child.getMeasuredHeight() + Integer.parseInt(xys[1]));
            ////        }
            //    }
        }
    }

    /**
     * 设置视图是否可以拖拽
     */
    public void isDrager(boolean flag) {
        this.drager = flag;
    }

    OnViewChangeListener mListener;

    public void addOnViewChangeListener(OnViewChangeListener listener) {
        mListener = listener;
    }

    public void resetAll() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Log.w(TAG, i + " onlayout  " + child.getLeft() + " " + child.getTop());

            if (mBeanMap.containsKey(child.getId())) {
                mBeanMap.get(child.getId()).setMove(true);
            }
        }
    }

    interface OnViewChangeListener {
        void onViewChagne(View view);
        void onViewRelease(View view);
    }

    public ViewBean getViewBean(int id) {
        return mBeanMap.get(id);
    }
}