package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.WalletGiftEnt;
import com.app.usertreatzasia.fragments.CouponWaletDetailFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GiftRecievedBinder extends ViewBinder<WalletGiftEnt> {

    private DockActivity dockActivity;
    private ImageLoader imageLoader;
    private BasePreferenceHelper prefHelper;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;

    public GiftRecievedBinder(DockActivity dockActivity, BasePreferenceHelper preferenceHelper) {
        super(R.layout.item_gift_wallet);
        this.dockActivity = dockActivity;
        imageLoader = ImageLoader.getInstance();
        prefHelper = preferenceHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final WalletGiftEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        imageLoader = ImageLoader.getInstance();
        viewHolder.txtActualAmount.setVisibility(View.GONE);
        viewHolder.txtAfterDiscount.setVisibility(View.GONE);
        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            viewHolder.txtDiscountDetail.setText(entity.getEvoucherDetail().getTitle() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            viewHolder.txtDiscountDetail.setText(entity.getEvoucherDetail().getInTitle() + "");
        } else {
            viewHolder.txtDiscountDetail.setText(entity.getEvoucherDetail().getMaTitle() + "");
        }

        imageLoader.displayImage(entity.getEvoucherDetail().getProductDetail().getProductImage(), viewHolder.offerImage);
        viewHolder.txtActualAmount.setPaintFlags(viewHolder.txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.txtActualAmount.setText("$" + entity.getEvoucherDetail().getProductDetail().getPrice() + "");
        viewHolder.txtAfterDiscount.setText("$" + UtilsGlobal.getRemainingAmountForRedeemCoupon(Integer.parseInt(entity.getEvoucherDetail().getProductDetail().getPrice()), Integer.parseInt(entity.getEvoucherDetail().getAmount())));

        updatedActualPrice = Float.valueOf(entity.getEvoucherDetail().getProductDetail().getPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePrice = String.format("%.2f", updatedActualPrice);
        viewHolder.txtActualAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePrice + "");

        //viewHolder.txtAfterDiscount.setText("$" + entity.getRewardPrice() + "");
        updatedDiscountedPrice = Float.valueOf(UtilsGlobal.getRemainingAmountForRedeemCoupon(Integer.parseInt(entity.getEvoucherDetail().getProductDetail().getPrice()), Integer.parseInt(entity.getEvoucherDetail().getAmount()))) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
        viewHolder.txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");


        viewHolder.txtPaidFree.setText(UtilsGlobal.getVoucherType(entity.getEvoucherDetail().getType(), dockActivity));
        viewHolder.txtAddress.setText(entity.getEvoucherDetail().getLocation() + "");

        viewHolder.txtExpiresOn.setText(DateHelper.dateFormat(entity.getEvoucherDetail().getExpiryDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");

        viewHolder.txtGiftByName.setText(entity.getGiftUserDetail().getFirstName());
        viewHolder.btnVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouponWaletDetailFragment fragment = CouponWaletDetailFragment.newInstance();
                fragment.setContent(entity.getEvoucherId(), AppConstants.TYPE_GIFT, entity.getEvoucherDetail().getPromoUrl());
                dockActivity.replaceDockableFragment(fragment,
                        CouponWaletDetailFragment.class.getName());
            }
        });
        prefHelper.putLikeCountEvoucher(entity.getEvoucherDetail().getEvoucher_like());

        viewHolder.txtActualAmount.setVisibility(View.GONE);
        viewHolder.txtAfterDiscount.setVisibility(View.GONE);
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.offerImage)
        ImageView offerImage;
        @BindView(R.id.txt_paid_free)
        AnyTextView txtPaidFree;
        @BindView(R.id.txtDiscountDetail)
        AnyTextView txtDiscountDetail;
        @BindView(R.id.txtActualAmount)
        AnyTextView txtActualAmount;
        @BindView(R.id.txtAfterDiscount)
        AnyTextView txtAfterDiscount;
        @BindView(R.id.txtGiftBy)
        AnyTextView txtGiftBy;
        @BindView(R.id.txtGiftByName)
        AnyTextView txtGiftByName;
        @BindView(R.id.txtExpiresOn)
        AnyTextView txtExpiresOn;
        @BindView(R.id.txtAddress)
        AnyTextView txtAddress;
        @BindView(R.id.btn_Voucher)
        Button btnVoucher;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
