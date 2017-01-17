package com.iven.tools;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.iven.tools.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = "IVEN_MainActivity";
    private Button btn_location;
    private Context context;

    @Override
    public int setLayout() {
        return 0;
    }

    @Override
    public void setTitle() {
        title_title.setText("首页");
        title_left.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.title_back), null, null, null);
        title_left.setOnClickListener(this);
    }

    @Override
    public void initWidget() {
    }

    @Override
    public void widgetClick(View view) {
    }
}
