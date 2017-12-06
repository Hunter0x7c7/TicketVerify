package com.ileiju.ticketverify.module.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ileiju.ticketverify.R;
import com.ileiju.ticketverify.base.BaseActivity;
import com.ileiju.ticketverify.base.BaseApplication;
import com.ileiju.ticketverify.util.OkHttpUtil;
import com.jude.beam.bijection.RequiresPresenter;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainModel.getInstance().onAppCreate(this);
    }


    @Override
    public View initLayout() {
        return View.inflate(BaseApplication.getContext(), R.layout.activity_main, null);
    }

    @Override
    public void initView() {
        super.initView();


       Button btn_terminal = (Button) findViewById(R.id.btn_terminal);
       Button btn_login = (Button) findViewById(R.id.btn_login);

        btn_terminal.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    public void testGetTerminal( ) {
        try {
            new OkHttpUtil().testGetTerminal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testPostDlogin( ) {
        try {
            new OkHttpUtil().testPostDlogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_terminal:

                testGetTerminal();
                break;
            case R.id.btn_login:

                testPostDlogin();
                break;
        }
    }
}
