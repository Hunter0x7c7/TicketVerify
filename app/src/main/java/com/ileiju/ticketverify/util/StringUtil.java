package com.ileiju.ticketverify.util;

import android.text.TextUtils;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串分割处理
 *
 * @author Zihang Huang
 */
public class StringUtil {
    /**
     * 按照splitor分割src
     *
     * @param src
     * @param splitor
     * @return
     */
    public static String[] splitString(String src, String splitor) {
        StringTokenizer s = new StringTokenizer(src, splitor);

        String[] strs = new String[s.countTokens()];
        int i = 0;
        while (s.hasMoreTokens()) {
            strs[i++] = s.nextToken();
        }
        return strs;
    }

    /**
     * 去除回车换行
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
            dest = Pattern.compile("\"|\\s*").matcher(dest).replaceAll("");
        }
        return dest;
    }

    /**
     * 去除重复部分
     */
    public static String replaceRepeat(String a, String b) {
        if (TextUtils.isEmpty(a) || TextUtils.isEmpty(b)) {
            return null;
        }
        int length = Math.min(a.length(), b.length());
        int pos = 0;
        while (pos < length) {
            if (0 != (a.charAt(pos) ^ b.charAt(pos))) {
                break;
            }
            pos++;
        }
        return b.substring(pos);
    }


    /**
     * 从Html文件中取出++++++符号中间的部分
     */
    public static String replaceHtml(String result) {
        String key = "+++++++";

        result = result.replaceAll("\\+{5,}", key);

        int i = result.indexOf(key) + key.length();
        int j = result.lastIndexOf(key);
        return result.substring(i, j);
    }

    /**
     * 从html中提取纯文本
     */
    public static String html2Text(String strHtml) {
        return strHtml.replaceAll("</?[^>]+>", "")  // 剔出<html>的标签
                .replaceAll("<a>\\s*|\t|\r|\n</a>", "") // 去除字符串中的空格,回车,换行符,制表符
                .replace("&quot;", "\"").replace("&amp;", "&")
                .replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");// 去除多个空行
    }

}
