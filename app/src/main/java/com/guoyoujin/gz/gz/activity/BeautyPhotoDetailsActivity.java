package com.guoyoujin.gz.gz.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.animation.DepthPageTransformer;
import com.guoyoujin.gz.gz.photoview.PhotoView;
import com.guoyoujin.gz.gz.utils.LoadingImgUtil;
import com.guoyoujin.gz.gz.vo.BeautyMainVo;

import java.util.ArrayList;

public class BeautyPhotoDetailsActivity extends AppCompatActivity implements
		OnPageChangeListener {

	private ArrayList<BeautyMainVo.Imgs> imgs;
	private ViewPager mViewPager;
	public int position;
    private Toolbar mToolbar;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.activity_view_pager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        if (getIntent() != null) {
            imgs = (ArrayList<BeautyMainVo.Imgs>) getIntent().getSerializableExtra("imgs");
            position = getIntent().getIntExtra("position", 0);
        }
        mToolbar.showOverflowMenu();

        mToolbar.setTitle(imgs.get(position).getDesc());//使用此方法一定要在 setSupportActionBar(mToolbar);方法之前
//        mToolbar.setSubtitle("1副标题");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
		initPageView();

		if (imgs != null && imgs.size() > 0) {
			mViewPager.setAdapter(new MyAdapter());
			mViewPager.setCurrentItem(position);
			mViewPager.setOnPageChangeListener(this);
			
			mViewPager.setPageTransformer(true, new DepthPageTransformer());
		}

	}

	protected void initPageView() {

		mViewPager = (ViewPager) findViewById(R.id.view_pager);
	}



	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgs.size();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewGroup) container).removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(View container, int arg1) {
			// TODO Auto-generated method stub

			View view = getLayoutInflater().inflate(
					R.layout.beauty_item_detail, null);
			LoadingImgUtil.loadimgAnimate(imgs.get(arg1).getImageUrl(),
                    (PhotoView) view.findViewById(R.id.photoview));
			((ViewPager) container).addView(view, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			return view;

		}

	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		if (TextUtils.isEmpty(imgs.get(arg0).getDesc())) {
            mToolbar.setTitle("美女");
		} else {
            mToolbar.setTitle(imgs.get(arg0).getDesc());
		}

	}
}
