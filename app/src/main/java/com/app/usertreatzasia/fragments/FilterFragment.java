package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.FilterEnt;
import com.app.usertreatzasia.entities.VochersTypes;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.interfaces.CheckBoxes;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.FilterItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FilterFragment extends BaseFragment implements View.OnClickListener, CheckBoxes {


    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.lv_filter)
    ListView lvFilter;
    ArrayList<FilterEnt> filterResult;
    ArrayList<FilterEnt> filterResult2;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
/*    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.cb_voucher)
    CheckBox cbVoucher;
    @BindView(R.id.cb_flash_sale)
    CheckBox cbFlashSale;
    @BindView(R.id.cb_promo)
    CheckBox cbPromo;*/

    private ArrayListAdapter<FilterEnt> adapter;
    private FilterItemBinder binder;
    private ArrayList<FilterEnt> userCollection;
    private static String ISHOMEFRAGMENT = "homeFragment";
    private static String ISFLASHSALEFRAGMENT = "flashSaleFragment";
    private static String ISHOMEWALLETFRAGMENT = "homeWalletFragment";
    private String isFlashSaleFragment;
    private String isHomeFragment;
    private String isHomeWalletFragment;
    StringBuilder sbVoucherType = new StringBuilder();

    VochersTypes vochersTypes = new VochersTypes();

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    public static FilterFragment newInstance(String isHomeFragment) {
        Bundle args = new Bundle();
        if (isHomeFragment.equalsIgnoreCase("ImComingFromFlashSale")) {
            args.putString(ISFLASHSALEFRAGMENT, isHomeFragment);
        } else if (isHomeFragment.equalsIgnoreCase("ImComingFromHome")) {
            args.putString(ISHOMEFRAGMENT, isHomeFragment);
        } else if (isHomeFragment.equalsIgnoreCase("ImComingFromHomeWallet")) {
            args.putString(ISHOMEWALLETFRAGMENT, isHomeFragment);
        }
        //args.putString(ISHOMEFRAGMENT, isHomeFragment);
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isHomeFragment = getArguments().getString(ISHOMEFRAGMENT);
            isFlashSaleFragment = getArguments().getString(ISFLASHSALEFRAGMENT);
            isHomeWalletFragment = getArguments().getString(ISHOMEWALLETFRAGMENT);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        ButterKnife.bind(this, view);
        vochersTypes = prefHelper.getVochersTypes();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
        getFilterData();

/*        if (vochersTypes != null) {

            if (vochersTypes.isEvoucher() && vochersTypes.isFlashSale() && vochersTypes.isPromoCode()) {
                cbAll.setChecked(true);
            } else {
                cbAll.setChecked(false);
            }

            cbVoucher.setChecked(vochersTypes.isEvoucher());
            cbFlashSale.setChecked(vochersTypes.isFlashSale());
            cbPromo.setChecked(vochersTypes.isPromoCode());

        } else {
            vochersTypes = new VochersTypes();
            prefHelper.putVochersTypes(vochersTypes);
        }

        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    vochersTypes.setEvoucher(true);
                    vochersTypes.setFlashSale(true);
                    vochersTypes.setPromoCode(true);
                    cbVoucher.setChecked(true);
                    cbFlashSale.setChecked(true);
                    cbPromo.setChecked(true);

                } else {
                    vochersTypes.setEvoucher(false);
                    vochersTypes.setFlashSale(false);
                    vochersTypes.setPromoCode(false);
                    cbVoucher.setChecked(false);
                    cbFlashSale.setChecked(false);
                    cbPromo.setChecked(false);
                }
            }
        });

        cbVoucher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    vochersTypes.setEvoucher(true);
                } else {
                    vochersTypes.setEvoucher(false);
                }
            }
        });

        cbFlashSale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    vochersTypes.setFlashSale(true);
                } else {
                    vochersTypes.setFlashSale(false);
                }
            }
        });

        cbPromo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vochersTypes.setPromoCode(true);
                } else {
                    vochersTypes.setPromoCode(false);
                }
            }
        });*/
    }

    private void getFilterData() {
        serviceHelper.enqueueCall(webService.filterData(prefHelper.getSignUpUser().getToken()), WebServiceConstants.FILTER);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.FILTER:
                filterResult = (ArrayList<FilterEnt>) result;
                filterResult2 = new ArrayList<>();

                for (int i = 0; i < filterResult.size(); i++) {
                    if (!filterResult.get(i).getTitle().equalsIgnoreCase("")) {
                        filterResult2.add(filterResult.get(i));
                    }
                }
                setFilterListData(filterResult2);
                break;
        }
    }

    private void setFilterListData(ArrayList<FilterEnt> result) {
        userCollection = new ArrayList<>();
        if (result.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvFilter.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvFilter.setVisibility(View.VISIBLE);

        }
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<FilterEnt> userCollection) {
        binder = new FilterItemBinder(getDockActivity(), this, prefHelper);
        adapter = new ArrayListAdapter<FilterEnt>(getDockActivity(), userCollection, binder);
        //adapter.clearList();
        lvFilter.setAdapter(adapter);
        //adapter.addAll(userCollection);
        /*adapter.notifyDataSetChanged();*/
    }

    private void setListners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.filter));
        titleBar.showBackButton();

        titleBar.showClearText(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (vochersTypes != null) {
/*                    vochersTypes.setEvoucher(false);
                    vochersTypes.setFlashSale(false);
                    vochersTypes.setPromoCode(false);
                    cbAll.setChecked(false);
                    cbVoucher.setChecked(false);
                    cbFlashSale.setChecked(false);
                    cbPromo.setChecked(false);
                    prefHelper.putVochersTypes(vochersTypes);*/
                }

                if (binder != null && adapter != null) {
                    binder.setClearFromFragment(true, userCollection.size() - 1);
                    adapter.notifyDataSetChanged();
                    return;
                }
            }
        });
    }

    String filterIDS = "";
    String type_select = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_submit:
                StringBuilder stringBuilder = new StringBuilder();
                if (filterResult != null) {
                    for (FilterEnt ent : filterResult) {
                        if (ent.ischecked()) {
                            stringBuilder.append(ent.getId());
                            stringBuilder.append(",");
                        }
                    }
                }
                filterIDS = stringBuilder.toString();
                if (filterIDS.length() > 0)
                    filterIDS = filterIDS.substring(0, filterIDS.length() - 1);

