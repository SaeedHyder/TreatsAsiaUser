package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.WalletEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.InternetHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class WalletFragment extends BaseFragment {


    @BindView(R.id.tv_amount_vouchers_available)
    AnyTextView tvAmountVouchersAvailable;
    @BindView(R.id.ll_coupons_available)
    LinearLayout llCouponsAvailable;
    @BindView(R.id.tv_amount_gifts_recieved)
    AnyTextView tvAmountGiftsRecieved;
    @BindView(R.id.ll_gifts_recieved)
    LinearLayout llGiftsRecieved;
    @BindView(R.id.tv_amount_event_ticket)
    AnyTextView tvAmountEventTicket;
    @BindView(R.id.ll_event_tickets)
    LinearLayout llEventTickets;
    @BindView(R.id.tv_amount_lucky)
    AnyTextView tvAmountLucky;
    @BindView(R.id.ll_lucky_draw_chances)
    LinearLayout llLuckyDrawChances;
    @BindView(R.id.tv_amount_total_points)
    AnyTextView tvAmountTotalPoints;
    @BindView(R.id.ll_total_points)
    LinearLayout llTotalPoints;
    @BindView(R.id.tv_amount_total_credits)
    AnyTextView tvAmountTotalCredits;
    @BindView(R.id.ll_total_credits)
    LinearLayout llTotalCredits;
    @BindView(R.id.ll_gifts_events_recieved)
    LinearLayout ll_gifts_events_recieved;
    @BindView(R.id.tv_amount_gifts_events_recieved)
    AnyTextView tv_amount_gifts_events_recieved;
    Unbinder unbinder;

    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWalletData();
        getMainActivity().refreshSideMenu();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getString(R.string.wallet));
        if (prefHelper.getNotificationCount() > 0) {
            titleBar.showNotificationDot();
        } else {
            titleBar.hideNotificationDot();
        }
    }

    @OnClick({R.id.ll_coupons_available, R.id.ll_gifts_recieved, R.id.ll_event_tickets, R.id.ll_lucky_draw_chances, R.id.ll_total_points, R.id.ll_total_credits, R.id.ll_gifts_events_recieved})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_coupons_available:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(), "HomeWalletFragment");
                //getDockActivity().replaceDockableFragment(VoucherAndGiftWalletFragment.newInstance(), "VoucherAndGiftWalletFragment");
                break;

            case R.id.ll_event_tickets:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    getDockActivity().replaceDockableFragment(EventsWalletFragment.newInstance(), "EventsWalletFragment");
                //getDockActivity().replaceDockableFragment(EventAndGiftWalletFragment.newInstance(), "EventAndGiftWalletFragment");
                break;

            case R.id.ll_lucky_draw_chances:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    getDockActivity().replaceDockableFragment(RewardsWalletFragment.newInstance(), "RewardsFragment");
                break;

            case R.id.ll_total_points:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    getDockActivity().replaceDockableFragment(PointsFragment.newInstance(), "PointsFragment");
                break;

            case R.id.ll_total_credits:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    getDockActivity().replaceDockableFragment(CreditsFragment.newInstance(), "CreditsFragment");
                break;

            //visibility for these two are now gone and are so useless
            case R.id.ll_gifts_events_recieved:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    getDockActivity().replaceDockableFragment(GiftEventRecievedFragment.newInstance(), "GiftEventRecievedFragment");
                break;

            case R.id.ll_gifts_recieved:
                if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity()))
                    getDockActivity().replaceDockableFragment(GiftRecievedFragment.newInstance(), "GiftRecievedFragment");
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

            int voucherCount;
            voucherCount = result.getUserEvoucherCount() + result.getUserGiftEvoucherCount();
            tvAmountVouchersAvailable.setText(voucherCount + "");
            tvAmountGiftsRecieved.setText(result.getUserGiftEvoucherCount() + "");

            int eventCount;
            eventCount = result.getUserEventCount() + result.getUserGiftEventCount();
            tvAmountEventTicket.setText(eventCount + "");
            tv_amount_gifts_events_recieved.setText(result.getUserGiftEventCount() + "");

            tvAmountLucky.setText(result.getUserReward() + "");
            tvAmountTotalPoints.setText(result.getPoint() + "");
            tvAmountTotalCredits.setText("$" + " " + result.getCredit());
        }
    }
}
