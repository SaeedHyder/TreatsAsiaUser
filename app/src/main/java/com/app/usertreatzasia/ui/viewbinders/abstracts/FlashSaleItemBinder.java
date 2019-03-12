package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.FlashSaleEnt;
import com.app.usertreatzasia.entities.LikeCountEntity;
import com.app.usertreatzasia.fragments.CouponDetailFlashFragment;
import com.app.usertreatzasia.fragments.CouponDetailFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.interfaces.FavoriteStatus;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FlashSaleItemBinder extends ViewBinder<FlashSaleEnt> {

    DockActivity dockActivity;
    BasePreferenceHelper prefHelper;
    private FavoriteStatus favoriteStatus;
    LikeCountEntity likeCountEntity;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;
    Float updatedCash;

    public FlashSaleItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, FavoriteStatus favoriteStatus) {

        super(R.layout.row_item_flashsale);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.favoriteStatus = favoriteStatus;
    }

    ImageLoader imageLoader;

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final FlashSaleEnt entity, int position, int grpPosition, View view, Activity activity) {
        imageLoader = ImageLoader.getInstance();
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        imageLoader = ImageLoader.getInstance();
        //  String discount = UtilsGlobal.getVoucherType(entity.getType(),dockActivity) + " <b><font color='#000000'>" + entity.getAmount() + "% </font></b>" + activity.getString(R.string.off_on) + entity.getMerchantDetail().getFirstName() + " " + entity.getMerchantDetail().getLastName();

        //prefHelper.putLikeCountFlash(entity);

        /*viewHolder.txtActualAmount.setVisibility(View.GONE);
        viewHolder.txtAfterDiscount.setVisibility(View.GONE);*/

        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            viewHolder.txtDiscountDetail.setText(entity.getTitle() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            viewHolder.txtDiscountDetail.setText(entity.getInTitle() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            viewHolder.txtDiscountDetail.setText(entity.getMaTitle() + "");
        } else {
            viewHolder.txtDiscountDetail.setText(entity.getTitle() + "");
        }

        updatedDiscountedPrice = Float.valueOf(UtilsGlobal.getRemainingAmountWithoutDollar(Integer.parseInt(entity.getAmount()), Integer.parseInt(entity.getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);

        viewHolder.txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency()+" "+formattedValuePriceDiscounted);


        int discountAmount = (Integer.parseInt(entity.getProductDetail().getPrice()) / 100) * (Integer.parseInt(entity.getAmount()));
        int AfterDiscount = (Integer.parseInt(entity.getProductDetail().getPrice())) - discountAmount;
        imageLoader.displayImage(entity.getProductDetail().getProductImage(), viewHolder.offerImage);
      //  viewHolder.txtActualAmount.setPaintFlags(viewHolder.txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
      //  viewHolder.txtActualAmount.setText("$" + entity.getProductDetail().getPrice() + "");
        //viewHolder.txtAfterDiscount.setText("$" + AfterDiscount + "");
        //viewHolder.txtAfterDiscount.setText(UtilsGlobal.getRemainingAmount(Integer.parseInt(entity.getAmount()), Integer.parseInt(entity.getProductDetail().getPrice())));

        updatedActualPrice = Float.valueOf(entity.getProductDetail().getPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePrice = String.format("%.2f", updatedActualPrice);
       // viewHolder.txtActualAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePrice + "");

        //viewHolder.txtAfterDiscount.setText("$" + entity.getRewardPrice() + "");

       // viewHolder.txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

        viewHolder.txtPaidFree.setText(UtilsGlobal.getVoucherType(entity.getType(), dockActivity));
        viewHolder.txtAddress.setText(entity.getLocation() + " ");
        viewHolder.txtExpiresOn.setText(DateHelper.dateFormat(entity.getExpiryDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + " ");
        //viewHolder.txtPointsAmount.setText(entity.getPoint());

        if (entity.getEvoucherLike() != null) {
            if (entity.getEvoucherLike() == 1) {
                viewHolder.ivFavorite.setChecked(true);
            } else
                viewHolder.ivFavorite.setChecked(false);
        }

        viewHolder.btnVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CouponDetailFlashFragment fragment = CouponDetailFlashFragment.newInstance();
                fragment.setContent(entity.getId(), AppConstants.TYPE_FlASH);
                //dockActivity.replaceDockableFragment(fragment, CouponDetailFragment.class.getName());
                dockActivity.replaceDockableFragment(fragment, CouponDetailFlashFragment.class.getName());
            }
        });

        viewHolder.ivFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                favoriteStatus.flashSaleLike(entity.getId(), isChecked);
            }
        });

        if (entity.getType().equals("promo_code")) {
            viewHolder.llPromoCode.setVisibility(View.VISIBLE);
            viewHolder.btnVoucher.setText("Get Promo Code");
        } else {
            viewHolder.llPromoCode.setVisibility(View.GONE);
            viewHolder.btnVoucher.setText("Get A Voucher");
        }

        if (entity.getIsPoint() == 1 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 0 && entity.getIsFree() == 0) {
            // only points
            viewHolder.llPoints.setVisibility(View.VISIBLE);
            viewHolder.txtPoints.setText(entity.getPoint());

            viewHolder.llCredits.setVisibility(View.GONE);
            viewHolder.llCash.setVisibility(View.GONE);
            viewHolder.llFree.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 0 && entity.getIsFree() == 0) {
            // only credit
            viewHolder.llCredits.setVisibility(View.VISIBLE);
            viewHolder.txtCredits.setText(entity.getPoint());

            viewHolder.llPoints.setVisibility(View.GONE);
            viewHolder.llCash.setVisibility(View.GONE);
            viewHolder.llFree.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // only cash
            viewHolder.llCash.setVisibility(View.VISIBLE);
            viewHolder.txtCash.setText(entity.getPoint());

            updatedCash = Float.valueOf(entity.getPoint()) * prefHelper.getConvertedAmount();
            String formattedValue = String.format("%.2f", updatedCash);
            viewHolder.txtCash.setText(formattedValue + "");
            viewHolder.currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());

            viewHolder.llPoints.setVisibility(View.GONE);
            viewHolder.llCredits.setVisibility(View.GONE);
            viewHolder.llFree.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 0 && entity.getIsFree() == 1) {

            viewHolder.llFree.setVisibility(View.VISIBLE);

            viewHolder.llPoints.setVisibility(View.GONE);
            viewHolder.llCredits.setVisibility(View.GONE);
            viewHolder.llCash.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 1 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 0 && entity.getIsFree() == 0) {
            // only point and credit
            viewHolder.llPoints.setVisibility(View.VISIBLE);
            viewHolder.txtPoints.setText(entity.getPoint());
            viewHolder.llCredits.setVisibility(View.VISIBLE);
            viewHolder.txtCredits.setText(entity.getPoint());

            viewHolder.llFree.setVisibility(View.GONE);
            viewHolder.llCash.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 1 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // only point and cash
            viewHolder.llPoints.setVisibility(View.VISIBLE);
            viewHolder.txtPoints.setText(entity.getPoint());
            viewHolder.llCash.setVisibility(View.VISIBLE);
            viewHolder.txtCash.setText(entity.getPoint());
            updatedCash = Float.valueOf(entity.getPoint()) * prefHelper.getConvertedAmount();
            String formattedValue = String.format("%.2f", updatedCash);
            viewHolder.txtCash.setText(formattedValue + "");
            viewHolder.currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());
            viewHolder.llFree.setVisibility(View.GONE);
            viewHolder.llCredits.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // only credit and cash
            viewHolder.llCredits.setVisibility(View.VISIBLE);
            viewHolder.txtCredits.setText(entity.getPoint());
            viewHolder.llCash.setVisibility(View.VISIBLE);
            viewHolder.txtCash.setText(entity.getPoint());
            updatedCash = Float.valueOf(entity.getPoint()) * prefHelper.getConvertedAmount();
            String formattedValue = String.format("%.2f", updatedCash);
            viewHolder.txtCash.setText(formattedValue + "");
            viewHolder.currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());
            viewHolder.llFree.setVisibility(View.GONE);
            viewHolder.llPoints.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 1 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // all type accepted
            viewHolder.llCredits.setVisibility(View.VISIBLE);
            viewHolder.txtCredits.setText(entity.getPoint());
            viewHolder.llPoints.setVisibility(View.VISIBLE);
            viewHolder.txtPoints.setText(entity.getPoint());
            viewHolder.llCash.setVisibility(View.VISIBLE);
            viewHolder.txtCash.setText(entity.getPoint());
            updatedCash = Float.valueOf(entity.getPoint()) * prefHelper.getConvertedAmount();
            String formattedValue = String.format("%.2f", updatedCash);
            viewHolder.txtCash.setText(formattedValue + "");
            viewHolder.currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());
            viewHolder.llFree.setVisibility(View.GONE);
        }
        try {
            updatedCash = Float.valueOf(entity.getPoint()) * prefHelper.getConvertedAmount();
        }catch (Exception e){
            e.printStackTrace();
        }

        String formattedValue = String.format("%.2f", updatedCash);
        viewHolder.txtCash.setText(formattedValue + "");
        viewHolder.currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());


         /*if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            //   viewHolder.txtDiscountDetail.setText(Html.fromHtml(discount));
            if ((entity.getTitle() + "").contains("%")) {
                viewHolder.txtDiscountDetail.setText(UtilsGlobal.setSpanString(entity.getTitle(),
                        UtilsGlobal.startIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), entity.getTitle() + ""),
                        UtilsGlobal.endIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), entity.getTitle() + ""),
                        dockActivity));
            } else {
                viewHolder.txtDiscountDetail.setText(entity.getTitle() + "");
            }

            // viewHolder.txtDiscountDetail.setText(entity.getType() + " " + Html.fromHtml(discount)+ " off on " + entity.getMerchantDetail().getFirstName() + " "+entity.getMerchantDetail().getLastName());
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            if ((entity.getInTitle() + "").contains("%")) {
                viewHolder.txtDiscountDetail.setText(UtilsGlobal.setSpanString(entity.getInTitle(),
                        UtilsGlobal.startIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), entity.getInTitle() + ""),
                        UtilsGlobal.endIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), entity.getInTitle() + ""),
                        dockActivity));
            } else {
                viewHolder.txtDiscountDetail.setText(entity.getInTitle() + "");
            }
        } else {
            if ((entity.getMaTitle() + "").contains("%")) {
                viewHolder.txtDiscountDetail.setText(UtilsGlobal.setSpanString(entity.getMaTitle(),
                        UtilsGlobal.startIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), entity.getMaTitle() + ""),
                        UtilsGlobal.endIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), entity.getMaTitle() + ""),
                        dockActivity));
            } else {
                viewHolder.txtDiscountDetail.setText(entity.getMaTitle() + "");
            }
        }*/
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.offerImage)
        ImageView offerImage;
        @BindView(R.id.txt_paid_free)
        AnyTextView txtPaidFree;
        @BindView(R.id.txtDiscountDetail)
        AnyTextView txtDiscountDetail;
        @BindView(R.id.iv_favorite)
        CheckBox ivFavorite;
        @BindView(R.id.txtActualAmount)
        AnyTextView txtActualAmount;
        @BindView(R.id.txtAfterDiscount)
        AnyTextView txtAfterDiscount;
        @BindView(R.id.txtPoints)
        AnyTextView txtPoints;
        @BindView(R.id.ll_points)
        LinearLayout llPoints;
        @BindView(R.id.txtCredits)
        AnyTextView txtCredits;
        @BindView(R.id.ll_credits)
        LinearLayout llCredits;
        @BindView(R.id.txtCash)
        AnyTextView txtCash;
        @BindView(R.id.ll_cash)
        LinearLayout llCash;
        @BindView(R.id.ll_free)
        LinearLayout llFree;
        @BindView(R.id.tv_promo)
        AnyTextView tvPromo;
        @BindView(R.id.tv_promo_code)
        AnyTextView tvPromoCode;
        @BindView(R.id.ll_promo_code)
        LinearLayout llPromoCode;
        @BindView(R.id.txtExpiresOn)
        AnyTextView txtExpiresOn;
        @BindView(R.id.txtAddress)
        AnyTextView txtAddress;
        @BindView(R.id.btn_Voucher)
        Button btnVoucher;
        @BindView(R.id.currency_type_cash)
        AnyTextView currencyTypeCash;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
