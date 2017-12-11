package com.ileiju.ticketverify.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jude.beam.bijection.Presenter;

/**
 * Created by hunter on 17/12/6.
 */

public abstract class BasePresenter<T extends BaseActivity> extends Presenter<T> {

    @Override
    protected void onCreate(@NonNull T view, Bundle savedState) {
        super.onCreate(view, savedState);
    }

    protected abstract BaseModel getModel();

    protected void runOnUiThread(Runnable runnable) {
        getView().runOnUiThread(runnable);
    }

}
