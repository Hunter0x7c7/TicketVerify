package com.ileiju.ticketverify.module.login;

import android.content.Context;

import com.ileiju.ticketverify.base.BaseModel;
import com.ileiju.ticketverify.global.ServiceInfo;
import com.ileiju.ticketverify.interfaces.HttpCallback;
import com.ileiju.ticketverify.interfaces.StatusCallback;
import com.ileiju.ticketverify.util.HttpURLConnUtil;
import com.ileiju.ticketverify.util.SystemUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hunter on 17/12/6.
 */

public class LoginModel extends BaseModel {


    public static LoginModel getInstance() {
        return getInstance(LoginModel.class);
    }

    @Override
    public void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);
    }

    /**
     * 登录
     */
    public void login(String userName, String password, final StatusCallback callback) {

        String pass = encryptPassword(password);
        if (pass == null) {
            if (callback != null) {
                callback.success("账号或密码错误，或您的账号还在审核中");
            }
            return;
        }

        //登录接口url
        String url = "http://my.12301.cc/dlogin.php";
        String cookie = "PHPSESSID=" + ServiceInfo.getInstance().getSessionID()
                + "; uid=CgEDglosAHt9YCM0AwxUAg==; passport=" + userName;

        Map<String, String> requestHeaders = new LinkedHashMap<>();
        requestHeaders.put("Host", "my.12301.cc");//
        requestHeaders.put("Connection", "keep-alive");
        requestHeaders.put("Accept", "application/json, text/javascript, */*; q=0.01");
        requestHeaders.put("Origin", "http://my.12301.cc");
        requestHeaders.put("X-Requested-With", "XMLHttpRequest");
        requestHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400");
        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        requestHeaders.put("Referer", "http://my.12301.cc/");
        requestHeaders.put("Accept-Encoding", "gzip, deflate");//
        requestHeaders.put("Accept-Language", "zh-CN,zh;q=0.8");
        requestHeaders.put("Cookie", cookie);

        Map<String, String> formBody = new LinkedHashMap<>();
        formBody.put("passport", userName);
        formBody.put("password", pass);


        HttpCallback httpCallback = new HttpCallback() {

            @Override
            public void onSucceed(Map<String, List<String>> headers, String result) {

                System.out.println(".....headers:" + headers);
                System.out.println(".....postRequest.result:" + result);

                if (callback != null) {
                    callback.success(result);
                }
            }

            @Override
            public void onFailure(Exception exception) {
                if (callback != null) {
                    callback.result(-1, exception.getMessage());
                }
            }
        };
        HttpURLConnUtil.post(requestHeaders, formBody, url, httpCallback);
    }

    /**
     * 退出登录
     */
    public void logout(final StatusCallback callback) {

        //退出登录接口url
        String url = "http://my.12301.cc/call/handle.php?from=logout";
        String cookie = "PHPSESSID=" + ServiceInfo.getInstance().getSessionID()
                + "; uid=CgEDglosAHt9YCM0AwxUAg==";

        Map<String, String> requestHeaders = new LinkedHashMap<>();
        requestHeaders.put("Host", "my.12301.cc");//
        requestHeaders.put("Connection", "keep-alive");
        requestHeaders.put("Accept", "application/json, text/javascript, */*; q=0.01");
        requestHeaders.put("X-Requested-With", "XMLHttpRequest");
        requestHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400");
        requestHeaders.put("Referer", "http://my.12301.cc/terminal_chk.html");
        requestHeaders.put("Accept-Encoding", "gzip, deflate, sdch");//
        requestHeaders.put("Accept-Language", "zh-CN,zh;q=0.8");
        requestHeaders.put("Cookie", cookie);

        HttpCallback httpCallback = new HttpCallback() {

            @Override
            public void onSucceed(Map<String, List<String>> headers, String result) {
                SystemUtil.println(headers + ":" + result);
                if (callback != null) {
                    callback.success(result);
                }
            }

            @Override
            public void onFailure(Exception exception) {
                SystemUtil.println(exception.getMessage());
                if (callback != null) {
                    callback.result(-1, exception.getMessage());
                }
            }
        };
        HttpURLConnUtil.get(requestHeaders, url, httpCallback);
    }


    private String encryptPassword(String password) {
        String encrypt = null;
        if (password != null) {
            switch (password) {
                case "cs033033":
                    encrypt = "22710ed445d6a5b967f4fbba9db75309&yzm=";
                    break;
            }
        }
        return encrypt;
    }


}
