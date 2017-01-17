package com.iven.app.tools;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;

/**
 * @author Iven
 * @date 2017/1/17 9:15
 * @Description SpannableString相关的工具类(TextView的富文本显示)
 */

public class SpannableStrings {
    /**
     * 首先讲解这四个常量的不同(懒人做法~CV)
     */
    /**
     * Non-0-length spans of type SPAN_INCLUSIVE_EXCLUSIVE expand
     * to include text inserted at their starting point but not at their
     * ending point.  When 0-length, they behave like marks.
     */
    //public static final int SPAN_INCLUSIVE_EXCLUSIVE = SPAN_MARK_MARK;

    /**
     * Spans of type SPAN_INCLUSIVE_INCLUSIVE expand
     * to include text inserted at either their starting or ending point.
     */
    //public static final int SPAN_INCLUSIVE_INCLUSIVE = SPAN_MARK_POINT;

    /**
     * Spans of type SPAN_EXCLUSIVE_EXCLUSIVE do not expand
     * to include text inserted at either their starting or ending point.
     * They can never have a length of 0 and are automatically removed
     * from the buffer if all the text they cover is removed.
     */
    //public static final int SPAN_EXCLUSIVE_EXCLUSIVE = SPAN_POINT_MARK;

    /**
     * Non-0-length spans of type SPAN_EXCLUSIVE_INCLUSIVE expand
     * to include text inserted at their ending point but not at their
     * starting point.  When 0-length, they behave like points.
     */
    //public static final int SPAN_EXCLUSIVE_INCLUSIVE = SPAN_POINT_POINT;
    /**
     * 这四个值分别表示
     1.前面包括，后面不包括，即在文本前插入新的文本会应用该样式，而在文本后插入新文本不会应用该样式
     2.前面包括，后面包括，即在文本前插入新的文本会应用该样式，而在文本后插入新文本也会应用该样式
     3.前面不包括，后面不包括
     4.前面不包括，后面包括
     */
    /**--------------------------------------------------------------------------------------------**/
    /**
     * 设置部分文本的背景色
     *
     * @param text            文本
     * @param backgroundColor 背景色
     * @param start           开始位置
     * @param end             结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextBackground(String text, int backgroundColor, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new BackgroundColorSpan(backgroundColor), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本的字体颜色
     *
     * @param text      文本
     * @param textColor 文本颜色
     * @param start     开始位置
     * @param end       结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextColor(String text, int textColor, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(textColor), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本的字体大小(绝对值，设置的多大就显示多大的字体Size)
     *
     * @param text     文本
     * @param textSize 字体大小
     * @param start    开始位置
     * @param end      结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextSize(String text, int textSize, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new AbsoluteSizeSpan(textSize), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本的字体大小(相对值，相对于默认字体大小的倍数)
     *
     * @param text     文本
     * @param multiple 相对于默认文本大小的倍数
     * @param start    开始位置
     * @param end      结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextRelativeSize(String text, float multiple, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new RelativeSizeSpan(multiple), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本粗体显示
     *
     * @param text  文本
     * @param start 开始位置
     * @param end   结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextBold(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本斜体显示
     *
     * @param text  文本
     * @param start 开始位置
     * @param end   结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextItalic(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本粗&斜体显示
     *
     * @param text  文本
     * @param start 开始位置
     * @param end   结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextBoldItalic(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本下划线
     *
     * @param text  文本
     * @param start 开始位置
     * @param end   结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextUnderline(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本删除线
     *
     * @param text  文本
     * @param start 开始位置
     * @param end   结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextDeleteline(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本以下标形式显示
     *
     * @param text  文本
     * @param start 开始位置
     * @param end   结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextSubscript(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new SubscriptSpan(), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 设置部分文本以上标形式显示
     *
     * @param text  文本
     * @param start 开始位置
     * @param end   结束位置(不包含)
     * @return SpannableString
     */
    public static SpannableString setTextSuperscript(String text, int start, int end) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new SuperscriptSpan(), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    /**
     * 改变部分文字的颜色
     *
     * @param text       改变颜色的文本
     * @param finalColor 改变后的颜色
     * @param textsize   文字大小 如果isSetSize为false，可不设置
     * @param startIndex 起始位置
     * @param endIndex   结束位置
     * @param isSetSize  true 根据startIndex和endIndex设置字体大小  false则不设置
     * @return spannableString
     */
    public static SpannableString setTextColorAndSize(String text, int finalColor, int textsize, int startIndex, int endIndex, boolean isSetSize) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ForegroundColorSpan(finalColor), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        if (isSetSize) {
            spannableString.setSpan(new AbsoluteSizeSpan(textsize, true), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }
    // TODO: 2016/3/17  设置   字体？
    // TODO: 2016/3/17  设置   部分文本的  点击事件？
}
