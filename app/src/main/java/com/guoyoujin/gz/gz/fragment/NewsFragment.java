package com.guoyoujin.gz.gz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.guoyoujin.gz.gz.R;

/**
 * Created by guoyoujin on 15/6/17.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    View rootView;
    TextView mMessageTitle;
    TextView mMessageSummary;
    ImageView mMessageIcon;
    ViewFlipper mMessageFlipper;
    ViewFlipper mListFlipper;
    private SwipeRefreshLayout mMessageSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        mMessageSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.vocabulary_message_swipe_refresh);
        mMessageSwipeRefreshLayout.setOnRefreshListener(this);
        mMessageSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh);
        mListFlipper = (ViewFlipper) rootView.findViewById(R.id.vocabulary_list_flipper);
        mMessageFlipper = (ViewFlipper) rootView.findViewById(R.id.vocabulary_message_flipper);
        mMessageIcon = (ImageView) rootView.findViewById(R.id.vocabulary_message_icon);
        mMessageTitle = (TextView) rootView.findViewById(R.id.vocabulary_message_title);
        mMessageSummary = (TextView) rootView.findViewById(R.id.vocabulary_message_summary);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {

    }
}
