package com.guoyoujin.gz.gz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.guoyoujin.gz.gz.R;

/**
 * Created by guoyoujin on 15/6/17.
 */
public class WeatherFragment extends Fragment {
    private View mRoleView;
    private ViewFlipper vocabulary_list_flipper;
    private ViewFlipper vocabulary_message_flipper;
    private SwipeRefreshLayout vocabulary_message_swipe_refresh;
    private ImageView vocabulary_message_icon;
    private TextView vocabulary_message_title;
    private TextView vocabulary_message_summary;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoleView = inflater.inflate(R.layout.fragment_weather,container,false);
        initView(mRoleView);
        return mRoleView;
    }
    public void initView(View view){
        vocabulary_list_flipper = (ViewFlipper)view.findViewById(R.id.vocabulary_list_flipper);
        vocabulary_message_flipper = (ViewFlipper)view.findViewById(R.id.vocabulary_message_flipper);
        vocabulary_message_swipe_refresh = (SwipeRefreshLayout)view.findViewById(R.id.vocabulary_message_swipe_refresh);
        vocabulary_message_icon = (ImageView)view.findViewById(R.id.vocabulary_message_icon);
        vocabulary_message_title = (TextView)view.findViewById(R.id.vocabulary_message_title);
        vocabulary_message_summary = (TextView)view.findViewById(R.id.vocabulary_message_summary);
    }
}
