package com.example.zhujiang.myapplication.game.GameWalkActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import com.example.zhujiang.myapplication.R;
import com.example.zhujiang.myapplication.game.MyLayoutParams;
import com.example.zhujiang.myapplication.game.SoundManager;
import com.example.zhujiang.myapplication.utils.DensityUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author zhuj 2018/9/11 下午6:02.
 */
public class WalkViewBG extends ViewGroup {

    private final static String TAG = "walkView";

    private List<WlakView> list = new ArrayList();

    /**
     * 上一个位置
     */
    private int mLastPostion = -1;

    /**
     * 单个宽高
     */
    private int itemWidth = 100;
    /**
     * 行
     */
    private int row = 2;
    /**
     * 列
     */
    private int column = 3;
    /**
     * 是否允许触摸移动
     */
    private boolean mEnableWalk = true;
    /**
     * 终点
     */
    private int successPosition;
    /**
     * 正确的移动步骤
     */
    private WlakView mWlakFrom, mWlakTo, mWlakArrows;

    /**
     * 行走的路径
     */
    private List<Integer> mMoveList = new ArrayList<>();

    private final int FRUIT_COUNT = 4;

    public WalkViewBG(Context context) {
        super(context);
    }

    public WalkViewBG(Context context, AttributeSet attrs) {
        super(context, attrs);
        itemWidth = DensityUtil.dip2px(context, 50);
        initViews();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < list.size(); i++) {
            WlakView boxView = list.get(i);
            MyLayoutParams boxPa = (MyLayoutParams) boxView.getLayoutParams();
            boxView.layout(boxPa.x, boxPa.y, boxPa.x + boxView.getLayoutParams().width,
                    boxPa.y + boxView.getLayoutParams().height);
        }

        MyLayoutParams paFrom = (MyLayoutParams) mWlakFrom.getLayoutParams();
        mWlakFrom.layout(paFrom.x, paFrom.y, paFrom.x + mWlakFrom.getLayoutParams().width,
                paFrom.y + mWlakFrom.getLayoutParams().height);

        MyLayoutParams paTo = (MyLayoutParams) mWlakTo.getLayoutParams();
        mWlakTo.layout(paTo.x, paTo.y, paTo.x + mWlakTo.getLayoutParams().width,
                paTo.y + mWlakTo.getLayoutParams().height);

