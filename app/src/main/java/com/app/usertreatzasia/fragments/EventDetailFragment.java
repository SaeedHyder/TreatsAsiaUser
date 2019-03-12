package com.app.usertreatzasia.fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
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
import com.app.usertreatzasia.entities.EventsEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.helpers.UIHelper;
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
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventDetailFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback {


    @BindView(R.id.iv_eventImage)
    ImageView ivEventImage;
    @BindView(R.id.txt_paid_free)
    AnyTextView txtPaidFree;
    @BindView(R.id.txt_eventTitle)
    AnyTextView txt_eventTitle;
    @BindView(R.id.txt_price)
    AnyTextView txtPrice;
    @BindView(R.id.txtPoints)
    AnyTextView txtPoints;
    @BindView(R.id.ll_points)
    LinearLayout llPoints;
    @BindView(R.id.txtCredits)
    AnyTextView txtCredits;
    @BindView(R.id.ll_credits)
    LinearLayout llCredits;
    @BindView(R.id.txtCash)
    AnyTextView txtCash;
    @BindView(R.id.ll_cash)
    LinearLayout llCash;
    @BindView(R.id.ll_free)
    LinearLayout llFree;
    @BindView(R.id.txt_time)
    AnyTextView txtTime;
    @BindView(R.id.txt_date)
    AnyTextView txtDate;
    @BindView(R.id.img_barcode)
    ImageView imgBarcode;
    @BindView(R.id.btn_butTicket)
    Button btnButTicket;
    @BindView(R.id.txt_EventDetails)
    AnyTextView txtEventDetails;
    /*    @BindView(R.id.map)
        SupportMapFragment map;*/
    @BindView(R.id.mainFrame)
    LinearLayout mainFrame;
    @BindView(R.id.currency_type_cash)
    AnyTextView currencyTypeCash;
    private String goToHome;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private static String EVENTID = "eventid";
    private static String ISLIKE = "IsLike";
    private int EventId;
    private int IsLike;
    private EventsEnt entity = new EventsEnt();
    private BuyTicketEnt ticketEntity = new BuyTicketEnt();
    ImageLoader imageLoader;
    EventsEnt global;
    private String CREDIT_AMOUNT = "0";
    private String POINT = "0";
    private String PAYMENT_TYPE = "free";
    private String SELECTED_TYPE = "free";
    String FormattedDate;
    private int ID;
    private int Type;
    private Date startDate;
    String description;
    long timeInMillisecondsStart;
    long timeInMillisecondsEnd;

    ComponentName cn;
    Long _eventId;
    int year;
    int month;
    int days;
    int hour;
    int minute;
    private View viewParent;
    Float updatedCash;
    Float updatedPrice;

    public static EventDetailFragment newInstance() {
        return new EventDetailFragment();
    }

   /* public static EventDetailFragment newInstance(Integer id, Integer event_like) {
        return new EventDetailFragment();
    }*/

    public static EventDetailFragment newInstance(Integer id, Integer event_like) {
        Bundle args = new Bundle();
        args.putInt(EVENTID, id);
        args.putInt(ISLIKE, event_like);
        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setContent(int id, int type) {
        setID(id);
        setType(type);
        //  return this;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setType(int type) {
        Type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            EventId = getArguments().getInt(EVENTID);
            IsLike = getArguments().getInt(ISLIKE);
        }
        System.out.printf("" + ID + ":" + Type);
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
            viewParent = inflater.inflate(R.layout.fragment_event_details, container, false);

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
        setListners();
        if (mapFragment == null) {
            initMap();
        } else {
            getEventDetailData();

            SimpleDateFormat dateFormatYouWant = new SimpleDateFormat("yyyy-MM-dd");
            FormattedDate = dateFormatYouWant.format(new Date());

            if (IsLike == 1) {
                getTitleBar().setFavoriteButn(true);
            } else {
                getTitleBar().setFavoriteButn(false);
            }
        }
        //pushAppointmentsToCalender(getDockActivity(), global.getEventName(), global.getDescription(), global.getVenue(), 1, Long.parseLong(global.getStartDate()), true, false,false);
    }

    private void getEventDetailData() {
        serviceHelper.enqueueCall(webService.eventDetail(EventId, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTSDETAIL);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.EVENTSDETAIL:
                entity = (EventsEnt) result;
                setEventDetailData(entity);
                addMarker(entity);
                global = (EventsEnt) result;

                break;

            case WebServiceConstants.BUYTICKET:
                ticketEntity = (BuyTicketEnt) result;
                //  imageLoader.displayImage(ticketEntity.getQrCodeUrl(),img_barcode);
                // btnButTicket.setVisibility(View.GONE);
                // img_barcode.setVisibility(View.VISIBLE);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getName());
                break;

            case WebServiceConstants.BuyEventFree:

                createEvent();
                UIHelper.showShortToastInCenter(getDockActivity(), "Events added successfully.");
                getDockActivity().replaceDockableFragment(EventsWalletFragment.newInstance(), "EventsWalletFragment");
                break;
        }
    }

    private void setEventDetailData(EventsEnt entity) {

/*        if (entity.getEventLike() != null) {
            if (entity.getEventLike() == 1) {
                getTitleBar().getCheckbox().setChecked(true);
            } else
                getTitleBar().getCheckbox().setChecked(false);
        }*/

        if (prefHelper.getEventLike() != null) {
            if (prefHelper.getEventLike() == 1) {
                getTitleBar().getCheckbox().setChecked(true);
            } else
                getTitleBar().getCheckbox().setChecked(false);
        }
        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            txt_eventTitle.setText(entity.getEventName() + "");
            Spanned desc = Html.fromHtml(entity.getDescription());
            txtEventDetails.setText(desc.toString().trim());
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            txt_eventTitle.setText(entity.getInEventName() + "");
            Spanned desc = Html.fromHtml(entity.getInDescription());
            txtEventDetails.setText(desc.toString().trim());
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            txt_eventTitle.setText(entity.getMaEventName() + "");
            Spanned desc = Html.fromHtml(entity.getMaDescription());
            txtEventDetails.setText(desc.toString().trim());
        } else {
            txt_eventTitle.setText(entity.getEventName() + "");
            Spanned desc = Html.fromHtml(entity.getDescription());
            txtEventDetails.setText(desc.toString().trim());
        }



        imageLoader.displayImage(entity.getEventImage(), ivEventImage);

        txtPrice.setText("$" + entity.getAmount() + "");

        txtPaidFree.setText(UtilsGlobal.getVoucherType(entity.getType(), getDockActivity()));

        txtDate.setText(DateHelper.dateFormat(entity.getStartDate(), AppConstants.DateFormat_DmY, AppConstants.DateFormat_YMD) + " " + getDockActivity().getResources().getString(R.string.to) + " " + DateHelper.dateFormat(entity.getEndDate(), AppConstants.DateFormat_DmY, AppConstants.DateFormat_YMD));

        txtTime.setText(DateHelper.dateFormat(entity.getStartTime(), AppConstants.DateFormat_Hours, AppConstants.DateFormat_HoursMinsSecs) + " " + UtilsGlobal.toTitleCase(entity.getStartFormat()) + " "
                + getDockActivity().getResources().getString(R.string.to) + " " +
                DateHelper.dateFormat(entity.getEndTime(), AppConstants.DateFormat_Hours, AppConstants.DateFormat_HoursMinsSecs) + " " + UtilsGlobal.toTitleCase(entity.getEndFormat()));

        mainFrame.setVisibility(View.VISIBLE);

        if (entity.getIsPoint() == 1 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 0 && entity.getIsFree() == 0) {
            // only points
            llPoints.setVisibility(View.VISIBLE);
            txtPoints.setText(entity.getPoint());

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 0 && entity.getIsFree() == 0) {
            // only credit
            llCredits.setVisibility(View.VISIBLE);
            txtCredits.setText(entity.getPoint());

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // only cash
            llCash.setVisibility(View.VISIBLE);
            txtCash.setText(entity.getPoint());

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 0 && entity.getIsFree() == 1) {

            llFree.setVisibility(View.VISIBLE);

        } else if (entity.getIsPoint() == 1 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 0 && entity.getIsFree() == 0) {
            // only point and credit
            llPoints.setVisibility(View.VISIBLE);
            txtPoints.setText(entity.getPoint());
            llCredits.setVisibility(View.VISIBLE);
            txtCredits.setText(entity.getPoint());

        } else if (entity.getIsPoint() == 1 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // only point and cash
            llPoints.setVisibility(View.VISIBLE);
            txtPoints.setText(entity.getPoint());
            llCash.setVisibility(View.VISIBLE);
            txtCash.setText(entity.getPoint());

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // only credit and cash
            llCredits.setVisibility(View.VISIBLE);
            txtCredits.setText(entity.getPoint());
            llCash.setVisibility(View.VISIBLE);
            txtCash.setText(entity.getPoint());

        } else if (entity.getIsPoint() == 1 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // all type accepted
            llCredits.setVisibility(View.VISIBLE);
            txtCredits.setText(entity.getPoint());
            llPoints.setVisibility(View.VISIBLE);
            txtPoints.setText(entity.getPoint());
            llCash.setVisibility(View.VISIBLE);
            txtCash.setText(entity.getPoint());
        }

        try {
            updatedCash = Float.valueOf(entity.getPoint()) * prefHelper.getConvertedAmount();

        String formattedValue = String.format("%.2f", updatedCash);
        txtCash.setText(formattedValue + "");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());

        updatedPrice = Float.valueOf(entity.getAmount()) * prefHelper.getConvertedAmount();
        String formattedValuePrice = String.format("%.2f", updatedPrice);
        txtPrice.setText(prefHelper.getConvertedAmountCurrrency() + " "  + formattedValuePrice + "");
    }

    private void setListners() {
        btnButTicket.setOnClickListener(this);
    }

    private void addMarker(EventsEnt entity) {
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
        getEventDetailData();

        SimpleDateFormat dateFormatYouWant = new SimpleDateFormat("yyyy-MM-dd");
        FormattedDate = dateFormatYouWant.format(new Date());

        if (IsLike == 1) {
            getTitleBar().setFavoriteButn(true);
        } else {
            getTitleBar().setFavoriteButn(false);
        }
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
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getString(R.string.event_detail));
        titleBar.showBackButton();
        titleBar.showShareButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = global.getEventName() + "http://Treatzasia.com";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
            }
        });
        titleBar.showFavoriteButton(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              /*  if (entity.getEvent_like() == 1 ) {
                    buttonView.setChecked(true);
                } else
                    buttonView.setChecked(false);*/

                serviceHelper.enqueueCall(webService.eventLike(EventId, isChecked ? 1 : 0, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTLIKE);
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_butTicket:
                if (global.getIsFree() == 1) {
                    serviceHelper.enqueueCall(webService.BuyEventsPointCredit(EventId, CREDIT_AMOUNT, POINT, FormattedDate, PAYMENT_TYPE, SELECTED_TYPE, prefHelper.getSignUpUser().getToken()), WebServiceConstants.BuyEventFree);
                } else {
                    getDockActivity().replaceDockableFragment(PaymentChooseFragment.newInstance(global, true), "PaymentChooseFragment");
                }
                break;
        }
    }

    private void createEvent() {

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            startDate = dateFormater.parse(global.getStartDate() + " " + global.getStartTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(startDate);

        long startTime = calendar.getTimeInMillis();
        long startTime2 = System.currentTimeMillis();

        description = global.getDescription();
        Spanned desc = Html.fromHtml(description);
        description = String.valueOf(desc);

        String givenDateString = global.getStartDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMillisecondsStart = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMillisecondsStart);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String givenDateStringEnd = global.getEndDate();
        SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date mDate = sdfEnd.parse(givenDateString);
            timeInMillisecondsEnd = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMillisecondsEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pushAppointmentsToCalender(getDockActivity(), global.getEventName(), description, global.getVenue(),
                1, startTime, true, false, false);
    }

    public long pushAppointmentsToCalender(Activity curActivity, String title, String addInfo, String place, int status, long startDate, boolean needReminder, boolean needMailService, boolean deleteEvent) {

        String eventUriString = "content://com.android.calendar/events";
        ContentValues eventValues = new ContentValues();

        eventValues.put("calendar_id", 1); // id, We need to choose from
        // our mobile for primary
        // its 1
        eventValues.put("title", title);
        eventValues.put("description", addInfo);
        eventValues.put("eventLocation", place);

        long endDate = startDate + 1000 * 60 * 60; // For next 1hr
//        Long endDate = global.getEndDate(); // For next 1hr

        eventValues.put(CalendarContract.Events.DTSTART, startDate);
        eventValues.put(CalendarContract.Events.DTEND, endDate);

        // values.put("allDay", 1); //If it is bithday alarm or such
        // kind (which should remind me for whole day) 0 for false, 1
        // for true
        eventValues.put("eventStatus", status); // This information is
        // sufficient for most
        // entries tentative (0),
        // confirmed (1) or canceled
        // (2):
        eventValues.put("eventTimezone", "UTC/GMT +2:00");
/*Comment below visibility and transparency  column to avoid java.lang.IllegalArgumentException column visibility is invalid error */

/*eventValues.put("visibility", 3); // visibility to default (0),
                                     // confidential (1), private
                                     // (2), or public (3):
eventValues.put("transparency", 0); // You can control whether
                                     // an event consumes time
                                     // opaque (0) or transparent
                                     // (1).
   */
        eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

        Uri eventUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(eventUriString), eventValues);
        long eventID = Long.parseLong(eventUri.getLastPathSegment());

        if (needReminder) {
            /***************** Event: Reminder(with alert) Adding reminder to event *******************/

            String reminderUriString = "content://com.android.calendar/reminders";

            ContentValues reminderValues = new ContentValues();

            reminderValues.put("event_id", eventID);
            reminderValues.put("minutes", 1440); // Default value of the
            // system. Minutes is a
            // integer
            reminderValues.put("method", 1); // Alert Methods: Default(0),
            // Alert(1), Email(2),
            // SMS(3)

            Uri reminderUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        }

        /***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/

        if (needMailService) {
            String attendeuesesUriString = "content://com.android.calendar/attendees";

            /********
             * To add multiple attendees need to insert ContentValues multiple
             * times
             ***********/
            ContentValues attendeesValues = new ContentValues();

            attendeesValues.put("event_id", eventID);
            attendeesValues.put("attendeeName", "xxxxx"); // Attendees name
            attendeesValues.put("attendeeEmail", "yyyy@gmail.com");// Attendee
            // E
            // mail
            // id
            attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
            // Relationship_None(0),
            // Organizer(2),
            // Performer(3),
            // Speaker(4)
            attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
            // Required(2), Resource(3)
            attendeesValues.put("attendeeStatus", 0); // NOne(0), Accepted(1),
            // Decline(2),
            // Invited(3),
            // Tentative(4)

            Uri attendeuesesUri = curActivity.getApplicationContext().getContentResolver().insert(Uri.parse(attendeuesesUriString), attendeesValues);
        }

        return eventID;
    }
}
