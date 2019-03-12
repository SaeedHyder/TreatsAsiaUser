package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.graphics.Paint;
import android.text.Html;
import android.view.View;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.HomeEnt;
import com.app.usertreatzasia.entities.PurchaseEnt;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchaseItemBinder extends ViewBinder<PurchaseEnt> {

    public PurchaseItemBinder() {
        super(R.layout.row_item_purchase);
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);

    }

    @Override
    public void bindView(PurchaseEnt entity, int position, int grpPosition, View view, Activity activity) {

        final PurchaseItemBinder.ViewHolder viewHolder = (PurchaseItemBinder.ViewHolder) view.getTag();

        viewHolder.txtTicketId.setText("#"+entity.getId());
        viewHolder.txtEvent.setText(entity.getEventDetail().getEventName()+"");
        viewHolder.txtDate.setText(DateHelper.dateFormat(entity.getEventDetail().getStartDate(), AppConstants.DateFormat_DMY, AppConstants.DateFormat_YMD) + "");
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_ticketId)
        AnyTextView txtTicketId;
        @BindView(R.id.txt_event)
        AnyTextView txtEvent;
        @BindView(R.id.txt_date)
        AnyTextView txtDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
