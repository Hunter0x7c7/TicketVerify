package com.ileiju.ticketverify.module.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;

import com.ileiju.ticketverify.R;
import com.ileiju.ticketverify.base.BaseActivity;
import com.ileiju.ticketverify.module.main.MainActivity;
import com.ileiju.ticketverify.util.NetworkUtil;
import com.ileiju.ticketverify.util.ToastUtil;


/**
 * 创建日期： 2017/3/21 13:50
 * 描    述：程序入口
 */

public class SplashActivity extends BaseActivity {

    private long mUptimeMillis;
    private int delayMillis = 1000 * 4;
    private final int NETWORK_AVAILABLE = 1001;
    private final int START_LOGIN_RUNNABLE = 1;
    private final int START_GUIDE_RUNNABLE = 2;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NETWORK_AVAILABLE://检查网络是否可用
                    boolean isAvailable = (boolean) msg.obj;
                    int network_disabled = R.string.network_disabled;
                    if (!isAvailable) {
                        ToastUtil.showPrompt(network_disabled, ToastUtil.LENGTH_LONG);//提示用户
                    }
                    break;
                case START_LOGIN_RUNNABLE:

                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initLayout() {
        return View.inflate(mActivity, R.layout.activity_splash, null);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();

        Runnable target = new Runnable() {
            @Override
            public void run() {
                boolean networkAvailable = NetworkUtil.isNetworkAvailable();
                Message msg = new Message();
                msg.what = NETWORK_AVAILABLE;
                msg.obj = networkAvailable;
                mHandler.sendMessage(msg);
            }
        };
        new Thread(target).start();


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mActivity, MainActivity.class));
                finish();
            }
        }, 1000);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
    }

}
