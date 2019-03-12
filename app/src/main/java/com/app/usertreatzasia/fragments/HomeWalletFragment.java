package com.app.usertreatzasia.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.WalletEvoucherEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DialogHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.interfaces.FavoriteStatus;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.HomeWalletItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeWalletFragment extends BaseFragment implements FavoriteStatus {


    @BindView(R.id.txt_homeHeading)
    AnyTextView txtHomeHeading;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_home)
    ListView lvHome;
    @BindView(R.id.txt_Vouchers)
    AnyTextView txtVouchers;
    @BindView(R.id.txt_Gifts)
    AnyTextView txtGifts;
    private ArrayListAdapter<WalletEvoucherEnt> adapter;
    private ArrayList<WalletEvoucherEnt> userCollection;
    private ImageLoader imageLoader;
    private ArrayList<WalletEvoucherEnt> evoucherResult;
    String searchText;
    private static String FILTERIDS = "filterIDS";
    private static String TYPESELECT = "type_select";
    private static String MERCHANTID = "merchantId";
    private String filterIds = "";
    private String type_select = "";
    private String merchantIdFilter = "";
    private static String GIFTID = "giftId";
    private String giftIdFilter = "";
    private static String GIFTNAME = "giftName";
    private String giftName = "";


    public static HomeWalletFragment newInstance(String filterIDS/*,String type_select*/) {
        Bundle args = new Bundle();
        args.putString(FILTERIDS, filterIDS);
        //args.putString(TYPESELECT, type_select);
        HomeWalletFragment fragment = new HomeWalletFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static HomeWalletFragment newInstance(int giftId, String giftName) {
        Bundle args = new Bundle();
        args.putString(GIFTID, String.valueOf(giftId));
        args.putString(GIFTNAME, String.valueOf(giftName));
        HomeWalletFragment fragment = new HomeWalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeWalletFragment newInstance() {
        return new HomeWalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            filterIds = getArguments().getString(FILTERIDS);
            giftIdFilter = getArguments().getString(GIFTID);
            giftName = getArguments().getString(GIFTNAME);
        }
        adapter = new ArrayListAdapter<WalletEvoucherEnt>(getDockActivity(), new HomeWalletItemBinder(getDockActivity(), prefHelper, this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet_home, container, false);
        prefHelper.setSelectedlanguage(AppConstants.ENGLISH);
        imageLoader = ImageLoader.getInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getEvoucherData();
        setHomeHeadingText();
        scrollListner();
        getDailog();
    }

    private void getDailog() {
        if (giftIdFilter != null) {
            if (!giftIdFilter.equals("")) {
                final DialogHelper cahoutDialog = new DialogHelper(getDockActivity());
                cahoutDialog.cashout(R.layout.cashout_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cahoutDialog.hideDialog();
                        giftDetails();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cahoutDialog.hideDialog();
                    }
                }, "Are you sure you want to send Gift to " + giftName + ".", "Gift");

                cahoutDialog.setCancelable(false);
                cahoutDialog.showDialog();
            }
        }
    }

    private void giftDetails() {

        if (giftIdFilter != null) {
            if (!giftIdFilter.equals("")) {
                serviceHelper.enqueueCall(webService.giftEvoucher(prefHelper.getEvoucherId(), AppConstants.GIFT, giftIdFilter, prefHelper.getSignUpUser().getToken()), WebServiceConstants.GIFTEVOUCHER);
            }
        }
        if (giftIdFilter == null) {
            giftIdFilter = "";
        }
    }

    private void scrollListner() {

        lvHome.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (getTitleBar().getEditTextViewSearch(R.id.edt_search).getText().toString().isEmpty()) {
                    getTitleBar().hideSearchBar();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int mPosition = 0;
                int mOffset = 0;
            }
        });
    }

    public ArrayList<WalletEvoucherEnt> getSearchedArray(String keyword) {
        if (userCollection.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<WalletEvoucherEnt> arrayList = new ArrayList<>();

        for (WalletEvoucherEnt item : userCollection) {
            String UserName = "";
            if (item != null) {
                //UserName = item.getMerchantDetail().getFirstName() + " " + item.getMerchantDetail().getLastName();
                UserName = item.getEvoucherDetail().getTitle();
            }
            if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                /*UserName.contains(keyword)*/
                arrayList.add(item);
            }
        }
        return arrayList;

    }

    private void getEvoucherData() {


        if (filterIds != null && type_select != null) {
            if (filterIds.equals("") && filterIds != null && type_select.equals("") && type_select != null) {
                serviceHelper.enqueueCall(webService.getWalletEvoucher(prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVOUCHER_WALLET);
            } else {
                serviceHelper.enqueueCall(webService.getWalletEvoucherFilter(filterIds, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVOUCHER_WALLET);
            }
        }

        if (filterIds == null) {
            filterIds = "";
        }
        if (type_select == null) {
            type_select = "";
        }

    }

    private void setHomeHeadingText() {
/*        String sourceString = "<b> <font color='#D3B260'> GREAT DEALS </font> </b> OF THE DAY";
        txtHomeHeading.setText(Html.fromHtml(sourceString));*/
        //txtHomeHeading.setText("VOUCHERS AVAILABLE " + userCollection.size());
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.EVOUCHER_WALLET:
                evoucherResult = (ArrayList<WalletEvoucherEnt>) result;
                setHomeListData(evoucherResult);
                break;

            case WebServiceConstants.getWalletHomeSearch:
                evoucherResult = (ArrayList<WalletEvoucherEnt>) result;
                setHomeListData(evoucherResult);
                break;

            case WebServiceConstants.GIFTEVOUCHER:
                UIHelper.showShortToastInCenter(getDockActivity(), "Gift has been sent successfully.");
                getDockActivity().replaceDockableFragment(WalletFragment.newInstance(), "WalletFragment");
                break;
        }
    }

    private void setHomeListData(ArrayList<WalletEvoucherEnt> result) {

        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        txtHomeHeading.setText("AVAILABLE VOUCHERS " + userCollection.size());
        txtHomeHeading.setTypeface(Typeface.DEFAULT_BOLD);
        bindData(userCollection);
    }

    private void bindData(ArrayList<WalletEvoucherEnt> userCollection) {

        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvHome.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvHome.setVisibility(View.VISIBLE);
        }
        adapter.clearList();
        lvHome.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setTitleBar(final TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButtonAsPerRequirement(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(WalletFragment.newInstance(), "WalletFragment");
            }
        });

        titleBar.showFilterButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(FilterFragment.newInstance("ImComingFromHomeWallet"), FilterFragment.class.getName());
            }
        });
        titleBar.showSearchButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleBar.showSearchBar(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //  bindData(getSearchedArray(s.toString()));
                    }
                }, new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        UtilsGlobal.HideKeyBoard(getDockActivity());
                        searchText = getTitleBar().getEditTextViewSearch(R.id.edt_search).getText().toString();
                        serviceHelper.enqueueCall(webService.getWalletEvoucherSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.getWalletHomeSearch);
                        return false;
                    }
                });
            }
        });
        titleBar.setSubHeading("Vouchers");
        titleBar.crossBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                getTitleBar().getEditTextViewSearch(R.id.edt_search).setText("");
                getEvoucherData();
            }
        });
        titleBar.searchClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                searchText = getTitleBar().getEditTextViewSearch(R.id.edt_search).getText().toString();
                serviceHelper.enqueueCall(webService.getWalletEvoucherSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.getWalletHomeSearch);
            }
        });
        titleBar.crossBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                getTitleBar().getEditTextViewSearch(R.id.edt_search).setText("");
                getEvoucherData();
            }
        });
    }

    @Override
    public void eVoucherLike(Integer entityId, boolean status) {
    }

    @Override
    public void eventsLike(Integer id, boolean isChecked) {
    }

    @Override
    public void flashSaleLike(Integer id, boolean isChecked) {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.txt_Vouchers, R.id.txt_Gifts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_Vouchers:
                //getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(), "HomeWalletFragment");
                break;
            case R.id.txt_Gifts:
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(GiftRecievedFragment.newInstance(), "GiftRecievedFragment");
                break;
        }
    }
}
