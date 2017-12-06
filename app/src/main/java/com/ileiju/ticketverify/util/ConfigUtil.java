package com.ileiju.ticketverify.util;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.ileiju.ticketverify.base.BaseApplication;

import java.util.Locale;

/**
 * 创建日期： 2017/2/28 16:50
 * 描    述：有关配置信息的工具类
 */
public class ConfigUtil extends Thread {
    private static boolean defaultDebug = true;
    private static boolean debugMode;

    /**
     * 获取远程服务器的ip地址
     *
     * @return
     */
    public static String getRemoteHost() {
        return getRemoteHost(true);
    }

    /**
     * 获取远程服务器的ip地址
     *
     * @return
     */
    public static String getRemoteHost(boolean isTransition) {

        return "";
    }

    /**
     * 获取指定包名程序的版本号
     *
     * @return 返回版本号
     */
    public static int getVersionCode() {
        return getVersionCode(getMyPackageName());
    }

    /**
     * 获取指定包名程序的版本号
     *
     * @param packageName 指定包名
     * @return 返回版本号
     */
    public static int getVersionCode(String packageName) {
        int verCode = -1;
        try {
            Context context = BaseApplication.getContext();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            verCode = packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取指定包名程序的版本名称
     *
     * @param packageName 指定包名
     * @return 返回版本名称
     */
    public static String getVersionName(String packageName) {
        String versionName = "";
        try {
            Context context = BaseApplication.getContext();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取程序的包名
     *
     * @return
     */
    public static String getMyPackageName() {
        Context context = BaseApplication.getContext();
        return context.getPackageName();
    }

    /**
     * 获取当前系统语言
     *
     * @return 返回系统语言
     */
    public static String getLanguageEnv() {
        Locale l = Locale.getDefault();
        String language = l.getLanguage();
        String country = l.getCountry().toLowerCase();
        if ("zh".equals(language)) {
            if ("cn".equals(country)) {
                language = "zh-CN";
            } else if ("tw".equals(country)) {
                language = "zh-TW";
            }
            language = language + "-" + country;
        }
        return language;
    }


    public static boolean isDebugMode() {
        return debugMode;
    }
}
