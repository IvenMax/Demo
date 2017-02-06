package com.iven.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author Iven
 * @date 2017/2/6 10:52
 * @Description
 */

public class FloatView extends View {
    private static final String TAG = "zpy_FloatView";

    //边界线的颜色(灰色)
    private static final String BORDERLINECOLOR = "#666666";
    //虚线颜色
    private static final String DASHLINECOLOR = "#999999";
    //文本颜色
    private static final String TEXTCOLOR = "#66666666";
    //底部日期的背景色
    private static final String BOTTOMTIMEBGCOLOR = "#aaE2EBF9";
    //默认边距(上下左右)
    private final int MARGINLEFT = 30;
    private final int MARGINRIGHT = 30;
    private final int MARGINTOP = 30;
    private final int MARGINBOTTOM = 30;
    //默认画笔宽度
    private int stockWidth = 3;
    private int mWidth, mHeight;//视图的宽高
    private int verticalspace;//竖直方向上每两个横线之间的间距
    private int leftTextSize = 30;//默认文本大小

    private boolean hasDate = true;//是否已经设置数据
    private String[] leftValues;
    private final String COLOR_BLUE = "#429ae6";
    //背景渐变色
    private final String COLOR_BLUE_ALPHA = "#11429ae6";

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//硬件加速(三种方法，详见百度)
        //        leftValues = new String[7];
        leftValues = new String[]{"+15000.00", "+10000.00", "+5000.00", "0.00", "-5000.00", "-10000.00", "-15000.00"};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w - MARGINLEFT - MARGINRIGHT;
        mHeight = h - MARGINTOP - MARGINBOTTOM;
        Log.i(TAG, "onSizeChanged:     mWidth = " + mWidth + "     mHeight  = " + mHeight);
        Log.i(TAG, "onSizeChanged:     mWidth + MARGINLEFT = " + (mWidth + MARGINLEFT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorders(canvas);
        drawLeftText(canvas);

    }


    /**
     * 绘制左侧文本
     *
     * @param canvas Canvas
     */
    private void drawLeftText(Canvas canvas) {
        for (int i = 0; i < leftValues.length; i++) {
            canvas.drawText(leftValues[i], MARGINLEFT + 10, MARGINTOP + verticalspace * i - 5, getTextPaint());
        }
    }

    /**
     * 左侧文本的Paint
     *
     * @return Paint
     */
    private Paint getTextPaint() {
        Paint paint = getDefaultPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(TEXTCOLOR));
        paint.setTextSize(leftTextSize);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextAlign(Paint.Align.LEFT);
        return paint;
    }

    /**
     * 绘制边框
     *
     * @param canvas Canvas
     */
    private void drawBorders(Canvas canvas) {
        Paint defaultPaint = getDefaultPaint();
        //绘制最上边横线
        canvas.drawLine(MARGINLEFT, MARGINTOP, mWidth + MARGINLEFT, MARGINTOP, defaultPaint);
        //右边竖线
        canvas.drawLine(mWidth + MARGINLEFT, MARGINTOP, mWidth + MARGINLEFT, mHeight + MARGINTOP, defaultPaint);
        //左边竖线
        canvas.drawLine(MARGINLEFT, MARGINTOP, MARGINLEFT, mHeight + MARGINTOP, defaultPaint);
        //底部竖线
        canvas.drawLine(MARGINLEFT, mHeight + MARGINTOP, mWidth + MARGINLEFT, mHeight + MARGINTOP, defaultPaint);
        defaultPaint.reset();
        //绘制中间的横向的虚线
        verticalspace = mHeight / 6;//绘制5条横线
        Log.i(TAG, "drawHorizontalDashLine: verticalspace = " + verticalspace);
        for (int i = 1; i < 6; i++) {
            int beginAndEndY = MARGINTOP + verticalspace * i;
            canvas.drawLine(MARGINLEFT, beginAndEndY, mWidth + MARGINLEFT, beginAndEndY, getDashLinePaint());
        }
    }

    /**
     * 默认画笔
     *
     * @return Paint
     */
    private Paint getDefaultPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//
        paint.setColor(Color.parseColor(BORDERLINECOLOR));
        paint.setStyle(Paint.Style.FILL);//填充样式
        paint.setStrokeWidth(stockWidth);
        return paint;
    }

    /**
     * 虚线画笔
     *
     * @return Paint
     */
    private Paint getDashLinePaint() {
        Paint paint = getDefaultPaint();
        paint.setColor(Color.parseColor(DASHLINECOLOR));
        DashPathEffect effect = new DashPathEffect(new float[]{8, 8}, 1);//虚线
        paint.setPathEffect(effect);
        return paint;
    }

    public FloatView(Context context) {
        super(context);
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
