package com.guoyoujin.gz.gz.fragment.tabfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.guoyoujin.gz.gz.R;

/**
 * Created by guoyoujin on 15/6/18.
 */
public class Fragment1 extends Fragment{
    private View view;
    private RecyclerView recyView_fragment;
    private ImageView vocabulary_message_icon;
    private TextView vocabulary_message_title;
    private TextView vocabulary_message_summary;
    private SwipeRefreshLayout vocabulary_message_swipe_refresh;
    private ViewFlipper vocabulary_message_flipper;
    private ViewFlipper vocabulary_list_flipper;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goddess,container,false);
        initView(view);
        return view;
    }
    private void initView(View view){
        recyView_fragment = (RecyclerView)view.findViewById(R.id.recyView_fragment);
        vocabulary_message_icon = (ImageView)view.findViewById(R.id.vocabulary_message_icon);
        vocabulary_message_title = (TextView)view.findViewById(R.id.vocabulary_message_title);
        vocabulary_message_summary = (TextView)view.findViewById(R.id.vocabulary_message_summary);
        vocabulary_message_swipe_refresh = (SwipeRefreshLayout) view.findViewById(R.id.vocabulary_message_swipe_refresh);
        vocabulary_message_swipe_refresh.setOnRefreshListener(onRefreshListener);
        vocabulary_message_swipe_refresh.setColorSchemeResources(R.color.swipe_refresh);
        vocabulary_message_flipper = (ViewFlipper) view.findViewById(R.id.vocabulary_message_flipper);
        vocabulary_list_flipper = (ViewFlipper) view.findViewById(R.id.vocabulary_list_flipper);
    }
    SwipeRefreshLayout.OnRefreshListener onRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
        public void onRefresh() {

        }
    };
}



