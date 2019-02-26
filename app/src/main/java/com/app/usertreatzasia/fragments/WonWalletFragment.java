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
import com.app.usertreatzasia.entities.RewardsWonEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.interfaces.RewardWalletSearchListener;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.RewardsWonWalletItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WonWalletFragment extends BaseFragment implements View.OnClickListener, RewardWalletSearchListener {

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_won)
    ListView lvWon;
    private TitleBar ParentTitleBar;

    private ArrayListAdapter<RewardsWonEnt> adapter;
    private ArrayList<RewardsWonEnt> userCollection;
    private ArrayList<RewardsWonEnt> rewardsWonDataResult;
    static boolean CheckerButton;

    public static WonWalletFragment newInstance(boolean checkerButton) {
        CheckerButton = checkerButton;
        return new WonWalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<RewardsWonEnt>(getDockActivity(), new RewardsWonWalletItemBinder(getDockActivity(), prefHelper, CheckerButton));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_won, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
        getRewardsWonData();
        RewardsWalletFragment.setWalletSearchListner(this);
    }

    public ArrayList<RewardsWonEnt> getSearchedArray(String keyword) {
        if (userCollection != null)
            if (userCollection.isEmpty()) {
                return new ArrayList<>();
            }

        ArrayList<RewardsWonEnt> arrayList = new ArrayList<>();
        if (userCollection != null)
            for (RewardsWonEnt item : userCollection) {
                String UserName = "";
                if (item != null) {
                    UserName = item.getRewardDetail().getTitle();
                }
                if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                    arrayList.add(item);
                }
            }
        return arrayList;
    }


    private void getRewardsWonData() {
        serviceHelper.enqueueCall(webService.rewardsWon(prefHelper.getSignUpUser().getToken()), WebServiceConstants.REWARDSWON);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.REWARDSWON:
                rewardsWonDataResult = (ArrayList<RewardsWonEnt>) result;
                setRewardsListData(rewardsWonDataResult);
                break;
        }
    }


    private void setRewardsListData(ArrayList<RewardsWonEnt> result) {
        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<RewardsWonEnt> userCollection) {
        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvWon.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvWon.setVisibility(View.VISIBLE);
        }
        adapter.clearList();
        lvWon.setAdapter(adapter);
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
    public void onClick(View v) {
        switch (v.getId()) {
        }
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
                getRewardsWonData();
                return false;
            }
        });
        titleBar.crossBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                getTitleBar().getEditTextViewSearch(R.id.edt_search).setText("");
                ParentTitleBar.hideSearchBar();
                getRewardsWonData();
            }
        });
    }
}
