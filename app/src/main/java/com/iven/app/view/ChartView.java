package com.iven.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

import com.iven.app.bean.ColumnBean;
import com.iven.app.tools.T;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.String.format;

/**
 * @author Iven
 * @date 2017/1/23 9:20
 * @Description 柱形图-用Path实现
 */

public class ChartView extends View {
    private static final String TAG = "zpy_ChartView";
    private ArrayList<ColumnBean> list = new ArrayList<>();//数据集合
    private Context mContext;
    //边界线的颜色(灰色)
    private static final String LINECOLOR = "#666666";
    //虚线颜色
    private static final String DASHLINECOLOR = "#999999";
    //文本颜色
    private static final String TEXTCOLOR = "#66666666";
    //负数颜色
    private static final String GREENCOLOR = "#048e63";
    //正数颜色
    private static final String REDCOLOR = "#f80d0d";
    //默认边距
    private final int MARGINLEFT = 20;
    private final int MARGINRIGHT = 20;
    private final int MARGINTOP = 40;
    private final int MARGINBOTTOM = 20;
    //柱状图之间的间距
    private final int HORIZONTALSPACE = 20;
    //竖轴之间的间距
    private int verticalSpace = 20;
    //View的宽高
    private int hight, width;
    //每个柱状图的宽度
    private float columnWidth = 15;
    private double averageValue;
    //默认字体大小
    private int textSize = 14;//px
    //默认画笔宽度
    private int stockWidth = 3;
    //是否有数据
    private boolean hasData = false;
    //是否是长按模式
    private boolean isLongPress = false;
    //系统默认的最小滑动距离(低于这个值，不算是滑动)
    private int defaultDistance = 0;


