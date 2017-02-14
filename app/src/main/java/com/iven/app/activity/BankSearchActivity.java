package com.iven.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iven.app.R;
import com.iven.app.bean.BankCode;
import com.iven.app.bean.BankSearchBean;
import com.iven.app.utils.ConstantValue;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class BankSearchActivity extends AppCompatActivity {

    private EditText edit_bank_no;
    private TextView tv_show_bank_name;
    private ImageView iv_show_bank_img;
    private static final String TAG = "zpy_BankSearchActivity";
    private ArrayList<BankCode> mBankCodeArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_search);
        mBankCodeArrayList = new ArrayList<>();
        getBankCodeInfoMess();
        init();
    }

    /**
     * 将编号和银行Name对应   -获取到
     */
    private void getBankCodeInfoMess() {
        String name = ConstantValue.BANKCODE_NAME.replace("{", "").replace("}", "").replace("\n", "").replace(" ", "");
        String[] split = name.split(",");
        for (int i = 0; i < split.length; i++) {
            String replace = split[i].replace("\"", "");
            BankCode bankCode = new BankCode();
            bankCode.setBankCode(replace.substring(0, replace.indexOf(":")));
            bankCode.setBankName(replace.substring(replace.indexOf(":") + 1, replace.length()));
            mBankCodeArrayList.add(bankCode);
        }
    }

    private void init() {

        edit_bank_no = (EditText) findViewById(R.id.edit_bank_no);
        tv_show_bank_name = (TextView) findViewById(R.id.tv_show_bank_name);
        iv_show_bank_img = (ImageView) findViewById(R.id.iv_show_bank_img);
        edit_bank_no.addTextChangedListener(new MyTextWatcher());
    }

    public void clearInfo(View view) {
        edit_bank_no.setText("");
        tv_show_bank_name.setText("");
        iv_show_bank_img.setImageResource(R.mipmap.ic_launcher);
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String string = s.toString();
            if (string.length() == 16 || string.length() == 19) {
                getInfo(string);
            }
        }
    }

    private void getInfo(final String bankNo) {
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=" + bankNo + "&cardBinCheck=true";
        final String imgUrl = "https://apimg.alipay.com/combo.png?d=cashier&t=";
        OkGo.get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Gson gson = new Gson();
                BankSearchBean bankSearchBean = gson.fromJson(s, BankSearchBean.class);
                String bank = bankSearchBean.getBank();
                if (null == bank)return;
                for (int i = 0; i < mBankCodeArrayList.size(); i++) {
                    if (bank.equals(mBankCodeArrayList.get(i).getBankCode())) {
                        tv_show_bank_name.setText(mBankCodeArrayList.get(i).getBankName());
                        break;
                    } else {
                        tv_show_bank_name.setText("--");
                    }
                }
                if (null != bankSearchBean.getBank()) {
                    Picasso.with(BankSearchActivity.this).load(imgUrl + bankSearchBean.getBank()).into(iv_show_bank_img);
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
            }
        });
    }
}
