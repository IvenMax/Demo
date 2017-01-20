package com.iven.app.activity;

import android.view.View;
import android.widget.Button;

import com.iven.app.R;
import com.iven.app.tools.T;
import com.iven.app.utils.DialogUtils;
import com.iven.tools.base.BaseActivity;

public class DialogUtilActivity extends BaseActivity {

    private Button btn_info_dialog;
    private Button btn_et_dialog;

    @Override
    public int setLayout() {
        return R.layout.activity_dialog_util;
    }

    @Override
    public void setTitle() {
        title_title.setText("DialogUtils");
        title_left.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.title_back), null, null, null);
        title_left.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
        btn_info_dialog = (Button) findViewById(R.id.btn_info_dialog);
        btn_info_dialog.setOnClickListener(this);
        btn_et_dialog = (Button) findViewById(R.id.btn_et_dialog);
        btn_et_dialog.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.btn_info_dialog:
                showTips();
                break;
            case R.id.btn_et_dialog:
                showEditTextTips();
                break;
        }
    }

    private void showTips() {
        final DialogUtils dialogUtils = new DialogUtils(this, false);
        dialogUtils.setDialogVerify(this, "title", "this is content", "left", "right", null, false, null, new DialogUtils.DialogClickListener() {
            @Override
            public void leftEvent() {
                T.showShort(DialogUtilActivity.this, "left click");
                dialogUtils.closeDilog();
            }

            @Override
            public void rightEvent() {
                T.showShort(DialogUtilActivity.this, "right click");
                dialogUtils.closeDilog();
            }
        });
        dialogUtils.showStandardDialog();
    }

    private void showEditTextTips() {
        final DialogUtils dialogUtils = new DialogUtils(this, false);
        dialogUtils.setDialogVerify(this, "title", null, "hint", "left", "right", null, false, null, new DialogUtils.DialogClickListener() {
            @Override
            public void leftEvent() {
                String edittextContent = dialogUtils.getEdittextContent();
                T.showShort(DialogUtilActivity.this, edittextContent + "---left");
                dialogUtils.closeDilog();
            }

            @Override
            public void rightEvent() {
                String edittextContent = dialogUtils.getEdittextContent();
                T.showShort(DialogUtilActivity.this, edittextContent + "--right");
                dialogUtils.closeDilog();
            }
        });
        dialogUtils.showAliasDialog();
    }
}
