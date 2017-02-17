package com.iven.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.iven.app.activity.BankSearchActivity;
import com.iven.app.activity.ChartViewActivity;
import com.iven.app.activity.DialogUtilActivity;
import com.iven.app.activity.FragmentHomeActivity;
import com.iven.app.activity.GalleryViewPagerActivity;
import com.iven.app.activity.ListViewSlideActivity;
import com.iven.app.activity.NoticeViewActivity;
import com.iven.app.activity.PullToRefreshActivity;
import com.iven.app.activity.TestActivity;
import com.iven.app.activity.UpdateVersionActivity;
import com.iven.app.activity.VDHActivity;
import com.iven.app.activity.WebActivity;
import com.iven.app.activity.listview.ListViewMultiplActivity;
import com.iven.app.activity.listview.ListViewSingleActivity;
import com.iven.app.activity.material.MaterialDesignActivity;
import com.iven.app.activity.recyclerview.RecyclerviewActivity;
import com.iven.app.activity.third.RealmActivity;
import com.iven.app.receiver.PowerConnectionReceiver;
import com.iven.app.utils.Common;

/**
 * Android Studio使用技巧---http://www.jianshu.com/p/8accfeefc182
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "zpy_MainActivity";
    private ImageView iv_circle;
    private PowerConnectionReceiver mPowerConnectionReceiver;
    private boolean isCharging;//是否正在充电
    private boolean usbCharge;//是否是USB形式充电
    private boolean acCharge;//是否是直流电形式充电


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aboutBattery();
        iv_circle = (ImageView) findViewById(R.id.iv_circle);
        iv_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }

    /**
     * 电池相关
     */
    private void aboutBattery() {
        //检测是否在充电 以及什么形式的充电
        batteryInfo();
        registerMyReceiver();
    }

    private void registerMyReceiver() {
        //检测是否充电
        mPowerConnectionReceiver = new PowerConnectionReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Common.ACTION_POWER_CHANGE);
        registerReceiver(mPowerConnectionReceiver, intentFilter);
        Intent intent = new Intent(Common.ACTION_POWER_CHANGE);
        intent.putExtra("isCharging",isCharging);
        intent.putExtra("usbCharge",usbCharge);
        intent.putExtra("acCharge",acCharge);
        sendBroadcast(intent);

    }

    /**
     * 检测当前是否在充电  以及 是以什么形式充电(USB or AC)
     */
    private void batteryInfo() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, intentFilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
        Log.i(TAG, "batteryChargeing: isCharging = " + isCharging);
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        Log.i(TAG, "batteryChargeing: usbCharge = " + usbCharge + "       acCharge = " + acCharge);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        Log.i(TAG, "batteryInfo: 当前电量 = "+level);

    }

    public void btnClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_jingdong://仿京东广告实现
                intent.setClass(this, NoticeViewActivity.class);
                break;
            case R.id.btn_version://蒙板显示
                intent.setClass(this, UpdateVersionActivity.class);
                break;
            case R.id.btn_vdh://nothing
                intent.setClass(this, VDHActivity.class);
                break;
            case R.id.btn_pulltoresresh:
                intent.setClass(this, PullToRefreshActivity.class);
                break;
            case R.id.btn_lv_slide:
                intent.setClass(this, ListViewSlideActivity.class);
                break;
            case R.id.btn_dialog://Dialog封装
                intent.setClass(this, DialogUtilActivity.class);
                break;
            case R.id.btn_realm_date://realm数据库初探
                intent.setClass(this, RealmActivity.class);
                break;
            case R.id.btn_single_listview://ListView属性实现单选模式
                intent.setClass(this, ListViewSingleActivity.class);
                break;
            case R.id.btn_multipl_listview://ListView属性实现多选模式
                intent.setClass(this, ListViewMultiplActivity.class);
                break;
            case R.id.btn_fragment://碎片回顾
                intent.setClass(this, FragmentHomeActivity.class);
                break;
            case R.id.btn_culmn://柱状图演示
                intent.setClass(this, ChartViewActivity.class);
                break;
            case R.id.btn_test://Test
                intent.setClass(this, TestActivity.class);
                break;
            case R.id.btn_recyclerview://recyclerview
                intent.setClass(this, RecyclerviewActivity.class);
                break;
            case R.id.btn_webview://webview
                intent.setClass(this, WebActivity.class);
                break;
            case R.id.btn_material_design://Material Design
                intent.setClass(this, MaterialDesignActivity.class);
                break;
            case R.id.btn_gallery_viewpager://画廊效果
                intent.setClass(this, GalleryViewPagerActivity.class);
                break;
            case R.id.btn_bank_search://通过银行卡号来获取对应的银行以及lOGO(只是借助支付宝的接口)
                intent.setClass(this, BankSearchActivity.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPowerConnectionReceiver){
            unregisterReceiver(mPowerConnectionReceiver);
        }
    }
}
