package com.app.usertreatzasia.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.AddCreditFragment;
import com.app.usertreatzasia.entities.WalletEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.InternetHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CreditsFragment extends BaseFragment {
    @BindView(R.id.tv_points_amount)
    AnyTextView tvPointsAmount;
    @BindView(R.id.btn_cash_out_credits)
    Button btnCashOutCredits;
    @BindView(R.id.btn_add_credits)
    Button btnAddCredits;
    @BindView(R.id.btn_view_history)
    Button btnViewHistory;
    Unbinder unbinder;

    public static CreditsFragment newInstance() {
        return new CreditsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credits, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getWalletData();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Credits");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getWalletData();
    }

    @OnClick({R.id.btn_cash_out_credits, R.id.btn_add_credits, R.id.btn_view_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cash_out_credits:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    if (prefHelper.getSubscriptionStatus()) {
                        getDockActivity().replaceDockableFragment(CashOutFragment.newInstance(), "CashOutFragment");
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Non subscribers cannot avail this feature. Please purchase TreatzAsia subscription plan");
                    }
                break;
            case R.id.btn_add_credits:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    getDockActivity().replaceDockableFragment(AddCreditFragment.newInstance(), "AddCreditFragment");
                break;
            case R.id.btn_view_history:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    getDockActivity().replaceDockableFragment(CreditHistoryFragment.newInstance(), "CreditHistoryFragment");
                break;
        }
    }

    private void getWalletData() {
        serviceHelper.enqueueCall(webService.getWalletData(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getWallet);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.getWallet:
                setWalletData((WalletEntity) result);
                break;
        }
    }

    private void setWalletData(WalletEntity result) {
        if (result != null) {
            tvPointsAmount.setText("$" + result.getCredit() + "");
            tvPointsAmount.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }
}
