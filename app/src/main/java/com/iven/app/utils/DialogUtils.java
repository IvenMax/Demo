package com.iven.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iven.app.R;

import static android.R.attr.gravity;

/**
 * @author Iven
 * @date 2017/1/9 13:31
 * @Description 统一的Dialog封装
 */

public class DialogUtils {
    //Dialog
    private Dialog mDialog;
    private Context context;
    private boolean isCancelable;//是否可以取消
    private DialogClickListener eventListener;// 按钮点击回调
    /**
     * 显示文本相关
     */
    private String title = "";// 标题
    private String content = "";// 内容
    private String leftText = "";// 左按钮显示的文字
    private String rightText = "";// 右按钮显示的文字
    private TextView dialog_textView_title, dialog_textView_content, dialog_textView_left, dialog_textView_right, dialog_textView_segment;
    private LinearLayout dialog_layout_button;
    private String hint;
    private String tip;
    // TODO: 2017/1/9
    boolean isBold = false;//字体是否加粗
    //是否显示左侧的图标
    private boolean isShowDrawable;
    private int leftDdrawable = 0;
    private int rightDdrawable = 0;
    private int imgId;//设置图片


    private int leftColor = -1;
    private int rightColor = -1;

    private Handler mHandler;
    private EditText et_alias;


    public DialogUtils(Context context, boolean isCancelable) {
        this.context = context;
        this.isCancelable = isCancelable;
        leftColor = -1;
        rightColor = -1;
    }

    /**
     * @param context       context
     * @param isCancelable  true=可取消
     * @param eventListener 监听
     */
    public DialogUtils(Context context, boolean isCancelable, DialogClickListener eventListener) {
        this.context = context;
        this.isCancelable = isCancelable;
        this.eventListener = eventListener;
    }

