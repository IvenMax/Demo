package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iven.app.R;

import java.util.ArrayList;

/**
 * ViewDragHelper 混合ListView的使用实现简易的下拉刷新效果
 * 2017年1月15日 16:40:38
 */
public class VDHActivity extends AppCompatActivity {

    private ListView vdh_listview;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vdh);
        init();
    }

    private void init() {
//        vdh_listview = (ListView) findViewById(R.id.vdh_listview);
//        datas = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            datas.add("test   " + i);
//        }
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas);
//        vdh_listview.setAdapter(adapter);

    }
}
