package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.graphics.Paint;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.RewardsEnt;
import com.app.usertreatzasia.fragments.CouponDetailFragment;
import com.app.usertreatzasia.fragments.PaymentMethodFragment;
import com.app.usertreatzasia.fragments.PaymentQuantityFragment;
import com.app.usertreatzasia.fragments.RewardsDetailHomeFragment;
import com.app.usertreatzasia.fragments.RewardsTermsAndConditionsFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RewardsItemBinder extends ViewBinder<RewardsEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    String goToHome;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;

    public RewardsItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper) {

        super(R.layout.row_item_rewards);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
    }

    ImageLoader imageLoader;

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);

    }

    @Override
    public void bindView(final RewardsEnt entity, int position, int grpPosition, View view, Activity activity) {
        imageLoader = ImageLoader.getInstance();
        final RewardsItemBinder.ViewHolder viewHolder = (RewardsItemBinder.ViewHolder) view.getTag();

        String terms_condition="";
        goToHome = "goToHome";
        viewHolder.txtActualAmount.setVisibility(View.GONE);
        viewHolder.txtAfterDiscount.setVisibility(View.GONE);
        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            viewHolder.txtRewardTitle.setText(entity.getTitle() + "");
            Spanned desc = Html.fromHtml(entity.getDescription());
            viewHolder.txtRewardDetail.setText(desc.toString().trim());
            terms_condition=Html.fromHtml(entity.getTermCondition())+"";

        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            viewHolder.txtRewardTitle.setText(entity.getInTitle() + "");
            Spanned desc = Html.fromHtml(entity.getInDescription());
            viewHolder.txtRewardDetail.setText(desc.toString().trim());
            terms_condition=Html.fromHtml(entity.getInTermCondition())+"";

        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            viewHolder.txtRewardTitle.setText(entity.getMaTitle()+"");
            Spanned desc = Html.fromHtml(entity.getMaDescription());
            viewHolder.txtRewardDetail.setText(desc.toString().trim());
            terms_condition=Html.fromHtml(entity.getMaTermCondition())+"";
        } else {
            viewHolder.txtRewardTitle.setText(entity.getTitle() + "");
            Spanned desc = Html.fromHtml(entity.getDescription());
            viewHolder.txtRewardDetail.setText(desc.toString().trim());
            terms_condition=Html.fromHtml(entity.getTermCondition())+"";
        }


        imageLoader.displayImage(entity.getRewardImage(), viewHolder.ivOfferImage);
        viewHolder.txtActualAmount.setPaintFlags(viewHolder.txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //viewHolder.txtActualAmount.setText("$" + entity.getOriginalPrice() + "");
        updatedActualPrice = Float.valueOf(entity.getOriginalPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePrice = String.format("%.2f", updatedActualPrice);
        viewHolder.txtActualAmount.setText(prefHelper.getConvertedAmountCurrrency() + " "  + formattedValuePrice + "");

        //viewHolder.txtAfterDiscount.setText("$" + entity.getRewardPrice() + "");
        updatedDiscountedPrice = Float.valueOf(entity.getRewardPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
        viewHolder.txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + " "  + formattedValuePriceDiscounted + "");
        //viewHolder.txtAfterDiscount.setText(UtilsGlobal.getRemainingAmount(Integer.parseInt(entity.getRewardPrice()), Integer.parseInt(entity.getOriginalPrice())));

        final String finalTerms_condition = terms_condition;
        viewHolder.btnTermsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dockActivity.replaceDockableFragment(RewardsDetailHomeFragment.newInstance(entity), "RewardsDetailHomeFragment");
                //dockActivity.replaceDockableFragment(PaymentQuantityFragment.newInstance(entity), "PaymentQuantityFragment");
            }
        });

        viewHolder.tv_start_end_date.setText(DateHelper.dateFormat(entity.getStartDate(), AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD) + " - " + DateHelper.dateFormat(entity.getEndDate(),AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD));

        if (entity.getPoint()==""){
            viewHolder.txtPointsAmount.setText("0");
        }else {
            viewHolder.txtPointsAmount.setText(entity.getPoint());
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
