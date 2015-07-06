package com.tongxinyiliao.kzdoctorapp.activity.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.litesuits.common.utils.BitmapUtil;
import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyActivity;
import com.tongxinyiliao.kzdoctorapp.config.AppCode;

import java.io.File;

/**
 * Created by ZSL on 2015/4/21.
 */
public class AuthActivity extends BaseMyActivity {
    ImageView iv_photo;
    Button bt_authset;
    MaterialDialog materialDialog;

    public final Uri Urifrom = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    public final Uri Urito = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initView();
        initEvent();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_auth);
        initView();
        initEvent();
        initData();
    }

    @Override
    protected void initView() {
        iv_photo = (ImageView) findViewById(R.id.auth_iv_photo);
        bt_authset = (Button) findViewById(R.id.auth_bt_authset);
        materialDialog = new MaterialDialog.Builder(this)
                .title("添加照片")
                .items(new String[]{"拍照", "从相册选取"})
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {

                        materialDialog.cancel();
                        if (i == 0) {
                            Intent intent = BitmapUtil.buildCaptureIntent(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg")));
                            startActivityForResult(intent, AppCode.code_paizhao);
                            // TODO 添加照相加剪裁功能
                        } else {
                            Intent intent = BitmapUtil.buildGalleryPickIntent(Urito, 1, 1, 200, 200, true);
                            startActivityForResult(intent, AppCode.code_xiangce);
                        }
                    }
                })
                .build();
    }

    @Override
    protected void initEvent() {
        iv_photo.setOnClickListener(this);
        bt_authset.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.auth_iv_photo: {
                materialDialog.show();
                break;
            }
            case R.id.auth_bt_authset: {

                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0)
            return;
        // 拍照
        if (requestCode == AppCode.code_paizhao) {
            //设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
            Intent intent = BitmapUtil.buildImagePickIntent(Uri.fromFile(picture), Urito, 1, 1, 200, 200, true);
            startActivityForResult(intent, AppCode.code_jiancai);
        }
        if (data == null)
            return;

        // 读取相册缩放图片
        if (requestCode == AppCode.code_xiangce || requestCode == AppCode.code_jiancai) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                iv_photo.setImageBitmap(photo);
            }
        }

    }
}
