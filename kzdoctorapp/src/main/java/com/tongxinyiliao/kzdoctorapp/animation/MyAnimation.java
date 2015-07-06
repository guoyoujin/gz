package com.tongxinyiliao.kzdoctorapp.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.view.View;

/**
 * Created by ZSL on 2015/4/23.
 */
public class MyAnimation {

    /**
     * 设置首页小按钮动画
     * @param view
     */
    public static void setanimation(View view){
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0.8f, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0.8f, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhY, pvhZ).setDuration(300).start();
    }


    /**
     * 设置缩放动画给定缩放比例值
     * @param view
     * @param scale
     */
    public static void setanimation(View view,float scale){
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                scale, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                scale, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhY, pvhZ).setDuration(300).start();
    }
}
