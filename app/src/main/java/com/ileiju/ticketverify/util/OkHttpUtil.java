package com.ileiju.ticketverify.util;

import android.util.Log;

import com.ileiju.ticketverify.interfaces.StatusCallback;
import com.ileiju.ticketverify.interfaces.StatusCallback1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.ContentValues.TAG;

/**
 * // Okhttp库Util
 * compile 'com.squareup.okhttp3:okhttp:3.1.2'
 */
public class OkHttpUtil {


    String uid = "CgEDiFomm9E6tFjJAwfYAg==";
    String phpsessid = "bcdpik0fsg9rn6gndhqu40q3f0";
    String passport = "cs033";
    String hm_lvt = "1512479700,1512483654";
    String hm_lpvt = "1512483658";
    String cookie = "PHPSESSID=" + phpsessid + "; ";
    //                + "Hm_lvt_ae1b02a5eda4a3187ee650f57480dd6e=" + hm_lvt + "; Hm_lpvt_ae1b02a5eda4a3187ee650f57480dd6e=" + hm_lpvt;
    String password = "22710ed445d6a5b967f4fbba9db75309&yzm=";

    public void testGetTerminal() {

        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("Host", "my.12301.cc");//
        hashMap.put("Connection", "keep-alive");
        hashMap.put("Accept", "application/json, text/javascript, */*; q=0.01");
        hashMap.put("Origin", "http://my.12301.cc");//
        hashMap.put("X-Requested-With", "XMLHttpRequest");
        hashMap.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400");
        hashMap.put("Content-Type", "application/x-www-form-urlencoded; charset=ISO8859-1");
        hashMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        hashMap.put("Referer", "http://my.12301.cc/");//
        hashMap.put("Accept-Encoding", "gzip, deflate");//
        hashMap.put("Accept-Language", "zh-CN,zh;q=0.8");
        hashMap.put("Cookie", cookie);

        //查询接口Cookie需要session id
        String url = "http://my.12301.cc/call/terminal.php?query=1&salerid=922190&voucher=624159";

        HttpCallback responseCallback = new HttpCallback() {
            @Override
            public void onFailure(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response, Headers headers, String restlt) {

                //{"status":"fail","msg":"????????","code":0}

                if (!response.isSuccessful()) {
                    System.out.println("response3......3.....Unexpected code " + response);
                    return;
                }

                for (int i = 0; i < headers.size(); i++) {
                    System.out.println(".....response3.Headers:" + headers.name(i) + ": " + headers.value(i));
                }

                try {
                    System.out.println(".........response:" + response);
                    System.out.println(".........restlt:" + restlt);
//                    TerminalBean terminalBean = new Gson().fromJson(restlt, TerminalBean.class);
//                    System.out.println(".........terminalBean:" + new Gson().toJson(terminalBean));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };

        getRequest(hashMap, url, responseCallback);
    }

    public void testPostDlogin2() {
        Map<String, String> hashMap1 = new LinkedHashMap<>();
        hashMap1.put("passport", passport);
        hashMap1.put("password", password);

        Map<String, String> hashMap2 = new LinkedHashMap<>();
        hashMap2.put("Host", "my.12301.cc");//
        hashMap2.put("Connection", "keep-alive");
        hashMap2.put("Accept", "application/json, text/javascript, */*; q=0.01");
        hashMap2.put("Origin", "http://my.12301.cc");//
        hashMap2.put("X-Requested-With", "XMLHttpRequest");
        hashMap2.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400");
        hashMap2.put("Content-Type", "application/x-www-form-urlencoded; charset=ISO8859-1");
        hashMap2.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        hashMap2.put("Referer", "http://my.12301.cc/");//
        hashMap2.put("Accept-Encoding", "gzip, deflate");//
        hashMap2.put("Accept-Language", "zh-CN,zh;q=0.8");
        hashMap2.put("Cookie", cookie);//

        //登录接口Cookie需要session id  passport=cs033
        String url = "http://my.12301.cc/dlogin.php";

        HttpCallback responseCallback = new HttpCallback() {
            @Override
            public void onFailure(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response, Headers headers, String restlt) {
                System.out.println("......onResponse:" + response);

                if (!response.isSuccessful()) {
                    System.out.println("response3......3.....Unexpected code " + response);
                    return;
                }

                for (int i = 0; i < headers.size(); i++) {
                    System.out.println(".....response3.Headers:" + headers.name(i) + ": " + headers.value(i));
                }

                try {
                    System.out.println(".........response:" + response);
                    System.out.println(".........restlt:" + restlt);
//                    LoginModel11.TerminalBean terminalBean = new Gson().fromJson(restlt, LoginModel11.TerminalBean.class);
//                    System.out.println(".........terminalBean:" + new Gson().toJson(terminalBean));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };

        postRequest(hashMap1, hashMap2, url, responseCallback);
    }

    public void testPostDlogin() {
        Map<String, String> hashMap1 = new LinkedHashMap<>();
        hashMap1.put("passport", passport);
        hashMap1.put("password", password);

        Map<String, String> hashMap2 = new LinkedHashMap<>();
        hashMap2.put("Host", "my.12301.cc");//
        hashMap2.put("Connection", "keep-alive");
        hashMap2.put("Accept", "application/json, text/javascript, */*; q=0.01");
        hashMap2.put("Origin", "http://my.12301.cc");//
        hashMap2.put("X-Requested-With", "XMLHttpRequest");
        hashMap2.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.104 Safari/537.36 Core/1.53.4033.400 QQBrowser/9.6.12624.400");
        hashMap2.put("Content-Type", "application/x-www-form-urlencoded; charset=ISO8859-1");
        hashMap2.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        hashMap2.put("Referer", "http://my.12301.cc/");//
        hashMap2.put("Accept-Encoding", "gzip, deflate");//
        hashMap2.put("Accept-Language", "zh-CN,zh;q=0.8");
        hashMap2.put("Cookie", cookie);//

        //登录接口Cookie需要session id  passport=cs033
        String url = "http://my.12301.cc/dlogin.php";

// data=%7B%22check%22%3A1%2C%22salerid%22%3A%22922190%22%2C%22ordernum%22%3A%2222973867%22%2C%22check_method%22%3A%220%22%2C%22list%22%3A%7B%2222973867%22%3A%221%22%7D%2C%22rtime%22%3A%22%22%7D

        HttpCallback responseCallback = new HttpCallback() {
            @Override
            public void onFailure(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response, Headers headers, String restlt) {
                System.out.println("......onResponse:" + response);

                if (!response.isSuccessful()) {
                    System.out.println("response3......3.....Unexpected code " + response);
                    return;
                }

                for (int i = 0; i < headers.size(); i++) {
                    System.out.println(".....response3.Headers:" + headers.name(i) + ": " + headers.value(i));
                }

                try {
                    System.out.println(".........response:" + response);
                    System.out.println(".........restlt:" + restlt);
//                    LoginModel11.TerminalBean terminalBean = new Gson().fromJson(restlt, LoginModel11.TerminalBean.class);
//                    System.out.println(".........terminalBean:" + new Gson().toJson(terminalBean));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        };

        postRequest(hashMap1, hashMap2, url, responseCallback);
    }

    public static void getRequest(final Map<String, String> requestHeaders
            , final String url, final HttpCallback httpCallback) {

        Runnable target = new Runnable() {
            @Override
            public void run() {

                OkHttpClient client = new OkHttpClient();
                Request.Builder builder = new Request.Builder().url(url);

                if (requestHeaders != null) {
                    Set<Map.Entry<String, String>> entrySet = requestHeaders.entrySet();
                    for (Map.Entry<String, String> entry : entrySet) {
                        if (entry == null) continue;
                        builder.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                Request request = builder.build();

                Headers headers = request.headers();
                Set<String> names = headers.names();
                for (String n : names)
                    System.out.println("request header:" + n + ":" + headers.get(n));

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (httpCallback != null) {
                            httpCallback.onFailure(call, e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            if (httpCallback != null) {
                                Headers headers = response.headers();
                                ResponseBody body = response.body();
                                String string = new String(body.string().getBytes("iso8859-1"), "utf-8");
                                httpCallback.onResponse(call, response, headers, string);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (httpCallback != null) {
                                httpCallback.onFailure(call, e);
                            }
                        }
                    }
                });
            }
        };
        new Thread(target).start();
    }

    public static void postRequest(final Map<String, String> requestHeader
            , final Map<String, String> formBody
            , final String url, final HttpCallback httpCallback) {

        Runnable target = new Runnable() {
            @Override
            public void run() {

                FormBody.Builder formBuilder = new FormBody.Builder();

                if (formBody != null) {
                    Set<Map.Entry<String, String>> entrySet = formBody.entrySet();
                    for (Map.Entry<String, String> entry : entrySet) {
                        if (entry == null) continue;
                        formBuilder.add(entry.getKey(), entry.getValue());//设置参数名称和参数值
                    }
                }

                OkHttpClient okHttpClient = new OkHttpClient();
                //Form表单格式的参数传递
                final FormBody formBody = formBuilder.build();
                Request.Builder requestBuilder = new Request
                        .Builder()
                        .post(formBody)//Post请求的参数传递，此处是和Get请求相比，多出的一句代码
                        .url(url);

                if (requestHeader != null) {
                    Set<Map.Entry<String, String>> entrySet = requestHeader.entrySet();
                    for (Map.Entry<String, String> entry : entrySet) {
                        if (entry == null) continue;
                        requestBuilder.addHeader(entry.getKey(), entry.getValue());
                    }
                }
                Request request = requestBuilder.build();

                FormBody body = (FormBody) request.body();
                int size = body.size();
                for (int i = 0; i < size; i++) {
                    String name = body.name(i);
                    String value = body.value(i);
                    System.out.println("....FormBody:" + name + ":" + value);
                }

                Set<String> names = request.headers().names();
                for (String n : names)
                    System.out.println("...headers...." + n + ":" + request.headers().get(n));

                Callback callback = new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (httpCallback != null) {
                            httpCallback.onFailure(call, e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        ResponseBody body = null;
                        try {
                            if (httpCallback != null) {
                                body = response.body();
                                System.out.println("...body:" + body);
//                                System.out.println("...body:" + new Gson().toJson(body));

                                String string = body.string();
                                System.out.println("...string:" + string);
                                Headers headers1 = response.headers();
                                httpCallback.onResponse(call, response, headers1, string);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (httpCallback != null) {
                                httpCallback.onFailure(call, e);
                            }
                        } finally {
                            try {
                                if (body != null)
                                    body.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                okHttpClient.newCall(request).enqueue(callback);
            }
        };
        new Thread(target).start();
    }

    public static class HttpCallback {

        public void onFailure(Call call, Exception exception) {
        }

        public void onResponse(Call call, Response response, Headers headers, String result) {
        }
    }


    /**
     * 下载文件
     *
     * @param url 文件url
     */
    public static void downloadFile(String url
            , final StatusCallback callBack) {

        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
                failedCallBack("下载失败", callBack);
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                try {
                    long total = response.body().contentLength();
                    // Log.e(TAG, "total------>" + total);
                    SystemUtil.println("total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    StringBuilder buffer = new StringBuilder();
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        SystemUtil.println("current------>" + current);
                        buffer.append(new String(buf, 0, len));
                    }
                    successCallBack(buffer.toString(), callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                    failedCallBack("下载失败", callBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            private void failedCallBack(String result, StatusCallback callBack) {
                if (callBack != null) {
                    callBack.result(-1, result);
                }
            }

            private void successCallBack(String file, StatusCallback callBack) {
                if (callBack != null) {
                    callBack.success(file);
                }
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url         文件url
     * @param destFileDir 存储目标目录
     */
    public static void downloadFile(String url, final String destFileDir
            , String fileName, final StatusCallback1<File> callBack) {

        File fileDir = new File(destFileDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        final File file = new File(destFileDir, fileName);
        if (file.exists()) {
            file.delete();
        }

        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
                failedCallBack("下载失败", callBack);
            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    // Log.e(TAG, "total------>" + total);
                    System.out.println("total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    StringBuilder buffer = new StringBuilder();
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        //  Log.e(TAG, "current------>" + current);
                        System.out.println("current------>" + current);
                        //  progressCallBack(total, current, callBack);
                        buffer.append(new String(buf, 0, len));
                    }
                    fos.flush();
                    successCallBack(file, callBack);

                    System.out.println("------>buffer:" + buffer.toString());


                } catch (IOException e) {
                    e.printStackTrace();
                    failedCallBack("下载失败", callBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void failedCallBack(String result, StatusCallback1<File> callBack) {
                if (callBack != null) {
                    callBack.result(-1, result);
                }
            }

            private void successCallBack(File file, StatusCallback1<File> callBack) {
                if (callBack != null) {
                    callBack.success(file);
                }
            }
        });
    }

}

