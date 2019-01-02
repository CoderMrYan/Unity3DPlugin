package com.ykbjson.lib.baselib.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ykbjson.lib.baselib.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description：V4包下Fragment作为基类封装
 * Creator：yankebin
 * CreatedAt：2018/12/18
 */
public abstract class BaseFragmentV4 extends Fragment implements IBaseView {
    protected Unbinder unbinder;
    protected View mainContentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (null == mainContentView) {
            mainContentView = inflater.inflate(contentViewLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, mainContentView);
            if (null == savedInstanceState) {
                savedInstanceState = new Bundle();
            }
            savedInstanceState.putAll(getArguments());
            onViewCreated(savedInstanceState, mainContentView);
        } else {
            unbinder = ButterKnife.bind(this, mainContentView);
        }
        return mainContentView;
    }

    @Override
    public void onDetach() {
        mainContentView = null;
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (mainContentView != null && mainContentView.getParent() != null) {
            ((ViewGroup) mainContentView.getParent()).removeView(mainContentView);
        }
        super.onDestroyView();

        onVieDestroyed();
    }

}
