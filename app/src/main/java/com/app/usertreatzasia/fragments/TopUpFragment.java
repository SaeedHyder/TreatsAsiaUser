package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TopUpFragment extends BaseFragment {

    @BindView(R.id.edt_enter_points)
    AnyEditTextView edtEnterPoints;
    @BindView(R.id.tv_points_available)
    AnyTextView tvPointsAvailable;
    @BindView(R.id.btn_top_up_points)
    Button btnTopUpPoints;
    @BindView(R.id.btn_TandC)
    Button btn_TandC;
    Unbinder unbinder;

    public static TopUpFragment newInstance() {
        return new TopUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_up, container, false);
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
        titleBar.showBackButton();
        titleBar.setSubHeading("Topup");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.edt_enter_points, R.id.btn_top_up_points, R.id.btn_TandC})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_enter_points:
                break;
            case R.id.btn_top_up_points:
                UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented with payment module.");
                break;
            case R.id.btn_TandC:
                getDockActivity().replaceDockableFragment(TermsAndConditionsFragment.newInstance(), "TermsAndConditionsFragment");
                break;
        }
    }
}
