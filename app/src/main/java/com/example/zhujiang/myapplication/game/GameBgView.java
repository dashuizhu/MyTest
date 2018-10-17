package com.example.zhujiang.myapplication.game;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.zhujiang.myapplication.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuj 2018/9/11 下午6:02.
 */
public class GameBgView extends ViewGroup {

    //private ImageView mDropView;
    private int mLastX, mLastY;

    //private ImageView mBoxView;

    private List<ItemView> list = new ArrayList();

    private List<BoxView> boxList = new ArrayList<>();
    //private List<View> boxlist = new ArrayList();
    //private ImageView boxArray = new ImageView[3];

    //private int[] boxArray = new int[9];

    private int mBox1Number = 0;
    private int mBox2Number = 0;

    public GameBgView(Context context) {
        super(context);
    }

    public GameBgView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        BoxView mBoxView;
        MyLayoutParams lpBox;
        for (int i = 0; i < 3; i++) {
            mBoxView = new BoxView(getContext());
            mBoxView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.toolbar_spilt_line));
            lpBox = new MyLayoutParams(300, 100);
            lpBox.x = 100 + 400 * i;
            lpBox.y = 240;
            mBoxView.setLayoutParams(lpBox);
            addView(mBoxView);

            boxList.add(mBoxView);
        }



        ItemView apple;
        for (int i = 1; i < 11; i++) {
            apple = new ItemView(getContext());
            apple.setMyId(i);
            apple.setImageResource(R.mipmap.ssdk_oks_classic_wechat);
            MyLayoutParams lp = new MyLayoutParams(100, 100);
            lp.x = 10 + i * 100;
            lp.y = 10 + 500;
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
                                if (onOver(v, mBoxView)) {
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
                                            if (position>=0) {
                                                oldBox.getBoxArray()[position] = -1;
                                                break;
                                            }
                                        }
                                    }

                                    layoutParams.isDrag = true;
                                    layoutParams.x =
                                            (int) mBoxView.getX() + boxPosition * v.getWidth();
                                    layoutParams.y = (int) mBoxView.getY() + mBoxView.getHeight()
                                            - v.getHeight();
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
                                        if (position>=0) {
                                            //mBoxView.getBoxArray()[position] = -1;
                                            //飞到原来的盒子当中
                                            layoutParams.isDrag = true;
                                            layoutParams.x =
                                                    (int) mBoxView.getX() + position * v.getWidth();
                                            layoutParams.y = (int) mBoxView.getY() + mBoxView.getHeight()
                                                    - v.getHeight();
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
                                    if (position>=0) {
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
        for (int i=0; i<boxList.size(); i++) {
            boxList.get(i).getBoxArray();
            Log.w("test", " box"+i +" : " + boxList.get(i).toBoxString());
        }
    }


    public void reset(int box1, int box2) {

    }

}
