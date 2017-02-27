package com.iven.app.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iven.app.R;
import com.iven.app.adapter.MyRecyclerAdapter;
import com.iven.app.view.BouncingMenu;

import java.util.ArrayList;

/**
 * @author Iven
 * @date 2017/1/19 14:24
 * @Description
 */

public class FirstFragment extends Fragment {
    private Context mContext;
    private static final String TAG = "zpy_FirstFragment";
    private BouncingMenu mBouncingMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_first, container, false);
        ArrayList<String> datas = new ArrayList();
        for (int i = 0; i < 30; i++) {
            datas.add("item   " + i);
        }
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(datas);
        mBouncingMenu = BouncingMenu.makeMenu(view.findViewById(R.id.ll_frag), R.layout.layout_bounch_sv, adapter);
        mBouncingMenu.show();
        return view;
    }
}
