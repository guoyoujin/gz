package com.guoyoujin.gz.gz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.utils.LoadingImgUtil;
import com.guoyoujin.gz.gz.vo.BeautyMainVo;

import java.util.ArrayList;


public class BeautyItemAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<BeautyMainVo.Imgs> rowImages;
	private ViewHolder viewHolder;

	public BeautyItemAdapter(Context mContext, ArrayList<BeautyMainVo.Imgs> rowImages) {
		super();
		this.mContext = mContext;
		this.rowImages = rowImages;
	}

	public void updateAdapter(ArrayList<BeautyMainVo.Imgs> rowImages) {
		this.rowImages = rowImages;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return rowImages.size();
	}

	@Override
	public Object getItem(int position) {
		return rowImages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_beauty_layout, null);
			viewHolder.thumbImage = (ImageView) convertView
					.findViewById(R.id.thumbImage);
			viewHolder.img_like = (ImageView) convertView
					.findViewById(R.id.img_like);
			viewHolder.title_tag = (TextView) convertView
					.findViewById(R.id.title_tag);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		BeautyMainVo.Imgs rowImage = rowImages.get(position);
		LoadingImgUtil.loadimgAnimate(rowImage.getThumbnailUrl(),
                viewHolder.thumbImage);
		viewHolder.title_tag.setText(rowImage.getTitle());

		return convertView;
	}

	static class ViewHolder {
		// 缩略图
		ImageView thumbImage;
		// 收藏
		ImageView img_like;
		// 标签
		TextView title_tag;
	}

}
