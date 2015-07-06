package com.guoyoujin.gz.gz.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.application.MyApplication;
import com.guoyoujin.gz.gz.vo.NewsVo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by guoyoujin on 15/6/21.
 */
public class NewsFragment1Adapter  extends RecyclerView.Adapter<NewsFragment1Adapter.ItemViewHolder>{
    private Context mContext;
    private List<NewsVo> mDataSet;
    public DisplayImageOptions options;
    public ImageLoader imageLoader = ImageLoader.getInstance();
    public ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public NewsFragment1Adapter(Context context, List<NewsVo> dataSet){
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_image)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
                .cacheOnDisc(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(3))
                .build();
        mContext = context;
        mDataSet = dataSet;

    }
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.news_common_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Log.e(MyApplication.TAG,mDataSet.get(position).toString());
        imageLoader.displayImage(mDataSet.get(position).getImage().getSrc(),
                holder.imgNewsPic,options, animateFirstListener);
        holder.tvTitle.setText(mDataSet.get(position).getTitle());
        holder.date_text.setText(mDataSet.get(position).getDate());
        holder.description.setText(mDataSet.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgNewsPic;
        public TextView tvTitle;
        public TextView date_text;
        public TextView description;
        public ItemViewHolder(View itemView) {
           super(itemView);
           imgNewsPic = (ImageView) itemView.findViewById(R.id.imgNewsPic);
           tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
           description = (TextView) itemView.findViewById(R.id.tvDescription);
           date_text = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
        @Override
        public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
