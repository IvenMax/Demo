package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.iven.app.R;
import com.iven.app.bean.ColumnBean;
import com.iven.app.view.ChartView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChartViewActivity extends AppCompatActivity {
    private ChartView chartview;
    private static final String TAG = "zpy_ChartViewActivity";
    private ArrayList<ColumnBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);
        chartview = (ChartView) findViewById(R.id.chartview);
    }

    public void loadData(View view) {
        chartview.setData(getData());
        Log.e(TAG, "loadData: 31" + "行 = " + getData().size());
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
