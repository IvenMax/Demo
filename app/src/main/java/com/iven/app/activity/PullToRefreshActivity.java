package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.iven.app.R;
import com.iven.app.adapter.CommonAdapter;
import com.iven.app.adapter.ViewHolder;
import com.iven.app.bean.ZhiHuHistoryBean;
import com.iven.app.view.PullToRefreshLayout;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class PullToRefreshActivity extends AppCompatActivity {
    private static final String TAG = "zpy_PullToRefreshActivity";

    private ListView test_pull_listview;
    private ArrayAdapter<String> adapter;
    private ArrayList<ZhiHuHistoryBean.StoriesBean> datas;
    //刷新相关
    private PullToRefreshLayout mPullToRefreshView;
    private ImageView mImageView;
    private String startDate = "20141101";
    private CommonAdapter mAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);

        init();
        initPullToRefreshLayout();
    }

    private void init() {
        datas = new ArrayList<>();
        test_pull_listview = (ListView) findViewById(R.id.test_pull_listview);
        test_pull_listview.setAdapter(mAdapter = new CommonAdapter<ZhiHuHistoryBean.StoriesBean>(getApplicationContext(), datas, R.layout.layout_item_pull_to_refresh) {
            @Override
            public void convert(ViewHolder viewHolder, ZhiHuHistoryBean.StoriesBean item) {
                //数据绑定iv_pull_to_refresh   tv_pull_to_refresh
                viewHolder.setText(R.id.tv_pull_to_refresh, item.getTitle());
            }
        });
        http_net(startDate);
    }

    private void initPullToRefreshLayout() {
        //-------------------------下拉刷新
        mPullToRefreshView = (PullToRefreshLayout) findViewById(R.id.pull_to_refresh);
        createRefreshView();
        mPullToRefreshView.setChildView(test_pull_listview);//将ListView添加到Layout中去

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.setRefreshing(false);
                int i = Integer.parseInt(startDate);
                int i1 = i + 1;
                http_net(i1 + "");
            }

            @Override
            public void onDragDistanceChange(float distance, float percent, float offset) {
                mPullToRefreshView.setRefreshing(false);
            }

            @Override
            public void onFinish() {
            }
        });
        //-------------------------下拉刷新
    }

    /**
     * 网络请求---此处使用的知乎日报的历史消息来测试，测试而已 (/ □ \)
     */
    private void http_net(String startDate) {
        //http://news-at.zhihu.com/api/4/news/before/20141101
        String url = "http://news-at.zhihu.com/api/4/news/before/" + startDate;
        OkGo.get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = new Gson();
                ZhiHuHistoryBean storiesBean = gson.fromJson(s, ZhiHuHistoryBean.class);
                List<ZhiHuHistoryBean.StoriesBean> stories = storiesBean.getStories();
                datas.addAll(stories);
                mAdapter.notifyDataSetChanged();
            }
        });


    }

    public View createRefreshView() {
        View headerView = mPullToRefreshView.setRefreshView(R.layout.layout_head);
        progressBar = (ProgressBar) headerView.findViewById(R.id.pb_view);
        mImageView = (ImageView) headerView.findViewById(R.id.text_view);
        mImageView.setImageResource(R.mipmap.drawable_text);
        //		imageView = (ImageView) headerView.findViewById(R.id.image_view);
        //		imageView.setVisibility(View.VISIBLE);
        //		imageView.setImageResource(R.mipmap.drawable_arrow);
        //		progressBar.setVisibility(View.GONE);
        return headerView;
    }
}