    /**
     * @param context        context
     * @param title          标题
     * @param content        描述内容
     * @param leftText       左侧按钮文字
     * @param rightText      右侧按钮文字
     * @param isShowDrawable 是否显示对号图片
     * @param handler        Handler
     * @param eventListener  监听
     * @param tip            提示信息
     */
    public void setDialogVerify(Context context, String title, String content, String leftText, String rightText, String tip, boolean isShowDrawable, Handler handler, DialogClickListener eventListener) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.leftText = leftText;
        this.rightText = rightText;
        this.tip = tip;
        this.isShowDrawable = isShowDrawable;
        this.eventListener = eventListener;
        this.mHandler = handler;
    }

    public void setDialogVerify(Context context, String title, String content, String leftText, String rightText, String tip, boolean isShowDrawable, Handler handler, boolean isBold, DialogClickListener eventListener) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.leftText = leftText;
        this.rightText = rightText;
        this.tip = tip;
        this.isShowDrawable = isShowDrawable;
        this.eventListener = eventListener;
        this.mHandler = handler;
        this.isBold = isBold;
    }

    /**
     * 带有EditText的
     */
    public void setDialogVerify(Context context, String title, String content, String hint, String leftText, String rightText, String tip, boolean isShowDrawable, Handler handler, DialogClickListener eventListener) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.hint = hint;
        this.leftText = leftText;
        this.rightText = rightText;
        this.tip = tip;
        this.isShowDrawable = isShowDrawable;
        this.eventListener = eventListener;
        this.mHandler = handler;
    }

    /**
     * 显示dialog
     *
     * @param
     */
    public void showStandardDialog() {
        showDialog(Gravity.CENTER);
    }

    /**
     * 显示提示信息的Dialog
     */

    private void showDialog(int center) {
        try {
            if (mDialog != null) {
                mDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_SEARCH;
            }
        };
        try {
            View view = View.inflate(context, R.layout.common_dialog, null);
            dialog_textView_title = (TextView) view.findViewById(R.id.dialog_textView_title);//标题
            dialog_textView_content = (TextView) view.findViewById(R.id.dialog_textView_content);//内容
            dialog_textView_left = (TextView) view.findViewById(R.id.dialog_textView_left);//左按钮
            dialog_textView_right = (TextView) view.findViewById(R.id.dialog_textView_right);//右按钮
            dialog_layout_button = (LinearLayout) view.findViewById(R.id.dialog_layout_button);//按钮栏
            dialog_textView_segment = (TextView) view.findViewById(R.id.dialog_textView_segment);//按钮之间分割线

            if (isBold) {//粗体字
                dialog_textView_title.getPaint().setFakeBoldText(true);//标题
                dialog_textView_content.getPaint().setFakeBoldText(true);//内容
                dialog_textView_left.getPaint().setFakeBoldText(true);
                dialog_textView_right.getPaint().setFakeBoldText(true);
            }
            //标题
            if (!TextUtils.isEmpty(title)) {
                dialog_textView_title.setText(title);
            } else {
                dialog_textView_title.setVisibility(View.GONE);
            }
            //内容
            if (null == content || "".equals(content)) {
                dialog_textView_content.setVisibility(View.GONE);
            } else {
                dialog_textView_content.setGravity(gravity);
                dialog_textView_content.setText(content);
            }
            //隐藏按钮栏
            if ((null == leftText || "".equals(leftText)) && (null == rightText || "".equals(rightText))) {
                dialog_layout_button.setVisibility(View.GONE);
            }

            setTextColor(dialog_textView_left, dialog_textView_right);

            if (null == leftText || "".equals(leftText)) {
                dialog_textView_left.setVisibility(View.GONE);
                dialog_textView_segment.setVisibility(View.GONE);
            } else {
                dialog_textView_left.setText(leftText);
            }
            if (null == rightText || "".equals(rightText)) {
                dialog_textView_right.setVisibility(View.GONE);
                dialog_textView_segment.setVisibility(View.GONE);
            } else {
                dialog_textView_right.setText(rightText);
            }

            dialog_textView_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_textView_right.setOnClickListener(null);
                    eventListener.leftEvent();
                }
            });
            dialog_textView_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog_textView_left.setOnClickListener(null);
                    eventListener.rightEvent();
                }
            });
            mDialog = new Dialog(context, R.style.dialog_background_dimEnabled);//添加相应的样式
            mDialog.setContentView(view);//设置显示的布局文件
            /** 将对话框的大小按屏幕大小的百分比设置 **/
            resetDialogSize();


            mDialog.setCancelable(isCancelable);
            mDialog.setCanceledOnTouchOutside(isCancelable);
            if (!isCancelable) {
                mDialog.setOnKeyListener(keyListener);
            }
            mDialog.show();
        } catch (Exception e) {
            Log.e("exception", "e==== " + e);
        }
    }

    /**
     * 显示EditText的Dialog
     */
    public void showAliasDialog() {
        try {
            if (mDialog != null) {
                mDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        View view = View.inflate(context, R.layout.dialog_edittext, null);
        TextView tv_title = (TextView) view.findViewById(R.id.dialog_tv_title);
        et_alias = (EditText) view.findViewById(R.id.dialog_et_alias);
        final TextView tv_left_ok = (TextView) view.findViewById(R.id.dialog_tv_left);
        TextView tv_right_skip = (TextView) view.findViewById(R.id.dialog_tv_right);

        setTextColor(tv_left_ok, tv_right_skip);

        /** 添加监听 */
        et_alias.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    tv_left_ok.setClickable(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                String s1 = s.toString();

            }
        });

        /** 左侧确定按钮点击事件 */
        tv_left_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message();
                message.what = 0;
                message.obj = et_alias.getText().toString();
                if (mHandler != null) {
                    mHandler.sendMessage(message);
                }
                if (eventListener != null) {
                    eventListener.leftEvent();
                }
            }
        });
        /** 右侧确定按钮点击事件 */
        tv_right_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventListener != null) {
                    eventListener.rightEvent();
                }
            }
        });


        mDialog = new Dialog(context, R.style.dialog_background_dimEnabled);
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        resetDialogSize();
        mDialog.setCancelable(isCancelable);
        mDialog.setCanceledOnTouchOutside(isCancelable);
        mDialog.show();
    }

    /**
     * 获取edittext输入的内容
     */
    public String getEdittextContent() {
        if (et_alias != null) {
            Editable text = et_alias.getText();
            if (text != null) {
                return text.toString();
            } else
                return "";
        } else {
            return "";
        }
    }

    /**
     * 显示dialog
     *
     * @param contentGravity 显示内容gravity
     */
    public void showStandardDialog(int contentGravity) {
        showDialog(contentGravity);
    }

    /**
     * 重置dialog的大小及位置
     */
    private void resetDialogSize() {
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        /*
         * 将对话框的大小按屏幕大小的百分比设置
         */
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.75); // 宽度设置为屏幕的0.65
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(p);
    }

    /**
     * 给两个按钮设置颜色
     *
     * @param leftText  左
     * @param rightText 右
     */
    private void setTextColor(TextView leftText, TextView rightText) {
        if (leftColor != -1) {
            leftText.setTextColor(leftColor);
        }
        if (rightColor != -1) {
            rightText.setTextColor(rightColor);
        }
    }

    /**
     * 关闭dialog
     *
     * @param
     */
    public void closeDilog() {
        if (mDialog != null) {
            try {
                mDialog.dismiss();
                mDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public interface DialogClickListener {

        /**
         * 左按钮点击事件
         */
        void leftEvent();

        /**
         * 右按钮点击事件
         */
        void rightEvent();
    }
}
