package com.app.usertreatzasia.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.WalletEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class PointsFragment extends BaseFragment {

    @BindView(R.id.tv_points_amount)
    AnyTextView tvPointsAmount;
    @BindView(R.id.btn_top_up_points)
    Button btnTopUpPoints;
    @BindView(R.id.btn_view_history)
    Button btnViewHistory;
    Unbinder unbinder;

    public static PointsFragment newInstance() {
        return new PointsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_points, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnTopUpPoints.setVisibility(View.GONE);

        getWalletData();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.points));
    }

    private void getWalletData() {
        serviceHelper.enqueueCall(webService.getWalletData(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getWallet);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.getWallet:
                setWalletData((WalletEntity) result);
                break;
        }
    }

    private void setWalletData(WalletEntity result) {
        if (result != null && result.getPoint() != null) {
            tvPointsAmount.setText(result.getPoint() + "");
            tvPointsAmount.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.btn_top_up_points, R.id.btn_view_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_top_up_points:
                break;
            case R.id.btn_view_history:
                getDockActivity().replaceDockableFragment(PointHistoryFragment.newInstance(), "PointHistoryFragment");
                break;
        }
    }
}
