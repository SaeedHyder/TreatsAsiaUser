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
import com.app.usertreatzasia.entities.RewardsEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.interfaces.RewardsSearchListener;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.RewardsItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RewardsInnerFragment extends BaseFragment implements View.OnClickListener, RewardsSearchListener {

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_rewards)
    ListView lvRewards;
    TitleBar ParentTitleBar;
    String textSearched;

    private ArrayList<RewardsEnt> rewardsDataResult;
    private ArrayListAdapter<RewardsEnt> adapter;
    private ArrayList<RewardsEnt> userCollection;

    public static RewardsInnerFragment newInstance() {
        return new RewardsInnerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<RewardsEnt>(getDockActivity(), new RewardsItemBinder(getDockActivity(), prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards_inner, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListners();
        getRewardsData();

        RewardsFragment.setSearchListner(this);
    }

    public ArrayList<RewardsEnt> getSearchedArray(String keyword) {
        if (userCollection != null)
            if (userCollection.isEmpty()) {
                return new ArrayList<>();
            }

        ArrayList<RewardsEnt> arrayList = new ArrayList<>();
        if (userCollection != null)
            for (RewardsEnt item : userCollection) {
                String UserName = "";
                if (item != null) {
                    UserName = item.getTitle();
                }
                if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                /*UserName.contains(keyword)*/
                    arrayList.add(item);
                }
            }
        return arrayList;
    }


    private void getRewardsData() {

        serviceHelper.enqueueCall(webService.rewards(prefHelper.getSignUpUser().getToken()), WebServiceConstants.REWARDS);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.REWARDS:
                rewardsDataResult = (ArrayList<RewardsEnt>) result;
                setRewardsListData(rewardsDataResult);
                break;
        }
    }

    private void setRewardsListData(ArrayList<RewardsEnt> result) {
        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<RewardsEnt> userCollection) {

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
}
