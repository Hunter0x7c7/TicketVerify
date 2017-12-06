package com.ileiju.ticketverify.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.ileiju.ticketverify.base.BaseApplication;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建日期： 2017/2/28 16:50
 * 描    述：网络工具类
 */
public class NetworkUtil {


    /**
     * 检查当前网络是否可用
     *
     * @return true可用 false不可用
     */
    public static boolean isNetworkAvailable() {
        Context context = BaseApplication.getContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (!ListUtil.isEmpty(networkInfo)) {
                for (NetworkInfo network : networkInfo) {
//                    System.out.println(i + "===状态===" + networkInfo[i].getState()+ " ===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (network.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
            SystemUtil.println("No network available");
        }
        return false;
    }


    /**
     * 获取正在连接的Wifi的名称
     *
     * @return 返回Wifi的名称，未连接时返回空
     */
    public static String getWifiSSID() {
        String wifiSSID = "";
        WifiManager wifiMgr = (WifiManager) BaseApplication.getContext().getSystemService(Context.WIFI_SERVICE);

//        int wifiState = wifiMgr.getWifiState();
        if (isWifiEnabled()) {
            WifiInfo info = wifiMgr.getConnectionInfo();
            info.getRssi();
            wifiSSID = info != null ? info.getSSID() : wifiSSID;
            String reg = "\".+\"";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(wifiSSID);
            boolean isPattern = matcher.matches();

            if (!TextUtils.isEmpty(wifiSSID) && isPattern) {
                wifiSSID = wifiSSID.substring(1, wifiSSID.length() - 1);
            }
        }
        return wifiSSID;
    }

    /**
     * 获取正在连接Wifi的信号强度
     *
     * @return 返回Wifi的信号强度
     */
    public static int getWifiRSSI() {
        int wifiRSSI = -1000;
        WifiManager wifiMgr = (WifiManager) BaseApplication.getContext().getSystemService(Context.WIFI_SERVICE);
        if (isWifiEnabled()) {
            WifiInfo info = wifiMgr.getConnectionInfo();
            wifiRSSI = info.getRssi();
        }
        return wifiRSSI;
    }

    /**
     * 获取Wifi的IP地址
     *
     * @return 返回IP地址，如:192.168.1.1
     */
    public static String getWifiIp() {
        Context myContext = BaseApplication.getContext();
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        if (isWifiEnabled()) {
            int ipAsInt = wifiMgr.getConnectionInfo().getIpAddress();
            if (ipAsInt == 0) {
                return null;
            } else {
                return intToIp(ipAsInt);
            }
        } else {
            return null;
        }
    }

    /**
     * 获取Wifi的IP地址
     *
     * @return 返回IP地址，如:192.168.1.1
     */
    public static String getWifiIp2() {
        String ip = null;
        WifiManager wifiManager = (WifiManager) BaseApplication.getContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && wifiInfo.getNetworkId() > -1) {
            int i = wifiInfo.getIpAddress();
            ip = String.format("%d.%d.%d.%d", i & 0xff, i >> 8 & 0xff, i >> 16 & 0xff, i >> 24 & 0xff);
        }
        return ip;
    }

    /**
     * 获取Wifi的IP地址当前网段的广播地址
     *
     * @return 返回IP地址，如:192.168.1.255
     */
    public static String getBroadcastAddress() {
        String ip = null;
        WifiManager wifiManager = (WifiManager) BaseApplication.getContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && wifiInfo.getNetworkId() > -1) {
            int i = wifiInfo.getIpAddress();
            ip = String.format("%d.%d.%d.%d", i & 0xff, i >> 8 & 0xff, i >> 16 & 0xff, 255);
        }
        return ip;
    }

    /**
     * 获取Wifi是否可用
     *
     * @return true为可用，false为不可用
     */
    public static boolean isWifiEnabled() {
        Context myContext = BaseApplication.getContext();
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            ConnectivityManager connManager = (ConnectivityManager) myContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiInfo = connManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiInfo.isConnected();
        } else {
            return false;
        }
    }

    /**
     * 把int->ip地址
     *
     * @param ipInt
     * @return String
     */
    public static String intToIp(int ipInt) {
        return new StringBuilder().append((ipInt & 0xff)).append('.')
                .append((ipInt >> 8) & 0xff).append('.').append(
                        (ipInt >> 16) & 0xff).append('.').append(((ipInt >> 24) & 0xff))
                .toString();
    }

    /**
     * 获取Wifi密码，需要Root权限，否则获取失败
     *
     * @param wifiName Wifi名称
     * @return 返回Wifi密码
     */
    public static String getWifiPassword(String wifiName) {
        String wifiPass = "";
        try {
            List<WifiInfos> wifiInfos = Read();
            for (WifiInfos w : wifiInfos) {
//                System.out.println("wifi:" + w.Ssid + ";pass:" + w.Password);
                if (w.Ssid.equals(wifiName)) {
                    wifiPass = w.Password;
                    SystemUtil.println("wifi:" + w.Ssid + ";pass:" + w.Password);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wifiPass;
    }

    /**
     * 获取所有保存的Wifi信息，需要Root权限，否则获取失败
     *
     * @return 返回Wifi列表
     * @throws Exception 抛出异常
     */
    public static List<WifiInfos> Read() throws Exception {
        List<WifiInfos> wifiInfos = new ArrayList<>();

        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        StringBuffer wifiConf = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getInputStream());
            dataOutputStream
                    .writeBytes("cat /data/misc/wifi/wpa_supplicant.conf\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    dataInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                wifiConf.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (Exception e) {
//            e.printStackTrace();
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                if (process != null) {
                    process.destroy();
                }
            } catch (Exception e) {
                throw e;
            }
        }

        Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}", Pattern.DOTALL);
        Matcher networkMatcher = network.matcher(wifiConf.toString());
        while (networkMatcher.find()) {
            String networkBlock = networkMatcher.group();
            Pattern ssid = Pattern.compile("ssid=\"([^\"]+)\"");
            Matcher ssidMatcher = ssid.matcher(networkBlock);

            if (ssidMatcher.find()) {
                WifiInfos wifiInfo = new WifiInfos();
                wifiInfo.Ssid = ssidMatcher.group(1);
                Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");
                Matcher pskMatcher = psk.matcher(networkBlock);
                if (pskMatcher.find()) {
                    wifiInfo.Password = pskMatcher.group(1);
                } else {
                    wifiInfo.Password = "无密码";
                }
                wifiInfos.add(wifiInfo);
            }

        }

        return wifiInfos;
    }

    /**
     * Wifi信息类
     */
    public static class WifiInfos {
        public String Ssid = "";
        public String Password = "";
    }
}
