package com.app.usertreatzasia.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.CreditHistoryEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.CreditHistoryBinder;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreditHistoryFragment extends BaseFragment {


    @BindView(R.id.lv_credit_history)
    ListView lvCreditHistory;
    Unbinder unbinder;

    private ArrayListAdapter<CreditHistoryEnt> adapter;
    private ArrayList<CreditHistoryEnt> creditHistroryResult;
    private ArrayList<CreditHistoryEnt> userCollection;

    public static CreditHistoryFragment newInstance() {
        return new CreditHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new CreditHistoryBinder(getDockActivity(), prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credit_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCreditHistoryList();
    }

    private void getCreditHistoryList() {

        serviceHelper.enqueueCall(webService.getCreditHistory(prefHelper.getSignUpUser().getToken()), WebServiceConstants.CREDITS_HISTORY);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.CREDITS_HISTORY:
                creditHistroryResult = (ArrayList<CreditHistoryEnt>) result;
                setPointHistoryData(creditHistroryResult);
                break;
        }
    }

    private void setPointHistoryData(ArrayList<CreditHistoryEnt> result) {

        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<CreditHistoryEnt> userCollection) {

        adapter.clearList();
        lvCreditHistory.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.credit_history));


/*        titleBar.showFilterButtonCorner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.filter_toast));
                //getDockActivity().replaceDockableFragment(PointFilterFragment.newInstance(), "PointFilterFragment");
            }
        });*/
    }
}
