package com.guoyoujin.gz.gz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoyoujin.gz.gz.R;

/**
 * Created by guoyoujin on 15/6/17.
 */
public class GoddessFragment extends Fragment{
    private View mRoleView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoleView = inflater.inflate(R.layout.fragment_goddess,container,false);
        return mRoleView;
    }
}
