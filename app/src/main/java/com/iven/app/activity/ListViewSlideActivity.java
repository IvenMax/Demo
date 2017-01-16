package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.iven.app.R;
import com.iven.app.adapter.CommonAdapter;
import com.iven.app.adapter.ViewHolder;

import java.util.ArrayList;

public class ListViewSlideActivity extends AppCompatActivity {
    private static final String TAG = "zpy_ListViewSlideActivity";
    private ListView lv_slide;
    private ArrayList<String> datas;
    private CommonAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_slide);
        init();
    }

    private void init() {
        lv_slide = (ListView) findViewById(R.id.lv_slide);
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("test   " + i);
        }
        lv_slide.setAdapter(adapter = new CommonAdapter<String>(this, datas, R.layout.layout_item_tv) {
            @Override
            public void convert(ViewHolder viewHolder, final String item) {
                viewHolder.setText(R.id.tv_content, item.toString());
                RelativeLayout delete = viewHolder.getView(R.id.delete_button);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "delete...", Toast.LENGTH_SHORT).show();
                        datas.remove(item);
                        notifyDataSetChanged();
                    }
                });
            }

        });
    }
}
