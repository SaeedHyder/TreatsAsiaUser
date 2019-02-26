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
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends BaseFragment {


    @BindView(R.id.edt_password)
    AnyEditTextView edt_password;
    @BindView(R.id.edt_new_password)
    AnyEditTextView edt_new_password;
    @BindView(R.id.edt_cfm_password)
    AnyEditTextView edt_cfm_password;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    Unbinder unbinder;

    public static ChangePasswordFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
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
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.change_password));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean validate() {
        return edt_password.testValidity() && edt_new_password.testValidity() && edt_cfm_password.testValidity();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (validate())
            if (edt_password.getText().toString().length() < 6) {
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.password_length_alert));
            } else if (edt_new_password.getText().toString().length() < 6) {
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.password_length_alert));
            } else if (edt_cfm_password.getText().toString().length() < 6) {
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.password_length_alert));
            } else if (edt_new_password.getText().toString().equals(edt_cfm_password.getText().toString())) {
                updatePassword();
            } else {
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.mismatch_password));
            }
    }

    private void updatePassword() {
        loadingStarted();
        Call<ResponseWrapper<SignupEntities>> call = webService.changePassword(
                edt_password.getText().toString(),
                edt_new_password.getText().toString(),
                edt_cfm_password.getText().toString(),
                prefHelper.getSignUpUser().getToken()
        );

        call.enqueue(new Callback<ResponseWrapper<SignupEntities>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SignupEntities>> call, Response<ResponseWrapper<SignupEntities>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Password has been changed successfully.");
                    getDockActivity().replaceDockableFragment(SettingsFragment.newInstance(), "SettingsFragment");
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
}
