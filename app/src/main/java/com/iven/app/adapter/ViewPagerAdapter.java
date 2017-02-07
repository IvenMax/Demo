package com.iven.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> list;

    public ViewPagerAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() > 1 ? 1000 : list.size();//实现无限轮播的关键返回数据改变
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.height = ViewPager.LayoutParams.MATCH_PARENT;
        params.width = ViewPager.LayoutParams.WRAP_CONTENT;
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        if (list.size() > 0) {
            position = position % list.size();//%计算
        }
        //加载图片
        Picasso.with(context).load(list.get(position)).into(imageView);
        //设置ImageView的监听回调事件处理
        imageView.setOnClickListener(new ImgOnClickListener(position));
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }

    /**
     * 自定义ImageView的点击事件
     */
    class ImgOnClickListener implements View.OnClickListener {
        private int position;

        public ImgOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "点击的索引 = " + position, Toast.LENGTH_SHORT).show();
        }
    }
}