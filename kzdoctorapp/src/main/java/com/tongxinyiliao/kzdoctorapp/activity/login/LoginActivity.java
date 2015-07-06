package com.tongxinyiliao.kzdoctorapp.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyActivity;

/**
 * Created by ZSL on 2015/4/21.
 */
public class LoginActivity extends BaseMyActivity {

    EditText et_phone;
    Button bt_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
        initData();
    }

    protected void initView() {
        et_phone = (EditText) findViewById(R.id.login_et_phone);
        bt_next = (Button) findViewById(R.id.login_bt_next);
    }

    protected void initEvent() {
        bt_next.setOnClickListener(this);
    }

    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_bt_next) {
            Intent intent=new Intent(LoginActivity.this,ResetPwdActivity.class);
            startActivity(intent);
        }
    }
}
