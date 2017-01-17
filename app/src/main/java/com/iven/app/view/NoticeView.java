package com.iven.app.view;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.iven.app.R;
import com.iven.app.bean.Notice;
import com.iven.app.tools.SizeUtils;
import com.iven.app.tools.SpannableStrings;

import java.util.ArrayList;

/**
 * @author Iven
 * @date 2017/1/17 12:36
 * @Description
 */

public class NoticeView extends ViewFlipper implements View.OnClickListener {
    private Context mContext;
    private ArrayList<Notice> mArrayList;
    private OnNoticeClickListener mListener;

    public NoticeView(Context context) {
        super(context);
    }

    public NoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(mContext);
    }

    private void init(Context context) {
        mArrayList = new ArrayList<>();
        setFlipInterval(3000);//设置滚动间隔时间
        setPadding(SizeUtils.dp2px(context, 5f), SizeUtils.dp2px(context, 5f), SizeUtils.dp2px(context, 5f), SizeUtils.dp2px(context, 5f));
        setInAnimation(AnimationUtils.loadAnimation(context, R.anim.notice_in));
        setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.notice_out));
    }

    public void addNotice(ArrayList<Notice> notices) {
        mArrayList = notices;
        removeAllViews();
        for (int i = 0; i < mArrayList.size(); i++) {
            Notice notice = notices.get(i);
            int titleLength = notice.getTitle().length();//取出来标题的文案的长度
            int contentLength = notice.getContent().length();//取出来内容的文案的长度
            String string = notice.getTitle() + " " + notice.getContent();
            int color = 0;
            if (TextUtils.isEmpty(notice.getTitleColor())) {
                color = Color.RED;
            } else {
                color = Color.parseColor(notice.getTitleColor());
            }
            SpannableString spannableString = SpannableStrings.setTextColor(string, color, 0, titleLength);
            TextView textView = new TextView(mContext);
            textView.setSingleLine(true);
            textView.setText(spannableString);
            textView.setTextSize(20f);//字体大小
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTag(i);
            textView.setOnClickListener(this);
            NoticeView.this.addView(textView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        Notice notice = mArrayList.get(position);
        if (mListener != null) {
            mListener.onNotieClick(position, notice);
        }
    }

    /**
     * 接口回调
     */
    public interface OnNoticeClickListener {
        void onNotieClick(int position, Notice notice);
    }

    public void setListener(OnNoticeClickListener listener) {
        mListener = listener;
    }

}
