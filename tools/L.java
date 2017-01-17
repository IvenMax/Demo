package com.hbyc.wxn.commontools;

import android.util.Log;

import java.util.Map;

/**
 * 控制台打印工具
 * @author 新年
 * 
 */
public class L {

	// debug模式下才开启控制台信息打印
	private static final boolean DEBUG = true;

	public static void i(Object obj, String msg) {
		if (DEBUG && obj != null && !S.isEmpty(msg)) {
			if(obj instanceof String){
				Log.i(obj.toString(), msg);
			}else{
				Log.i(obj.getClass().getSimpleName(), msg);
			}
		}
	}
	public static void v(Object obj, String msg) {
		if (DEBUG && obj != null && !S.isEmpty(msg)) {
			if(obj instanceof String){
				Log.v(obj.toString(), msg);
			}else{
				Log.v(obj.getClass().getSimpleName(), msg);
			}
		}
	}

	public static void e(Object obj, String msg) {
		if (DEBUG && obj != null && !S.isEmpty(msg)) {
			if(obj instanceof String){
				Log.e(obj.toString(), msg);
			}else{
				Log.e(obj.getClass().getSimpleName(), msg);
			}
		}
	}

	public static void d(Object obj, String msg) {
		if (DEBUG && obj != null && !S.isEmpty(msg)) {
			if(obj instanceof String){
				Log.d(obj.toString(), msg);
			}else{
				Log.d(obj.getClass().getSimpleName(), msg);
			}
		}
	}

	public static void w(Object obj, String msg) {
		if (DEBUG && obj != null && !S.isEmpty(msg)) {
			if(obj instanceof String){
				Log.w(obj.toString(), msg);
			}else{
				Log.w(obj.getClass().getSimpleName(), msg);
			}
		}
	}
	
	
	public static void showMap(Object obj,  Map map){
		if (DEBUG && obj != null && map != null && map.size() > 0 ) {
			
			StringBuilder sb = new StringBuilder("请求参数:---------> ");
			for(Object key : map.keySet()){
				if(key != null && map.get(key) != null){
					sb.append(key.toString() + "=" + map.get(key).toString() + ",");
				}
			}
			String result = sb.substring(0, sb.length() - 1) + "<----------------";

			if(obj instanceof String){
				Log.i(obj.toString(), result);
			}else{
				Log.i(obj.getClass().getSimpleName(), result);
			}
		}
	}
}
