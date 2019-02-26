package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.SubscriptionEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.ui.views.TitleBar;

public class Pager2 extends BaseFragment {

    public static Pager2 newInstance() {
        return new Pager2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pager_2, container, false);
        return view;
    }

    public void scaleImage(float scaleX) {
        view.setScaleY(scaleX);
        view.invalidate();
    }
    public void setContent(SubscriptionEntity result) {
        if (result != null) {

        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }
}
