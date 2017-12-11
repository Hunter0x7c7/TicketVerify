package com.ileiju.ticketverify.module.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ileiju.ticketverify.base.BasePresenter;
import com.ileiju.ticketverify.interfaces.StatusCallback;

/**
 * Created by hunter on 17/12/6.
 */

public class SplashPresenter extends BasePresenter<SplashActivity> {

    @Override
    protected void onCreate(@NonNull SplashActivity view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    protected SplashModel getModel() {
        return SplashModel.getInstance();
    }

    public void getSessionID(String url) {
        StatusCallback callback = new StatusCallback() {
            @Override
            public void success(String info) {

                getSessionIdSucceed(info);
            }

            @Override
            public void result(int status, String info) {

                getSessionIdFailure(info);
            }
        };
        getModel().getSessionID(url, callback);
    }

    private void getSessionIdSucceed(final String result) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getView().getSessionIdSucceed(result);
            }
        };
        runOnUiThread(runnable);
    }

    private void getSessionIdFailure(final String result) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getView().getSessionIdFailure(result);
            }
        };
        runOnUiThread(runnable);
    }


}
