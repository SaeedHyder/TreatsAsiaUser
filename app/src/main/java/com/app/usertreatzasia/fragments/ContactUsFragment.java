package com.app.usertreatzasia.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.ResponseWrapper;
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

public class ContactUsFragment extends BaseFragment {

    @BindView(R.id.edt_msg)
    AnyEditTextView edt_msg;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    Unbinder unbinder;

    public static ContactUsFragment newInstance() {
        return new ContactUsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
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
        titleBar.setSubHeading(getString(R.string.contact_us));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (validate()){
            if (edt_msg.getText().toString().length()<10){
                UIHelper.showShortToastInCenter(getDockActivity(),"Message length should not be of less than 10 characters.");
            } else {
                updateProfileData();
            }
        }
    }

    private boolean validate() {
        return edt_msg.testValidity() ;
    }

    private void updateProfileData() {
        loadingStarted();
        Call<ResponseWrapper> call = webService.contactUs(

                edt_msg.getText().toString() + "",
                prefHelper.getSignUpUser().getToken()
        );

        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                getMainActivity().onLoadingFinished();

                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    UIHelper.showShortToastInCenter(getDockActivity(), "Your message has been sent successfully.");
                    getDockActivity().replaceDockableFragment(SettingsFragment.newInstance(), "SettingsFragment");

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }
}
