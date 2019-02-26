package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.EntityTierAmount;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TotalMembersFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.tv_tier_1)
    AnyTextView tvTier1;
    @BindView(R.id.ll_tier_1)
    LinearLayout llTier1;
    @BindView(R.id.tv_tier_2)
    AnyTextView tvTier2;
    @BindView(R.id.ll_tier_2)
    LinearLayout llTier2;

    public static TotalMembersFragment newInstance() {
        return new TotalMembersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total_members, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
            serviceHelper.enqueueCall(webService.getTierAmountData(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getTierAmount);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.getTierAmount:
                EntityTierAmount entity = (EntityTierAmount) result;

                tvTier1.setText(prefHelper.getConvertedAmountCurrrency() + " " + entity.getTier_one_total() + "");
                tvTier2.setText(prefHelper.getConvertedAmountCurrrency() + " " + entity.getTier_two_total() + "");

                if (tvTier1.getText().toString().equalsIgnoreCase(prefHelper.getConvertedAmountCurrrency() + " ")){
                    tvTier1.setText(prefHelper.getConvertedAmountCurrrency() + " 0");
                }
                if (tvTier2.getText().toString().equalsIgnoreCase(prefHelper.getConvertedAmountCurrrency() + " ")){
                    tvTier2.setText(prefHelper.getConvertedAmountCurrrency() + " 0");
                }
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Total Members");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.ll_tier_1, R.id.ll_tier_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_tier_1:
                getDockActivity().replaceDockableFragment(MemberDetailFragment.newInstance("Tier 1", AppConstants.TIER1), "MemberDetailFragment");
                break;
            case R.id.ll_tier_2:
                getDockActivity().replaceDockableFragment(MemberDetailFragment.newInstance("Tier 2", AppConstants.TIER2), "MemberDetailFragment");
                break;
        }
    }
}
