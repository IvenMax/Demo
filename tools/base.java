package com.zpy.app.base;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hbyc.wxn.commontools.L;
import com.hbyc.wxn.commontools.SPUtils;
import com.jaredrummler.android.processes.AndroidProcesses;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zpy.app.MyApplication;
import com.zpy.app.R;
import com.zpy.app.SystemBarTintManager;
import com.zpy.app.activity.center.GestureByUserActivity;
import com.zpy.app.activity.login.LoginActivity;
import com.zpy.app.bean.User;
import com.zpy.app.utils.ConstantValue;
import com.zpy.app.utils.DialogUtils;
import com.zpy.app.view.PullToRefreshLayout;
import com.zpy.app.utils.ScreenObserver;
import com.zpy.app.utils.UserUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Semaphore;

import cn.jpush.android.api.InstrumentedActivity;


/**
 * 应用程序Activity的基类
 *
 * @author kymjs
 * @version 1.0
 * @created 2013-11-24
 */

public abstract class BaseActivity extends InstrumentedActivity implements OnClickListener {


    private static final int ACTIVITY_RESUME = 0;
    private static final int ACTIVITY_STOP = 1;
    private static final int ACTIVITY_PAUSE = 2;
    private static final int ACTIVITY_DESTROY = 3;

    private static boolean isRegist = false;//是否注册监听home键的广播

    protected View title;
    protected TextView title_title, title_right, title_left, title_virtual, title_left_num, title_right_num;
    protected ImageView title_img;

    public static Context context;
    public int activityState;
    public static String TAG = "";

    // 是否允许全屏
    private boolean mAllowFullScreen = false;
    // 是否显示标题栏
    private boolean mIsShowTitle = true;

    public SharedPreferences mPreferences;

    /**
     * 锁屏监听
     */
    public ScreenObserver mScreenObserver;

    /**
     * 可点击控件的集合
     */
    protected List<Integer> clickableWidgetList = new ArrayList<Integer>();
    public MyApplication ma;
    protected int titleHeight;
    protected int windowHeight;
    public int noTitleHeight;
    protected int windowWidth;

    protected boolean isLoad = false;//是否正在加载

    /**
     * key value 由于消息多个接口需要这两个字段
     */
    protected String[] key_user_id = {"user_id"};


    /**
     * 刷新成员变量
     */
    public PullToRefreshLayout mPullToRefreshView;
    //	public ProgressBar progressBar;
    public ImageView textView;

    /**
     * 状态高度
     */
    public int statusBarHeight = 0;
    //	public ImageView imageView;

    /**
     * 记录在哪个页面退出的  0退出 1正常
     */
//	private String flag = "0";

    /**
     * 布局ID
     *
     * @return
     */
    public abstract int setLayout();

    /**
     * 设置标题显示的内容
     */
    public abstract void setTitle();

    /**
     * 初始化控件
     */
    public abstract void initWidget();

    /**
     * 添加事件
     */
    public abstract void widgetClick(View v);

    public Semaphore mInfoSemaphore;

    /**
     * 添加信号量
     */
    public void addSemaphore() {
        try {
            mInfoSemaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加自定义 layout
     */
    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        Object view = null;
        if (name.equals("FrameLayout")) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals("LinearLayout")) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals("RelativeLayout")) {
            view = new AutoRelativeLayout(context, attrs);
        }

        return (View) (view != null ? view : super.onCreateView(name, context, attrs));
    }


    private long startTime;

    public void setAllowFullScreen(boolean allowFullScreen) {
        if (allowFullScreen) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 沉浸式状态栏
     */
    private SystemBarTintManager tintManager;

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.welcom_backgrond));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    /**
     * Sp中存储手势的代码
     *
     * @return
     */
    public static String getSPGestureFlag() {
        if (TextUtils.isEmpty(UserUtils.getInstance().user.getUserId())) {
            return
                    "start";
        } else {
            return UserUtils.getInstance().user.getUserId() + "_gesture";
        }

    }

    private void setImmersionStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 设置标题是否显示
     */
    public void setShowTitle(boolean isShowTitle) {
        if (isShowTitle) {
            title.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
        }
    }

