package com.tongxinyiliao.kzdoctorapp.viewpager;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tongxinyiliao.kzdoctorapp.R;
import com.tongxinyiliao.kzdoctorapp.activity.webview.WebViewActivity;
import com.tongxinyiliao.kzdoctorapp.animation.MyAnimation;
import com.tongxinyiliao.kzdoctorapp.base.activity.BaseMyFragment;
import com.tongxinyiliao.kzdoctorapp.entity.AdvInfo;
import com.tongxinyiliao.kzdoctorapp.httputils.HttpUrl;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseMyFragment implements View.OnClickListener {

    //initView
    View view;
    ViewPager vp_show;
    LinearLayout ll_tips;
    ImageView iv_add, iv_kz, iv_hospital, iv_problem, iv_aboutme;


    private List<ImageView> images = new ArrayList<ImageView>();
    ImageView[] tips;

    Handler handler;

    //设置广告的轮播时间
    private final int ad_time = 5000;

    // ViewPager-item视图集合的保存
    private ArrayList<View> views;
    // ViewPager-item临时视图
    private View view1;
    private List<ImageView> dotList;
    // 当前item
    private int currentItem = 1;
    // 数据源集合
    private List<AdvInfo> list = new ArrayList<AdvInfo>();

    //是否创建过了
    boolean isCrated = false;

    //创建view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    //创建布局
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initEvent();
        initData();
    }


    protected void initView() {
        view = getView();
        vp_show = (ViewPager) view.findViewById(R.id.home_vp_show);
        ll_tips = (LinearLayout) view.findViewById(R.id.home_ll_tips);
        iv_add = (ImageView) view.findViewById(R.id.home_iv_add);
        iv_kz = (ImageView) view.findViewById(R.id.home_iv_kzjieshao);
        iv_hospital = (ImageView) view.findViewById(R.id.home_iv_kzyiyuan);
        iv_problem = (ImageView) view.findViewById(R.id.home_iv_kzwenti);
        iv_aboutme = (ImageView) view.findViewById(R.id.home_iv_aboutme);

    }

    @Override
    protected void initEvent() {
        iv_aboutme.setOnClickListener(this);
        iv_problem.setOnClickListener(this);
        iv_hospital.setOnClickListener(this);
        iv_kz.setOnClickListener(this);
        vp_show.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pageIndex = position;
                // c' a b c a'
                // 向右滑动到a'时,将a'页设置为a的位置，则可以继续向右滑动。同理当向左滑动到 c'时，将c'页设置为c。
                if (position == 0) {
                    // 当视图在第一个时，将页面号设置为图片的最后一张。
                    pageIndex = list.size(); // 2'>2
                } else if (position == list.size() + 1) {
                    // 当视图在最后一个是,将页面号设置为图片的第一张。
                    pageIndex = 1; // 0'>0
                }

                if (position != pageIndex) {
                    // mPager.setCurrentItem(pageIndex, false);
                    currentItem = pageIndex;
                } else {
                    currentItem = position;
                }
                vp_show.setCurrentItem(currentItem, false);
                for (int i = 0; i < dotList.size(); i++) {
                    if (i == currentItem) {
                        dotList.get(i).setBackgroundResource(R.drawable.page_indicator_focused);
                    } else {
                        dotList.get(i).setBackgroundResource(R.drawable.page_indicator_unfocused);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//				if (state == 2) {
//					handler.removeMessages(1);
//				}
            }
        });


        iv_add.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        AdvInfo adv1 = new AdvInfo();
        adv1.setImagesrc("http://g.hiphotos.baidu.com/image/pic/item/bd3eb13533fa828b3010b07bff1f4134970a5a72.jpg");
        AdvInfo adv2 = new AdvInfo();
        adv2.setImagesrc("http://h.hiphotos.baidu.com/image/w%3D2048/sign=54cfa7549045d688a302b5a490fa7c1e/a50f4bfbfbedab64947d23a7f536afc379311e4d.jpg");
        AdvInfo adv3 = new AdvInfo();
        adv3.setImagesrc("http://img0.bdstatic.com/img/image/fushi/tiaowen.jpg");
        list.add(adv1);
        list.add(adv2);
        list.add(adv3);

        views = new ArrayList<View>();
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        // 由于要实现第一张图片和最后一张图片还可以继续滑，所以前后各多加了一个view
        // 假如有三张图片a,b,c，实现无限循环。在viewpager中设置5个view，第一张为三张图片的最后一张，第五张为三张图片的第一张
        // c' a b c a'
        int length = list.size() + 2;
        for (int i = 0; i < length; i++) {
            view1 = mInflater.inflate(R.layout.myadimageview, null);
            views.add(view1);
        }

        initDot();
        vp_show.setAdapter(new myPagerAdapter());
        // 默认选择索引1也就是a
        vp_show.setCurrentItem(1);

        //创建handler
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int id = msg.what;
                if (id == 1) {
                    vp_show.setCurrentItem(currentItem);
                    sleep(ad_time);

                    if (currentItem >= views.size()) {
                        currentItem = 1;
                    } else {
                        currentItem++;
                    }
                }
            }

            public void sleep(long delayMillis) {
                this.removeMessages(1);
                this.sendMessageDelayed(obtainMessage(1), delayMillis);
            }
        };


        //设置广告自动轮播
        handler.sendEmptyMessageDelayed(1, ad_time);

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.home_iv_add) {
            MyAnimation.setanimation(iv_add, 0.6f);
            showDialog();
        } else if (id == R.id.home_iv_kzjieshao) {
            MyAnimation.setanimation(iv_kz);
            Intent intent=new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("html_url", HttpUrl.KZ_INTRODUCE);
            intent.putExtra("title","快诊介绍");

            startActivity(intent);
        } else if (id == R.id.home_iv_kzyiyuan) {
            MyAnimation.setanimation(iv_hospital);
            Intent intent=new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("html_url", HttpUrl.KZ_JOIN_HOSPITAL);
            intent.putExtra("title","合作医院");

            startActivity(intent);
        } else if (id == R.id.home_iv_kzwenti) {
            MyAnimation.setanimation(iv_problem); Intent intent=new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("html_url", HttpUrl.KZ_COMMON_QUESTION);
            intent.putExtra("title","常见问题");

            startActivity(intent);

        } else if (id == R.id.home_iv_aboutme) {
            MyAnimation.setanimation(iv_aboutme);
            Intent intent=new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("html_url", HttpUrl.KZ_INTRODUCE);
            startActivity(intent);

        }
    }


    private void showDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .create();
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.message_alertdialog);
        //设置下边对其
        window.setGravity(Gravity.BOTTOM);
        // 设置宽高
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);
        // 设置监听
        ImageView iv_cancel = (ImageView) window.findViewById(R.id.message_alertdialog_iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }


    class myPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(View container, final int position) {

            ((ViewPager) container).addView(views.get(position));
            if (views.get(position) != null) {
                // views.size=5 view(c' a b c a') list.size=3 list(a b c)
                if (position == 0) {// c'>c

                    ImageLoader.getInstance().displayImage(list.get(list.size() - 1).getImagesrc(), (ImageView) views.get(position));
                } else if (position == (views.size() - 1)) {// a'>a
                    ImageLoader.getInstance().displayImage(list.get(0).getImagesrc(), (ImageView) views.get(position));
                } else {
                    ImageLoader.getInstance().displayImage(list.get(position - 1).getImagesrc(), (ImageView) views.get(position));
                }
                views.get(position).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    }
                });
            }
            return views.get(position);
        }
    }


    // 由于前后各多添加了一个view,所以前后的点去掉
    private void initDot() {
        dotList = new ArrayList<ImageView>();
        for (int i = 0; i < views.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(12, 0, 12, 0);
            ImageView m = new ImageView(getActivity());
            if (i == 0) { // 由于前后各添加了一个view,所以前后的点隐藏掉
                m.setVisibility(View.GONE);
            } else if (i == 1) {// 默认索引1也就是a为选中状态
                m.setVisibility(View.VISIBLE);
                m.setBackgroundResource(R.drawable.page_indicator_focused);
            } else if (i > 1 && i < views.size() - 1) {
                m.setVisibility(View.VISIBLE);
                m.setBackgroundResource(R.drawable.page_indicator_unfocused);
            } else { // 由于前后各多添加了一个view,所以前后的点隐藏掉
                m.setVisibility(View.GONE);
            }
            m.setLayoutParams(params);
            ll_tips.addView(m);
            dotList.add(m);
        }
    }

}