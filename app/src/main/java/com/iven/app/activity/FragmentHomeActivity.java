package com.iven.app.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.iven.app.R;
import com.iven.app.fragment.FirstFragment;
import com.iven.app.fragment.SecondFragment;
import com.iven.app.tools.T;

/**
 * Fragment复习
 * 泓洋：http://blog.csdn.net/lmj623565791/article/details/42628537
 * 郭霖：http://blog.csdn.net/guolin_blog/article/details/8881711
 */
public class FragmentHomeActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private RadioButton rdb_left, rdb_right;
    private Context mContext;
    private FragmentManager manager;
    private FirstFragment mFirstFragment;
    private SecondFragment mSecondFragment;
    Bundle mBundle = new Bundle();//用于在两个碎片之间传值


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_home);
        mContext = this;
        manager = getFragmentManager();
        if (null != savedInstanceState) {
            mFirstFragment = (FirstFragment) manager.findFragmentByTag("first");
            mSecondFragment = (SecondFragment) manager.findFragmentByTag("second");
        }
        init();
    }

    private void init() {
        rdb_left = (RadioButton) findViewById(R.id.rdb_left);
        rdb_right = (RadioButton) findViewById(R.id.rdb_right);
        rdb_left.setOnCheckedChangeListener(this);
        rdb_right.setOnCheckedChangeListener(this);
        mFirstFragment = new FirstFragment();
        mSecondFragment = new SecondFragment();
        rdb_left.setChecked(true);//默认让选中第一个Fragment
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked && null != manager) {
            switch (buttonView.getId()) {
                case R.id.rdb_left:
                    T.showShort(mContext, "left choosed");
                    switchContent(mSecondFragment, mFirstFragment, "first");
                    break;
                case R.id.rdb_right:
                    T.showShort(mContext, "right choosed");
                    switchContent(mFirstFragment, mSecondFragment, "second");
                    break;
            }
        }
    }

    /**
     * Fragmen切换
     *
     * @param from 切换前所显示的
     * @param to   切换的目标
     * @param tag  tag
     */
    public void switchContent(Fragment from, Fragment to, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (null == from || !from.isAdded()) {
            transaction.add(R.id.all_container, to, tag).commitAllowingStateLoss();
            return;
        }
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.all_container, to, tag).commit();
        } else {
            transaction.hide(from).show(to).commitAllowingStateLoss();
        }
    }



    public void setData(Bundle bundle) {
        this.mBundle = bundle;
    }

    public Bundle getData() {
        return mBundle;
    }
}

