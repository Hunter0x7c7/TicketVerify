package com.ileiju.ticketverify.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ileiju.ticketverify.R;
import com.jude.beam.bijection.Presenter;
import com.jude.beam.expansion.BeamBaseActivity;

/**
 */
public abstract class BaseActivity<T extends Presenter> extends BeamBaseActivity<T> {
    public Activity mActivity;
    private FrameLayout mFrameLayout;
    private TextView mTitle;
    private RelativeLayout mTitlebar;
    private ImageButton ibBack;
    private TextView mBtnMore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        init();
        initView();
        initData();

        BaseApplication.getInstance().addActivity(this);   //创建Activity时，添加到List进行管理。
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
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getInstance().removeActivity(this);   //退出Activity时， 到List删除Activity。
    }


    /**
     * 初始化
     */
    private void init() {

        mActivity = this;

        //初始化标题栏
        mTitlebar = (RelativeLayout) findViewById(R.id.title_bar);
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mBtnMore = (TextView) findViewById(R.id.ib_more);

        mFrameLayout = (FrameLayout) findViewById(R.id.fl_content);
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(initLayout());

        //返回按钮点击事件
        if (ibBack != null)
            ibBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        setCustomTitleBar(false);
    }

    /**
     * 设置当前界面的布局，必须实现
     *
     * @return 返回View
     */
    public abstract View initLayout();

    /**
     * 初始化视图，可以不实现
     */
    public void initView() {
    }

    /**
     * 初始化数据，可以不实现
     */
    public void initData() {
    }

    /**
     * 设置标题
     *
     * @param title 标题名字
     */
    public void setTitle(CharSequence title) {
        setCustomTitleBar(!TextUtils.isEmpty(title));
        mTitle.setText(title);
    }

    /**
     * 设置标题
     *
     * @param title 标题名字
     */
    public void setTitle(CharSequence title, int size) {
        setCustomTitleBar(!TextUtils.isEmpty(title));
        mTitle.setText(title);
        mTitle.setTextSize(size);
    }

    /**
     * 设置标题
     *
     * @param resId 标题名字Id
     */
    public void setTitle(@StringRes int resId) {
        setTitle(getString(resId));
    }

    /**
     * 设置标题
     */
    public void setHomePageTitle(String title) {
        setCustomTitleBar(true);
        mTitle.setText(title);
        mTitle.setGravity(Gravity.LEFT);

        setIsShowBack(false);
    } /**
     * 设置标题
     *
     * @param resId 标题名字Id
     */
    public void setHomePageTitle(@StringRes int resId) {
        setCustomTitleBar(true);
        mTitle.setText(resId);
        mTitle.setGravity(Gravity.LEFT);

        setIsShowBack(false);
    }


    /**
     * 是否使用自定义标题栏
     *
     * @param isShow true显示 ，false不显示
     */
    public void setCustomTitleBar(boolean isShow) {
        mTitlebar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 使用标题栏更多按钮
     */
    public void setMoreButton(@StringRes int resId, View.OnClickListener listener) {
        String string = getString(resId);
        setMoreButton(string, listener);
    }

    /**
     * 使用标题栏更多按钮
     */
    public void setMoreButton(CharSequence text, View.OnClickListener listener) {
        mBtnMore.setVisibility(View.VISIBLE);
        mBtnMore.setText(text);
        //更多按钮点击事件
        mBtnMore.setOnClickListener(listener);
    }

    /**
     * 设置是否显示返回按键
     */
    public void setIsShowBack(boolean isShowBack) {
        ibBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
    }


    /**
     * 加载中 Dialog
     */
    public void showLoadingDialog() {

    }

    /**
     * 加载中 Dialog
     */
    public void showLoadingDialog(@StringRes int id) {

    }


    private long defaultDelayMillis = 800;
    private Handler mHandler = new Handler();

    /**
     * 异步执行一个延时Runnable
     *
     * @param r
     */
    public void postDelayed(Runnable r) {
        postDelayed(r, defaultDelayMillis);
    }

    /**
     * 异步执行一个延时Runnable
     *
     * @param r
     * @param delayMillis
     */
    public void postDelayed(Runnable r, long delayMillis) {
        if (r != null) {
            mHandler.postDelayed(r, delayMillis);
        }
    }

    /**
     * 获取R文件中的String
     *
     * @param resId
     * @return
     */
    public String getStringAll(@StringRes int... resId) {
        StringBuffer sb = new StringBuffer();
        int length = resId.length;
        for (int i = 0; i < length; i++) {
            sb.append(getString(resId[i]));
        }
        return sb.toString();
    }


}
