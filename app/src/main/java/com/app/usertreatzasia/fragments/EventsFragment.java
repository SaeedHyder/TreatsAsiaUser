package com.app.usertreatzasia.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.EventsEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.interfaces.FavoriteStatus;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.EventsItemBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventsFragment extends BaseFragment implements View.OnClickListener, FavoriteStatus {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_events)
    ListView lvEvents;
    @BindView(R.id.iv_eVoucher)
    ImageView ivEVoucher;
    @BindView(R.id.txt_evoucher)
    AnyTextView txtEvoucher;
    @BindView(R.id.ll_Evoucher)
    LinearLayout llEvoucher;
    @BindView(R.id.iv_events)
    ImageView ivEvents;
    @BindView(R.id.txt_events)
    AnyTextView txtEvents;
    @BindView(R.id.ll_events)
    LinearLayout llEvents;
    @BindView(R.id.iv_rewards)
    ImageView ivRewards;
    @BindView(R.id.txt_rewards)
    AnyTextView txtRewards;
    @BindView(R.id.ll_Rewards)
    LinearLayout llRewards;
    @BindView(R.id.iv_flashSale)
    ImageView ivFlashSale;
    @BindView(R.id.txt_flashSale)
    AnyTextView txtFlashSale;
    @BindView(R.id.ll_flashSale)
    LinearLayout llFlashSale;
    private ArrayListAdapter<EventsEnt> adapter;
    private ArrayList<EventsEnt> userCollection;
    private ArrayList<EventsEnt> eventsResult;
    private EventsEnt eventsEnt = new EventsEnt();
    String searchText;

    private ImageLoader imageLoader;

    public static EventsFragment newInstance() {
        return new EventsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<EventsEnt>(getDockActivity(), new EventsItemBinder(getDockActivity(), prefHelper, this));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        imageLoader = ImageLoader.getInstance();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListners();
        getMainActivity().refreshSideMenu();
        setBottomBarTextColor();
        getEventsData();
        scrollListner();

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

    public ArrayList<EventsEnt> getSearchedArray(String keyword) {
        if (userCollection.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<EventsEnt> arrayList = new ArrayList<>();

        for (EventsEnt item : userCollection) {
            String UserName = "";
            if (item != null) {
                UserName = item.getEventName();
            }
            if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                /*UserName.contains(keyword)*/
                arrayList.add(item);
            }
        }
        return arrayList;
    }


    private void getEventsData() {
        serviceHelper.enqueueCall(webService.events(prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTS);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.EVENTS:
                eventsResult = (ArrayList<EventsEnt>) result;
                setEventsListData(eventsResult);
                break;
            case WebServiceConstants.getHomeEventSearch:
                eventsResult = (ArrayList<EventsEnt>) result;
                setEventsListData(eventsResult);
                break;
        }
    }

    private void setEventsListData(ArrayList<EventsEnt> result) {

        userCollection = new ArrayList<>();
        userCollection.addAll(result);
        bindData(userCollection);
    }

    private void bindData(ArrayList<EventsEnt> userCollection) {

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


    private void setBottomBarTextColor() {
        ivEvents.setImageResource(R.drawable.eventsfilled);
    }

    private void setListners() {
        llEvoucher.setOnClickListener(this);
        llEvents.setOnClickListener(this);
        llRewards.setOnClickListener(this);
        llFlashSale.setOnClickListener(this);
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
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            UtilsGlobal.HideKeyBoard(getDockActivity());
                            searchText = getTitleBar().getEditTextViewSearch(R.id.edt_search).getText().toString();
                            serviceHelper.enqueueCall(webService.eventSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.getHomeEventSearch);

                            return true;
                        }
                        return false;
                    }
                });
            }
        }
        );
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
                serviceHelper.enqueueCall(webService.eventSearch(searchText, prefHelper.getSignUpUser().getToken()), WebServiceConstants.getHomeEventSearch);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_Evoucher:
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), HomeFragment.class.getName());
                break;
            case R.id.ll_events:
                getDockActivity().replaceDockableFragment(EventsFragment.newInstance(), EventsFragment.class.getName());
                break;
            case R.id.ll_Rewards:
                getDockActivity().replaceDockableFragment(RewardsFragment.newInstance(), RewardsFragment.class.getName());
                break;
            case R.id.ll_flashSale:
                getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(), FlashSaleFragment.class.getName());
                break;
        }
    }


    @Override
    public void eVoucherLike(Integer entityId, boolean id) {
    }

    @Override
    public void eventsLike(Integer entityId, boolean status) {
        serviceHelper.enqueueCall(webService.eventLike(entityId, status ? 1 : 0, prefHelper.getSignUpUser().getToken()), WebServiceConstants.EVENTLIKE);
        eventsEnt.setEventLike(status ? 1 : 0);
    }

    @Override
    public void flashSaleLike(Integer id, boolean isChecked) {
    }
}
