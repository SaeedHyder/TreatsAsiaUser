package com.app.usertreatzasia.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.SubscriptionEntity;
import com.app.usertreatzasia.entities.Subscriptionfeature;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.PagerItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Pager1 extends BaseFragment {

    @BindView(R.id.txtHeading)
    AnyTextView txtHeading;
    @BindView(R.id.txtSubHeading)
    AnyTextView txtSubHeading;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.txt_currency)
    AnyTextView txtCurrency;
    @BindView(R.id.txt_amount)
    AnyTextView txtAmount;
    @BindView(R.id.ll_amount)
    LinearLayout llAmount;
    int subscription_id;
    @BindView(R.id.lv_bullets)
    ListView lvBullets;
    Unbinder unbinder;
    Float updatedActualPrice;

    private ArrayList<Subscriptionfeature> userCollection;
    private ArrayList<SubscriptionEntity> subscriptionResult;

    private ArrayListAdapter<Subscriptionfeature> adapter;

    public SubscriptionEntity getEntity() {
        return Entity;
    }

    public void setEntity(SubscriptionEntity entity) {
        Entity = entity;
    }

    private SubscriptionEntity Entity;
    private ArrayList<SubscriptionEntity> entity;

    public static Pager1 newInstance() {
        return new Pager1();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<Subscriptionfeature>(getDockActivity(), new PagerItemBinder(getDockActivity()));
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pager_1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void scaleImage(float scaleX) {
        view.setScaleY(scaleX);
        view.invalidate();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] nameArrray = getEntity().getSubscriptionName().split(" ");
        String heading = nameArrray[0];
        String sub_heading = nameArrray[1];

        txtHeading.setText(heading);
        txtSubHeading.setText(sub_heading);


        //txtAmount.setText(getEntity().getPrice());

        updatedActualPrice = Float.valueOf(getEntity().getPrice()) * prefHelper.getConvertedAmount();
        String formattedValuePrice = String.format("%.2f", updatedActualPrice);
        txtAmount.setText(formattedValuePrice + "");
        txtCurrency.setText(prefHelper.getConvertedAmountCurrrency()+"");
        subscription_id = getEntity().getId();

        /*prefHelper.setSubscriptionId(subscription_id);*/

        setBulletsListData(Entity.getSubscriptionfeatures());
        //setBulletsListData(entity);
        txtAmount.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void setContent(SubscriptionEntity result) {
        if (result != null) {
            setEntity(result);
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void setBulletsListData(List<Subscriptionfeature> subscriptionResult) {

        userCollection = new ArrayList<>();
        userCollection.addAll(subscriptionResult);
        bindData(userCollection);
    }

    private void bindData(ArrayList<Subscriptionfeature> userCollection) {

        adapter.clearList();
        lvBullets.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }
}
