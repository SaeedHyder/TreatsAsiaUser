package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.EvoucherEnt;
import com.app.usertreatzasia.entities.WalletEvoucherEnt;
import com.app.usertreatzasia.fragments.CouponDetailFragment;
import com.app.usertreatzasia.fragments.CouponWaletDetailFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.interfaces.FavoriteStatus;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;



public class HomeWalletItemBinder extends ViewBinder<WalletEvoucherEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private FavoriteStatus favoriteStatus;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;

    public HomeWalletItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, FavoriteStatus favoriteStatus) {
        super(R.layout.row_item_wallet_home);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.favoriteStatus = favoriteStatus;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new HomeWalletItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final WalletEvoucherEnt entity, int position, int grpPosition, View view, Activity activity) {

        final HomeWalletItemBinder.ViewHolder viewHolder = (HomeWalletItemBinder.ViewHolder) view.getTag();
        imageLoader = ImageLoader.getInstance();
        viewHolder.ivFavorite.setEnabled(false);

        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            viewHolder.txtDiscountDetail.setText(entity.getEvoucherDetail().getTitle() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            viewHolder.txtDiscountDetail.setText(entity.getEvoucherDetail().getInTitle() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            viewHolder.txtDiscountDetail.setText(entity.getEvoucherDetail().getMaTitle() + "");
        } else {
            viewHolder.txtDiscountDetail.setText(entity.getEvoucherDetail().getTitle() + "");
        }


        updatedDiscountedPrice = Float.valueOf(UtilsGlobal.getRemainingAmountWithoutDollar(Integer.parseInt(entity.getEvoucherDetail().getAmount()), Integer.parseInt(entity.getEvoucherDetail().getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
        viewHolder.txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency()+" "+formattedValuePriceDiscounted);

        imageLoader.displayImage(entity.getEvoucherDetail().getProductDetail().getProductImage(), viewHolder.offerImage);


        viewHolder.txtPaidFree.setText(UtilsGlobal.getVoucherType(entity.getEvoucherDetail().getType(), dockActivity));
        viewHolder.txtAddress.setText(entity.getEvoucherDetail().getLocation() + " ");
        viewHolder.txtPointsAmount.setText(entity.getEvoucherDetail().getPoint());
        if (entity.getEvoucherDetail().getEvoucherLike() == 1) {
            viewHolder.ivFavorite.setChecked(true);
        } else
            viewHolder.ivFavorite.setChecked(false);

        viewHolder.txtExpiresOn.setText(DateHelper.dateFormat(entity.getEvoucherDetail().getExpiryDate(), AppConstants.DateFormat_DmY, AppConstants.DateFormat_YMD) + "");

        viewHolder.btnVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouponWaletDetailFragment fragment = CouponWaletDetailFragment.newInstance();
                fragment.setContent(entity.getEvoucherId(), AppConstants.TYPE_HOME, entity.getEvoucherDetail().getPromo_url());
                dockActivity.replaceDockableFragment(fragment,
                        CouponWaletDetailFragment.class.getName());
            }
        });


        if (entity.getEvoucherDetail().getType().equals("promo_code")){
            viewHolder.ll_promo_code.setVisibility(View.VISIBLE);
            viewHolder.tv_promo_code.setText(entity.getEvoucherDetail().getPromoCode()+"");
            viewHolder.tv_promo_code.setTextColor(Color.WHITE);
            viewHolder.btnVoucher.setText("Get Promo Code");
        } else {
            viewHolder.ll_promo_code.setVisibility(View.GONE);
            viewHolder.tv_promo_code.setTextColor(ContextCompat.getColor(dockActivity, R.color.appGolden));
            viewHolder.btnVoucher.setText("REDEEM");
        }


    }

    static class ViewHolder extends ViewBinder.BaseViewHolder {

        @BindView(R.id.offerImage)
        ImageView offerImage;
        @BindView(R.id.txt_paid_free)
        AnyTextView txtPaidFree;
        @BindView(R.id.txtDiscountDetail)
        AnyTextView txtDiscountDetail;
        @BindView(R.id.iv_favorite)
        CheckBox ivFavorite;

        @BindView(R.id.txtAfterDiscount)
        AnyTextView txtAfterDiscount;
        @BindView(R.id.txtExpiresOn)
        AnyTextView txtExpiresOn;
        @BindView(R.id.txtAddress)
        AnyTextView txtAddress;
        @BindView(R.id.btn_Voucher)
        Button btnVoucher;
        @BindView(R.id.tv_promo_code)
        AnyTextView tv_promo_code;
        @BindView(R.id.ll_promo_code)
        LinearLayout ll_promo_code;
        @BindView(R.id.txtPointsAmount)
        AnyTextView txtPointsAmount;
        @BindView(R.id.ll_amount_points)
        LinearLayout ll_amount_points;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
