package com.app.usertreatzasia.ui.views;

import android.content.Context;
import android.graphics.Color;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.interfaces.IGetLocation;
import com.google.android.gms.maps.model.LatLng;

public class TitleBar extends RelativeLayout {

    private TextView txtTitle;
    private ImageView btnLeft;
    private ImageView btnRight0;
    private ImageView btnRight1;
    private ImageView btnRight2;
    private AnyTextView txt_badge_notifications;
    private TextView txtClear;
    private TextView txtDone;
    private LinearLayout llSearch;
    private EditText edt_search;
    private ImageView iv_cancel;
    private ImageView iv_search;
    private CheckBox CBRight2;
    private ImageView btnRightFirst;
    private ImageView btnRightOne;


    private View.OnClickListener menuButtonListener;
    private OnClickListener backButtonListener;

    private Context context;
    private AnyTextView txtBadge;
    private AnyTextView txtBadge0;


    public TitleBar(Context context) {
        super(context);
        this.context = context;
        initLayout(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
        if (attrs != null)
            initAttrs(context, attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLayout(context);
        if (attrs != null)
            initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
    }

    private void bindViews() {

        txtTitle = (TextView) this.findViewById(R.id.txt_subHead);
        btnRight0 = (ImageView) this.findViewById(R.id.btnRight0);
        btnRight1 = (ImageView) this.findViewById(R.id.btnRight1);
        btnRight2 = (ImageView) this.findViewById(R.id.btnRight2);
        btnLeft = (ImageView) this.findViewById(R.id.btnLeft);
        txtClear = (TextView) this.findViewById(R.id.txtClear);
        txtDone = (TextView) this.findViewById(R.id.txtDone);
        txtBadge = (AnyTextView) findViewById(R.id.txtBadge);
        txtBadge0 = (AnyTextView) findViewById(R.id.txtBadge0);
        edt_search = (EditText) findViewById(R.id.edt_search);
        iv_cancel = (ImageView) this.findViewById(R.id.iv_cancel);
        iv_search = (ImageView) this.findViewById(R.id.iv_search);
        llSearch = (LinearLayout) this.findViewById(R.id.llSearch);
        CBRight2=(CheckBox)this.findViewById(R.id.CBRight2);
        btnRightFirst = (ImageView) this.findViewById(R.id.btnRightFirst);
        btnRightOne = (ImageView) this.findViewById(R.id.btnRightOne);
        txt_badge_notifications = (AnyTextView) this.findViewById(R.id.txt_badge_notifications);
    }

    private void initLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_main, this);
        bindViews();
/*
        edt_search.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //do here your stuff

                    return true;
                }
                return false;
            }
        });*/
    }

    public void hideButtons() {
        txtTitle.setVisibility(View.GONE);
        btnLeft.setVisibility(View.GONE);
        btnRight2.setVisibility(View.GONE);
        btnRight1.setVisibility(View.GONE);
        btnRight0.setVisibility(View.GONE);
        txtClear.setVisibility(View.GONE);
        txtDone.setVisibility(View.GONE);
        txtBadge.setVisibility(View.GONE);
        txtBadge0.setVisibility(View.GONE);
        llSearch.setVisibility(GONE);
        CBRight2.setVisibility(GONE);
        btnRightFirst.setVisibility(GONE);
        btnRightOne.setVisibility(GONE);
        txt_badge_notifications.setVisibility(GONE);
    }

    public void showSearchBar(TextWatcher textWatcher, EditText.OnEditorActionListener searchListner) {
        llSearch.setVisibility(VISIBLE);
        edt_search.setOnEditorActionListener(searchListner);
       /* iv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search.setText("");
            }
        });*/
        edt_search.addTextChangedListener(textWatcher);
    }

    public CheckBox getCheckbox(){
        return CBRight2;
    }

    public void crossBtnClick(OnClickListener cross) {

        iv_cancel.setOnClickListener(cross);
    }

    public EditText getEditTextViewSearch(int resouceId) {

        EditText edt_search = (EditText) findViewById(resouceId);
        return edt_search;
    }

    public void searchClick( OnClickListener search){

        iv_search.setOnClickListener(search);
    }

    public void hideSearchBar(){
        edt_search.setText("");
        llSearch.setVisibility(GONE);
    }

    public void showBackButton() {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setImageResource(R.drawable.backbtn);
        btnLeft.setOnClickListener(backButtonListener);
    }

    public void showBackButtonAsPerRequirement(OnClickListener backbtn) {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setImageResource(R.drawable.backbtn);
        btnLeft.setOnClickListener(backbtn);
    }

    public void showMenuButton() {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setOnClickListener(menuButtonListener);
        btnLeft.setImageResource(R.drawable.dropdown);
    }

