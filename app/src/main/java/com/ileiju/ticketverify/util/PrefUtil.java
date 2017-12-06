package com.ileiju.ticketverify.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ileiju.ticketverify.base.BaseApplication;


/**
 * 创建日期： 2017/2/28 16:50
 * 描    述：SharePreference封装
 */
public class PrefUtil {

    /**
     * 配置文件名字
     */
    public static final String PREF_NAME = "config";

    /**
     * 获取一个boolean类型的配置信息
     *
     * @param key          配置信息的名字
     * @param defaultValue 配置信息的默认值
     * @return 返回配置信息的值
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 设置一个boolean类型的配置信息
     *
     * @param key   配置信息的名字
     * @param value 需要设置配置信息的值
     */
    public static void setBoolean(String key, boolean value) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 设置一个string类型的配置信息
     *
     * @param key          配置信息的名字
     * @param defaultValue 配置信息的默认值
     * @return 返回配置信息的值
     */
    public static String getString(String key, String defaultValue) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 获取一个string类型的配置信息
     *
     * @param key   配置信息的名字
     * @param value 需要设置配置信息的值
     */
    public static void setString(String key, String value) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取一个int类型的配置信息
     *
     * @param key          配置信息的名字
     * @param defaultValue 配置信息的默认值
     * @return 返回配置信息的值
     */
    public static int getInt(String key, int defaultValue) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 设置一个int类型的配置信息
     *
     * @param key   配置信息的名字
     * @param value 需要设置配置信息的值
     */
    public static void setInt(String key, int value) {
        SharedPreferences sp = BaseApplication.getContext().getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }

}
