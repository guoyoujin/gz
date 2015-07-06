package com.tongxinyiliao.kzdoctorapp.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyFragmentActivity;
import com.tongxinyiliao.kzdoctorapp.viewpager.HomeActivity;
import com.tongxinyiliao.kzdoctorapp.viewpager.MeActivity;
import com.tongxinyiliao.kzdoctorapp.viewpager.MessageActivity;
import com.tongxinyiliao.kzdoctorapp.viewpager.Order1Activity;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class MainActivity extends BaseMyFragmentActivity implements OnPageChangeListener {
    //initView
    private ViewPager mViewPager;
    private ImageView tab_img_message, tab_img_order, tab_img_me, tab_img_home;
    TextView tv_titlebar_center,tv_titlebar_left;
    //initData
    ImageView[] tabs;
    List<Fragment> fTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;

    String [] titleString={"首页","订单","消息","个人"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.app_img_loading)// 加载失败的时候
                .cacheOnDisc().cacheInMemory().imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(config);

        initView();
        initEvent();
        initData();
    }

    /**
     * 初始化界面
     */
    protected void initView() {
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mViewPager.setOnPageChangeListener(this);
        tv_titlebar_center= (TextView) findViewById(R.id.titlebar_tv_center);
        tv_titlebar_center.setText("首页");
        //设置titlebarleft隐藏
        tv_titlebar_left= (TextView) findViewById(R.id.titlebar_tv_left);
        tv_titlebar_left.setVisibility(View.INVISIBLE);
        initTab();
    }

    @Override
    protected void initEvent() {

    }

    ;

    /**
     * 初始化界面
     */
    void initTab() {
        tab_img_home = (ImageView) findViewById(R.id.tab_img_home);
        tab_img_home.setOnClickListener(this);
        tab_img_order = (ImageView) findViewById(R.id.tab_img_order);
        tab_img_order.setOnClickListener(this);
        tab_img_message = (ImageView) findViewById(R.id.tab_img_message);
        tab_img_message.setOnClickListener(this);
        tab_img_me= (ImageView) findViewById(R.id.tab_img_me);
        tab_img_me.setOnClickListener(this);
        tabs = new ImageView[4];
        tabs[0] = tab_img_home;
        tabs[1] = tab_img_order;
        tabs[2] = tab_img_message;
        tabs[3] = tab_img_me;
        tabs[0].setSelected(true);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

        HomeActivity homeActivity=new HomeActivity();
        Order1Activity order1Activity =new Order1Activity();
        MessageActivity messageActivity=new MessageActivity();
        MeActivity meActivity=new MeActivity();
        //设置三个界面
        fTabs.add(homeActivity);
        fTabs.add(order1Activity);
        fTabs.add(messageActivity);
        fTabs.add(meActivity);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fTabs.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fTabs.get(position);
            }
        };
        //设置viewpager的缓存页面的个数
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mAdapter);

    }

    //tab的点击事件
    @Override
    public void onClick(View v) {
        int vid = v.getId();
        switch (vid) {
            case R.id.tab_img_home: {
                selectedTab(0);
                break;
            }
            case R.id.tab_img_order: {
                selectedTab(1);
                break;
            }
            case R.id.tab_img_message: {
                selectedTab(2);
                break;
            }
            case R.id.tab_img_me: {
                selectedTab(3);
                break;
            }
            default:
                selectedTab(0);
                break;
        }
    }

    /**
     * 选择了那一项
     *
     * @param select
     */
    void selectedTab(int select) {
        for (int i = 0; i < tabs.length; i++) {
            if (i == select) {
                tabs[i].setSelected(true);
                mViewPager.setCurrentItem(select, false);
            } else {
                tabs[i].setSelected(false);
            }
        }
        //设置titlebar
        tv_titlebar_center.setText(titleString[select]);
    }

    //viewpager 界面改变
    @Override
    public void onPageScrollStateChanged(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //界面发生改变时选择当前页面
        selectedTab(position);
    }

}
