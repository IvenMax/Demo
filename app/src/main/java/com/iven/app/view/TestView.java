package com.iven.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Iven
 * @date 2017/2/3 9:30
 * @Description
 */

public class TestView extends View {
    private static final String TAG = "zpy_TestView";
    private Paint mPaint;
    private String startColor = "#ff0000";
    private String endColor = "#0000ff";
    private LinearGradient mLinearGradient;

    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        mLinearGradient = new LinearGradient(100, 150, 400, 400, Color.parseColor(startColor), Color.parseColor(endColor), Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(100, 100, 500, 500, mPaint);
    }
}
