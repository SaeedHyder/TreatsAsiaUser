package com.app.usertreatzasia.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.app.usertreatzasia.helpers.DialogHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.PinEntryEditText;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyNumberFragment extends BaseFragment {

    @BindView(R.id.txt_pin_entry)
    PinEntryEditText txt_pin_entry;
    @BindView(R.id.tv_msg)
    AnyTextView tv_msg;
    @BindView(R.id.tv_counter)
    AnyTextView tv_counter;
    @BindView(R.id.tv_seconds)
    AnyTextView tv_seconds;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    Unbinder unbinder;

    public static VerifyNumberFragment newInstance() {
        return new VerifyNumberFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify_number, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        counter();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.verification_code));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (txt_pin_entry.getText().toString().equalsIgnoreCase("")) {
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.enter_verification_code));
        } else if (txt_pin_entry.getText().toString().length() < 4) {
            UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.correct_verification_code));
        } else {
            if (timer != null) {
                timer.cancel();
            }
            verifyCode();
        }
    }

    CountDownTimer timer;

    public void counter() {
        timer = new CountDownTimer(225000, 1000) {

            public void onTick(long millisUntilFinished) {

                String text = String.format(Locale.getDefault(), "%2d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                if (tv_counter != null) {
                    tv_counter.setText(text + "");
                    tv_counter.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }

            public void onFinish() {
                if (tv_counter != null) {
                    tv_counter.setText("0");
                }

                if (tv_counter != null) {
                    if (tv_counter.getText().toString().equalsIgnoreCase("0")) {
                        final DialogHelper logoutdialog = new DialogHelper(getDockActivity());
                        logoutdialog.logout(R.layout.resend_code_dialog, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                logoutdialog.hideDialog();
                                resendCode();
                                counter();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                logoutdialog.hideDialog();
                                getDockActivity().replaceDockableFragment(EnterNumberFragment.newInstance(), "EnterNumberFragment");
                            }
                        });
                        logoutdialog.setCancelable(false);
                        logoutdialog.showDialog();
                    } else {

                    }
                }
            }
        }.start();
    }

    private void verifyCode() {
        loadingStarted();
        Call<ResponseWrapper<SignupEntities>> call = webService.verifyCode(
                prefHelper.getUser().getId(),
                txt_pin_entry.getText().toString()
        );

        call.enqueue(new Callback<ResponseWrapper<SignupEntities>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SignupEntities>> call, Response<ResponseWrapper<SignupEntities>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    getDockActivity().popBackStackTillEntry(0);
                    tv_counter.setText("0");
                    if (response.body().getResult().getIsSubscription() == 1) {
                        prefHelper.setSubscriptionStatus(true);
                    } else {
                        prefHelper.setSubscriptionStatus(false);
                    }

                    if (prefHelper.getUser().getIsVerified() == 0) {
                        prefHelper.putSignupUser((response.body().getResult()));
                        getDockActivity().replaceDockableFragment(SignUpFragment.newInstance(), "SignUpFragment");
                    } else {
                        if (prefHelper.getUser().getIsVerified() == 1) {
                            serviceHelper.enqueueCall(webService.updateToken(FirebaseInstanceId.getInstance().getToken(), AppConstants.DEVICE_TYPE, response.body().getResult().getToken()), WebServiceConstants.updateToken);
                        }

                        prefHelper.putSignupUser((response.body().getResult()));
                        prefHelper.setLoginStatus(true);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), "FlashSaleFragment");
                    }

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

    private void resendCode() {
        loadingStarted();
        Call<ResponseWrapper<EnterNumberEntity>> call = webService.enterNumber(
                prefHelper.getUser().getMobileNo()
        );

        call.enqueue(new Callback<ResponseWrapper<EnterNumberEntity>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<EnterNumberEntity>> call, Response<ResponseWrapper<EnterNumberEntity>> response) {
                loadingFinished();
                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                    prefHelper.putUser(response.body().getResult());
                    UIHelper.showShortToastInCenter(getDockActivity(), "Code has been sent again.");
                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<EnterNumberEntity>> call, Throwable t) {
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
                break;
        }
    }
}
