package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.PurchaseEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.usertreatzasia.activities.DockActivity.KEY_FRAG_FIRST;



public class MyHistoryFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.txt_Redemption)
    AnyTextView txtRedemption;
    @BindView(R.id.txt_Purchase)
    AnyTextView txtPurchase;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;

    public static MyHistoryFragment newInstance() {
        return new MyHistoryFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myhistory, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReplaceListViewFragment(RedemptionFragment.newInstance());
        setListners();
        getMainActivity().refreshSideMenu();
    }

    private void setListners() {

        txtPurchase.setOnClickListener(this);
        txtRedemption.setOnClickListener(this);
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
        titleBar.setSubHeading(getString(R.string.myHistory));
        titleBar.showMenuButton();
        if (prefHelper.getNotificationCount()>0){
            titleBar.showNotificationDot();
        } else {
            titleBar.hideNotificationDot();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_Redemption:
                txtRedemption.setTextColor(getResources().getColor(R.color.text_color_gold));
                txtPurchase.setTextColor(getResources().getColor(R.color.white));
                ReplaceListViewFragment(RedemptionFragment.newInstance());
                break;
            case R.id.txt_Purchase:
                txtRedemption.setTextColor(getResources().getColor(R.color.white));
                txtPurchase.setTextColor(getResources().getColor(R.color.text_color_gold));
                ReplaceListViewFragment(PurchaseFragment.newInstance());
                break;
        }
    }
}
