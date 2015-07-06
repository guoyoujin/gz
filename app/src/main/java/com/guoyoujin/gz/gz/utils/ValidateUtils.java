/*******************************************************************************
 *
 * Copyright (c) Weaver Info Tech Co. Ltd
 *
 * Utils
 *
 * app.util.Utils.java
 * TODO: File description or class description.
 *
 * @author: gao_chun
 * @since:  2014年9月26日
 * @version: 1.0.0
 *
 * @changeLogs:
 *     1.0.0: First created this class.
 *
 ******************************************************************************/
package com.guoyoujin.gz.gz.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 *@author gao_chun
 *
 */
public class ValidateUtils {

    /**
     * 判断手机号码
     * @param paramString
     * @return
     */
    public static boolean isMobileNO(String paramString){
        return Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,3,5-9]))\\d{8}$").matcher(paramString).matches();
    }

    /**
     * 判断邮编
     * @param paramString
     * @return
     */
    public static boolean isZipNO(String zipString){
        String str = "^[1-9][0-9]{5}$";
        return Pattern.compile(str).matcher(zipString).matches();
    }

    /**
     * 打电话
     * @param context
     * @param phone_num
     */
    public static void makePhoneCall(Context context, String phone_num) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (manager.getSimState()) {
            case TelephonyManager.SIM_STATE_READY:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_num));       //叫出呼叫程序
                //Intent phoneIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_num));     //直接打出去
                context.startActivity(phoneIntent);
                break;
            case TelephonyManager.SIM_STATE_ABSENT:
                Toast.makeText(context, "无SIM卡", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "SIM卡被锁定或未知状态", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    /**
     * 判断邮箱是否合法
     * @param email
     * @return
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    /**
     * 验证输入密码长度 (6-18位)
     * @param password
     * @return
     */
    public static boolean isPassword(String password){
        if (null == password || "".equals(password)) return false;
        Pattern p =  Pattern.compile("^\\d{6,18}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 获取WiFi Mac地址
     * @param context
     * @return
     */
    public static String getMacAddress(Context context){
        WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        return m_szWLANMAC;
    }


    /**
     * 获取imei
     * @param context
     * @return
     */
    public static String getImei(Context context){
        @SuppressWarnings("static-access")
		TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        return szImei;
    }


    /*获取当前应用的版本号*/
    public static int getVersionCode(Context context){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            // 不可能发生.
            // can't reach
            return 0;
        }
    }

    /**
     * 检查当前网络是否可用
     *
     * @param context
     * @return
     */

    public static boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null){
            return false;
        }else{
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0){
                for (int i = 0; i < networkInfo.length; i++){

                    System.out.println(i + "===网络状态===" + networkInfo[i].getState());
                    System.out.println(i + "===网络类型===" + networkInfo[i].getTypeName());

                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
