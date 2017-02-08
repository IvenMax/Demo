package com.iven.app.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.view.TimeSharingView;

/**
 * @author Iven
 * @date 2017/1/19 14:24
 * @Description
 */

public class SecondFragment extends Fragment {
    private Context mContext;
    private static final String TAG = "zpy_SecondFragment";
    private TimeSharingView timeSharingView;
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_second, container, false);
        return view;
    }

}
