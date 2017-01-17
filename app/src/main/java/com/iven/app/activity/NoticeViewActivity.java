package com.iven.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.bean.Notice;
import com.iven.app.tools.SpannableStrings;
import com.iven.app.tools.T;
import com.iven.app.view.NoticeView;

import java.util.ArrayList;

/**
 * 模仿 京东 首页 垂直滚动新闻
 */
public class NoticeViewActivity extends AppCompatActivity {

    private NoticeView notice_view;
    private TextView tv_jd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_view);
        init();
    }

    private void init() {
        tv_jd = (TextView) findViewById(R.id.tv_jd);
        SpannableString spannableString = SpannableStrings.setTextBackgroundAndTextColor("京东快报：", Color.RED, Color.WHITE, new int[]{2, 4}, new int[]{2, 4});
        tv_jd.setText(spannableString);
        notice_view = (NoticeView) findViewById(R.id.notice_view);
        ArrayList<Notice> list = new ArrayList<>();
        Notice notice = new Notice("大促", "新年好货马上抢", "#ff0000", null, "www.baidu.com");
        Notice notice2 = new Notice("爆", "9.9元好东西啊~~~", "#0000ff", null, "www.google.com");
        Notice notice3 = new Notice("疯子", "疯子一样的抢购，来吧", "#00ff00", null, "www.qq.com");
        list.add(notice);
        list.add(notice2);
        list.add(notice3);
        notice_view.addNotice(list);
        notice_view.setListener(new NoticeView.OnNoticeClickListener() {
            @Override
            public void onNotieClick(int position, Notice notice) {
                T.showShort(NoticeViewActivity.this, "url = " + notice.getUrl());
            }
        });
        notice_view.startFlipping();
    }
}
