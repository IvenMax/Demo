package com.iven.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iven.app.R;
import com.iven.app.adapter.CommonAdapter;
import com.iven.app.adapter.ViewHolder;
import com.iven.app.bean.InvestBean;
import com.iven.app.bean.News;
import com.iven.app.utils.ConstantValue;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "zpy_TestActivity";
    private ListView list_view;
    private ArrayList<InvestBean.DataBean> datas;
    private CommonAdapter<InvestBean.DataBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
        http_post();
    }

    private void init() {
        datas = new ArrayList<>();
        list_view = (ListView) findViewById(R.id.list_view);
        list_view.setAdapter(mAdapter = new CommonAdapter<InvestBean.DataBean>(this, datas, R.layout.layout_item_3tv) {
            @Override
            public void convert(ViewHolder viewHolder, InvestBean.DataBean item) {
                viewHolder.setText(R.id.tv_3_id, item.getId() + "");
                viewHolder.setText(R.id.tv_3_name, item.getName());
                viewHolder.setText(R.id.tv_3_option, item.isOption() + "");
                TextView textView = viewHolder.getView(R.id.tv_3_option);
                if (item.isOption()) {
                    textView.setTextColor(Color.RED);
                } else {
                    textView.setTextColor(Color.GREEN);
                }

            }
        });
    }

    private void http_get() {
        OkGo.get(ConstantValue.ZHIHU_LATEST).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = new Gson();
                News news = gson.fromJson(s, News.class);

            }
        });
    }

    public void http_post() {
        OkGo.post("http://10.0.140.22:8315/investment_target/query").tag(this).upJson(addParams()).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = new Gson();
                Log.e(TAG, "onSuccess: 58" + "行 = ");
                InvestBean investBean = gson.fromJson(s, InvestBean.class);
                List<InvestBean.DataBean> data = investBean.getData();
                datas.addAll(data);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private String addParams() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", "T000003652");
        Log.i("json数据", jsonObject.toString());//just for log
        return jsonObject.toString();
    }

    public void btnClick(View view) {
        switch (view.getId()){
            case R.id.btn_realm_save:
                break;
        }
    }
}
