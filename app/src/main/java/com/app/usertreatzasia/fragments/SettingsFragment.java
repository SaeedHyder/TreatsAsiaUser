package com.app.usertreatzasia.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DialogHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingsFragment extends BaseFragment {

    @BindView(R.id.tv_suscribed_plans)
    AnyTextView tvSuscribedPlans;
    @BindView(R.id.tv_terms_and_conditions)
    AnyTextView tvTermsAndConditions;
    @BindView(R.id.tv_privacy_policy)
    AnyTextView tvPrivacyPolicy;
    @BindView(R.id.tv_contact_us)
    AnyTextView tvContactUs;
    @BindView(R.id.tv_change_password)
    AnyTextView tv_change_password;
    @BindView(R.id.tv_logout)
    AnyTextView tv_logout;
    @BindView(R.id.tv_invite_friends)
    AnyTextView tv_invite_friends;

    Unbinder unbinder;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (prefHelper.getSignUpUser().getIsSubscription() == 1) {
            tvSuscribedPlans.setText("My Subscription");
        } else {
            tvSuscribedPlans.setText("Subscription Plan");
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getString(R.string.settings));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_suscribed_plans, R.id.tv_terms_and_conditions, R.id.tv_privacy_policy, R.id.tv_change_password, R.id.tv_contact_us, R.id.tv_logout, R.id.tv_invite_friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_suscribed_plans:

                if (prefHelper.getSubscriptionStatus()) {
                    getDockActivity().replaceDockableFragment(MySubscriptionFragment.newInstance(), MySubscriptionFragment.class.getName());
                } else {
                    getDockActivity().replaceDockableFragment(SubscriptionPlan.newInstance(true), SubscriptionPlan.class.getName());
                }
                break;
            case R.id.tv_invite_friends:
                if (prefHelper.getSubscriptionStatus()) {
                    getDockActivity().replaceDockableFragment(InviteFriendsFragment.newInstance(), InviteFriendsFragment.class.getName());
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.error_payment_subscription));
                }
                break;
            case R.id.tv_terms_and_conditions:
                getDockActivity().replaceDockableFragment(TermsAndConditionsFragment.newInstance(), TermsAndConditionsFragment.class.getName());
                break;
            case R.id.tv_privacy_policy:
                getDockActivity().replaceDockableFragment(PrivacyPolicyFragment.newInstance(), PrivacyPolicyFragment.class.getName());
                break;
            case R.id.tv_change_password:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), "ChangePasswordFragment");
                break;
            case R.id.tv_contact_us:
                Uri uri = Uri.parse(prefHelper.getContactUsCms());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                UIHelper.showLongToastInCenter(getDockActivity(), "You are being redirect to Treatz Asia website.");
                break;
            case R.id.tv_logout:

                final DialogHelper logoutdialog = new DialogHelper(getDockActivity());
                logoutdialog.logout(R.layout.logout_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutdialog.hideDialog();
                        logoutUser();
                        prefHelper.setLoginStatus(false);
                        prefHelper.setFirebase_TOKEN(null);
                        prefHelper.setCountryCode(null);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(EnterNumberFragment.newInstance(), EnterNumberFragment.class.getName());
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutdialog.hideDialog();
                    }
                });
                logoutdialog.setCancelable(false);
                logoutdialog.showDialog();

                break;
        }
    }

    private void logoutUser() {
        serviceHelper.enqueueCall(webService.logout(prefHelper.getSignUpUser().getToken()), WebServiceConstants.logout);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.logout:
                prefHelper.setLoginStatus(false);
                prefHelper.setFirebase_TOKEN(null);
                prefHelper.setCountryCode(null);
                prefHelper.putSideMenuName("");
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(EnterNumberFragment.newInstance(), EnterNumberFragment.class.getName());
                break;
        }
    }
}
