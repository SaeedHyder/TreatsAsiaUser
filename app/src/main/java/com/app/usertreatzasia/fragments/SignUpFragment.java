package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.EnterNumberEntity;
import com.app.usertreatzasia.entities.ResponseWrapper;
import com.app.usertreatzasia.entities.SignupEntities;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends BaseFragment {

    @BindView(R.id.edt_name)
    AnyEditTextView edtName;
    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edt_referal_code)
    AnyEditTextView edtReferalCode;
    @BindView(R.id.edt_password)
    AnyEditTextView edtPassword;
    @BindView(R.id.edt_cfm_password)
    AnyEditTextView edtCfmPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        //titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.sign_up));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (validate())
            if (edtPassword.getText().toString().length() < 6) {
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.password_length_alert));
            } else if (edtCfmPassword.getText().toString().length() < 6) {
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.password_length_alert));
            } else if (edtPassword.getText().toString().equals(edtCfmPassword.getText().toString())) {
                getSignupInfo();
            } else {
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.mismatch_confirm_password));
            }
    }

    private boolean validate() {
        return edtName.testValidity() && edtEmail.testValidity() && edtPassword.testValidity() && edtCfmPassword.testValidity();
    }

    private void getSignupInfo() {

        loadingStarted();
        Call<ResponseWrapper<SignupEntities>> call = webService.userSignup(

                prefHelper.getSignUpUser().getId(),
                edtName.getText().toString() + "",
                "",
                prefHelper.getFirebase_TOKEN(),
                edtEmail.getText().toString() + "",
                edtReferalCode.getText().toString() + "",
                AppConstants.DEVICE_TYPE,
                edtPassword.getText().toString() + "",
                edtCfmPassword.getText().toString() + "");

        call.enqueue(new Callback<ResponseWrapper<SignupEntities>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SignupEntities>> call, Response<ResponseWrapper<SignupEntities>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    prefHelper.setLoginStatus(true);
                    getDockActivity().popBackStackTillEntry(0);
                    prefHelper.setUserId(response.body().getResult().getId());
                    //prefHelper.putUser(response.body().getResult());
                    prefHelper.putSignupUser(response.body().getResult());

                    if (response.body().getResult().getIsSubscription() == 1 ) {
                        prefHelper.setSubscriptionStatus(true);
                    } else {
                        prefHelper.setSubscriptionStatus(false);
                    }

                    getDockActivity().popBackStackTillEntry(0);
                    serviceHelper.enqueueCall(webService.updateToken(FirebaseInstanceId.getInstance().getToken(), AppConstants.DEVICE_TYPE, response.body().getResult().getToken()), WebServiceConstants.updateToken);
                    getDockActivity().replaceDockableFragment(SubscriptionPlan.newInstance(false), "SubscriptionPlan");

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<SignupEntities>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.updateToken:
                //UIHelper.showShortToastInCenter(getDockActivity(), "Token Updated!");
                break;
        }
    }
}

