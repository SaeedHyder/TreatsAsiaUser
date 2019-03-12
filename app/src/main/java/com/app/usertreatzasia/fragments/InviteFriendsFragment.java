package com.app.usertreatzasia.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.ReferalCodeEntity;
import com.app.usertreatzasia.entities.SignupEntities;
import com.app.usertreatzasia.fragments.abstracts.BaseFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.UIHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.app.usertreatzasia.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class InviteFriendsFragment extends BaseFragment {
    @BindView(R.id.txt_message)
    AnyTextView txtMessage;
    @BindView(R.id.txt_code)
    AnyTextView txtCode;
    @BindView(R.id.btn_copy_code)
    AnyTextView btnCopyCode;
    @BindView(R.id.btn_invite)
    Button btnInvite;
    @BindView(R.id.btn_skip)
    Button btnSkip;
    String ReferalCode;
    String Name;

    public static InviteFriendsFragment newInstance() {
        return new InviteFriendsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invite_friends, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userProfileServiceToGetReferalCode();
    }

    private void userProfileServiceToGetReferalCode() {
        serviceHelper.enqueueCall(webService.getReferalCode(prefHelper.getSignUpUser().getToken()), WebServiceConstants.getUser);
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.invite_friends));
    }
    private void ShareMyCode(String message){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
       sendIntent.putExtra(Intent.EXTRA_TEXT, message);


        if (sendIntent.resolveActivity(getMainActivity().getPackageManager()) != null)
            startActivity(Intent.createChooser(sendIntent, getString(R.string.share_via)));
//        startActivity(sendIntent);
    }

    public void setReferalCode(ReferalCodeEntity result){

        txtCode.setText(result.getReferralCode());
        ReferalCode = result.getReferralCode();
        Name = result.getFirstName() + result.getLastName();
    }

    @OnClick({R.id.btn_copy_code, R.id.btn_invite, R.id.btn_skip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_copy_code:
                ClipboardManager clipboard = (ClipboardManager) getMainActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("invite", txtCode.getText().toString() + "");
                clipboard.setPrimaryClip(clip);
                UIHelper.showShortToastInCenter(getDockActivity(),getString(R.string.code_copied));
                break;
            case R.id.btn_invite:
                String shareBody = "Your friend " + Name +" has invited you to join TreatzAsia where you can enjoy amazing deals and discounts, events, rewards and much more. Use Referral Code " + ReferalCode +" on signup "



                        + "http://Treatzasia.com";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
                //ShareMyCode("");
                break;
            case R.id.btn_skip:
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(FlashSaleFragment.newInstance(),FlashSaleFragment.class.getSimpleName());
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag) {
        super.ResponseSuccess(result, Tag);

        switch (Tag) {
            case WebServiceConstants.getUser:
                setReferalCode((ReferalCodeEntity) result);
                break;
        }
    }
}
