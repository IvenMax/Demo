package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.iven.app.R;
import com.iven.app.adapter.MyRecyclerAdapter;
import com.iven.app.bean.ColumnBean;
import com.iven.app.bean.TestBean;
import com.iven.app.callback.JsonCallback;
import com.iven.app.view.BouncingMenu;
import com.iven.app.view.FloatView;
import com.lzy.okgo.OkGo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "zpy_TestActivity";
    private FloatView float_view;
    private BouncingMenu mBouncingMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        float_view = (FloatView) findViewById(R.id.float_view);
        ArrayList<String> datas = new ArrayList();
        for (int i = 0; i < 30; i++) {
            datas.add("item   "+i);
        }
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(datas);
        mBouncingMenu = BouncingMenu.makeMenu(findViewById(R.id.ll_act_test),R.layout.layout_bounch_sv,adapter);
//        mBouncingMenu.show();
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
            Log.e(TAG, "getData: 41" + "行 = " + columnBean.toString());
        }
        return list;
    }

    private double getDecimal(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return Double.parseDouble(decimalFormat.format(value));
    }

    public void loadFloatViewDate(View view) {
//        float_view.setData(getData());
        Log.e(TAG, "loadFloatViewDate: 68" + "行 = " );
        OkGo.get("https://api.douban.com/v2/movie/top250?start=1&count=20")
                .execute(new JsonCallback<TestBean>() {
                    @Override
                    public void onSuccess(TestBean testBean, Call call, Response response) {
                        int count = testBean.getCount();
                        Log.e(TAG, "onSuccess: 75" + "行 = count = " +count);
                        Log.e(TAG, "onSuccess: 72" + "行 = " +"-----"+testBean.getTitle());
                        List<TestBean.SubjectsBean> subjects = testBean.getSubjects();
                        int size = subjects.size();
                        Log.e(TAG, "onSuccess: 77" + "行 = size = " +size);

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e(TAG, "onError: 79" + "行 = " );
                    }
                });
//        mBouncingMenu.show();
    }

    public void stopDj(View view) {
//        mBouncingMenu.dismiss();
    }
}
