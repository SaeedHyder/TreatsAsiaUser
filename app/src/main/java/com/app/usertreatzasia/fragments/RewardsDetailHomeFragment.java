package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.RewardsEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RewardsDetailHomeFragment extends BaseFragment {

    @BindView(R.id.iv_offerImage)
    ImageView ivOfferImage;
    @BindView(R.id.txtRewardTitle)
    AnyTextView txtRewardTitle;
    @BindView(R.id.txtActualAmount)
    AnyTextView txtActualAmount;
    @BindView(R.id.txtAfterDiscount)
    AnyTextView txtAfterDiscount;
    @BindView(R.id.txtPointsAmount)
    AnyTextView txtPointsAmount;
    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    @BindView(R.id.tv_start_end_date)
    AnyTextView tvStartEndDate;
    @BindView(R.id.txtRewardDetail)
    AnyTextView txtRewardDetail;
    @BindView(R.id.btn_grab_now)
    Button btnGrabNow;
    @BindView(R.id.txtTermsAndCondition)
    AnyTextView txtTermsAndCondition;
    private static String rewardsData = "rewardsData";
    private RewardsEnt rewardsEnt;
    String goToHome;
    ImageLoader imageLoader;
    Unbinder unbinder;

    public static RewardsDetailHomeFragment newInstance(RewardsEnt rewardsEnt) {
        Bundle args = new Bundle();
        args.putString(rewardsData, new Gson().toJson(rewardsEnt));
        RewardsDetailHomeFragment fragment = new RewardsDetailHomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rewardsData = getArguments().getString(rewardsData);
        if (rewardsData != null) {
            rewardsEnt = new Gson().fromJson(rewardsData, RewardsEnt.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards_home_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        setRewardDetailData();

    }

    private void setRewardDetailData() {

        String terms_condition = "";
        goToHome = "goToHome";
        txtActualAmount.setVisibility(View.GONE);
        txtAfterDiscount.setVisibility(View.GONE);

        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            txtRewardTitle.setText(rewardsEnt.getTitle() + "");
            Spanned desc = Html.fromHtml(rewardsEnt.getDescription());
            txtRewardDetail.setText(desc.toString().trim());
            terms_condition = Html.fromHtml(rewardsEnt.getTermCondition()) + "";
            txtTermsAndCondition.setText(terms_condition + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            txtRewardTitle.setText(rewardsEnt.getInTitle() + "");
            Spanned desc = Html.fromHtml(rewardsEnt.getInDescription());
            txtRewardDetail.setText(desc.toString().trim());
            terms_condition = Html.fromHtml(rewardsEnt.getInTermCondition()) + "";
            txtTermsAndCondition.setText(terms_condition + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            txtRewardTitle.setText(rewardsEnt.getMaTitle() + "");
            Spanned desc = Html.fromHtml(rewardsEnt.getMaDescription());
            txtRewardDetail.setText(desc.toString().trim());
            terms_condition = Html.fromHtml(rewardsEnt.getMaTermCondition()) + "";
            txtTermsAndCondition.setText(terms_condition + "");
        } else {
            txtRewardTitle.setText(rewardsEnt.getTitle() + "");
            Spanned desc = Html.fromHtml(rewardsEnt.getDescription());
            txtRewardDetail.setText(desc.toString().trim());
            terms_condition = Html.fromHtml(rewardsEnt.getTermCondition()) + "";
            txtTermsAndCondition.setText(terms_condition + "");
        }

        imageLoader.displayImage(rewardsEnt.getRewardImage(), ivOfferImage);

        tvStartEndDate.setText(DateHelper.dateFormat(rewardsEnt.getStartDate(), AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD) + " - " + DateHelper.dateFormat(rewardsEnt.getEndDate(), AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD));

        if (rewardsEnt.getPoint() == "") {
            txtPointsAmount.setText("0");
        } else {
            txtPointsAmount.setText(rewardsEnt.getPoint());
        }

        txtActualAmount.setVisibility(View.GONE);
        txtAfterDiscount.setVisibility(View.GONE);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Reward Detail");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btn_grab_now)
    public void onViewClicked() {
        getDockActivity().replaceDockableFragment(PaymentQuantityFragment.newInstance(rewardsEnt), "PaymentQuantityFragment");
    }
}
