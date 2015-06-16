package com.guoyoujin.gz.gz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.guoyoujin.gz.gz.activity.ContentActivity;
import com.guoyoujin.gz.gz.producttour.ProductTourActivity;


public class SplashActivity extends Activity {

    private static final int GO_HOME = 100;
    private static final int GO_GUIDE = 200;
    boolean isFirst = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //请注意这里没有布局文件，直接在activity的背景里面设置
        init();
    }

    private void init() {
        SharedPreferences preferences = getSharedPreferences("first_pref",MODE_PRIVATE);
        isFirst = preferences.getBoolean("isFirst", true);
        if (false) {
            mHandler.sendEmptyMessageDelayed(GO_HOME, 2800);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, 2800);
        }
    }

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, ContentActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        this.finish();
    }

    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, ProductTourActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        this.finish();
    }
}