//	private void initGrade() {
//		boolean isGraded = mPreferences.getBoolean("isGraded", false);
//		if (!isGraded) { // 如果没有评过分
//			initDialog();
//			boolean isHinted = mPreferences.getBoolean("isHinted", false);
//			final long activeTime = mPreferences.getLong("activeTime", 0L);
//			int savedRandom1 = mPreferences.getInt("random1", 0);
//			int savedRandom2 = mPreferences.getInt("random2", 0);
//			if (savedRandom1 == 0 && savedRandom2 == 0) {
//
//				int randomWeek1 = (int) (Math.random() * 7);
//				int randomWeek2 = (int) (Math.random() * 7);
//				mPreferences.edit().putInt("random1", randomWeek1).commit();
//				mPreferences.edit().putInt("random2", randomWeek2).commit();
//			}
//
//			savedRandom1 = mPreferences.getInt("random1", 0);
//			savedRandom2 = mPreferences.getInt("random2", 0);
//
//			int weekTime = mPreferences.getInt("weekTime", 0);
//			if (0 != activeTime) { // 在线时间不为空
//				if (isHinted) { // 已经显示过提示
//					int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
//					int currentWeek = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) - 1;
//					if (0 != weekTime && currentWeek == weekTime) { // 当前周与上次退出时间为同一周
//						if (currentDay == savedRandom1 || currentDay == savedRandom2) { // 本次显示
//							new Handler().postDelayed(new Runnable() {
//
//								@Override
//								public void run() {
//									// TODO Auto-generated method stub
//									if (activeTime > (1000 * 60 * 30)) { // 在线时间大于30分钟
//										mPreferences.edit().putLong("activeTime", 0L).commit();
//										showPop();
//									} else {
//										TimeUtils.startTimeCount4Grade(1000 * 60 * 30 - activeTime, 1000 * 60, handler);
//									}
//								}
//							}, 1000 * 60);
//						}
//					}
//				} else {
//					new Handler().postDelayed(new Runnable() {
//
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							TimeUtils.startTimeCount4Grade(1000 * 60 * 30 - activeTime, 1000 * 60, handler);
//						}
//					}, 1000 * 30);
//					TimeUtils.startTimeCount4Grade(1000 * 30 * 60 - activeTime, 1000 * 60, handler);
//				}
//			} else {
//				if (!isHinted) {
//					TimeUtils.startTimeCount4Grade(1000 * 60 * 30, 1000 * 60, handler);
//				}
//			}
//		}
//	}

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    showPop();
                    break;
                default:
                    break;
            }
        }

    };

    private void showPop() {
        if (null != dialog) {
            dialog.setContext(AppManager.getAppManager().currentActivity());
            mPreferences.edit().putBoolean("isHinted", true).commit();
            dialog.showProcessDialog();

            int savedRandom1 = mPreferences.getInt("random1", 0);
            int savedRandom2 = mPreferences.getInt("random2", 0);
            int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
            if (currentDay == savedRandom1) {

                mPreferences.edit().putInt("random1", 0).commit();
            }
            if (currentDay == savedRandom2) {

                mPreferences.edit().putInt("random2", 0).commit();
            }
        }
    }

    private DialogUtils dialog;

