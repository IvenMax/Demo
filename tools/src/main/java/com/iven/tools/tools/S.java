package com.iven.tools.tools;

/**
 * @auth Iven
 * 2016/11/22 22:17
 * @desc 字符串工具类
 */

public class S {
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
}
