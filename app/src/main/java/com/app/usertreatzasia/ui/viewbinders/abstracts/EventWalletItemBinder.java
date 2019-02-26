package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.WalletEventEntity;
import com.app.usertreatzasia.fragments.EventWalletDetailFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.interfaces.FavoriteStatus;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventWalletItemBinder extends ViewBinder<WalletEventEntity> {

    DockActivity dockActivity;
    ImageLoader imageLoader;
    BasePreferenceHelper prefHelper;
    private FavoriteStatus favoriteStatus;

    public EventWalletItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, FavoriteStatus favoriteStatus) {
        super(R.layout.row_item_events_wallet);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.favoriteStatus = favoriteStatus;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new EventWalletItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(final WalletEventEntity entity, int position, int grpPosition, View view, Activity activity) {

        final EventWalletItemBinder.ViewHolder viewHolder = (EventWalletItemBinder.ViewHolder) view.getTag();
        imageLoader = ImageLoader.getInstance();
        if (entity != null) {
            viewHolder.iv_favorite.setEnabled(false);
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
            viewHolder.txtPointsAmount.setText(entity.getEventDetail().getPoint());
            imageLoader.displayImage(entity.getEventDetail().getEventImage(), viewHolder.ivEventImage);
            // viewHolder.txtEventTitle.setText(entity.getEventName()+"");
            //  viewHolder.txtEventDetail.setText(entity.getDescription()+"");
            viewHolder.txtExpiresOn.setText(DateHelper.dateFormat(entity.getEventDetail().getEndDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");
            viewHolder.txtAddress.setText(entity.getEventDetail().getVenue() + "");

            viewHolder.txtPaidFree.setText(UtilsGlobal.getVoucherType(entity.getEventDetail().getType(), dockActivity));

            if (entity.getEventDetail().getEventLike() == 1) {
                viewHolder.iv_favorite.setChecked(true);
            } else
                viewHolder.iv_favorite.setChecked(false);
            view.setVisibility(View.VISIBLE);
        }

        viewHolder.btnViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dockActivity.replaceDockableFragment(EventWalletDetailFragment.newInstance(entity.getEventDetail()), EventWalletDetailFragment.class.getName());
            }
        });
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_eventImage)
        ImageView ivEventImage;
        @BindView(R.id.txt_paid_free)
        AnyTextView txtPaidFree;
        @BindView(R.id.txt_EventTitle)
        AnyTextView txtEventTitle;
        @BindView(R.id.iv_favorite)
        CheckBox iv_favorite;
        @BindView(R.id.txtEventDetail)
        AnyTextView txtEventDetail;
        @BindView(R.id.txtExpiresOn)
        AnyTextView txtExpiresOn;
        @BindView(R.id.txtAddress)
        AnyTextView txtAddress;
        @BindView(R.id.btn_ViewDetail)
        Button btnViewDetail;
        @BindView(R.id.txtPointsAmount)
        AnyTextView txtPointsAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
