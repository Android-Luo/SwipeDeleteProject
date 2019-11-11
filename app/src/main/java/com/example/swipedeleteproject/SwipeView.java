package com.example.swipedeleteproject;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;


/**
 * date on  2019/11/7
 * time on  11:14
 * author LUO
 */
public class SwipeView extends ViewGroup {

    private static final String TAG = "SwipeView";

    //Scroller滑动类
    private Scroller mScroller;

    //内容布局的View
    private View contentView;

    //功能布局的View
    private View deleteView;

    //缓存当前滑动的View
    private View mCacheView;

    //判断拖动的最小移动像素数
    private int mTouchSlop;

    //手指按下时的屏幕坐标
    private float mXDown;

    //记录上一次x移动的距离
    private float mXLastMove;

    //最终滑动的距离
    private int mXMove;

    //可以滑动的最大距离
    private int mXaxLength;


    public SwipeView(Context context) {
        this(context, null);
    }

    public SwipeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //实例化Scroller类
        mScroller = new Scroller(context);
        //得到TouchSlop的值
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i =0; i< childCount; i++) {
            View childView = getChildAt(i);
            childView.setClickable(true);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            contentView = getChildAt(0);
            deleteView = getChildAt(1);
            contentView.layout(0, 0, contentView.getMeasuredWidth(), contentView.getMeasuredHeight());
            deleteView.layout(contentView.getMeasuredWidth(), 0, deleteView.getMeasuredWidth() + contentView.getMeasuredWidth(), deleteView.getMeasuredHeight());

            //得到可以滑动的最大距离，也就是deleteView的宽度
            mXaxLength = deleteView.getMeasuredWidth();
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //得到按下时的坐标
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                Log.i(TAG, "onInterceptTouchEvent" + "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onInterceptTouchEvent" + "ACTION_MOVE");
                float x = ev.getRawX();
                //如果移动的距离大于TouchSlop，则认为滑动了
                if (Math.abs(x - mXDown) > mTouchSlop) {
                    //拦截孩子的触摸事件，分给此viewGroup
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onInterceptTouchEvent" + "ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "onInterceptTouchEvent" + "ACTION_CANCEL");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "onTouchEvent" + "ACTION_MOVE");
                float x = event.getRawX();
                mXMove = (int)(mXLastMove - x);
                if (getScrollX() > 0) {
                    requestDisallowInterceptTouchEvent(true);
                }
                scrollBy(mXMove, 0);
                //控制左右边界
                Log.i("View", "mXMove= " + mXMove + " " + getScrollX() + " " + event.getRawX() + " " + mXDown );
                //判断滑动边界
                if (getScrollX() < 0) {
                    scrollTo(0, 0);
                } else if (getScrollX() > mXaxLength) {
                    scrollTo(mXaxLength, 0);
                }
                mXLastMove = event.getRawX();
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent" + "ACTION_UP");
                //手指抬起，滑动距离超过item一半的话，自动滑开整个item
                if (getScrollX() > mXaxLength / 2) {
                    openSwipeView();
                    mCacheView = SwipeView.this;
                } else {
                    closeSwipeView();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "onTouchEvent" + "ACTION_CANCEL");
                break;
        }

        return super.onTouchEvent(event);
    }

    //用动画的方式打开视图
    private void openSwipeView() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(getScrollX(), mXaxLength);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float)animation.getAnimatedValue();
                scrollTo((int) currentValue, 0);
            }
        });
        valueAnimator.setDuration(150);
        valueAnimator.start();
    }

    //用动画的方式关闭视图
    private void closeSwipeView() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(getScrollX(), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float)animation.getAnimatedValue();
                scrollTo((int) currentValue, 0);
            }
        });
        valueAnimator.setDuration(150);
        valueAnimator.start();
    }

//
//    @Override
//    public void computeScroll() {
//        super.computeScroll();
//        //重写computeScroll()方法，并在内部完成平滑滚动的逻辑
//        if (mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//        }
//    }
}
