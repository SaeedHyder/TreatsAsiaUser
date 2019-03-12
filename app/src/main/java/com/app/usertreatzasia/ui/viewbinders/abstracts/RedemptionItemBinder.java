package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.RedemptionEnt;
import com.app.usertreatzasia.fragments.RemainingAmountFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RedemptionItemBinder extends ViewBinder<RedemptionEnt> {

    DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;
    Float updatedRemainingAmount;


    public RedemptionItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper) {
        super(R.layout.row_item_redemption);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);

    }

    @Override
    public void bindView(final RedemptionEnt entity, int position, int grpPosition, View view, Activity activity) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        imageLoader = ImageLoader.getInstance();

        viewHolder.txtActualAmount.setVisibility(View.GONE);
        viewHolder.txtAfterDiscount.setVisibility(View.GONE);


        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            viewHolder.txtOfferTitle.setText(entity.getEvoucherDetail().getTitle() + "");

        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            viewHolder.txtOfferTitle.setText(entity.getEvoucherDetail().getInTitle() + "");

        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            viewHolder.txtOfferTitle.setText(entity.getEvoucherDetail().getMaTitle() + "");
        } else {
            viewHolder.txtOfferTitle.setText(entity.getEvoucherDetail().getTitle() + "");
        }

        imageLoader.displayImage(entity.getEvoucherDetail().getProductDetail().getProductImage(), viewHolder.ivOfferImage);


        viewHolder.txtAfterDiscount.setText("$" + UtilsGlobal.getRemainingAmountForRedeemCoupon(Integer.parseInt((entity.getEvoucherDetail().getAmount())), Integer.parseInt(entity.getEvoucherDetail().getProductDetail().getPrice())));
        viewHolder.txtActualAmount.setPaintFlags(viewHolder.txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.txtActualAmount.setText("$" + entity.getEvoucherDetail().getProductDetail().getPrice() + "");

        updatedActualPrice = Float.valueOf(entity.getEvoucherDetail().getProductDetail().getPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePrice = String.format("%.2f", updatedActualPrice);
        viewHolder.txtActualAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePrice + "");

        //viewHolder.txtAfterDiscount.setText("$" + entity.getRewardPrice() + "");
        updatedDiscountedPrice = Float.valueOf(UtilsGlobal.getRemainingAmountForRedeemCoupon(Integer.parseInt((entity.getEvoucherDetail().getAmount())), Integer.parseInt(entity.getEvoucherDetail().getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
        final String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
        viewHolder.txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

        viewHolder.txtRedemptionDate.setText(DateHelper.dateFormat(entity.getPurchaseDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");


        if (entity.getRemainingAmount().equalsIgnoreCase("")) {
            viewHolder.txtAmountRemainings.setText(prefHelper.getConvertedAmountCurrrency() + " " + "0.00");
        } else {
            // viewHolder.txtAmountRemainings.setText("$ "+entity.getRemainingAmount());
            updatedRemainingAmount = Float.valueOf(entity.getRemainingAmount()) * prefHelper.getConvertedAmount();
            String formattedValue = String.format("%.2f", updatedRemainingAmount);
            viewHolder.txtAmountRemainings.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
        }

        viewHolder.ll_main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = "";
                if (!entity.getRemainingAmount().equalsIgnoreCase("")) {
                    Float amount = Float.valueOf(entity.getRemainingAmount()) * prefHelper.getConvertedAmount();
                    value = String.format("%.2f", amount);
                }
                dockActivity.replaceDockableFragment(RemainingAmountFragment.newInstance(value, entity.getEvoucherDetail().getProductDetail().getProductImage()), RemainingAmountFragment.class.getName());
            }
        });


    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_offerImage)
        ImageView ivOfferImage;
        @BindView(R.id.txtOfferTitle)
        AnyTextView txtOfferTitle;
        @BindView(R.id.txtActualAmount)
        AnyTextView txtActualAmount;
        @BindView(R.id.txtAfterDiscount)
        AnyTextView txtAfterDiscount;
        @BindView(R.id.txtRedemptionDate)
        AnyTextView txtRedemptionDate;
        @BindView(R.id.txtAmountRemainings)
        AnyTextView txtAmountRemainings;
        @BindView(R.id.ll_AmountRemaining)
        LinearLayout llAmountRemaining;
        @BindView(R.id.ll_main_layout)
        LinearLayout ll_main_layout;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
