package com.guoyoujin.gz.gz.application;

import android.app.Application;
import android.content.Context;

import com.guoyoujin.gz.gz.book.date.FinalDate;
import com.guoyoujin.gz.gz.book.helper.BookDB;
import com.guoyoujin.gz.gz.utils.NetUtil;
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
    public static BookDB bookDB;
    private static Context context;
    private static Application mApplication;
    public static int mNetWorkState;
    public static synchronized Application getInstance() {
        return mApplication;
    }
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mNetWorkState = NetUtil.getNetworkState(this);
        Logger.init(TAG)
                .setMethodCount(3)
                .hideThreadInfo()
                .setLogLevel(LogLevel.NONE)
                .setMethodOffset(2);
        initImageLoader(getApplicationContext());
        context = getApplicationContext();
        initDateBase();
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
    private static void initDateBase() {
        bookDB = new BookDB(context, FinalDate.DATABASE_TABKE);
    }

}
