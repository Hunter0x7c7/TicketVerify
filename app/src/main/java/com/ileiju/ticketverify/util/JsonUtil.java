package com.ileiju.ticketverify.util;

import com.google.gson.Gson;

/**
 * Json类
 * Created by hunter on 17/12/9.
 */

public class JsonUtil {

    private static Gson mGson;

    private JsonUtil() {
    }

    private static Gson getGson() {
        if (mGson == null) {
            synchronized (HttpURLConnUtil.class) {
                if (mGson == null) {
                    mGson = new Gson();
                }
            }
        }
        return mGson;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return getGson().fromJson(json, classOfT);
    }

    public static String toJson(Object str) {
        return getGson().toJson(str);
    }

}
