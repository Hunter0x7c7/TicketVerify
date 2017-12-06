package com.ileiju.ticketverify.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.ileiju.ticketverify.handler.CrashHandler;
import com.jude.beam.Beam;

import org.xutils.BuildConfig;
import org.xutils.x;

import java.util.LinkedList;
import java.util.List;


/**
 */
public class BaseApplication extends Application {
    /**
     * 全局上下文对象
     */
    private static Context mContext;
    private List<Activity> mList = new LinkedList<>();   //用于存放每个Activity的List
    private static BaseApplication mInstance;    //BaseApplication实例

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能


        //初始化全局异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        Beam.init(this);
    }


    /**
     * 获取上下文对象
     *
     * @return 返回上下文对象
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 通过一个方法给外面提供实例
     *
     * @return
     */
    public synchronized static BaseApplication getInstance() {
        if (null == mInstance) {
            mInstance = new BaseApplication();
        }
        return mInstance;
    }

    /**
     * add Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * remove Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        mList.remove(activity);
    }

    /**
     * 退出登录。遍历List，退出其它Activity
     */
    public void logout(Activity activity) {

        if (mList != null) {
            try {
                for (Activity a : mList) {
                    if (a != null && a != activity)
                        a.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 遍历List，退出每一个Activity
     */
    public void exit() {

        if (mList != null) {
            try {
                for (Activity activity : mList) {
                    if (activity != null)
                        activity.finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();   //告诉系统回收
    }

}
