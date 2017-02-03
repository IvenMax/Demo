package com.iven.app.activity.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.iven.app.R;
import com.iven.app.adapter.RrecyclerviewAdapter;
import com.iven.app.bean.MovieBean;
import com.iven.app.utils.ConstantValue;
import com.iven.tools.tools.T;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class RecyclerviewActivity extends AppCompatActivity {
    private static final String TAG = "zpy_RecyclerviewActivity";
    private RecyclerView recyclerview_basic;
    private List<MovieBean.DataBean.ComingBean> mComingBeanList;
    private RrecyclerviewAdapter mRrecyclerviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        init();
    }

    private void init() {
        recyclerview_basic = (RecyclerView) findViewById(R.id.recyclerview_basic);
        mComingBeanList = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(RecyclerviewActivity.this, 1);
        recyclerview_basic.setLayoutManager(manager);

        http_load();
    }

    /**
     * 获取数据
     */
    private void http_load() {
        OkGo.get(ConstantValue.MOVIE_URL).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                mComingBeanList = parserData(s);
                mRrecyclerviewAdapter = new RrecyclerviewAdapter(mComingBeanList, RecyclerviewActivity.this);
                recyclerview_basic.setAdapter(mRrecyclerviewAdapter);
                mRrecyclerviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                T.showShort(RecyclerviewActivity.this, e.getMessage());
            }
        });
    }

    //解析数据
    private List<MovieBean.DataBean.ComingBean> parserData(String string) {
        Gson gson = new Gson();
        MovieBean movieBean = gson.fromJson(string, MovieBean.class);
        List<MovieBean.DataBean.ComingBean> coming = movieBean.getData().getComing();
        return coming;
    }
}
