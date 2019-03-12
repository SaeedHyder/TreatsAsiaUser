package com.app.usertreatzasia.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.AlphabetItem;
import com.app.usertreatzasia.entities.FilterArrayEnt;
import com.app.usertreatzasia.entities.GetUsersEnt;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.interfaces.HeaderNameInterface;
import com.app.usertreatzasia.interfaces.RecyclerViewOnClick;
import com.app.usertreatzasia.ui.adapters.FastScrollAdapter;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;

public class GiftRecipientsFragment extends BaseFragment implements HeaderNameInterface, RecyclerViewOnClick {


    @BindView(R.id.edt_enter_keyword)
    AnyEditTextView edtEnterKeyword;

    @BindView(R.id.fast_scroller_recycler)
    IndexFastScrollRecyclerView fastScrollerRecycler;

    @BindView(R.id.tv_header_text)
    AnyTextView tv_header_text;

    private static String IS_VOUCHER_WALLET_FRAGMENT = "voucherFragment";
    private static String IS_VOUCHER_GIFT_RECIEVED_FRAGMENT = "voucherGiftRecievedFragment";
    private static String IS_EVENT_FRAGMENT = "eventFragment";
    private static String IS_EVENT_GIFT_RECIEVED_FRAGMENT = "eventGiftRecievedFragment";

    private static String getIsVoucherWalletFragment;
    private static String getIsVoucherGiftRecievedFragment;
    private static String getIsEventFragment;
    private static String getIsEventGiftRecievedFragment;
    Unbinder unbinder;

    private List<FilterArrayEnt> mDataArray;
    private ArrayList<FilterArrayEnt> mDataArray2;
    private List<AlphabetItem> mAlphabetItems;
    private List<GetUsersEnt> totalMerchants;

