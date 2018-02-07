package com.ileiju.ticketverify.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ileiju.ticketverify.base.BasePresenter;
import com.ileiju.ticketverify.interfaces.StringCallback;
import com.ileiju.ticketverify.module.main.bean.QueryBean;
import com.ileiju.ticketverify.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by hunter on 17/12/6.
 */

public class MainPresenter extends BasePresenter<MainActivity> {

    @Override
    protected void onCreate(@NonNull MainActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    protected MainModel getModel() {
        return MainModel.getInstance();
    }

    public void queryTicket(String salerid, String voucher) {

        StringCallback callback = new StringCallback() {
            @Override
            public void success(String info) {
                queryTicketSucceed(info);
            }

            @Override
            public void result(int status, String info) {
                queryTicketFailure(info);
            }

        };

        getModel().queryTicket(salerid, voucher, callback);
    }

    public void verify(String salerid, String ordernum) {

        getModel().verify(salerid, ordernum, new StringCallback() {


            @Override
            public void success(String result) {

                System.out.println("...success.....verify:" + result);
            }

            @Override
            public void result(int status, String result) {
                System.out.println("..result......verify:" + result);

            }
        });
    }


    private void queryTicketSucceed(final String info) {
        System.out.println(".....queryTicketSucceed:" + info);


        QueryBean queryBean = null;
        try {
            JSONObject jsonObjName = new JSONObject(info);
            JSONObject orders = jsonObjName.getJSONObject("orders");
            if (orders != null) {
                String result = replaceOrderKey(info);
                queryBean = JsonUtil.fromJson(result, QueryBean.class);

                System.out.println("........result:" + result);
                System.out.println("........queryBean:" + queryBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //{"status":"success","orders":{"22973015":{"ordernum":"22973015","code":"029584","mcode":"029584","ptype":"A","pmode":"0","status":"0","landid":"68357","series":"0","endtime":"2017-12-05","ordertel":"13616060093","ordername":"许雪花","ordertime":"2017-12-05 20:51:34","begintime":"2017-12-05","paystatus":"1","checktime":"0000-00-00 00:00:00","ifprint":"0","tickets":[{"tid":159572,"tnum":1,"tnum_s":1,"name":"免费票","tprice":"0","ordernum":"22973015","status":"0","batch_check":"0","refund_audit":"0","revoke_audit":"1"}],"onsale":0,"isResource":false}}}
        //  String testStr = "{\"status\":\"success\",\"orders\":{\"22973015\":{\"ordernum\":\"22973015\",\"code\":\"029584\",\"mcode\":\"029584\",\"ptype\":\"A\",\"pmode\":\"0\",\"status\":\"0\",\"landid\":\"68357\",\"series\":\"0\",\"endtime\":\"2017-12-05\",\"ordertel\":\"13616060093\",\"ordername\":\"许雪花\",\"ordertime\":\"2017-12-05 20:51:34\",\"begintime\":\"2017-12-05\",\"paystatus\":\"1\",\"checktime\":\"0000-00-00 00:00:00\",\"ifprint\":\"0\",\"tickets\":[{\"tid\":159572,\"tnum\":1,\"tnum_s\":1,\"name\":\"免费票\",\"tprice\":\"0\",\"ordernum\":\"22973015\",\"status\":\"0\",\"batch_check\":\"0\",\"refund_audit\":\"0\",\"revoke_audit\":\"1\"}],\"onsale\":0,\"isResource\":false}}}";

        final QueryBean finalQueryBean = queryBean;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (finalQueryBean != null) {
                    getView().queryTicketSucceed(finalQueryBean);
                } else {
                    getView().queryTicketFailure(info);
                }
            }
        });
    }

    private void queryTicketFailure(final String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getView().queryTicketFailure(info);
            }
        });
    }

    private String replaceOrderKey(String str) {
        String result = null;
        try {
            JSONObject jsonObjName = new JSONObject(str);
            //key迭代器
            Iterator<?> itName = jsonObjName.keys();
            JSONObject ordersJsonObject = null;
            while (itName.hasNext()) {
                String specID = itName.next().toString();

                if (specID.equals("orders")) {
                    ordersJsonObject = jsonObjName.getJSONObject(specID);
                    break;
                }
            }
            if (ordersJsonObject != null) {
                Iterator<?> keys = ordersJsonObject.keys();
                String specID = keys.next().toString();

                result = str.replace("\"" + specID + "\":", "\"orderKey\":");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //{"status":"success","orders":[]}


}
