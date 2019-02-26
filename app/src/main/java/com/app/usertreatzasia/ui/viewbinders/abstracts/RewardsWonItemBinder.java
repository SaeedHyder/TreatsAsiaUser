package com.app.usertreatzasia.ui.viewbinders.abstracts;


import android.app.Activity;
import android.graphics.Paint;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.RewardsWonEnt;
import com.app.usertreatzasia.fragments.PaymentMethodFragment;
import com.app.usertreatzasia.fragments.WonDetailFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardsWonItemBinder  extends ViewBinder<RewardsWonEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    String goToHome;
    boolean checkerButton;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;

    public RewardsWonItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, boolean CheckerButton) {

        super(R.layout.row_item_won);
        this.dockActivity = dockActivity;
        checkerButton =CheckerButton;
        this.prefHelper = prefHelper;
    }

    ImageLoader imageLoader;

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new RewardsWonItemBinder.ViewHolder(view);

    }

    @Override
    public void bindView(final RewardsWonEnt entity, int position, int grpPosition, View view, Activity activity) {
        imageLoader = ImageLoader.getInstance();
        final RewardsWonItemBinder.ViewHolder viewHolder = (RewardsWonItemBinder.ViewHolder) view.getTag();
        viewHolder.txtActualAmount.setVisibility(View.GONE);
        viewHolder.txtAfterDiscount.setVisibility(View.GONE);
        String terms_condition="";
        goToHome = "goToHome";

        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            viewHolder.txtRewardTitle.setText(entity.getRewardDetail().getTitle() + "");
            viewHolder.txtRewardDetail.setText(Html.fromHtml(entity.getRewardDetail().getDescription())+"");
            terms_condition=Html.fromHtml(entity.getRewardDetail().getTermCondition())+"";

        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            viewHolder.txtRewardTitle.setText(entity.getRewardDetail().getInTitle() + "");
            viewHolder.txtRewardDetail.setText(Html.fromHtml(entity.getRewardDetail().getInDescription())+"");
            terms_condition=Html.fromHtml(entity.getRewardDetail().getInTermCondition())+"";

        } else {
            viewHolder.txtRewardTitle.setText(entity.getRewardDetail().getMaTitle()+"");
            viewHolder.txtRewardDetail.setText(Html.fromHtml(entity.getRewardDetail().getMaDescription())+"");
            terms_condition=Html.fromHtml(entity.getRewardDetail().getMaTermCondition())+"";
        }


        imageLoader.displayImage(entity.getRewardDetail().getRewardImage(), viewHolder.ivOfferImage);
        viewHolder.txtActualAmount.setPaintFlags(viewHolder.txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //viewHolder.txtActualAmount.setText("$" + entity.getRewardDetail().getOriginalPrice() + "");
        //viewHolder.txtAfterDiscount.setText("$" + entity.getRewardDetail().getRewardPrice() + "");

        updatedActualPrice = Float.valueOf(entity.getRewardDetail().getOriginalPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePrice = String.format("%.2f", updatedActualPrice);
        viewHolder.txtActualAmount.setText(prefHelper.getConvertedAmountCurrrency() + " "  + formattedValuePrice + "");

        updatedDiscountedPrice = Float.valueOf(entity.getRewardDetail().getRewardPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
        viewHolder.txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + " "  + formattedValuePriceDiscounted + "");

        final String finalTerms_condition = terms_condition;

        if (checkerButton){
            viewHolder.btnTermsCondition.setVisibility(View.VISIBLE);
        } else {
            viewHolder.btnTermsCondition.setVisibility(View.GONE);
        }
        viewHolder.btnTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dockActivity.replaceDockableFragment(WonDetailFragment.newInstance(entity), "WonDetailFragment");
            }
        });
        viewHolder.tv_start_end_date.setText(DateHelper.dateFormat(entity.getRewardDetail().getStartDate(), AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD) + " - " + DateHelper.dateFormat(entity.getRewardDetail().getEndDate(),AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD));

        if (entity.getRewardDetail().getPoint().equals(0)){
            viewHolder.txtPointsAmount.setText("0");
        } else {
            viewHolder.txtPointsAmount.setText(entity.getRewardDetail().getPoint());
        }

        viewHolder.txtActualAmount.setVisibility(View.GONE);
        viewHolder.txtAfterDiscount.setVisibility(View.GONE);
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_offerImage)
        ImageView ivOfferImage;
        @BindView(R.id.txtRewardTitle)
        AnyTextView txtRewardTitle;
        @BindView(R.id.txtActualAmount)
        AnyTextView txtActualAmount;
        @BindView(R.id.txtAfterDiscount)
        AnyTextView txtAfterDiscount;
        @BindView(R.id.txtRewardDetail)
        AnyTextView txtRewardDetail;
        @BindView(R.id.btn_Terms_Condition)
        Button btnTermsCondition;
        @BindView(R.id.txtPointsAmount)
        AnyTextView txtPointsAmount;
        @BindView(R.id.tv_start_end_date)
        AnyTextView tv_start_end_date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
