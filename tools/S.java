package com.hbyc.wxn.commontools;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2014/8/29
 *
 * @NewY. W
 */
public class S {

    // 邮箱的匹配
    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    // 2种日期的匹配
    private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 将日期格式的字符串转化成Date对象
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater2.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * 获取当天日期
     *
     * @param dateformat
     * @return
     */
    public static String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    /**
     * 获取上一年日期
     */
    public static String getLastYear() {
        String[] date_Year = getNowTime("yyyy-MM-dd").split("-");
        String lastYear = Integer.parseInt(date_Year[0]) - 1 + "-" + date_Year[1] + "-01";
        return lastYear;
    }

    /**
     * 判断一个字符串是否只含有数字、字母、下划线、@和点，且长度不能小于6位
     *
     * @param input
     * @return
     */
    public static boolean isNormalStr(String input) {
        if (isEmpty(input)) {
            return false;
        }
        // -/:;()$&@".,?!'[]{}#%^*+=_\|~<>€£¥•.,?!'
        String regx = "^[a-zA-Z0-9_@\\.]{6,}$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(input).matches();
    }

    public static boolean isRegistName(String input) {

        return false;
    }

    /**
     * 判断字符串是否是null, 或者是"", 或者是包含空字符的字符串
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否是null, 或者是"", 或者是包含空字符的字符串
     */
    public static boolean isEmpty(Object object) {
        if (null == object) {
            return true;
        }
        String input = object.toString();
        if (input == null || "".equals(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否是匹配 email格式
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        return emailer.matcher(email).matches();
    }

    /**
     * 校验一个字符串是否匹配 手机号码的格式
     */
    public static boolean isMobile(String mobile) {
        String regex = "^1(3[0-9]|5[0-9]|8[0-9])\\d{8}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobile);
        return m.find();
    }

    // public static boolean checkPwd(String password){
    // String regex = "^$"
    // }

    /**
     * 将一个手机号的中间4位隐藏
     *
     * @param phoneNum
     * @return 如果不是手机号, 直接返回原有的字符串
     */
    public static String hiddenMobile(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            return "";
        }
        if (isMobileNumber(phoneNum)) {
            char[] chars = phoneNum.toCharArray();
            for (int i = 3; i <= 6; i++) {
                chars[i] = '*';
            }
            return new String(chars);
        }
        return phoneNum;
    }

    public static boolean isMobileNumber(String mobiles) {
        if (S.isEmpty(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 如果content的字符长度超过length, 就保留length长度的内容,后面的内容删掉,用 "..."替代
     *
     * @param content
     * @param length
     * @return
     */
    public static String endStringWithEllip(String content, int length) {
        if (content.length() > length) {
            StringBuilder sb = new StringBuilder(content.substring(0, length));
            sb.append("...");
            return sb.toString();
        }
        return content;
    }

    /**
     * @param value 一个可以按照split分割的字符串
     * @param split
     * @return List<String> 分割之后的字符串集合
     */
    public static List<String> split2List(String value, String split) {
        if (value != null && value.length() > 0) {
            List<String> retVal = new ArrayList<String>();
            String[] strArr = value.split(split);
            if (strArr.length > 1) {
                for (String str : strArr) {
                    retVal.add(str);
                }
            } else {
                retVal.add(value);
            }
            return retVal;
        }
        return null;
    }

    /**
     * @param strs  需要被整合成一个字符串的字符串集合
     * @param split 各个字符串中间用split作为分隔符
     * @return 得到一个按照spit间隔的字符串
     */
    public static String integrate2Str(List<String> strs, String split) {
        if (strs != null && strs.size() > 0) {
            StringBuilder retVal = new StringBuilder();
            for (String str : strs) {
                retVal.append(split + str);
            }
            return retVal.substring(1).toString();
        }
        return null;
    }

    /**
     * 全角转半角
     *
     * @param input
     * @return
     */
    // 半角空格32,全角空格12288
    // 其他字符半角33~126,其他字符全角65281~65374,相差65248
    public static String SBCToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    // 半角空格32,全角空格12288
    // 其他字符半角33~126,其他字符全角65281~65374,相差65248
    public static String DBCToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] > 33 && c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 除去空格，忽略全角半角符号,忽略大小写比较
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isEqual(String a, String b) {
        String na = SBCToDBC(a).replaceAll(" ", "");
        String nb = SBCToDBC(b).replaceAll(" ", "");
        return (na == nb || na.equalsIgnoreCase(nb));
    }

    /**
     * 校验身份证
     *
     * @param str
     * @return
     */
    public static boolean isIdentity(String str) {
        if (S.isEmpty(str)) {
            return false;
        }
        String regx = "([0-9]{17}([0-9]|X))|([0-9]{15})";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    /**
     * 校验密码, 长度大于六, 数字 和字母的组合
     *
     * @param str
     * @return
     */
    public static boolean isPwd(String str) {
        if (S.isEmpty(str)) {
            return false;
        }

        String regx = "^[0-9a-zA-Z]{6,}$";

        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    /**
     * 判断输入的密码是不是6到18位的任意字符
     *
     * @param pwd
     * @return
     */
    public static boolean isPwd2(String pwd) {
        if (S.isEmpty(pwd)) {
            return false;
        }
        String regx = "^\\S{6,18}$";
        // String regx = "[0-9a-zA-Z@~:\\-\\*^%&',;=?$\\.\\x22]{6,18}";
        Pattern pattern = Pattern.compile(regx);
        boolean isStr = pattern.matcher(pwd).matches();
        return isStr;
    }

    /**
     * 验证验证输入汉字
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsChinese(String str) {
        if (S.isEmpty(str)) {
            return false;
        }
        String regex = "^[\u4e00-\u9fa5],{0,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    public static void clearStringBuilder(StringBuilder sb) {
        if (sb != null) {
            sb.delete(0, sb.length());
        }
    }

    public static boolean isUrl(String input) {
        if (isEmpty(input)) {
            return false;
        }
        // string pattern = @"http://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?";
        String regex = "^http://([\\w-]+\\.)+[\\w-]+(/[\\w./?%&=]*)?$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input).matches();
    }

    /**
     * 提取 一个字符串 中的 数字,
     *
     * @param input
     * @return
     */
    public static Double getNumber(String input) {
        if (S.isEmpty(input)) {
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

        if (S.isEmpty(numberStr)) {
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
     * 提取 一个字符串 中的 数字 转为Int,
     *
     * @param input
     * @return
     */
    public static int getNumber2Int(String input) {
        if (S.isEmpty(input)) {
            return 0;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if ((c >= '0' && c <= '9') || c == '.' || c == '-') {
                sb.append(c);
            }
        }

        String numberStr = sb.toString();

        if (S.isEmpty(numberStr)) {
            return 0;
        }

        try {
            return Integer.valueOf(numberStr);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 提取 一个字符串 中的 数字,
     *
     * @param input
     * @return
     */
    public static int getVersionNum(String input) {
        if (S.isEmpty(input)) {
            return 0;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if ((c >= '0' && c <= '9') || c == '.' || c == '-') {
                if (c != '.') {
                    sb.append(c);
                }
            }
        }

        String numberStr = sb.toString();

        if (S.isEmpty(numberStr)) {
            return 0;
        }

        try {
            return Integer.valueOf(numberStr);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * 提取 一个字符串 中的 数字,只提取整数
     *
     * @param input
     * @return
     */
    public static Integer getInteger(String input) {
        Double tmp = getNumber(input);
        if (tmp != null) {
            return tmp.intValue();
        }
        return null;
    }

    /**
     * 判断给定的字符串是否是表示16进制的字符串
     *
     * @param content
     * @return
     */
    public static boolean isHexNumber(String content) {
        if (S.isEmpty(content)) {
            return false;
        }
        int length = content.length();
        if (length > 50) {
            content = content.substring(0, 30);
        }
        length = content.length();
        String regex = "^[0-9a-fA-F]{" + length + "}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(content).matches();
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 判断输入的密码是否太过简单 4中类型 1, 小写字母 2, 大写字母 3,数字 4,其他字符 密码至少是2中类型的组合
     *
     * @param pwd
     * @return true:小于2种类型的组合; false:2种及其以上的组合
     */
    public static boolean isPwdSimple(String pwd) {
        int type_a = 0;
        int type_A = 0;
        int type_0 = 0;
        int type__ = 0;

        for (int i = 0; i < pwd.length(); i++) {
            char tmp = pwd.charAt(i);
            if (tmp >= 'a' && tmp <= 'z') {
                type_a = 1;
            } else if (tmp >= 'A' && tmp <= 'Z') {
                type_A = 1;
            } else if (tmp >= '0' && tmp <= '9') {
                type_0 = 1;
            } else {
                type__ = 1;
            }
        }
        int result = type_a + type_A + type_0 + type__;
        return result < 2;
    }

    /**
     * @param downedFileLength2 字节数
     * @return 用 "00b"或者"0.00kb"或者"0.00mb"表示的字节大小
     */
    public static String getFileSize(long downedFileLength2) {
        double size;
        if (downedFileLength2 < 1024 && downedFileLength2 >= 0) { // 小于1k
            return downedFileLength2 + "b";
        } else if (downedFileLength2 < 1024 * 1024) {// 小于1M
            size = 1.0 * downedFileLength2 / 1024;
            return String.format(".2f", size) + "k";
        } else if (downedFileLength2 < 1024 * 1024 * 1024) {// 小于1G
            size = 1.0 * downedFileLength2 / (1024 * 1024);
            return String.format(".2f", size) + "m";
        }
        return "0b";
    }

    /**
     * 处理文字格式为 1,000,000.00
     */

    public static String formatNumber(Object object) {
        String str = object.toString();
        NumberFormat nf = new DecimalFormat("#,##0.####");
        return nf.format(getNumber(str));
    }

    /**
     * 改格式为货币格式 如“ 100,000,000”
     *
     * @param object
     * @param decimalNum 小数点个数，如2表示保留小数点后2位小数
     * @return
     */
    public static String formatNumberWithoutDecimal(Object object, int decimalNum) {
        if (null == object) {
            StringBuffer sb = null;
            sb.append("0.");
            for (int i = 0; i < decimalNum; i++) {
                sb.append("0");
            }
            return sb.toString();
        }
        String str = object.toString();

        if (str != "" && str != " " && str != null &&
                !str.equals("0") && !str.equals("0.0") && !str.equals("0.00")) {
            StringBuffer appendStr = null;
            if (decimalNum > 0) {
                appendStr = new StringBuffer();
                appendStr.append(".");
                for (int i = 0; i < decimalNum; i++) {
                    appendStr.append("0");
                }
            }
            NumberFormat nf = new DecimalFormat("#,##0" + (null == appendStr ? "" : appendStr.toString()));
            BigDecimal bg = new BigDecimal(str);
            return nf.format(bg);
        } else {
            return "0.00";
        }
    }

    /**
     * 处理时间，把 2015-08-15转为2015.08.15格式
     */
    public static String formatDate(String str) {
        if (null != str)
            return str.replace("-", ".");
        return null;
    }


    /**
     * 处理时间，把 2015-08-15转为 08-15格式
     */
    public static String formatDateSplit(String str) {
        if (!S.isEmpty(str)) {
            if (str.contains(" ")) {
                str = str.split(" ")[0];
            }
            String date[] = str.split("-");
            if (date.length >= 3) {
                if (date[2].contains(" ")) {
                    String[] newDates = date[2].split(" ");
                    date[2] = newDates[0];
                }
            }
            return date[1] + "-" + date[2];
        }
        return null;
    }

    /**
     * 格式化日期  把 2015-08-15 12:00:00转为 08-15 12:00格式
     *
     * @param date 原有日期 格式必须为2015-08-15 12:00:00 否侧会出现异常
     * @return
     */
    public static String formatDate_MM_dd_HH_mm(String date) {
        if(S.isEmpty(date))return "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = dateFormat.parse(date);
            dateFormat = new SimpleDateFormat("MM-dd HH:mm");
            return date = dateFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化日期
     *
     * @param date        时间
     * @param originRegex 格式化前的格式
     * @param finalRegex  格式化后的格式
     * @return
     */
    public static String formatDate(String date, String originRegex, String finalRegex) {
        if (TextUtils.isEmpty(date)) {
            throw new IllegalArgumentException("日期不能为空");
        } else if (TextUtils.isEmpty(originRegex) || TextUtils.isEmpty(finalRegex)) {
            throw new IllegalArgumentException("日期格式不能为空originRegex或者finalRegex不能为空");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(originRegex);
        try {
            Date parse = dateFormat.parse(date);
            dateFormat = new SimpleDateFormat(finalRegex);
            return dateFormat.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 保留两位小数
     *
     * @param obj
     * @return
     */
    public static String get2Decimal(Object obj) {
        if (null == obj) {
            return "0.00";
        }
        String string = obj.toString();
        if (S.isEmpty(string)) {
            return "0.00";
        } else {

            NumberFormat nf = new DecimalFormat("#,##0.00");
            Double d = getNumber(string);
            if (d == 0) {
                return "0.00";
            }
            return nf.format(d);
        }
    }

    /**
     * 保留三位小数
     *
     * @param string
     * @return
     */
    public static String get3Decimal(String string) {

        if (string == "" || string == null) {
            return "0.00";
        } else {

            Double d = S.getNumber(string);
            return String.format("%.3f", d);

        }
    }

    /**
     * 改变部分文字的颜色
     *
     * @param context
     * @param text       改变颜色的文本
     * @param finalColor 改变后的颜色
     * @param startIndex 起始位置
     * @param endIndex   结束位置
     * @return
     */
    public static SpannableString setTextColor(Context context, String text, int finalColor, int startIndex, int endIndex) {
        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new ForegroundColorSpan(finalColor), startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spanString;
    }


    /**
     * 四舍五入
     *
     * @param object
     * @return int
     */
    public static int getIntValue(Object object) {
        String str = object.toString();
        try {
            Double d = Double.valueOf(str);

            if (d < 0) {

                double result = Math.abs(d);
                return -(int) Math.round(result);
            }
            return (int) Math.round(d);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @param str
     * @param decimalNum 小数点个数，如2表示保留小数点后2位小数
     * @return
     */
    public static String formatNumberWithDecimal(String str, int decimalNum) {

        if (str != "" && !str.equals("0") && !str.equals("0.0")) {
            StringBuffer appendStr = null;
            if (decimalNum > 0) {
                appendStr = new StringBuffer();
                appendStr.append(".");
                for (int i = 0; i < decimalNum; i++) {
                    appendStr.append("0");
                }
            }
            NumberFormat nf = new DecimalFormat("0" + (null == appendStr ? "" : appendStr.toString()));
            nf.setRoundingMode(RoundingMode.HALF_UP);
            return nf.format(getNumber(str));
        } else {
            return "0.000";
        }
    }

    /**
     * 把小数转化为百分比
     *
     * @param obj
     * @param decimalNum 小数点后位数
     * @return
     */
    public static String parseDecimal2Percent(Object obj, int decimalNum) {
        if (null == obj) {
            StringBuffer sb = null;
            sb.append("0.");
            for (int i = 0; i < decimalNum; i++) {
                sb.append("0");
            }
            sb.append("%");
            return sb.toString();
        }
        double value = Double.parseDouble(obj.toString());
        NumberFormat num = NumberFormat.getPercentInstance();
        num.setMaximumFractionDigits(decimalNum);
        num.setMinimumFractionDigits(decimalNum);

        return num.format(value);

    }

    /**
     * 输入两个字符串日期2016-03格式，比较两个日期
     *
     * @param beginDate
     * @param endDate
     * @return true 前面的日期小于后面日期。
     */
    public static boolean compareString(String beginDate, String endDate) {
        try {
            String[] bStrings = beginDate.split("-");
            String[] eStrings = endDate.split("-");
            int bYear = Integer.parseInt(bStrings[0]);
            int eYear = Integer.parseInt(eStrings[0]);
            int bMonth = Integer.parseInt(bStrings[1]);
            int eMonth = Integer.parseInt(eStrings[1]);
            if (bYear > eYear) {
                return false;
            } else if (bYear == eYear) {
                if (bMonth > eMonth) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断当前字符串是否在ascll码表中
     *
     * @param string 要判断的字符串
     * @return 返回空说明包括非ascll码表以外的字符
     */
    public static String string2Unicode(String string) {
        StringBuffer unicode = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            if (!(c >= 0 && c <= 127)) {
                return "";
            } else {
                unicode.append(c);
            }
        }
        return unicode.toString();
    }

    /**
     * Unicode转码
     *
     * @param url
     * @return
     */
    public static String unicode2utf(String url) {
        String str = "";
        try {
            if (url != null) {
                byte[] bytes = url.getBytes("utf-8");
                str = new String(bytes, "utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean hasSmartBar() {
        try {
            // 新型号可用反射调用  Build.hasSmartBar()
            Method method = Class.forName("android.os.Build").getMethod("hasSmartBar");
            return ((Boolean) method.invoke(null)).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getPreviousDate(String receiveDate) {

        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(receiveDate));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date date = calendar.getTime();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 券商账号后4位前面用*替换
     *
     * @param brokerNum
     * @return
     */
    public static String replaceBrokerNum(String brokerNum) {
        String replaceNum = "";
        if (!TextUtils.isEmpty(brokerNum)) {
            if (brokerNum.length() > 4) {
                String temp = brokerNum.substring(0, brokerNum.length() - 4);
                replaceNum = " (".concat(brokerNum.replace(temp, "****")).concat(")");
            } else {
                replaceNum = " (****".concat(brokerNum) + ")";
            }
        }
        return replaceNum;
    }


    /**
     * 判断底部navigator是否已经显示
     *
     * @param windowManager
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasSoftKeys(WindowManager windowManager) {
        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        return (realHeight - displayHeight) > 0;
    }

    /**
     * 数字类型时间转化为时分秒
     * @param num
     * @param type    TYPE_SEC_μs 微秒 TYPE_SEC_MS 毫秒 TYPE_SEC 秒
     * @return
     */
    public static String long2Time(long num, int type) {
        switch (type) {
            case TYPE_SEC_μs:
                num = num / 1000;
                break;
            case TYPE_SEC:
                num = num * 1000;
                break;
        }
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        num = num / 1000;
        return String.format("%02d:%02d:%02d", num / 3600, (num % 3600) / 60, (num % 60));
    }

    /**
     * 微秒
     */
    public static final int TYPE_SEC_μs = 0;

    /**
     * 毫秒
     */
    public static final int TYPE_SEC_MS = 1;

    /**
     * 秒
     */
    public static final int TYPE_SEC = 2;

    /**
     * dp--->px转换
     * @param dpValue  value of dp
     * @return px
     */
    public static int dp2px(Context context, int dpValue) {
        return (int) (dpValue * context.getResources().getDisplayMetrics().density);
    }
}
