package com.iven.app.tools;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.iven.app.R;
import com.iven.app.bean.ColumnBean;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

    /**
     * 两位小数
     * @param value
     * @return
     */
    private static double getDecimal(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return Double.parseDouble(decimalFormat.format(value));
    }
    /**
     * 根据中间值获取如果没有数据返回-1
     *
     * @param list     数据集合
     * @param midValue 中间值
     */
    public static int getMaxIndex(ArrayList<ColumnBean> list, Context context, double midValue) {
        if (list == null || list.size() == 0) {
            return -1;
        }
        int size = list.size();
        double indexValue = Math.abs(list.get(0).getNetValue() - midValue);
        int index = 0;

        for (int i = 1; i < size; i++) {
            double tempValue = Math.abs(list.get(i).getNetValue() - midValue);
            if (tempValue > indexValue) {
                indexValue = tempValue;
                index = i;
            }
        }
        return index;
    }
    public static ArrayList<ColumnBean> getData() {
        ArrayList<ColumnBean> list = new ArrayList<ColumnBean>();
        for (int i = 0; i < 31; i++) {
            ColumnBean columnBean = new ColumnBean();
            double value = (10000 - ((Math.random() * 20000)));
            value = getDecimal(value);
            columnBean.setValue(value);
            if (i + 1 < 10) {
                columnBean.setDate("2016-11-0" + (i + 1));
            } else {
                columnBean.setDate("2016-11-" + (i + 1));
            }
            list.add(columnBean);
        }
        return list;
    }
}
