package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.EntityTierRecord;
import com.app.usertreatzasia.entities.EntityTierRecordParent;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.adapters.ArrayListAdapter;
import com.app.usertreatzasia.ui.viewbinders.abstracts.MemberDetailBinder;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.ExpandedListView;
import com.app.usertreatzasia.ui.views.TitleBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MemberDetailFragment extends BaseFragment {


    @BindView(R.id.tv_date)
    AnyTextView tvDate;
    @BindView(R.id.lv_total_members)
    ExpandedListView lvTotalMembers;
    private static String headerTitle = "";
    private static String serviceTier = "";
    String dateName;
    String dateMonth;
    String dateYear;
    Unbinder unbinder;
    @BindView(R.id.tv_total_amount)
    AnyTextView tvTotalAmount;
    @BindView(R.id.sp_months)
    Spinner spMonths;
    private ArrayListAdapter<EntityTierRecord> adapter;
    private ArrayList<String> months = new ArrayList<>();

    private boolean spinner = false;

    public static MemberDetailFragment newInstance(String isTier, String tier2) {
        headerTitle = isTier;
        serviceTier = tier2;
        return new MemberDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayListAdapter<>(getDockActivity(), new MemberDetailBinder(getDockActivity(), prefHelper));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMonths();
        setDate();
        getSpinnerValue();
        lvTotalMembers.setOnTouchListener(null);
        lvTotalMembers.setScrollContainer(false);
        lvTotalMembers.setExpanded(true);
        spinner = false;
    }

    private void setDate() {

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String dateToStr = format.format(today);
        dateName = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_MY, "yyyy-MM-dd hh:mm:ss a");
        dateMonth = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_M, "yyyy-MM-dd hh:mm:ss a");
        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
        tvDate.setText(dateName + "");
        hitTierService();
    }

    private void hitTierService() {
        if (prefHelper != null && prefHelper.getSignUpUser() != null && prefHelper.getSignUpUser().getToken() != null)
            spinner = true;
        serviceHelper.enqueueCall(webService.getTierRecord(serviceTier, dateMonth, dateYear, prefHelper.getSignUpUser().getToken()), WebServiceConstants.getTierRecord);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.getTierRecord:
                EntityTierRecordParent entityTierRecordParent = (EntityTierRecordParent) result;
                bindData((ArrayList<EntityTierRecord>) entityTierRecordParent.getRecord(), entityTierRecordParent.getTotal());
                break;
        }
    }

    public void bindData(ArrayList<EntityTierRecord> record, Integer total) {
        if (total == 0) {
            tvTotalAmount.setText(prefHelper.getConvertedAmountCurrrency() + " 0");
        } else {
            tvTotalAmount.setText(prefHelper.getConvertedAmountCurrrency() + " " + total);
        }
        adapter.clearList();
        lvTotalMembers.setAdapter(adapter);
        adapter.addAll(record);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(headerTitle + "");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.tv_date)
    public void onViewClicked() {
        spMonths.performClick();
        spinner = true;
    }

    private void setMonths() {

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        String dateToStr = format.format(today);
        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");

        months = new ArrayList<>();
        months.add("January " + dateYear);
        months.add("February " + dateYear);
        months.add("March " + dateYear);
        months.add("April " + dateYear);
        months.add("May " + dateYear);
        months.add("June " + dateYear);
        months.add("July " + dateYear);
        months.add("August " + dateYear);
        months.add("September " + dateYear);
        months.add("October " + dateYear);
        months.add("November " + dateYear);
        months.add("December " + dateYear);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getDockActivity(), R.layout.spinner_item_2, months);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_2);
        spMonths.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getSpinnerValue() {

        spMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner) {
                    if (position == 0) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("January " + dateYear);
                        dateMonth.equalsIgnoreCase("1");
                        dateMonth = "1";
                        hitTierService();
                    } else if (position == 1) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("February " + dateYear);
                        dateMonth.equalsIgnoreCase("2");
                        dateMonth = "2";
                        hitTierService();
                    } else if (position == 2) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("March " + dateYear);
                        dateMonth.equalsIgnoreCase("3");
                        dateMonth = "3";
                        hitTierService();
                    } else if (position == 3) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("April " + dateYear);
                        dateMonth.equalsIgnoreCase("4");
                        dateMonth = "4";
                        hitTierService();
                    } else if (position == 4) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("May " + dateYear);
                        dateMonth.equalsIgnoreCase("5");
                        dateMonth = "5";
                        hitTierService();
                    } else if (position == 5) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("June " + dateYear);
                        dateMonth.equalsIgnoreCase("6");
                        dateMonth = "6";
                        hitTierService();
                    } else if (position == 6) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("July " + dateYear);
                        dateMonth.equalsIgnoreCase("7");
                        dateMonth = "7";
                        hitTierService();
                    } else if (position == 7) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("August " + dateYear);
                        dateMonth.equalsIgnoreCase("8");
                        dateMonth = "8";
                        hitTierService();
                    } else if (position == 8) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("September " + dateYear);
                        dateMonth.equalsIgnoreCase("9");
                        dateMonth = "9";
                        hitTierService();
                    } else if (position == 9) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("October " + dateYear);
                        dateMonth.equalsIgnoreCase("10");
                        dateMonth = "10";
                        hitTierService();
                    } else if (position == 10) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("November " + dateYear);
                        dateMonth.equalsIgnoreCase("11");
                        dateMonth = "11";
                        hitTierService();
                    } else if (position == 11) {
                        Date today = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
                        String dateToStr = format.format(today);
                        dateYear = DateHelper.dateFormat(dateToStr, AppConstants.DateFormat_Y, "yyyy-MM-dd hh:mm:ss a");
                        tvDate.setText("December " + dateYear);
                        dateMonth.equalsIgnoreCase("12");
                        dateMonth = "12";
                        hitTierService();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