//	private void initDialog() {
//		dialog = new DialogUtils(this, true, new DialogUtils.DialogClickListener() {
//			@Override
//			public void rightEvent() {
//				mPreferences.edit().putBoolean("isHinted", true).commit();
//				dialog.closeProgressDilog();
//			}
//
//			@Override
//			public void leftEvent() {
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("ComeFrom", "Dialog");
//				mPreferences.edit().putBoolean("isGraded", true).commit();
//				Intent intent = new Intent(Intent.ACTION_VIEW);
//				intent.setData(Uri.parse("market://details?id=" + BaseActivity.this.getPackageName()));
//				BaseActivity.this.startActivity(intent);
//			}
//		});
//		dialog.setDialog(null, "这么棒，去给好评！", "评论", "拒绝");
//	}

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        title = findViewById(R.id.title);
        title_title = (TextView) title.findViewById(R.id.title_title);
        title_right = (TextView) title.findViewById(R.id.title_right);
        title_left = (TextView) title.findViewById(R.id.title_left);
        title_img = (ImageView) title.findViewById(R.id.title_img);
        title_virtual = (TextView) title.findViewById(R.id.title_virtual);
        title_left_num = (TextView) findViewById(R.id.title_left_num);
        title_right_num = (TextView) findViewById(R.id.title_right_num);
        // 分辨率
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = getWindowManager();

        windowManager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        windowHeight = metrics.heightPixels;
        windowWidth = metrics.widthPixels;
        LayoutParams params = title.getLayoutParams();

        params.height = (int) (width / 8);
        titleHeight = params.height;
        title.setLayoutParams(params);

        noTitleHeight = windowHeight - titleHeight;

    }

    /**
     * 只让一个发生点击事件的方法
     */
    protected void singleExecute(int id) {
        for (int i = 0; i < clickableWidgetList.size(); i++) {
            if (id != clickableWidgetList.get(i)) {
                this.findViewById(clickableWidgetList.get(i)).setOnClickListener(null);
            }
        }
    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /***************************************************************************
     * 打印Activity生命周期
     ***************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        //	initWindow();

        L.e(TAG, "onCreate.activityName = " + getClass().getSimpleName());

        context = this;
        TAG = "zpy_" + context.getClass().getSimpleName();
        mPreferences = getSharedPreferences("foreground", MODE_PRIVATE);
        startTime = System.currentTimeMillis();
        MyApplication.count++;

//		if (MyApplication.count == 1)
//			initGrade();
        // 竖屏锁定
        if (setDefaultPortrait()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        //获取资源文件ID
        int resId = setLayout();
        View view = View.inflate(this, resId, null);
        setContentView(resId);
//		ViewUtils.wireViews(this, this, view);
        setAllowFullScreen(mAllowFullScreen);
        AppManager.getAppManager().addActivity(this);

        ma = (MyApplication) getApplication();


        initTitle();
        setShowTitle(mIsShowTitle);
        setTitle();
        initWidget();

//		//对所有的左侧返回按钮统一进行处理
//		if (null != this.title_left && this.title_left.getVisibility() == View.VISIBLE && null != this.title_left.getCompoundDrawables() && null != this.title_left.getCompoundDrawables()[0]) {
//			this.title_left.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					finish();
//				}
//			});
//		}

	/*	// 注册广播监听home键
        String simpleName = getClass().getSimpleName();
		if (simpleName.equals("MenuActivity") || simpleName.equals("GuideActivity") && !isRegist) {
			IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
			registerReceiver(mHomeKeyEventReceiver, filter);
			isRegist = true;
		}*/
    }


    /**
     * 让子类复写此方法
     *
     * @return
     */
    protected boolean setDefaultPortrait() {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        L.i(this, "---------onStart ");
    }


    public static boolean lockscreenTime = false;//判断锁屏是否大于5 秒

    @Override
    protected void onResume() {
        super.onResume();

        context = this;
        activityState = ACTIVITY_RESUME;
//        Log.i("zyl", "先走onResume");
        // app 从后台唤醒，进入前台
//        Log.e(TAG, "onResume======  isActivie======== " + isActive + "   className===  " + this.getClass().getSimpleName());


        if (!isActive && !"SplashActivity".equals(this.getClass().getSimpleName())) {
            long stop = mPreferences.getLong("stop", 0L);
            if (stop != 0) {
                if (!AppManager.getAppManager().currentActivity().getClass().getName().equals(GestureByUserActivity.class.getName())
                        ) {
                    isActive = true;
                    long current = System.currentTimeMillis();

                    // 离开60s后回来需要输入手势验证。
                    if (current > stop && current - stop > 1000 * 60) {
                        showGuestureLocker();
                    }
                }
            }

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        activityState = ACTIVITY_STOP;
        // JPushInterface.onPause(this);
//        Log.i(TAG, "onStop: 运行在前台还是后台 ？" + AndroidProcesses.isMyProcessInTheForeground());
        if (!AndroidProcesses.isMyProcessInTheForeground()) {
            // app 进入后台
            mPreferences.edit().putLong("stop", System.currentTimeMillis()).commit();
            // 全局变量isActive = false 记录当前已经进入后台
            isActive = false;
        }

//        Log.e(TAG, "onStop======   isActive====   " + isActive);
    }


    /**
     * 是否跳转到手势密码界面
     */
    public void showGuestureLocker() {
        if (UserUtils.validLogin(this)) {
            String isOff = SPUtils.getString(BaseActivity.this, getSPGestureFlag(), ConstantValue.STATE_OFF);

            if (!AppManager.getAppManager().currentActivity().getClass().equals(GestureByUserActivity.class)
                    && !isOff.equals(ConstantValue.STATE_OFF)
                    && !AppManager.getAppManager().BooleanActivity(GestureByUserActivity.class)) {
                lockscreenTime = false;

                SPUtils.setObject(context, "lastActive", AppManager.getAppManager().currentActivity().getClass());
                Intent intent = new Intent();
                intent.setClass(context, GestureByUserActivity.class);
                context.startActivity(intent);
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSE;

    }


    public long getStartTime() {
        return startTime;
    }


    public static boolean isActive = false;

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
        activityState = ACTIVITY_DESTROY;

        SPUtils.setObject(context, "lastActive", null);
        AppManager.getAppManager().finishActivity(this);

	/*	//取消home键的监听
        if (getClass().getSimpleName().equals("MenuActivity")) {
			unregisterReceiver(mHomeKeyEventReceiver);
		}*/
    }

    /**
     * 弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    public class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
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

    @SuppressWarnings("unchecked")
    public <T> T f(int resId) {
        return (T) findViewById(resId);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        //这里暂时没有用了 zyl  20161-9
        if (cls.getName().equals(LoginActivity.class.getName())) {
            intent.putExtra("isLogin", true);
        }
        intent.setClass(this, cls);
        startActivity(intent);
        // overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        // overridePendingTransition(R.anim.hyperspace_in,
        // R.anim.hyperspace_out);
        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        // 这里有2个动画的配置文件
        // overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        // overridePendingTransition(R.anim.hyperspace_in,
        // R.anim.hyperspace_out);
        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }


    /**
     * 通过Action跳转界面
     **/
    public void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    public void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }

        startActivity(intent);
        // overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        // overridePendingTransition(R.anim.hyperspace_in,
        // R.anim.hyperspace_out);
        // //实现淡入浅出的效果
        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);

        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        // overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        // overridePendingTransition(R.anim.hyperspace_in,
        // R.anim.hyperspace_out);

        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public void startActivityAndFinish(Class<?> clz) {
        Intent intent = new Intent();
        if (clz.getName().equals(LoginActivity.class.getName())) {
            intent.putExtra("isLogin", true);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.setClass(this, clz);
        startActivity(intent);
        this.finish();
        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityAndFinish(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        Log.i("wucanclassName", this.getClass().getSimpleName());
        // 这里有2个动画的配置文件
        // overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        // overridePendingTransition(R.anim.hyperspace_in,
        // R.anim.hyperspace_out);
        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        this.finish();
        //overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 带有右进右出动画的退出
     */
    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
//		 overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }


    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String pkName = getApplicationContext().getPackageName();
        List<RunningAppProcessInfo> apps = am.getRunningAppProcesses();
        if (apps == null) {
            return false;
        }
        for (RunningAppProcessInfo app : apps) {
            if (app.processName.equals(pkName) && app.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接查询push设置的url  如果value的值是 1:true,2:false
     */
    protected String getParams(String[] key, String[] value) {
        JSONObject jsonObject = new JSONObject();
        try {
            for (int i = 0; i < key.length; i++) {
                if ("true".equals(value[i])) {
                    jsonObject.put(key[i], true);
                } else if ("false".equals(value[i])) {
                    jsonObject.put(key[i], false);
                } else {
                    jsonObject.put(key[i], value[i]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "params=" + jsonObject.toString();
    }


    /**
     * siai接口参数上次
     */
    public String SIAI_getParams(String[] key, String[] value) {
        JSONObject jsonObject = new JSONObject();

        JSONObject common_jsonObject = new JSONObject();
        String[] userinfor_key = {"user_id", "regist_id", "vt_user_id", "user_type"};
        User user = UserUtils.getInstance().user;
        String[] userinfor_value = {user.getUserId(), user.getRegistId(), user.getVtUserId(), "3"};
        try {
            for (int i = 0; i < userinfor_key.length; i++) {
                common_jsonObject.put(userinfor_key[i], userinfor_value[i]);
            }
            jsonObject.put("user_info", common_jsonObject);
            for (int i = 0; i < key.length; i++) {
                jsonObject.put(key[i], value[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        com.hbyc.wxn.commontools.L.i("siai_json", "=="+jsonObject.toString() + "==");
        return jsonObject.toString();
    }


    /**
     * siai接口参数上次  itarder 没有交易
     */
    public String SIAI_getParams(String[] key, String[] value,Boolean no_userInfo) {
        JSONObject jsonObject = new JSONObject();
        JSONObject common_jsonObject = new JSONObject();
        String[] userinfor_key = {"user_id", "regist_id", "vt_user_id", "user_type"};
        User user = UserUtils.getInstance().user;
        String[] userinfor_value = {user.getUserId(), user.getRegistId(), user.getVtUserId(), "3"};
        try {
            if(no_userInfo){
                for (int i = 0; i < userinfor_key.length; i++) {
                    common_jsonObject.put(userinfor_key[i], userinfor_value[i]);
                }
                jsonObject.put("user_info", common_jsonObject);
            }
            for (int i = 0; i < key.length; i++) {
                jsonObject.put(key[i], value[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        com.hbyc.wxn.commontools.L.i("siai_json", "----->"+jsonObject.toString() + "<-----");
        return jsonObject.toString();
    }


    /**
     * 添加脚布局留白
     */
    protected void addFooterView(Context context, ListView listView, int heightId) {
        TextView tv_footer = new TextView(context);
        ListView.LayoutParams params = new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, heightId);
        tv_footer.setBackgroundColor(getResources().getColor(R.color.white));
        tv_footer.setLayoutParams(params);
        listView.addFooterView(tv_footer);
        listView.setFooterDividersEnabled(false);
    }
  /*  private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    flag = "1";//添加标记
                    //表示按了home键,程序到了后台
//                    Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)) {
                    //表示长按home键,显示最近使用的程序列表
//                    Toast.makeText(context, "长按显示最近使用", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
*/


    public class MyAsyncTask extends AsyncTask<Integer, Integer, Boolean> {

        private ViewGroup viewGroup;
        private int sum;
        private int temp;
        private PullToRefreshLayout mPullToRefreshView;

        public MyAsyncTask(ViewGroup viewGroup, int sum) {
            this.viewGroup = viewGroup;
            this.sum = sum;
            temp = sum;
        }

        public MyAsyncTask(ViewGroup viewGroup, int sum, PullToRefreshLayout mPullToRefreshView) {
            this.viewGroup = viewGroup;
            this.sum = sum;
            temp = sum;
            this.mPullToRefreshView = mPullToRefreshView;
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                while (sum > 0) {
                    mInfoSemaphore.acquire();
                    sum--;
                }
                return true;
            } catch (InterruptedException e) {
                e.printStackTrace();
                sum = temp;
                while (sum > 0) {
                    mInfoSemaphore.release();
                    sum--;
                }
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);
            if (s) {

                sum = temp;
                while (sum > 0) {
                    mInfoSemaphore.release();
                    sum--;
                }
                if (viewGroup != null && viewGroup.getVisibility() != View.VISIBLE) {
                    viewGroup.setVisibility(View.VISIBLE);
                }

            }
            if (mPullToRefreshView != null) {
                mPullToRefreshView.setRefreshing(false);
            }

        }

    }


    public View createRefreshView() {
        View headerView = mPullToRefreshView.setRefreshView(R.layout.layout_head);
//		progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        textView = (ImageView) headerView.findViewById(R.id.text_view);
        textView.setImageResource(R.mipmap.drawable_text);
//		imageView = (ImageView) headerView.findViewById(R.id.image_view);
//		imageView.setVisibility(View.VISIBLE);
//		imageView.setImageResource(R.mipmap.drawable_arrow);
//		progressBar.setVisibility(View.GONE);
        return headerView;
    }


}
