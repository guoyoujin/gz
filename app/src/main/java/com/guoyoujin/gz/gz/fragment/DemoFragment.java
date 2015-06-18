package com.guoyoujin.gz.gz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.guoyoujin.gz.gz.R;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

/**
 * Created by guoyoujin on 15/6/18.
 */
public class DemoFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = FragmentPagerItem.getPosition(getArguments());
        TextView title = (TextView) view.findViewById(R.id.item_title);
        title.setText(String.valueOf(position));
    }
}
