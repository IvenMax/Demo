package com.iven.app.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iven.app.R;

/**
 * @author Iven
 * @date 2017/1/19 14:24
 * @Description
 */

public class FirstFragment extends Fragment {
    private Context mContext;
    private static final String TAG = "zpy_FirstFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_first, container, false);
        Button button = (Button) view.findViewById(R.id.btn_fragment_01);
        return view;
    }
}
