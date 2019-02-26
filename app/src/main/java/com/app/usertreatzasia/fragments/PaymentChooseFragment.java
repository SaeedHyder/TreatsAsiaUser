package com.app.usertreatzasia.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.EventsEnt;
import com.app.usertreatzasia.entities.EvoucherEnt;
import com.app.usertreatzasia.entities.FlashSaleEnt;
import com.app.usertreatzasia.entities.RewardsEnt;
import com.app.usertreatzasia.entities.WalletEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PaymentChooseFragment extends BaseFragment {


    private static String flashSaleData = "flashSaleData";
    private static String evoucherHomeData = "evoucherHomeData";
    private static String eventData = "eventData";
    private static String rewardData = "rewardData";
    @BindView(R.id.txt_points)
    AnyTextView txtPoints;
    @BindView(R.id.ll_points)
    LinearLayout llPoints;
    @BindView(R.id.view_1)
    View view1;
    @BindView(R.id.txt_credits)
    AnyTextView txtCredits;
    @BindView(R.id.ll_credits)
    LinearLayout llCredits;
    @BindView(R.id.view_2)
    View view2;
    @BindView(R.id.txt_cash)
    AnyTextView txtCash;
    @BindView(R.id.ll_cash)
    LinearLayout llCash;
    @BindView(R.id.view_3)
    View view3;
    @BindView(R.id.cbPoints)
    RadioButton cbPoints;
    @BindView(R.id.cbCredits)
    RadioButton cbCredits;
    @BindView(R.id.cbCash)
    RadioButton cbCash;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.btnProceed)
    Button btnProceed;
    private FlashSaleEnt flashsaleEnt;
    private EvoucherEnt evoucherEnt;
    private EventsEnt eventEnt;
    private RewardsEnt rewardsEnt;
    String CREDIT_AMOUNT = "0";
    String POINT = "0";
    String FormattedDate;
    String PAYMENT_TYPE_POINTS = "point";
    String SELECTED_TYPE_POINTS = "point";
    String SELECTED_TYPE__EVENT_POINTS = "epoint";
    String PAYMENT_TYPE_CREDITS = "credit";
    String SELECTED_TYPE_CREDITS = "credit";
    String TYPE_FLASHSALE = "flashsale";
    String TYPE_EVENT = "event";
    private static boolean IS_FLASHSALE;
    private static boolean IS_EVOUCHER_HOME;
    private static boolean IS_EVENT;
    private static boolean IS_REWARD;
    private static String QUANTITY = "Qty";
    private int Quantity;
    private static final int B_T_REQUEST_CODE = 9001;
    int pointUp;
    int pointDown;
    int creditUp;
    int creditDown;
    boolean proceed = false;
    private Date startDate;
    String description;
    long timeInMillisecondsStart;
    long timeInMillisecondsEnd;
    Unbinder unbinder;
    Float updatedCashFlash;
    Float updatedCashEvents;
    boolean buttonChecker = false;

    public static PaymentChooseFragment newInstance(FlashSaleEnt flashsaleEnt, boolean imComingFromFlashSale) {
        Bundle args = new Bundle();
        IS_FLASHSALE = imComingFromFlashSale;
        args.putString(flashSaleData, new Gson().toJson(flashsaleEnt));
        PaymentChooseFragment fragment = new PaymentChooseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PaymentChooseFragment newInstance(EvoucherEnt evoucherEnt, boolean imComingFromEvoucherHome) {
        Bundle args = new Bundle();
        IS_EVOUCHER_HOME = imComingFromEvoucherHome;
        args.putString(evoucherHomeData, new Gson().toJson(evoucherEnt));
        PaymentChooseFragment fragment = new PaymentChooseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PaymentChooseFragment newInstance(EventsEnt eventEnt, boolean imComingFromEvent) {
        Bundle args = new Bundle();
        IS_EVENT = imComingFromEvent;
        args.putString(eventData, new Gson().toJson(eventEnt));
        PaymentChooseFragment fragment = new PaymentChooseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PaymentChooseFragment newInstance(RewardsEnt rewardsEnt, boolean imComingFromReward, Integer Qty) {
        Bundle args = new Bundle();
        IS_REWARD = imComingFromReward;
        args.putInt(QUANTITY, Qty);
        args.putString(rewardData, new Gson().toJson(rewardsEnt));
        PaymentChooseFragment fragment = new PaymentChooseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        flashSaleData = getArguments().getString(flashSaleData);
        if (flashSaleData != null) {
            flashsaleEnt = new Gson().fromJson(flashSaleData, FlashSaleEnt.class);
        }

        evoucherHomeData = getArguments().getString(evoucherHomeData);
        if (evoucherHomeData != null) {
            evoucherEnt = new Gson().fromJson(evoucherHomeData, EvoucherEnt.class);
        }

        eventData = getArguments().getString(eventData);
        if (eventData != null) {
            eventEnt = new Gson().fromJson(eventData, EventsEnt.class);
        }

        rewardData = getArguments().getString(rewardData);
        if (rewardData != null) {
            rewardsEnt = new Gson().fromJson(rewardData, RewardsEnt.class);
        }

        if (getArguments() != null) {
            Quantity = getArguments().getInt(QUANTITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_choose, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonChecker = false;
        getWalletData();
        pointCreditsIntDataGet();
        currentDate();
    }

    private void pointCreditsIntDataGet() {
/*        if (!txtPoints.getText().toString().equalsIgnoreCase("")) {
            pointUp = Integer.parseInt(txtPoints.getText().toString());
        }
        if (!txtCredits.getText().toString().equalsIgnoreCase("")) {
            pointDown = result.getPoint();
        }

        if (!txtCredits.getText().toString().equalsIgnoreCase("")) {
            creditDown = Integer.parseInt(txtCredits.getText().toString());
        }
        if (!txtAmountCredits.getText().toString().equalsIgnoreCase("")) {
            creditDown = Integer.parseInt(txtAmountCredits.getText().toString());
        }*/
    }

    private void getWalletData() {
        if (prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
            serviceHelper.enqueueCall(webService.getWalletData(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getWallet);
    }

    private void setFlashSaleView() {

        if (flashsaleEnt != null && flashsaleEnt.getIsPoint() != null && flashsaleEnt.getIsCredit() != null && flashsaleEnt.getIsPaypal() != null) {
            if (flashsaleEnt.getIsPoint() == 1 && flashsaleEnt.getIsCredit() == 0 && flashsaleEnt.getIsPaypal() == 0) {
                // only points
                llPoints.setVisibility(View.VISIBLE);
                cbPoints.setVisibility(View.VISIBLE);
                txtPoints.setText(flashsaleEnt.getPoint());
                cbPoints.setChecked(true);

            } else if (flashsaleEnt.getIsPoint() == 0 && flashsaleEnt.getIsCredit() == 1 && flashsaleEnt.getIsPaypal() == 0) {
                // only credit
                llCredits.setVisibility(View.VISIBLE);
                cbCredits.setVisibility(View.VISIBLE);
                txtCredits.setText(flashsaleEnt.getPoint());
                cbCredits.setChecked(true);

            } else if (flashsaleEnt.getIsPoint() == 0 && flashsaleEnt.getIsCredit() == 0 && flashsaleEnt.getIsPaypal() == 1) {
                // only cash
                llCash.setVisibility(View.VISIBLE);
                cbCash.setVisibility(View.VISIBLE);
                //txtCash.setText(flashsaleEnt.getPoint());
                txtCash.setText(flashsaleEnt.getPoint());
                cbCash.setChecked(true);

                updatedCashFlash = Float.valueOf(flashsaleEnt.getPoint()) * prefHelper.getConvertedAmount();
                String formattedValue = String.format("%.2f", updatedCashFlash);
                txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
            } else if (flashsaleEnt.getIsPoint() == 1 && flashsaleEnt.getIsCredit() == 1 && flashsaleEnt.getIsPaypal() == 0) {
                // only point and credit
                llPoints.setVisibility(View.VISIBLE);
                cbPoints.setVisibility(View.VISIBLE);
                llCredits.setVisibility(View.VISIBLE);
                cbCredits.setVisibility(View.VISIBLE);
                txtPoints.setText(flashsaleEnt.getPoint());
                txtCredits.setText(flashsaleEnt.getPoint());
                cbPoints.setChecked(true);

            } else if (flashsaleEnt.getIsPoint() == 1 && flashsaleEnt.getIsCredit() == 0 && flashsaleEnt.getIsPaypal() == 1) {
                // only point and cash
                llPoints.setVisibility(View.VISIBLE);
                cbPoints.setVisibility(View.VISIBLE);
                llCash.setVisibility(View.VISIBLE);
                cbCash.setVisibility(View.VISIBLE);
                txtPoints.setText(flashsaleEnt.getPoint());
                txtCash.setText(flashsaleEnt.getPoint());
                cbPoints.setChecked(true);

                updatedCashFlash = Float.valueOf(flashsaleEnt.getPoint()) * prefHelper.getConvertedAmount();
                String formattedValue = String.format("%.2f", updatedCashFlash);
                txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");

            } else if (flashsaleEnt.getIsPoint() == 0 && flashsaleEnt.getIsCredit() == 1 && flashsaleEnt.getIsPaypal() == 1) {
                // only credit and cash
                llCredits.setVisibility(View.VISIBLE);
                cbCredits.setVisibility(View.VISIBLE);
                llCash.setVisibility(View.VISIBLE);
                cbCash.setVisibility(View.VISIBLE);
                txtCredits.setText(flashsaleEnt.getPoint());
                txtCash.setText(flashsaleEnt.getPoint());
                updatedCashFlash = Float.valueOf(flashsaleEnt.getPoint()) * prefHelper.getConvertedAmount();
                String formattedValue = String.format("%.2f", updatedCashFlash);
                txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
                cbCredits.setChecked(true);

            } else if (flashsaleEnt.getIsPoint() == 1 && flashsaleEnt.getIsCredit() == 1 && flashsaleEnt.getIsPaypal() == 1) {
                // all type accepted
                llPoints.setVisibility(View.VISIBLE);
                cbPoints.setVisibility(View.VISIBLE);
                llCredits.setVisibility(View.VISIBLE);
                cbCredits.setVisibility(View.VISIBLE);
                llCash.setVisibility(View.VISIBLE);
                cbCash.setVisibility(View.VISIBLE);
                txtPoints.setText(flashsaleEnt.getPoint());
                txtCredits.setText(flashsaleEnt.getPoint());
                txtCash.setText(flashsaleEnt.getPoint());
                updatedCashFlash = Float.valueOf(flashsaleEnt.getPoint()) * prefHelper.getConvertedAmount();
                String formattedValue = String.format("%.2f", updatedCashFlash);
                txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
                cbPoints.setChecked(true);

            } else if (flashsaleEnt.getIsPoint() == 0 && flashsaleEnt.getIsCredit() == 0 && flashsaleEnt.getIsPaypal() == 0) {
                llPoints.setVisibility(View.GONE);
                cbPoints.setVisibility(View.GONE);
                llCredits.setVisibility(View.GONE);
                cbCredits.setVisibility(View.GONE);
                llCash.setVisibility(View.GONE);
                cbCash.setVisibility(View.GONE);
                txtPoints.setText("");
                txtCredits.setText("");
                txtCash.setText("");
            }
            if (!txtPoints.getText().toString().equalsIgnoreCase("")) {
                pointUp = Integer.parseInt(txtPoints.getText().toString());
            }
            if (!txtCredits.getText().toString().equalsIgnoreCase("")) {
                creditUp = Integer.parseInt(txtCredits.getText().toString());
            }
        }
        buttonChecker = true;
    }

    private void setEvoucherView() {
        llPoints.setVisibility(View.VISIBLE);
        llCredits.setVisibility(View.GONE);
        llCash.setVisibility(View.GONE);
        cbPoints.setVisibility(View.VISIBLE);
        cbCredits.setVisibility(View.GONE);
        cbCash.setVisibility(View.GONE);
        if (evoucherEnt != null && evoucherEnt.getPoint() != null)
            txtPoints.setText(evoucherEnt.getPoint());
        if (!txtPoints.getText().toString().equalsIgnoreCase("")) {
            pointUp = Integer.parseInt(txtPoints.getText().toString());
        }
        if (!txtCredits.getText().toString().equalsIgnoreCase("")) {
            creditUp = Integer.parseInt(txtCredits.getText().toString());
        }
        cbPoints.setChecked(true);
        buttonChecker = true;
    }

    private void setEventView() {

        if (eventEnt != null && eventEnt.getIsPoint() != null && eventEnt.getIsCredit() != null && eventEnt.getIsPaypal() != null) {
            if (eventEnt.getIsPoint() == 1 && eventEnt.getIsCredit() == 0 && eventEnt.getIsPaypal() == 0) {
                // only points
                llPoints.setVisibility(View.VISIBLE);
                cbPoints.setVisibility(View.VISIBLE);
                txtPoints.setText(eventEnt.getPoint());
                cbPoints.setChecked(true);

            } else if (eventEnt.getIsPoint() == 0 && eventEnt.getIsCredit() == 1 && eventEnt.getIsPaypal() == 0) {
                // only credit
                llCredits.setVisibility(View.VISIBLE);
                cbCredits.setVisibility(View.VISIBLE);
                txtCredits.setText(eventEnt.getPoint());
                cbCredits.setChecked(true);

            } else if (eventEnt.getIsPoint() == 0 && eventEnt.getIsCredit() == 0 && eventEnt.getIsPaypal() == 1) {
                // only cash
                llCash.setVisibility(View.VISIBLE);
                cbCash.setVisibility(View.VISIBLE);
                //txtCash.setText(eventEnt.getPoint());
                txtCash.setText(eventEnt.getPoint());
                updatedCashEvents = Float.valueOf(eventEnt.getPoint()) * prefHelper.getConvertedAmount();
                String formattedValue = String.format("%.2f", updatedCashEvents);
                txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
                cbCash.setChecked(true);

            } else if (eventEnt.getIsPoint() == 1 && eventEnt.getIsCredit() == 1 && eventEnt.getIsPaypal() == 0) {
                // only point and credit
                llPoints.setVisibility(View.VISIBLE);
                cbPoints.setVisibility(View.VISIBLE);
                llCredits.setVisibility(View.VISIBLE);
                cbCredits.setVisibility(View.VISIBLE);
                txtPoints.setText(eventEnt.getPoint());
                txtCredits.setText(eventEnt.getPoint());
                cbPoints.setChecked(true);

            } else if (eventEnt.getIsPoint() == 1 && eventEnt.getIsCredit() == 0 && eventEnt.getIsPaypal() == 1) {
                // only point and cash
                llPoints.setVisibility(View.VISIBLE);
                cbPoints.setVisibility(View.VISIBLE);
                llCash.setVisibility(View.VISIBLE);
                cbCash.setVisibility(View.VISIBLE);
                txtPoints.setText(eventEnt.getPoint());
                txtCash.setText(eventEnt.getPoint());
                updatedCashEvents = Float.valueOf(eventEnt.getPoint()) * prefHelper.getConvertedAmount();
                String formattedValue = String.format("%.2f", updatedCashEvents);
                txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
                cbPoints.setChecked(true);

            } else if (eventEnt.getIsPoint() == 0 && eventEnt.getIsCredit() == 1 && eventEnt.getIsPaypal() == 1) {
                // only credit and cash
                llCredits.setVisibility(View.VISIBLE);
                cbCredits.setVisibility(View.VISIBLE);
                llCash.setVisibility(View.VISIBLE);
                cbCash.setVisibility(View.VISIBLE);
                txtCredits.setText(eventEnt.getPoint());
                txtCash.setText(eventEnt.getPoint());
                updatedCashEvents = Float.valueOf(eventEnt.getPoint()) * prefHelper.getConvertedAmount();
                String formattedValue = String.format("%.2f", updatedCashEvents);
                txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
                cbCredits.setChecked(true);

            } else if (eventEnt.getIsPoint() == 1 && eventEnt.getIsCredit() == 1 && eventEnt.getIsPaypal() == 1) {
                // all type accepted
                llPoints.setVisibility(View.VISIBLE);
                cbPoints.setVisibility(View.VISIBLE);
                llCredits.setVisibility(View.VISIBLE);
                cbCredits.setVisibility(View.VISIBLE);
                llCash.setVisibility(View.VISIBLE);
                cbCash.setVisibility(View.VISIBLE);
                txtPoints.setText(eventEnt.getPoint());
                txtCredits.setText(eventEnt.getPoint());
                txtCash.setText(eventEnt.getPoint());
                updatedCashEvents = Float.valueOf(eventEnt.getPoint()) * prefHelper.getConvertedAmount();
                String formattedValue = String.format("%.2f", updatedCashEvents);
                txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
                cbPoints.setChecked(true);

            } else if (eventEnt.getIsPoint() == 0 && eventEnt.getIsCredit() == 0 && eventEnt.getIsPaypal() == 0) {
                llPoints.setVisibility(View.GONE);
                cbPoints.setVisibility(View.GONE);
                llCredits.setVisibility(View.GONE);
                cbCredits.setVisibility(View.GONE);
                llCash.setVisibility(View.GONE);
                cbCash.setVisibility(View.GONE);
                txtPoints.setText("");
                txtCredits.setText("");
                txtCash.setText("");
            }
            if (!txtPoints.getText().toString().equalsIgnoreCase("")) {
                pointUp = Integer.parseInt(txtPoints.getText().toString());
            }
            if (!txtCredits.getText().toString().equalsIgnoreCase("")) {
                creditUp = Integer.parseInt(txtCredits.getText().toString());
            }
        }
        buttonChecker = true;
    }

    private void setRewardView() {

        llPoints.setVisibility(View.VISIBLE);
        llCredits.setVisibility(View.GONE);
        llCash.setVisibility(View.GONE);
        cbPoints.setVisibility(View.VISIBLE);
        cbCredits.setVisibility(View.GONE);
        cbCash.setVisibility(View.GONE);
        if (!txtPoints.getText().toString().equalsIgnoreCase("")) {
            pointUp = Integer.parseInt(txtPoints.getText().toString());
        }
        if (!txtCredits.getText().toString().equalsIgnoreCase("")) {
            creditUp = Integer.parseInt(txtCredits.getText().toString());
        }
        cbPoints.setChecked(true);
        buttonChecker = true;
    }

    private void currentDate() {
        SimpleDateFormat dateFormatYouWant = new SimpleDateFormat("yyyy-MM-dd");
        FormattedDate = dateFormatYouWant.format(new Date());
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Payment Method");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        IS_FLASHSALE = false;
        IS_EVOUCHER_HOME = false;
        IS_EVENT = false;
        IS_REWARD = false;
    }

    @OnClick(R.id.btnProceed)
    public void onViewClicked() {

        if (buttonChecker) {
            if (IS_FLASHSALE) {
                if (cbPoints.isChecked()) {
                    if (prefHelper.getSubscriptionStatus()) {
                        if (pointUp <= pointDown) {
                            //IS_FLASHSALE =false;
                            if (flashsaleEnt != null && flashsaleEnt.getId() != null && flashsaleEnt.getPoint() != null
                                    && prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
                                serviceHelper.enqueueCall(webService.BuyFlashSalePointCredit(flashsaleEnt.getId(), CREDIT_AMOUNT, flashsaleEnt.getPoint(), FormattedDate, PAYMENT_TYPE_POINTS, SELECTED_TYPE_POINTS, prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyFlashSaleCreditPoint);
                        } else {
                            //IS_FLASHSALE =false;
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_points));
                        }
                    } else {
                        //IS_FLASHSALE =false;
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_subscription));
                    }
                } else if (cbCredits.isChecked()) {
                    if (creditUp <= creditDown) {
                        // IS_FLASHSALE =false;
                        if (flashsaleEnt != null && flashsaleEnt.getId() != null && flashsaleEnt.getPoint() != null
                                && prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
                            serviceHelper.enqueueCall(webService.BuyFlashSalePointCredit(flashsaleEnt.getId(), flashsaleEnt.getPoint(), POINT, FormattedDate, PAYMENT_TYPE_CREDITS, SELECTED_TYPE_CREDITS, prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyFlashSaleCreditPoint);
                    } else {
                        //IS_FLASHSALE =false;
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_credits));
                    }
                } else if (cbCash.isChecked()) {
                    DropInRequest dropInRequest = new DropInRequest()
                            .clientToken(AppConstants.clientToken);
                    startActivityForResult(dropInRequest.getIntent(getDockActivity()), B_T_REQUEST_CODE);
                } else {
                    //IS_FLASHSALE =false;
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_select_payment));
                }
            } else if (IS_EVOUCHER_HOME) {
                if (cbPoints.isChecked()) {
                    if (prefHelper.getSubscriptionStatus()) {
                        if (pointUp <= pointDown) {
                            //IS_EVOUCHER_HOME =false;
                            if (evoucherEnt != null && evoucherEnt.getId() != null && evoucherEnt.getPoint() != null && evoucherEnt.getProductDetail() != null && evoucherEnt.getProductDetail().getPrice() != null && prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
                                serviceHelper.enqueueCall(webService.BuyEvoucherPoint(evoucherEnt.getId(), evoucherEnt.getProductDetail().getPrice(), evoucherEnt.getPoint(), FormattedDate, PAYMENT_TYPE_POINTS, prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyEvoucherPoint);
                        } else {
                            // IS_EVOUCHER_HOME =false;
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_points));
                        }
                    } else {
                        // IS_EVOUCHER_HOME =false;
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_subscription));
                    }
                } else {
                    //IS_EVOUCHER_HOME =false;
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_select_payment));
                }
            } else if (IS_EVENT) {
                if (cbPoints.isChecked()) {
                    if (prefHelper.getSubscriptionStatus()) {
                        if (pointUp <= pointDown) {
                            //IS_EVENT =false;
                            if (eventEnt != null && eventEnt.getPoint() != null && eventEnt.getId() != null && prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null) {
                                createEvent();
                                serviceHelper.enqueueCall(webService.BuyEventsPointCredit(eventEnt.getId(), CREDIT_AMOUNT, eventEnt.getPoint(), FormattedDate, PAYMENT_TYPE_POINTS, SELECTED_TYPE__EVENT_POINTS, prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyEventCreditPoint);
                            }
                        } else {
                            //IS_EVENT =false;
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_points));
                        }
                    } else {
                        //IS_EVENT =false;
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_subscription));
                    }

                } else if (cbCredits.isChecked()) {
                    if (creditUp <= creditDown) {
                        //IS_EVENT =false;
                        if (eventEnt != null && eventEnt.getPoint() != null && eventEnt.getId() != null && prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null) {
                            createEvent();
                            serviceHelper.enqueueCall(webService.BuyEventsPointCredit(eventEnt.getId(), eventEnt.getPoint(), POINT, FormattedDate, PAYMENT_TYPE_CREDITS, SELECTED_TYPE_CREDITS, prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyEventCreditPoint);
                        }
                    } else {
                        //IS_EVENT =false;
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_credits));
                    }
                } else if (cbCash.isChecked()) {
                    DropInRequest dropInRequest = new DropInRequest()
                            .clientToken(AppConstants.clientToken);
                    startActivityForResult(dropInRequest.getIntent(getDockActivity()), B_T_REQUEST_CODE);
                } else {
                    //IS_EVENT =false;
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_select_payment));
                }
            } else if (IS_REWARD) {
                if (!cbPoints.isChecked()) {
                    if (prefHelper.getSubscriptionStatus()) {
                        if (pointUp <= pointDown) {
                            //IS_REWARD =false;
                            if (rewardsEnt != null && rewardsEnt.getId() != null && prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
                                serviceHelper.enqueueCall(webService.BuyRewardPoint(rewardsEnt.getId(), String.valueOf(Quantity), prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyRewardPoint);
                        } else {
                            //IS_REWARD =false;
                            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_points));
                        }
                    } else {
                        //IS_REWARD =false;
                        UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_subscription));
                    }
                } else {
                    //IS_REWARD =false;
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_select_payment));
                }
            }
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {

        switch (Tag) {

            case WebServiceConstants.getWallet:
                setWalletData((WalletEntity) result);
                break;

            case WebServiceConstants.BuyFlashSaleCreditPoint:
                //UIHelper.showShortToastInCenter(getDockActivity(), "Flashsale added successfully.");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(), "HomeWalletFragment");
                break;

            case WebServiceConstants.BuyFlashSaleCash:
                //UIHelper.showShortToastInCenter(getDockActivity(), "Flashsale added successfully.");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(), "HomeWalletFragment");
                break;

            case WebServiceConstants.BuyEvoucherPoint:
                //UIHelper.showShortToastInCenter(getDockActivity(), "E-voucher added successfully.");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(), "HomeWalletFragment");
                break;

            case WebServiceConstants.BuyEventCreditPoint:
                //UIHelper.showShortToastInCenter(getDockActivity(), "Event added successfully.");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(EventsWalletFragment.newInstance(), "EventsWalletFragment");
                break;

            case WebServiceConstants.BuyEventCash:
                //UIHelper.showShortToastInCenter(getDockActivity(), "Event added successfully.");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(EventsWalletFragment.newInstance(), "EventsWalletFragment");
                break;

            case WebServiceConstants.BuyRewardPoint:
                //UIHelper.showShortToastInCenter(getDockActivity(), "Reward added successfully.");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(RewardsWalletFragment.newInstance(), "RewardsWalletFragment");
                break;
        }
    }

    private void setWalletData(WalletEntity result) {
        if (result != null) {
            if (result != null && result.getPoint() != null)
                cbPoints.setText("Points " + "(" + result.getPoint() + ")");
            if (result != null && result.getCredit() != null)
                cbCredits.setText("Credits " + "(" + result.getCredit() + ")");
            if (result != null && result.getPoint() != null)
                pointDown = Integer.parseInt(result.getPoint());
            if (result != null && result.getCredit() != null)
                creditDown = Integer.parseInt(result.getCredit());
        }
        pointCreditsIntDataGet();
        proceed = true;
        if (proceed) {
            if (IS_FLASHSALE) {
                setFlashSaleView();
            } else if (IS_EVOUCHER_HOME) {
                setEvoucherView();
            } else if (IS_EVENT) {
                setEventView();
            } else if (IS_REWARD) {
                setRewardView();
            } else {
                UIHelper.showShortToastInCenter(getDockActivity(), "Error. Please try again later.");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == B_T_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                Log.e("ResultSuccess", "onActivityResult: " + result);
                if (IS_FLASHSALE) {
                    //IS_FLASHSALE =false;
                    if (flashsaleEnt != null && flashsaleEnt.getPoint() != null && prefHelper != null && prefHelper.getConvertedAmount() != null && prefHelper.getConvertedAmountCurrrency() != null
                            && flashsaleEnt.getId() != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null) {
                        updatedCashFlash = Float.valueOf(flashsaleEnt.getPoint()) * prefHelper.getConvertedAmount();
                        String formattedValue = String.format("%.2f", updatedCashFlash);
                        txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
                        serviceHelper.enqueueCall(webService.BuyFlashSaleCash(flashsaleEnt.getId(), TYPE_FLASHSALE, flashsaleEnt.getPoint(), result.getPaymentMethodNonce().getNonce(), prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyFlashSaleCash);
                    }
                } else if (IS_EVENT) {
                    //IS_EVENT =false;
                    if (eventEnt != null && eventEnt.getPoint() != null && prefHelper != null && prefHelper.getConvertedAmount() != null && prefHelper.getConvertedAmountCurrrency() != null
                            && eventEnt.getId() != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null) {
                        updatedCashFlash = Float.valueOf(eventEnt.getPoint()) * prefHelper.getConvertedAmount();
                        String formattedValue = String.format("%.2f", updatedCashFlash);
                        txtCash.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");
                        createEvent();
                        serviceHelper.enqueueCall(webService.BuyEventCash(eventEnt.getId(), TYPE_EVENT, eventEnt.getPoint(), result.getPaymentMethodNonce().getNonce(), prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyEventCash);
                    }
                    // use the result to update your UI and send the payment method nonce to your server
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    // the user canceled
                } else {
                    // handle errors here, an exception may be available in
                    Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                }
            }
        }
    }

    private void createEvent() {

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            startDate = dateFormater.parse(eventEnt.getStartDate() + " " + eventEnt.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(startDate);

        long startTime = calendar.getTimeInMillis();
        long startTime2 = System.currentTimeMillis();

        description = eventEnt.getDescription();
        Spanned desc = Html.fromHtml(description);
        description = String.valueOf(desc);

        String givenDateString = eventEnt.getStartDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMillisecondsStart = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMillisecondsStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String givenDateStringEnd = eventEnt.getEndDate();
        SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date mDate = sdfEnd.parse(givenDateString);
            timeInMillisecondsEnd = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMillisecondsEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pushAppointmentsToCalender(getDockActivity(), eventEnt.getEventName(), description, eventEnt.getVenue(),
                1, startTime, true, false, false);
    }

    public long pushAppointmentsToCalender(Activity curActivity, String title, String addInfo, String place, int status, long startDate, boolean needReminder, boolean needMailService, boolean deleteEvent) {

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        // our mobile for primary
        // its 1
        eventValues.put("title", title);
        eventValues.put("description", addInfo);
        eventValues.put("eventLocation", place);

        long endDate = startDate + 1000 * 60 * 60; // For next 1hr
//        Long endDate = global.getEndDate(); // For next 1hr

        eventValues.put(CalendarContract.Events.DTSTART, startDate);
        eventValues.put(CalendarContract.Events.DTEND, endDate);

        // values.put("allDay", 1); //If it is bithday alarm or such
        // kind (which should remind me for whole day) 0 for false, 1
        // for true
        eventValues.put("eventStatus", status); // This information is
        // sufficient for most
        // entries tentative (0),
        // confirmed (1) or canceled
        // (2):
        eventValues.put("eventTimezone", "UTC/GMT +2:00");
/*Comment below visibility and transparency  column to avoid java.lang.IllegalArgumentException column visibility is invalid error */

/*eventValues.put("visibility", 3); // visibility to default (0),
                                     // confidential (1), private
                                     // (2), or public (3):
eventValues.put("transparency", 0); // You can control whether
                                     // an event consumes time
                                     // opaque (0) or transparent
                                     // (1).
   */
        eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

        Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());

        if (needReminder) {
            /***************** Event: Reminder(with alert) Adding reminder to event *******************/

            String reminderUriString = "content://com.android.calendar/reminders";

            ContentValues reminderValues = new ContentValues();

            reminderValues.put("event_id", eventID);
            reminderValues.put("minutes", 1440); // Default value of the
            // system. Minutes is a
            // integer
            reminderValues.put("method", 1); // Alert Methods: Default(0),
            // Alert(1), Email(2),
            // SMS(3)

            Uri reminderUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        }

        /***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/

        if (needMailService) {
            String attendeuesesUriString = "content://com.android.calendar/attendees";

            /********
             * To add multiple attendees need to insert ContentValues multiple
             * times
             ***********/
            ContentValues attendeesValues = new ContentValues();

            attendeesValues.put("event_id", eventID);
            attendeesValues.put("attendeeName", "xxxxx"); // Attendees name
            attendeesValues.put("attendeeEmail", "yyyy@gmail.com");// Attendee
            // E
            // mail
            // id
            attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
            // Relationship_None(0),
            // Organizer(2),
            // Performer(3),
            // Speaker(4)
            attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
            // Required(2), Resource(3)
            attendeesValues.put("attendeeStatus", 0); // NOne(0), Accepted(1),
            // Decline(2),
            // Invited(3),
            // Tentative(4)

            Uri attendeuesesUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(attendeuesesUriString), attendeesValues);
        }

        return eventID;
    }
}
