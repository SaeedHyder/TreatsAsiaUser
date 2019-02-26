package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.CmsEntity;
import com.app.usertreatzasia.entities.ResponseWrapper;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyFragment extends BaseFragment {


    @BindView(R.id.tv_body)
    AnyTextView tvBody;
    Unbinder unbinder;

    public static PrivacyPolicyFragment newInstance() {
        return new PrivacyPolicyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPrivacyPolicy();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.privacy_policy));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getPrivacyPolicy() {
        loadingStarted();
        Call<ResponseWrapper<CmsEntity>> call = webService.getCms(
                AppConstants.PRIVACY_POLICY,
                prefHelper.getSignUpUser().getToken()
        );

        call.enqueue(new Callback<ResponseWrapper<CmsEntity>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<CmsEntity>> call, Response<ResponseWrapper<CmsEntity>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    tvBody.setText(Html.fromHtml(response.body().getResult().getBody()));
                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<CmsEntity>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }
}
