package com.tongxinyiliao.kzdoctorapp.base.activity;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.config.AppConfig;

public abstract class BaseMyActivity extends Activity implements View.OnClickListener {
    private TextView tv_title;
    private TextView btn_left, btn_right;
    private LinearLayout linout_title_bar,centre_lin_layout;
    private LinearLayout ly_content,net_status_bar;
    private ImageView img_title_group;
    // 内容区域的布局
    private View contentView;
    public Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置严苛模式
        AppConfig.getInstance().setStrictMode();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_title);

        centre_lin_layout=(LinearLayout) findViewById(R.id.centre_lin_layout);
        img_title_group=(ImageView)findViewById(R.id.img_title_group);
        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_left = (TextView) findViewById(R.id.btn_left);
        btn_right = (TextView)findViewById(R.id.btn_right);
        linout_title_bar=(LinearLayout) findViewById(R.id.linout_title_bar);
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        if(context==null){
            context=BaseMyActivity.this;
        }
    }

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void initData();


    public void setContentLayout(int resId) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(resId, null);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
        contentView.setLayoutParams(layoutParams);
        contentView.setBackgroundDrawable(null);
        if (null != ly_content) {
            ly_content.addView(contentView);
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStart() {

        super.onStart();

    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    /***
     * 设置内容区域
     *
     * @param view
     *            View对象
     */
    public void setContentLayout(View view) {
        if (null != ly_content) {
            ly_content.addView(view);
        }
    }

    /**
     * 得到内容的View
     *
     * @return
     */
    public View getLyContentView() {

        return contentView;
    }

    /**
     * 得到左边的按钮
     *
     * @return
     */
    public TextView getbtn_left() {
        return btn_left;
    }

    /**
     * 得到title
     *
     * @return
     */
    public TextView getTitle_tv() {
        return tv_title;
    }
    /**
     * 得到右边的按钮
     *
     * @return
     */
    public TextView getbtn_right() {
        return btn_right;
    }

    /**
     * 得到linear
     *
     * @return
     */
    public LinearLayout get_title_linearLayout(){
        return centre_lin_layout;
    }

    /**
     * 得到Image
     *
     * @return
     */
    public ImageView get_title_Image(){
        return img_title_group;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {

        if (null != tv_title) {
            tv_title.setText(title);
        }

    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(int resId) {
        tv_title.setText(getString(resId));
    }

    /**
     * 设置左边按钮的图片资源
     *
     * @param resId
     */
    public void setbtn_leftRes(int resId) {
        if (null != btn_left) {
            btn_left.setBackgroundResource(resId);
        }

    }

    /**
     * 设置左边按钮的图片资源
     *
     * @param
     */
    @SuppressWarnings("deprecation")
    public void setbtn_leftRes(Drawable drawable) {
        if (null != btn_left) {
            btn_left.setBackgroundDrawable(drawable);
        }

    }

    /**
     * 设置右边按钮的图片资源
     *
     * @param resId
     */
    public void setbtn_rightRes(int resId) {
        if (null != btn_right) {
            btn_right.setBackgroundResource(resId);
        }
    }

    /**
     * 设置右边按钮的图片资源
     *
     * @param drawable
     */
    @SuppressWarnings("deprecation")
    public void setbtn_rightRes(Drawable drawable) {
        if (null != btn_right) {
            btn_right.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 隐藏上方的标题栏
     */
    public void hideTitleView() {

        if (null != linout_title_bar) {
            linout_title_bar.setVisibility(View.GONE);
        }
    }
    /**
     * 显示上方的标题栏
     */
    public void showTitleView() {

        if (null != linout_title_bar) {
            linout_title_bar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏左边的按钮
     */
    public void hidebtn_left() {

        if (null != btn_left) {
            btn_left.setVisibility(View.GONE);
        }

    }

    /***
     * 隐藏右边的按钮
     */
    public void hidebtn_right() {
        if (null != btn_right) {
            btn_right.setVisibility(View.GONE);
        }

    }


    /**
     * 显示Toast
     * @param content
     */
    public void showToast(String content){
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

}
