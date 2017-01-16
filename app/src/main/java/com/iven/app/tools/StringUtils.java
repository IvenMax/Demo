package com.iven.app.tools;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.iven.app.R;

/**
 * @author Iven
 * @date 2017/1/16 10:09
 * @Description
 */

public class StringUtils {

    /**
     * 提取 一个字符串 中的 数字,
     *
     * @param input
     * @return
     */
    public static Double getNumber(String input) {
        if (TextUtils.isEmpty(input)) {
            return 0D;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if ((c >= '0' && c <= '9') || c == '.' || c == '-') {
                sb.append(c);
            }
        }

        String numberStr = sb.toString();

        if (TextUtils.isEmpty(numberStr)) {
            return 0D;
        }

        try {
            return Double.valueOf(numberStr);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return 0D;
    }

    /**
     * 判断字体显示的颜色   完成期列表字体颜色
     *
     * @param textView
     * @param content
     * @param context
     */
    public static void getComppleteTextColor(TextView textView, String content, Context context) {
        if (content == null || content.equals("0.00") || content == "") {
            if (content == "") {
                content = "0.00";
            }
            textView.setTextColor(context.getResources().getColor(R.color.color_f80d0d));
            textView.setText(content);

        } else {

            if (getNumber(content) < 0) {
                textView.setTextColor(context.getResources().getColor(R.color.color_048e63));
                textView.setText(content);
            } else {
                textView.setTextColor(context.getResources().getColor(R.color.color_f80d0d));
                textView.setText("+" + content);
            }
        }
    }
}
