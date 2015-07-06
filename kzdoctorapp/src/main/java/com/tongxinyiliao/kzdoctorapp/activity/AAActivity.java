package com.tongxinyiliao.kzdoctorapp.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyActivity;

/**
 * Created by ZSL on 2015/4/21.
 */
public class AAActivity extends BaseMyActivity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.aa);
        initView();
        initEvent();
        initData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
