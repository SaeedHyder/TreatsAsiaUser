package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.countEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SimpleSideMenuFragment extends BaseFragment {


    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_profile)
    LinearLayout llProfile;
    @BindView(R.id.ll_wallet)
    LinearLayout llWallet;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.txt_badge_notifications)
    AnyTextView txtBadgeNotifications;
    @BindView(R.id.ll_notofications)
    LinearLayout llNotofications;
    @BindView(R.id.ll_language)
    LinearLayout llLanguage;
    @BindView(R.id.ll_about_app)
    LinearLayout llAboutApp;
    @BindView(R.id.ll_referRresaurant)
    LinearLayout ll_referRresaurant;
    @BindView(R.id.ll_settings)
    LinearLayout llSettings;
    Unbinder unbinder;
    @BindView(R.id.tv_home)
    AnyTextView tvHome;
    @BindView(R.id.tv_profile)
    AnyTextView tvProfile;
    @BindView(R.id.tv_wallet)
    AnyTextView tvWallet;
    @BindView(R.id.tv_history)
    AnyTextView tvHistory;
    @BindView(R.id.tv_notification)
    AnyTextView tvNotification;
    @BindView(R.id.tv_language)
    AnyTextView tvLanguage;
    @BindView(R.id.tv_about_app)
    AnyTextView tvAboutApp;
    @BindView(R.id.tv_setttings)
    AnyTextView tvSetttings;
    @BindView(R.id.tv_ReferRestaurant)
    AnyTextView tvReferRestaurant;


    public static SimpleSideMenuFragment newInstance() {
        return new SimpleSideMenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_sidemenu, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper != null && prefHelper.getSideMenuName() != null && !prefHelper.getSideMenuName().equalsIgnoreCase("")) {
            if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.HOME)) {
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.PROFILE)) {
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.WALLET)) {
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.HISTORY)) {
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.NOTIFICATIONS)) {
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.REFERRESTAURANT)) {
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            }else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.LANGUAGE)) {
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.ABOUT_APP)) {
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.SETTINGS)) {
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else {
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            }
        } else {
            tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
        }
        getNotificationCount();
        setbadgeCount();
    }

    private void getNotificationCount() {
/*        if (prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null) {
            serviceHelper.enqueueCall(webService.getNotificationCount(prefHelper.getSignUpUser().getToken()),
                    WebServiceConstants.getNotificationCount);
        }*/
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {

            case WebServiceConstants.getNotificationCount:
                //prefHelper.setNotificationCount(((countEnt) result).getCount());
                /*setbadgeCount();*/

                break;
        }
    }

    private void setbadgeCount() {
        if (txtBadgeNotifications!=null) {
            if (prefHelper.getNotificationCount() > 0) {
                // AppConstants.BadgeCountNotification = 0;
                txtBadgeNotifications.setVisibility(View.VISIBLE);
                txtBadgeNotifications.setText(prefHelper.getNotificationCount() + "");
            } else {
                txtBadgeNotifications.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();


        if (prefHelper != null && prefHelper.getSideMenuName() != null && !prefHelper.getSideMenuName().equalsIgnoreCase("")) {
            if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.HOME)) {
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.PROFILE)) {
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.WALLET)) {
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.HISTORY)) {
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.NOTIFICATIONS)) {
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            }else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.REFERRESTAURANT)) {
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.LANGUAGE)) {
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.ABOUT_APP)) {
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else if (prefHelper.getSideMenuName().equalsIgnoreCase(AppConstants.SETTINGS)) {
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            } else {
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
            }
        } else {
            tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
        }
    }

    void closeMenu() {
        getMainActivity().closemenu();
    }

    @OnClick({R.id.ll_home, R.id.ll_profile, R.id.ll_wallet, R.id.ll_history, R.id.ll_notofications, R.id.ll_language, R.id.ll_about_app, R.id.ll_settings, R.id.ll_referRresaurant})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvLanguage.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                prefHelper.putSideMenuName(AppConstants.HOME);
                closeMenu();
                getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), FlashSaleFragment.class.getName());
                break;
            case R.id.ll_profile:
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvLanguage.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                prefHelper.putSideMenuName(AppConstants.PROFILE);
                closeMenu();
                getDockActivity().replaceDockableFragment(ProfileFragment.newInstance(), ProfileFragment.class.getName());
                break;
            case R.id.ll_wallet:
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvLanguage.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                prefHelper.putSideMenuName(AppConstants.WALLET);
                closeMenu();
                getDockActivity().replaceDockableFragment(WalletFragment.newInstance(), WalletFragment.class.getName());
                break;
            case R.id.ll_history:
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvLanguage.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                prefHelper.putSideMenuName(AppConstants.HISTORY);
                closeMenu();
                getDockActivity().replaceDockableFragment(MyHistoryFragment.newInstance(), MyHistoryFragment.class.getName());
                break;
            case R.id.ll_notofications:
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvLanguage.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                prefHelper.putSideMenuName(AppConstants.NOTIFICATIONS);
                closeMenu();
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), NotificationsFragment.class.getName());
                txtBadgeNotifications.setVisibility(View.GONE);
                break;

            case R.id.ll_referRresaurant:
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
                tvLanguage.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                prefHelper.putSideMenuName(AppConstants.LANGUAGE);
                closeMenu();
                getDockActivity().replaceDockableFragment(ReferResturantFragment.newInstance(), ReferResturantFragment.class.getName());
                break;
            case R.id.ll_language:
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvLanguage.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                prefHelper.putSideMenuName(AppConstants.LANGUAGE);
                closeMenu();
                getDockActivity().replaceDockableFragment(LanguageFragment.newInstance(), LanguageFragment.class.getName());
                break;
            case R.id.ll_about_app:
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvLanguage.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                prefHelper.putSideMenuName(AppConstants.ABOUT_APP);
                closeMenu();
                getDockActivity().replaceDockableFragment(AboutAppFragment.newInstance(), AboutAppFragment.class.getName());
                break;
            case R.id.ll_settings:
                tvHome.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvProfile.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvWallet.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvHistory.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvNotification.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvReferRestaurant.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvLanguage.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvAboutApp.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.white));
                tvSetttings.setTextColor(ContextCompat.getColor(getDockActivity(), R.color.golden));
                prefHelper.putSideMenuName(AppConstants.SETTINGS);
                closeMenu();
                getDockActivity().replaceDockableFragment(SettingsFragment.newInstance(), SettingsFragment.class.getName());
                break;
        }
    }
}

