package com.app.usertreatzasia.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
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
import com.app.usertreatzasia.entities.RewardsWonEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WonDetailFragment extends BaseFragment {


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
    @BindView(R.id.btn_Terms_Condition)
    Button btnTermsCondition;
    String trimmedSlug;
    Unbinder unbinder;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;

    private static String RewardsWonData = "RewardsWonData";
    private RewardsWonEnt rewardsEnt;

    public static WonDetailFragment newInstance(RewardsWonEnt rewardsEnt) {
        Bundle args = new Bundle();
        args.putString(RewardsWonData, new Gson().toJson(rewardsEnt));
        WonDetailFragment fragment = new WonDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        RewardsWonData = getArguments().getString(RewardsWonData);
        if (RewardsWonData != null) {
            rewardsEnt = new Gson().fromJson(RewardsWonData, RewardsWonEnt.class);
        }
        imageLoader=ImageLoader.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_won_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDetailDate();
    }

    ImageLoader imageLoader;
    private void setDetailDate() {

        txtActualAmount.setVisibility(View.GONE);
        txtAfterDiscount.setVisibility(View.GONE);

        String terms_condition="";

        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            txtRewardTitle.setText(rewardsEnt.getRewardDetail().getTitle() + "");
            Spanned desc = Html.fromHtml(rewardsEnt.getRewardDetail().getDescription());
            txtRewardDetail.setText(desc.toString().trim());

        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            txtRewardTitle.setText(rewardsEnt.getRewardDetail().getInTitle() + "");
            Spanned desc = Html.fromHtml(rewardsEnt.getRewardDetail().getInDescription());
            txtRewardDetail.setText(desc.toString().trim());

        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            txtRewardTitle.setText(rewardsEnt.getRewardDetail().getMaTitle()+"");
            Spanned desc = Html.fromHtml(rewardsEnt.getRewardDetail().getMaDescription());
            txtRewardDetail.setText(desc.toString().trim());

        } else {
            txtRewardTitle.setText(rewardsEnt.getRewardDetail().getTitle() + "");
            Spanned desc = Html.fromHtml(rewardsEnt.getRewardDetail().getDescription());
            txtRewardDetail.setText(desc.toString().trim());
        }

        imageLoader.displayImage(rewardsEnt.getRewardDetail().getRewardImage(), ivOfferImage);
        txtActualAmount.setPaintFlags(txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtActualAmount.setText(prefHelper.getConvertedAmountCurrrency() + "" + rewardsEnt.getRewardDetail().getOriginalPrice() + "");
        txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + "" + rewardsEnt.getRewardDetail().getRewardPrice() + "");

        updatedActualPrice = Float.valueOf(rewardsEnt.getRewardDetail().getOriginalPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePrice = String.format("%.2f", updatedActualPrice);
        txtActualAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePrice + "");

        //viewHolder.txtAfterDiscount.setText("$" + entity.getRewardPrice() + "");
        updatedDiscountedPrice = Float.valueOf(rewardsEnt.getRewardDetail().getRewardPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
        txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

        final String finalTerms_condition = terms_condition;
        tvStartEndDate.setText(DateHelper.dateFormat(rewardsEnt.getRewardDetail().getStartDate(), AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD) + " - " + DateHelper.dateFormat(rewardsEnt.getRewardDetail().getEndDate(),AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD));
        trimmedSlug = rewardsEnt.getRewardDetail().getSlug().trim();
        if (rewardsEnt.getRewardDetail().getPoint().equals(0)){
            txtPointsAmount.setText("0");
        } else {
            txtPointsAmount.setText(rewardsEnt.getRewardDetail().getPoint());
        }

        txtActualAmount.setVisibility(View.GONE);
        txtAfterDiscount.setVisibility(View.GONE);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Rewards");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_Terms_Condition)
    public void onViewClicked() {
        Uri uri = Uri.parse(prefHelper.getRewardCms() + trimmedSlug);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        UIHelper.showLongToastInCenter(getDockActivity(), "You are being redirect to Treatz Asia website.");
    }
}

