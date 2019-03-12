package com.app.usertreatzasia.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.Editable;
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
import com.app.usertreatzasia.entities.AddCmsEntity;
import com.app.usertreatzasia.entities.AdvertisementEntity;
import com.app.usertreatzasia.entities.CurrencyRateEntity;
import com.app.usertreatzasia.entities.FlashSaleEnt;
import com.app.usertreatzasia.entities.ResponseWrapper;
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
import com.app.usertreatzasia.ui.viewbinders.abstracts.FlashSaleItemBinder;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class FlashSaleFragment extends BaseFragment implements View.OnClickListener, FavoriteStatus, IGetLocation {

    @BindView(R.id.txt_homeHeading)
    AnyTextView txtHomeHeading;
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_flashSale)
    ListView lvFlashSale;
    @BindView(R.id.ll_Evoucher)
    LinearLayout llEvoucher;
    @BindView(R.id.ll_flashSale)
    LinearLayout llFlashSale;
    @BindView(R.id.ll_events)
    LinearLayout llEvents;
    @BindView(R.id.ll_Rewards)
    LinearLayout llRewards;
    @BindView(R.id.txt_evoucher)
    AnyTextView txtEvoucher;
    @BindView(R.id.txt_flashSale)
    AnyTextView txtFlashSale;
    @BindView(R.id.txt_events)
    AnyTextView txtEvents;
    @BindView(R.id.txt_rewards)
    AnyTextView txtRewards;
    @BindView(R.id.iv_eVoucher)
    ImageView ivEVoucher;
    @BindView(R.id.iv_flashSale)
    ImageView ivFlashSale;
    @BindView(R.id.iv_events)
    ImageView ivEvents;
    @BindView(R.id.iv_rewards)
    ImageView ivRewards;
    @BindView(R.id.tv_address)
    AnyTextView tv_address;

    private ArrayListAdapter<FlashSaleEnt> adapter;
    private ArrayList<FlashSaleEnt> userCollection;
    private ArrayList<FlashSaleEnt> flashSaleResult;
    private static String FILTERIDS = "filterIDS";
    private String filterIds = "";
    private static String MERCHANTID = "merchantId";
    private String merchantIdFilter = "";
    String searchText;
    private LatLng location;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private ImageLoader imageLoader;

    public static FlashSaleFragment newInstance() {
        return new FlashSaleFragment();
    }

    public static FlashSaleFragment newInstance(String filterIDS) {
        Bundle args = new Bundle();
        args.putString(FILTERIDS, filterIDS);
        FlashSaleFragment fragment = new FlashSaleFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static FlashSaleFragment newInstance(int merchantId) {
        Bundle args = new Bundle();
        args.putString(MERCHANTID, String.valueOf(merchantId));
        FlashSaleFragment fragment = new FlashSaleFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            filterIds = getArguments().getString(FILTERIDS);
            merchantIdFilter = getArguments().getString(MERCHANTID);
        }
        adapter = new ArrayListAdapter<>(getDockActivity(), new FlashSaleItemBinder(getDockActivity(), prefHelper, this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashsale, container, false);

        imageLoader = ImageLoader.getInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!prefHelper.isloadAdd()) {
            prefHelper.setLoadAdd(true);
            serviceHelper.enqueueCall(webService.getAdCms(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getAdCms);
            if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
                getDockActivity().emptyBackStack();
                getDockActivity().replaceDockableFragment(AdvertisementFragment.newInstance(), "AdvertisementFragment");
            }
        }
        tv_address.setOnClickListener(this);
        location = new LatLng(0, 0);
        getCurrencyAndLanguage();
        getMainActivity().refreshSideMenu();
        getFlashSaleData();
        setBottomBarTextColor();
        scrollListner();
        getNotificationCount();
        //getCurrencyAndLanguage();
    }

    private void getCurrencyAndLanguage() {

        if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase(AppConstants.SGD)) {
            getCurrencyRateSGD();
        } else if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase(AppConstants.IDR)) {
            getCurrencyRateIDR();
        } else if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase(AppConstants.MYR)) {
            getCurrencyRateMYR();
        } else if (prefHelper.getConvertedAmountCurrrency().equalsIgnoreCase("")) {
            getCurrencyRateSGD();
        }
    }

    private void getNotificationCount() {
        serviceHelper.enqueueCall(webService.getNotificationCount(prefHelper.getSignUpUser().getToken()),
                WebServiceConstants.getNotificationCount);
    }

    private void scrollListner() {

        lvFlashSale.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    public ArrayList<FlashSaleEnt> getSearchedArray(String keyword) {
        if (userCollection.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<FlashSaleEnt> arrayList = new ArrayList<>();

        for (FlashSaleEnt item : userCollection) {
            String UserName = "";
            if (item.getMerchantDetail() != null) {
                UserName = item.getMerchantDetail().getFirstName() + " " + item.getMerchantDetail().getLastName();
            }
            if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                /*UserName.contains(keyword)*/
                arrayList.add(item);
            }
        }
        return arrayList;
    }

    private void getFlashSaleData() {
        if (filterIds != null) {
            if (filterIds.equals("")) {
                serviceHelper.enqueueCall(webService.flashSale(prefHelper.getSignUpUser().getToken()), WebServiceConstants.FLASHSALE);
            } else {
                serviceHelper.enqueueCall(webService.flashSaleFilter(filterIds, prefHelper.getSignUpUser().getToken()), WebServiceConstants.FLASHSALE);
            }
        }
        if (merchantIdFilter != null) {
            if (!merchantIdFilter.equals("")) {
                serviceHelper.enqueueCall(webService.flashSaleMerchant(merchantIdFilter, prefHelper.getSignUpUser().getToken()), WebServiceConstants.FLASHSALE);
            }
        }
        if (filterIds == null) {
            filterIds = "";
        }

        if (merchantIdFilter == null) {
            merchantIdFilter = "";
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.FLASHSALE:
                flashSaleResult = (ArrayList<FlashSaleEnt>) result;
                setFlashSaleListData(flashSaleResult);
                break;
            case WebServiceConstants.getNotificationCount:
                //prefHelper.setNotificationCount(((countEnt) result).getCount() );
                break;
            case WebServiceConstants.getAdCms:
                AddCmsEntity entity = (AddCmsEntity) result;
                AdvertisementEntity advertisementEntity = entity.getAdvertisement();
                prefHelper.putAdvertisementEntity(advertisementEntity);
                prefHelper.setRewardCms(entity.getReward());
                prefHelper.setContactUsCms(entity.getContactus());
                break;
        }
    }

    private void setFlashSaleListData(ArrayList<FlashSaleEnt> result) {

        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<FlashSaleEnt> userCollection) {

        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvFlashSale.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvFlashSale.setVisibility(View.VISIBLE);
        }

        adapter.clearList();
        lvFlashSale.setAdapter(adapter);
        adapter.addAll(userCollection);
        adapter.notifyDataSetChanged();
    }

    private void setBottomBarTextColor() {
        txtFlashSale.setTextColor(getResources().getColor(R.color.text_color_gold_bottom));
        ivFlashSale.setImageResource(R.drawable.flashsalefilled);
    }

    @Override
    public void setTitleBar(final TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        if (prefHelper.getNotificationCount() > 0) {
            titleBar.showNotificationDot();
        } else {
            titleBar.hideNotificationDot();
        }

        titleBar.showFilterButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(FilterFragment.newInstance("ImComingFromFlashSale"), FilterFragment.class.getName());
            }
        });
        titleBar.showSearchButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                                //bindData(getSearchedArray(s.toString()));
                            }
                        }, new TextView.OnEditorActionListener() {
                            @Override
                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                UtilsGlobal.HideKeyBoard(getDockActivity());
                                searchText = getTitleBar().getEditTextViewSearch(R.id.edt_search).getText().toString();
                                serviceHelper.enqueueCall(webService.flashSaleSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.FLASHSALE);
                                return false;
                            }
                        });
                        //llSearch.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        titleBar.setSubHeading(getString(R.string.flashsale));

        titleBar.searchClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                searchText = getTitleBar().getEditTextViewSearch(R.id.edt_search).getText().toString();
                serviceHelper.enqueueCall(webService.flashSaleSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.FLASHSALE);
            }
        });
        titleBar.showNewFilterButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDockActivity().replaceDockableFragment(BrandFilterFragment.newInstance(false), BrandFilterFragment.class.getName());
            }
        });

        titleBar.crossBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                getTitleBar().getEditTextViewSearch(R.id.edt_search).setText("");
                getFlashSaleData();
            }
        });

        titleBar.showMapFilterButton(this);
        titleBar.showMapFilterButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_address.callOnClick();
            }
        });
    }

    @Override
    public void eVoucherLike(Integer entityId, boolean id) {
    }

    @Override
    public void eventsLike(Integer id, boolean isChecked) {
    }

    @Override
    public void flashSaleLike(Integer entityId, boolean status) {
        if (entityId != null)
            serviceHelper.enqueueCall(webService.flashSaleLike(entityId, status ? 1 : 0, prefHelper.getSignUpUser().getToken()), WebServiceConstants.FLASHSALELIKE);
    }

    public void getCurrencyRateSGD() {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            Call<ResponseWrapper<CurrencyRateEntity>> call = withoutHeaderWebService.getCurrencyRate(

                    AppConstants.USD,
                    AppConstants.SGD
            );

            call.enqueue(new Callback<ResponseWrapper<CurrencyRateEntity>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<CurrencyRateEntity>> call, Response<ResponseWrapper<CurrencyRateEntity>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                        prefHelper.setConvertedAmount(response.body().getResult().getRate());
                        prefHelper.setConvertedAmountCurrency(AppConstants.SGD);

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<CurrencyRateEntity>> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }

    public void getCurrencyRateMYR() {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            Call<ResponseWrapper<CurrencyRateEntity>> call = withoutHeaderWebService.getCurrencyRate(

                    AppConstants.USD,
                    AppConstants.MYR
            );

            call.enqueue(new Callback<ResponseWrapper<CurrencyRateEntity>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<CurrencyRateEntity>> call, Response<ResponseWrapper<CurrencyRateEntity>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                        prefHelper.setConvertedAmount(response.body().getResult().getRate());
                        prefHelper.setConvertedAmountCurrency(AppConstants.MYR);

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<CurrencyRateEntity>> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }

    public void getCurrencyRateIDR() {
        if (InternetHelper.CheckInternetConectivityandShowToast(getDockActivity())) {
            loadingStarted();
            Call<ResponseWrapper<CurrencyRateEntity>> call = withoutHeaderWebService.getCurrencyRate(

                    AppConstants.USD,
                    AppConstants.IDR
            );

            call.enqueue(new Callback<ResponseWrapper<CurrencyRateEntity>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<CurrencyRateEntity>> call, Response<ResponseWrapper<CurrencyRateEntity>> response) {
                    loadingFinished();
                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                        prefHelper.setConvertedAmount(response.body().getResult().getRate());
                        prefHelper.setConvertedAmountCurrency(AppConstants.IDR);

                    } else {
                        UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<CurrencyRateEntity>> call, Throwable t) {
                    loadingFinished();
                    UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
                }
            });
        }
    }

    @Override
    public void onLocationSet(LatLng location, String formattedAddress) {
        UIHelper.hideSoftKeyboard(getDockActivity(), getView());
        this.location = location;
        serviceHelper.enqueueCall(webService.flashSaleMap(String.valueOf(location.latitude), String.valueOf(location.longitude), prefHelper.getSignUpUser().getToken()), WebServiceConstants.FLASHSALE);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.ll_flashSale, R.id.ll_Evoucher, R.id.ll_events, R.id.ll_Rewards})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_flashSale:
                getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), FlashSaleFragment.class.getName());
                break;
            case R.id.ll_Evoucher:
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getName());
                break;
            case R.id.ll_events:
                getDockActivity().replaceDockableFragment(EventsFragment.newInstance(), EventsFragment.class.getName());
                break;
            case R.id.ll_Rewards:
                getDockActivity().replaceDockableFragment(RewardsFragment.newInstance(), RewardsFragment.class.getName());
                break;
        }
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
                    serviceHelper.enqueueCall(webService.flashSaleMap(String.valueOf(location.latitude), String.valueOf(location.longitude), prefHelper.getSignUpUser().getToken()), WebServiceConstants.FLASHSALE);
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getDockActivity(), data);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }
}
