package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.graphics.Paint;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.RewardsWalletEntity;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RewardsWalletItemBinder extends ViewBinder<RewardsWalletEntity> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;

    public RewardsWalletItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper) {

        super(R.layout.row_item_wallet_reward);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
    }

    ImageLoader imageLoader;

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final RewardsWalletEntity entity, int position, int grpPosition, View view, Activity activity) {
        imageLoader = ImageLoader.getInstance();
        final RewardsWalletItemBinder.ViewHolder viewHolder = (RewardsWalletItemBinder.ViewHolder) view.getTag();

        String terms_condition = "";
        viewHolder.txtActualAmount.setVisibility(View.GONE);
        viewHolder.txtAfterDiscount.setVisibility(View.GONE);
        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            viewHolder.txtRewardTitle.setText(entity.getRewardDetail().getTitle() + "");
            Spanned desc = Html.fromHtml(entity.getRewardDetail().getDescription());
            viewHolder.txtRewardDetail.setText(desc.toString().trim());

/*            terms_condition = Html.fromHtml(entity.getRewardDetail().getTermCondition()) + "";
            Spanned desc2 = Html.fromHtml(entity.getRewardDetail().getTermCondition());
            viewHolder.txtRewardDetail.setText(desc2.toString().trim());*/

        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            viewHolder.txtRewardTitle.setText(entity.getRewardDetail().getInTitle() + "");
            Spanned desc = Html.fromHtml(entity.getRewardDetail().getInDescription());
            viewHolder.txtRewardDetail.setText(desc.toString().trim());

        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            viewHolder.txtRewardTitle.setText(entity.getRewardDetail().getMaTitle() + "");
            Spanned desc = Html.fromHtml(entity.getRewardDetail().getMaDescription());
            viewHolder.txtRewardDetail.setText(desc.toString().trim());
        } else {
            viewHolder.txtRewardTitle.setText(entity.getRewardDetail().getTitle() + "");
            Spanned desc = Html.fromHtml(entity.getRewardDetail().getDescription());
            viewHolder.txtRewardDetail.setText(desc.toString().trim());
        }


        imageLoader.displayImage(entity.getRewardDetail().getRewardImage(), viewHolder.ivOfferImage);
        viewHolder.txtActualAmount.setPaintFlags(viewHolder.txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.txtActualAmount.setText("$" + entity.getRewardDetail().getOriginalPrice() + "");
        viewHolder.txtAfterDiscount.setText("$" + entity.getRewardDetail().getRewardPrice() + "");
        viewHolder.txtPointsAmount.setText(entity.getRewardDetail().getPoint());

        updatedActualPrice = Float.valueOf(entity.getRewardDetail().getOriginalPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePrice = String.format("%.2f", updatedActualPrice);
        viewHolder.txtActualAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePrice + "");

        //viewHolder.txtAfterDiscount.setText("$" + entity.getRewardPrice() + "");
        updatedDiscountedPrice = Float.valueOf(entity.getRewardDetail().getRewardPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
        viewHolder.txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

        if (entity.getQuantity().equalsIgnoreCase("1")) {
            viewHolder.txtChances.setText("You have " + 1 + " Chance");
        } else {
            viewHolder.txtChances.setText("You have " + entity.getQuantity() + " Chances");
        }

        viewHolder.tv_start_end_date.setText
                (DateHelper.dateFormat(entity.getRewardDetail().getStartDate(), AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD)
                        + " - " +
                 DateHelper.dateFormat(entity.getRewardDetail().getEndDate(), AppConstants.DateFormat_DM, AppConstants.DateFormat_YMD));
        final String finalTerms_condition = terms_condition;

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
        @BindView(R.id.txtPointsAmount)
        AnyTextView txtPointsAmount;
        @BindView(R.id.txtChances)
        AnyTextView txtChances;
        @BindView(R.id.tv_start_end_date)
        AnyTextView tv_start_end_date;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}