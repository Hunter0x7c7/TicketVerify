package com.ileiju.ticketverify.module.main;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jude.beam.bijection.Presenter;

/**
 * Created by hunter on 17/12/6.
 */

public class MainPresenter extends Presenter<MainActivity> {

    @Override
    protected void onCreate(@NonNull MainActivity view, Bundle savedState) {
        super.onCreate(view, savedState);


        getModel();
    }

    private MainModel getModel() {
        return MainModel.getInstance();
    }
}
