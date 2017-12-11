package com.ileiju.ticketverify.module.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.ileiju.ticketverify.R;
import com.ileiju.ticketverify.base.BaseActivity;
import com.ileiju.ticketverify.base.BaseApplication;
import com.ileiju.ticketverify.global.ServiceInfo;
import com.ileiju.ticketverify.interfaces.OnPermissionListener;
import com.ileiju.ticketverify.interfaces.StatusCallback;
import com.ileiju.ticketverify.module.login.LoginActivity;
import com.ileiju.ticketverify.util.NetworkUtil;
import com.ileiju.ticketverify.util.PermissionUtil;
import com.ileiju.ticketverify.util.SystemUtil;
import com.ileiju.ticketverify.util.ToastUtil;
import com.jude.beam.bijection.RequiresPresenter;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


/**
 * 创建日期： 2017/3/21 13:50
 * 描    述：程序入口
 */

@RequiresPresenter(SplashPresenter.class)
public class SplashActivity extends BaseActivity<SplashPresenter> {

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

        //请求需要的所有权限
        String[] permissions = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        OnPermissionListener onPermissionListener = new OnPermissionListener() {
            @Override
            public void onGranted() {
                //获得权限后初始化数据
                initMyData();
            }

            @Override
            public void onDenied(List<String> permissions) {
                //没有权限
                ToastUtil.showPrompt("您拒绝授权可用权限，应用即将退出");
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            SystemClock.sleep(2000);

                            //退出程序
                            BaseApplication.getInstance().exit();   //退出
                        } catch (Exception e) {
                            SystemUtil.println("error : " + e);
                        }
                    }
                }.start();
            }
        };
        requestPermission(onPermissionListener, permissions);
    }

    private void requestPermission(final OnPermissionListener onPermissionListener, final String... permissions) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //申请需要的所有权限
                PermissionUtil.request(onPermissionListener, permissions);
            }
        };
        postDelayed(runnable, 100);
    }

    private void initMyData() {
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


        //请求这个Url得到一个SessionID
        getPresenter().getSessionID("http://my.12301.cc/");
    }

    public void getSessionIdFailure(String session) {
        ToastUtil.showPrompt("获取SessionID失败");
        SystemUtil.println(session);
    }

    public void getSessionIdSucceed(String session) {

        //SessionID获取成功
        ServiceInfo.getInstance().setSessionID(session);
        SystemUtil.println("SessionID获取成功:" + session);

        startActivity(new Intent(mActivity, LoginActivity.class));
        finish();
    }


    //以下全都是面向上层的接口。这里全都是回调形式的异步返回。
    public void userLogin(final String userName, final String password, final StatusCallback callback) {
        //这里是向网络请求数据，并接受返回数据，解析返回上层。这里我在Callback里完成数据解析。让代码更简洁。
//        RequestManager.getInstance().post(API.URL.ModifyName, new RequestMap("password", password), callback);

        System.out.println(".userLogin......userName:" + userName + " password:" + password + " callback:" + callback);

        final Retrofit retrofitDlogin = new Retrofit.Builder()
                .baseUrl("http://my.12301.cc")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RequestSerives requestSerives = retrofitDlogin.create(RequestSerives.class);//这里采用的是Java的动态代理模式
        Callback<ResponseBody> bodyCallback = new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    Headers headers = response.headers();
                    Set<String> names = headers.names();
                    for (String name : names) {
                        System.out.println(".............next:" + name + ":" + headers.values(name));
                    }
                    ToastUtil.showPrompt("....response:" + new Gson().toJson(response.message()));

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    call.cancel();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println(".......失败.......t:" + t.getMessage() + " call:" + call.toString());
            }
        };
        requestSerives.dlogin().enqueue(bodyCallback);
        requestSerives.terminal("1", "922190", "624159").enqueue(bodyCallback);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://shuihuo.tyuniot.com")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RequestSerives apiService = retrofit.create(RequestSerives.class);
        apiService.login3(userName, password, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) {
                        System.out.println("......doOnNext........:" + new Gson().toJson(loginBean.Data));
                        ToastUtil.showPrompt("....doOnNext...........:" + new Gson().toJson(loginBean.Data));
                    }
                })
                //                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) {

                        System.out.println("......subscribe.....accept...:" + new Gson().toJson(loginBean.Data));
                        ToastUtil.showPrompt("....subscribe.....accept......:" + new Gson().toJson(loginBean.Data));
                        if (callback != null) {
                            callback.success(new Gson().toJson(loginBean));
                        }
//                        if (callback != null) {
//                            callback.result(1 + (int) (System.currentTimeMillis() % 2), "");
//                        }
                    }
                });


    }


    public void userLogin(final String userName, final String password) {


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://shuihuo.tyuniot.com")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


        RequestSerives apiService = retrofit.create(RequestSerives.class);
        apiService.login3(userName, password, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) {
                        System.out.println("......doOnNext........:" + new Gson().toJson(loginBean.Data));
                        ToastUtil.showPrompt("....doOnNext........");
                    }
                })
//                    .unsubscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) {

                        ToastUtil.showPrompt("....subscribe.......accept...:" + new Gson().toJson(loginBean.Data));
                        try {
                            System.out.println("......subscribe.....accept...:" + new Gson().toJson(loginBean.Data));
//                                if (callback != null) {
//                                    callback.success(new Gson().toJson(loginBean));
//                                }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


    }

    public interface RequestSerives {
        /**
         * 表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
         * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
         */
        @POST("/form")
        @FormUrlEncoded
        Call<ResponseBody> testFormUrlEncoded1(@Field("username") String name, @Field("age") int age);

        /**
         * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
         * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
         */
        @POST("/form")
        @Multipart
        Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

        @POST("/login/Login")
        Call<LoginBean> login(@Query("param1") String userName, @Query("param2") String password, @Query("flag") String flag);

        @POST("/login/Login")
        Call<ResponseBody> login2(@Query("param1") String userName, @Query("param2") String password, @Query("flag") String flag);

        @POST("/login/Login")
        Observable<LoginBean> login3(@Query("param1") String userName, @Query("param2") String password, @Query("flag") String flag);

        @GET("/dlogin_n.html")
        Call<ResponseBody> dlogin();

        @GET("/call/terminal.php")
        Call<ResponseBody> terminal(@Query("query") String query, @Query("salerid") String salerid, @Query("voucher") String voucher);
    }

    public class LoginBean {
        public String Code;
        public String Msg;
        public DataBean Data;
    }

    public class DataBean {
        private int User_ID;
        private String User_Name;
        private String User_Phone;
        private String User_Type;
        private String User_Role;
        private int User_Region_ID;
        private String User_Region;
        private String Access_Token;
        private String User_Status;
    }

}
