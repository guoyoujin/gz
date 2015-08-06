package com.guoyoujin.gz.gz.kale;


import android.net.Uri;
import android.widget.TextView;

import com.guoyoujin.gz.gz.R;
import com.guoyoujin.gz.gz.utils.L;
import com.guoyoujin.gz.gz.vo.BeautyMainVo;


/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/10
 */
public class waterFallOrangeItem implements AdapterItem<BeautyMainVo.Imgs> {

    /** 内容主体的图片 */
    public DynamicHeightSimpleDraweeView contentSdv;

    /** 图片下方的描述文字 */
    public TextView descriptionTv;



    @Override
    public int getLayoutResId() {
        return R.layout.item_beauty_test_layout;
    }

    @Override
    public void initViews(ViewHolder viewHolder, BeautyMainVo.Imgs data, int i) {
        L.e(data.getThumbnailUrl());
        contentSdv = viewHolder.getView(R.id.thumbImage_face);
        descriptionTv = viewHolder.getView(R.id.title_tag_face);
        setViews(data,i);
    }

    public void setViews(BeautyMainVo.Imgs data, int position) {
        L.e(data.getThumbnailUrl());
        contentSdv.setImageURI(Uri.parse(data.getThumbLargeTnUrl()));
        float picRatio = (float) data.getThumbLargeHeight() / data.getThumbLargeWidth();
        contentSdv.setHeightRatio(picRatio);
        descriptionTv.setText(data.getTitle());
    }
}
