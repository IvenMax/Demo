package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.iven.app.R;
import com.iven.app.bean.ColumnBean;
import com.iven.app.view.FloatView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "zpy_TestActivity";
    private FloatView float_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        float_view = (FloatView) findViewById(R.id.float_view);
        float_view.setData(getData());
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

    public void loadFloatViewDate(View view) {
        float_view.setData(getData());
    }
}
