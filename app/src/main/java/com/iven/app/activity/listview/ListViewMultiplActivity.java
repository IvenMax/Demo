package com.iven.app.activity.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iven.app.R;
import com.iven.app.adapter.CommonAdapter;
import com.iven.app.adapter.ViewHolder;
import com.iven.app.bean.InvestBean;
import com.iven.app.tools.T;
import com.iven.app.view.CheckableLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewMultiplActivity extends AppCompatActivity {
    private ListView lisetView;
    private static final String TAG = "zpy_ListViewMultiplActivity";
    private ArrayList<InvestBean> datas;
    private CommonAdapter adapter;
    private CheckableLayout mCheckableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_multipl);
        init();
    }

    private void init() {
        lisetView = (ListView) findViewById(R.id.listview_multipl);
        datas = new ArrayList<>();

        datas.addAll(getLists());
        lisetView.setAdapter(adapter = new CommonAdapter<InvestBean>(this, datas, R.layout.item_listview_modal) {
            @Override
            public void convert(ViewHolder viewHolder, InvestBean item) {
                viewHolder.setText(R.id.tv_text_name, item.getName());
                mCheckableLayout = viewHolder.getView(R.id.toggle_id);
                SparseBooleanArray checkedItemPositions = lisetView.getCheckedItemPositions();
                boolean b = checkedItemPositions.get(viewHolder.getPosition());
                mCheckableLayout.setChecked(b);
            }

        });
        lisetView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckableLayout checkableLayout = (CheckableLayout) view.findViewById(R.id.toggle_id);
                checkableLayout.toggle();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void btnClick(View view) {
        StringBuilder sb = new StringBuilder();
        SparseBooleanArray checkedItemPositions = lisetView.getCheckedItemPositions();
        for (int i = 0; i < datas.size(); i++) {
            boolean b = checkedItemPositions.get(i);
            if (b) {
                sb.append(datas.get(i).getName()+"-");
            }
        }
        if (null != sb){
            T.showShort(ListViewMultiplActivity.this,sb.toString());
        }

    }

    public static ArrayList<InvestBean> getLists() {
        ArrayList<InvestBean> list = new ArrayList<>();
        String str = "{\"status_code\":200,\"message\":\"success\",\"data\":[{\"id\":63,\"name\":\"传媒\",\"option\":false},{\"id\":32,\"name\":\"餐饮旅游\",\"option\":true},{\"id\":20,\"name\":\"电力及公用事业\",\"option\":false},{\"id\":27,\"name\":\"电力设备\",\"option\":false},{\"id\":60,\"name\":\"电子元器件\",\"option\":true},{\"id\":42,\"name\":\"房地产\",\"option\":false},{\"id\":41,\"name\":\"非银行金融\",\"option\":false},{\"id\":34,\"name\":\"纺织服装\",\"option\":true},{\"id\":28,\"name\":\"国防军工\",\"option\":true},{\"id\":21,\"name\":\"钢铁\",\"option\":true},{\"id\":22,\"name\":\"基础化工\",\"option\":true},{\"id\":24,\"name\":\"建材\",\"option\":true},{\"id\":33,\"name\":\"家电\",\"option\":true},{\"id\":62,\"name\":\"计算机\",\"option\":true},{\"id\":50,\"name\":\"交通运输\",\"option\":true},{\"id\":26,\"name\":\"机械\",\"option\":true},{\"id\":23,\"name\":\"建筑\",\"option\":true},{\"id\":11,\"name\":\"煤炭\",\"option\":true},{\"id\":37,\"name\":\"农林牧渔\",\"option\":true},{\"id\":30,\"name\":\"汽车\",\"option\":true},{\"id\":25,\"name\":\"轻工制造\",\"option\":true},{\"id\":31,\"name\":\"商贸零售\",\"option\":true},{\"id\":36,\"name\":\"食品饮料\",\"option\":true},{\"id\":10,\"name\":\"石油化工\",\"option\":true},{\"id\":61,\"name\":\"通信\",\"option\":true},{\"id\":40,\"name\":\"银行\",\"option\":true},{\"id\":12,\"name\":\"有色金属\",\"option\":true},{\"id\":35,\"name\":\"医药\",\"option\":true},{\"id\":70,\"name\":\"综合\",\"option\":true}]}";
        try {
            JSONObject jsonObject = new JSONObject(str);
            JSONArray array = jsonObject.optJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jo = array.optJSONObject(i);
                int id = jo.optInt("id");
                String name = jo.optString("name");
                boolean option = jo.optBoolean("option");
                InvestBean bean = new InvestBean();
                bean.setId(id);
                bean.setName(name);
                bean.setOption(option);
                list.add(bean);
            }
            Log.e("IVEN", "无网测试" + list.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
