package com.example.zhujiang.myapplication.game;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.utils.DensityUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zhuj 2018/9/11 下午6:02.
 */
public class GameBgView extends ViewGroup {

    //private ImageView mDropView;
    private int mLastX, mLastY;

    //private ImageView mBoxView;

    /**
     * 固定可以移动的水果
     */
    private List<ItemView> list = new ArrayList();

    /**
     * 已经放好的水果
     */
    private List<ItemView> boxItemList = new ArrayList<>();

    /**
     * 盘子，用于装水果
     */
    private List<BoxView> boxList = new ArrayList<>();
    //private List<View> boxlist = new ArrayList();
    //private ImageView boxArray = new ImageView[3];

    //private int[] boxArray = new int[9];

    private int itemWidth = 100;

    private int mBackgourRes;

    public GameBgView(Context context) {
        super(context);
    }

    public GameBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBackgourRes = getFrultBackgroud();
        initViews();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < boxList.size(); i++) {
            BoxView boxView = boxList.get(i);
            MyLayoutParams boxPa = (MyLayoutParams) boxView.getLayoutParams();
            boxView.layout(boxPa.x, boxPa.y, boxPa.x + boxView.getLayoutParams().width,
                    boxPa.y + boxView.getLayoutParams().height);
        }

        MyLayoutParams layoutParams;
        ItemView view;
        for (int i = 0; i < list.size(); i++) {
            layoutParams = (MyLayoutParams) list.get(i).getLayoutParams();
            view = list.get(i);
            int getx = (int) view.getX();
            int gety = (int) view.getY();
            int left = view.getLeft();
            int lpX = layoutParams.x;
            if (layoutParams.isDrag) {
                view.layout(view.getLeft() + layoutParams.x, view.getTop() + layoutParams.y,
                        view.getRight() + layoutParams.x, view.getBottom() + layoutParams.y);
            } else {
                ////这个x 、y为0
                int x = layoutParams.x;
                int y = layoutParams.y;
                //这个回到原点
                view.layout(x, y, x + view.getLayoutParams().width,
                        y + view.getLayoutParams().height);

                //这个回停止到本地
                //view.layout((int)view.getX(), (int)view.getY(),(int)view.getX() + view.getLayoutParams().width,
                //        (int)view.getY() + view.getLayoutParams().height);
            }
        }

        for (int i = 0; i < boxItemList.size(); i++) {
            layoutParams = (MyLayoutParams) boxItemList.get(i).getLayoutParams();
            view = boxItemList.get(i);
            int getx = (int) view.getX();
            int gety = (int) view.getY();
            int left = view.getLeft();
            int lpX = layoutParams.x;
            ////这个x 、y为0
            int x = layoutParams.x;
            int y = layoutParams.y;
            //这个回到原点
            view.layout(x, y, x + view.getLayoutParams().width, y + view.getLayoutParams().height);

            //这个回停止到本地
            //view.layout((int)view.getX(), (int)view.getY(),(int)view.getX() + view.getLayoutParams().width,
            //        (int)view.getY() + view.getLayoutParams().height);
        }
    }

    @Override
    protected MyLayoutParams generateDefaultLayoutParams() {
        return new MyLayoutParams(0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private void initViews() {
        itemWidth = DensityUtil.dip2px(getContext(), 50);
        BoxView mBoxView;
        MyLayoutParams lpBox;
        int boxWidth = 3 * itemWidth;
        for (int i = 0; i < 3; i++) {
            mBoxView = new BoxView(getContext());
            mBoxView.setBackgroundColor(
                    ContextCompat.getColor(getContext(), R.color.toolbar_spilt_line));
            lpBox = new MyLayoutParams(boxWidth, boxWidth);
            lpBox.x = DensityUtil.dip2px(getContext(), 54) * (i+1) + (i) * boxWidth;
            lpBox.y = 2*itemWidth;
            mBoxView.setLayoutParams(lpBox);
            addView(mBoxView);

            boxList.add(mBoxView);
        }

        //mBackgourRes = getFrultBackgroud();

        ItemView apple;
        for (int i = 0; i < 9; i++) {
            apple = new ItemView(getContext());
            apple.setMyId(i);
            apple.setImageResource(mBackgourRes);
            MyLayoutParams lp = new MyLayoutParams(itemWidth, itemWidth);
            //中间338 - 4.5个 50
            lp.x = DensityUtil.dip2px(getContext(), 108) + i * itemWidth;
            lp.y = DensityUtil.dip2px(getContext(), 300);
            apple.setSrcX((int) lp.x);
            apple.setSrcY((int) lp.y);
            apple.setLayoutParams(lp);

            addView(apple);
            list.add(apple);
            apple.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ItemView itemView = (ItemView) v;
                    MyLayoutParams layoutParams = (MyLayoutParams) v.getLayoutParams();
                    int x = (int) event.getRawX(), y = (int) event.getRawY();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mLastX = x;
                            mLastY = y;
                            layoutParams.isDrag = false;
                            requestLayout();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            layoutParams.isDrag = true;
                            layoutParams.x = x - mLastX;
                            layoutParams.y = y - mLastY;
                            requestLayout();
                            mLastX = x;
                            mLastY = y;

                            break;
                        case MotionEvent.ACTION_UP:
                            //layoutParams = (MyLayoutParams) mDropView.getLayoutParams();
                            layoutParams.isDrag = true;
                            layoutParams.x = x - mLastX;
                            layoutParams.y = y - mLastY;
                            requestLayout();
                            mLastX = x;
                            mLastY = y;
                            layoutParams.isDrag = false;
                            requestLayout();

                            //if (itemView.isSelected()) {
                            boolean isInbox = false;
                            BoxView mBoxView = null;
                            for (int j = 0; j < boxList.size(); j++) {
                                mBoxView = boxList.get(j);
                                if (mBoxView.isEmptyBox() && onOver(v, mBoxView)) {
                                    isInbox = true;
                                    break;
                                }
                            }
                            if (isInbox) {
                                int boxPosition = mBoxView.getEmptyPosition(itemView.getMyId());
                                if (boxPosition > -1) {

                                    //原来有位置
                                    if (itemView.isSelected()) {
                                        BoxView oldBox;
                                        for (int j = 0; j < boxList.size(); j++) {
                                            oldBox = boxList.get(j);
                                            int position = oldBox.getBoxPostion(itemView.getMyId());
                                            if (position >= 0) {
                                                oldBox.getBoxArray()[position] = -1;
                                                break;
                                            }
                                        }
                                    }

                                    layoutParams.isDrag = true;
                                    int[] location = mBoxView.getLayoutLocation(boxPosition, itemWidth);
                                    layoutParams.x = location[0];
                                    layoutParams.y = location[1];
                                    requestLayout();
                                    layoutParams.isDrag = false;
                                    requestLayout();
                                    v.setSelected(true);
                                    mBoxView.getBoxArray()[boxPosition] = itemView.getMyId();
                                    printlnBox();
                                    return false;
                                } else {
                                    //原来有位置，但是当前放不下，就回到原来位置

                                    for (int j = 0; j < boxList.size(); j++) {
                                        mBoxView = boxList.get(j);
                                        int position = mBoxView.getBoxPostion(itemView.getMyId());
                                        if (position >= 0) {
                                            //mBoxView.getBoxArray()[position] = -1;
                                            //飞到原来的盒子当中
                                            layoutParams.isDrag = true;
                                            int[] location =
                                                    mBoxView.getLayoutLocation(boxPosition, itemWidth);
                                            layoutParams.x = location[0];
                                            layoutParams.y = location[1];
                                            requestLayout();
                                            layoutParams.isDrag = false;
                                            requestLayout();
                                            v.setSelected(true);
                                            mBoxView.getBoxArray()[position] = itemView.getMyId();
                                            printlnBox();
                                            return false;
                                        }
                                    }
                                }
                            }
                            //原来有位置 ，但是当前在盒子外面， 就回到底部，将原来位置清空
                            if (v.isSelected()) {
                                for (int j = 0; j < boxList.size(); j++) {
                                    mBoxView = boxList.get(j);
                                    int position = mBoxView.getBoxPostion(itemView.getMyId());
                                    if (position >= 0) {
                                        mBoxView.getBoxArray()[position] = -1;
                                    }
                                }
                            }
                            printlnBox();
                            v.setSelected(false);
                            layoutParams.x = itemView.getSrcX();
                            layoutParams.y = itemView.getSrcY();
                            requestLayout();

                            //MyValueAnimator.create(itemView.getX(), itemView.getSrcX(),
                            //        itemView.getY(), itemView.getSrcY(), itemView).setDuration(1000).start();

                            //} else {
                            //
                            //}

                            ////单个盒子的处理方式
                            //BoxView mBoxView = boxList.get(j);
                            //if (onOver(v, mBoxView )) {
                            //    int boxCount;
                            //    if (itemView.isSelected()) {
                            //        boxCount = mBoxView.getBoxPostion(itemView.getMyId());
                            //    } else {
                            //        boxCount = mBoxView.getEmptyPosition();
                            //    }
                            //    layoutParams.isDrag = true;
                            //    layoutParams.x = (int) mBoxView.getX() + boxCount * v.getWidth();
                            //    layoutParams.y = (int) mBoxView.getY() + mBoxView.getHeight() - v.getHeight();
                            //    requestLayout();
                            //    layoutParams.isDrag = false;
                            //    requestLayout();
                            //    v.setSelected(true);
                            //    boxArray[boxCount] = itemView.getMyId();
                            //} else {
                            //    if (itemView.isSelected()) {
                            //        int boxCount = getBoxPostion(itemView.getMyId());
                            //        boxArray[j][boxCount] = 0;
                            //    }
                            //    //如果不在
                            //    v.setSelected(false);
                            //    layoutParams.x = itemView.getSrcX();
                            //    layoutParams.y = itemView.getSrcY();
                            //    requestLayout();
                            //}

                            //locationOccupiedView(mDropView.getLeft() + (mLastItemIsLeft ? ((mDropView.getWidth() / 2) + (mDropView.getWidth() / 4))
                            //                : mDropView.getWidth() - ((mDropView.getWidth() / 2) + (mDropView.getWidth() / 4))),
                            //        (float) (mDropView.getTop() + (mDropView.getHeight() * .8)));
                            break;
                    }

                    return true;
                }
            });
        }

        //设置出现 消失动画
        LayoutTransition transition = new LayoutTransition();

        PropertyValuesHolder appearingScaleX =
                PropertyValuesHolder.ofFloat("scaleX", 0f, 1f, 1.2f, 1f);
        PropertyValuesHolder appearingScaleY =
                PropertyValuesHolder.ofFloat("scaleY", 0f, 1f, 1.2f, 1f);
        PropertyValuesHolder appearingAlpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        ObjectAnimator mAnimatorAppearing =
                ObjectAnimator.ofPropertyValuesHolder(this, appearingAlpha, appearingScaleX,
                        appearingScaleY);
        //为LayoutTransition设置动画及动画类型
        //mAnimatorAppearing.setDuration(5000);
        transition.setAnimator(LayoutTransition.APPEARING, mAnimatorAppearing);

        PropertyValuesHolder disScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1.0f, 0f);
        PropertyValuesHolder disScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1.0f, 0f);
        PropertyValuesHolder disAlpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        ObjectAnimator mAnimatorDis =
                ObjectAnimator.ofPropertyValuesHolder(this, disAlpha, disScaleX, disScaleY);
        //mAnimatorDis.setDuration(1000);
        transition.setAnimator(LayoutTransition.DISAPPEARING, mAnimatorDis);

        transition.setDuration(1000);
        setLayoutTransition(transition);
    }

    public void startShow() {
        //post(new Runnable() {
        //    @Override
        //    public void run() {
        //        initViews();
        //    }
        //});
    }

    private boolean onOver(View iv1, View iv2) {
        Rect currentViewRect = new Rect();
        iv1.getGlobalVisibleRect(currentViewRect);

        Rect secondRect = new Rect();
        iv2.getGlobalVisibleRect(secondRect);
        boolean onOver = Rect.intersects(currentViewRect, secondRect);
        return onOver;
    }

    private void printlnBox() {
        for (int i = 0; i < boxList.size(); i++) {
            boxList.get(i).getBoxArray();
            Log.w("test", " box" + i + " : " + boxList.get(i).toBoxString());
        }
    }

    private int[] mBoxValueArray;
    private int mEmptyBoxIndex;

    public boolean isRight() {
        if (mBoxValueArray == null) {
            return false;
        }
        int rightValue = mBoxValueArray[mEmptyBoxIndex];
        int nowSize = boxList.get(mEmptyBoxIndex).getSize();
        return rightValue == nowSize;
    }

    public void hideAnimation() {
        for (int i = 0; i < boxItemList.size(); i++) {
            removeView(boxItemList.get(i));
        }
        boxItemList.clear();
    }

    public void initBox(final int[] boxValueArray, final int emptyIndex) {
        mBoxValueArray = boxValueArray;
        mEmptyBoxIndex = emptyIndex;

        //mBackgourRes = getFrultBackgroud();

        //所有位置归为
        MyLayoutParams layoutParams;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(false);
            layoutParams = (MyLayoutParams) list.get(i).getLayoutParams();
            layoutParams.x = list.get(i).getSrcX();
            layoutParams.y = list.get(i).getSrcY();
        }
        requestLayout();

        //ScaleAnimation scaleAnimation  = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        //scaleAnimation.setDuration(500);
        //
        //for (int i=0;i< boxItemList.size(); i++) {
        //    boxItemList.get(i).startAnimation(scaleAnimation);
        //}

        //Observable.timer(500, TimeUnit.MILLISECONDS)
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .subscribe(new Action1<Long>() {
        //            @Override
        //            public void call(Long aLong) {

        ItemView apple;
        for (int j = 0; j < boxList.size(); j++) {

            int boxValue = boxValueArray[j];
            //为0的可以放水果， 不为0 表示不可以拖入
            boxList.get(j).setEmptyBox(j == emptyIndex);
            boxList.get(j).init();

            if (j == emptyIndex) {
                //空的盒子，不用添加view
                continue;
            }

            //给每个box 初始化水果个数
            for (int i = 0; i < boxValue; i++) {
                apple = new ItemView(getContext());
                apple.setImageResource(mBackgourRes);
                MyLayoutParams lp = new MyLayoutParams(itemWidth, itemWidth);
                int[] position = boxList.get(j).getLayoutLocation(i, itemWidth);
                lp.x = position[0];
                lp.y = position[1];
                apple.setSrcX((int) lp.x);
                apple.setSrcY((int) lp.y);

                apple.setLayoutParams(lp);

                //apple.setAlpha(0.2f);
                //apple.startAnimation(animationSet);
                addView(apple);

                boxItemList.add(apple);
            }
        }
    }

    private @IdRes
    int getFrultBackgroud() {
        Random random = new Random();
        int value = random.nextInt(4);
        int resId;
        switch (value) {
            case 0:
                resId = R.mipmap.fruit_1;
                break;
            case 1:
                resId = R.mipmap.fruit_2;
                break;
            case 2:
                resId = R.mipmap.fruit_3;
                break;
            case 3:
            default:
                resId = R.mipmap.fruit_4;
                break;
        }
        return resId;
    }
}
