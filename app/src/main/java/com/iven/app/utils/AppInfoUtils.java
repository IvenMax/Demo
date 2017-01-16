package com.iven.app.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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
}
