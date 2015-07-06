package com.tongxinyiliao.kzdoctorapp.base.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.tongxinyiliao.kzdoctorapp.config.AppConfig;

/**
 * Created by ZSL on 2015/4/21.
 */



@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseMyFragment extends Fragment implements View.OnClickListener{

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        //设置严苛模式
//        AppConfig.getInstance().setStrictMode();
//        super.onActivityCreated(savedInstanceState);
//    }

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void initData();

    /**
     * 显示Toast
     * @param content
     */
    public void showToast(String content){
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }
}
