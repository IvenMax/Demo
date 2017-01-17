package com.iven.tools.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * @auth Iven
 * 2016/11/23 22:31
 * @desc
 */

public class MyAppUtils {
    /**
     * 获得APP的包名
     *
     * @param context 上下文
     * @return packageName
     */
    public static String getAppPackageName(Context context) {
        return context.getApplicationInfo().packageName;
    }

    /**
     * versionCode
     * versionName
     * 获得APP版本号
     * 获得APP名称
     */
    /**
     * 获得APP 的 versionCode
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getAppPackageName(context), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获得APP 的 versionName
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getAppPackageName(context), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    /**
     * 将版本Name 的 "2.1.2"形式转换成 "212"形式
     *
     * @param context
     * @return
     */
    public static String getAppVersionName2String(Context context) {
        return getAppPackageName(context).replace(".", "").trim();
    }

    /**
     * 隐藏软键盘
     *
     * @param activity activity
     */
    public static void hiddenSoftInput(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

//    public static boolean isInstallApp(Context context,String packageName){
//        !S.isEmpty(packageName) &&
//    }
}
