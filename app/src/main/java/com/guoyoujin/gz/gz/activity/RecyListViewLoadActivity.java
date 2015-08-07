package com.guoyoujin.gz.gz.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.application.MyApplication;
import com.guoyoujin.gz.gz.communication.GetBeautyInterface;
import com.guoyoujin.gz.gz.kale.AdapterItem;
import com.guoyoujin.gz.gz.kale.VerticalSwipeRefreshLayout;
import com.guoyoujin.gz.gz.kale.decoration.DividerGridItemDecoration;
import com.guoyoujin.gz.gz.kale.layoutmanager.ExStaggeredGridLayoutManager;
import com.guoyoujin.gz.gz.kale.recycler.ExCommonRcvAdapter;
import com.guoyoujin.gz.gz.kale.recycler.ExRecyclerView;
import com.guoyoujin.gz.gz.kale.recycler.OnRecyclerViewScrollListener;
import com.guoyoujin.gz.gz.kale.waterFallOrangeItem;
import com.guoyoujin.gz.gz.kale.waterFallWhiteItem;
import com.guoyoujin.gz.gz.vo.BeautyMainVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.guoyoujin.gz.gz.activity.RecyListViewLoadActivity.java
 * @author: guoyoujin
 * @date: 2015-08-05 16:13
 */

public class RecyListViewLoadActivity extends AppCompatActivity {
    private String girlTag="小清新";
    private String BEAUTY_COL = "美女";
    private BeautyMainVo beautyVo;
    private ArrayList<BeautyMainVo.Imgs> imgs = new ArrayList<BeautyMainVo.Imgs>();
    private Handler handler = new Handler();
    private int rn = 30;// 拿多少条
    private int pn = 0; // 从那一条数据开始拿
    public int position = 0;
    private boolean isFresh = false;
    private boolean isLoadMore = false;
    private static final String TAG = "RecyListViewLoadActivity";
    private Toolbar mToolbar;
    private AaaWaterFallAdapter waterFallAdapter;
    /**
     * 垂直方向下拉刷新的控件
     */
    private VerticalSwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout headerLl;

    private ImageView headerIv;


    private ImageView floatIV;
    private ExRecyclerView waterFallRcv;
    private View loadModel;
    private RelativeLayout pull_to_refresh_load_relativelayout;
    private ProgressBar pull_to_refresh_load_progress;
    private TextView pull_to_refresh_loadmore_text;
    private Button button_load_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_recy_list_view_load);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));//使用此方法一定要在 setSupportActionBar(mToolbar);方法之前
//        mToolbar.setSubtitle("1副标题");
        setSupportActionBar(mToolbar);
        findViews();
    }
    
    protected void findViews() {
        swipeRefreshLayout = (VerticalSwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        waterFallRcv = (ExRecyclerView) findViewById(R.id.waterFall_recyclerView);
        View headerRoot = LayoutInflater.from(this).inflate(R.layout.waterfall_header, null);
        headerLl = (LinearLayout) headerRoot.findViewById(R.id.header_linearLayout);
        headerIv = (ImageView) headerRoot.findViewById(R.id.header_imageView);
        floatIV = (ImageView) findViewById(R.id.float_imageButton);
        initLoadModel() ;
        setViews();
    }
    protected void setViews() {
        setFloatIv();
        setSwipeRefreshLayout();
        setWaterFallRcv();
        setHeaderView();
        setFooterView();
        loadModel.setVisibility(View.GONE);
    }
    private void setFloatIv() {
        floatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterFallRcv.smoothScrollToPosition(0);
            }
        });
    }
    public void initLoadModel(){
        loadModel = LayoutInflater.from(this).inflate(R.layout.recy_list_bottom_load_mode,null);
        button_load_button = (Button) loadModel.findViewById(R.id.button_load_button);
        pull_to_refresh_load_relativelayout = (RelativeLayout) loadModel.findViewById(R.id.pull_to_refresh_load_relativelayout);
    }

    private boolean isLoadingData = false;

    /**
     * 设置下拉刷新控件，下拉后加载新的数据
     */
    private void setSwipeRefreshLayout() {
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isFresh = true;
                if (!isLoadingData) {
                    getData();
                    isLoadingData = true;
                }
            }
        });
    }


    /**
     * 设置瀑布流控件
     */
    private void setWaterFallRcv() {
        // 设置头部或底部的操作应该在setAdapter之前
        waterFallRcv.addHeaderView(headerLl);
        waterFallRcv.addFooterView(loadModel);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new ExStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, true);// 可替换
        waterFallRcv.setLayoutManager(staggeredGridLayoutManager);

        // 添加分割线
        waterFallRcv.addItemDecoration(new DividerGridItemDecoration(this));
        //waterFallRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//可替换
