package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.interfaces.SideMenuItemClickListener;
import com.app.usertreatzasia.ui.viewbinders.abstracts.SidemenuBinder;
import com.app.usertreatzasia.ui.views.CustomRecyclerView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SideMenuFragment extends BaseFragment implements SideMenuItemClickListener {

    @BindView(R.id.recycler_side_nav)
    CustomRecyclerView recyclerSideNav;
    SidemenuBinder sidemenuBinder;
    private ArrayList<String> menuCollections;

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindData();
    }

    private void bindData() {
        menuCollections = new ArrayList<>();
        menuCollections.add(getResources().getString(R.string.home));
        menuCollections.add(getResources().getString(R.string.my_profile));
        menuCollections.add(getResources().getString(R.string.wallet));
        menuCollections.add(getResources().getString(R.string.history));
        menuCollections.add(getResources().getString(R.string.notifications));
        menuCollections.add(getResources().getString(R.string.refer_restaurant));
        menuCollections.add(getResources().getString(R.string.language));
        menuCollections.add(getResources().getString(R.string.about_app));
        menuCollections.add(getResources().getString(R.string.settings));
        sidemenuBinder = new SidemenuBinder(this);
        recyclerSideNav.BindRecyclerView(sidemenuBinder,
                menuCollections, new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false), new DefaultItemAnimator());
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    void closeMenu(){
        getMainActivity().closemenu();
    }

    @Override
    public void onMenuItemClicked(int currentPosition, int previousPosition, String title) {
        recyclerSideNav.notifyItemChanged(previousPosition);
        recyclerSideNav.notifyItemChanged(currentPosition);
        if (title.equals(getResources().getString(R.string.home))) {
            closeMenu();
            getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(),FlashSaleFragment.class.getName());

        } else if (title.equals(getResources().getString(R.string.my_profile))) {
            closeMenu();
            getDockActivity().replaceDockableFragment(ProfileFragment.newInstance(),ProfileFragment.class.getName());

        } else if (title.equals(getResources().getString(R.string.wallet))) {
            closeMenu();
            getDockActivity().replaceDockableFragment(WalletFragment.newInstance(),WalletFragment.class.getName());

        } else if (title.equals(getResources().getString(R.string.history))) {
            closeMenu();
            getDockActivity().replaceDockableFragment(MyHistoryFragment.newInstance(),MyHistoryFragment.class.getName());

        } else if (title.equals(getResources().getString(R.string.notifications))) {
            closeMenu();
            getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(),NotificationsFragment.class.getName());

        } else if (title.equals(getResources().getString(R.string.refer_restaurant))) {
            closeMenu();
            getDockActivity().replaceDockableFragment(ReferResturantFragment.newInstance(),ReferResturantFragment.class.getName());

        } else if (title.equals(getResources().getString(R.string.language))) {
            closeMenu();
            getDockActivity().replaceDockableFragment(LanguageFragment.newInstance(),LanguageFragment.class.getName());

        } else if (title.equals(getResources().getString(R.string.about_app))) {
            closeMenu();
            getDockActivity().replaceDockableFragment(AboutAppFragment.newInstance(),AboutAppFragment.class.getName());

        } else if (title.equals(getResources().getString(R.string.settings))) {
            closeMenu();
            getDockActivity().replaceDockableFragment(SettingsFragment.newInstance(),SettingsFragment.class.getName());
        }
    }
}
