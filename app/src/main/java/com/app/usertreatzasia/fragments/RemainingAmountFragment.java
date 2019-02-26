package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.PurchaseEnt;
import com.app.usertreatzasia.entities.RedemptionEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RemainingAmountFragment extends BaseFragment {


    @BindView(R.id.tv_remaining_amount)
    AnyTextView tvRemainingAmount;
    @BindView(R.id.btn_proceed)
    Button btnProceed;
    Unbinder unbinder;
    @BindView(R.id.iv_productImage)
    ImageView ivProductImage;
    private RedemptionEnt redemptionEnt;
    private static String redemptionItemData = "redemptionItemData";
    private static String REMAINING_AMOUNT = "";
    private static String PHOTO = "";
    private ImageLoader imageLoader;
    Float updatedRemainingAmount;

    public static RemainingAmountFragment newInstance() {
        return new RemainingAmountFragment();
    }

    public static RemainingAmountFragment newInstance(String remainingAmount, String photo) {
        Bundle b = new Bundle();
        REMAINING_AMOUNT = remainingAmount;
        PHOTO = photo;
        RemainingAmountFragment fragment = new RemainingAmountFragment();
        b.putString(REMAINING_AMOUNT, remainingAmount);
        b.putString(PHOTO, photo);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();

/*        REMAINING_AMOUNT = getArguments().getString(REMAINING_AMOUNT);
        if (REMAINING_AMOUNT != null) {
            REMAINING_AMOUNT = new Gson().fromJson(REMAINING_AMOUNT, PurchaseEnt.class);
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remaining_amount, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData();
    }

    private void setData() {

        imageLoader.displayImage(PHOTO, ivProductImage);
        if (REMAINING_AMOUNT.equalsIgnoreCase("")) {
            tvRemainingAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + 0 + "");
        } else {
            //tvRemainingAmount.setText("$ " + REMAINING_AMOUNT + "");
/*            updatedRemainingAmount = Float.valueOf(REMAINING_AMOUNT) * prefHelper.getConvertedAmount();
            String formattedValue = String.format("%.2f", updatedRemainingAmount);
            tvRemainingAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");*/
            tvRemainingAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + REMAINING_AMOUNT + "");
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
/*        titleBar.showNotificationButtonInCorner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(), "NotificationsFragment");
            }
        }, prefHelper.getNotificationCount());*/
        titleBar.setSubHeading(getString(R.string.history));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_proceed)
    public void onViewClicked() {
        UIHelper.showShortToastInCenter(getDockActivity(), "Will be implemented in Beta.");
    }
}
