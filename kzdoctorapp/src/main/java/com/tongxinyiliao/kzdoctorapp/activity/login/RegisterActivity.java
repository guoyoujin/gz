package com.tongxinyiliao.kzdoctorapp.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyActivity;

/**
 * Created by ZSL on 2015/4/21.
 */
public class RegisterActivity extends BaseMyActivity {

    EditText et_phone,et_name,et_authcode,et_invitecode,et_pwd;
    Button bt_reg,bt_getauthcode;
    CheckBox cb_agreement;


    Handler handler;
    int time=60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
        initData();
    }



    protected void initView() {
        et_phone = (EditText) findViewById(R.id.register_et_phone);
        et_name = (EditText) findViewById(R.id.register_et_name);
        et_authcode = (EditText) findViewById(R.id.register_et_authcode);
        et_pwd = (EditText) findViewById(R.id.register_et_resetpwd);
        et_invitecode = (EditText) findViewById(R.id.register_et_invitecode);
        bt_reg = (Button) findViewById(R.id.register_bt_reg);
        bt_getauthcode = (Button) findViewById(R.id.register_bt_getauthcode);
        cb_agreement= (CheckBox) findViewById(R.id.register_cb_agreement);
        cb_agreement.setText(Html.fromHtml("<a>用户协议</a>"));
    }

    protected void initEvent() {
        bt_reg.setOnClickListener(this);
        bt_getauthcode.setOnClickListener(this);
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
        if (id == R.id.register_bt_reg) {
            Intent intent=new Intent(RegisterActivity.this,AuthActivity.class);
            startActivity(intent);
        }else if(id==R.id.register_bt_getauthcode){
            bt_getauthcode.setEnabled(false);
            bt_getauthcode.setBackgroundResource(R.drawable.button_bg_gary);
            handler.sendEmptyMessage(1);

        }
    }
}
