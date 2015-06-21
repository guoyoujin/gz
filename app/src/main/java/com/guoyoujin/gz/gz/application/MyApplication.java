package com.guoyoujin.gz.gz.application;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by guoyoujin on 15/6/21.
 */
public class MyApplication extends Application{

    public static final String TAG="GuoYouJin";
    public void onCreate() {
        super.onCreate();
        Logger.init(TAG)
                .setMethodCount(3)
                .hideThreadInfo()
                .setLogLevel(LogLevel.NONE)
                .setMethodOffset(2);
        initImageLoader(getApplicationContext());
    }

    public void initImageLoader(Context context){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//				.writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
