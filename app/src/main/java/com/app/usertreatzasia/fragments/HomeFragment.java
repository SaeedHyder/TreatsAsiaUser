package com.app.usertreatzasia.fragments;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.EvoucherEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.InternetHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.interfaces.FavoriteStatus;
import com.app.usertreatzasia.interfaces.IGetLocation;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.dialogs.DialogFactory;
import com.app.usertreatzasia.ui.viewbinders.abstracts.HomeItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class HomeFragment extends BaseFragment implements View.OnClickListener, FavoriteStatus, IGetLocation {


    @BindView(R.id.txt_homeHeading)
    AnyTextView txtHomeHeading;

    @BindView(R.id.lv_home)
    ListView lvHome;

    @BindView(R.id.ll_Evoucher)
    LinearLayout llEvoucher;

    @BindView(R.id.ll_events)
    LinearLayout llEvents;

    @BindView(R.id.ll_Rewards)
    LinearLayout llRewards;


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;

    @BindView(R.id.txt_evoucher)
    AnyTextView txtEvoucher;

    @BindView(R.id.txt_events)
    AnyTextView txtEvents;

    @BindView(R.id.txt_rewards)
    AnyTextView txtRewards;


    @BindView(R.id.iv_eVoucher)
    ImageView ivEVoucher;


    @BindView(R.id.iv_events)
    ImageView ivEvents;

    @BindView(R.id.iv_rewards)
    ImageView ivRewards;

    @BindView(R.id.ll_sticky)
    LinearLayout ll_sticky;

    @BindView(R.id.iv_flashSale)
    ImageView ivFlashSale;

    @BindView(R.id.txt_flashSale)
    AnyTextView txtFlashSale;
    @BindView(R.id.tv_address)
    AnyTextView tv_address;
    @BindView(R.id.ll_flashSale)
    LinearLayout llFlashSale;
    private LatLng location;
    private ArrayListAdapter<EvoucherEnt> adapter;
    private ArrayList<EvoucherEnt> userCollection;
    private ImageLoader imageLoader;
    private ArrayList<EvoucherEnt> evoucherResult;
    private static String FILTERIDS = "filterIDS";
    private static String TYPESELECT = "type_select";
    private static String MERCHANTID = "merchantId";
    private String filterIds = "";
    private String type_select = "";
    private String merchantIdFilter = "";
    private String merchantid = "";
    private String search = "";
    String searchText;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public static HomeFragment newInstance(String filterIDS/*, String type_select*/) {
        Bundle args = new Bundle();
        args.putString(FILTERIDS, filterIDS);
        //args.putString(TYPESELECT, type_select);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static HomeFragment newInstance(int merchantId) {
        Bundle args = new Bundle();
        args.putString(MERCHANTID, String.valueOf(merchantId));
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            filterIds = getArguments().getString(FILTERIDS);
            //type_select = getArguments().getString(TYPESELECT);
            merchantIdFilter = getArguments().getString(MERCHANTID);
        }
        adapter = new ArrayListAdapter<EvoucherEnt>(getDockActivity(), new HomeItemBinder(getDockActivity(), prefHelper, this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        prefHelper.setSelectedlanguage(AppConstants.ENGLISH);
        imageLoader = ImageLoader.getInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_address.setOnClickListener(this);
        location = new LatLng(0, 0);
        setHomeHeadingText();
        setBottomBarTextColor();
        getEvoucherData();
        scrollListner();
        getMainActivity().refreshSideMenu();
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

    public ArrayList<EvoucherEnt> getSearchedArray(String keyword) {
        if (userCollection.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<EvoucherEnt> arrayList = new ArrayList<>();

        for (EvoucherEnt item : userCollection) {
            String UserName = "";
            if (item.getTitle() != null) {
                UserName = item.getTitle();
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
                serviceHelper.enqueueCall(webService.eVoucherHome(prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVOUCHER);
            } else {
                serviceHelper.enqueueCall(webService.eVoucherSearchFilter(filterIds, type_select, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVOUCHER);
            }
        }
        if (merchantIdFilter != null) {
            if (!merchantIdFilter.equals("")) {
                serviceHelper.enqueueCall(webService.eVoucherSearchMerchant(merchantIdFilter, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVOUCHER);
            }
        }
        if (filterIds == null) {
            filterIds = "";
        }
        if (type_select == null) {
            type_select = "";
        }
        if (merchantIdFilter == null) {
            merchantIdFilter = "";
        }
        //prefHelper.getUser().getId(), prefHelper.getUser().getToken()
        //serviceHelper.enqueueCall(webService.eVoucherHome(prefHelper.getUser().getId(), prefHelper.getUser().getToken()), WebServiceConstants.EVOUCHER);
        //serviceHelper.enqueueCall(webService.eVoucherHome(prefHelper.getUser().getId(), filterIds,type_select,merchantid,search), WebServiceConstants.EVOUCHER);
    }

    private void setBottomBarTextColor() {
        txtEvoucher.setTextColor(getResources().getColor(R.color.text_color_gold_bottom));

        ivEVoucher.setImageResource(R.drawable.evoucherfilled);
        //imageLoader.displayImage("drawable://" + R.drawable.evoucherfilled,ivEVoucher);
    }

    private void setHomeHeadingText() {
        String sourceString = "<b> <font color='#D3B260'> GREAT DEALS </font> </b> OF THE DAY";
        txtHomeHeading.setText(Html.fromHtml(sourceString));
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.EVOUCHER:
                evoucherResult = (ArrayList<EvoucherEnt>) result;
                setHomeListData(evoucherResult);
                break;

            case WebServiceConstants.getHomeSearch:
                evoucherResult = (ArrayList<EvoucherEnt>) result;
                setHomeListData(evoucherResult);
                break;

/*            case WebServiceConstants.EVOUCHERLIKE:
                UIHelper.showShortToastInCenter(getDockActivity(), getString(R.string.liked));
                break;*/
        }
    }

    private void setHomeListData(ArrayList<EvoucherEnt> result) {

        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<EvoucherEnt> userCollection) {

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
        titleBar.showMenuButton();
        if (prefHelper.getNotificationCount()>0){
            titleBar.showNotificationDot();
        } else {
            titleBar.hideNotificationDot();
        }
/*        titleBar.showNotificationButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().addDockableFragment(NotificationsFragment.newInstance(), NotificationsFragment.class.getName());
            }
        }, prefHelper.getNotificationCount());*/
        titleBar.showFilterButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(FilterFragment.newInstance("ImComingFromHome"), FilterFragment.class.getName());
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
                    }
                }, new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        UtilsGlobal.HideKeyBoard(getDockActivity());
                        searchText = getTitleBar().getEditTextViewSearch(R.id.edt_search).getText().toString();
                        serviceHelper.enqueueCall(webService.eVoucherSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.getHomeSearch);
                        return false;
                    }
                });
            }
        });
        titleBar.showNewFilterButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(BrandFilterFragment.newInstance(true), BrandFilterFragment.class.getName());
            }
        });
        titleBar.setSubHeading(getString(R.string.e_voucher));

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
                serviceHelper.enqueueCall(webService.eVoucherSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.getHomeSearch);
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
        titleBar.showMapFilterButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_address.callOnClick();
            }
        });
    }

    @OnClick({R.id.ll_Evoucher, R.id.ll_flashSale, R.id.ll_events, R.id.ll_Rewards})
    public void onViewClicked(View v) {

        switch (v.getId()) {
            case R.id.ll_Evoucher:
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getName());
                break;
            case R.id.ll_flashSale:
                getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), FlashSaleFragment.class.getName());
                break;
            case R.id.ll_events:
                getDockActivity().replaceDockableFragment(EventsFragment.newInstance(), EventsFragment.class.getName());
                break;
            case R.id.ll_Rewards:
                getDockActivity().replaceDockableFragment(RewardsFragment.newInstance(), RewardsFragment.class.getName());
                break;
        }
    }

    @Override
    public void eVoucherLike(Integer entityId, boolean status) {
        serviceHelper.enqueueCall(webService.evoucherLike(entityId, status ? 1 : 0, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVOUCHERLIKE);
    }

    @Override
    public void eventsLike(Integer id, boolean isChecked) {
    }

    @Override
    public void flashSaleLike(Integer id, boolean isChecked) {
    }

    @Override
    public void onLocationSet(LatLng location, String formattedAddress) {
        UIHelper.hideSoftKeyboard(getDockActivity(), getView());
        this.location = location;
        serviceHelper.enqueueCall(webService.eVoucherSearchMap(String.valueOf(location.latitude), String.valueOf(location.longitude), prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVOUCHER);
    }

    @Override
    public void onClick(View v) {
       /* if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            MapControllerFragment mapControllerFragment = MapControllerFragment.newInstance();
            mapControllerFragment.setDelegate(this);
            DialogFactory.showMapControllerDialog(getDockActivity(), mapControllerFragment);
        }*/
        requestLocationPermission();
    }

    private void requestLocationPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                          /*  if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                                edtEmail.setCursorVisible(false);
                                MapControllerFragment mapControllerFragment = MapControllerFragment.newInstance();
                                mapControllerFragment.setDelegate(ProfileFragment.this);

                                DialogFactory.showMapControllerDialog(getDockActivity(), mapControllerFragment);
                            }*/
                            openLocationSelector();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestLocationPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestLocationPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant LocationEnt Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();


    }

    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Uri uri = Uri.fromParts("package", getDockActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void openLocationSelector() {

        try {
           /* Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getDockActivity());*/
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(getDockActivity()), PLACE_AUTOCOMPLETE_REQUEST_CODE);
            //this.startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(getDockActivity(), data);
                if (place != null) {
                    UIHelper.hideSoftKeyboard(getDockActivity(), getView());
                    this.location = place.getLatLng();
                    serviceHelper.enqueueCall(webService.eVoucherSearchMap(String.valueOf(location.latitude), String.valueOf(location.longitude), prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVOUCHER);
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getDockActivity(), data);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }
}

