package com.tongxinyiliao.kzdoctorapp.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyActivity;

/**
 * Created by ZSL on 2015/4/21.
 */
public class ResetPwdActivity extends BaseMyActivity {


    EditText et_authcode,et_resetpwd;
    Button bt_getauthcode,bt_reset;


    Handler handler;
    int time=60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        initView();
        initEvent();
        initData();
    }

    protected void initView() {
        et_authcode = (EditText) findViewById(R.id.login_et_authcode);
        et_resetpwd = (EditText) findViewById(R.id.login_et_resetpwd);
        bt_reset = (Button) findViewById(R.id.login_bt_reset);
        bt_getauthcode = (Button) findViewById(R.id.login_bt_getauthcode);
    }

    protected void initEvent() {
        bt_getauthcode.setOnClickListener(this);
        bt_reset.setOnClickListener(this);
    }

    protected void initData() {
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                int id= msg.what;
                if (id==1){
                    if (time!=0) {
                        bt_getauthcode.setText((time--) + "秒");
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }else{
                        bt_getauthcode.setEnabled(true);
                        bt_getauthcode.setBackgroundResource(R.drawable.button_bg_lv);
                        bt_getauthcode.setText("重新获取");
                        time=120;
                    }
                }
            }
        };

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_bt_getauthcode) {
            bt_getauthcode.setEnabled(false);
            bt_getauthcode.setBackgroundResource(R.drawable.button_bg_gary);
            handler.sendEmptyMessage(1);
        }else if(id == R.id.login_bt_reset){
            Intent intent=new Intent(ResetPwdActivity.this,RegisterActivity.class);
            startActivity(intent);
        }
    }
}
