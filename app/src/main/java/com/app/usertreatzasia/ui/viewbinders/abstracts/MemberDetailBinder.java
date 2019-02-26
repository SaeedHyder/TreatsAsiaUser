package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.view.View;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.EntityTierRecord;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;

public class MemberDetailBinder extends ViewBinder<EntityTierRecord> {

    private DockActivity dockActivity;
    private BasePreferenceHelper preferenceHelper;

    public MemberDetailBinder(DockActivity dockActivity, BasePreferenceHelper preferenceHelper) {
        super(R.layout.item_total_members);
        this.dockActivity = dockActivity;
        this.preferenceHelper = preferenceHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(EntityTierRecord entity, int position, int grpPosition, View view, Activity activity) {
        MemberDetailBinder.ViewHolder viewHolder = (MemberDetailBinder.ViewHolder) view.getTag();

        viewHolder.tv_user.setText(entity.getUser() + "");
        viewHolder.tv_amount.setText(preferenceHelper.getConvertedAmountCurrrency() + " " + entity.getAmount());
    }

    public static class ViewHolder extends BaseViewHolder {


        AnyTextView tv_user;
        AnyTextView tv_amount;

        public ViewHolder(View view) {

            tv_user = (AnyTextView) view.findViewById(R.id.tv_user);
            tv_amount = (AnyTextView) view.findViewById(R.id.tv_amount);
        }
    }
}
