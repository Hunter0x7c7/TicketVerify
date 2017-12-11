package com.ileiju.ticketverify.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ileiju.ticketverify.R;
import com.ileiju.ticketverify.base.BaseActivity;
import com.ileiju.ticketverify.base.BaseApplication;
import com.ileiju.ticketverify.global.ServiceInfo;
import com.ileiju.ticketverify.module.main.MainActivity;
import com.ileiju.ticketverify.util.ToastUtil;
import com.jude.beam.bijection.RequiresPresenter;

import static com.ileiju.ticketverify.R.id.btn_login;

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseActivity<LoginPresenter>
        implements View.OnClickListener {

    private EditText etUserName;
    private EditText etPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View initLayout() {
        return View.inflate(BaseApplication.getContext(), R.layout.activity_login, null);
    }

    @Override
    public void initView() {
        super.initView();

        setHomePageTitle("Login");

        etUserName = (EditText) findViewById(R.id.et_user_name);
        etPassword = (EditText) findViewById(R.id.et_password);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case btn_login:

                login();
                break;
        }
    }

    public void login() {
        getPresenter().login(getUserName(), getPassword());
    }


    public void loginSucceed(String result) {
        ToastUtil.showPrompt("登录成功");

        ServiceInfo.getInstance().setUserName(getUserName());

        startActivity(new Intent(mActivity, MainActivity.class));
        finish();
    }

    public void loginFailure(String result) {
        ToastUtil.showPrompt(result);
    }

    private String getUserName() {
        return etUserName.getText().toString();
    }

    private String getPassword() {
        return etPassword.getText().toString();
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
            super.onBackPressed();
        }
    }

}
