package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.ResponseWrapper;
import com.app.usertreatzasia.entities.SubscriptionPostEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PaymentMethodFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.rbtn_paypal)
    CheckBox rbtnPaypal;
    @BindView(R.id.rbtn_samsung)
    CheckBox rbtncredits;
    @BindView(R.id.btn_proceed)
    Button btnProceed;

    static boolean checkerInviteOrSettings;
    static int SUBSCRIPTION_ID;
    static String Home = "";

    public static PaymentMethodFragment newInstance(boolean checker, int subscription_id) {
        checkerInviteOrSettings = checker;
        SUBSCRIPTION_ID = subscription_id;
        return new PaymentMethodFragment();
    }

    public static PaymentMethodFragment newInstance(String home){
        Home = home;
        return new PaymentMethodFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.payment_method));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_method, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setlistener();
        setCreditsText("$10");
    }

    private void setCreditsText(String credit) {
        String text = getString(R.string.use_credits) + " " + " <font color='#DBA431'>" + "(" + credit + ")" + "</font>";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            rbtncredits.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            rbtncredits.setText(Html.fromHtml(text));
        }
    }

    private void setlistener() {
        rbtnPaypal.setOnCheckedChangeListener(this);
        rbtncredits.setOnCheckedChangeListener(this);
    }

    @OnClick(R.id.btn_proceed)
    public void onViewClicked() {
        //subscriptionPost();

        if (Home.equals("goToHome") && Home != null){
            UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented with Payment Module.");
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
        } else if (!checkerInviteOrSettings) {
            UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented with Payment Module.");
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
            //getDockActivity().replaceDockableFragment(SettingsFragment.newInstance(), "SettingsFragment");
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented with Payment Module.");
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
            //getDockActivity().replaceDockableFragment(InviteFriendsFragment.newInstance(), "InviteFriendsFragment");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    }

    public void subscriptionPost() {
        loadingStarted();
        Call<ResponseWrapper<SubscriptionPostEntity>> call = webService.userSubscription(
                prefHelper.getUser().getId(),
                SUBSCRIPTION_ID,
                "",// TODO: 10/9/2017 amount
                ""/*,// TODO: 10/9/2017 currency
                prefHelper.getUser().getToken()*/
        );

        call.enqueue(new Callback<ResponseWrapper<SubscriptionPostEntity>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SubscriptionPostEntity>> call, Response<ResponseWrapper<SubscriptionPostEntity>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    UIHelper.showShortToastInCenter(getDockActivity(), "Your package has been suscribed.");

                    if (!checkerInviteOrSettings) {
                        getDockActivity().replaceDockableFragment(SettingsFragment.newInstance(), "SettingsFragment");
                    } else {
                        getDockActivity().replaceDockableFragment(InviteFriendsFragment.newInstance(), "InviteFriendsFragment");
                    }

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<SubscriptionPostEntity>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }
}
