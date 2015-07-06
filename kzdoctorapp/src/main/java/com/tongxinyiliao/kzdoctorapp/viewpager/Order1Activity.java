package com.tongxinyiliao.kzdoctorapp.viewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyFragment;
import com.tongxinyiliao.kzdoctorapp.entity.OrderEntity;
import com.tongxinyiliao.kzdoctorapp.myadapters.OrderAdapter;
import com.tongxinyiliao.kzdoctorapp.ui.RefershUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class Order1Activity extends BaseMyFragment {
    private String mUrl = "http://img5.duitang.com/uploads/blog/201407/17/20140717113117_mUssJ.thumb.jpeg";
    //下拉刷新
    protected PtrFrameLayout mPtrFrameLayout;
    ListView lv_order;


    List<OrderEntity> list;
    OrderAdapter orderAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //显示视图
        return inflater.inflate(R.layout.activity_order, container, false);
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
        lv_order = (ListView) view.findViewById(R.id.order_lv_order);
        mPtrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.order_material_style_ptr_frame);
        initRefersh();
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
                //刷新完成
                mPtrFrameLayout.refreshComplete();
            }
        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        list=new ArrayList<>();
        for (int i=1000;i<1010;i++){
            OrderEntity orderEntity=new OrderEntity("00"+i,"已处理","小明","CT",600+"");
            list.add(orderEntity);
        }
        orderAdapter=new OrderAdapter(getActivity(),list);
        lv_order.setAdapter(orderAdapter);


    }

    @Override
    public void onClick(View v) {

    }

    //测试添加
    public void addMessage(){
        for (int i=100;i<120;i++){
            OrderEntity orderEntity=new OrderEntity("00"+i,"已处理","小明","CT",600+"");
            list.add(orderEntity);
        }
        orderAdapter.notifyDataSetChanged();
    }
}