    public void showNotificationDot(){
        txt_badge_notifications.setVisibility(VISIBLE);
    }

    public void hideNotificationDot(){
        txt_badge_notifications.setVisibility(GONE);
    }

    public void showNewFilterButton(View.OnClickListener filter) {
        btnRightFirst.setVisibility(View.VISIBLE);
        btnRightFirst.setOnClickListener(filter);
        btnRightFirst.setImageResource(R.drawable.a_z);
    }

    public void showMapFilterButton(View.OnClickListener filter) {
        btnRightOne.setVisibility(View.VISIBLE);
        btnRightOne.setOnClickListener(filter);
        btnRightOne.setImageResource(R.drawable.map_icn);
    }

    public void showFilterButton(View.OnClickListener filter) {
        btnRight1.setVisibility(View.VISIBLE);
        btnRight1.setOnClickListener(filter);
        btnRight1.setImageResource(R.drawable.filter);
    }

    public void showFilterButtonCorner(View.OnClickListener filter) {
        btnRight2.setVisibility(View.VISIBLE);
        btnRight2.setOnClickListener(filter);
        btnRight2.setImageResource(R.drawable.filter);
    }

    public void showSearchButton(View.OnClickListener search) {
        btnRight2.setVisibility(View.VISIBLE);
        btnRight2.setOnClickListener(search);
        btnRight2.setImageResource(R.drawable.search);
    }

    public void showFavoriteButton(CheckBox.OnCheckedChangeListener Favorite) {
        CBRight2.setVisibility(View.VISIBLE);
        btnRight2.setVisibility(INVISIBLE);
        CBRight2.setOnCheckedChangeListener(Favorite);
    }

    public void setFavoriteButn(boolean status){

        CBRight2.setChecked(status);
    }

    public void showShareButton(View.OnClickListener share) {
        btnRight1.setVisibility(View.VISIBLE);
        btnRight1.setOnClickListener(share);
        btnRight1.setImageResource(R.drawable.share);
    }

    public void showShareButtonEnd(View.OnClickListener share) {
        btnRight2.setVisibility(View.VISIBLE);
        btnRight2.setOnClickListener(share);
        btnRight2.setImageResource(R.drawable.share);
    }

    public void showClearText(View.OnClickListener clear) {
        txtClear.setVisibility(View.VISIBLE);
        txtClear.setOnClickListener(clear);
        txtClear.setText(R.string.clear);
    }

    public void showDoneText(View.OnClickListener done) {
        txtDone.setVisibility(View.VISIBLE);
        txtDone.setOnClickListener(done);
        txtDone.setText(R.string.done);
    }

    public void showSkipText(View.OnClickListener skip) {
        txtClear.setVisibility(View.VISIBLE);
        txtClear.setOnClickListener(skip);
        txtClear.setText(R.string.skip);
        txtClear.setTextColor(Color.WHITE);
        txtClear.setBackgroundColor(Color.TRANSPARENT);
    }

    public void showNotificationButton(View.OnClickListener notification) {
        btnRight0.setVisibility(View.VISIBLE);
        btnRight0.setOnClickListener(notification);
        btnRight0.setImageResource(R.drawable.notification);
    }

    public void showNotificationButton(OnClickListener listener, int Count) {
        btnRight0.setVisibility(View.VISIBLE);
        btnRight0.setOnClickListener(listener);
        btnRight0.setImageResource(R.drawable.notification);
        if (Count > 0) {
            txtBadge0.setVisibility(View.VISIBLE);
            txtBadge0.setText(Count + "");
        } else {
            txtBadge0.setVisibility(View.GONE);
        }
    }

    public void showNotificationButtonInCorner(OnClickListener listener, int Count) {
        btnRight2.setVisibility(View.VISIBLE);
        btnRight2.setOnClickListener(listener);
        btnRight2.setImageResource(R.drawable.notification);
        if (Count > 0) {
            txtBadge.setVisibility(View.VISIBLE);
            txtBadge.setText(Count + "");
        } else {
            txtBadge.setVisibility(View.GONE);
        }
    }

    public void showNotificationButtonInCorner(View.OnClickListener notification) {
        btnRight2.setVisibility(View.VISIBLE);
        btnRight2.setOnClickListener(notification);
        btnRight2.setImageResource(R.drawable.notification);
    }

    public void setSubHeading(String heading) {
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText(heading);
    }

    public void showTitleBar() {
        this.setVisibility(View.VISIBLE);
    }

    public void hideTitleBar() {
        this.setVisibility(View.GONE);
    }

    public void setMenuButtonListener(View.OnClickListener listener) {
        menuButtonListener = listener;
    }

    public void setBackButtonListener(View.OnClickListener listener) {
        backButtonListener = listener;
    }
}