    public static GiftRecipientsFragment newInstance(String imComingFromEvoucherWallet) {
        Bundle args = new Bundle();
        if (imComingFromEvoucherWallet.equalsIgnoreCase("ImComingFromEvoucherWallet")) {
            args.putString(IS_VOUCHER_WALLET_FRAGMENT, imComingFromEvoucherWallet);
        } else if (imComingFromEvoucherWallet.equalsIgnoreCase("ImComingFromEvoucherGfitRecieved")) {
            args.putString(IS_VOUCHER_GIFT_RECIEVED_FRAGMENT, imComingFromEvoucherWallet);
        } else if (imComingFromEvoucherWallet.equalsIgnoreCase("ImComingFromEventWallet")) {
            args.putString(IS_EVENT_FRAGMENT, imComingFromEvoucherWallet);
        } else if (imComingFromEvoucherWallet.equalsIgnoreCase("ImComingFromEventGiftRecieved")) {
            args.putString(IS_EVENT_GIFT_RECIEVED_FRAGMENT, imComingFromEvoucherWallet);
        }
        //args.putString(ISHOMEFRAGMENT, isHomeFragment);
        GiftRecipientsFragment fragment = new GiftRecipientsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            getIsVoucherWalletFragment = getArguments().getString(IS_VOUCHER_WALLET_FRAGMENT);
            getIsVoucherGiftRecievedFragment = getArguments().getString(IS_VOUCHER_GIFT_RECIEVED_FRAGMENT);
            getIsEventFragment = getArguments().getString(IS_EVENT_FRAGMENT);
            getIsEventGiftRecievedFragment = getArguments().getString(IS_EVENT_GIFT_RECIEVED_FRAGMENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gift_recipients, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMerchantsData();
        searchingName();
    }

    private void searchingName() {

        edtEnterKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                localSearch(getSearchedArray(s.toString()));
            }
        });
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading("Select Recipient");
    }

    protected void initialiseData() {
        //Recycler view data
        // mDataArray =DataHelper.getAlphabetData();

        //Alphabet fast scroller data
        if (mDataArray != null) {
            mAlphabetItems = new ArrayList<>();
            List<String> strAlphabets = new ArrayList<>();
            for (int i = 0; i < mDataArray.size(); i++) {
                String name = mDataArray.get(i).getName();
                if (name == null || name.trim().isEmpty())
                    continue;

                String word = name.substring(0, 1);
                if (!strAlphabets.contains(word)) {
                    strAlphabets.add(word);
                    mAlphabetItems.add(new AlphabetItem(i, word, false));
                }
            }
        }
    }

    protected void initialiseUI() {
        fastScrollerRecycler.setLayoutManager(new LinearLayoutManager(getDockActivity()));
        fastScrollerRecycler.setAdapter(new FastScrollAdapter(mDataArray, getDockActivity(), this, this));

        fastScrollerRecycler.setIndexTextSize(12);
        fastScrollerRecycler.setIndexBarColor("#000000");
        fastScrollerRecycler.setIndexBarCornerRadius(3);
        fastScrollerRecycler.setIndexBarTransparentValue((float) 0);
        fastScrollerRecycler.setIndexbarMargin(0);
        fastScrollerRecycler.setIndexbarWidth(35);
        fastScrollerRecycler.setPreviewPadding(5);
        fastScrollerRecycler.setIndexBarTextColor("#FFFFFF");

        fastScrollerRecycler.setIndexBarVisibility(true);
        fastScrollerRecycler.setIndexbarHighLateTextColor("#000000");
        fastScrollerRecycler.setIndexBarHighLateTextVisibility(true);
    }

    public ArrayList<FilterArrayEnt> getSearchedArray(String keyword) {
        //mDataArray = DataHelper.getAlphabetData();
        if (mDataArray != null && mDataArray.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<FilterArrayEnt> arrayList = new ArrayList<>();
        if (mDataArray != null) {
            for (FilterArrayEnt item : mDataArray) {
                String UserName = "";
                if (item != null) {
                    UserName = item.getName();
                }
                if (Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(UserName).find()) {
                    /*UserName.contains(keyword)*/
                    arrayList.add(new FilterArrayEnt(item.getName(), item.getId()));

                }
            }
        }
        return arrayList;

    }

    private void localSearch(ArrayList<FilterArrayEnt> data) {

        mDataArray2 = data;

        //Alphabet fast scroller data
        mAlphabetItems = new ArrayList<>();
        List<String> strAlphabets = new ArrayList<>();
        for (int i = 0; i < mDataArray2.size(); i++) {
            String name = mDataArray2.get(i).getName();
            if (name == null || name.trim().isEmpty())
                continue;

            String word = name.substring(0, 1);
            if (!strAlphabets.contains(word)) {
                strAlphabets.add(word);
                mAlphabetItems.add(new AlphabetItem(i, word, false));
            }
        }

        fastScrollerRecycler.setAdapter(new FastScrollAdapter(mDataArray2, getDockActivity(), this, this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void getHeaderName(String alpha) {
        tv_header_text.setText(alpha);
    }

    private void getMerchantsData() {
        serviceHelper.enqueueCall(webService.getGiftUsers(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getGiftUsers);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.getGiftUsers:
                mDataArray = new ArrayList<>();
                totalMerchants = (ArrayList<GetUsersEnt>) result;
                Collections.sort(totalMerchants, new Comparator<GetUsersEnt>() {
                    @Override
                    public int compare(GetUsersEnt getMerchantsEnt, GetUsersEnt t1) {
                        if (getMerchantsEnt.getFirstName() != null && t1.getFirstName() != null) {
                            return getMerchantsEnt.getFirstName().toLowerCase().trim().compareTo(t1.getFirstName().toLowerCase().trim());
                        }
                        return 1;
                    }
                });
                for (GetUsersEnt item : totalMerchants) {
                    if (item.getFirstName()!=null && !item.getFirstName().equals(""))
                        mDataArray.add(new FilterArrayEnt(item.getFirstName(), item.getId()));
                }
                initialiseData();
                initialiseUI();
                break;
        }
    }

    @Override
    public void click(int position, List<FilterArrayEnt> mDataArray) {


        if (getIsVoucherWalletFragment != null) {
            if (getIsVoucherWalletFragment.equalsIgnoreCase("ImComingFromEvoucherWallet")) {
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(mDataArray.get(position).getId(), mDataArray.get(position).getName()), "CouponWaletDetailFragment");
            }
        } else if (getIsVoucherGiftRecievedFragment != null) {
            if (getIsVoucherGiftRecievedFragment.equalsIgnoreCase("ImComingFromEvoucherWallet")) {
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(HomeWalletFragment.newInstance(mDataArray.get(position).getId(), mDataArray.get(position).getName()), "HomeWalletFragment");
            }
        } else if (getIsEventFragment != null) {
            if (getIsEventFragment.equalsIgnoreCase("ImComingFromEventWallet")) {
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(EventsWalletFragment.newInstance(mDataArray.get(position).getId(), mDataArray.get(position).getName()), "EventsWalletFragment");
            }
        } else if (getIsEventGiftRecievedFragment != null) {
            if (getIsEventGiftRecievedFragment.equalsIgnoreCase("ImComingFromEventGiftRecieved")) {
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(EventsWalletFragment.newInstance(mDataArray.get(position).getId(), mDataArray.get(position).getName()), "EventsWalletFragment");
            }
        } else {
            UIHelper.showShortToastInCenter(getDockActivity(), "Sorry for inconvenience. Issue is being resolved");
        }

    }
}
