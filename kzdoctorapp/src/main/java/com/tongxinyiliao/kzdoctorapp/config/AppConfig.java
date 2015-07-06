package com.tongxinyiliao.kzdoctorapp.config;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created by ZSL on 2015/4/21.
 */
public class AppConfig {

    //http://loopj.com/android-async-http/
    //服务器配置
    public static String host="kztest.txzs.org";

    private static  AppConfig appConfig;

    //单例模式
    public static AppConfig getInstance() {
        if (appConfig == null) {
            appConfig = new AppConfig();
        }
        return appConfig;
    }

    //是否开启严苛模式
    public final boolean isStrictMode = true;
    //app的Tag
    public final String AppTag = "KZDoctorApp";


    /**
     * 严苛模式
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setStrictMode() {
        if (isStrictMode) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
    }





}
