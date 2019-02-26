package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.graphics.Paint;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.GiftReceivedEvent;
import com.app.usertreatzasia.fragments.CouponWaletDetailFragment;
import com.app.usertreatzasia.fragments.EventGiftReceivedDetailFragment;
import com.app.usertreatzasia.fragments.EventWalletDetailFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiftRecievedEventBinder extends ViewBinder<GiftReceivedEvent> {

    private DockActivity dockActivity;
    private ImageLoader imageLoader;
    private BasePreferenceHelper prefHelper;

    public GiftRecievedEventBinder(DockActivity dockActivity, BasePreferenceHelper preferenceHelper) {
        super(R.layout.item_gift_recieved_events);
        this.dockActivity = dockActivity;
        imageLoader = ImageLoader.getInstance();
        prefHelper = preferenceHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final GiftReceivedEvent entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();
        imageLoader = ImageLoader.getInstance();
        if(entity!=null){

            if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
                viewHolder.txtEventTitle.setText(entity.getEventDetail().getEventName() + "");
                Spanned desc = Html.fromHtml(entity.getEventDetail().getDescription());
                viewHolder.txtEventDetail.setText(desc.toString().trim());
            } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
                viewHolder.txtEventTitle.setText(entity.getEventDetail().getInEventName() + "");
                Spanned desc = Html.fromHtml(entity.getEventDetail().getInDescription());
                viewHolder.txtEventDetail.setText(desc.toString().trim());
            } else if (prefHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
                viewHolder.txtEventTitle.setText(entity.getEventDetail().getMaEventName() + "");
                Spanned desc = Html.fromHtml(entity.getEventDetail().getMaDescription());
                viewHolder.txtEventDetail.setText(desc.toString().trim());
            } else {
                viewHolder.txtEventTitle.setText(entity.getEventDetail().getEventName() + "");
                Spanned desc = Html.fromHtml(entity.getEventDetail().getDescription());
                viewHolder.txtEventDetail.setText(desc.toString().trim());
            }
            viewHolder.txtGiftByName.setText(entity.getGiftUserDetail().getFirstName());
            imageLoader.displayImage(entity.getEventDetail().getEventImage(),viewHolder.ivEventImage);
            // viewHolder.txtEventTitle.setText(entity.getEventName()+"");
            //  viewHolder.txtEventDetail.setText(entity.getDescription()+"");
            /*viewHolder.txtExpiresOn.setText(DateHelper.dateFormat(entity.getEventDetail().getStartTime(), AppConstants.DateFormat_Hours, AppConstants.DateFormat_HoursMinsSecs) + " " + UtilsGlobal.toTitleCase(entity.getEventDetail().getStartFormat()) + " "
                    + dockActivity.getResources().getString(R.string.to) + " " +
                    DateHelper.dateFormat(entity.getEventDetail().getEndTime(), AppConstants.DateFormat_Hours, AppConstants.DateFormat_HoursMinsSecs) + " " + UtilsGlobal.toTitleCase(entity.getEventDetail().getEndFormat()));*/



            viewHolder.txtExpiresOn.setText(DateHelper.dateFormat(entity.getEventDetail().getEndDate(),AppConstants.DateFormat_DMY,AppConstants.DateFormat_YMD)+"");
            viewHolder.txtAddress.setText(entity.getEventDetail().getVenue()+"");

            viewHolder.txtPaidFree.setText(UtilsGlobal.getVoucherType(entity.getEventDetail().getType(),dockActivity));
        }

        viewHolder.btnViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dockActivity.replaceDockableFragment(EventGiftReceivedDetailFragment.newInstance(entity), EventWalletDetailFragment.class.getName());
            }
        });

    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.iv_eventImage)
        ImageView ivEventImage;
        @BindView(R.id.txt_paid_free)
        AnyTextView txtPaidFree;
        @BindView(R.id.txt_EventTitle)
        AnyTextView txtEventTitle;
        @BindView(R.id.txtEventDetail)
        AnyTextView txtEventDetail;
        @BindView(R.id.txtGiftBy)
        AnyTextView txtGiftBy;
        @BindView(R.id.txtGiftByName)
        AnyTextView txtGiftByName;
        @BindView(R.id.txtExpiresOn)
        AnyTextView txtExpiresOn;
        @BindView(R.id.txtAddress)
        AnyTextView txtAddress;
        @BindView(R.id.btn_ViewDetail)
        Button btnViewDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
