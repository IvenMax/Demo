package com.iven.app.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.iven.app.R;

/**
 * @author Iven
 * @date 2017/2/27 10:41
 * @Description
 */

public class BouncingView extends View {
    private Path mPath;
    private Paint mPaint;
    private int mArcHeight = 0;//当前弧度高度
    private int mMaxArcHeight;//最大弧度高度值
    private Status mStatus = Status.STATUS_NONE;
    private AnimationListener animationListener;

    public BouncingView(Context context) {
        super(context);
        init();
    }

    public BouncingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BouncingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.white));
        mMaxArcHeight = 150;
    }

    private enum Status {
        STATUS_NONE, STATUS_UP, STATUS_DOWN;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int currentPointY = 0;
        switch (mStatus) {
            case STATUS_NONE:
                currentPointY = 0;
                break;
            case STATUS_DOWN:
                currentPointY = mMaxArcHeight;
                break;
            case STATUS_UP:
                currentPointY = (int) ((getHeight() * (1 - (float) mArcHeight / mMaxArcHeight)) + mMaxArcHeight);
                break;
        }
        int y = currentPointY - mArcHeight;
        mPath.reset();
        /**
         * 二阶贝塞尔曲线   三个点
         * 起始点(0,currentY)
         * 拐点(getWidth()/2,currentY-mArcHeight)
         * 终点(getWidth(),currentY)
         */
        mPath.moveTo(0, currentPointY);
        mPath.quadTo(getWidth() / 2, y, getWidth(), currentPointY);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    //显示
    public void show() {
        if (animationListener != null) {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //显示数据
                    animationListener.showContent();
                }
            }, 600);
        }
        ;
        mStatus = Status.STATUS_UP;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mMaxArcHeight);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                if (mArcHeight == mMaxArcHeight) {
                    bounce();
                }
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    //会弹
    public void bounce() {
        mStatus = Status.STATUS_DOWN;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mMaxArcHeight, 0);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcHeight = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    public interface AnimationListener {
        void showContent();
    }

    public void setAnimationListener(AnimationListener listener) {
        this.animationListener = listener;
    }
}
