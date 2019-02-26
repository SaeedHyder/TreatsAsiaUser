package com.app.usertreatzasia.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.PointHistoryEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.PointHistoryBinder;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PointHistoryFragment extends BaseFragment {


    @BindView(R.id.lv_point_history)
    ListView lvPointHistory;
    Unbinder unbinder;
    //private ArrayList<CreditPointHistoryEnt> pointHistoryCollection = new ArrayList<>();

    private ArrayListAdapter<PointHistoryEnt> adapter;
    private ArrayList<PointHistoryEnt> pointHistroryResult;
    private ArrayList<PointHistoryEnt> userCollection;

    public static PointHistoryFragment newInstance() {
        return new PointHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new PointHistoryBinder(getDockActivity(), prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point_history, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getPointHistoryList();
    }

    private void getPointHistoryList() {

            serviceHelper.enqueueCall(webService.getPointHistory(prefHelper.getSignUpUser().getToken()), WebServiceConstants.POINTS_HISTORY);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.POINTS_HISTORY:
                pointHistroryResult = (ArrayList<PointHistoryEnt>) result;
                setPointHistoryData(pointHistroryResult);
                break;
        }
    }

    private void setPointHistoryData(ArrayList<PointHistoryEnt> result) {

        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<PointHistoryEnt> userCollection) {

        adapter.clearList();
        lvPointHistory.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.point_history));
/*        titleBar.showFilterButtonCorner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showLongToastInCenter(getDockActivity(), getString(R.string.filter_toast));
                //getDockActivity().replaceDockableFragment(PointFilterFragment.newInstance(), "PointFilterFragment");
            }
        });*/
    }
}
