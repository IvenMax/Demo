package com.iven.app.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.bean.ColumnBean;
import com.iven.app.view.TimeSharingView;

import java.text.DecimalFormat;
import java.util.ArrayList;

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
        timeSharingView = (TimeSharingView) view.findViewById(R.id.timeshare_view);
        mTextView = (TextView) view.findViewById(R.id.tv_log);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
            }
        });
        return view;
    }

    private void setData() {
        h = 9;
        m = 25;
        ArrayList<ColumnBean> list = getData();
        timeSharingView.setData(list);
    }

    //创建假数据
    int h = 9;
    int m = 25;

    public ArrayList<ColumnBean> getData() {
        ArrayList<ColumnBean> list = new ArrayList<ColumnBean>();
        for (int i = 0; i < 49; i++) {
            ColumnBean columnBean = new ColumnBean();
            double value = ((Math.random() * 10000));
            double netValue = ((Math.random() * 2));
            value = getDecimal(value);
            columnBean.setValue(value);
            columnBean.setNetValue(get4Decimal(netValue));
            m += 5;
            String mm = m >= 60 ? "00" : String.valueOf(m < 10 ? ("0" + m) : m);
            if (m >= 60) {
                h++;
                m = 0;
            }
            if (h == 11 && m > 30) {
                h = 13;
                m = 5;
                mm = "05";
            }
            columnBean.setDate((h < 10 ? ("0" + h) : h) + ":" + mm);
            list.add(columnBean);
        }
        for (int i = 0; i < list.size(); i++) {
            Log.e(TAG, "getData: value = " + list.get(i).toString());
        }
        return list;

    }

    private double getDecimal(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return Double.parseDouble(decimalFormat.format(value));
    }

    private double get4Decimal(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.0000");
        return Double.parseDouble(decimalFormat.format(value));
    }

    //快速排序
    public void sort(int arr[], int low, int high) {
        int l = low;
        int h = high;
        int povit = arr[low];

        while (l < h) {
            while (l < h && arr[h] >= povit)
                h--;
            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                l++;
            }

            while (l < h && arr[l] <= povit)
                l++;

            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                h--;
            }
        }
        //        print(arr);
        if (l > low)
            sort(arr, low, l - 1);

    }
}
