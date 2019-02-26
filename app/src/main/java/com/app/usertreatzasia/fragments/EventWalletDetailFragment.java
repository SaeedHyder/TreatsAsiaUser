package com.app.usertreatzasia.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.BuyTicketEnt;
import com.app.usertreatzasia.entities.EventDetailWallet;
import com.app.usertreatzasia.entities.NewEventDetailEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.app.usertreatzasia.global.WebServiceConstants.REDEEMTICKET;


public class EventWalletDetailFragment extends BaseFragment implements OnMapReadyCallback {


    @BindView(R.id.iv_eventImage)
    ImageView ivEventImage;
    @BindView(R.id.txt_paid_free)
    AnyTextView txtPaidFree;
    @BindView(R.id.txt_eventTitle)
    AnyTextView txt_eventTitle;
    @BindView(R.id.txt_price)
    AnyTextView txtPrice;
    @BindView(R.id.txtPointsAmount)
    AnyTextView txtPointsAmount;
    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    @BindView(R.id.txt_time)
    AnyTextView txtTime;
    @BindView(R.id.txt_date)
    AnyTextView txtDate;
    @BindView(R.id.img_barcode)
    ImageView imgBarcode;
    @BindView(R.id.btn_butTicket)
    Button btnButTicket;
    @BindView(R.id.btn_gift)
    Button btnGift;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.ll_promocode)
    LinearLayout ll_promocode;
    @BindView(R.id.txt_EventDetails)
    AnyTextView txtEventDetails;
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;


    private String goToHome;
    private View viewParent;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private static String EVENTID = "eventid";
    private static String ISLIKE = "IsLike";
    private int EventId;
    private int IsLike;
    //private EventDetailWallet entity = new EventDetailWallet();
    private BuyTicketEnt ticketEntity = new BuyTicketEnt();
    ImageLoader imageLoader;
    private static String walletEventDetails = "walletEventDetails";
    private EventDetailWallet walletDetailEntity;
    int checkboxValue = 0;
    NewEventDetailEnt global;
    Float updatedPrice;
    public static EventWalletDetailFragment newInstance() {
        return new EventWalletDetailFragment();
    }

    public static EventWalletDetailFragment newInstance(int id) {
        return new EventWalletDetailFragment();
    }

    public static EventWalletDetailFragment newInstance(EventDetailWallet walletDetailEntity) {
        Bundle args = new Bundle();
        args.putString(walletEventDetails, new Gson().toJson(walletDetailEntity));
        EventWalletDetailFragment fragment = new EventWalletDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

/*    public static EventWalletDetailFragment newInstance(int id, Integer event_like) {
        Bundle args = new Bundle();
        args.putInt(EVENTID, id);
        args.putInt(ISLIKE, event_like);
        EventWalletDetailFragment fragment = new EventWalletDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
/*            EventId = getArguments().getInt(EVENTID);
            IsLike = getArguments().getInt(ISLIKE);*/
            String i = getArguments().getString(walletEventDetails);
            walletDetailEntity = new Gson().fromJson(i, EventDetailWallet.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent.getParent();
            if (parent != null)
                parent.removeView(viewParent);
        }
        try {
            viewParent = inflater.inflate(R.layout.fragment_event_wallet_details, container, false);

        } catch (InflateException e) {
            e.printStackTrace();
        }
        if (viewParent != null)
            ButterKnife.bind(this, viewParent);

        ButterKnife.bind(this, viewParent);
        return viewParent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goToHome = "goToHome";
        mainFrame.setVisibility(View.INVISIBLE);
        imageLoader = ImageLoader.getInstance();
        if (mapFragment == null) {
            initMap();
        }

        getEventDetailData();


/*        if (IsLike == 1) {
            getTitleBar().setFavoriteButn(true);
        } else {
            getTitleBar().setFavoriteButn(false);
        }*/
    }

    private void getEventDetailData() {
        prefHelper.setEventId(walletDetailEntity.getId());
        serviceHelper.enqueueCall(webService.eventDetailWallet(String.valueOf(walletDetailEntity.getId()), prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTS_WALLET_DETAIL);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.EVENTS_WALLET_DETAIL:
                global = (NewEventDetailEnt) result;
                break;

            case REDEEMTICKET:
                ticketEntity = (BuyTicketEnt) result;
                imageLoader.displayImage(ticketEntity.getQrCodeUrl(), imgBarcode);
                //btn_cancel.setVisibility(View.VISIBLE);
                btnButTicket.setVisibility(View.GONE);
                btnGift.setVisibility(View.GONE);
                imgBarcode.setVisibility(View.VISIBLE);
                //getDockActivity().addDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getName());
                break;
        }
    }

    private void setEventDetailData() {

        checkboxValue = walletDetailEntity.getEventLike();
        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            txt_eventTitle.setText(walletDetailEntity.getEventName() + "");
            Spanned desc = Html.fromHtml(walletDetailEntity.getDescription());
            txtEventDetails.setText(desc.toString().trim());
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            txt_eventTitle.setText(walletDetailEntity.getInEventName() + "");
            Spanned desc = Html.fromHtml(walletDetailEntity.getInDescription());
            txtEventDetails.setText(desc.toString().trim());
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            txt_eventTitle.setText(walletDetailEntity.getMaEventName() + "");
            Spanned desc = Html.fromHtml(walletDetailEntity.getMaDescription());
            txtEventDetails.setText(desc.toString().trim());
        } else {
            txt_eventTitle.setText(walletDetailEntity.getEventName() + "");
            Spanned desc = Html.fromHtml(walletDetailEntity.getDescription());
            txtEventDetails.setText(desc.toString().trim());
        }

        if (walletDetailEntity.getEventLike()==1){
            getTitleBar().getCheckbox().setChecked(true);
        } else
            getTitleBar().getCheckbox().setChecked(false);
        imageLoader.displayImage(walletDetailEntity.getEventImage(), ivEventImage);

        //txtPrice.setText("$" + walletDetailEntity.getAmount() + "");

        updatedPrice = Float.valueOf(walletDetailEntity.getAmount()) * prefHelper.getConvertedAmount();
        String formattedValuePriceDiscounted = String.format("%.2f", updatedPrice);
        txtPrice.setText(prefHelper.getConvertedAmountCurrrency() + " " + formattedValuePriceDiscounted + "");

        txtPaidFree.setText(UtilsGlobal.getVoucherType(walletDetailEntity.getType(), getDockActivity()));

        txtDate.setText(DateHelper.dateFormat(walletDetailEntity.getStartDate(), AppConstants.DateFormat_DmY, AppConstants.DateFormat_YMD) + " " + getDockActivity().getResources().getString(R.string.to) + " " + DateHelper.dateFormat(walletDetailEntity.getEndDate(), AppConstants.DateFormat_DmY, AppConstants.DateFormat_YMD));

        txtTime.setText(DateHelper.dateFormat(walletDetailEntity.getStartTime(), AppConstants.DateFormat_Hours, AppConstants.DateFormat_HoursMinsSecs) + " " + UtilsGlobal.toTitleCase(walletDetailEntity.getStartFormat()) + " "
                + getDockActivity().getResources().getString(R.string.to) + " " +
                DateHelper.dateFormat(walletDetailEntity.getEndTime(), AppConstants.DateFormat_Hours, AppConstants.DateFormat_HoursMinsSecs) + " " + UtilsGlobal.toTitleCase(walletDetailEntity.getEndFormat()));
        txtPointsAmount.setText(walletDetailEntity.getPoint() + "");
        mainFrame.setVisibility(View.VISIBLE);
        ll_promocode.setVisibility(View.GONE);
    }

    private void addMarker(EventDetailWallet entity) {
        LatLng markerPosition = new LatLng(Double.parseDouble(entity.getLatitude()), Double.parseDouble(entity.getLongitude()));
        MarkerOptions marker = new MarkerOptions().position(markerPosition)
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.set_pickup_location,
                        getString(R.string.campaign_launched), getString(R.string.on) + " " + DateHelper.dateFormat(entity.getStartDate(), "dd MMMM,yyyy", "yyyy-MM-dd") + " " + getString(R.string.at) + " " + entity.getVenue())));

        mMap.addMarker(marker);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(markerPosition)
                .zoom(14)
                .bearing(0)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
    }

    private void initMap() {
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setEventDetailData();
        addMarker(walletDetailEntity);
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId, String title, String detail) {

        View customMarkerView = ((LayoutInflater) getMainActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        TextView textView = (TextView) customMarkerView.findViewById(R.id.txtHeading);
        textView.setText(title);
        textView.setTextColor(getResources().getColor(R.color.black));


        TextView textViewDetail = (TextView) customMarkerView.findViewById(R.id.txtDetail);
        textViewDetail.setText(detail);

        LinearLayout markerView = (LinearLayout) customMarkerView.findViewById(R.id.ll_Markerview);
        markerView.setBackground(getResources().getDrawable(R.drawable.bg_rounder_transparent));


        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void setTitleBar(final TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.event_detail));

        titleBar.showBackButtonAsPerRequirement(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getDockActivity().replaceDockableFragment(EventsWalletFragment.newInstance(), "EventsWalletFragment");
            }
        });
        titleBar.showShareButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = walletDetailEntity.getEventName() + "http://Treatzasia.com";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
            }
        });
        titleBar.showFavoriteButton(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkboxValue == 1) {
                    buttonView.setChecked(true);
                } else
                    buttonView.setChecked(false);
            }

        });
        titleBar.getCheckbox().setEnabled(false);
    }

    @OnClick({R.id.btn_butTicket, R.id.btn_gift, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_butTicket:
                serviceHelper.enqueueCall(webService.redeemTicket(String.valueOf(walletDetailEntity.getId()), AppConstants.REDEEM, prefHelper.getSignUpUser().getToken()), REDEEMTICKET);
                break;
            case R.id.btn_gift:
                //serviceHelper.enqueueCall(webService.giftTicket(walletDetailEntity.getId(), AppConstants.GIFT, 6,prefHelper.getSignUpUser().getToken()), GIFTTICKET);
                getDockActivity().replaceDockableFragment(GiftRecipientsFragment.newInstance("ImComingFromEventWallet"), "GiftRecipientsFragment");
                break;
        }
    }
}