/*                stringBuilder = new StringBuilder();
                if (vochersTypes.isEvoucher()) {
                    stringBuilder.append(AppConstants.evoucher);
                    stringBuilder.append(",");
                }
                if (vochersTypes.isFlashSale()) {
                    stringBuilder.append(AppConstants.flashsale);
                    stringBuilder.append(",");
                }
                if (vochersTypes.isPromoCode()) {
                    stringBuilder.append(AppConstants.promo_code);
                    stringBuilder.append(",");
                }*/

/*                type_select = stringBuilder.toString();
                if (type_select.length() > 0)
                    type_select = type_select.substring(0, type_select.length() - 1);

                prefHelper.putVochersTypes(vochersTypes);*/

                if (isHomeFragment != null) {
                    if (isHomeFragment.equalsIgnoreCase("ImComingFromHome")) {
                        getDockActivity().replaceDockableFragment(HomeFragment.newInstance(filterIDS), "HomeFragment");
                    }
                } else if (isFlashSaleFragment != null) {
                    if (isFlashSaleFragment.equalsIgnoreCase("ImComingFromFlashSale")) {
                        getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(filterIDS), "FlashSaleFragment");
                    }
                } else if (isHomeWalletFragment != null) {
                    if (isHomeWalletFragment.equalsIgnoreCase("ImComingFromHomeWallet")) {
                        getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(filterIDS), "HomeWalletFragment");
                    }
                } else {
                    UIHelper.showShortToastInCenter(getDockActivity(), "Sorry for inconvenience. Issue is being resolved");
                }
                break;
        }
    }

    @Override
    public void NotifyDataSet() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
