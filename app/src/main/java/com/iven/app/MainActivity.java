package com.iven.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.iven.app.activity.DialogUtilActivity;
import com.iven.app.activity.ListViewSlideActivity;
import com.iven.app.activity.NoticeViewActivity;
import com.iven.app.activity.PullToRefreshActivity;
import com.iven.app.activity.UpdateVersionActivity;
import com.iven.app.activity.VDHActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zpy_MainActivity";
    private ImageView iv_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_circle = (ImageView) findViewById(R.id.iv_circle);
        iv_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }

    public void btnClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_jingdong:
                intent.setClass(this, NoticeViewActivity.class);
                break;
            case R.id.btn_version:
                intent.setClass(this, UpdateVersionActivity.class);
                break;
            case R.id.btn_vdh:
                intent.setClass(this, VDHActivity.class);
                break;
            case R.id.btn_pulltoresresh:
                intent.setClass(this, PullToRefreshActivity.class);
                break;
            case R.id.btn_lv_slide:
                intent.setClass(this, ListViewSlideActivity.class);
                break;
            case R.id.btn_dialog:
                intent.setClass(this, DialogUtilActivity.class);
                break;
        }
        startActivity(intent);
    }

    /**
     * 开始旋转动画
     */
    private void startAnim() {
        RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(400);
        animation.setRepeatCount(4);
        animation.setInterpolator(new LinearInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.e(TAG, "onAnimationStart: 42" + "行 = ");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.e(TAG, "onAnimationEnd: 48" + "行 = -----------");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.e(TAG, "onAnimationRepeat: 59" + "行 = ");
            }
        });
        iv_circle.startAnimation(animation);
    }

}
