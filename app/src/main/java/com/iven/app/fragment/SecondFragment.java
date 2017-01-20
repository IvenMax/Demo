package com.iven.app.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iven.app.R;
import com.iven.app.activity.FragmentHomeActivity;

/**
 * @author Iven
 * @date 2017/1/19 14:24
 * @Description
 */

public class SecondFragment extends Fragment {
    private Context mContext;
    private static final String TAG = "zpy_SecondFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_second, container, false);
        FragmentHomeActivity activity = (FragmentHomeActivity) getActivity();
        if (null != activity) {
            Bundle data = activity.getData();
            Log.e(TAG, "onCreateView: 31" + "行 = " + data.getString("test"));
        }
        return view;
    }
}