//
        List<BeautyMainVo.Imgs> list = new ArrayList<>();// 先放一个空的list
        waterFallAdapter = new AaaWaterFallAdapter(list);


        // 不显示滚动到顶部/底部的阴影（减少绘制）
        waterFallRcv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //waterFallRcv.setClipToPadding(true);
        /**
         * 监听滚动事件
         */
        waterFallRcv.addOnScrollListener(new OnRecyclerViewScrollListener() {

            @Override
            public void onScrollUp() {
                //Log.d(TAG, "onScrollUp");
                hideViews();
            }

            @Override
            public void onScrollDown() {
                //Log.d(TAG, "onScrollDown");
                showViews();
            }

            @Override
            public void onBottom() {
                //Log.d(TAG, "on bottom");
                Toast.makeText(RecyListViewLoadActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                // 到底部自动加载
                if (!isLoadingData) {
                    isLoadingData = true;
                    Log.d(TAG, "loading old data");
                    getData();
//                    mDataManager.loadOldData(AaaActivity.this);
                    loadModel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onMoved(int distanceX, int distanceY) {
                // Log.d(TAG, "distance X = " + distanceX + "distance Y = " + distanceY);
                setToolbarBgByScrollDistance(distanceY);
            }
        });

        /**
         * 监听点击和长按事件
         */
        waterFallRcv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RecyListViewLoadActivity.this, "on click", Toast.LENGTH_SHORT).show();
                imgs.remove(position);
                waterFallAdapter.updateData(imgs);
            }
        });
        waterFallRcv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RecyListViewLoadActivity.this, "on long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        waterFallRcv.setAdapter(waterFallAdapter);
        getData();
    }

    private float headerHeight;

    /**
     * 设置头部的view
     */
    private void setHeaderView() {
        headerIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到某个位置
                waterFallRcv.scrollToPosition(10);
            }
        });
        headerIv.post(new Runnable() {
            @Override
            public void run() {
                headerHeight = headerIv.getHeight();
                //Log.d(TAG, "headerHeight" + headerHeight);
            }
        });

    }

    /**
     * 设置底部的view
     */
    private void setFooterView() {
//        progressBar.setText("正在加载...");
//        progressBar.getBackground().setAlpha(80);
    }


    /**
     * 滑动时影藏float button
     */
    private void hideViews() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) floatIV.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        floatIV.animate().translationY(floatIV.getHeight() + fabBottomMargin)
                .setInterpolator(new AccelerateInterpolator(2)).start();
    }

    /**
     * 滑动时出现float button
     */
    private void showViews() {
        floatIV.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    /**
     * 通过滚动的状态来设置toolbar的透明度
     */
    private void setToolbarBgByScrollDistance(int distance) {
        //Log.d(TAG, "distance" + distance);
        final float ratio = Math.min(distance / headerHeight, 1);
        final float newAlpha = ratio * 1;
        mToolbar.setAlpha(newAlpha);
    }




    public void onError(String msg) {
        Toast.makeText(RecyListViewLoadActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }
    private void getData() {
        pull_to_refresh_load_relativelayout.setVisibility(View.VISIBLE);
        button_load_button.setVisibility(View.GONE);

        new Thread() {
            @Override
            public void run() {
                try {
                    beautyVo = GetBeautyInterface.getNetData(BEAUTY_COL, girlTag, pn, rn);
                    com.guoyoujin.gz.gz.utils.Log.e(MyApplication.TAG, "beautyVo============" + beautyVo.toString());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (beautyVo != null) {
                            ArrayList<BeautyMainVo.Imgs> img = beautyVo.getImgs();
                            if (img != null && img.size() > 0) {
                                img.remove(img.size() - 1);
                                if (isFresh) {
                                    imgs.clear();
                                    isFresh = false;
                                }
                                if (isLoadMore) {
                                    isLoadMore = false;
                                }
                                pn = pn + rn;
                                isLoadingData = false;
                                imgs.addAll(img);
                                waterFallAdapter.updateData(imgs);
                                swipeRefreshLayout.setRefreshing(false);
                                pull_to_refresh_load_relativelayout.setVisibility(View.GONE);
                                button_load_button.setVisibility(View.VISIBLE);
                            } else {

                            }
                        } else {

                        }
                    }
                });
            }

        }.start();
    }

    class AaaWaterFallAdapter extends ExCommonRcvAdapter<BeautyMainVo.Imgs> {

        protected AaaWaterFallAdapter(List<BeautyMainVo.Imgs> data) {
            super(data);
        }

        @NonNull
        @Override
        protected AdapterItem<BeautyMainVo.Imgs> initItemView(Object type) {
            switch (Integer.valueOf(type.toString())) {
                case BeautyMainVo.Imgs.FIRST:
                    return new waterFallOrangeItem();
                case BeautyMainVo.Imgs.Second:
                    return new waterFallWhiteItem();
                default:
                    return new waterFallWhiteItem();
            }
        }
    }

}