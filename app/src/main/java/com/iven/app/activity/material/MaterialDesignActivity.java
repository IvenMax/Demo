package com.iven.app.activity.material;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.fragment.ColumnFragment;
import com.iven.app.fragment.FirstFragment;
import com.iven.app.fragment.SecondFragment;

import java.util.ArrayList;

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
    private TabLayout mTabLayout;
    private ArrayList<Fragment> mFragmentArrayList;

    /**
     * Fragment
     */
    private FragmentManager manager;
    private ColumnFragment mColumnFragment;
    private SecondFragment mSecondFragment;
    private FirstFragment mFirstFragment;
    private int currentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        manager = getFragmentManager();
        init();
    }

    private void init() {
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        navigation_view.setItemIconTintList(null);//让menu里边的图片显示原始颜色
        mDrawerLayout = (DrawerLayout) findViewById(R.id.md_drawerlayout);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mFragmentArrayList = new ArrayList<>();
        mColumnFragment = new ColumnFragment();
        mSecondFragment = new SecondFragment();
        mFirstFragment = new FirstFragment();
        mFragmentArrayList.add(mColumnFragment);
        mFragmentArrayList.add(mSecondFragment);
        mFragmentArrayList.add(mFirstFragment);


        headerClick();
        itemClick();
        initActionBar();
        initTab();

    }

    private void initTab() {
        mTabLayout.removeAllTabs();
        mTabLayout.addTab(mTabLayout.newTab().setText("柱状图"));
        mTabLayout.addTab(mTabLayout.newTab().setText("002"));
        mTabLayout.addTab(mTabLayout.newTab().setText("003"));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        Fragment fragment = mFragmentArrayList.get(currentPage);
                        switchContent(fragment, mFragmentArrayList.get(0), "first");
                        currentPage = 0;
                        break;
                    case 1:
                        Fragment fragment1 = mFragmentArrayList.get(currentPage);
                        switchContent(fragment1, mFragmentArrayList.get(1), "second");
                        currentPage = 1;
                        break;
                    case 2:
                        Fragment fragment2 = mFragmentArrayList.get(currentPage);
                        switchContent(fragment2, mFragmentArrayList.get(2), "third");
                        currentPage = 2;
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                switch (item.getItemId()) {
                    case R.id.menu_column:
                        mTabLayout.getTabAt(0).select();
                        break;
                    case R.id.wallet:
                        mTabLayout.getTabAt(1).select();
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawer(Gravity.LEFT);
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

    /**
     * Fragmen切换
     *
     * @param from 切换前所显示的
     * @param to   切换的目标
     * @param tag  tag
     */
    public void switchContent(Fragment from, Fragment to, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (null == from || !from.isAdded()) {
            transaction.add(R.id.md_container, to, tag).commitAllowingStateLoss();
            return;
        }
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.md_container, to, tag).commit();
        } else {
            transaction.hide(from).show(to).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        switchContent(null, mFragmentArrayList.get(0), "first");
    }
}
