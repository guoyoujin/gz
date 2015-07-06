package com.tongxinyiliao.kzdoctorapp.myadapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.entity.MessageEntity;
import com.tongxinyiliao.kzdoctorapp.utils.ViewHolder;

import java.util.List;

/**
 * Created by ZSL on 2015/4/28.
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageEntity> list;

    public MessageAdapter(Context context,List<MessageEntity> list) {
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
        ViewHolder viewHolder=ViewHolder.get(context,convertView,parent,R.layout.message_item,position);
        TextView name=viewHolder.getView(R.id.message_item_name);
        TextView msg=viewHolder.getView(R.id.message_item_msg);
        TextView time=viewHolder.getView(R.id.message_item_time);

        MessageEntity messageEntity=list.get(position);

        name.setText(messageEntity.getName());
        msg.setText(messageEntity.getMessage());
        time.setText(messageEntity.getTime());

        return viewHolder.getmConvertView();
    }
}
