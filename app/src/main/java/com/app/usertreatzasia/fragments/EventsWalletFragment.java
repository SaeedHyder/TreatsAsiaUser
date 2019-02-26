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
import com.app.usertreatzasia.entities.EventsEnt;
import com.app.usertreatzasia.entities.WalletEventEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DialogHelper;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.interfaces.FavoriteStatus;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.EventWalletItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EventsWalletFragment extends BaseFragment implements FavoriteStatus {

    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_events)
    ListView lvEvents;
    @BindView(R.id.txt_events)
    AnyTextView txtEvents;
    @BindView(R.id.txt_Gifts)
    AnyTextView txtGifts;
    @BindView(R.id.txt_eventHeading)
    AnyTextView txtEventHeading;
    private ArrayListAdapter<WalletEventEntity> adapter;
    private ArrayList<WalletEventEntity> userCollection;
    private ArrayList<WalletEventEntity> eventsResult;
    private EventsEnt eventsEnt = new EventsEnt();
    String searchText;
    private static String GIFTID = "giftId";
    private String giftIdFilter = "";
    private static String GIFTNAME = "giftName";
    private String giftName = "";

    private ImageLoader imageLoader;

    public static EventsWalletFragment newInstance() {
        return new EventsWalletFragment();
    }

    public static EventsWalletFragment newInstance(int giftId, String giftName) {
        Bundle args = new Bundle();
        args.putString(GIFTID, String.valueOf(giftId));
        args.putString(GIFTNAME, String.valueOf(giftName));
        EventsWalletFragment fragment = new EventsWalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            giftIdFilter = getArguments().getString(GIFTID);
            giftName = getArguments().getString(GIFTNAME);
        }
        adapter = new ArrayListAdapter<WalletEventEntity>(getDockActivity(), new EventWalletItemBinder(getDockActivity(), prefHelper, this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_wallet, container, false);
        imageLoader = ImageLoader.getInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDailog();
        getEventsData();
        scrollListner();
    }

    private void getDailog() {
        if (giftIdFilter != null) {
            if (!giftIdFilter.equals("")) {
                final DialogHelper cahoutDialog = new DialogHelper(getDockActivity());
                cahoutDialog.cashout(R.layout.cashout_dialog, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cahoutDialog.hideDialog();
                        getGiftData();
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

    private void getGiftData() {
        if (giftIdFilter != null) {
            if (!giftIdFilter.equals("")) {
                serviceHelper.enqueueCall(webService.giftTicket(prefHelper.getEventId(), AppConstants.GIFT, giftIdFilter, prefHelper.getSignUpUser().getToken()), WebServiceConstants.GIFTTICKET);
            }
        }
        if (giftIdFilter == null) {
            giftIdFilter = "";
        }
    }

    private void scrollListner() {

        lvEvents.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    public ArrayList<WalletEventEntity> getSearchedArray(String keyword) {
        if (userCollection.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<WalletEventEntity> arrayList = new ArrayList<>();

        for (WalletEventEntity item : userCollection) {
            String UserName = "";
            if (item != null) {
                UserName = item.getEventDetail().getEventName();
            }
            if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                /*UserName.contains(keyword)*/
                arrayList.add(item);
            }
        }
        return arrayList;
    }

    private void getEventsData() {
        serviceHelper.enqueueCall(webService.eventsWallet(prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTS_WALLET);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.EVENTS_WALLET:
                eventsResult = (ArrayList<WalletEventEntity>) result;
                setEventsListData(eventsResult);
                break;

            case WebServiceConstants.EVENTS_WALLET_SEARCH:
                eventsResult = (ArrayList<WalletEventEntity>) result;
                setEventsListData(eventsResult);
                break;

            case WebServiceConstants.GIFTTICKET:
                UIHelper.showShortToastInCenter(getDockActivity(), "Gift has been sent successfully.");
                getDockActivity().replaceDockableFragment(WalletFragment.newInstance(), "WalletFragment");
                break;
        }
    }

    private void setEventsListData(ArrayList<WalletEventEntity> result) {

        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        txtEventHeading.setText("AVAILABLE EVENTS " + userCollection.size());
        txtEventHeading.setTypeface(Typeface.DEFAULT_BOLD);
        bindData(userCollection);
    }

    private void bindData(ArrayList<WalletEventEntity> userCollection) {

        if (userCollection.size() <= 0) {
            txtNoData.setVisibility(View.VISIBLE);
            lvEvents.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            lvEvents.setVisibility(View.VISIBLE);

        }

        adapter.clearList();
        lvEvents.setAdapter(adapter);
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
                getDockActivity().replaceDockableFragment(WalletFragment.newInstance(), "WalletFragment");
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
                        serviceHelper.enqueueCall(webService.eventsWalletSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTS_WALLET_SEARCH);
                        return false;
                    }
                });

            }
        });
        titleBar.setSubHeading(getString(R.string.events));
        titleBar.crossBtnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                getTitleBar().getEditTextViewSearch(R.id.edt_search).setText("");
                getEventsData();
            }
        });
        titleBar.searchClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsGlobal.HideKeyBoard(getDockActivity());
                searchText = getTitleBar().getEditTextViewSearch(R.id.edt_search).getText().toString();
                serviceHelper.enqueueCall(webService.eventsWalletSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTS_WALLET_SEARCH);
            }
        });
    }

    @Override
    public void eVoucherLike(Integer entityId, boolean id) {
    }

    @Override
    public void eventsLike(Integer entityId, boolean status) {
    }

    @Override
    public void flashSaleLike(Integer id, boolean isChecked) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.txt_events, R.id.txt_Gifts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_events:
                //getDockActivity().replaceDockableFragment(EventsWalletFragment.newInstance(), "EventsWalletFragment");
                break;
            case R.id.txt_Gifts:
                getDockActivity().replaceDockableFragment(GiftEventRecievedFragment.newInstance(), "GiftEventRecievedFragment");
                break;
        }
    }
}
