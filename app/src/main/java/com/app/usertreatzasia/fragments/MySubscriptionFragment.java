package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.NewUserSubscriptionEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MySubscriptionFragment extends BaseFragment {

    @BindView(R.id.tv_membership_type)
    AnyTextView tvMembershipType;
    @BindView(R.id.tv_subscription_date)
    AnyTextView tvSubscriptionDate;
    @BindView(R.id.tv_expiry_date)
    AnyTextView tvExpiryDate;
    @BindView(R.id.btn_upgrade_subscription)
    Button btnUpgradeSubscription;
    Unbinder unbinder;

    public static MySubscriptionFragment newInstance() {
        return new MySubscriptionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_subscription, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getNewSubscriptionData();
    }

    private void getNewSubscriptionData() {

        serviceHelper.enqueueCall(webService.getNewUserSubscription(prefHelper.getSignUpUser().getToken()), WebServiceConstants.NEW_SUBSCRIPTION);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {

            case WebServiceConstants.NEW_SUBSCRIPTION:
                NewUserSubscriptionEntity entity = (NewUserSubscriptionEntity) result;
                setSubscriptionData(entity);
                break;
        }
    }

    private void setSubscriptionData(NewUserSubscriptionEntity entity) {
        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            if (entity.getSubscriptionDetail().getSubscriptionName()!=null)
            tvMembershipType.setText(entity.getSubscriptionDetail().getSubscriptionName() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            if (entity.getSubscriptionDetail().getInSubscriptionName()!=null)
            tvMembershipType.setText(entity.getSubscriptionDetail().getInSubscriptionName() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            if (entity.getSubscriptionDetail().getMaSubscriptionName()!=null)
            tvMembershipType.setText(entity.getSubscriptionDetail().getMaSubscriptionName() + "");
        } else {
            if (entity.getSubscriptionDetail().getSubscriptionName()!=null)
            tvMembershipType.setText(entity.getSubscriptionDetail().getSubscriptionName() + "");
        }

        if (entity.getPurchaseDate()!=null)
        tvSubscriptionDate.setText(DateHelper.dateFormat(entity.getPurchaseDate(), AppConstants.DateFormat_DmY_NEW_Subs, AppConstants.DateFormat_YMD));
        if (entity.getExpireDate()!=null)
        tvExpiryDate.setText(DateHelper.dateFormat(entity.getExpireDate(), AppConstants.DateFormat_DmY_NEW_Subs, AppConstants.DateFormat_YMD));
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.my_subscription));
    }

    @OnClick(R.id.btn_upgrade_subscription)
    public void onViewClicked() {
        getDockActivity().replaceDockableFragment(SubscriptionPlan.newInstance(true),SubscriptionPlan.class.getName());
    }
}
