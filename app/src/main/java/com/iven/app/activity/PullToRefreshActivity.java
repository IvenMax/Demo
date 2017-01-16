package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iven.app.R;
import com.iven.app.tools.L;
import com.iven.app.view.PullToRefreshLayout;

import java.util.ArrayList;

public class PullToRefreshActivity extends AppCompatActivity {
    private static final String TAG = "zpy_PullToRefreshActivity";

    private ListView test_pull_listview;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> datas;
    private PullToRefreshLayout mPullToRefreshView;
    private boolean isShowloading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);

        init();
    }

    private void init() {
        test_pull_listview = (ListView) findViewById(R.id.test_pull_listview);
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("test---   " + i);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas);
        test_pull_listview.setAdapter(adapter);
        initPullToRefreshLayout();
    }

    private void initPullToRefreshLayout() {
        mPullToRefreshView = (PullToRefreshLayout) findViewById(R.id.pull_to_refresh);
        //        createRefreshView();
        mPullToRefreshView.setChildView(test_pull_listview);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                L.e(TAG, "onRefresh: 49" + "行     = ");
                isShowloading = false;
            }

            @Override
            public void onDragDistanceChange(float distance, float percent, float offset) {
            }

            @Override
            public void onFinish() {
                L.e(TAG, "onFinish: 59" + "行     = " + "finish.................");
            }
        });
    }
}
