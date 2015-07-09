package com.guoyoujin.gz.gz.fragment.tabfragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.guoyoujin.gz.gz.adapter.NewsFragment1Adapter;
import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.application.MyApplication;
import com.guoyoujin.gz.gz.communication.GetNewsInterface;
import com.guoyoujin.gz.gz.utils.Log;
import com.guoyoujin.gz.gz.vo.NewsListVo;
import com.guoyoujin.gz.gz.vo.NewsVo;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * Created by guoyoujin on 15/6/18.
 */
public class NewsFragments extends Fragment {
    private LinearLayoutManager layoutManager;
    private View view;
    private RecyclerView recyView_fragment;
    private ImageView vocabulary_message_icon;
    private TextView vocabulary_message_title;
    private TextView vocabulary_message_summary;
    private SwipeRefreshLayout vocabulary_message_swipe_refresh;
    private ViewFlipper vocabulary_message_flipper;
    private ViewFlipper vocabulary_list_flipper;
    private int channelId=0;
    private int requiredPage = 1;
    private NewsListVo newslist;
    private ArrayList<NewsVo> list;
    int[] id = { 5, 18, 27, 37, 21, 36, 23, 24 }; // 新闻 = 5，便民 = 18 ，社区= 27，美食 = 37 ，娱乐 = 21，电影 = 36，房产 =23，汽车 = 24

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1,container,false);
        channelId = id[FragmentPagerItem.getPosition(getArguments())];
        initView(view);
        showData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(MyApplication.TAG,"======onResume()===========");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(MyApplication.TAG,"======onStart()===========");

    }

    private void initView(View view){
        recyView_fragment = (RecyclerView)view.findViewById(R.id.recyView_fragment);
        vocabulary_message_icon = (ImageView)view.findViewById(R.id.vocabulary_message_icon);
        vocabulary_message_title = (TextView)view.findViewById(R.id.vocabulary_message_title);
        vocabulary_message_summary = (TextView)view.findViewById(R.id.vocabulary_message_summary);
        vocabulary_message_swipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.vocabulary_message_swipe_refresh_fragment1);
        vocabulary_message_swipe_refresh.setOnRefreshListener(onRefreshListener);
        vocabulary_message_swipe_refresh.setColorSchemeResources(R.color.swipe_refresh);
        vocabulary_message_flipper = (ViewFlipper) view.findViewById(R.id.vocabulary_message_flipper);
        vocabulary_list_flipper = (ViewFlipper) view.findViewById(R.id.vocabulary_list_flipper);
    }

    public void showData(){
        vocabulary_message_icon.setImageResource(R.drawable.ic_error_red_36dp);
        vocabulary_message_title.setText(R.string.no_items_title);
        vocabulary_message_summary.setText(R.string.no_items_summary);
        if (vocabulary_message_flipper.getDisplayedChild()==0){
            vocabulary_message_flipper.showNext();
        }
        Log.e(MyApplication.TAG,"启动线程访问网络");
        new GetNewsDataThread().start();
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        public void onRefresh() {
            if (vocabulary_message_flipper.getDisplayedChild()==1){
                vocabulary_message_flipper.showPrevious();
            }
            if (vocabulary_list_flipper.getDisplayedChild()==0){
                vocabulary_list_flipper.showPrevious();
            }
            new GetNewsDataThread().start();
        }
    };

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                default:
            }
        }
    };

    // 获取搜索数据
    class GetNewsDataThread extends Thread {
        @Override
        public void run() {
            try {
                newslist = GetNewsInterface.getNetData(channelId, requiredPage);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (newslist != null) {
                            list = newslist.getList();
                            if (list != null && list.size() > 0) {
                               //更新ui
                                //Logger.e("错误","message");
                                Log.e(MyApplication.TAG,"vocabulary_list_flipper.getDisplayedChild()===="+vocabulary_list_flipper.getDisplayedChild());
                                if (vocabulary_list_flipper.getDisplayedChild()==0){
                                    vocabulary_list_flipper.showNext();
                                }
                                layoutManager = new LinearLayoutManager(getActivity());
                                recyView_fragment.setLayoutManager(layoutManager);
                                NewsFragment1Adapter adapter=new NewsFragment1Adapter(getActivity(),list);
                                recyView_fragment.setAdapter(adapter);
                            } else {
                               //错误
                            }
                        } else {
                            //错误
                            Logger.e("错误","message");
                        }
                        vocabulary_message_swipe_refresh.setRefreshing(false);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initFilpMessageView(){
        if (vocabulary_message_flipper.getDisplayedChild()==0){
            vocabulary_message_flipper.showNext();
        }
        if (vocabulary_message_flipper.getDisplayedChild()==1){
            vocabulary_message_flipper.showPrevious();
        }

    }

    public void initFileListFilpView(){
        if (vocabulary_list_flipper.getDisplayedChild()==1){
            vocabulary_list_flipper.showPrevious();
        }
        if (vocabulary_list_flipper.getDisplayedChild()==0){
            vocabulary_list_flipper.showNext();
        }
    }
}



