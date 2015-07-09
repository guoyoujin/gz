package com.guoyoujin.gz.gz.fragment.tabfragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.adapter.LayoutAdapter;
import com.guoyoujin.gz.gz.application.MyApplication;
import com.guoyoujin.gz.gz.communication.GetBeautyInterface;
import com.guoyoujin.gz.gz.utils.Log;
import com.guoyoujin.gz.gz.vo.BeautyMainVo;
import com.guoyoujin.gz.gz.vo.BeautyMainVo.Imgs;

import org.lucasr.twowayview.ItemClickSupport;
import org.lucasr.twowayview.widget.DividerItemDecoration;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;

public class BeautyDetailFragment extends Fragment{
    private TwoWayView twoWayView;
	private View currentView;
	private String girlTag;
	private String BEAUTY_COL = "美女";
	private BeautyMainVo beautyVo;
	private ArrayList<Imgs> imgs = new ArrayList<Imgs>();
	private Handler handler = new Handler();
	private LinearLayout viewContainer;
	private int pn = 0; // 从那一条数据开始拿
	private View listView;
	private LayoutAdapter mAdapter;
	private int rn = 30;// 拿多少条
	private boolean isFresh = false;
	private boolean isLoadMore = false;
	public int position = 0;
	private FragmentActivity activity;


	public BeautyDetailFragment() {
	}
	public BeautyDetailFragment(String girlTag) {
		this.girlTag = girlTag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		activity = getActivity();
        if (currentView == null) {
			currentView = LayoutInflater.from(activity).inflate(R.layout.fragment_progress_container, null);
			viewContainer = (LinearLayout) currentView.findViewById(R.id.container);
		} else {
			ViewGroup parent = (ViewGroup) currentView.getParent();
			if (parent != null) {
				parent.removeView(currentView);
			}
		}
		getData();
		return currentView;
	}

	private void initView() {
		if (listView == null || mAdapter == null) {
			listView = LayoutInflater.from(activity).inflate(R.layout.waterfall_listview, null);
            twoWayView = (TwoWayView) listView.findViewById(R.id.twoWayView);
            twoWayView.setHasFixedSize(true);
            twoWayView.setLongClickable(true);
            initTwoWayView();
            viewContainer.removeAllViews();
			viewContainer.addView(listView);
		}

	}
    public void initTwoWayView(){
        final ItemClickSupport itemClick = ItemClickSupport.addTo(twoWayView);
        itemClick.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View child, int position, long id) {

            }
        });
        itemClick.setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(RecyclerView parent, View child, int position, long id) {

                return true;
            }
        });
        twoWayView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {

            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {

            }
        });
        final Drawable divider = getResources().getDrawable(R.drawable.divider);
        twoWayView.addItemDecoration(new DividerItemDecoration(divider));
        twoWayView.setAdapter(new LayoutAdapter(activity, twoWayView,imgs));
    }

	private void initNetErro() {
		// TODO
		final View loadView = LayoutInflater.from(activity).inflate(R.layout.loading_view, null);
		loadView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		View netErroView = LayoutInflater.from(activity).inflate(R.layout.page_store_net_erro, null);
		Button reloadBtn = (Button) netErroView.findViewById(R.id.reload_btn);
		netErroView.findViewById(R.id.net_erro_img).setVisibility(View.VISIBLE);
		reloadBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (viewContainer != null) {
					viewContainer.removeAllViews();
					viewContainer.addView(loadView);
					getData();
				}
			}
		});
		if (viewContainer != null) {
			viewContainer.removeAllViews();
			viewContainer.addView(netErroView);
		}

	}

	private void getData() {
		new Thread() {
			@Override
			public void run() {
				try {
					beautyVo = GetBeautyInterface.getNetData(BEAUTY_COL,girlTag, pn, rn);
                    Log.e(MyApplication.TAG,"beautyVo============"+beautyVo.toString());
                } catch (Exception e1) {
					e1.printStackTrace();
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (beautyVo != null) {
							ArrayList<Imgs> img = beautyVo.getImgs();
							if (img != null && img.size() > 0) {
								img.remove(img.size() - 1);
								if (isFresh) {
									imgs.clear();
									isFresh = false;
								}
								if (isLoadMore) {
									isLoadMore = false;
								}
								imgs.addAll(img);
								initView();
							} else {
								initNetErro();
							}
						} else {
							initNetErro();
						}
					}
				});
			}

		}.start();
	}

//	@Override
//	public void onItemClick(PLA_AdapterView<?> parent, View view, int position,
//			long id) {
//		if(position == 0) return;
//		Intent intent = new Intent();
//		intent.setClass(activity, BeautyPhotoDetailsActivity.class);
//		Bundle bundle = new Bundle();
//		bundle.putInt("position", position - 1);
//		bundle.putSerializable("imgs", imgs);
//		intent.putExtras(bundle);
//		startActivity(intent);
//		activity.overridePendingTransition(R.anim.abc_fade_in,
//				R.anim.abc_fade_out);
//
//	}

	public void onRefresh() {
		isFresh = true;
		pn = 0;
		getData();
	}

	public void onLoadMore() {
		if (beautyVo != null && beautyVo.getTotalNum() > imgs.size()) {
			isLoadMore = true;
			pn = pn + rn;
			getData();
		} else {
			Toast.makeText(activity, "没有更多妹子啦", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}
}
