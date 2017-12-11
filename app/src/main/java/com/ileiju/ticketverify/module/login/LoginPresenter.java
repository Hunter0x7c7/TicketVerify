package com.ileiju.ticketverify.module.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ileiju.ticketverify.R;
import com.ileiju.ticketverify.base.BasePresenter;
import com.ileiju.ticketverify.interfaces.StatusCallback;
import com.ileiju.ticketverify.util.JsonUtil;
import com.ileiju.ticketverify.util.SystemUtil;

/**
 * Created by hunter on 17/12/6.
 */

public class LoginPresenter extends BasePresenter<LoginActivity> {

    @Override
    protected void onCreate(@NonNull LoginActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    protected LoginModel getModel() {
        return LoginModel.getInstance();
    }

    public void login(String userName, String password) {


        StatusCallback  callback = new StatusCallback () {
            @Override
            public void success(String info) {
                loginSucceed(info);
            }

            @Override
            public void result(int status, String info) {
                loginFailure(info);
            }

        };

        getModel().login(userName, password, callback);

    }

    private void loginSucceed(final String result) {
        SystemUtil.println(result);

        final LoginBean loginBean = JsonUtil.fromJson(result, LoginBean.class);
        final LoginActivity activity = getView();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                if (loginBean != null) {
                    if (loginBean.code == 200) {
                        activity.loginSucceed(result);
                    } else {
                        activity.loginFailure(loginBean.msg);//登录失败
                    }
                } else {
                    String string = activity.getString(R.string.request_failure);//登录失败
                    activity.loginFailure(string);
                }

            }
        };
        runOnUiThread(runnable);
    }

    private void loginFailure(final String result) {
        SystemUtil.println(result);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LoginActivity activity = getView();
                String string = activity.getString(R.string.request_error);//登录错误
                activity.loginFailure(string);
            }
        };
        runOnUiThread(runnable);
    }

    /**
     * 登录Bean
     * {"code":0,"msg":"账号或密码错误，或您的账号还在审核中","vcode":0,"url":""}
     * {"code":200,"msg":"成功","vcode":0}
     */
    public class LoginBean {
        private int code;
        private String msg;
        private int vcode;
        private String url;
    }


}
