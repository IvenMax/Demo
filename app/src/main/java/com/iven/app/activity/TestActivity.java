package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iven.app.R;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ListView test_listview;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initListView();
    }

    private void initListView() {
        test_listview = (ListView) findViewById(R.id.test_listview);
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("test---   " + i);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas);
        test_listview.setAdapter(adapter);
    }
}
