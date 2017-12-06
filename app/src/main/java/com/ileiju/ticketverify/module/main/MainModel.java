package com.ileiju.ticketverify.module.main;

import android.content.Context;

import com.jude.beam.model.AbsModel;

/**
 * Created by hunter on 17/12/6.
 */

public class MainModel extends AbsModel {

    private Context mContext;

    public static MainModel getInstance() {
        return getInstance(MainModel.class);
    }

    @Override
    public void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);

        mContext = ctx;
    }


}
