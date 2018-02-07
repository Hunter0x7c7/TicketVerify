package com.ileiju.ticketverify.module.main;

import android.content.Context;

import com.ileiju.ticketverify.base.BaseModel;
import com.ileiju.ticketverify.global.ServiceInfo;
import com.ileiju.ticketverify.interfaces.HttpCallback;
import com.ileiju.ticketverify.interfaces.StringCallback;
import com.ileiju.ticketverify.module.splash.SplashModel;
import com.ileiju.ticketverify.util.HttpURLConnUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hunter on 17/12/6.
 */

public class MainModel extends BaseModel {


    public static MainModel getInstance() {
        return getInstance(MainModel.class);
    }

    @Override
    public void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);

    }


    public void queryTicket(String salerid, String voucher
            , final StringCallback callback) {

        ServiceInfo info = ServiceInfo.getInstance();

        String url = "http://my.12301.cc/call/terminal.php?query=1&salerid=" + salerid + "&voucher=" + voucher;
        //http://my.12301.cc/call/terminal.php?query=1&salerid=922190&voucher=624159

        String cookie = "PHPSESSID=" + info.getSessionID()
                + "; uid=CgEEzVomyNOmW3cCAyLOAg==; passport=" + info.getUserName();
        //Cookie: uid=CgEDiFomm9E6tFjJAwfYAg==; PHPSESSID=32u4sejgtaj64khs9k0hrk37u0; passport=cs033; Hm_lvt_ae1b02a5eda4a3187ee650f57480dd6e=1512479700,1512483654; Hm_lpvt_ae1b02a5eda4a3187ee650f57480dd6e=1512483658

        Map<String, String> requestHeaders = new LinkedHashMap<>();
        requestHeaders.put("Host", "my.12301.cc");//
        requestHeaders.put("Connection", "keep-alive");
        requestHeaders.put("Accept", "application/json, text/javascript, */*; q=0.01");
//        requestHeaders.put("Origin", "http://my.12301.cc");
        requestHeaders.put("X-Requested-With", "XMLHttpRequest");
        requestHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400");
//        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        requestHeaders.put("Referer", "http://my.12301.cc/terminal_chk.html");
        requestHeaders.put("Accept-Encoding", "gzip, deflate, sdch");//
        requestHeaders.put("Accept-Language", "zh-CN,zh;q=0.8");
        requestHeaders.put("Cookie", cookie);

        HttpCallback httpCallback = new HttpCallback() {

            @Override
            public void onSucceed(Map<String, List<String>> headers, String result) {
                System.out.println(".....headers:" + headers);
                System.out.println(".....getRequest.result:" + result);

                if (callback != null) {
                    callback.success(result);
                }
            }

            @Override
            public void onFailure(Exception exception) {
                if (callback != null) {
                    callback.result(-1, exception.getMessage() );
                }
            }
        };
        HttpURLConnUtil.get(requestHeaders, url, httpCallback);
    }

    /**
     * 验证票
     *
     * @param salerid  产品编号
     * @param ordernum 订单号码
     * @param callback
     */
    public void verify(String salerid, String ordernum, StringCallback callback) {

        ServiceInfo info = ServiceInfo.getInstance();

        String url = "http://my.12301.cc/call/terminal.php";
        String cookie = "PHPSESSID=" + info.getSessionID()
                + "; uid=CgEEzVomyNOmW3cCAyLOAg==; passport=" + info.getUserName();
        //Cookie: uid=CgEDiFomm9E6tFjJAwfYAg==; PHPSESSID=32u4sejgtaj64khs9k0hrk37u0; passport=cs033; Hm_lvt_ae1b02a5eda4a3187ee650f57480dd6e=1512479700,1512483654; Hm_lpvt_ae1b02a5eda4a3187ee650f57480dd6e=1512483658

        Map<String, String> requestHeaders = new LinkedHashMap<>();
        requestHeaders.put("Host", "my.12301.cc");//
        requestHeaders.put("Connection", "keep-alive");
        requestHeaders.put("Accept", "application/json, text/javascript, */*; q=0.01");
        requestHeaders.put("Origin", "http://my.12301.cc");
        requestHeaders.put("X-Requested-With", "XMLHttpRequest");
        requestHeaders.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400");
        requestHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        requestHeaders.put("Referer", "http://my.12301.cc/terminal_chk.html");
        requestHeaders.put("Accept-Encoding", "gzip, deflate");//
        requestHeaders.put("Accept-Language", "zh-CN,zh;q=0.8");
        requestHeaders.put("Cookie", cookie);

        String data = "{\"check\":1,\"salerid\":\"" + salerid + "\",\"ordernum\":\"" + ordernum + "\",\"check_method\":\"0\",\"list\":{\"" + ordernum + "\":\"1\"},\"rtime\":\"\"}";
        Map<String, String> formBody = new LinkedHashMap<>();
        formBody.put("data", data);

        //data={"check":1,"salerid":"922190","ordernum":"22973867","check_method":"0","list":{"22973867":"1"},"rtime":""}
        HttpCallback httpCallback = new HttpCallback() {

            @Override
            public void onSucceed(Map<String, List<String>> headers, String result) {
                System.out.println(".....headers:" + headers);
                System.out.println(".....getRequest.result:" + result);

                //{"status":"fail","msg":"订单已过期"}
                //{"status":"success","msg":"验证成功"}
            }

            @Override
            public void onFailure(Exception exception) {
                System.out.println(".....exception:" + exception);

            }
        };
        // HttpURLConnUtil.post (requestHeaders, formBody, url, httpCallback);


        SplashModel.getInstance().getEncryptPassword();
    }


}