        MyLayoutParams paArrow = (MyLayoutParams) mWlakArrows.getLayoutParams();
        mWlakArrows.layout(paArrow.x, paArrow.y, paArrow.x + mWlakArrows.getLayoutParams().width,
                paArrow.y + mWlakArrows.getLayoutParams().height);
    }

    @Override
    protected MyLayoutParams generateDefaultLayoutParams() {
        return new MyLayoutParams(0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置最大高度， 固定， 1.5是底部 from to ， 0。5的边距
        setMeasuredDimension(column * itemWidth, (int) ((row + 1.5) * itemWidth));
        //setMeasuredDimension(column * itemWidth, row * itemWidth);
    }

    private void initViews() {
        Random random = new Random();
        WlakView walkView;
        MyLayoutParams lpBox;
        for (int i = 0; i < row * column; i++) {
            walkView = new WlakView(getContext());
            walkView.setBackgroundResource(R.drawable.btn_walk_selector);
            lpBox = new MyLayoutParams(itemWidth, itemWidth);
            lpBox.x = itemWidth * (i % column);
            //表盘竖直居中， 1.5是 底部from  2 to 间距， 上下对称
            lpBox.y = (int) (itemWidth * (i / column));
            walkView.setLayoutParams(lpBox);

            //int value = random.nextInt(2);
            walkView.setStep(0);
            //walkView.setValue(value);
            addView(walkView);

            list.add(walkView);
        }

        mWlakFrom = new WlakView(getContext());
        mWlakFrom.setBackgroundResource(R.drawable.btn_walk_selector);
        lpBox = new MyLayoutParams(itemWidth, itemWidth);
        lpBox.x = 0;
        //表盘竖直居中， 1.5是 底部from  2 to 间距， 上下对称， 0。5是间距
        lpBox.y = (int) (itemWidth * (row + 0.5));
        mWlakFrom.setLayoutParams(lpBox);
        addView(mWlakFrom);

        mWlakArrows = new WlakView(getContext());
        mWlakArrows.setBackgroundResource(R.mipmap.game_walk_arrow_right);
        lpBox = new MyLayoutParams(itemWidth/2, itemWidth/2);
        lpBox.x = (int) (itemWidth * 1);
        lpBox.y = (int) (itemWidth * (row + 0.75));
        mWlakArrows.setLayoutParams(lpBox);
        addView(mWlakArrows);

        mWlakTo = new WlakView(getContext());
        mWlakTo.setBackgroundResource(R.drawable.btn_walk_selector);
        lpBox = new MyLayoutParams(itemWidth, itemWidth);
        lpBox.x = (int) (itemWidth * 1.5);
        lpBox.y = (int) (itemWidth * (row + 0.5));
        mWlakTo.setLayoutParams(lpBox);
        addView(mWlakTo);



        //随机生成 水果路线，
        int fromValue = random.nextInt(FRUIT_COUNT);
        int toValue = random.nextInt(FRUIT_COUNT);
        while (toValue == fromValue) {
            toValue = random.nextInt(FRUIT_COUNT);
        }
        mWlakFrom.setValue(fromValue);
        mWlakTo.setValue(toValue);

        final List<Integer> routerList = initRouter();

        // from to from to 这样奇数 偶数 循环
        for (int i = 0; i < routerList.size(); i++) {
            if (i % 2 == 0) {
                list.get(routerList.get(i)).setValue(fromValue);
            } else {
                list.get(routerList.get(i)).setValue(toValue);
            }
        }
        successPosition = routerList.get(routerList.size() - 1);
        //list.get(successPosition).setBackgroundResource(R.drawable.ic_launcher);
        Set<Integer> set = new HashSet<>(routerList);
        for (int i = 0; i < list.size(); i++) {
            if (!set.contains(i)) {
                list.get(i).setValue(random.nextInt(FRUIT_COUNT));
            }
        }

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (!mEnableWalk) {
                    return false;
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mGameListener != null) {
                        mGameListener.onDrawing(true);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    if (mGameListener != null) {
                        mGameListener.onDrawing(false);
                    }
                }

                int x = (int) event.getX();
                int y = (int) event.getY();

                int position = y / itemWidth * column + x / itemWidth;

                //如果是同一个位置，就直接返回
                if (position == mLastPostion) {
                    return true;
                }
                if (position < 0 || position >= list.size()) {
                    return true;
                }

                //按下时候，必须跟着原来的 位置继续
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mLastPostion != -1) {
                        if (list.get(position).getStep() != 1) {
                            if (mGameListener != null) {
                                //alphaAnimation(list.get(mLastPostion));
                                mGameListener.onWalkBack("从当前位置继续走");
                            }
                            return false;
                        }
                    } else {
                        if (position != 0) {
                            if (mGameListener != null) {
                                alphaAnimation(list.get(0));
                                mGameListener.onWalkBack("请从第一格开始");
                            }
                            return false;
                        }
                    }
                }

                Log.w("test", "walk " + mLastPostion + " now " + position);
                if (!isRightWalk(mLastPostion, position)) {
                    if (mGameListener != null) {
                        //alphaAnimation(list.get(0));
                        mGameListener.onWalkBack("只能横着 或 竖着走");
                    }
                    return false;
                }

                WlakView mNowWalkView = list.get(position);

                //已经走过了，撤退事件
                if (mNowWalkView.getStep() == 2) {
                    if (mGameListener != null) {
                        mGameListener.onWalkBack("不允许撤退");
                    }
                    //alphaAnimation(mNowWalkView);
                    return false;
                }

                mMoveList.add(position);
                mNowWalkView.setSelected(true);
                mNowWalkView.setStep(1);
                alphaAnimationNow();
                SoundManager.getInstance(getContext()).play(SoundManager.VIDEO_MOVE);
                if (mLastPostion != -1) {
                    WlakView last = list.get(mLastPostion);
                    if (last.getAnimation() != null) {
                        last.getAnimation().cancel();
                    }
                    //注意传入的int  float
                    list.get(mLastPostion).setAlpha(1f);
                    list.get(mLastPostion).setStep(2);
                }

                mLastPostion = position;

                if (mLastPostion == successPosition) {
                    if (mGameListener != null) {

                        mGameListener.onWalkRounter(checkRouter());
                    }
                    mEnableWalk = false;
                    return false;
                }

                return true;
            }
        });
    }

    //private boolean onOver(View iv1, View iv2) {
    //    Rect currentViewRect = new Rect();
    //    iv1.getGlobalVisibleRect(currentViewRect);
    //
    //    Rect secondRect = new Rect();
    //    iv2.getGlobalVisibleRect(secondRect);
    //    boolean onOver = Rect.intersects(currentViewRect, secondRect);
    //    return onOver;
    //}

    private WalkGameListener mGameListener;

    public void setWalkGameListener(WalkGameListener listener) {
        mGameListener = listener;
    }

    private boolean isRightWalk(int lastPostion, int nowPosition) {
        int disValue = Math.abs(nowPosition - lastPostion);
        if (disValue == 1) {
            //必须是同一列， 才算正确，相当于就是往右移动1
            return nowPosition / column == lastPostion / column;
        } else if (disValue == column) {
            //或者上下移动
            return true;
        }
        return false;
    }

    public void resetAll() {
        Random random = new Random();

        //棋盘个数发生了变化
        onLevelUp();
        setMeasuredDimension(column * itemWidth, (int) ((row + 1.5f) * itemWidth));
        removeAllViews();
        list.clear();
        //因为数据发生了变化，重新画图
        WlakView walkView;
        MyLayoutParams lpBox;
        for (int i = 0; i < row * column; i++) {
            walkView = new WlakView(getContext());
            walkView.setBackgroundResource(R.drawable.btn_walk_selector);
            lpBox = new MyLayoutParams(itemWidth, itemWidth);
            lpBox.x = itemWidth * (i % column);
            lpBox.y = (int) (itemWidth * (i / column));
            walkView.setLayoutParams(lpBox);

            //int value = random.nextInt(2);
            walkView.setStep(0);
            walkView.setSelected(false);
            //walkView.setValue(value);
            addView(walkView);

            list.add(walkView);
        }

        mWlakFrom = new WlakView(getContext());
        mWlakFrom.setBackgroundResource(R.drawable.btn_walk_selector);
        lpBox = new MyLayoutParams(itemWidth, itemWidth);
        lpBox.x = 0;
        lpBox.y = (int) (itemWidth * (row + 0.5f));
        mWlakFrom.setLayoutParams(lpBox);
        addView(mWlakFrom);

        mWlakArrows = new WlakView(getContext());
        mWlakArrows.setBackgroundResource(R.mipmap.game_walk_arrow_right);
        lpBox = new MyLayoutParams(itemWidth/2, itemWidth/2);
        lpBox.x = (int) (itemWidth * 1);
        lpBox.y = (int) (itemWidth * (row + 0.75));
        mWlakArrows.setLayoutParams(lpBox);
        addView(mWlakArrows);


        mWlakTo = new WlakView(getContext());
        mWlakTo.setBackgroundResource(R.drawable.btn_walk_selector);
        lpBox = new MyLayoutParams(itemWidth, itemWidth);
        lpBox.x = (int) (itemWidth * 1.5);
        lpBox.y = (int) (itemWidth * (row + 0.5f));
        mWlakTo.setLayoutParams(lpBox);
        addView(mWlakTo);

        //如果个数不发生变化，就用下面这套
        //WlakView wlakView;
        //for (int i = 0; i < list.size(); i++) {
        //    wlakView = list.get(i);
        //    //wlakView.setValue(random.nextInt(2));
        //    wlakView.setStep(0);
        //    wlakView.setSelected(false);
        //    wlakView.setAlpha(1);
        //    wlakView.setBackgroundResource(R.drawable.btn_walk_selector);
        //}

        int fromValue = random.nextInt(FRUIT_COUNT);
        int toValue = random.nextInt(FRUIT_COUNT);
        while (toValue == fromValue) {
            toValue = random.nextInt(FRUIT_COUNT);
        }
        mWlakFrom.setValue(fromValue);
        mWlakTo.setValue(toValue);

        List<Integer> routerList = initRouter();
        for (int i = 0; i < routerList.size(); i++) {
            if (i % 2 == 0) {
                list.get(routerList.get(i)).setValue(fromValue);
            } else {
                list.get(routerList.get(i)).setValue(toValue);
            }
        }
        successPosition = routerList.get(routerList.size() - 1);
        //list.get(successPosition).setBackgroundResource(R.drawable.ic_launcher);
        Set<Integer> set = new HashSet<>(routerList);
        for (int i = 0; i < list.size(); i++) {
            if (!set.contains(i)) {
                //给非路线图， 随机生成一种水果
                int value = random.nextInt(FRUIT_COUNT);
                //以下是否有必要，修改跟from to都不一致，或者不做限制
                while (value == fromValue) {
                    value = random.nextInt(FRUIT_COUNT);
                }
                list.get(i).setValue(value);
            }
        }

        mMoveList.clear();
        mLastPostion = -1;
        mEnableWalk = true;
    }

    public void alphaAnimation(View view) {

        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.5f);
        alphaAnimation.setDuration(800);
        alphaAnimation.setRepeatCount(3);
        view.startAnimation(alphaAnimation);
    }

    ValueAnimator alphaAnimation;

    public void alphaAnimationNow() {
        if (alphaAnimation == null) {
            ValueAnimator alphaAnimation = ObjectAnimator.ofFloat(0, 2);
            alphaAnimation.setDuration(3000);
            alphaAnimation.setRepeatCount(Animation.INFINITE);
            alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float f = (float) animation.getAnimatedValue();
                    if (mLastPostion >= 0) {
                        list.get(mLastPostion).setAlpha(Math.abs(1f - f));
                    }
                }
            });
            alphaAnimation.start();
        }
    }

    /**
     * 获得正确的行走路线
     */
    private List<Integer> initRouter() {

        Random random = new Random();
        List<Integer> set = new ArrayList<>();
        int start = 0;

        //结束位置， 随机一行， 然后最后一列
        //int end = (random.nextInt(row)) * column + column - 1;
        //修改为固定位置，第一排最后一个
        int end = column + column - 1;

        //中间位置， 需要先走到中间位置，然后起点 、终点的最短路线，连接
        int middle = 0;
        while (middle == 0 || middle == end) {
            middle = random.nextInt(row * column);
            //限制不能出现在最后一列，（有中间点，是终点的下面，然后路线已经走过，不能撤退，导致无法到终点）
            if (middle % column == column - 1) {
                middle = middle - 1;
            }
        }

        //加起点
        set.add(start);

        //到中间位置， 需要右移动 、下移动次数
        int rightStep = (middle - start) % column;
        int verStep = (middle - start) / column;
        int next = start;
        //还未走到中间位置，就一直走
        while (rightStep > 0 || verStep > 0) {
            //随机生成，右走 或 下走
            boolean isRight = random.nextBoolean();
            if (isRight && rightStep > 0) {
                //右走，次数减1
                next += 1;
                rightStep--;
            } else if (verStep > 0) {
                //如果还有往下走次数
                next += column;
                verStep--;
            } else {
                //不满足的走路，比如随机生成了往下走，但已经达到往下走次数
                //或者  随机生成往右走，但已经到达了往右次数
                //这里不做 回退路线
                continue;
            }
            set.add(next);
        }

        //从中间点， 到达终点， 右走次数、 上下移动次数
        rightStep = (end % column - middle % column);
        verStep = (end / column - middle / column);
        while ((rightStep != 0 || verStep != 0)) {
            boolean isRight = random.nextBoolean();
            //往右走
            if (isRight && rightStep > 0) {
                next += 1;
                rightStep--;
                set.add(next);
            } else if (verStep != 0) {
                //上下移动， 这里可能会 有往上移动
                int newValue = next;
                if (verStep > 0) {
                    newValue += column;
                } else {
                    newValue -= column;
                }
                //避免回退,检查当前路线中是否存在
                boolean isContains = false;
                for (int a : set) {
                    if (a == newValue) {
                        isContains = true;
                        break;
                    }
                }
                //不存在就，就说明移动有效
                if (!isContains) {
                    //记录移动的值
                    if (newValue > next) {
                        verStep--;
                    } else {
                        verStep++;
                    }
                    set.add(newValue);
                    next = newValue;
                }
            }
        }
        Log.d(TAG, "walk router:" + set.toString());
        return set;
    }

    /**
     * 获得行走的胜利位置
     */
    //public int[] getEndPosition() {
    //
    //    int[] location = new int[2];
    //    list.get(successPosition).getLocationInWindow(location);
    //    //因为躲在树后面， 左移动画 一个宽度
    //    location[0] = (int) (location[0] + itemWidth - getWidth());
    //    //这里因为位置发生了变化，
    //    return location;
    //}
    public int getSuccessY() {
        return (successPosition / column) * itemWidth;
    }

    /**
     * 检查路径， 是否正确，是否按规划路线行走
     */
    private List<PositionBean> checkRouter() {
        if (alphaAnimation != null && alphaAnimation.isRunning()) {
            alphaAnimation.cancel();
        }
        List<PositionBean> positionList = new ArrayList<>();
        PositionBean positionBean;
        for (int i = 0; i < mMoveList.size(); i++) {
            positionBean = new PositionBean();
            //走动的位置
            int position = mMoveList.get(i);
            //保存的值
            int value = list.get(position).getValue();
            //判断顺序，偶数from  奇数to
            boolean isRight;
            if (i % 2 == 0) {
                isRight = (value == mWlakFrom.getValue());
            } else {
                isRight = (value == mWlakTo.getValue());
            }
            positionBean.setPosition(position);
            positionBean.setEnd(false);
            positionBean.setSuccess(isRight);
            positionBean.setX((int) list.get(position).getX());
            positionBean.setY((int) list.get(position).getY());
            positionList.add(positionBean);
            if (!isRight) {
                list.get(position).setBackgroundResource(R.drawable.bg_game_walk_step2);
                alphaAnimation(list.get(position));
                break;
            }
        }

        positionList.get(positionList.size() - 1).setEnd(true);
        return positionList;
    }

    public void cleanMoveRouter() {
        for (Integer position : mMoveList) {
            list.get(position).setStep(0);
            list.get(position).setAlpha(1f);
            list.get(position).setSelected(false);
        }
        mMoveList.clear();
        mLastPostion = -1;
    }

    private void onLevelUp() {
        //游戏难度提升
        row++;
        column++;
        if (row > 4) {
            row = 4;
        }
        if (column > 6) {
            column = 6;
        }
    }
}
