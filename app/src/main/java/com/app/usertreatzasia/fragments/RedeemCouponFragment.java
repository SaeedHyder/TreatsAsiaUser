package com.app.usertreatzasia.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RedeemCouponFragment extends BaseFragment {
    @BindView(R.id.iv_couponImage)
    ImageView ivCouponImage;
    @BindView(R.id.txt_paid_type)
    AnyTextView txtPaidType;
    @BindView(R.id.txt_couponDetail)
    AnyTextView txtCouponDetail;
    @BindView(R.id.txtActualAmount)
    AnyTextView txtActualAmount;
    @BindView(R.id.txtAfterDiscount)
    AnyTextView txtAfterDiscount;
    @BindView(R.id.txt_expire_time)
    AnyTextView txtExpireTime;
    @BindView(R.id.txt_address)
    AnyTextView txtAddress;
    @BindView(R.id.txt_remaining_qty)
    AnyTextView txtRemainingQty;
    @BindView(R.id.img_barcode)
    ImageView imgBarcode;
    @BindView(R.id.txt_termsDetails)
    AnyTextView txtTermsDetails;
    private int ID;
    private String Type;
    public void setContent(int id, String type) {
        setID(id);
        setType(type);
        //  return this;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setType(String type) {
        Type = type;
    }
    public static RedeemCouponFragment newInstance() {
        return new RedeemCouponFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_redeem_coupon, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtActualAmount.setPaintFlags(txtActualAmount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showShareButtonEnd(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = getResources().getString(R.string.share_msg);
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
            }
        });
    }

}
