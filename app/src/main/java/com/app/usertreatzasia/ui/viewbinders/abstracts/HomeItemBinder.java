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
import com.app.usertreatzasia.entities.EvoucherEnt;
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


public class HomeItemBinder extends ViewBinder<EvoucherEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private FavoriteStatus favoriteStatus;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;

    public HomeItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, FavoriteStatus favoriteStatus) {
        super(R.layout.row_item_home);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.favoriteStatus = favoriteStatus;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final EvoucherEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        imageLoader = ImageLoader.getInstance();
        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            viewHolder.txtDiscountDetail.setText(entity.getTitle() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            viewHolder.txtDiscountDetail.setText(entity.getInTitle() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            viewHolder.txtDiscountDetail.setText(entity.getMaTitle() + "");
        }

        updatedDiscountedPrice = Float.valueOf(UtilsGlobal.getRemainingAmountWithoutDollar(Integer.parseInt(entity.getAmount()), Integer.parseInt(entity.getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);

        viewHolder.txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency()+" "+formattedValuePriceDiscounted);

        imageLoader.displayImage(entity.getProductDetail().getProductImage(), viewHolder.offerImage);

        viewHolder.txtPaidFree.setText(UtilsGlobal.getVoucherType(entity.getType(), dockActivity));
        viewHolder.txtAddress.setText(entity.getLocation() + "");

        viewHolder.ll_points_1.setVisibility(View.VISIBLE);
        viewHolder.txtPoints1.setText("Points");
        viewHolder.txtPointsAmount1.setText(entity.getPoint());

        if (entity.getEvoucherLike() == 1) {
            viewHolder.ivFavorite.setChecked(true);
        } else
            viewHolder.ivFavorite.setChecked(false);

        viewHolder.txtExpiresOn.setText(DateHelper.dateFormat(entity.getExpiryDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");

        viewHolder.btnVoucher.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                CouponDetailFragment fragment = CouponDetailFragment.newInstance();
                fragment.setContent(entity.getId(), AppConstants.TYPE_HOME);
                dockActivity.replaceDockableFragment(fragment,
                        CouponDetailFragment.class.getName());
            }
        });

        viewHolder.ivFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                favoriteStatus.eVoucherLike(entity.getId(), isChecked);

            }
        });

        if (entity.getTypeSelect().equals("promo_code")) {
            viewHolder.ll_promo_code.setVisibility(View.VISIBLE);
            viewHolder.btnVoucher.setText(R.string.get_a_promo);
        } else {
            viewHolder.ll_promo_code.setVisibility(View.GONE);
            viewHolder.btnVoucher.setText(R.string.get_a_voucher);
        }
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
        @BindView(R.id.txtPointsAmount1)
        AnyTextView txtPointsAmount1;
        @BindView(R.id.txtPoints1)
        AnyTextView txtPoints1;
        @BindView(R.id.ll_points_1)
        LinearLayout ll_points_1;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
