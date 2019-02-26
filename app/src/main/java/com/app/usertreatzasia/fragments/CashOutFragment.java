package com.app.usertreatzasia.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.TopupCashoutTermsConditionEntity;
import com.app.usertreatzasia.entities.WalletEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DialogHelper;
import com.app.usertreatzasia.helpers.InternetHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CashOutFragment extends BaseFragment {

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
    int valueStore = 0;
    Float TotalCredit;
    Float AMOUNT;
    Unbinder unbinder;

    public static CashOutFragment newInstance() {
        return new CashOutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_out, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getWalletData();
        getTermsAndCondition();
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Cashout");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.edt_enter_points, R.id.btn_top_up_points, R.id.btn_TandC})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edt_enter_points:
                break;
            case R.id.btn_top_up_points:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    if (!edtEnterPoints.getText().toString().equalsIgnoreCase("") && !edtEnterPoints.getText().toString().equalsIgnoreCase("0")) {
                        AMOUNT = Float.valueOf(edtEnterPoints.getText().toString());
                        if (!(AMOUNT > TotalCredit)) {
                            if (getDockActivity() != null) {
                                String formattedAmount = String.format("%.2f", AMOUNT);
                                final DialogHelper cahoutDialog = new DialogHelper(getDockActivity());
                                cahoutDialog.cashout(R.layout.cashout_dialog, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cahoutDialog.hideDialog();
                                        if (edtEnterPoints != null && edtEnterPoints.getText().toString() != null && !edtEnterPoints.getText().toString().equals("0") && !edtEnterPoints.getText().toString().equals("")) {
                                            if ((Integer.parseInt(edtEnterPoints.getText().toString())) <= valueStore) {
                                                cashoutCredit();
                                            } else {
                                                UIHelper.showShortToastInCenter(getDockActivity(), "You do not have the required credits to proceed.");
                                            }
                                        } else {
                                            UIHelper.showShortToastInCenter(getDockActivity(), "Please enter amount to proceed.");
                                        }
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        cahoutDialog.hideDialog();
                                    }
                                }, "Are you sure you want to cash out " + formattedAmount + " credits to points.", "Confirmation");
                                cahoutDialog.setCancelable(false);
                                cahoutDialog.showDialog();
                            }
                        } else {
                            UIHelper.showShortToastInCenter(getDockActivity(), "Credit amount entered is more than the current credits available! ");
                        }
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Please enter credit to proceed!");
                    }
                break;
            case R.id.btn_TandC:
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
        if (edtEnterPoints != null && prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
            serviceHelper.enqueueCall(webService.cashoutCredit(edtEnterPoints.getText().toString(), prefHelper.getSignUpUser().getToken()), WebServiceConstants.getCashoutCredit);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.getWallet:
                setWalletData((WalletEntity) result);
                break;

            case WebServiceConstants.getCashoutCredit:
                UIHelper.showShortToastInCenter(getDockActivity(), "Credits have been successfully converted into points.");
                getMainActivity().popFragment();
                break;

            case WebServiceConstants.getTermsConditionTopupCashout:
                setTermsConditions((TopupCashoutTermsConditionEntity) result);
                break;
        }
    }

    private void setWalletData(WalletEntity result) {
        tvCreditsAvailable.setText("$" + result.getCredit() + "");
        tvPointsAvailable.setText(result.getPoint() + "");
        TotalCredit = Float.valueOf(result.getCredit());
        tvCreditsAvailable.setTypeface(Typeface.DEFAULT_BOLD);
        tvPointsAvailable.setTypeface(Typeface.DEFAULT_BOLD);
        valueStore = Integer.parseInt(result.getPoint());
    }

    private void setTermsConditions(TopupCashoutTermsConditionEntity result) {
        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            tv_t_c_description.setText(Html.fromHtml(result.getTermCashout()));
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            tv_t_c_description.setText(Html.fromHtml(result.getInTermCashout()));
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            tv_t_c_description.setText(Html.fromHtml(result.getMaTermCashout()));
        } else {
            tv_t_c_description.setText(Html.fromHtml(result.getTermCashout()));
        }
    }
}
