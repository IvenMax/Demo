package com.hbyc.wxn.commontools;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public class T {


    private static String oldMsg;
    protected static Toast toast   = null;
    private static long oneTime=0;
    private static long twoTime=0;

    public static void showShort(Context context, String s){
        if(toast==null){
            toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            oneTime=System.currentTimeMillis();
        }else{
            twoTime=System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime-oneTime>Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime=twoTime;
    }


    public static void showShort(Context context, int resId){
        showShort(context, context.getString(resId));
    }


    public static void showLong(Context context, String s){
        if(toast==null){
            toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            oneTime=System.currentTimeMillis();
        }else{
            twoTime=System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if(twoTime-oneTime>Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime=twoTime;
    }















//    private static Toast toast;
//
//    private T() {
//        /* cannot be instantiated */
//        throw new UnsupportedOperationException("cannot be instantiated");
//    }
//
//    public static boolean isShow = true;
//
//    /**
//     * 短时间显示Toast
//     *
//     * @param context
//     * @param message
//     */
//    public static void showShort(Context context, CharSequence message) {
//        if (isShow) {
//
//            if (toast == null) {
//                toast = new Toast(context);
//            }
//
//            toast.setDuration(Toast.LENGTH_SHORT);
//            toast.setText(message);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        }
//    }
//
//
//    /**
//     * 长时间显示Toast
//     *
//     * @param context
//     * @param message
//     */
//    public static void showLong(Context context, CharSequence message) {
//        if (isShow) {
//            if (toast == null) {
//                toast = new Toast(context);
//            }
//            toast.setDuration(Toast.LENGTH_LONG);
//            toast.setText(message);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//        }
//    }
//
//
//    /**
//     * 自定义显示Toast时间
//     *
//     * @param context
//     * @param message
//     * @param duration
//     */
//    public static void show(Context context, CharSequence message, int duration) {
//        if (isShow)
//            Toast.makeText(context, message, duration).show();
//    }
//
//    /**
//     * 自定义显示Toast时间
//     *
//     * @param context
//     * @param message
//     * @param duration
//     */
//    public static void show(Context context, int message, int duration) {
//        if (isShow)
//            Toast.makeText(context, message, duration).show();
//    }

}