    public ChartView(Context context) {
        super(context);
        init(context);
    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w - MARGINLEFT - MARGINRIGHT;
        hight = h - MARGINTOP - MARGINBOTTOM;
        Log.i(TAG, "onSizeChanged: width = " + width + "              hight = " + hight);
        //计算出来每个柱状图的宽度=宽度-2x左右边线的宽度-所有的柱间距     最后除以31个条
        columnWidth = (width - stockWidth * 2 - HORIZONTALSPACE * 30) / 31.0f;
        Log.i(TAG, "onSizeChanged: columnWidth = " + columnWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorders(canvas);
        if (hasData) {
            drawLeftText(canvas);
            drawColumn(canvas);
        }
        //        drawLeftText(canvas);
    }

    /**
     * 绘制柱状图
     *
     * @param canvas canvas
     */
    private void drawColumn(Canvas canvas) {
        if (null == list || list.size() == 0) {
            T.showShort(mContext, "没有数据");
            return;
        }
        Paint paint = getDefaultPaint();
        paint.setStyle(Paint.Style.FILL);//填充
        int center = verticalSpace * 3 + MARGINTOP;//中轴线
        ColumnBean columnBean = list.get(0);


    }

    /**
     * 画左边的文本
     *
     * @param canvas
     */
    private void drawLeftText(Canvas canvas) {
        Paint textPaint = getTextPaint(TEXTCOLOR, 30);
        int maxValueIndex = getMaxValueIndex();
        if (maxValueIndex == -1) {
            return;
        }
        double[] doubles = calculateAverage(list.get(maxValueIndex).getValue());
        if (doubles == null || doubles.length == 0) {
            return;
        }
        String[] leftStrings = new String[doubles.length];
        for (int i = 0; i < doubles.length; i++) {
            double aDouble = doubles[i];
            if (aDouble > 0) {
                leftStrings[i] = String.valueOf("+" + formatValue(aDouble));
            } else if (aDouble == 0) {
                leftStrings[i] = String.valueOf(format("%s", "0.00"));
            } else {
                leftStrings[i] = String.valueOf(formatValue(aDouble));
            }
        }
        for (int i = 0; i < leftStrings.length; i++) {
            float v = textPaint.measureText(leftStrings[i]);
            Paint.FontMetrics metrics = new Paint.FontMetrics();
            Log.e(TAG, "drawLeftText: 142" + "行 = " + v);
            canvas.drawText(leftStrings[i], MARGINLEFT + 10, MARGINTOP - 10 + verticalSpace * i, textPaint);
        }
        textPaint.reset();

    }

    private double[] calculateAverage(double maxValue) {

        if (maxValue == 0) {
            T.showShort(mContext, "最大值不能为0");
            return null;
        }
        averageValue = maxValue / 3;
        double[] text = new double[7];//左边就显示7个数字
        int temp = 3;
        if (maxValue > 0) {
            for (int i = 0; i < 7; i++) {
                text[i] = formatValue(averageValue * (temp--));
                if (temp + 1 == 0) {
                    text[i] = 0.00f;
                }
            }
        } else {
            for (int i = 0; i < 6; i++) {
                text[i] = formatValue(averageValue * (temp--));
                if (temp + 1 == 0) {
                    text[i] = 0.00f;
                }
            }
        }
        return text;
    }

    /**
     * 画最外边的框框
     *
     * @param canvas Canvas
     */
    private void drawBorders(Canvas canvas) {
        Paint defaultPaint = getDefaultPaint();
        Path path = new Path();
        path.moveTo(MARGINLEFT, MARGINTOP);//移动到起点
        //上边框线
        path.lineTo(width + MARGINLEFT, MARGINTOP);//终点坐标:(view的宽度+左边距,上边距)
        //右边框线
        path.lineTo(width + MARGINLEFT, hight + MARGINTOP);//
        //底边框线
        path.lineTo(MARGINLEFT, hight + MARGINTOP);
        path.close();//闭合
        canvas.drawPath(path, defaultPaint);
        path.reset();//重置
        verticalSpace = hight / 6;//等分
        Paint dashLinePaint = getDashLinePaint();
        for (int i = 1; i < 6; i++) {
            int startAndEndY = MARGINTOP + verticalSpace * i;
            canvas.drawLine(MARGINLEFT, startAndEndY, width + MARGINLEFT, startAndEndY, dashLinePaint);
        }
        defaultPaint.reset();
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        defaultDistance = ViewConfiguration.get(context).getScaledEdgeSlop();
        Log.e(TAG, "init: 220" + "行 = " + defaultDistance);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);//关闭硬件加速(三种方法，详见百度)
    }

    /**
     * 默认画笔
     *
     * @return Paint
     */
    private Paint getDefaultPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);//
        paint.setColor(Color.parseColor(LINECOLOR));
        paint.setStyle(Paint.Style.STROKE);//填充样式
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

    /**
     * 左侧文本的Paint
     *
     * @param textColor 文本颜色
     * @param textSize  文本大小
     * @return Paint
     */
    private Paint getTextPaint(String textColor, int textSize) {
        Paint paint = getDefaultPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor(textColor));
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextAlign(Paint.Align.LEFT);
        return paint;
    }

    /**
     * 获取最大值的索引
     *
     * @return
     */
    private int getMaxValueIndex() {
        if (null == list || list.size() == 0) {
            T.showShort(mContext, "没有数据");
            return -1;
        }
        int size = list.size();
        double abs = Math.abs(list.get(0).getValue());
        int index = 0;
        for (int i = 0; i < size; i++) {
            double temp = Math.abs(list.get(i).getValue());
            if (temp > abs) {
                abs = temp;
                index = i;
            }
        }
        return index;
    }

    /**
     * 保留两位小数
     *
     * @param value
     * @return
     */
    private double formatValue(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return Double.valueOf(decimalFormat.format(value));
    }

    /**---------------------------------对外方法-------------------------**/
    /**
     * 设置数据
     *
     * @param list List
     */
    public void setData(ArrayList<ColumnBean> list) {
        this.list = list;
        hasData = true;
        postInvalidate();
    }
}
