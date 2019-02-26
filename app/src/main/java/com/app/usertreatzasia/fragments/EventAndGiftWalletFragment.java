package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.usertreatzasia.activities.DockActivity.KEY_FRAG_FIRST;


public class EventAndGiftWalletFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.txt_events)
    AnyTextView txtEvents;
    @BindView(R.id.txt_Gifts)
    AnyTextView txtGifts;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;

    public static EventAndGiftWalletFragment newInstance() {
        return new EventAndGiftWalletFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_purchased_gift_wallet, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReplaceListViewFragment(EventsWalletFragment.newInstance());
        setListners();
        getMainActivity().refreshSideMenu();
    }

    private void setListners() {

        txtEvents.setOnClickListener(this);
        txtGifts.setOnClickListener(this);
    }

    private void ReplaceListViewFragment(BaseFragment frag) {

        FragmentTransaction transaction = getChildFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.mainFrame, frag);
        transaction
                .addToBackStack(
                        getChildFragmentManager().getBackStackEntryCount() == 0 ? KEY_FRAG_FIRST
                                : null).commit();
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
        titleBar.setSubHeading(getString(R.string.events));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_events:
                txtEvents.setTextColor(getResources().getColor(R.color.text_color_gold));
                txtGifts.setTextColor(getResources().getColor(R.color.white));
                ReplaceListViewFragment(EventsWalletFragment.newInstance());
                break;

            case R.id.txt_Gifts:
                txtEvents.setTextColor(getResources().getColor(R.color.white));
                txtGifts.setTextColor(getResources().getColor(R.color.text_color_gold));
                ReplaceListViewFragment(GiftEventRecievedFragment.newInstance());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
