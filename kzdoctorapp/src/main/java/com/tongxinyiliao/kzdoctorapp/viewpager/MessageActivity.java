package com.tongxinyiliao.kzdoctorapp.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyFragment;
import com.tongxinyiliao.kzdoctorapp.entity.MessageEntity;
import com.tongxinyiliao.kzdoctorapp.myadapters.MessageAdapter;
import com.tongxinyiliao.kzdoctorapp.ui.RefershUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MessageActivity extends BaseMyFragment {
    //下拉刷新
    protected PtrFrameLayout mPtrFrameLayout;
    //下拉刷新

    ListView lv_msg;

    List<MessageEntity> messageEntities;
    MessageAdapter messageAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_message, container, false);
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
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.message_material_style_ptr_frame);
        lv_msg= (ListView) view.findViewById(R.id.message_lv_msg);
        initRefersh();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        messageEntities=new ArrayList<MessageEntity>();
        for (int i=0;i<100;i++){
            MessageEntity entity=new MessageEntity("2015.04.28","该回家了","小明");
            messageEntities.add(entity);
        }
        messageAdapter=new MessageAdapter(getActivity(),messageEntities);
        lv_msg.setAdapter(messageAdapter);


    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 下拉刷新
     */
    private void initRefersh() {
        RefershUtils.createRefersh(mPtrFrameLayout, getActivity());
        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                addMessage();
                frame.refreshComplete();
            }
        });

    }


    //测试添加
    public void addMessage(){
        for (int i=100;i<120;i++){
            MessageEntity entity=new MessageEntity("2015.04.28","该回家了"+i,"小明"+i);
            messageEntities.add(entity);
        }
        messageAdapter.notifyDataSetChanged();
    }
}
