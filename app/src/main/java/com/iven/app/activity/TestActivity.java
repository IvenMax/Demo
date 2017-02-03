package com.iven.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.iven.app.R;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "zpy_TestActivity";
    private int count = 0;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        mContext = this;
    }

    //Button的点击事件
    public void loadData(View view) {
        count++;
//                T.showShort(this,"count = "+count);
        Context applicationContext = this.getApplicationContext();

        Toast.makeText(applicationContext, "c = " + count, Toast.LENGTH_SHORT).show();
        //        boolean destroyed = false;
        //        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
        //            destroyed = this.isDestroyed();
        //        }
        //        boolean finishing = this.isFinishing();
        //        T.showShort(this, destroyed + "-");
    }
}
