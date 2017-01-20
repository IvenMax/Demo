package com.iven.app.base;

import android.app.Fragment;
import android.view.View;

import com.iven.tools.base.BaseActivity;

/**
 * @author Iven
 * @date 2017/1/20 9:45
 * @Description Fragment基类
 */

public abstract class BaseFragment extends Fragment {
    protected BaseActivity mBaseActivity;
    protected View mRootView;

}
