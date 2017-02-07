package com.iven.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.iven.app.R;
import com.iven.app.adapter.ViewPagerAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 使用ViewPager实现Gallery效果
 */
public class GalleryViewPagerActivity extends AppCompatActivity {
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 6://banner图
                    if (viewPager.getCurrentItem() == adapter.getCount()) {
                        viewPager.setCurrentItem(400, false);
                    } else {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                    this.sendEmptyMessageDelayed(6, 2000);
                    break;
            }
        }
    };
    private ViewPager viewPager;
    private LinearLayout ll_container;
    private ArrayList<String> datas;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view_pager);
        init();
    }

    private void init() {
        adapter = new ViewPagerAdapter(this, datas);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        /**
         * 随便添加了几个图片的链接，用Picasso实现图片的加载展示
         */
        datas = new ArrayList<>();
        datas.add("http://pic1.zhimg.com/4766e0648_m.jpg");
        datas.add("http://pic1.zhimg.com/bd751e76463e94aa10c7ed2529738314_m.jpg");
        datas.add("http://pic4.zhimg.com/e6637a38d22475432c76e6c9e46336fb_m.jpg");
        datas.add("http://pic4.zhimg.com/6a1ddebda9e8899811c4c169b92c35b3.jpg");
        //设置适配器
        adapter = new ViewPagerAdapter(this, datas);
        //设置两个图片之间的间距
        viewPager.setPageMargin(30);
        //设置滚动
        setViewPagerScroller();
        viewPager.setAdapter(adapter);
        //设置ViewPager默认显示中间的
        if (datas.size() > 1) {
            viewPager.setCurrentItem(500);
            mHandler.removeMessages(6);
            mHandler.sendEmptyMessage(6);
        }
        //处理父容器的事件分发
        ll_container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);//交由ViewPager来处理事件
            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        if (datas.size() > 1) {//滑动时停止轮播
                            mHandler.removeMessages(6);
                        }
                        break;
                    case MotionEvent.ACTION_UP://手指抬起时，开始轮播
                        if (datas.size() > 1) {
                            mHandler.removeMessages(6);
                            mHandler.sendEmptyMessageDelayed(6, 2000);
                        }
                        break;
                }
                return false;
            }
        });
        /**
         * 一定要重写setOnPageChangeListener方法，实现父容器的不断刷新绘制，不然显示不了
         *
         */
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                ll_container.invalidate();//不断重新绘制刷新父view
            }
        });

    }

    /**
     * 设置viewpager的切换时间
     */
    private void setViewPagerScroller() {
        ViewPagerScoller viewPagerScoller = new ViewPagerScoller(this, new DecelerateInterpolator());
        viewPagerScoller.setDuration(1000);//设置移动时间
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(viewPager, viewPagerScoller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义滚动时间
     */
    class ViewPagerScoller extends Scroller {
        private int mDuration = 500;

        public ViewPagerScoller(Context context) {
            super(context);
        }

        public ViewPagerScoller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScoller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, this.mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        /**
         * 设置viewpager切换的动画时间
         */
        public void setDuration(int duration) {
            this.mDuration = duration;
        }
    }
}
