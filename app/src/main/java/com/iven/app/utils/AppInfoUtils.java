package com.iven.app.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * @auth Iven
 * 2017/1/15 10:37
 * @desc APP 信息相关的工具类
 */

public class AppInfoUtils {
    /**
     * 获得versionName
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * versionName
     *
     * @param context context
     * @param change  是否是将"1.1.0"格式变成"110"格式
     * @return
     */
    public static String getVersion(Context context, boolean change) {
        String string = "";
        if (change) {//没有特殊格式
            string = getVersion(context).replace(".", "");
        } else {
            string = getVersion(context);
        }
        return string;
    }

    /**
     * 判断APP是前台 or 后台运行
     *
     * @param context Context
     * @return true 后台运行
     */
    public static boolean isAPPBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
        if (!runningTasks.isEmpty()) {
            ComponentName topActivity = runningTasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
