package com.ileiju.ticketverify.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.ileiju.ticketverify.base.BaseActivity;
import com.jude.beam.bijection.Presenter;

/**
 * Created by hunter on 17/12/6.
 */

public class MainPresenter extends Presenter<BaseActivity> {

    @Override
    protected void onCreate(@NonNull BaseActivity view, Bundle savedState) {
        super.onCreate(view, savedState);


        getModel();
    }

    private MainModel getModel() {
        return MainModel.getInstance();
    }
}
