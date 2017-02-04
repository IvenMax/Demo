package com.iven.app.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.iven.app.R;
import com.iven.app.bean.ColumnBean;
import com.iven.app.view.ChartView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @author Iven
 * @date 2017/2/4 11:59
 * @Description
 */

public class ColumnFragment extends Fragment {
    private static final String TAG = "zpy_ColumnFragment";
    private Context mContext;
    private Button btn_column_load;
    private ChartView chartview;
    private ArrayList<ColumnBean> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chart_view, container, false);
        btn_column_load = (Button) view.findViewById(R.id.btn_column_load);
        chartview = (ChartView) view.findViewById(R.id.chartview);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_column_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: 47" + "行 = " );
                chartview.setData(getData());
            }
        });
    }

    public ArrayList<ColumnBean> getData() {
        ArrayList<ColumnBean> list = new ArrayList<ColumnBean>();
        for (int i = 0; i < 31; i++) {
            ColumnBean columnBean = new ColumnBean();
            double value = (10000 - ((Math.random() * 20000)));
            value = getDecimal(value);
            columnBean.setValue(value);
            if (i + 1 < 10) {
                columnBean.setDate("2016-11-0" + (i + 1));
            } else {
                columnBean.setDate("2016-11-" + (i + 1));
            }
            list.add(columnBean);
        }
        return list;
    }

    private double getDecimal(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return Double.parseDouble(decimalFormat.format(value));
    }

}
