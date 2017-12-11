package com.ileiju.ticketverify.global;

/**
 * Created by hunter on 17/12/9.
 */

public class ServiceInfo {

    private static ServiceInfo mInstance;
    private String mSessionID;
    private String mUserName;

    //私有化构造方法
    private ServiceInfo() {
    }

    //获取单例的实例
    public static ServiceInfo getInstance() {

        if (mInstance == null) {
            synchronized (ServiceInfo.class) {
                mInstance = new ServiceInfo();
            }
        }
        return mInstance;
    }

    public String getSessionID() {
        return mSessionID;
    }

    public void setSessionID(String mSessionID) {
        this.mSessionID = mSessionID;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }
}
