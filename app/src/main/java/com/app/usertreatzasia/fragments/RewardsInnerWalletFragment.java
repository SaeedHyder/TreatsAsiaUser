package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.RewardsWalletEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.interfaces.RewardWalletSearchListener;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.RewardsWalletItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardsInnerWalletFragment extends BaseFragment implements View.OnClickListener, RewardWalletSearchListener {

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_rewards)
    ListView lvRewards;
    TitleBar ParentTitleBar;

    private ArrayList<RewardsWalletEntity> rewardsDataResult;
    private ArrayListAdapter<RewardsWalletEntity> adapter;
    private ArrayList<RewardsWalletEntity> userCollection;

    public static RewardsInnerWalletFragment newInstance() {
        return new RewardsInnerWalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<RewardsWalletEntity>(getDockActivity(), new RewardsWalletItemBinder(getDockActivity(), prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards_inner_wallet, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListners();
        getRewardsData();

        RewardsWalletFragment.setWalletSearchListner(this);
        //scrollListner();

    }

    public ArrayList<RewardsWalletEntity> getSearchedArray(String keyword) {
        if (userCollection != null)
            if (userCollection.isEmpty()) {
                return new ArrayList<>();
            }

        ArrayList<RewardsWalletEntity> arrayList = new ArrayList<>();

        if (userCollection!=null)
        for (RewardsWalletEntity item : userCollection) {
            String UserName = "";
            if (item.getRewardDetail() != null) {
                UserName = item.getRewardDetail().getTitle();
            }
            if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                /*UserName.contains(keyword)*/
                arrayList.add(item);
            }
        }
        return arrayList;

    }


    private void getRewardsData() {

        serviceHelper.enqueueCall(webService.rewardsWallet(prefHelper.getSignUpUser().getToken()), WebServiceConstants.REWARDS_WALLET);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.REWARDS_WALLET:
                rewardsDataResult = (ArrayList<RewardsWalletEntity>) result;
                setRewardsListData(rewardsDataResult);
                break;
        }
    }

    private void setRewardsListData(ArrayList<RewardsWalletEntity> result) {
        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<RewardsWalletEntity> userCollection) {

        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvRewards.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvRewards.setVisibility(View.VISIBLE);
        }
        adapter.clearList();
        lvRewards.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    private void setListners() {
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


    @Override
    public void searchClick(TitleBar titleBar) {
        ParentTitleBar = titleBar;
        titleBar.showSearchBar(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                bindData(getSearchedArray(s.toString()));
            }
        }, new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                getTitleBar().getEditTextViewSearch(R.id.edt_search).setText("");
                ParentTitleBar.hideSearchBar();
                getRewardsData();
                return false;
            }
        });
        titleBar.crossBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                getTitleBar().getEditTextViewSearch(R.id.edt_search).setText("");
                ParentTitleBar.hideSearchBar();
                getRewardsData();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
