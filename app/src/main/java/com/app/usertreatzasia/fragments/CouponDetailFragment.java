package com.app.usertreatzasia.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.EvoucherEnt;
import com.app.usertreatzasia.entities.RedeemEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponDetailFragment extends BaseFragment {
    private static String Key_ID = "ID";
    private static String Key_TYPE = "TYPE";
    @BindView(R.id.iv_couponImage)
    ImageView ivCouponImage;
    @BindView(R.id.txt_paid_type)
    AnyTextView txtPaidType;
    @BindView(R.id.txt_couponDetail)
    AnyTextView txtCouponDetail;

    @BindView(R.id.txtAfterDiscount)
    AnyTextView txtAfterDiscount;
    @BindView(R.id.txt_expire_time)
    AnyTextView txtExpireTime;
    @BindView(R.id.txt_address)
    AnyTextView txtAddress;
    @BindView(R.id.txt_remaining_qty)
    AnyTextView txtRemainingQty;
    @BindView(R.id.btn_redeem)
    Button btnRedeem;
    @BindView(R.id.txt_termsDetails)
    AnyTextView txtTermsDetails;
    @BindView(R.id.img_barcode)
    ImageView imgBarcode;
    /*    @BindView(R.id.txtPointsAmount)
        AnyTextView txtPointsAmount;
        @BindView(R.id.txtPoints)
        AnyTextView txtPoints;*/
    @BindView(R.id.ll_points_1)
    LinearLayout ll_points_1;
    @BindView(R.id.txtPointsAmount1)
    AnyTextView txtPointsAmount1;
    @BindView(R.id.txtPoints1)
    AnyTextView txtPoints1;
    @BindView(R.id.tv_promo)
    AnyTextView tvPromo;
    @BindView(R.id.tv_promo_code)
    AnyTextView tvPromoCode;
    @BindView(R.id.ll_promo_code)
    LinearLayout llPromoCode;
    @BindView(R.id.llMainFrame)
    LinearLayout llMainFrame;

    private int ID;
    private String Type;
    private String goToHome;
    String TermsAndCondition;
    EvoucherEnt global;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;
    Float updatedCash;
    boolean buttonChecker = false;

    public static CouponDetailFragment newInstance() {
        return new CouponDetailFragment();
    }

    public static CouponDetailFragment newInstance(Integer ID, String Type) {
        Bundle b = new Bundle();
        CouponDetailFragment fragment = new CouponDetailFragment();
        b.putInt(Key_ID, ID);
        b.putString(Key_TYPE, Type);
        fragment.setArguments(b);
        return fragment;
    }

    public void setContent(int id, String type) {
        setID(id);
        setType(type);
        //  return this;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.printf("" + ID + ":" + Type);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
//        RedeemCouponFragment fragment;
        switch (Tag) {
            case WebServiceConstants.FlashSaleDetail:
                llMainFrame.setVisibility(View.VISIBLE);
                setDetailData((EvoucherEnt) result);
                global = (EvoucherEnt) result;
                break;
            case WebServiceConstants.CouponDetail:
                llMainFrame.setVisibility(View.VISIBLE);
                setDetailData((EvoucherEnt) result);
                global = (EvoucherEnt) result;
                break;
            case WebServiceConstants.BuyCoupon:
                imgBarcode.setVisibility(View.VISIBLE);
                btnRedeem.setVisibility(View.GONE);
                //     Picasso.with(getDockActivity()).load(((RedeemEnt) result).getQr_code_url()).fit().into(imgBarcode);
                ImageLoader.getInstance().displayImage(((RedeemEnt) result).getQr_code_url(), imgBarcode);
               /* fragment = RedeemCouponFragment.newInstance();
                fragment.setContent(ID, Type);
                getDockActivity().replaceDockableFragment(fragment,
                        RedeemCouponFragment.class.getSimpleName());*/
                break;
            case WebServiceConstants.BuyFlashSale:
                imgBarcode.setVisibility(View.VISIBLE);
                btnRedeem.setVisibility(View.GONE);
                //Picasso.with(getDockActivity()).load(((RedeemEnt) result).getQr_code_url()).into(imgBarcode);
                ImageLoader.getInstance().displayImage(((RedeemEnt) result).getQr_code_url(), imgBarcode);
               /* fragment = RedeemCouponFragment.newInstance();
                fragment.setContent(ID, Type);
                getDockActivity().replaceDockableFragment(fragment,
                        RedeemCouponFragment.class.getSimpleName());*/
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showShareButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = global.getTitle() + "http://Treatzasia.com";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getDockActivity().getResources().getString(R.string.share)));
            }
        });
        titleBar.showFavoriteButton(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              /*  if (entity.getEvent_like() == 1 ) {
                    buttonView.setChecked(true);
                } else
                    buttonView.setChecked(false);*/

                serviceHelper.enqueueCall(webService.evoucherLike(/*entity.getId()*/ID, isChecked ? 1 : 0, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTLIKE);
            }

        });
        titleBar.setSubHeading(getDockActivity().getString(R.string.voucher));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon_detail, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llMainFrame.setVisibility(View.GONE);
        buttonChecker = false;
        if (prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
            serviceHelper.enqueueCall(webService.getCouponDetail(ID + "", prefHelper.getSignUpUser().getToken()), WebServiceConstants.CouponDetail);


        goToHome = "goToHome";


    }

    @Override
    public void onResume() {
        super.onResume();

        buttonChecker = false;
        if (prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
            serviceHelper.enqueueCall(webService.getCouponDetail(ID + "", prefHelper.getSignUpUser().getToken()), WebServiceConstants.CouponDetail);



    }

    private void setDetailData(EvoucherEnt result) {
        if (result != null) {
            if (result.getType() != null)
                txtPaidType.setText(UtilsGlobal.getVoucherType(result.getType() + "", getDockActivity()));


            if (result.getEvoucherLike() != null) {
                if (result.getEvoucherLike() == 1) {
                    if (getMainActivity() != null && getTitleBar() != null)
                        getTitleBar().getCheckbox().setChecked(true);
                } else if (getMainActivity() != null && getTitleBar() != null)
                    getTitleBar().getCheckbox().setChecked(false);
            }



            if (result.getAmount() != null && result.getProductDetail() != null && result.getProductDetail().getPrice() != null && prefHelper != null && prefHelper.getConvertedAmount() != null) {
                updatedDiscountedPrice = Float.valueOf(UtilsGlobal.getRemainingAmountWithoutDollar(Integer.parseInt(result.getAmount()), Integer.parseInt(result.getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
                String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
                txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");
            }



            if (result.getExpiryDate() != null)
                txtExpireTime.setText(getDockActivity().getString(R.string.expires_on_detail) + " " + DateHelper.getFormatedDate("yyyy-MM-dd", "MMM dd yyyy",
                        result.getExpiryDate() + ""));
            if (result.getCount() != null)
                txtRemainingQty.setText(getDockActivity().getString(R.string.remaining_qty) + " " + result.getCount()); //result.getCount());

            if (result.getProductDetail() != null && result.getProductDetail().getProductImage() != null)
                ImageLoader.getInstance().displayImage(result.getProductDetail().getProductImage(), ivCouponImage);

            if (result.getLocation() != null)
                txtAddress.setText(result.getLocation() + " ");
            if (result.getTermCondition() != null)
                txtTermsDetails.setText(Html.fromHtml(result.getTermCondition()));
            if (result.getTypeSelect() != null) {
                if (result.getTypeSelect().equals("promo_code")) {
                    llPromoCode.setVisibility(View.VISIBLE);
                    btnRedeem.setText("Add to Wallet");
                } else {
                    llPromoCode.setVisibility(View.GONE);
                    btnRedeem.setText("Add to Wallet");
                }
            }
            ll_points_1.setVisibility(View.VISIBLE);
            if (result.getPoint() != null)
                txtPointsAmount1.setText(result.getPoint() + "");




            if (prefHelper != null && prefHelper.getSelectedLanguage() != null && result.getTitle() != null) {
                if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
                    txtCouponDetail.setText(result.getTitle() + "");
                } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
                    txtCouponDetail.setText(result.getInTitle() + "");
                } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
                    txtCouponDetail.setText(result.getMaTitle() + "");
                } else {
                    txtCouponDetail.setText(result.getTitle() + "");
                }
            }
        }
        buttonChecker = true;
    }

    @OnClick({R.id.btn_redeem})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_redeem:
                if (buttonChecker) {
                    if (global != null) {
                        getDockActivity().replaceDockableFragment(PaymentChooseFragment.newInstance(global, true), "PaymentChooseFragment");
                    }
                }
                break;
        }
    }
}
