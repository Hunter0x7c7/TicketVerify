package com.ileiju.ticketverify.module.splash;

import android.content.Context;
import android.support.annotation.Nullable;

import com.ileiju.ticketverify.base.BaseModel;
import com.ileiju.ticketverify.interfaces.StatusCallback;
import com.ileiju.ticketverify.util.JsonUtil;
import com.ileiju.ticketverify.util.OkHttpUtil;
import com.ileiju.ticketverify.util.StringUtil;
import com.ileiju.ticketverify.util.SystemUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by hunter on 17/12/6.
 */

public class SplashModel extends BaseModel {


    public static SplashModel getInstance() {
        return getInstance(SplashModel.class);
    }

    @Override
    public void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);

    }

    public void getSessionID(final String url, final StatusCallback callback) {

        Map<String, String> requestHeaders = new LinkedHashMap<>();
        requestHeaders.put("Host", "my.12301.cc");//
        requestHeaders.put("Connection", "keep-alive");
        requestHeaders.put("Upgrade-Insecure-Requests", "1");
        requestHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400");
        requestHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        requestHeaders.put("Accept-Encoding", "gzip, deflate, sdch");//
        requestHeaders.put("Accept-Language", "zh-CN,zh;q=0.8");

        OkHttpUtil.HttpCallback responseCallback = new OkHttpUtil.HttpCallback() {
            @Override
            public void onResponse(Call call, Response response, Headers headers, String result) {

                String sessionId = parseSessionID(headers);
                if (callback != null) {
                    callback.success(sessionId);
                }
            }

            @Override
            public void onFailure(Call call, Exception exception) {
                super.onFailure(call, exception);
                if (callback != null) {
                    callback.result(-1, exception.getMessage());
                }
            }
        };

        OkHttpUtil.getRequest(requestHeaders, url, responseCallback);
    }

    @Nullable
    private String parseSessionID(Headers headers) {
        //返回头: Set-Cookie: PHPSESSID=dlnvo88jipgr82q9m4vioegt01; path=/; domain=.12301.cc
        Set<String> names = headers.names();
        String sessionHeader = "Set-Cookie";
        String sessId = "PHPSESSID";
        String sessionValue = null;
        for (String name : names) {
            SystemUtil.println("result header:" + name + ":" + headers.get(name));

            if (name == null) continue;
            if (name.equals(sessionHeader)) {
                String value = headers.get(name);

                if (value != null && value.contains(sessId)) {
                    sessionValue = headers.get(name);
                    break;
                }
            }
        }

        //PHPSESSID=e6dijmf02cffjth4dan5s2vv41; path=/; domain=.12301.cc
        String sessionId = null;
        if (sessionValue != null) {

            String[] strings = StringUtil.splitString(sessionValue, ";");
            for (String sess : strings) {
                if (sess.startsWith(sessId + "=")) {
                    String[] split = StringUtil.splitString(sess, "=");
                    if (split != null && split.length >= 2) {
                        sessionId = split[1];
                        break;
                    }
                }
            }
        }
        return sessionId;
    }

    public void getEncryptPassword() {
//        String url  = "https://github.com/HunterHuang0X7C7/HunterServer/blob/master/TicketVerify.txt";
        String url = "https://raw.githubusercontent.com/Hunter0X7C7/HunterServer/master/TicketVerify.txt";

        OkHttpUtil.downloadFile(url, new StatusCallback() {
            @Override
            public void success(String result) {

                System.out.println("...getEncryptPassword...result:" + result);
                String html = StringUtil.replaceHtml(result);
                String json = StringUtil.html2Text(html);
                try {

                    System.out.println("...getEncryptPassword...json:" + json);
                    PasswordBean passwordBean = JsonUtil.fromJson(json, PasswordBean.class);

                    System.out.println("....code:" + passwordBean.code);
                    System.out.println("....msg:" + passwordBean.msg);
                    for (DataBean db : passwordBean.data) {
                        System.out.println("....data:" + db.key + " = " + db.value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void result(int status, String result) {
                System.out.println("..f..result:" + result);
            }
        });
    }

    public class PasswordBean {
        private int code;
        private String msg;
        private List<DataBean> data;
    }

    public class DataBean {
        private String key;
        private String value;
    }


}
