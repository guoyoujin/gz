package com.guoyoujin.gz.gz.kale;

import android.net.Uri;
import android.widget.TextView;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.utils.L;
import com.guoyoujin.gz.gz.vo.BeautyMainVo;


public class waterFallWhiteItem implements AdapterItem<BeautyMainVo.Imgs> {

    public DynamicHeightSimpleDraweeView contentSdv;

    public TextView descriptionTv;


    @Override
    public int getLayoutResId() {
        return R.layout.item_beauty_test_layout;
    }

    @Override
    public void initViews(ViewHolder vh, BeautyMainVo.Imgs photoData, int position) {
        contentSdv = vh.getView(R.id.thumbImage_face);
        descriptionTv = vh.getView(R.id.title_tag_face);
        setViews(photoData, position);
    }

    private void setViews(BeautyMainVo.Imgs data,int position) {
        L.e(data.getThumbnailUrl());
        contentSdv.setImageURI(Uri.parse(data.getThumbnailUrl()));
        float picRatio = (float) data.getThumbLargeHeight() / data.getThumbLargeWidth();
        contentSdv.setHeightRatio(picRatio);
        descriptionTv.setText(data.getTitle());
    }
}

