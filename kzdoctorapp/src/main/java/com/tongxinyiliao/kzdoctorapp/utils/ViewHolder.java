package com.tongxinyiliao.kzdoctorapp.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZSL on 2015/4/28.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews=new SparseArray<View>();
        this.mPosition=position;
        mConvertView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);
    }

    /**
     * 获取到ViewHolder
     * @param context
     * @param converView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View converView, ViewGroup parent, int layoutId, int position) {
        if (converView==null) {
            return new ViewHolder(context,parent,layoutId,position);
        } else {
            ViewHolder viewHolder=(ViewHolder) converView.getTag();
            viewHolder.mPosition=position;
            return viewHolder;
        }
    }

    //获取到childView
    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if (view == null){
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }
}
