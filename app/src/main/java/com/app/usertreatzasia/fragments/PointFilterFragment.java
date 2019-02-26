package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PointFilterFragment extends BaseFragment {

    @BindView(R.id.cb_latest_to_old)
    RadioButton cbLatestToOld;
    @BindView(R.id.cb_old_to_latest)
    RadioButton cbOldToLatest;
    @BindView(R.id.btnApply)
    Button btnApply;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    Unbinder unbinder;

    public static PointFilterFragment newInstance() {
        return new PointFilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_filter, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading("Points Filter");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.cb_latest_to_old, R.id.cb_old_to_latest, R.id.btnApply, R.id.radioGroup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_latest_to_old:
                break;
            case R.id.cb_old_to_latest:
                break;
            case R.id.btnApply:
                break;
            case R.id.radioGroup:
                break;
        }
    }
}
