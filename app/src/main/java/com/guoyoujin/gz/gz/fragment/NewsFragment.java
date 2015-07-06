package com.guoyoujin.gz.gz.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.application.MyApplication;
import com.guoyoujin.gz.gz.fragment.tabfragment.NewsFragments;
import com.guoyoujin.gz.gz.utils.Log;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * Created by guoyoujin on 15/6/17.
 */
public class NewsFragment extends Fragment{
    private  static String CLASS_NAME="NewsFragment";
    View rootView;
    SmartTabLayout viewpagertab;
    ViewPager viewpager;
    int[] chnanelId={ 5, 18, 27, 37, 21, 36, 23, 24 };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        Log.e(MyApplication.TAG,"======onCreateView======");
        initView(rootView);
        initFragment();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(MyApplication.TAG, CLASS_NAME + "====onResume()===");

    }

    public void initView(View view){
        viewpagertab = (SmartTabLayout)view.findViewById(R.id.viewpagertab);
        viewpager = (ViewPager)view.findViewById(R.id.viewpager);
    }
    public void initFragment(){
        FragmentPagerItems pages = new FragmentPagerItems(getActivity());
        Demo demo=new Demo();
        for (int titleResId : demo.tabs()) {
            Log.e(MyApplication.TAG,"初始化fragment");
            pages.add(FragmentPagerItem.of(getActivity().getString(titleResId), NewsFragments.class));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getActivity().getSupportFragmentManager(), pages);
        viewpager.setAdapter(adapter);
        viewpagertab.setViewPager(viewpager);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }


    public static void startActivity(Context context) {

    }
}
