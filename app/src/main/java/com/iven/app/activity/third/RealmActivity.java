package com.iven.app.activity.third;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Realm数据库
 * 参考链接：
 * http://www.jianshu.com/p/28912c2f31db#
 * https://gold.xitu.io/entry/57ce91eca22b9d006c305995
 */
public class RealmActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "zpy_TestActivity";
    private ListView list_view;
    private ArrayList<InvestBean> datas;
    private CommonAdapter<InvestBean> mAdapter;
    private Button btn_realm_get, btn_realm_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
        http_post();
    }

    private void init() {
        btn_realm_get = (Button) findViewById(R.id.btn_realm_get);
        btn_realm_save = (Button) findViewById(R.id.btn_realm_save);
        btn_realm_get.setOnClickListener(this);
        btn_realm_save.setOnClickListener(this);
        datas = new ArrayList<>();
        list_view = (ListView) findViewById(R.id.list_view);
        list_view.setAdapter(mAdapter = new CommonAdapter<InvestBean>(this, datas, R.layout.layout_item_3tv) {
            @Override
            public void convert(ViewHolder viewHolder, InvestBean item) {
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
                ArrayList<InvestBean> lll = null;
                try {
                    lll = new ArrayList<InvestBean>();
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.optJSONObject(i);
                        InvestBean bean = new InvestBean();
                        bean.setId(object.optInt("id"));
                        bean.setName(object.optString("name"));
                        bean.setOption(object.optBoolean("option"));
                        lll.add(bean);
                    }
                    datas.addAll(lll);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String addParams() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id", "T000003652");
        Log.i("json数据", jsonObject.toString());//just for log
        return jsonObject.toString();
    }


    @Override
    public void onClick(View v) {
        Log.e(TAG, "btnClick: 113" + "行 = " + "kaishi...............");
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name("name.realm").build();
        Realm realm = Realm.getInstance(realmConfiguration);
        switch (v.getId()) {
            case R.id.btn_realm_save:
                Log.e(TAG, "onClick: 132" + "行 = " + datas.size() + "----");
                if (null == datas) {
                    return;
                }
                realm.beginTransaction();
                for (int i = 0; i < datas.size(); i++) {
                    InvestBean bean = datas.get(i);
                    realm.copyToRealm(bean);
                }
                realm.commitTransaction();
                break;
            case R.id.btn_realm_get:
                if (datas.size() !=0){
                    datas.clear();
                }
                RealmResults<InvestBean> all = realm.where(InvestBean.class).findAll();
                List<InvestBean> investBeen = realm.copyFromRealm(all);
                datas.addAll(investBeen);
                mAdapter.notifyDataSetChanged();
                for (int i = 0; i < investBeen.size(); i++) {
                    Log.e(TAG, "onClick: 145" + "行 = " + investBeen.get(i).toString());
                }
                break;
            case R.id.btn_realm_delete:
                realm.beginTransaction();
                InvestBean object = realm.createObject(InvestBean.class);
                if (object.getName().equals("传媒")){
                    object.deleteFromRealm();
                }
                realm.commitTransaction();

                RealmResults<InvestBean> test = realm.where(InvestBean.class).findAll();
                List<InvestBean> ttttt = realm.copyFromRealm(test);
                for (int i = 0; i < ttttt.size(); i++) {
                    Log.e(TAG, "onClick: 166" + "行 = " +ttttt.get(i));
                }
                break;
        }
    }
}
