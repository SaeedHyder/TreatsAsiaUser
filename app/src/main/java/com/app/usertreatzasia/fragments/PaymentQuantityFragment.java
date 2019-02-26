package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.RewardsEnt;
import com.app.usertreatzasia.entities.WalletEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PaymentQuantityFragment extends BaseFragment {

    @BindView(R.id.tv_credit_type)
    AnyTextView tvCreditType;
    @BindView(R.id.tv_credit_amount)
    AnyTextView tvCreditAmount;
    @BindView(R.id.tv_buy_lucky)
    AnyTextView tvBuyLucky;
    @BindView(R.id.tv_sub_text)
    AnyTextView tvSubText;
    @BindView(R.id.tv_quantity)
    AnyTextView tvQuantity;
    @BindView(R.id.iv_up)
    ImageView ivUp;
    @BindView(R.id.ll_up)
    LinearLayout llUp;
    @BindView(R.id.txtQuantityNumber)
    AnyTextView txtQuantityNumber;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.ll_down)
    LinearLayout llDown;
    @BindView(R.id.rbtn_samsung)
    CheckBox rbtnSamsung;
    @BindView(R.id.btn_proceed)
    Button btnProceed;
    @BindView(R.id.txt_amount)
    AnyTextView txt_amount;
    private RewardsEnt rewardsEnt;
    private static String rewardsData = "rewardsData";
    int rpCounter;
    int constantRpCounter;
    int walletPoints;
    int counter = 1;
    int creditAmount;
    Unbinder unbinder;

    public static PaymentQuantityFragment newInstance(RewardsEnt rewardsEnt) {
        Bundle args = new Bundle();
        args.putString(rewardsData, new Gson().toJson(rewardsEnt));
        PaymentQuantityFragment fragment = new PaymentQuantityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rewardsData = getArguments().getString(rewardsData);
        if (rewardsData != null) {
            rewardsEnt = new Gson().fromJson(rewardsData, RewardsEnt.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_quantity, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRewardData();
        serviceHelper.enqueueCall(webService.getWalletData(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getWallet);
    }

    private void setRewardData() {
        tvCreditAmount.setText(rewardsEnt.getPoint());
        txtQuantityNumber.setText("1");
        rpCounter = Integer.parseInt(rewardsEnt.getPoint());
        constantRpCounter = Integer.parseInt(rewardsEnt.getPoint());
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Payment Method");
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {

        switch (Tag) {

            case WebServiceConstants.getWallet:
                setWalletData((WalletEntity) result);
                break;

            case WebServiceConstants.BuyRewardPoint:
                UIHelper.showLongToastInCenter(getDockActivity(), "You have successfully purchase lucky draw chances and consumed" + rpCounter + "points.");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(RewardsWalletFragment.newInstance(), "RewardsWalletFragment");
                break;
        }
    }

    private void setWalletData(WalletEntity result) {
        txt_amount.setText(result.getPoint());
        walletPoints = Integer.parseInt(result.getPoint());
    }

    @OnClick({R.id.ll_down, R.id.btn_proceed, R.id.ll_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_up:
                counter = counter + 1;
                rpCounter = rpCounter + constantRpCounter;
                if (rpCounter <= walletPoints ) {
                    txtQuantityNumber.setText(counter + "");
                    creditAmount = counter * Integer.valueOf(rewardsEnt.getPoint());
                    tvCreditAmount.setText(creditAmount+"");
                } else {
                    counter = counter - 1;
                    rpCounter = rpCounter - constantRpCounter;
                    txtQuantityNumber.setText(counter + "");
                    creditAmount = counter * Integer.valueOf(rewardsEnt.getPoint());
                    tvCreditAmount.setText(creditAmount+"");
                    UIHelper.showShortToastInCenter(getDockActivity(), "Error: You don't have enough points to buy " + counter + " tickets");
                }
                break;

            case R.id.ll_down:
                if (counter != 1 && !(counter < 1)) {
                    counter = counter - 1;
                    rpCounter = rpCounter - constantRpCounter;
                    txtQuantityNumber.setText(counter + "");
                    creditAmount = counter * Integer.valueOf(rewardsEnt.getPoint());
                    tvCreditAmount.setText(creditAmount+"");
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Quantity cannot be less than 1.");
                }

                break;
            case R.id.btn_proceed:
                if (prefHelper.getSubscriptionStatus()) {
                    serviceHelper.enqueueCall(webService.BuyRewardPoint(rewardsEnt.getId(), String.valueOf(counter), prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyRewardPoint);
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_subscription));
                }
                break;
        }
    }
}
