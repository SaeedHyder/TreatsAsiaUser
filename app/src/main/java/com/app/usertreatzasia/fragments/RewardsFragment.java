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
import com.app.usertreatzasia.interfaces.RewardsSearchListener;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.usertreatzasia.activities.DockActivity.KEY_FRAG_FIRST;


public class RewardsFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;
    @BindView(R.id.txt_evoucher)
    AnyTextView txtEvoucher;
    @BindView(R.id.ll_Evoucher)
    LinearLayout llEvoucher;

    @BindView(R.id.txt_events)
    AnyTextView txtEvents;
    @BindView(R.id.ll_events)
    LinearLayout llEvents;
    @BindView(R.id.txt_rewards)
    AnyTextView txtRewards;
    @BindView(R.id.ll_Rewards)
    LinearLayout llRewards;

    @BindView(R.id.txt_Reward)
    AnyTextView txtReward;
    @BindView(R.id.txt_Won)
    AnyTextView txtWon;
    @BindView(R.id.iv_eVoucher)
    ImageView ivEVoucher;

    @BindView(R.id.iv_events)
    ImageView ivEvents;
    @BindView(R.id.iv_rewards)
    ImageView ivRewards;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edt_search)
    AnyEditTextView edtSearch;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    @BindView(R.id.iv_flashSale)
    ImageView ivFlashSale;
    @BindView(R.id.txt_flashSale)
    AnyTextView txtFlashSale;
    @BindView(R.id.ll_flashSale)
    LinearLayout llFlashSale;

    private RewardsInnerFragment rewardsInnerFragment;
    private WonFragment wonFragment;

    private ImageLoader imageLoader;
    private static RewardsSearchListener rewardsSearchListener;
    private TitleBar Title;


    public static RewardsFragment newInstance() {
        return new RewardsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rewardsInnerFragment = RewardsInnerFragment.newInstance();
        wonFragment = WonFragment.newInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);
        imageLoader = ImageLoader.getInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ReplaceListViewFragment(rewardsInnerFragment);
        setListners();
        setBottomBarTextColor();
        getMainActivity().refreshSideMenu();
    }

    public static void setSearchListner(RewardsSearchListener rewardsSearchistener) {
        rewardsSearchListener = rewardsSearchistener;
    }

    private void setBottomBarTextColor() {

        txtRewards.setTextColor(getResources().getColor(R.color.text_color_gold_bottom));
        ivRewards.setImageResource(R.drawable.rewardsfilled);
        //  imageLoader.displayImage("drawable://" + R.drawable.rewardsfilled, ivRewards);
    }

    private void setListners() {
        llEvoucher.setOnClickListener(this);
        llEvents.setOnClickListener(this);
        llRewards.setOnClickListener(this);
        txtReward.setOnClickListener(this);
        txtWon.setOnClickListener(this);
        llFlashSale.setOnClickListener(this);
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
        titleBar.showMenuButton();
        if (prefHelper.getNotificationCount() > 0) {
            titleBar.showNotificationDot();
        } else {
            titleBar.hideNotificationDot();
        }
        titleBar.showSearchButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardsSearchListener!=null)
                rewardsSearchListener.searchClick(titleBar);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ll_Evoucher:
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getName());
                break;
            case R.id.ll_events:
                getDockActivity().replaceDockableFragment(EventsFragment.newInstance(), EventsFragment.class.getName());
                break;
            case R.id.ll_Rewards:
                getDockActivity().replaceDockableFragment(RewardsFragment.newInstance(), RewardsFragment.class.getName());
                break;
            case R.id.txt_Reward:
                txtReward.setTextColor(getResources().getColor(R.color.text_color_gold));
                txtWon.setTextColor(getResources().getColor(R.color.white));
                ReplaceListViewFragment(rewardsInnerFragment);
                break;
            case R.id.txt_Won:
                txtReward.setTextColor(getResources().getColor(R.color.white));
                txtWon.setTextColor(getResources().getColor(R.color.text_color_gold));
                ReplaceListViewFragment(wonFragment);
                break;
            case R.id.ll_flashSale:
                getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), FlashSaleFragment.class.getName());
                break;
        }
    }
}
