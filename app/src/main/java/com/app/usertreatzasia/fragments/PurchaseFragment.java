package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.EvoucherEnt;
import com.app.usertreatzasia.entities.PurchaseEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.PurchaseItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PurchaseFragment extends BaseFragment {

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_purchase)
    ListView lvPurchase;
    private ArrayListAdapter<PurchaseEnt> adapter;
    private ArrayList<PurchaseEnt> userCollection;
    private ArrayList<PurchaseEnt> purchaseDataResult;

    public static PurchaseFragment newInstance() {
        return new PurchaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new PurchaseItemBinder());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
        getPurchaseListData();
    }

    private void getPurchaseListData() {
        serviceHelper.enqueueCall(webService.purchaseHistory(prefHelper.getSignUpUser().getToken()), WebServiceConstants.PURCHASE_HISTORY);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.PURCHASE_HISTORY:
                purchaseDataResult = (ArrayList<PurchaseEnt>) result;
                setPurchaseListData(purchaseDataResult);
                break;
        }
    }

    private void setPurchaseListData(ArrayList<PurchaseEnt> result) {
        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<PurchaseEnt> userCollection) {

        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvPurchase.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvPurchase.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvPurchase.setAdapter(adapter);
        adapter.addAll(this.userCollection);
        adapter.notifyDataSetChanged();
    }

    private void setListners() {

        lvPurchase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDockActivity().replaceDockableFragment(EventTicket.newInstance(userCollection.get(position)),EventTicket.class.getName());
            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }
}
