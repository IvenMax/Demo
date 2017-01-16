package com.iven.app.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.iven.app.R;

/**
 * @auth Iven
 * 2017/1/15 10:58
 * @desc
 */

public class UpdateDialogFragment extends DialogFragment {
    private Dialog mDialog;
    private DialogClickListener eventListener;// 按钮点击回调
    private TextView tv_left;
    private TextView tv_right;
    private String titile;
    private String content;
    private String leftContent;
    private String rightContent;
    private TextView dialog_line;
    private TextView dialog_textView_content;
    TextView dialog_textView_title;
    private OnKeyListener onKeyListener;

    public UpdateDialogFragment() {

    }

    @SuppressLint("ValidFragment")
    public UpdateDialogFragment(String title, String content, String leftContent, String rightContent, DialogClickListener dialogClickListener) {
        this.titile = title;
        this.content = content;
        this.leftContent = leftContent;
        this.rightContent = rightContent;
        eventListener = dialogClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.updatedialog, null);
        mDialog = new Dialog(getActivity(), R.style.dialog_background_dimEnabled);
        mDialog.setContentView(view);//添加view
        dialogSettings();
        initView(view);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (onKeyListener != null) {
                    return onKeyListener.onkeyDown(keyCode, event);
                } else
                    return false;
            }
        });
        return mDialog;

    }

    private void initView(View view) {
        tv_left = ((TextView) view.findViewById(R.id.dialog_textView_left));
        tv_right = ((TextView) view.findViewById(R.id.dialog_textView_right));
        dialog_textView_content = ((TextView) view.findViewById(R.id.dialog_textView_content));
        dialog_textView_title = ((TextView) view.findViewById(R.id.dialog_textView_title));
        dialog_line = ((TextView) view.findViewById(R.id.dialog_line));
        //左侧按钮点击事件
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventListener.leftEvent();
                dismiss();
            }
        });
        //右侧按钮点击事件
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventListener.rightEvent();
                dismiss();
            }
        });
        if (!TextUtils.isEmpty(leftContent)) {
            tv_left.setVisibility(View.VISIBLE);
            tv_left.setText(leftContent);
            dialog_line.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(rightContent)) {
            tv_right.setVisibility(View.VISIBLE);
            tv_right.setText(rightContent);
        }
        //dialog_textView_title.setText(titile);
        dialog_textView_content.setText(content);
    }

    /**
     * Dialog相关设置
     */
    private void dialogSettings() {
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.42); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        dialogWindow.setAttributes(p);
        mDialog.setCanceledOnTouchOutside(false);//外部禁止点击
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mDialog != null) {
            dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mDialog != null) {
            dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    public interface OnKeyListener {
        boolean onkeyDown(int keyCode, KeyEvent event);
    }

    public void setOnKeyListener(OnKeyListener listener) {
        this.onKeyListener = listener;
    }
}
