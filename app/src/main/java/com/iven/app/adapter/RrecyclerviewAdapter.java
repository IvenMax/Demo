package com.iven.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.bean.MovieBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author Iven
 * @date 2017/2/3 9:58
 * @Description
 */

public class RrecyclerviewAdapter extends RecyclerView.Adapter {
    private List<MovieBean.DataBean.ComingBean> mComingBeanList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public RrecyclerviewAdapter(List<MovieBean.DataBean.ComingBean> comingBeanList, Context context) {
        mComingBeanList = comingBeanList;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.layout_item_recyclerview, null);
        MyViewHolder viewHolder = new MyViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.setData(position);
    }

    @Override
    public int getItemCount() {
        return null == mComingBeanList ? 0 : mComingBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_mv_name;
        private TextView tv_mv_dec;
        private TextView tv_mv_date;
        private ImageView iv_mv_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_mv_name = (TextView) itemView.findViewById(R.id.tv_mv_name);
            tv_mv_dec = (TextView) itemView.findViewById(R.id.tv_mv_dec);
            tv_mv_date = (TextView) itemView.findViewById(R.id.tv_mv_date);
            iv_mv_image = (ImageView) itemView.findViewById(R.id.iv_mv_image);
        }

        public void setData(int index) {
            MovieBean.DataBean.ComingBean comingBean = mComingBeanList.get(index);
            tv_mv_name.setText(comingBean.getNm());
            tv_mv_dec.setText(comingBean.getScm());
            tv_mv_date.setText(comingBean.getShowInfo());
            String imagUrl = comingBean.getImg();
            String newImagUrl = imagUrl.replaceAll("w.h", "50.80");
            Picasso.with(mContext).load(newImagUrl).into(iv_mv_image);
        }
    }
}
