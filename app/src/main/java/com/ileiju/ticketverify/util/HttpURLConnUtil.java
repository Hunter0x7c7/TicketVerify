package com.ileiju.ticketverify.util;

import com.ileiju.ticketverify.interfaces.HttpCallback;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * Created by hunter on 17/12/9.
 * 网络工具类
 */
public class HttpURLConnUtil {

    private final static String ENCODING = "UTF-8";
    private final static String GZIP_CODING = "gzip";


    public static void get(final Map<String, String> headers, final String url
            , final HttpCallback httpCallback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) new URL(url).openConnection();

                    conn.setConnectTimeout(5 * 1000);
                    conn.setDoOutput(true);
                    //get方式提交
                    conn.setRequestMethod("GET");
                    //凭借请求头文件
                    if (headers != null) {
                        Set<Map.Entry<String, String>> entrySet = headers.entrySet();
                        for (Map.Entry<String, String> entry : entrySet) {
                            conn.setRequestProperty(entry.getKey(), entry.getValue());
                        }
                    }
                    Map<String, List<String>> requestHeaders = conn.getHeaderFields();
                    Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();
                    for (Map.Entry<String, List<String>> entrie : entries)
                        System.out.println("get request header:" + entrie.getKey() + ":" + entrie.getValue());


                    conn.connect();

                    String encoding = conn.getContentEncoding();
                    Map<String, List<String>> resultHeaders = conn.getHeaderFields();
                    if (resultHeaders != null) {
                        Set<Map.Entry<String, List<String>>> entrySet = resultHeaders.entrySet();
                        for (Map.Entry<String, List<String>> entry : entrySet) {
                            System.out.println("get result header:" + entry.getKey() + ":" + entry.getValue());
                        }
                    }

                    InputStream inputStream = conn.getInputStream();
                    String result = readStream(inputStream, encoding);

                    if (httpCallback != null) {
                        httpCallback.onSucceed(resultHeaders, result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    if (httpCallback != null) {
                        httpCallback.onFailure(e);
                    }

                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                }
            }
        }).start();

    }

    /**
     * POST请求获取数据
     */
    public static void post(final Map<String, String> headers, final Map<String, String> formBody
            , final String url, final HttpCallback httpCallback) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                PrintWriter printWriter = null;
                try {
                    conn = (HttpURLConnection) new URL(url).openConnection();
                    // conn.setReadTimeout(2000);//读取超时 单位毫秒
                    conn.setConnectTimeout(5 * 1000);//连接超时 单位毫秒
                    conn.setDoOutput(true);// 发送POST请求必须设置
                    conn.setDoInput(true);// 发送POST请求必须设置
                    conn.setUseCaches(false);  //不允许缓存
                    conn.setRequestMethod("POST");// 提交模式

                    //凭借请求头文件
                    if (headers != null) {
                        Set<Map.Entry<String, String>> entrySet = headers.entrySet();
                        for (Map.Entry<String, String> entry : entrySet) {
                            conn.setRequestProperty(entry.getKey(), entry.getValue());
                        }
                    }

                    Map<String, List<String>> requestHeaders = conn.getRequestProperties();
                    Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();
                    for (Map.Entry<String, List<String>> entry : entries)
                        System.out.println("post request header:" + entry.getKey() + ":" + entry.getValue());

                    // 获取URLConnection对象对应的输出流 getOutputStream()会自动connect();
                    printWriter = new PrintWriter(conn.getOutputStream());

                    StringBuilder sb = new StringBuilder();
                    if (formBody != null) {
                        Set<Map.Entry<String, String>> entryBody = formBody.entrySet();
                        for (Map.Entry<String, String> entry : entryBody) {
                            if (sb.length() > 0) {
                                sb.append("&");
                            }
                            sb.append(entry.getKey());
                            sb.append("=");
                            sb.append(entry.getValue());
                        }
                    }

                    // 发送请求参数
                    printWriter.write(sb.toString());//post的参数 xx=xx&yy=yy

                    // flush输出流的缓冲
                    printWriter.flush();
                    //开始获取数据
                    String encoding = conn.getContentEncoding();
                    Map<String, List<String>> resultHeaders = conn.getHeaderFields();
                    if (resultHeaders != null) {
                        Set<Map.Entry<String, List<String>>> entrySet = resultHeaders.entrySet();
                        for (Map.Entry<String, List<String>> entry : entrySet) {
                            System.out.println("post result header:" + entry.getKey() + ":" + entry.getValue());
                        }
                    }

                    String result = readStream(conn.getInputStream(), encoding);
                    if (httpCallback != null) {
                        httpCallback.onSucceed(resultHeaders, result);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (httpCallback != null) httpCallback.onFailure(e);

                } finally {
                    if (printWriter != null) printWriter.close();

                    if (conn != null) conn.disconnect();
                }
            }
        }).start();
    }


    /**
     * 读取将InputStream中的字节读以字符的形式取到字符串中，如果encoding是gzip，那么需要先有GZIPInputStream进行封装
     *
     * @param inputStream InputStream字节流
     * @param encoding    编码格式
     * @return String类型的形式
     */
    private static String readStream(InputStream inputStream, String encoding) {

        InputStreamReader inputStreamReader = null;
        GZIPInputStream gZIPInputStream = null;

        StringBuilder buffer = new StringBuilder();
        try {
            if (GZIP_CODING.equalsIgnoreCase(encoding)) {
                gZIPInputStream = new GZIPInputStream(inputStream);
                inputStreamReader = new InputStreamReader(gZIPInputStream, ENCODING);
            } else {
                inputStreamReader = new InputStreamReader(inputStream, ENCODING);
            }

            char[] c = new char[1024];

            int lenI;
            while ((lenI = inputStreamReader.read(c)) != -1) {
                buffer.append(new String(c, 0, lenI));
            }
            System.out.println("......buffer:" + buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (gZIPInputStream != null) {
                    gZIPInputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return buffer.toString();
    }


}
