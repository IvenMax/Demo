package com.iven.app.view.viewdrag;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iven.app.tools.L;
import com.iven.app.tools.T;

/**
 * @author Iven
 * @date 2017/1/15 16:30
 */

public class ViewDragLayout extends LinearLayout {
    private static final String TAG = "zpy_ViewDragLayout";
    private ViewDragHelper mDragHelper;
    private View mContentView;//layout中的子view
    private Point locationPoint = new Point();//初始位置的坐标
    private int mWidth, mHeight;
    private Context context;


    public ViewDragLayout(Context context) {
        super(context);
        this.context = context;
    }

    public ViewDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e(TAG, "onFinishInflate: 22" + "行 = " + "onFinishInflate......");
        if (getChildCount() == 0) {
            throw new RuntimeException("must have one child view");
        }
        if (getChildCount() > 1) {
            throw new RuntimeException("child view should be one");
        }
        mContentView = getChildAt(0);//默认只有一个child,并且就是ListView
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        locationPoint.x = mContentView.getLeft();
        locationPoint.y = mContentView.getTop();
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        L.e(TAG, "onLayout: 53" + "行     x = " + locationPoint.x + "     y = " + locationPoint.y);
        L.e(TAG, "onLayout: 53" + "行     mWidth = " + mWidth + "     mHeight = " + mHeight);
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP);//设置顶部可以触摸发生事件
    }


    /**
     * 自定义回调方法处理事件
     */
    private class DragHelperCallback extends ViewDragHelper.Callback {
        //返回true,表示处理该view的拖拽事件
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //            return child == mContentView;
            return true;
        }

        //边界触摸的时候回调
        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            T.showShort(getContext(), "顶部触摸...");
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mContentView.getWidth();
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            return newLeft;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mContentView.getHeight();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            int hhhh = top;
            if (top > 200) {
                hhhh = 200;
            } else if (top < 0) {
                hhhh = 0;
            }
            return hhhh;
        }

        //捕获到children的时候触发
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            if (capturedChild == mContentView) {
                Toast.makeText(context, "capture...", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "onViewCaptured: 165" + "行 = ");
            }
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        //手指释放时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (releasedChild == mContentView) {
                mDragHelper.settleCapturedViewAt(locationPoint.x, locationPoint.y);
                invalidate();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        //上边界触摸滑动的时候，让child跟着动
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            //            mDragHelper.captureChildView(mContentView,pointerId);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }
    }

    //处理事件拦截
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }
    //处理touch事件


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
