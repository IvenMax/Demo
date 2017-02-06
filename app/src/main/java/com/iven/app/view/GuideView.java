package com.iven.app.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.iven.app.R;

import java.util.List;


/**
 * @author Iven
 * @date 2017/1/17 16:53
 * @Description 新手指引(蒙板)
 */
public class GuideView extends RelativeLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    private Context mContent;
    private List<View> mViews;
    private boolean first = true;
    /**
     * GuideView 偏移量
     */
    private int offsetX, offsetY;
    /**
     * 目标控件，与外围的高亮形状之间的距离间隙
     */
    private int horizontalGap = 20;
    private int verticalGap = 20;
    /**
     * targetView 的外切圆半径
     */
    private int radius = 20;//默认是20px的弧度角
    /**
     * 需要显示提示信息的View
     */
    private View targetView;
    /**
     * 自定义View
     */
    private View customGuideView;
    /**
     * 透明圆形画笔
     */
    private Paint mCirclePaint;
    /**
     * 背景色画笔
     */
    private Paint mBackgroundPaint;
    /**
     * targetView是否已测量
     */
    private boolean isMeasured;
    /**
     * targetView圆心
     * targetView的中心点的坐标值
     */
    private int[] targetViewCenter;
    /**
     * targertView的宽高
     */
    private int targetViewWidth, targetViewHeight;
    /**
     * 绘图层叠模式
     */
    private PorterDuffXfermode porterDuffXfermode;
    /**
     * 绘制前景bitmap
     */
    private Bitmap bitmap;
    /**
     * 背景色和透明度，格式 #aarrggbb
     */
    private int backgroundColor;
    /**
     * Canvas,绘制bitmap
     */
    private Canvas tempCanvas;
    /**
     * 相对于targetView的位置.在target的那个方向
     */
    private Direction direction;

    /**
     * 形状
     */
    private MyShape myShape;
    /**
     * targetView左上角坐标
     */
    private int[] location;

    private boolean onClickExit;
    private OnClickCallback onclickListener;
    private OnShowCallback onshowListener;//显示回调

    /**
     * 复位
     */
    public void restoreState() {
        offsetX = offsetY = 0;
        horizontalGap = verticalGap = 0;
        radius = 0;
        mCirclePaint = null;
        mBackgroundPaint = null;
        isMeasured = false;
        targetViewCenter = null;
        porterDuffXfermode = null;
        bitmap = null;
        needDraw = true;
        tempCanvas = null;
    }

    public int[] getLocation() {
        return location;
    }

    public void setLocation(int[] location) {
        this.location = location;
    }

    public GuideView(Context context) {
        super(context);
        this.mContent = context;
        init();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public void setHorizontalGap(int horizontalGap) {
        this.horizontalGap = horizontalGap;
    }

    public void setVerticalGap(int verticalGap) {
        this.verticalGap = verticalGap;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setShape(MyShape shape) {
        this.myShape = shape;
    }

    public void setCustomGuideView(View customGuideView) {
        this.customGuideView = customGuideView;
        if (!first) {
            restoreState();
        }
    }

    public void setBgColor(int background_color) {
        this.backgroundColor = background_color;
    }

    public View getTargetView() {
        return targetView;
    }

    public void setTargetView(View targetView) {
        this.targetView = targetView;
        //        restoreState();
        if (!first) {
            //            guideViewLayout.removeAllViews();
        }
    }

    /**
     * 初始化相关
     */
    private void init() {
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);// 或者CLEAR
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setXfermode(porterDuffXfermode);
    }


    public int[] getTargetViewCenter() {
        return targetViewCenter;
    }

    public void setTargetViewCenter(int[] targetViewCenter) {
        this.targetViewCenter = targetViewCenter;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void hide() {
        if (customGuideView != null) {
            targetView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            this.removeAllViews();
            ((FrameLayout) ((Activity) mContent).getWindow().getDecorView()).removeView(this);
            restoreState();
        }
    }

    public void show() {
        if (targetView != null) {
            targetView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }

        this.setBackgroundResource(R.color.transparent);
        if (null != onshowListener) {
            onshowListener.onShowGuideView();
        }

        ((FrameLayout) ((Activity) mContent).getWindow().getDecorView()).addView(this);
        first = false;
    }

    /**
     *
     */
    private void createGuideView() {
        customGuideView.measure(0, 0);
        int customGuideViewHeight = customGuideView.getMeasuredHeight();
        int customGuideViewWidth = customGuideView.getMeasuredWidth();
        // Tips布局参数,要显示的图片or文字的布局参数
        LayoutParams guideViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //默认的位置参数设置
        guideViewParams.setMargins(0, targetViewCenter[1] + radius + 10, 0, 0);

        if (customGuideView != null) {
            if (direction != null) {
                int width = this.getWidth();
                int height = this.getHeight();

                int left = targetViewCenter[0] - radius;
                int right = targetViewCenter[0] + radius;
                //int top = targetViewCenter[1] - radius;
                int top = (int) (targetViewCenter[1] + targetViewHeight / 2 + verticalGap);
                int bottom = (int) (targetViewCenter[1] + targetViewHeight / 2 + verticalGap);
                //int bottom = targetViewCenter[1] + radius;
                // TODO: 2016/12/26 暂定只用top和bottom
                switch (direction) {
                    //底部图片
                    case BOTTOM:
                        // this.setGravity(Gravity.CENTER_HORIZONTAL);
                        //关键是设置top
                        guideViewParams.addRule(RelativeLayout.BELOW, targetView.getId());
                        guideViewParams.leftMargin = targetViewCenter[0] - customGuideViewWidth / 2;
                        guideViewParams.topMargin = bottom + offsetY;
                        guideViewParams.rightMargin = -offsetX;
                        guideViewParams.bottomMargin = -bottom - offsetX;
                        //                        guideViewParams.setMargins(targetViewCenter[0] - targetViewWidth, bottom + offsetY, -offsetX, -bottom - offsetY);
                        break;
                    //顶部图片
                    case TOP:
                        //this.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                        guideViewParams.addRule(RelativeLayout.ABOVE, targetView.getId());
                        guideViewParams.leftMargin = targetViewCenter[0] - customGuideViewWidth / 2;
                        guideViewParams.topMargin = targetViewCenter[1] - targetViewHeight / 2 - verticalGap - customGuideViewHeight;
                        guideViewParams.rightMargin = -offsetX;
                        guideViewParams.bottomMargin = verticalGap;
                        break;
                    /*case LEFT:
                        this.setGravity(Gravity.RIGHT);
                        guideViewParams.setMargins(offsetX - width + left, top + offsetY, width - left - offsetX, -top - offsetY);
                        break;

                    case RIGHT:
                        guideViewParams.setMargins(right + offsetX, top + offsetY, -right - offsetX, -top - offsetY);
                        break;
                    case LEFT_TOP:
                        this.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
                        guideViewParams.setMargins(offsetX - width + left, offsetY - height + top, width - left - offsetX, height - top - offsetY);
                        break;
                    case LEFT_BOTTOM:
                        this.setGravity(Gravity.RIGHT);
                        guideViewParams.setMargins(offsetX - width + left, bottom + offsetY, width - left - offsetX, -bottom - offsetY);
                        break;
                    case RIGHT_TOP:
                        this.setGravity(Gravity.BOTTOM);
                        guideViewParams.setMargins(right + offsetX, offsetY - height + top, -right - offsetX, height - top - offsetY);
                        break;
                    case RIGHT_BOTTOM:
                        guideViewParams.setMargins(right + offsetX, bottom + offsetY, -right - offsetX, -top - offsetY);
                        break;*/
                }
            } else {
                guideViewParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                guideViewParams.setMargins(offsetX, offsetY, -offsetX, -offsetY);
            }
            //将图片add进去
            this.removeAllViews();
            this.addView(customGuideView, guideViewParams);
        }
    }

    /**
     * 获得targetView 的宽高以及位置信息
     * 如果未测量，返回｛-1， -1｝
     */
    private int[] getTargetViewSize() {
        int[] targetViewScale = {-1, -1};
        if (isMeasured) {
            targetViewScale[0] = targetView.getWidth();
            targetViewScale[1] = targetView.getHeight();
        }
        return targetViewScale;
    }

    /**
     * 获得targetView 的半径
     *
     * @return
     */
    private int getTargetViewRadius() {
        if (isMeasured) {
            int[] size = getTargetViewSize();
            int x = size[0];
            int y = size[1];

            return (int) (Math.sqrt(x * x + y * y) / 2);
        }
        return -1;
    }

    boolean needDraw = true;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasured)
            return;

        if (targetView == null)
            return;
        //        createGuideView();
        drawBackground(canvas);
    }

    /**
     *
     */
    private void drawBackground(Canvas canvas) {
        needDraw = false;
        // 先绘制bitmap，再将bitmap绘制到屏幕
        bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        //在tempCanvas上绘制高亮区域
        tempCanvas = new Canvas(bitmap);

        // 背景画笔&设置
        Paint bgPaint = new Paint();
        if (backgroundColor != 0) {
            bgPaint.setColor(backgroundColor);
        } else {
            bgPaint.setColor(getResources().getColor(R.color.color_cc222222));
        }
        // 绘制屏幕背景
        tempCanvas.drawRect(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight(), bgPaint);

        // targetView 的透明圆形画笔
        if (mCirclePaint == null) {
            mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCirclePaint.setXfermode(porterDuffXfermode);
        }

        if (myShape != null) {
            RectF rectF = new RectF();
            switch (myShape) {
                case CIRCULAR://圆形//坐标值、
                    tempCanvas.drawCircle(targetViewCenter[0], targetViewCenter[1], radius, mCirclePaint);//绘制圆形
                    break;
                case ELLIPSE://椭圆
                    //RectF对象
                    rectF.left = targetViewCenter[0] - 150;                              //左边
                    rectF.top = targetViewCenter[1] - 50;                                   //上边
                    rectF.right = targetViewCenter[0] + 150;                             //右边
                    rectF.bottom = targetViewCenter[1] + 50;                                //下边
                    tempCanvas.drawOval(rectF, mCirclePaint);                   //绘制椭圆
                    break;
                // TODO: 2016/12/22 鉴于不会采用椭圆和圆形，暂时不处理他们了
                case RECTANGULAR://圆角矩形
                    /*rectF.left = targetViewCenter[0] - 150;                              //左边
                    rectF.top = targetViewCenter[1] - 50;                                   //上边
                    rectF.right = targetViewCenter[0] + 150;                             //右边
                    rectF.bottom = targetViewCenter[1] + 50;                                //下边*/
                    rectF.left = (float) (targetViewCenter[0] - targetViewWidth / 2 - horizontalGap);
                    rectF.top = (float) (targetViewCenter[1] - targetViewHeight / 2 - verticalGap);
                    rectF.right = (float) (targetViewCenter[0] + targetViewWidth / 2 + horizontalGap);
                    rectF.bottom = (float) (targetViewCenter[1] + targetViewHeight / 2 + verticalGap);
                    tempCanvas.drawRoundRect(rectF, radius, radius, mCirclePaint);                   //绘制圆角矩形
                    break;
            }
        } else {
            //不设置形状的话，默认是画圆形的
            tempCanvas.drawCircle(targetViewCenter[0], targetViewCenter[1], radius, mCirclePaint);//绘制圆形
        }

        // 绘制到屏幕
        canvas.drawBitmap(bitmap, 0, 0, bgPaint);
        bitmap.recycle();
    }

    public void setOnClickExit(boolean onClickExit) {
        this.onClickExit = onClickExit;
    }

    public void setOnclickListener(OnClickCallback onclickListener) {
        this.onclickListener = onclickListener;
    }

    public void setOnshowListener(OnShowCallback onshowListener) {
        this.onshowListener = onshowListener;
    }

    private void setClickInfo() {
        final boolean exit = onClickExit;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickListener != null) {
                    onclickListener.onClickedGuideView();
                }
                if (exit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        hide();
                    }
                }
            }
        });
    }

    /**
     *
     */
    @Override
    public void onGlobalLayout() {
        if (isMeasured)
            return;
        if (targetView.getHeight() > 0 && targetView.getWidth() > 0) {
            isMeasured = true;
        }
        //目标View的宽高赋值
        targetViewWidth = targetView.getWidth();
        targetViewHeight = targetView.getHeight();

        // 获取targetView的中心坐标
        if (targetViewCenter == null) {
            // 获取左上角坐标
            location = new int[2];
            targetView.getLocationInWindow(location);
            targetViewCenter = new int[2];
            // 获取中心坐标
            targetViewCenter[0] = location[0] + targetView.getWidth() / 2;
            targetViewCenter[1] = location[1] + targetView.getHeight() / 2;
        }
        // 获取targetView外切圆半径
        if (radius == 0) {
            //            radius = getTargetViewRadius();
            radius = 15;
        }
        // 添加GuideView、创建
        createGuideView();
    }


    /**
     * 定义目标控件的形状，共3种。圆形，椭圆，带圆角的矩形（可以设置圆角大小），不设置则默认是圆形
     */
    public enum MyShape {
        CIRCULAR, ELLIPSE, RECTANGULAR
    }

    /**
     * GuideView点击Callback
     */
    public interface OnClickCallback {
        void onClickedGuideView();
    }

    /**
     * 显示回调
     */
    public interface OnShowCallback {
        void onShowGuideView();
    }

    public static class Builder {
        static GuideView guiderView;
        static Builder instance = new Builder();
        Context mContext;

        private Builder() {
        }

        public Builder(Context ctx) {
            mContext = ctx;
        }

        public static Builder newInstance(Context ctx) {
            guiderView = new GuideView(ctx);
            return instance;
        }

        public Builder setTargetView(View target) {
            guiderView.setTargetView(target);
            return instance;
        }

        public Builder setBgColor(int color) {
            guiderView.setBgColor(color);
            return instance;
        }

        public Builder setDirction(Direction dir) {
            guiderView.setDirection(dir);
            return instance;
        }

        public Builder setShape(MyShape shape) {
            guiderView.setShape(shape);
            return instance;
        }

        public Builder setOffset(int x, int y) {
            guiderView.setOffsetX(x);
            guiderView.setOffsetY(y);
            return instance;
        }

        public Builder setGap(int horizontalGap, int verticalGap) {
            guiderView.setHorizontalGap(horizontalGap);
            guiderView.setVerticalGap(verticalGap);
            return instance;
        }

        public Builder setRadius(int radius) {
            guiderView.setRadius(radius);
            return instance;
        }

        public Builder setCustomGuideView(View view) {
            guiderView.setCustomGuideView(view);
            return instance;
        }

        public Builder setCenter(int X, int Y) {
            guiderView.setTargetViewCenter(new int[]{X, Y});
            return instance;
        }


        public GuideView build() {
            guiderView.setClickInfo();
            return guiderView;
        }

        public Builder setOnclickExit(boolean onclickExit) {
            guiderView.setOnClickExit(onclickExit);
            return instance;
        }

        public Builder setOnclickListener(final OnClickCallback callback) {
            guiderView.setOnclickListener(callback);
            return instance;
        }

        public Builder setOnShowListener(final OnShowCallback callback) {
            guiderView.setOnshowListener(callback);
            return instance;
        }
    }

    public enum Direction {
        LEFT, TOP, RIGHT, BOTTOM,
        LEFT_TOP, LEFT_BOTTOM,
        RIGHT_TOP, RIGHT_BOTTOM
    }
}
