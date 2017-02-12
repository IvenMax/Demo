package com.iven.app.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.TextView;

import com.iven.app.MainActivity;
import com.iven.app.R;
import com.iven.app.utils.AppInfoUtils;

import static android.R.attr.endX;
import static android.R.attr.endY;
import static android.R.attr.startX;
import static android.R.attr.startY;

/**
 * @author Iven
 * @date 2017/2/12 9:46
 */

public class WindowService extends Service {
    private static final String TAG = "zpy_WindowService";
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private View mWindowView;
    private TextView mTextView;
    private int defaultDistance;//系统默认滑动距离
    private int lastX, lastY;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        init();
        setClick();
    }


    private void init() {
        defaultDistance = ViewConfiguration.get(getApplication()).getScaledEdgeSlop();
        Log.i(TAG, "init: defaultDistance = " + defaultDistance);
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;//表示在所有应用程序之上，但在状态栏之下
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mLayoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        /**--初始化View--**/
        mWindowView = LayoutInflater.from(getApplication()).inflate(R.layout.layout_window, null);
        mTextView = (TextView) mWindowView.findViewById(R.id.tv_window);
        /**将View添加到Window中**/
        mWindowManager.addView(mWindowView, mLayoutParams);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mWindowView) {
            Log.i(TAG, "removeView: ");
            mWindowManager.removeView(mWindowView);
        }
        Log.i(TAG, "onDestroy: ");
    }

    /**
     * 处理点击事件
     */
    private void setClick() {
        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                Log.e(TAG, "onTouch: 100" + "行 =   x = " + x + "         y = " + y);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:

                        if (shouldIntercept()) {
                            mWindowManager.updateViewLayout(mWindowView, mLayoutParams);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (shouldIntercept()) {
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: 121" + "行 = " + AppInfoUtils.isAPPBackground(WindowService.this));
                if (AppInfoUtils.isAPPBackground(WindowService.this)) {
                    Intent intent = new Intent(WindowService.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

    }

    private boolean shouldIntercept() {
        if (defaultDistance < Math.abs((startX - endX)) || defaultDistance < Math.abs(startY - endY)) {
            return true;
        }
        return false;
    }
}
