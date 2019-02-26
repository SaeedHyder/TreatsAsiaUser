package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.interfaces.RewardWalletSearchListener;
import com.app.usertreatzasia.interfaces.RewardsSearchListener;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.usertreatzasia.activities.DockActivity.KEY_FRAG_FIRST;

public class RewardsWalletFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edt_search)
    AnyEditTextView edtSearch;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.txt_Reward)
    AnyTextView txtReward;
    @BindView(R.id.txt_Won)
    AnyTextView txtWon;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;
    private RewardsInnerWalletFragment rewardsInnerWalletFragment;
    private WonWalletFragment wonWalletFragment;

    private ImageLoader imageLoader;
    private static RewardWalletSearchListener rewardsWalletSearchListener;
    private TitleBar Title;


    public static RewardsWalletFragment newInstance() {
        return new RewardsWalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rewardsInnerWalletFragment = RewardsInnerWalletFragment.newInstance();
        wonWalletFragment = WonWalletFragment.newInstance(false);
    }

    public static void setWalletSearchListner(RewardWalletSearchListener rewardsSearchistener) {
        rewardsWalletSearchListener = rewardsSearchistener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards_wallet, container, false);
        imageLoader = ImageLoader.getInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReplaceListViewFragment(rewardsInnerWalletFragment);
        setListners();
    }


    private void setListners() {
        txtReward.setOnClickListener(this);
        txtWon.setOnClickListener(this);
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
    public void setTitleBar(final TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        this.Title = titleBar;

        titleBar.setSubHeading(getString(R.string.Rewards));
        titleBar.showBackButtonAsPerRequirement(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(WalletFragment.newInstance(), "WalletFragment");
            }
        });
        titleBar.showSearchButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardsWalletSearchListener!=null)
                    rewardsWalletSearchListener.searchClick(titleBar);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txt_Reward:
                txtReward.setTextColor(getResources().getColor(R.color.text_color_gold));
                txtWon.setTextColor(getResources().getColor(R.color.white));
                ReplaceListViewFragment(rewardsInnerWalletFragment);
                break;
            case R.id.txt_Won:
                txtReward.setTextColor(getResources().getColor(R.color.white));
                txtWon.setTextColor(getResources().getColor(R.color.text_color_gold));
                ReplaceListViewFragment(wonWalletFragment);
                break;
        }
    }

}
