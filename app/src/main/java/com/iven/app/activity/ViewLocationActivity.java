package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.iven.app.R;

public class ViewLocationActivity extends AppCompatActivity {

    private Button mButton;
    private static final String TAG = "zpy_ViewLocationActivity";
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);
        init();
    }

    private void init() {
        mButton = (Button) findViewById(R.id.btn_info);
        mButton.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private int mHeight;
            private int mWidth;

            @Override
            public void onGlobalLayout() {
                mButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mLeft = mButton.getLeft();
                mRight = mButton.getRight();
                mTop = mButton.getTop();
                mBottom = mButton.getBottom();
                Log.e(TAG, "init: 31" + "行 =   mLeft=" + mLeft + "   mRight = " + mRight + "   mTop = " + mTop + "   mBottom = " + mBottom);
                mWidth = mButton.getWidth();
                mHeight = mButton.getHeight();
                Log.e(TAG, "onGlobalLayout: 43" + "行 =   mWidth = " +mWidth+"   mHeight = "+mHeight);
            }
        });
    }
}
