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
import com.app.usertreatzasia.entities.FlashSaleEnt;
import com.app.usertreatzasia.entities.RedeemEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponDetailFlashFragment extends BaseFragment {
    private static String Key_ID = "ID";
    private static String Key_TYPE = "TYPE";
    @BindView(R.id.iv_couponImage)
    ImageView ivCouponImage;
    @BindView(R.id.txt_paid_free)
    AnyTextView txtPaidType;
    @BindView(R.id.txt_couponDetail)
    AnyTextView txtCouponDetail;
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
    @BindView(R.id.currency_type_cash)
    AnyTextView currencyTypeCash;
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
    @BindView(R.id.txt_expire_time)
    AnyTextView txtExpireTime;
    @BindView(R.id.txt_address)
    AnyTextView txtAddress;
    @BindView(R.id.txt_remaining_qty)
    AnyTextView txtRemainingQty;
    @BindView(R.id.img_barcode)
    ImageView imgBarcode;
    @BindView(R.id.btn_redeem)
    Button btnRedeem;
    @BindView(R.id.txt_termsDetails)
    AnyTextView txtTermsDetails;
    private int ID;
    private String Type;
    private String goToHome;
    private String CREDIT_AMOUNT = "0";
    private String POINT = "0";
    private String PAYMENT_TYPE = "free";
    private String SELECTED_TYPE = "free";
    String TermsAndCondition;
    String FormattedDate;
    FlashSaleEnt global;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;
    Float updatedCash;
    int discountAmount;
    int AfterDiscount;
    boolean buttonChecker = false;

    public static CouponDetailFlashFragment newInstance() {
        return new CouponDetailFlashFragment();
    }

    public static CouponDetailFlashFragment newInstance(Integer ID, String Type) {
        Bundle b = new Bundle();
        CouponDetailFlashFragment fragment = new CouponDetailFlashFragment();
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
                setDetailData((FlashSaleEnt) result);
                global = (FlashSaleEnt) result;
                break;
            case WebServiceConstants.BuyFlashSaleDetail:
                setDetailData((FlashSaleEnt) result);
                global = (FlashSaleEnt) result;
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
            case WebServiceConstants.BuyFlashSaleFree:
                UIHelper.showShortToastInCenter(getDockActivity(), "Flash Sale voucher added successfully.");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(), "HomeWalletFragment");
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
                //String shareBody = getResources().getString(R.string.share_msg);
                String shareBody = global.getTitle() + "http://Treatzasia.com";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getDockActivity().getResources().getString(R.string.share)));
            }
        });
        titleBar.showFavoriteButton(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                serviceHelper.enqueueCall(webService.flashSaleLike(ID, isChecked ? 1 : 0, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTLIKE);
            }

        });
        titleBar.setSubHeading(getDockActivity().getString(R.string.voucher));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashsale_coupon_detail, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtActualAmount.setVisibility(View.GONE);
        txtAfterDiscount.setVisibility(View.GONE);
        buttonChecker = false;
        if (prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
            serviceHelper.enqueueCall(webService.flashSaleDetail(ID + "", prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyFlashSaleDetail);

        txtActualAmount.setPaintFlags(txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        goToHome = "goToHome";

        SimpleDateFormat dateFormatYouWant = new SimpleDateFormat("yyyy-MM-dd");
        FormattedDate = dateFormatYouWant.format(new Date());

        txtActualAmount.setVisibility(View.GONE);
        txtAfterDiscount.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        txtActualAmount.setVisibility(View.GONE);
        txtAfterDiscount.setVisibility(View.GONE);
        buttonChecker = false;
        if (prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
            serviceHelper.enqueueCall(webService.flashSaleDetail(ID + "", prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyFlashSaleDetail);

        txtActualAmount.setPaintFlags(txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        goToHome = "goToHome";

        SimpleDateFormat dateFormatYouWant = new SimpleDateFormat("yyyy-MM-dd");
        FormattedDate = dateFormatYouWant.format(new Date());

        txtActualAmount.setVisibility(View.GONE);
        txtAfterDiscount.setVisibility(View.GONE);
    }

    private void setDetailData(FlashSaleEnt result) {

        if (result != null) {
            txtActualAmount.setVisibility(View.GONE);
            txtAfterDiscount.setVisibility(View.GONE);
            if (result != null && result.getEvoucherLike() != null) {
                if (result.getEvoucherLike() == 1) {
                    getTitleBar().getCheckbox().setChecked(true);
                } else {
                    getTitleBar().getCheckbox().setChecked(false);
                }
            }

            if (prefHelper != null && prefHelper.getSelectedLanguage() != null) {
                if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
                    txtCouponDetail.setText(result.getTitle() + "");
                    txtTermsDetails.setText(result.getTermCondition() + "");
                } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
                    txtCouponDetail.setText(result.getInTitle() + "");
                    txtTermsDetails.setText(result.getInTermCondition() + "");
                } else {
                    txtCouponDetail.setText(result.getMaTitle() + "");
                    txtTermsDetails.setText(result.getMaTermCondition() + "");
                }
            }

            if (result != null && result.getProductDetail() != null && result.getProductDetail().getPrice() != null && result.getAmount() != null) {
                discountAmount = (Integer.parseInt(result.getProductDetail().getPrice()) / 100) * (Integer.parseInt(result.getAmount()));
                AfterDiscount = (Integer.parseInt(result.getProductDetail().getPrice())) - discountAmount;
            }
            txtActualAmount.setPaintFlags(txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if (result.getProductDetail() != null && result.getProductDetail().getPrice() != null)
                txtActualAmount.setText("$" + result.getProductDetail().getPrice() + "");
            txtAfterDiscount.setText("$" + AfterDiscount + "");
            if (result.getAmount() != null && result.getProductDetail() != null && result.getProductDetail().getPrice() != null)
                txtAfterDiscount.setText(UtilsGlobal.getRemainingAmount(Integer.parseInt(result.getAmount()), Integer.parseInt(result.getProductDetail().getPrice())));

            if (result.getProductDetail() != null && result.getProductDetail().getPrice() != null && prefHelper != null && prefHelper.getConvertedAmount() != null)
                updatedActualPrice = Float.valueOf(result.getProductDetail().getPrice()) * prefHelper.getConvertedAmount();
            String formattedValuePrice = String.format("%.2f", updatedActualPrice);
            txtActualAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePrice + "");

            if (result.getAmount() != null && result.getProductDetail() != null && result.getProductDetail().getPrice() != null && prefHelper != null && prefHelper.getConvertedAmount() != null)
                updatedDiscountedPrice = Float.valueOf(UtilsGlobal.getRemainingAmountWithoutDollar(Integer.parseInt(result.getAmount()), Integer.parseInt(result.getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
            String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
            txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

            if (result.getType() != null)
                txtPaidType.setText(UtilsGlobal.getVoucherType(result.getType() + "", getDockActivity()));
            if (result.getExpiryDate() != null)
                txtExpireTime.setText(getDockActivity().getString(R.string.expires_on_detail) + " " + DateHelper.getFormatedDate("yyyy-MM-dd", "MMM dd yyyy",
                        result.getExpiryDate() + ""));
            if (result.getCount() != null)
                txtRemainingQty.setText(getDockActivity().getString(R.string.remaining_qty) + " " + result.getCount()); //result.getCount());
            //txtAfterDiscount.setText("$" + UtilsGlobal.getRemainingAmountForRedeemCoupon(Integer.parseInt(result.getProductDetail().getPrice()), Integer.parseInt(result.getAmount())));
            if (result.getProductDetail() != null && result.getProductDetail().getProductImage() != null)
                ImageLoader.getInstance().displayImage(result.getProductDetail().getProductImage(), ivCouponImage);
            //txtActualAmount.setPaintFlags(txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            //txtActualAmount.setText(getString(R.string.selected_current) + result.getProductDetail().getPrice() + "");
        /*txtAfterDiscount.setText(getString(R.string.selected_current) + AfterDiscount + "");*/
            if (result.getLocation() != null)
                txtAddress.setText(result.getLocation() + " ");
            txtTermsDetails.setText(Html.fromHtml(result.getTermCondition()));
            if (result.getType() != null) {
                if (result.getType().equals("promo_code")) {
                    llPromoCode.setVisibility(View.VISIBLE);
                    btnRedeem.setText("Add to Wallet");
                } else {
                    llPromoCode.setVisibility(View.GONE);
                    btnRedeem.setText("Add to Wallet");
                }
            }
/*        txtPointsAmount.setText(result.getPoint() + "");
        txtPoints.setText("Points");*/

            if (result.getIsPoint() != null && result.getIsCredit() != null && result.getIsPaypal() != null && result.getIsFree() != null) {
                if (result.getIsPoint() == 1 && result.getIsCredit() == 0 && result.getIsPaypal() == 0 && result.getIsFree() == 0) {
                    // only points
                    llPoints.setVisibility(View.VISIBLE);
                    txtPoints.setText(result.getPoint());

                } else if (result.getIsPoint() == 0 && result.getIsCredit() == 1 && result.getIsPaypal() == 0 && result.getIsFree() == 0) {
                    // only credit
                    llCredits.setVisibility(View.VISIBLE);
                    txtCredits.setText(result.getPoint());

                } else if (result.getIsPoint() == 0 && result.getIsCredit() == 0 && result.getIsPaypal() == 1 && result.getIsFree() == 0) {
                    // only cash
                    llCash.setVisibility(View.VISIBLE);
                    txtCash.setText(result.getPoint());

                    updatedCash = Float.valueOf(result.getPoint()) * prefHelper.getConvertedAmount();
                    String formattedValue = String.format("%.2f", updatedCash);
                    txtCash.setText(formattedValue + "");
                    currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());

                } else if (result.getIsPoint() == 0 && result.getIsCredit() == 0 && result.getIsPaypal() == 0 && result.getIsFree() == 1) {

                    llFree.setVisibility(View.VISIBLE);

                } else if (result.getIsPoint() == 1 && result.getIsCredit() == 1 && result.getIsPaypal() == 0 && result.getIsFree() == 0) {
                    // only point and credit
                    llPoints.setVisibility(View.VISIBLE);
                    txtPoints.setText(result.getPoint());
                    llCredits.setVisibility(View.VISIBLE);
                    txtCredits.setText(result.getPoint());

                } else if (result.getIsPoint() == 1 && result.getIsCredit() == 0 && result.getIsPaypal() == 1 && result.getIsFree() == 0) {
                    // only point and cash
                    llPoints.setVisibility(View.VISIBLE);
                    txtPoints.setText(result.getPoint());
                    llCash.setVisibility(View.VISIBLE);
                    txtCash.setText(result.getPoint());
                    updatedCash = Float.valueOf(result.getPoint()) * prefHelper.getConvertedAmount();
                    String formattedValue = String.format("%.2f", updatedCash);
                    txtCash.setText(formattedValue + "");
                    currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());
                } else if (result.getIsPoint() == 0 && result.getIsCredit() == 1 && result.getIsPaypal() == 1 && result.getIsFree() == 0) {
                    // only credit and cash
                    llCredits.setVisibility(View.VISIBLE);
                    txtCredits.setText(result.getPoint());
                    llCash.setVisibility(View.VISIBLE);
                    txtCash.setText(result.getPoint());
                    updatedCash = Float.valueOf(result.getPoint()) * prefHelper.getConvertedAmount();
                    String formattedValue = String.format("%.2f", updatedCash);
                    txtCash.setText(formattedValue + "");
                    currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());
                } else if (result.getIsPoint() == 1 && result.getIsCredit() == 1 && result.getIsPaypal() == 1 && result.getIsFree() == 0) {
                    // all type accepted
                    llCredits.setVisibility(View.VISIBLE);
                    txtCredits.setText(result.getPoint());
                    llPoints.setVisibility(View.VISIBLE);
                    txtPoints.setText(result.getPoint());
                    llCash.setVisibility(View.VISIBLE);
                    txtCash.setText(result.getPoint());
                    updatedCash = Float.valueOf(result.getPoint()) * prefHelper.getConvertedAmount();
                    String formattedValue = String.format("%.2f", updatedCash);
                    txtCash.setText(formattedValue + "");
                    currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());
                }
            }
/*
        if (result.getTypeSelect().equals("isFree")) {
            txtPointsAmount.setText(result.getPoint() + "");
            txtPoints.setText("Points");
        } else if (result.getTypeSelect().equals("isPoint")){
            txtPointsAmount.setText(result.getPoint() + "");
            txtPoints.setText("Points");
        } else if (result.getTypeSelect().equals("isCredit")){
            txtPointsAmount.setText(result.getPoint() + "");
            txtPoints.setText("Credits");
        } else if (result.getTypeSelect().equals("isCash")){
            txtPointsAmount.setText(result.getPoint() + "");
            txtPoints.setText("$");
        }

        //txtRemainingQty.setText("Remaining Qty" + result.getProductDetail().getQuantity());

        switch (prefHelper.getSelectedLanguage()) {
            case AppConstants.ENGLISH:
                if ((result.getTitle() + "").contains("%")) {
                    txtCouponDetail.setText(UtilsGlobal.setSpanString(result.getTitle(),
                            UtilsGlobal.startIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), result.getTitle() + ""),
                            UtilsGlobal.endIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), result.getTitle() + ""),
                            getDockActivity()));
                } else
                    txtCouponDetail.setText(result.getTitle() + "");

                txtTermsDetails.setText(result.getTermCondition() + "");

                break;
            case AppConstants.MALAYSIAN:
                if ((result.getMaTitle() + "").contains("%")) {
                    txtCouponDetail.setText(UtilsGlobal.setSpanString(result.getMaTitle(),
                            UtilsGlobal.startIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), result.getMaTitle() + ""),
                            UtilsGlobal.endIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), result.getMaTitle() + ""),
                            getDockActivity()));
                } else
                    txtCouponDetail.setText(result.getMaTitle() + "");

                txtTermsDetails.setText(result.getMaTermCondition() + "");
                break;
            case AppConstants.INDONESIAN:
                if ((result.getInTitle() + "").contains("%")) {
                    txtCouponDetail.setText(UtilsGlobal.setSpanString(result.getInTitle(),
                            UtilsGlobal.startIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), result.getInTitle() + ""),
                            UtilsGlobal.endIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), result.getInTitle() + ""),
                            getDockActivity()));
                } else
                    txtCouponDetail.setText(result.getInTitle() + "");

                txtTermsDetails.setText(result.getInTermCondition() + "");
                break;
            default:
                if ((result.getTitle() + "").contains("%")) {
                    txtCouponDetail.setText(UtilsGlobal.setSpanString(result.getTitle(),
                            UtilsGlobal.startIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), result.getTitle() + ""),
                            UtilsGlobal.endIndexOf(Pattern.compile(AppConstants.Regex_Number_Pattern), result.getTitle() + ""),
                            getDockActivity()));
                } else
                    txtCouponDetail.setText(result.getTitle() + "");



                break;
        }*/

            if (result.getPoint() != null)
                updatedCash = Float.valueOf(result.getPoint()) * prefHelper.getConvertedAmount();
            String formattedValue = String.format("%.2f", updatedCash);
            txtCash.setText(formattedValue + "");
            if (prefHelper != null && prefHelper.getConvertedAmountCurrrency() != null)
                currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());

            txtActualAmount.setVisibility(View.GONE);
            txtAfterDiscount.setVisibility(View.GONE);
        }
        buttonChecker = true;
    }

    @OnClick({R.id.btn_redeem})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_redeem:

                if (buttonChecker) {
                    if (global != null) {
                        if (global.getIsFree() == 1) {
                            serviceHelper.enqueueCall(webService.BuyFlashSalePointCredit(ID, CREDIT_AMOUNT, POINT, FormattedDate, PAYMENT_TYPE, SELECTED_TYPE, prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyFlashSaleFree);
                        } else {
                            getDockActivity().replaceDockableFragment(PaymentChooseFragment.newInstance(global, true), "PaymentChooseFragment");
                        }
                    }
                }
                break;
        }
    }
}
