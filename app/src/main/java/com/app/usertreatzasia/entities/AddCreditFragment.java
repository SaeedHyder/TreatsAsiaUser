package com.app.usertreatzasia.entities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.fragments.EnterNumberFragment;
import com.app.usertreatzasia.fragments.FlashSaleFragment;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DialogHelper;
import com.app.usertreatzasia.helpers.InternetHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddCreditFragment extends BaseFragment {

    @BindView(R.id.edt_enter_points)
    AnyEditTextView edtEnterPoints;
    @BindView(R.id.tv_credits_available)
    AnyTextView tvCreditsAvailable;
    @BindView(R.id.tv_points_available)
    AnyTextView tvPointsAvailable;
    @BindView(R.id.btn_top_up_points)
    Button btnTopUpPoints;
    @BindView(R.id.btn_TandC)
    Button btn_TandC;
    @BindView(R.id.tv_t_c_description)
    AnyTextView tv_t_c_description;
    @BindView(R.id.txt_currency)
    AnyTextView txtCurrency;
    private static final int B_T_REQUEST_CODE = 9001;
    Unbinder unbinder;
    Float Price;
    String formattedValuePrice;
    @BindView(R.id.tv_currency_type)
    AnyTextView tvCurrencyType;
    @BindView(R.id.tv_20)
    AnyTextView tv20;
    @BindView(R.id.tv_50)
    AnyTextView tv50;
    @BindView(R.id.tv_100)
    AnyTextView tv100;
    Float AMOUNT;

    public static AddCreditFragment newInstance() {
        return new AddCreditFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_credit, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getWalletData();
        getTermsAndCondition();
        txtCurrency.setText(prefHelper.getConvertedAmountCurrrency() + "");
        tvCurrencyType.setText(prefHelper.getConvertedAmountCurrrency() + "");
        AMOUNT = Float.valueOf("0");
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Topup Credit");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.edt_enter_points, R.id.btn_top_up_points, R.id.btn_TandC, R.id.tv_20, R.id.tv_50, R.id.tv_100})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_enter_points:
                break;
            case R.id.btn_top_up_points:

                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                    if (AMOUNT != 0) {
                        String formattedAmount = String.format("%.2f", AMOUNT);
                        final DialogHelper cahoutDialog = new DialogHelper(getDockActivity());
                        cahoutDialog.cashout(R.layout.cashout_dialog, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cahoutDialog.hideDialog();
                                cashoutCredit();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cahoutDialog.hideDialog();
                            }
                        }, "Are you sure you want to buy credits worth " + prefHelper.getConvertedAmountCurrrency() + " " + formattedAmount, "Confirmation");

                        cahoutDialog.setCancelable(false);
                        cahoutDialog.showDialog();
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Please select a value first.");
                    }
                }
                break;

            case R.id.btn_TandC:
                break;
            case R.id.tv_20:
                tv20.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tv20.setBackgroundColor(ContextCompat.getColor(getDockActivity(), R.color.appGolden));

                tv50.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.appGolden));
                tv50.setBackgroundColor(ContextCompat.getColor(getDockActivity(), R.color.transparent));

                tv100.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.appGolden));
                tv100.setBackgroundColor(ContextCompat.getColor(getDockActivity(), R.color.transparent));
                AMOUNT = Float.valueOf("20");
                break;

            case R.id.tv_50:
                tv50.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tv50.setBackgroundColor(ContextCompat.getColor(getDockActivity(), R.color.appGolden));

                tv20.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.appGolden));
                tv20.setBackgroundColor(ContextCompat.getColor(getDockActivity(), R.color.transparent));

                tv100.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.appGolden));
                tv100.setBackgroundColor(ContextCompat.getColor(getDockActivity(), R.color.transparent));
                AMOUNT = Float.valueOf("50");
                break;
            case R.id.tv_100:
                tv50.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.appGolden));
                tv50.setBackgroundColor(ContextCompat.getColor(getDockActivity(), R.color.transparent));

                tv20.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.appGolden));
                tv20.setBackgroundColor(ContextCompat.getColor(getDockActivity(), R.color.transparent));

                tv100.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tv100.setBackgroundColor(ContextCompat.getColor(getDockActivity(), R.color.appGolden));
                AMOUNT = Float.valueOf("100");
                break;
        }
    }

    private void getTermsAndCondition() {
        serviceHelper.enqueueCall(webService.termsConditionTopupCashout(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getTermsConditionTopupCashout);
    }

    private void getWalletData() {
        serviceHelper.enqueueCall(webService.getWalletData(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getWallet);
    }

    private void cashoutCredit() {

        if (AMOUNT != 0) {
            DropInRequest dropInRequest = new DropInRequest()
                    .clientToken(AppConstants.clientToken);
            startActivityForResult(dropInRequest.getIntent(getDockActivity()), B_T_REQUEST_CODE);
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), "Please select amount to proceed.");
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.getWallet:
                setWalletData((WalletEntity) result);
                break;

            case WebServiceConstants.getTermsConditionTopupCashout:
                setTermsConditions((TopupCashoutTermsConditionEntity) result);
                break;

            case WebServiceConstants.topupCredit:
                UIHelper.showShortToastInCenter(getDockActivity(), "Cash have been successfully converted into credits.");
                getMainActivity().popFragment();
                break;
        }
    }

    private void setTermsConditions(TopupCashoutTermsConditionEntity result) {
        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            tv_t_c_description.setText(Html.fromHtml(result.getTermTopup()));
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            tv_t_c_description.setText(Html.fromHtml(result.getInTermTopup()));
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            tv_t_c_description.setText(Html.fromHtml(result.getMaTermTopup()));
        }
        tv_t_c_description.setText(Html.fromHtml(result.getTermTopup()));
    }

    private void setWalletData(WalletEntity result) {
        tvCreditsAvailable.setText("$ " + result.getCredit() + "");
        tvPointsAvailable.setText("$ " + result.getPoint() + "");
        tvCreditsAvailable.setTypeface(Typeface.DEFAULT_BOLD);
        tvPointsAvailable.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == B_T_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                Log.e("ResultSuccess", "onActivityResult: " + result);
                //Price = Float.valueOf(AMOUNT) / prefHelper.getConvertedAmount();
                //formattedValuePrice = String.format("%.2f", Price);
                Price = Float.valueOf(AMOUNT);
                formattedValuePrice = String.format("%.2f", Price);
                serviceHelper.enqueueCall(webService.topUpCreditCash(formattedValuePrice, result.getPaymentMethodNonce().getNonce(), prefHelper.getSignUpUser().getToken()), WebServiceConstants.topupCredit);
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    public void getCurrencyRateSGD() {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            Call<ResponseWrapper<CurrencyRateEntity>> call = withoutHeaderWebService.getCurrencyRate(

                    AppConstants.USD,
                    AppConstants.SGD
            );

            call.enqueue(new Callback<ResponseWrapper<CurrencyRateEntity>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<CurrencyRateEntity>> call, Response<ResponseWrapper<CurrencyRateEntity>> response) {
                    loadingFinished();

                    //if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    if (response.body().getResponse().equals(WebServiceConstants.TOKEN_EXPIRED_ERROR_CODE)) {
                        UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                        prefHelper.setLoginStatus(false);
                        prefHelper.setFirebase_TOKEN(null);
                        prefHelper.setCountryCode(null);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(EnterNumberFragment.newInstance(), EnterNumberFragment.class.getName());
                    } else {
                        if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                            prefHelper.setConvertedAmount(response.body().getResult().getRate());
                            prefHelper.setConvertedAmountCurrency(AppConstants.SGD);
                            getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), FlashSaleFragment.class.getName());
                        } else {
                            UIHelper.showShortToastInCenter(getDockActivity(), response.body().getMessage());
                        }
                    }
                }


                @Override
                public void onFailure(Call<ResponseWrapper<CurrencyRateEntity>> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }

    public void getCurrencyRateMYR() {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            Call<ResponseWrapper<CurrencyRateEntity>> call = withoutHeaderWebService.getCurrencyRate(

                    AppConstants.USD,
                    AppConstants.MYR
            );

            call.enqueue(new Callback<ResponseWrapper<CurrencyRateEntity>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<CurrencyRateEntity>> call, Response<ResponseWrapper<CurrencyRateEntity>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                        prefHelper.setConvertedAmount(response.body().getResult().getRate());
                        prefHelper.setConvertedAmountCurrency(AppConstants.MYR);
                        getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), FlashSaleFragment.class.getName());

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<CurrencyRateEntity>> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }

    public void getCurrencyRateIDR() {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            Call<ResponseWrapper<CurrencyRateEntity>> call = withoutHeaderWebService.getCurrencyRate(

                    AppConstants.USD,
                    AppConstants.IDR
            );

            call.enqueue(new Callback<ResponseWrapper<CurrencyRateEntity>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<CurrencyRateEntity>> call, Response<ResponseWrapper<CurrencyRateEntity>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                        prefHelper.setConvertedAmount(response.body().getResult().getRate());
                        prefHelper.setConvertedAmountCurrency(AppConstants.IDR);
                        getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), FlashSaleFragment.class.getName());
                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<CurrencyRateEntity>> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }
}
