package com.app.usertreatzasia.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.AddCmsEntity;
import com.app.usertreatzasia.entities.AdvertisementEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AdvertisementFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_msg)
    AnyTextView tvMsg;
    @BindView(R.id.tv_counter)
    AnyTextView tvCounter;
    @BindView(R.id.tv_seconds_skip)
    AnyTextView tvSecondsSkip;
    @BindView(R.id.ll_skip)
    LinearLayout llSkip;
    int serviceTime;
    boolean enableSkip = false;
    @BindView(R.id.imageView)
    ImageView imageView;
    ImageLoader imageLoader;
    boolean checkerServiceLoaded = false;

    public static AdvertisementFragment newInstance() {
        return new AdvertisementFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advertisement, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(webService.getAdCms(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getAdCms);
        tvMsg.setVisibility(View.VISIBLE);
        tvCounter.setVisibility(View.VISIBLE);
        if (checkerServiceLoaded) {
            setAdvertismentData();
        }
    }

    private void setAdvertismentData() {

        if (prefHelper.advertisementEntity() != null) {

            loadingStarted();
            Picasso.with(getDockActivity())
                    .load(prefHelper.advertisementEntity().getAdvertisementImage())
                    .into(imageView, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            loadingFinished();
                            counter();
                        }

                        @Override
                        public void onError() {
                            loadingFinished();
                            UIHelper.showLongToastInCenter(getDockActivity(), "Fail");
                        }
                    });
        }
    }

    CountDownTimer timer;

    public void counter() {

        serviceTime = Integer.parseInt(prefHelper.advertisementEntity().getTime());
        serviceTime = serviceTime * 1000;

        timer = new CountDownTimer(serviceTime, 1000) {

            public void onTick(long millisUntilFinished) {

                String text = String.format(Locale.getDefault(), "%2d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                if (tvCounter != null) {
                    tvCounter.setText(text + "");
                    tvCounter.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }

            public void onFinish() {
                if (tvCounter != null) {
                    tvCounter.setText("0");
                }
                if (tvCounter.getText().toString().equalsIgnoreCase("0")) {

                    tvMsg.setVisibility(View.GONE);
                    tvCounter.setVisibility(View.GONE);
                    tvSecondsSkip.setText(R.string.skips);
                    enableSkip = true;
                } else {

                }
            }
        }.start();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @OnClick({R.id.ll_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_skip:
                if (enableSkip) {
                    if (timer != null) {
                        timer.cancel();
                    }
                    getDockActivity().popBackStackTillEntry(0);
                    if (prefHelper.isLogin()) {
                        getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), "FlashSaleFragment");
                    } else {
                        getDockActivity().replaceDockableFragment(EnterNumberFragment.newInstance(), "EnterNumberFragment");
                    }
                }
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {

            case WebServiceConstants.getAdCms:
                AddCmsEntity entity = (AddCmsEntity) result;
                AdvertisementEntity advertisementEntity = entity.getAdvertisement();
                prefHelper.putAdvertisementEntity(advertisementEntity);
                prefHelper.setRewardCms(entity.getReward());
                prefHelper.setContactUsCms(entity.getContactus());
                checkerServiceLoaded = true;
                setAdvertismentData();
                break;
        }
    }
}