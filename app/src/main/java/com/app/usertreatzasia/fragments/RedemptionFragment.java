package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.PurchaseEnt;
import com.app.usertreatzasia.entities.RedemptionEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.RedemptionItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RedemptionFragment extends BaseFragment {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_redemtionHistory)
    ListView lvRedemtionHistory;
    private ArrayListAdapter<RedemptionEnt> adapter;
    private ArrayList<RedemptionEnt> userCollection;
    private ArrayList<RedemptionEnt> redemptionDataResult;

    public static RedemptionFragment newInstance() {
        return new RedemptionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<RedemptionEnt>(getDockActivity(), new RedemptionItemBinder(getDockActivity(),prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_redemption_history, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getRedemptionListData();

/*        lvRedemtionHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().replaceDockableFragment(RemainingAmountFragment.newInstance(),RemainingAmountFragment.class.getName());
            }
        });*/
    }

    private void getRedemptionListData() {
        serviceHelper.enqueueCall(webService.redemptionHistory(prefHelper.getSignUpUser().getToken()), WebServiceConstants.REDEMPTIONHISTORY);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.REDEMPTIONHISTORY:
                redemptionDataResult = (ArrayList<RedemptionEnt>) result;
                setRedemptionListData(redemptionDataResult);
                break;
        }
    }


    private void setRedemptionListData(ArrayList<RedemptionEnt> result) {

        userCollection = new ArrayList<>();
        if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvRedemtionHistory.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvRedemtionHistory.setVisibility(View.VISIBLE);

        }
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<RedemptionEnt> userCollection) {

        adapter.clearList();
        lvRedemtionHistory.setAdapter(adapter);
        adapter.addAll(this.userCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();

    }
}
