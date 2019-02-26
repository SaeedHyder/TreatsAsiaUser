package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.ResponseWrapper;
import com.app.usertreatzasia.entities.SubscriptionEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.adapters.ViewPagerAdapter;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscriptionPlan extends BaseFragment {


    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.sliding_tabs)
    TabLayout slidingTabs;

    ArrayList<SubscriptionEntity> entitySubscriptions;

/*    @BindView(R.id.edt_promo_code)
    AnyEditTextView edtPromoCode;*/

    private View rootView;

    @BindView(R.id.btn_select_packagef)
    Button btnSelectPackage;

    ViewPagerAdapter adapter;
    int positionOfPager;

    static boolean checkerSetting;
    static boolean checkerServiceComplete;

    public static SubscriptionPlan newInstance(boolean checker) {
        checkerSetting = checker;
        return new SubscriptionPlan();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscription_plan, container, false);
        rootView = view;
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSubscriptionData();
        checkerServiceComplete = false;
/*        if (edtPromoCode!= null){
            // TODO: 12/5/2017 get promo code if not null and send to service
        }*/
    }

    private void setViewPager(ArrayList<SubscriptionEntity> result) {

        adapter = new ViewPagerAdapter(getChildFragmentManager(),
                getDockActivity(), getMainActivity(), result);
        setPagerSetting();

        pager.setAdapter(adapter);

        slidingTabs.setupWithViewPager(pager);
        for (int i = 0; i < slidingTabs.getTabCount(); i++) {
            TabLayout.Tab tab = slidingTabs.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
        positionOfPager = pager.getCurrentItem();
        slidingTabs.addOnTabSelectedListener(OnTabSelectedListener);
        TabLayout.Tab currentTab = slidingTabs.getTabAt(positionOfPager);
        if (currentTab != null) {
            adapter.SetOnSelectView(currentTab, positionOfPager);
          /*  View customView = currentTab.getCustomView();
            if (customView != null) {
                customView.setSelected(true);
            }
            currentTab.select();*/
        }
    }

    private void setPagerSetting() {
        pager.setClipToPadding(false);
        pager.setPageMargin(10);
       /* pager.setPadding(20, 8, 20, 8);
        pager.setOffscreenPageLimit(3);*/
        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = pager.getMeasuredWidth() -
                        pager.getPaddingLeft() - pager.getPaddingRight();
                int pageHeight = pager.getHeight();
                int paddingLeft = pager.getPaddingLeft();
                float transformPos = (float) (page.getLeft() -
                        (pager.getScrollX() + paddingLeft)) / pageWidth;
                int max = pageHeight / 10;

                if (transformPos < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0.7f);// to make left transparent
                    page.setScaleY(0.9f);
                } else if (transformPos <= 1) { // [-1,1]
                    page.setAlpha(1f);
                    page.setScaleY(1f);
                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0.7f);// to make right transparent
                    page.setScaleY(0.9f);
                }
            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.subscriptions));
        if (!checkerSetting) {
            titleBar.showSkipText(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), "FlashSaleFragment");
                }
            });
        } else {
            titleBar.showBackButton();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private TabLayout.OnTabSelectedListener OnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            adapter.SetOnSelectView(tab, c);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int c = tab.getPosition();
            adapter.SetUnSelectView(tab, c);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };


    @OnClick(R.id.btn_select_packagef)
    public void onViewClicked() {

        if (checkerServiceComplete) {
            getDockActivity().replaceDockableFragment(CheckPromoCodeFragment.newInstance(adapter.getSelectedEntity(pager.getCurrentItem()).getPrice(),adapter.getSelectedEntity(pager.getCurrentItem()) == null ? 0 : adapter.getSelectedEntity(pager.getCurrentItem()).getId()), "CheckPromoCodeFragment");
        }
/*        if (checkerServiceComplete) {
            if (!checkerSetting) {
                getDockActivity().replaceDockableFragment(PaymentMethodFragment.newInstance(true, adapter.getSelectedEntity(pager.getCurrentItem()) == null ? 0 : adapter.getSelectedEntity(pager.getCurrentItem()).getId()), "PaymentMethodFragment");
            } else {
                getDockActivity().replaceDockableFragment(PaymentMethodFragment.newInstance(false, adapter.getSelectedEntity(pager.getCurrentItem()) == null ? 0 : adapter.getSelectedEntity(pager.getCurrentItem()).getId()), "PaymentMethodFragment");
            }
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), "Please wait.");
        }*/
    }

    public void getSubscriptionData() {
        loadingStarted();
        Call<ResponseWrapper<List<SubscriptionEntity>>> call = webService.getSubscription(
                prefHelper.getSignUpUser().getToken()
        );

        call.enqueue(new Callback<ResponseWrapper<List<SubscriptionEntity>>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<List<SubscriptionEntity>>> call, Response<ResponseWrapper<List<SubscriptionEntity>>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    entitySubscriptions = new ArrayList<SubscriptionEntity>();
                    entitySubscriptions = (ArrayList<SubscriptionEntity>) response.body().getResult();

                    if (!(entitySubscriptions.size() == 0)) {
                        setViewPager(entitySubscriptions);
                        //   adapter.data(entitySubscriptions);
                    } else {
                        UIHelper.showShortToastInCenter(getDockActivity(), "No Subscription Plans Available.");
                    }
                    checkerServiceComplete = true;

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<List<SubscriptionEntity>>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }
}
