package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.PurchaseEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EventTicket extends BaseFragment {

    @BindView(R.id.txt_ticket_Id)
    AnyTextView txtTicketId;
    @BindView(R.id.txt_event)
    AnyTextView txtEvent;
    @BindView(R.id.txt_amount_paid)
    AnyTextView txtAmountPaid;
    @BindView(R.id.txt_purchase_date)
    AnyTextView txtPurchaseDate;
    @BindView(R.id.txt_event_date)
    AnyTextView txtEventDate;
    Unbinder unbinder;

    private static String purchaseItemData = "purchaseItemData";
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.iv_barcode)
    ImageView ivBarcode;
    private PurchaseEnt purchaseEnt;
    private ImageLoader imageloader;
    Float updatedRemainingAmount;

    public static EventTicket newInstance() {
        return new EventTicket();
    }

    public static EventTicket newInstance(PurchaseEnt purchaseEnt) {
        Bundle args = new Bundle();
        args.putString(purchaseItemData, new Gson().toJson(purchaseEnt));
        EventTicket fragment = new EventTicket();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        purchaseItemData = getArguments().getString(purchaseItemData);
        if (purchaseItemData != null) {
            purchaseEnt = new Gson().fromJson(purchaseItemData, PurchaseEnt.class);
        }
        imageloader = ImageLoader.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_ticket, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setData();
    }

    private void setData() {

        txtTicketId.setText("#" + purchaseEnt.getEventDetail().getId() + " ");
        txtEvent.setText(purchaseEnt.getEventDetail().getEventName() + "");
        //txtAmountPaid.setText("$"+purchaseEnt.getEventDetail().getAmount() + "");

        updatedRemainingAmount = Float.valueOf(purchaseEnt.getEventDetail().getAmount()) * prefHelper.getConvertedAmount();
        String formattedValue = String.format("%.2f", updatedRemainingAmount);
        txtAmountPaid.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValue + "");

        txtPurchaseDate.setText(DateHelper.dateFormat(purchaseEnt.getPurchaseDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");
        txtEventDate.setText(DateHelper.dateFormat(purchaseEnt.getEventDetail().getStartDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");
        imageloader.displayImage(purchaseEnt.getEventDetail().getEventImage(), ivHeader);
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
}
