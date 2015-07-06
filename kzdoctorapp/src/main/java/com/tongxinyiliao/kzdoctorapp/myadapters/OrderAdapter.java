package com.tongxinyiliao.kzdoctorapp.myadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.entity.OrderEntity;
import com.tongxinyiliao.kzdoctorapp.utils.ViewHolder;

import java.util.List;

/**
 * Created by ZSL on 2015/4/28.
 */
public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderEntity> list;

    public OrderAdapter(Context context, List<OrderEntity> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=ViewHolder.get(context,convertView,parent,R.layout.order_item,position);
        TextView number=viewHolder.getView(R.id.order_item_number);
        TextView status=viewHolder.getView(R.id.order_item_status);
        TextView name=viewHolder.getView(R.id.order_item_patient_name);
        TextView project=viewHolder.getView(R.id.order_item_patient_project);
        TextView jine=viewHolder.getView(R.id.order_item_patient_jine);

        OrderEntity orderEntity=list.get(position);
        number.setText("订单号"+orderEntity.getNumber());
        status.setText(orderEntity.getStatus());
        name.setText("患者姓名:"+orderEntity.getName());
        project.setText("检查项目:"+orderEntity.getProject());
        jine.setText(orderEntity.getJine()+"￥");
        return viewHolder.getmConvertView();
    }
}
