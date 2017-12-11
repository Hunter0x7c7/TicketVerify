package com.ileiju.ticketverify.base;

import android.content.Context;

import com.jude.beam.model.AbsModel;

/**
 * Created by hunter on 17/12/6.
 */

public class BaseModel extends AbsModel {

    protected Context mContext;

    public static BaseModel getInstance() {
        return getInstance(BaseModel.class);
    }

    @Override
    public void onAppCreate(Context ctx) {
        super.onAppCreate(ctx);

        mContext = ctx;
    }


}
