package com.ileiju.ticketverify.module.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ileiju.ticketverify.R;
import com.ileiju.ticketverify.base.BaseActivity;
import com.ileiju.ticketverify.base.BaseApplication;
import com.ileiju.ticketverify.module.login.LoginModel;
import com.ileiju.ticketverify.module.main.bean.QueryBean;
import com.ileiju.ticketverify.util.ToastUtil;
import com.jude.beam.bijection.RequiresPresenter;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends BaseActivity<MainPresenter>
        implements View.OnClickListener {

    private EditText etSalerid;
    private EditText etVoucher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View initLayout() {
        return View.inflate(BaseApplication.getContext(), R.layout.activity_main, null);
    }

    @Override
    public void initView() {
        super.initView();

        setHomePageTitle("Verify");

        etSalerid = (EditText) findViewById(R.id.et_salerid);
        etVoucher = (EditText) findViewById(R.id.et_voucher);
        Button btn_terminal = (Button) findViewById(R.id.btn_terminal);
        Button btn_verify = (Button) findViewById(R.id.btn_verify);

        btn_terminal.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
    }


    public void queryTicket() {
        getPresenter().queryTicket(getSalerid(), getVoucher());
    }

    public void queryTicketSucceed(QueryBean result) {
        ToastUtil.showPrompt("查询成功");


        QueryBean.OrderBean orderKey = result.getOrders().getOrderKey();
        String ordernum = orderKey.getOrdernum();
        String code = orderKey.getCode();

        getPresenter().verify(code, ordernum);
    }

    public void queryTicketFailure(String result) {
        System.out.println(".....queryTicketFailure:" + result);

        ToastUtil.showPrompt("没有查询到匹配的订单哦~");
    }

    public void verify() {
        getPresenter().verify("922190", "22973867");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_terminal:

                queryTicket();
                break;
            case R.id.btn_verify:

                verify();
                break;
        }
    }


    public String getSalerid() {
        return etSalerid.getText().toString();
    }

    public String getVoucher() {
        return etVoucher.getText().toString();
    }

    private long mExitTime = 0;

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - mExitTime > 2000) {
            String showContent = getStringAll(R.string.click_2_exit, R.string.app_name);
            ToastUtil.showPrompt(showContent);
            mExitTime = System.currentTimeMillis();
        } else {
            LoginModel.getInstance().logout(null);

            BaseApplication.getInstance().exit();   //退出
            MainActivity.super.onBackPressed();
        }
    }

}
