package com.app.usertreatzasia.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.GiftReceivedEvent;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.GiftRecievedEventBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GiftEventRecievedFragment extends BaseFragment {


    @BindView(R.id.txt_homeHeading)
    AnyTextView txtHomeHeading;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_home)
    ListView lvHome;
    Unbinder unbinder;
    @BindView(R.id.txt_events)
    AnyTextView txtEvents;
    @BindView(R.id.txt_Gifts)
    AnyTextView txtGifts;

    private ArrayListAdapter<GiftReceivedEvent> adapter;
    private ArrayList<GiftReceivedEvent> walletGiftEnt;
    private ArrayList<GiftReceivedEvent> userCollection;

    public static GiftEventRecievedFragment newInstance() {
        return new GiftEventRecievedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new GiftRecievedEventBinder(getDockActivity(), prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift_event_recieved, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getGiftRecievedData();
    }

    private void getGiftRecievedData() {

        serviceHelper.enqueueCall(webService.getWalletGiftEvent(prefHelper.getSignUpUser().getToken()), WebServiceConstants.GIFT_RECIEVED_EVENT);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.GIFT_RECIEVED_EVENT:
                walletGiftEnt = (ArrayList<GiftReceivedEvent>) result;
                setGiftListData(walletGiftEnt);
                break;
        }
    }

    private void setGiftListData(ArrayList<GiftReceivedEvent> result) {
        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        txtHomeHeading.setText("GIFTS RECIEVED " + userCollection.size());
        txtHomeHeading.setTypeface(Typeface.DEFAULT_BOLD);
        bindData(userCollection);
    }

    private void bindData(ArrayList<GiftReceivedEvent> userCollection) {

        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvHome.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvHome.setVisibility(View.VISIBLE);
        }
        adapter.clearList();
        lvHome.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButtonAsPerRequirement(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(WalletFragment.newInstance(), "WalletFragment");
            }
        });
        titleBar.setSubHeading("Gifts");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.txt_events, R.id.txt_Gifts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_events:
                getDockActivity().replaceDockableFragment(EventsWalletFragment.newInstance(), "EventsWalletFragment");
                break;
            case R.id.txt_Gifts:
                //getDockActivity().replaceDockableFragment(GiftEventRecievedFragment.newInstance(), "GiftEventRecievedFragment");
        }
    }
}
