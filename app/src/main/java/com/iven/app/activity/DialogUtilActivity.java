package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.iven.app.R;
import com.iven.app.tools.T;
import com.iven.app.utils.DialogUtils;

public class DialogUtilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_util);
    }

    //362103198308130210


    public void btnClick(View view) {
        switch (view.getId()) {
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
