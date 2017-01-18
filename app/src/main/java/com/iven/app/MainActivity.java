package com.iven.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.iven.app.activity.DialogUtilActivity;
import com.iven.app.activity.ListViewSlideActivity;
import com.iven.app.activity.NoticeViewActivity;
import com.iven.app.activity.PullToRefreshActivity;
import com.iven.app.activity.RealmActivity;
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
            case R.id.btn_realm_date:
                intent.setClass(this, RealmActivity.class);
                break;
        }
        startActivity(intent);
    }

    /**
     * 开始旋转动画
     */
    private void startAnim() {
        RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(2);
        animation.setInterpolator(new LinearInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                backgroundAlpha(0.6f);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                backgroundAlpha(1.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        iv_circle.startAnimation(animation);

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

}
