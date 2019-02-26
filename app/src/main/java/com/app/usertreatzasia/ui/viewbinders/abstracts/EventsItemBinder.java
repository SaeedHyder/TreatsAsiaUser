package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.EventsEnt;
import com.app.usertreatzasia.fragments.EventDetailFragment;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.UtilsGlobal;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.interfaces.FavoriteStatus;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventsItemBinder extends ViewBinder<EventsEnt> {

    DockActivity dockActivity;
    ImageLoader imageLoader;
    BasePreferenceHelper prefHelper;
    private FavoriteStatus favoriteStatus;
    Float updatedCash;

    public EventsItemBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, FavoriteStatus favoriteStatus) {
        super(R.layout.row_item_events);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.favoriteStatus = favoriteStatus;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final EventsEnt entity, int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        imageLoader = ImageLoader.getInstance();
        if (entity != null) {
            if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
                viewHolder.txtEventTitle.setText(entity.getEventName());
                Spanned desc = Html.fromHtml(entity.getDescription());
                viewHolder.txtEventDetail.setText(desc.toString().trim());
            } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
                viewHolder.txtEventTitle.setText(entity.getInEventName() + "");
                Spanned desc = Html.fromHtml(entity.getInDescription());
                viewHolder.txtEventDetail.setText(desc.toString().trim());
            } else {
                viewHolder.txtEventTitle.setText(entity.getMaEventName() + "");
                Spanned desc = Html.fromHtml(entity.getMaDescription());
                viewHolder.txtEventDetail.setText(desc.toString().trim());
            }

            imageLoader.displayImage(entity.getEventImage(), viewHolder.ivEventImage);
            viewHolder.txtExpiresOn.setText(DateHelper.dateFormat(entity.getEndDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");
            viewHolder.txtAddress.setText(entity.getVenue() + "");
            viewHolder.txtPaidFree.setText(UtilsGlobal.getVoucherType(entity.getType(), dockActivity));
            prefHelper.setEventLike(entity.getEventLike());

            if (entity.getEventLike() == 1) {
                viewHolder.ivFavorite.setChecked(true);
            } else
                viewHolder.ivFavorite.setChecked(false);
        }

/*        viewHolder.btnViewDetail.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                EventDetailFragment fragment = EventDetailFragment.newInstance();
                fragment.setContent(entity.getId(), entity.getEventLike());
                dockActivity.replaceDockableFragment(fragment,
                        EventDetailFragment.class.getName());
            }
        });*/

        viewHolder.btnViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dockActivity.replaceDockableFragment(EventDetailFragment.newInstance(entity.getId(), entity.getEventLike()), "EventDetailFragment");
            }
        });

        viewHolder.ivFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                favoriteStatus.eventsLike(entity.getId(), isChecked);
            }
        });

        if (entity.getIsPoint() == 1 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 0 && entity.getIsFree() == 0) {
            // only points
            viewHolder.llPoints.setVisibility(View.VISIBLE);
            viewHolder.txtPoints.setText(entity.getPoint());

            viewHolder.llCredits.setVisibility(View.GONE);
            viewHolder.llCash.setVisibility(View.GONE);
            viewHolder.llFree.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 0 && entity.getIsFree() == 0) {
            // only credit
            viewHolder.llCredits.setVisibility(View.VISIBLE);
            viewHolder.txtCredits.setText(entity.getPoint());

            viewHolder.llPoints.setVisibility(View.GONE);
            viewHolder.llCash.setVisibility(View.GONE);
            viewHolder.llFree.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // only cash
            viewHolder.llCash.setVisibility(View.VISIBLE);


            updatedCash = Float.valueOf(entity.getPoint()) * prefHelper.getConvertedAmount();
            String formattedValue = String.format("%.2f", updatedCash);
            viewHolder.txtCash.setText(formattedValue + "");
            viewHolder.currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());


            viewHolder.llPoints.setVisibility(View.GONE);
            viewHolder.llCredits.setVisibility(View.GONE);
            viewHolder.llFree.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 0 && entity.getIsFree() == 1) {

            viewHolder.llFree.setVisibility(View.VISIBLE);

            viewHolder.llPoints.setVisibility(View.GONE);
            viewHolder.llCredits.setVisibility(View.GONE);
            viewHolder.llCash.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 1 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 0 && entity.getIsFree() == 0) {
            // only point and credit
            viewHolder.llPoints.setVisibility(View.VISIBLE);
            viewHolder.txtPoints.setText(entity.getPoint());
            viewHolder.llCredits.setVisibility(View.VISIBLE);
            viewHolder.txtCredits.setText(entity.getPoint());

            viewHolder.llFree.setVisibility(View.GONE);
            viewHolder.llCash.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 1 && entity.getIsCredit() == 0 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // only point and cash
            viewHolder.llPoints.setVisibility(View.VISIBLE);
            viewHolder.txtPoints.setText(entity.getPoint());
            viewHolder.llCash.setVisibility(View.VISIBLE);
            viewHolder.txtCash.setText(entity.getPoint());

            viewHolder.llFree.setVisibility(View.GONE);
            viewHolder.llCredits.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 0 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // only credit and cash
            viewHolder.llCredits.setVisibility(View.VISIBLE);
            viewHolder.txtCredits.setText(entity.getPoint());
            viewHolder.llCash.setVisibility(View.VISIBLE);
            viewHolder.txtCash.setText(entity.getPoint());

            viewHolder.llFree.setVisibility(View.GONE);
            viewHolder.llPoints.setVisibility(View.GONE);

        } else if (entity.getIsPoint() == 1 && entity.getIsCredit() == 1 && entity.getIsPaypal() == 1 && entity.getIsFree() == 0) {
            // all type accepted
            viewHolder.llCredits.setVisibility(View.VISIBLE);
            viewHolder.txtCredits.setText(entity.getPoint());
            viewHolder.llPoints.setVisibility(View.VISIBLE);
            viewHolder.txtPoints.setText(entity.getPoint());
            viewHolder.llCash.setVisibility(View.VISIBLE);
            viewHolder.txtCash.setText(entity.getPoint());

            viewHolder.llFree.setVisibility(View.GONE);
        }
        updatedCash = Float.valueOf(entity.getPoint()) * prefHelper.getConvertedAmount();
        String formattedValue = String.format("%.2f", updatedCash);
        viewHolder.txtCash.setText(formattedValue + "");
        viewHolder.currencyTypeCash.setText(prefHelper.getConvertedAmountCurrrency());
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.iv_eventImage)
        ImageView ivEventImage;
        @BindView(R.id.txt_paid_free)
        AnyTextView txtPaidFree;
        @BindView(R.id.txt_EventTitle)
        AnyTextView txtEventTitle;
        @BindView(R.id.txtPoints)
        AnyTextView txtPoints;
        @BindView(R.id.ll_points)
        LinearLayout llPoints;
        @BindView(R.id.txtCredits)
        AnyTextView txtCredits;
        @BindView(R.id.ll_credits)
        LinearLayout llCredits;
        @BindView(R.id.txtCash)
        AnyTextView txtCash;
        @BindView(R.id.ll_cash)
        LinearLayout llCash;
        @BindView(R.id.ll_free)
        LinearLayout llFree;
        @BindView(R.id.iv_favorite)
        CheckBox ivFavorite;
        @BindView(R.id.txtEventDetail)
        AnyTextView txtEventDetail;
        @BindView(R.id.txtExpiresOn)
        AnyTextView txtExpiresOn;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtAddress)
        AnyTextView txtAddress;
        @BindView(R.id.btn_ViewDetail)
        Button btnViewDetail;
        @BindView(R.id.currency_type_cash)
        AnyTextView currencyTypeCash;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
