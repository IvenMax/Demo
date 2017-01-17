package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iven.app.R;
import com.iven.app.utils.AppInfoUtils;
import com.iven.app.view.UpdateDialogFragment;

public class UpdateVersionActivity extends AppCompatActivity {
    private static final String TAG = "zpy_UpdateVersion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_version);
    }

    public void btnClick(View view) {
        String version = AppInfoUtils.getVersion(this, true);
        Log.e(TAG, "btnClick: 20" + "行 = version=" + version);
        showUpdateDialog();
    }

    private void showUpdateDialog() {
        String content = "1.修复xxx Bug;\\n2.更新UI界面.";
        String apkUrl = "https://dbank.qrcb.com.cn/web/bao/Dbank_app_android.apk";
        final UpdateDialogFragment dialogFragment = new UpdateDialogFragment("title", content, "取消", "更新", new UpdateDialogFragment.DialogClickListener() {
            @Override
            public void leftEvent() {
                Toast.makeText(UpdateVersionActivity.this, "取消更新...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightEvent() {
                Toast.makeText(UpdateVersionActivity.this, "right click", Toast.LENGTH_SHORT).show();
            }
        });
        dialogFragment.show(getFragmentManager(), "tag");
    }
}
