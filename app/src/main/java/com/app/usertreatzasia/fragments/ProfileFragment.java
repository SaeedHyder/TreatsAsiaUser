package com.app.usertreatzasia.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.ResponseWrapper;
import com.app.usertreatzasia.entities.SignupEntities;
import com.app.usertreatzasia.entities.SignupEntitiesChanged;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.interfaces.IGetLocation;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends BaseFragment implements IGetLocation, View.OnClickListener {

    @BindView(R.id.edt_name)
    AnyEditTextView edtName;
    @BindView(R.id.edt_email)
    AnyEditTextView edtEmail;
    @BindView(R.id.edt_number)
    AnyEditTextView edtNumber;
    @BindView(R.id.tv_address)
    AnyTextView tv_address;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_view_point_history)
    Button btn_view_point_history;
    @BindView(R.id.btn_view_credit_history)
    Button btn_view_credit_history;
    @BindView(R.id.tv_credit_spent)
    AnyTextView tvCreditSpent;
    @BindView(R.id.ll_total_credits_spent)
    LinearLayout llTotalCreditsSpent;
    @BindView(R.id.tv_credit_earned)
    AnyTextView tvCreditEarned;
    @BindView(R.id.ll_total_credits_earned)
    LinearLayout llTotalCreditsEarned;
    @BindView(R.id.tv_points_spent)
    AnyTextView tvPointsSpent;
    @BindView(R.id.ll_total_points_spent)
    LinearLayout llTotalPointsSpent;
    @BindView(R.id.tv_points_earned)
    AnyTextView tvPointsEarned;
    @BindView(R.id.ll_total_points_earned)
    LinearLayout llTotalPointsEarned;
    @BindView(R.id.tv_total_members)
    AnyTextView tvTotalMembers;
    @BindView(R.id.ll_total_members)
    LinearLayout llTotalMembers;
    @BindView(R.id.tv_total_vouchers_purchased)
    AnyTextView tvTotalVouchersPurchased;
    @BindView(R.id.ll_total_vouchers_purchased)
    LinearLayout llTotalVouchersPurchased;
    private LatLng location;

    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getProfileData();
        edtNumber.setEnabled(false);
        tv_address.setOnClickListener(this);
        location = new LatLng(0, 0);

        btn_view_point_history.setVisibility(View.GONE);
        btn_view_credit_history.setVisibility(View.GONE);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getString(R.string.my_profile));
        titleBar.showDoneText(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                    if (tv_address.getText().toString().equals("Please Enter Location")) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Please enter location.");
                    } else if (edtNumber.getText().toString().length() < 6) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Please enter correct number.");
                    } else if (tv_address.getText().toString().length() < 3) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Please enter complete address.");
                    } else {
                        updateProfileData();
                    }
            }
        });
    }

    @OnClick({R.id.btn_update, R.id.btn_view_point_history, R.id.btn_view_credit_history, R.id.ll_total_credits_spent, R.id.ll_total_credits_earned, R.id.ll_total_points_spent, R.id.ll_total_points_earned, R.id.ll_total_members, R.id.ll_total_vouchers_purchased})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_update:
                if (validate())
                    if (tv_address.getText().toString().equals("")) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Please enter location.");
                    } else if (edtNumber.getText().toString().length() < 6) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Please enter correct number.");
                    } else if (tv_address.getText().toString().length() < 3) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Please enter complete address.");
                    } else {
                        updateProfileData();
                    }
                break;
            case R.id.btn_view_point_history:
                getDockActivity().replaceDockableFragment(PointHistoryFragment.newInstance(""), "PointHistoryFragment");
                break;
            case R.id.btn_view_credit_history:
                getDockActivity().replaceDockableFragment(CreditHistoryFragment.newInstance(), "CreditHistoryFragment");
                break;
            case R.id.ll_total_credits_spent:
                getDockActivity().replaceDockableFragment(CreditHistoryFragment.newInstance(), "CreditHistoryFragment");
                break;
            case R.id.ll_total_credits_earned:
                getDockActivity().replaceDockableFragment(CreditHistoryFragment.newInstance(), "CreditHistoryFragment");
                break;
            case R.id.ll_total_points_spent:
                getDockActivity().replaceDockableFragment(PointHistoryFragment.newInstance(AppConstants.Spend), "PointHistoryFragment");
                break;
            case R.id.ll_total_points_earned:
                getDockActivity().replaceDockableFragment(PointHistoryFragment.newInstance(AppConstants.Earned), "PointHistoryFragment");
                break;
            case R.id.ll_total_members:
                getDockActivity().replaceDockableFragment(TotalMembersFragment.newInstance(), "TotalMembersFragment");
                break;
            case R.id.ll_total_vouchers_purchased:
                break;
        }
    }

    private boolean validate() {
        return edtName.testValidity() && edtEmail.testValidity() && edtNumber.testValidity();
    }

    private void getProfileDataFromPreference() {
        if (prefHelper != null && prefHelper.getSignUpUser() != null) {
            edtName.setText(prefHelper.getSignUpUser().getFirstName() + "");
            edtEmail.setText(prefHelper.getSignUpUser().getEmail() + "");
            edtNumber.setText(prefHelper.getSignUpUser().getMobileNo() + "");
            tv_address.setText(prefHelper.getSignUpUser().getLocation() + "");
            if (prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getLatitude() != null && prefHelper.getSignUpUser().getLongitude() != null
                    && !prefHelper.getSignUpUser().getLatitude().equalsIgnoreCase("") && !prefHelper.getSignUpUser().getLongitude().equalsIgnoreCase("")
                    ) {
                location = new LatLng(Double.parseDouble(prefHelper.getSignUpUser().getLatitude()), Double.parseDouble(prefHelper.getSignUpUser().getLongitude()));
            }
        }
    }

    private void getProfileData() {
        loadingStarted();
        Call<ResponseWrapper<SignupEntitiesChanged>> call = webService.getUserProfileUpdated(

                prefHelper.getSignUpUser().getToken()
        );

        call.enqueue(new Callback<ResponseWrapper<SignupEntitiesChanged>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SignupEntitiesChanged>> call, Response<ResponseWrapper<SignupEntitiesChanged>> response) {
                getMainActivity().onLoadingFinished();

                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    edtName.setText(response.body().getResult().getFirstName() + "");
                    edtEmail.setText(response.body().getResult().getEmail() + "");
                    edtNumber.setText(response.body().getResult().getMobileNo() + "");
                    if (response.body().getResult().getLocation() != null) {
                        tv_address.setText(response.body().getResult().getLocation() + "");
                    } else {
                        tv_address.setText("Please Enter Location");
                    }
                    if (response.body().getResult().getUsedCredit() != null) {
                        tvCreditSpent.setText("$" + " " + response.body().getResult().getUsedCredit());
                    } else {
                        tvCreditSpent.setText("$" + " " + "0");
                    }
                    if (response.body().getResult().getEarnedCredit() != null) {
                        tvCreditEarned.setText("$" + " " + response.body().getResult().getEarnedCredit() + "");
                    } else {
                        tvCreditEarned.setText("$" + " " + "0");
                    }

                    tvTotalMembers.setText(response.body().getResult().getGettotalmember() + "");
                    tvTotalVouchersPurchased.setText(response.body().getResult().getGetevoucher() + "");
                    if (response.body().getResult().getUsedPoint() != null) {
                        tvPointsSpent.setText(response.body().getResult().getUsedPoint() + "");
                    } else {
                        tvPointsSpent.setText("0");
                    }
                    if (response.body().getResult().getEarnedPoint() != null) {
                        tvPointsEarned.setText(response.body().getResult().getEarnedPoint() + "");
                    } else {
                        tvPointsEarned.setText("0");
                    }

                    if (response.body().getResult() != null && response.body().getResult().getLatitude() != null && response.body().getResult().getLongitude() != null
                            && !response.body().getResult().getLatitude().equalsIgnoreCase("") && !response.body().getResult().getLongitude().equalsIgnoreCase("")) {
                        location = new LatLng(Double.parseDouble(response.body().getResult().getLatitude()), Double.parseDouble(response.body().getResult().getLongitude()));
                    }
                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<SignupEntitiesChanged>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }

    private void updateProfileData() {
        loadingStarted();
        Call<ResponseWrapper<SignupEntities>> call = webService.updateUserProfile(

                edtName.getText().toString() + "",
                edtEmail.getText().toString() + "",
                edtNumber.getText().toString() + "",
                tv_address.getText().toString() + "",
                location.latitude + "",
                location.longitude + "",
                prefHelper.getSignUpUser().getToken()
        );

        call.enqueue(new Callback<ResponseWrapper<SignupEntities>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SignupEntities>> call, Response<ResponseWrapper<SignupEntities>> response) {
                loadingFinished();

                if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {

                    UIHelper.showShortToastInCenter(getDockActivity(), "Profile has been updated successfully.");
                    getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), "FlashSaleFragment");

                } else {
                    UIHelper.showLongToastInCenter(getDockActivity(), response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<SignupEntities>> call, Throwable t) {
                loadingFinished();
                UIHelper.showLongToastInCenter(getDockActivity(), t.getMessage());
            }
        });
    }

    @Override
    public void onLocationSet(LatLng location, String formattedAddress) {
        tv_address.setText(formattedAddress + "");
        this.location = location;
    }

    @Override
    public void onClick(View v) {
        requestLocationPermission();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
                    tv_address.setText(getMainActivity().getCurrentAddress(place.getLatLng().latitude,place.getLatLng().longitude));
                    this.location = place.getLatLng();
                }

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getDockActivity(), data);

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }
}
