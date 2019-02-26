package com.app.usertreatzasia.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.PromoCodeEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.helpers.Utils;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.app.usertreatzasia.ui.views.Util;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CheckPromoCodeFragment extends BaseFragment {


    private static final int B_T_REQUEST_CODE = 9001;
    @BindView(R.id.rbtn_cash)
    CheckBox rbtnCash;
    @BindView(R.id.txt_amount)
    AnyTextView txtAmount;
    @BindView(R.id.edt_enter_promo_code)
    AnyEditTextView edtEnterPromoCode;
    @BindView(R.id.btn_proceed)
    Button btnProceed;
    static int SUBSCRIPTION_ID;
    static String ACTUAL_PRICE;
    static boolean checkerButton;
    Unbinder unbinder;

    public static CheckPromoCodeFragment newInstance(String actual_price, int subscription_id) {
        SUBSCRIPTION_ID = subscription_id;
        ACTUAL_PRICE = actual_price;
        return new CheckPromoCodeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_promo_code, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkerButton = true;
        txtAmount.setText(ACTUAL_PRICE);
        edtEnterPromoCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtEnterPromoCode.getText().toString().equalsIgnoreCase("")) {
                    btnProceed.setText("Proceed");
                } else {
                    btnProceed.setText("Check Promo Code");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        rbtnCash.setChecked(true);
        rbtnCash.setEnabled(false);
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
        unbinder.unbind();
    }

    @OnClick(R.id.btn_proceed)
    public void onViewClicked() {

        UtilsGlobal.HideKeyBoard(getDockActivity());
        if (checkerButton) {
            if (edtEnterPromoCode != null && !edtEnterPromoCode.getText().toString().equalsIgnoreCase("")) {
                serviceHelper.enqueueCall(webService.checkPromoCode(SUBSCRIPTION_ID, edtEnterPromoCode.getText().toString(), prefHelper.getSignUpUser().getToken()), WebServiceConstants.checkPromoCode);
            } else {
                if (edtEnterPromoCode.getText().toString().equalsIgnoreCase("")) {
                    DropInRequest dropInRequest = new DropInRequest()
                            .clientToken(AppConstants.clientToken);
                    startActivityForResult(dropInRequest.getIntent(getDockActivity()), B_T_REQUEST_CODE);
                }
            }
        } else {

            DropInRequest dropInRequest = new DropInRequest()
                    .clientToken(AppConstants.clientToken);
            startActivityForResult(dropInRequest.getIntent(getDockActivity()), B_T_REQUEST_CODE);
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.checkPromoCode:
                setWalletDataAndUI((PromoCodeEnt) result);
                break;
        }

        switch (Tag) {
            case WebServiceConstants.BUY_SUBSCRIPTION_CASH:
                    getDockActivity().replaceDockableFragment(InviteFriendsFragment.newInstance(), "InviteFriendsFragment");
                    prefHelper.setSubscriptionStatus(true);
                break;
        }
    }

    private void setWalletDataAndUI(PromoCodeEnt result) {
        txtAmount.setText(result.getAmount());
        btnProceed.setText("Proceed");
        edtEnterPromoCode.setFocusable(false);
        edtEnterPromoCode.setEnabled(false);
        edtEnterPromoCode.setCursorVisible(false);
        edtEnterPromoCode.setKeyListener(null);
        checkerButton = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == B_T_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                Log.e("ResultSuccess", "onActivityResult: "+result);
                serviceHelper.enqueueCall(webService.SuscribePromoCodeCash(SUBSCRIPTION_ID, txtAmount.getText().toString(),result.getPaymentMethodNonce().getNonce(),  prefHelper.getSignUpUser().getToken()), WebServiceConstants.BUY_SUBSCRIPTION_CASH);
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
