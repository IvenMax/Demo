package com.iven.app.activity.material;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iven.app.R;

/**
 * Material Design
 */
public class MaterialDesignActivity extends AppCompatActivity {
    private static final String TAG = "zpy_MaterialDesignActivity";
    private NavigationView navigation_view;
    private TextView tv_security_name;
    private ImageView iv_navigationview_img;
    private boolean isZhongXin = true;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String globalTag="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_material_design);
        init();
    }

    private void init() {
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        navigation_view.setItemIconTintList(null);//让menu里边的图片显示原始颜色
        mDrawerLayout = (DrawerLayout) findViewById(R.id.md_drawerlayout);
        headerClick();
        itemClick();
        initActionBar();

    }

    private void initActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle("Mark");
        mActionBar.setDisplayHomeAsUpEnabled(true);//home键可点击
        mActionBar.setDisplayShowHomeEnabled(true);//显示home键
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mActionBarDrawerToggle.syncState();//同步DrawerLayout
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    /**
     * Item点击事件处理
     */
    private void itemClick() {
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MaterialDesignActivity.this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
//                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    /**
     * 处理头布局的点击事件
     */
    private void headerClick() {
        View view = navigation_view.getHeaderView(0);//获得头布局getHeaderView()
        iv_navigationview_img = (ImageView) view.findViewById(R.id.iv_navigationview_img);
        tv_security_name = (TextView) view.findViewById(R.id.tv_security_name);
        iv_navigationview_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isZhongXin) {
                    iv_navigationview_img.setImageResource(R.mipmap.security_no);
                    tv_security_name.setText("改变自己");
                    isZhongXin = false;
                } else {
                    iv_navigationview_img.setImageResource(R.mipmap.security_zhongxin);
                    tv_security_name.setText("中信证券");
                    isZhongXin = true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            mActionBarDrawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}
