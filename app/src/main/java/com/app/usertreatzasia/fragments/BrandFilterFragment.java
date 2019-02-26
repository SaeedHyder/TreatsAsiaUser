package com.app.usertreatzasia.fragments;

import android.graphics.Typeface;
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
import com.app.usertreatzasia.entities.GetMerchantsEnt;
import com.app.usertreatzasia.entities.WalletEntity;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.interfaces.HeaderNameInterface;
import com.app.usertreatzasia.interfaces.RecyclerViewOnClick;
import com.app.usertreatzasia.ui.adapters.FastScrollAdapter;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;


public class BrandFilterFragment extends BaseFragment implements HeaderNameInterface,RecyclerViewOnClick {


    @BindView(R.id.edt_enter_points)
    AnyEditTextView edtEnterPoints;

    @BindView(R.id.tv_current_credits)
    AnyTextView tv_current_credits;

    @BindView(R.id.tv_points_available)
    AnyTextView tv_points_available;

    @BindView(R.id.tv_header_text)
    AnyTextView tv_header_text;

    @BindView(R.id.fast_scroller_recycler)
    IndexFastScrollRecyclerView fastScrollerRecycler;


    String stringMerchantIDs = "";

    Unbinder unbinder;

    private ArrayList<FilterArrayEnt> mDataArray;
    private ArrayList<FilterArrayEnt> mDataArray2;

    private List<AlphabetItem> mAlphabetItems;
    private List<GetMerchantsEnt> totalMerchants;
    private static boolean ISHOMEFRAGMENT;

    public static BrandFilterFragment newInstance(boolean isHomeFragment) {
        ISHOMEFRAGMENT = isHomeFragment;
        return new BrandFilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand_filter, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMerchantsData();
        searchingName();
        tv_current_credits.setTypeface(Typeface.DEFAULT_BOLD);
        tv_points_available.setTypeface(Typeface.DEFAULT_BOLD);
        getWalletData();
    }

    private void searchingName() {

        edtEnterPoints.addTextChangedListener(new TextWatcher() {
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
        titleBar.setSubHeading("Brand Filter");
    }

    private void getWalletData() {
        serviceHelper.enqueueCall(webService.getWalletData(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getWallet);
    }

    protected void initialiseData() {
        //Recycler view data
        //mDataArray = DataHelper.getAlphabetData();


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
        fastScrollerRecycler.setAdapter(new FastScrollAdapter(mDataArray, getDockActivity(),this,this));

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
                    arrayList.add(new FilterArrayEnt(item.getName(),item.getId()));

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

        fastScrollerRecycler.setAdapter(new FastScrollAdapter(mDataArray2, getDockActivity(),this,this));
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
        serviceHelper.enqueueCall(webService.getMerchants(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getMerchants);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.getMerchants:
                mDataArray = new ArrayList<>();
                totalMerchants = (ArrayList<GetMerchantsEnt>) result;
                if (totalMerchants.size()>0) {
                    tv_points_available.setText(totalMerchants.size() + "");
                } else {
                    tv_points_available.setText("0");
                }
                for (GetMerchantsEnt item : totalMerchants) {
                    mDataArray.add(new FilterArrayEnt(item.getFirstName(),item.getId()));
                }
                initialiseData();
                initialiseUI();
                break;

            case WebServiceConstants.getWallet:
                setWalletData((WalletEntity) result);
                break;
        }
    }

    private void setWalletData(WalletEntity result) {
        //tv_points_available.setText(result.getPoint());
    }

    @OnClick(R.id.fast_scroller_recycler)
    public void onViewClicked() {

    }

    @Override
    public void click(int position, List<FilterArrayEnt> mDataArray) {

        if (ISHOMEFRAGMENT) {
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(mDataArray.get(position).getId()), "HomeFragment");
        } else {
            getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(mDataArray.get(position).getId()), "HomeWalletFragment");
        }
    }
}
