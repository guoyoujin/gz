package com.tongxinyiliao.kzdoctorapp.base.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.tongxinyiliao.kzdoctorapp.config.AppConfig;

/**
 * Created by ZSL on 2015/4/21.
 */

public abstract class BaseMyFragmentActivity extends FragmentActivity implements View.OnClickListener{
//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        //设置严苛模式
//        AppConfig.getInstance().setStrictMode();
//        super.onCreate(savedInstanceState, persistentState);
//    }

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void initData();

    /**
     * 显示Toast
     * @param content
     */
    public void showToast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }
}
