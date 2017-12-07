package com.ileiju.ticketverify.module.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ileiju.ticketverify.base.BaseActivity;
import com.jude.beam.bijection.Presenter;

/**
 * Created by hunter on 17/12/6.
 */

public class SplashPresenter extends Presenter<BaseActivity> {

    @Override
    protected void onCreate(@NonNull BaseActivity view, Bundle savedState) {
        super.onCreate(view, savedState);


        getModel();
    }

    private SplashModel getModel() {
        return SplashModel.getInstance();
    }
}
