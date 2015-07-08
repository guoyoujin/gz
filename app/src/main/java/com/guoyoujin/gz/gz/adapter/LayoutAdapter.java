/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guoyoujin.gz.gz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.utils.LoadingImgUtil;
import com.guoyoujin.gz.gz.vo.BeautyMainVo;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.StaggeredGridLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;

import java.util.ArrayList;

public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {
    private static final int COUNT = 100;

    private final Context mContext;
    private final TwoWayView mRecyclerView;
    private ArrayList<BeautyMainVo.Imgs> rowImages;
    private int mCurrentItemId = 0;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        // 缩略图
        public final ImageView thumbImage;
        // 收藏
        public final ImageView img_like;
        // 标签
        public final TextView title_tag;
        public SimpleViewHolder(View view) {
            super(view);
            title_tag = (TextView) view.findViewById(R.id.title_tag);
            img_like = (ImageView) view.findViewById(R.id.img_like);
            thumbImage = (ImageView) view.findViewById(R.id.thumbImage);
        }
    }

    public LayoutAdapter(Context context, TwoWayView recyclerView, ArrayList<BeautyMainVo.Imgs> rowImages) {
        mContext = context;
        this.rowImages = rowImages;
        mRecyclerView = recyclerView;
    }

    public void removeItem(int position) {
        notifyItemRemoved(position);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item_beauty_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        BeautyMainVo.Imgs rowImage = rowImages.get(position);
        LoadingImgUtil.loadimgAnimate(rowImage.getThumbnailUrl(),
                holder.thumbImage);
        holder.title_tag.setText(rowImage.getTitle());
        boolean isVertical = (mRecyclerView.getOrientation() == TwoWayLayoutManager.Orientation.VERTICAL);
        final View itemView = holder.itemView;

        final int itemId = position;
        final int dimenId;
        if (itemId % 3 == 0) {
            dimenId = R.dimen.staggered_child_medium;
        } else if (itemId % 5 == 0) {
            dimenId = R.dimen.staggered_child_large;
        } else if (itemId % 7 == 0) {
            dimenId = R.dimen.staggered_child_xlarge;
        } else {
            dimenId = R.dimen.staggered_child_small;
        }

        final int span;
        if (itemId == 2) {
            span = 2;
        } else {
            span = 1;
        }

        final int size = mContext.getResources().getDimensionPixelSize(dimenId);

        final StaggeredGridLayoutManager.LayoutParams lp =
                (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();

        if (!isVertical) {
            lp.span = span;
            lp.width = size;
            itemView.setLayoutParams(lp);
        } else {
            lp.span = span;
            lp.height = size;
            itemView.setLayoutParams(lp);
        }
    }

    @Override
    public int getItemCount() {
        return rowImages.size();
    }
}
