package com.tongxinyiliao.kzdoctorapp.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.activity.login.LoginActivity;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyFragment;

public class MeActivity extends BaseMyFragment {

    //initView
    Button bt_userlogin,bt_userreg,bt_auth;
    RelativeLayout rl_myInfo;


    //initData
    Intent intent=new Intent();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initEvent();
        initData();


    }

    @Override
    protected void initView() {
        View view = getView();
        rl_myInfo= (RelativeLayout) view.findViewById(R.id.aboutme_rl_myinfo);
//        bt_userlogin = (Button) view.findViewById(R.id.login_bt_userlogin);
//        bt_userreg= (Button) view.findViewById(R.id.login_bt_userreg);
//        bt_auth= (Button) view.findViewById(R.id.login_bt_auth);
    }

    @Override
    protected void initEvent() {
//        bt_userlogin.setOnClickListener(this);
//        bt_userreg.setOnClickListener(this);
//        bt_auth.setOnClickListener(this);
        rl_myInfo.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==R.id.aboutme_rl_myinfo) {
            intent.setClass(getActivity(), LoginActivity.class);
        }
// else if(id==R.id.login_bt_userreg){
//            intent.setClass(getActivity(), RegisterActivity.class);
//        }else if(id==R.id.login_bt_auth){
//            intent.setClass(getActivity(), AuthActivity.class);
//        }else if(id==R.id.titlebar_tv_left){
//            showToast("left");
//            return;
//        }
        startActivity(intent);
    }
}
