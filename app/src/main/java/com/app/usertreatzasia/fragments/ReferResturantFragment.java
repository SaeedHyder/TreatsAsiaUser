package com.app.usertreatzasia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyEditTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.app.usertreatzasia.global.WebServiceConstants.ReferRestaurant;

public class ReferResturantFragment extends BaseFragment {
    @BindView(R.id.txtRestaurantName)
    AnyEditTextView txtRestaurantName;
    @BindView(R.id.txtWebsite)
    AnyEditTextView txtWebsite;
    @BindView(R.id.txtComment)
    AnyEditTextView txtComment;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;

    public static ReferResturantFragment newInstance() {
        Bundle args = new Bundle();

        ReferResturantFragment fragment = new ReferResturantFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refer_resturant, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listner();

    }

    private void listner() {

        txtComment.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    private boolean isvalidated() {
        if (txtRestaurantName.getText().toString().trim().isEmpty() || txtRestaurantName.getText().toString().trim().length() < 3) {
            txtRestaurantName.setError(getDockActivity().getResources().getString(R.string.enter_restaurant_name));
            if (txtRestaurantName.requestFocus()) {
                setEditTextFocus(txtRestaurantName);
            }
            return false;
        } else
            return true;

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showMenuButton();
        titleBar.setSubHeading(getDockActivity().getResources().getString(R.string.refer_restaurant));
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (isvalidated()) {
            serviceHelper.enqueueCall(webService.referRestaurant(txtRestaurantName.getText().toString(), txtWebsite.getText().toString(), txtComment.getText().toString(), prefHelper.getSignUpUser().getToken() + ""), ReferRestaurant);
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);
        switch (Tag) {
            case ReferRestaurant:
                UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.you_have_successfully) + txtRestaurantName.getText().toString());
                getDockActivity().popFragment();
                break;
        }
    }
}
