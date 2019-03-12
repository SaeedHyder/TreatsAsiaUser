package com.app.usertreatzasia.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.BuyVoucherEnt;
import com.app.usertreatzasia.entities.WalletEvoucherDetailEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.app.usertreatzasia.global.WebServiceConstants.CANCEL_EVOUCHER_EVENTS;
import static com.app.usertreatzasia.global.WebServiceConstants.REDEEMEVOUCHER;


public class CouponWaletDetailFragment extends BaseFragment {

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
    /*    @BindView(R.id.txt_remaining_qty)
        AnyTextView txtRemainingQty;*/
    @BindView(R.id.btn_redeem)
    Button btnRedeem;
    @BindView(R.id.btn_gift)
    Button btn_gift;
    @BindView(R.id.txt_termsDetails)
    AnyTextView txtTermsDetails;
    @BindView(R.id.img_barcode)
    ImageView imgBarcode;
    @BindView(R.id.txtPointsAmount)
    AnyTextView txtPointsAmount;
    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    @BindView(R.id.tv_promo)
    AnyTextView tvPromo;
    @BindView(R.id.tv_promo_code)
    AnyTextView tvPromoCode;
    @BindView(R.id.ll_promo_code)
    LinearLayout llPromoCode;
    @BindView(R.id.ll_amount_points)
    LinearLayout ll_amount_points;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_website_link)
    Button btnWebsiteLink;
    @BindView(R.id.llMainFrame)
    LinearLayout llMainFrame;

    private BuyVoucherEnt evoucherEntity = new BuyVoucherEnt();
    private int ID;
    private String URL;
    ImageLoader imageLoader;
    private int evoucherID;
    private String Type;
    int checkboxValue = 0;
    WalletEvoucherDetailEnt global;
    Float updatedActualPrice;
    Float updatedDiscountedPrice;

    public static CouponWaletDetailFragment newInstance() {
        return new CouponWaletDetailFragment();
    }

    public static CouponWaletDetailFragment newInstance(Integer ID, String Type) {
        Bundle b = new Bundle();
        CouponWaletDetailFragment fragment = new CouponWaletDetailFragment();
        b.putInt(Key_ID, ID);
        b.putString(Key_TYPE, Type);
        fragment.setArguments(b);
        return fragment;
    }

    public void setContent(int id, String type, String promo_url) {
        setID(id);
        setType(type);
        setUrl(promo_url);
    }

    public void setUrl(String url) {
        this.URL = url;
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

        switch (Tag) {

            case WebServiceConstants.VoucherWalletDetail:
                llMainFrame.setVisibility(View.VISIBLE);
                setDetailData((WalletEvoucherDetailEnt) result);
                global = ((WalletEvoucherDetailEnt) result);
                break;
            case WebServiceConstants.REDEEMEVOUCHER:
                evoucherEntity = (BuyVoucherEnt) result;
                btn_cancel.setVisibility(View.VISIBLE);
                imageLoader.displayImage(evoucherEntity.getQrCodeUrl(), imgBarcode);
                imgBarcode.setVisibility(View.VISIBLE);
                btnRedeem.setVisibility(View.GONE);
                btn_gift.setVisibility(View.GONE);

                break;
/*            case WebServiceConstants.BuyFlashSale:
                imgBarcode.setVisibility(View.VISIBLE);
                btnRedeem.setVisibility(View.GONE);
                //Picasso.with(getDockActivity()).load(((RedeemEnt) result).getQr_code_url()).into(imgBarcode);
                ImageLoader.getInstance().displayImage(((RedeemEnt) result).getQr_code_url(), imgBarcode);
               *//* fragment = RedeemCouponFragment.newInstance();
                fragment.setContent(ID, Type);
                getDockActivity().replaceDockableFragment(fragment,
                        RedeemCouponFragment.class.getSimpleName());*//*
                break;*/
            case CANCEL_EVOUCHER_EVENTS:
                UIHelper.showShortToastInCenter(getDockActivity(), "QR Code Cancelled Successfully!");
                btn_cancel.setVisibility(View.GONE);
                btnRedeem.setVisibility(View.VISIBLE);
                btn_gift.setVisibility(View.VISIBLE);
                imgBarcode.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void setTitleBar(final TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButtonAsPerRequirement(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Type.equalsIgnoreCase("Home")) {
                    getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(), "HomeWalletFragment");
                } else if (Type.equalsIgnoreCase("Gift")) {
                    getDockActivity().replaceDockableFragment(GiftRecievedFragment.newInstance(), "GiftRecievedFragment");
                }
            }
        });
        titleBar.showShareButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = global.getEvoucher_detail().getTitle() + "http://Treatzasia.com";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
            }
        });
        titleBar.showFavoriteButton(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkboxValue == 1) {
                    buttonView.setChecked(true);
                } else
                    buttonView.setChecked(false);
            }

        });
        titleBar.getCheckbox().setEnabled(false);
        titleBar.setSubHeading(getString(R.string.voucher));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coupon_wallet_detail, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llMainFrame.setVisibility(View.GONE);
        prefHelper.setEvoucherId(ID);
        serviceHelper.enqueueCall(webService.getWalletVoucherDetail(ID + "", prefHelper.getSignUpUser().getToken()), WebServiceConstants.VoucherWalletDetail);


        ll_amount_points.setVisibility(View.GONE);
        imageLoader = ImageLoader.getInstance();


    }

    private void setDetailData(WalletEvoucherDetailEnt result) {

        if (result != null) {
            if (result.getEvoucher_detail() != null) {
                //checkboxValue = result.getEvoucher_detail().getL
                evoucherID = result.getEvoucherId();
                txtPaidType.setText(UtilsGlobal.getVoucherType(result.getEvoucher_detail().getType() + "", getDockActivity()));
                txtExpireTime.setText(getString(R.string.expires_on_detail) + " " + DateHelper.getFormatedDate("yyyy-MM-dd", "MMMM dd yyyy",
                        result.getEvoucher_detail().getExpiryDate() + ""));

                int discountAmount = (Integer.parseInt(result.getEvoucher_detail().getProductDetail().getPrice()) / 100) * (Integer.parseInt(result.getEvoucher_detail().getAmount()));
                int AfterDiscount = (Integer.parseInt(result.getEvoucher_detail().getProductDetail().getPrice())) - discountAmount;

                imageLoader.displayImage(result.getEvoucher_detail().getProductDetail().getProductImage(), ivCouponImage);

                updatedDiscountedPrice = Float.valueOf(UtilsGlobal.getRemainingAmountWithoutDollar(Integer.parseInt(result.getEvoucher_detail().getAmount()), Integer.parseInt(result.getEvoucher_detail().getProductDetail().getPrice()))) * prefHelper.getConvertedAmount();
                String formattedValuePriceDiscounted = String.format("%.2f", updatedDiscountedPrice);
                txtAfterDiscount.setText(prefHelper.getConvertedAmountCurrrency()+" "+formattedValuePriceDiscounted);

                txtAddress.setText(result.getEvoucher_detail().getLocation() + " ");


                if (Type.equalsIgnoreCase("Gift")) {
                    if (prefHelper != null && prefHelper.getLikeCountEvoucher() != null) {
                        if (prefHelper.getLikeCountEvoucher() == 1) {
                            getTitleBar().getCheckbox().setChecked(true);
                        } else
                            getTitleBar().getCheckbox().setChecked(false);
                    }
                }
                if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
                    txtCouponDetail.setText(result.getEvoucher_detail().getTitle() + "");
                    Spanned desc = Html.fromHtml(result.getEvoucher_detail().getTermCondition());
                    txtTermsDetails.setText(desc.toString().trim());
                } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
                    txtCouponDetail.setText(result.getEvoucher_detail().getInTitle() + "");
                    Spanned desc = Html.fromHtml(result.getEvoucher_detail().getInTermCondition());
                    txtTermsDetails.setText(desc.toString().trim());
                } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
                    txtCouponDetail.setText(result.getEvoucher_detail().getMaTitle() + "");
                    Spanned desc = Html.fromHtml(result.getEvoucher_detail().getMaTermCondition());
                    txtTermsDetails.setText(desc.toString().trim());
                } else {
                    txtCouponDetail.setText(result.getEvoucher_detail().getTitle() + "");
                    Spanned desc = Html.fromHtml(result.getEvoucher_detail().getTermCondition());
                    txtTermsDetails.setText(desc.toString().trim());
                }

                if (result.getEvoucher_detail().getType().equals("promo_code")) {
                    llPromoCode.setVisibility(View.VISIBLE);
                    btnWebsiteLink.setVisibility(View.VISIBLE);
                    tvPromoCode.setText(result.getEvoucher_detail().getPromoCode());
                    btnRedeem.setVisibility(View.GONE);
                } else {
                    llPromoCode.setVisibility(View.GONE);
                    btnWebsiteLink.setVisibility(View.GONE);
                    btnRedeem.setText("Tap to Redeem");
                    btnRedeem.setVisibility(View.VISIBLE);
                }
                txtPointsAmount.setText(result.getEvoucher_detail().getPoint() + "");

            }
        }
    }

    @OnClick({R.id.btn_redeem, R.id.btn_gift, R.id.btn_cancel, R.id.btn_website_link})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_redeem:
                serviceHelper.enqueueCall(webService.redeemEvoucher(evoucherID, AppConstants.REDEEM, prefHelper.getSignUpUser().getToken()), REDEEMEVOUCHER);
                break;
            case R.id.btn_gift:
                getDockActivity().replaceDockableFragment(GiftRecipientsFragment.newInstance("ImComingFromEvoucherWallet"), "GiftRecipientsFragment");

                break;
            case R.id.btn_cancel:
                serviceHelper.enqueueCall(webService.cancelVoucherEvents(global.getId(), prefHelper.getSignUpUser().getToken()), CANCEL_EVOUCHER_EVENTS);
                break;

            case R.id.btn_website_link:
                if (URL!=null && URL != "") {
                    if (URLUtil.isValidUrl(URL)) {
                        String urlWebsite = URL;
                        Intent iWebsite = new Intent(Intent.ACTION_VIEW);
                        iWebsite.setData(Uri.parse(urlWebsite));
                        startActivity(iWebsite);
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Error. Please try again later.");
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}