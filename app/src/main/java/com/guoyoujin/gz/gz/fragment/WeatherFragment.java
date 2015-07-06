package com.guoyoujin.gz.gz.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.application.MyApplication;
import com.guoyoujin.gz.gz.global.Globe;
import com.guoyoujin.gz.gz.utils.Log;
import com.guoyoujin.gz.gz.utils.TimeUtil;
import com.guoyoujin.gz.gz.view.CirclePageIndicator;
import com.guoyoujin.gz.gz.vo.WeatherInfo;
import com.guoyoujin.gz.gz.weath.FirstWeatherFragment;
import com.guoyoujin.gz.gz.weath.GetWeatherTask;
import com.guoyoujin.gz.gz.weath.SecondWeatherFragment;
import com.guoyoujin.gz.gz.weath.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private WeatherInfo mAllWeather;

    private View mAqiRootView;
    private TextView cityTv, timeTv, aqiDataTv, aqiQualityTv, temperatureTv,
            climateTv, windTv, weekTv;
    private ViewPager mViewPager;
    private List<Fragment> fragments;
    private WeatherPagerAdapter mWeatherPagerAdapter;
    private ImageView weatherImg, aqiImg;
    private SharePreferenceUtil mSpUtil;
    private HashMap<String, Integer> mWeatherIcon;// 天气图标

    private TranslateAnimation left, right;
    private ImageView runImage;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case Globe.GET_WEATHER_SCUESS:
                    Log.e(MyApplication.TAG,"数据结果来了");
                    if(vocabulary_message_flipper.getDisplayedChild() == 0){
                        vocabulary_message_flipper.showNext();
                    }
                    updateAqiInfo(mAllWeather);
                    updateWeatherInfo(mAllWeather);
                    break;
                case Globe.GET_WEATHER_FAIL:
                    Toast.makeText(getActivity(), "获取天气失败，请重试", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            vocabulary_message_swipe_refresh.setRefreshing(false);
        }

    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoleView = inflater.inflate(R.layout.fragment_weather,container,false);
        initView(mRoleView);
        getSharePreferenceUtil();
        initWeatherIconMap();
        initViewFragment(mRoleView);
        return mRoleView;
    }
    public void initView(View view){
        vocabulary_list_flipper = (ViewFlipper)view.findViewById(R.id.vocabulary_list_flipper);
        vocabulary_message_flipper = (ViewFlipper)view.findViewById(R.id.vocabulary_message_flipper);
        vocabulary_message_swipe_refresh = (SwipeRefreshLayout)view.findViewById(R.id.vocabulary_message_swipe_refresh);
        vocabulary_message_icon = (ImageView)view.findViewById(R.id.vocabulary_message_icon);
        vocabulary_message_title = (TextView)view.findViewById(R.id.vocabulary_message_title);
        vocabulary_message_summary = (TextView)view.findViewById(R.id.vocabulary_message_summary);
        vocabulary_message_swipe_refresh.setOnRefreshListener(onRefreshListener);

    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (vocabulary_message_flipper.getDisplayedChild()==1){
                vocabulary_message_flipper.showPrevious();
            }
            if (vocabulary_list_flipper.getDisplayedChild()==0){
                vocabulary_list_flipper.showPrevious();
            }
            new GetWeatherTask(mHandler, WeatherFragment.this).execute();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        vocabulary_message_icon.setImageResource(R.drawable.ic_error_red_36dp);
        vocabulary_message_title.setText(R.string.no_items_title);
        vocabulary_message_summary.setText(R.string.no_items_summary);
        if (vocabulary_message_flipper.getDisplayedChild()==0){
            vocabulary_message_flipper.showNext();
        }
        new GetWeatherTask(mHandler, this).execute();
    }

    private void initViewFragment(View view) {
        cityTv = (TextView) view.findViewById(R.id.city);
        timeTv = (TextView) view.findViewById(R.id.time);
        timeTv.setText("未发布");
        weekTv = (TextView) view.findViewById(R.id.week_today);
        mAqiRootView = view.findViewById(R.id.aqi_root_view);
        mAqiRootView.setVisibility(View.INVISIBLE);
        aqiDataTv = (TextView) view.findViewById(R.id.pm_data);
        aqiQualityTv = (TextView) view.findViewById(R.id.pm2_5_quality);
        aqiImg = (ImageView) view.findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) view.findViewById(R.id.temperature);
        climateTv = (TextView) view.findViewById(R.id.climate);
        windTv = (TextView) view.findViewById(R.id.wind);
        weatherImg = (ImageView) view.findViewById(R.id.weather_img);
        runImage = (ImageView) view.findViewById(R.id.run_image);
        fragments = new ArrayList<Fragment>();
        fragments.add(new FirstWeatherFragment(this, mAllWeather));
        fragments.add(new SecondWeatherFragment(this, mAllWeather));
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_wether);
        mWeatherPagerAdapter = new WeatherPagerAdapter(getFragmentManager(),fragments);
        mViewPager.setAdapter(mWeatherPagerAdapter);
        ((CirclePageIndicator) view.findViewById(R.id.indicator)).setViewPager(mViewPager);
        vocabulary_list_flipper.showNext();
        vocabulary_message_flipper.showNext();
    }

    /**
     * 更新天气界面
     */
    private void updateWeatherInfo(WeatherInfo allWeather) {
        weekTv.setText("今天 " + TimeUtil.getWeek(0, TimeUtil.XING_QI));
        if (allWeather != null) {
            cityTv.setText(allWeather.getCity());
            if (!TextUtils.isEmpty(allWeather.getFeelTemp())) {
                temperatureTv.setText(allWeather.getFeelTemp());
                mSpUtil.setSimpleTemp(allWeather.getFeelTemp()
                        .replace("~", "/").replace("℃", "°"));// 保存一下温度信息，用户小插件
            } else {
                temperatureTv.setText(allWeather.getTemp0());
                mSpUtil.setSimpleTemp(allWeather.getTemp0().replace("~", "/")
                        .replace("℃", "°"));
            }

            String climate = allWeather.getWeather0();
            climateTv.setText(climate);
            mSpUtil.setSimpleClimate(climate);// 保存一下天气信息，用户小插件

            weatherImg.setImageResource(getWeatherIcon(climate));
            windTv.setText(allWeather.getWind0());

            String time = allWeather.getIntime();
            mSpUtil.setTimeSamp(TimeUtil.getLongTime(time));// 保存一下更新的时间戳，记录更新时间
            timeTv.setText(TimeUtil.getDay(mSpUtil.getTimeSamp()) + "发布");
        } else {
            cityTv.setText(mSpUtil.getCity());
            timeTv.setText("未同步");
            temperatureTv.setText("N/A");
            climateTv.setText("N/A");
            windTv.setText("N/A");
            weatherImg.setImageResource(R.drawable.na);
            Toast.makeText(getActivity(), "获取天气信息失败", Toast.LENGTH_SHORT)
                    .show();
        }
        runAnimation();
    }

    /**
     * 更新aqi界面
     */
    private void updateAqiInfo(WeatherInfo allWeather) {
        if (allWeather != null && allWeather.getAQIData() != null) {
            mAqiRootView.setVisibility(View.VISIBLE);
            aqiDataTv.setText(allWeather.getAQIData());
            int aqi = Integer.parseInt(allWeather.getAQIData());
            int aqi_img = R.drawable.biz_plugin_weather_0_50;
            String aqiText = "无数据";
            if (aqi > 300) {
                aqi_img = R.drawable.biz_plugin_weather_greater_300;
                aqiText = "严重污染";
            } else if (aqi > 200) {
                aqi_img = R.drawable.biz_plugin_weather_201_300;
                aqiText = "重度污染";
            } else if (aqi > 150) {
                aqi_img = R.drawable.biz_plugin_weather_151_200;
                aqiText = "中度污染";
            } else if (aqi > 100) {
                aqi_img = R.drawable.biz_plugin_weather_101_150;
                aqiText = "轻度污染";
            } else if (aqi > 50) {
                aqi_img = R.drawable.biz_plugin_weather_51_100;
                aqiText = "良";
            } else {
                aqi_img = R.drawable.biz_plugin_weather_0_50;
                aqiText = "优";
            }
            aqiImg.setImageResource(aqi_img);
            aqiQualityTv.setText(aqiText);
        } else {
            mAqiRootView.setVisibility(View.INVISIBLE);
            aqiQualityTv.setText("");
            aqiDataTv.setText("");
            aqiImg.setImageResource(R.drawable.biz_plugin_weather_0_50);
            Toast.makeText(getActivity(), "该城市暂无空气质量数据", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public int getWeatherIcon(String climate) {
        int weatherIcon = R.drawable.biz_plugin_weather_qing;
        if (climate.contains("转")) {// 天气带转字，取前面那部分
            String[] strs = climate.split("转");
            climate = strs[0];
            if (climate.contains("到")) {// 如果转字前面那部分带到字，则取它的后部分
                strs = climate.split("到");
                climate = strs[1];
            }
        }
        if (getWeatherIconMap().containsKey(climate)) {
            weatherIcon = getWeatherIconMap().get(climate);
        }
        return weatherIcon;
    }

    public Map<String, Integer> getWeatherIconMap() {
        if (mWeatherIcon == null || mWeatherIcon.isEmpty())
            mWeatherIcon = initWeatherIconMap();
        return mWeatherIcon;
    }

    private HashMap<String, Integer> initWeatherIconMap() {
        if (mWeatherIcon != null && !mWeatherIcon.isEmpty())
            return mWeatherIcon;
        mWeatherIcon = new HashMap<String, Integer>();
        mWeatherIcon.put("暴雪", R.drawable.biz_plugin_weather_baoxue);
        mWeatherIcon.put("暴雨", R.drawable.biz_plugin_weather_baoyu);
        mWeatherIcon.put("大暴雨", R.drawable.biz_plugin_weather_dabaoyu);
        mWeatherIcon.put("大雪", R.drawable.biz_plugin_weather_daxue);
        mWeatherIcon.put("大雨", R.drawable.biz_plugin_weather_dayu);

        mWeatherIcon.put("多云", R.drawable.biz_plugin_weather_duoyun);
        mWeatherIcon.put("雷阵雨", R.drawable.biz_plugin_weather_leizhenyu);
        mWeatherIcon.put("雷阵雨冰雹",
                R.drawable.biz_plugin_weather_leizhenyubingbao);
        mWeatherIcon.put("晴", R.drawable.biz_plugin_weather_qing);
        mWeatherIcon.put("沙尘暴", R.drawable.biz_plugin_weather_shachenbao);

        mWeatherIcon.put("特大暴雨", R.drawable.biz_plugin_weather_tedabaoyu);
        mWeatherIcon.put("雾", R.drawable.biz_plugin_weather_wu);
        mWeatherIcon.put("小雪", R.drawable.biz_plugin_weather_xiaoxue);
        mWeatherIcon.put("小雨", R.drawable.biz_plugin_weather_xiaoyu);
        mWeatherIcon.put("阴", R.drawable.biz_plugin_weather_yin);

        mWeatherIcon.put("雨夹雪", R.drawable.biz_plugin_weather_yujiaxue);
        mWeatherIcon.put("阵雪", R.drawable.biz_plugin_weather_zhenxue);
        mWeatherIcon.put("阵雨", R.drawable.biz_plugin_weather_zhenyu);
        mWeatherIcon.put("中雪", R.drawable.biz_plugin_weather_zhongxue);
        mWeatherIcon.put("中雨", R.drawable.biz_plugin_weather_zhongyu);
        return mWeatherIcon;
    }

    class WeatherPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;

        public WeatherPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        public int getCount() {
            return fragments.size();
        }

    }

    public WeatherInfo getAllWeather() {
        return mAllWeather;
    }

    public void SetAllWeather(WeatherInfo allWeather) {
        mAllWeather = allWeather;
    }

    public synchronized SharePreferenceUtil getSharePreferenceUtil() {
        if (mSpUtil == null)
            mSpUtil = new SharePreferenceUtil(getActivity(),
                    SharePreferenceUtil.CITY_SHAREPRE_FILE);
        return mSpUtil;
    }

    // 壁纸动画
    public void runAnimation() {

        right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f,
                Animation.RELATIVE_TO_PARENT, -1f,
                Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
                0f);
        left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f,
                Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT,
                0f, Animation.RELATIVE_TO_PARENT, 0f);
        right.setDuration(30000);
        left.setDuration(30000);
        right.setFillAfter(true);
        left.setFillAfter(true);

        right.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                runImage.startAnimation(left);
            }
        });
        left.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                runImage.startAnimation(right);
            }
        });
        runImage.startAnimation(right);
    }
}
