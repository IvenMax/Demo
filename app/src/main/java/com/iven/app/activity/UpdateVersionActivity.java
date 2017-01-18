package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.iven.app.R;
import com.iven.app.utils.AppInfoUtils;
import com.iven.app.view.GuideView;
import com.iven.app.view.UpdateDialogFragment;

public class UpdateVersionActivity extends AppCompatActivity {
    private static final String TAG = "zpy_UpdateVersion";
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_version);
        mButton = (Button) findViewById(R.id.btn_guideview);
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_guideview:
                showMask235();
                break;
            default:
                String version = AppInfoUtils.getVersion(UpdateVersionActivity.this, true);
                Log.e(TAG, "btnClick: 20" + "行 = version=" + version);
                showUpdateDialog();
                break;
        }
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

    private void showMask235() {
        final ImageView iv = new ImageView(this);
        iv.setImageResource(R.mipmap.menu_2_3_5);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        iv.setLayoutParams(params);
        final GuideView guideView = GuideView.Builder.newInstance(this).setTargetView(mButton)//设置目标
                .setCustomGuideView(iv)//设置蒙层上面使用的图片
                .setDirction(GuideView.Direction.BOTTOM).setShape(GuideView.MyShape.RECTANGULAR)   // 设置圆形显示区域，
                .setOffset(10, 10).setGap(10, 10).setRadius(20).setBgColor(getResources().getColor(R.color.color_cc222222)).setOnclickListener(new GuideView.OnClickCallback() {
                    @Override
                    public void onClickedGuideView() {
                    }
                }).setOnShowListener(new GuideView.OnShowCallback() {
                    @Override
                    public void onShowGuideView() {
                    }
                }).build();
        guideView.setOnclickListener(new GuideView.OnClickCallback() {
            @Override
            public void onClickedGuideView() {
                if (null != guideView) {
                    guideView.hide();
                }
            }
        });
        guideView.show();
    }
}
