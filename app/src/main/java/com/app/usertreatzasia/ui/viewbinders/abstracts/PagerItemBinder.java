package com.app.usertreatzasia.ui.viewbinders.abstracts;


import android.app.Activity;
import android.view.View;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.Subscriptionfeature;
import com.app.usertreatzasia.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagerItemBinder extends ViewBinder<Subscriptionfeature> {

    DockActivity dockActivity;
    String subscriptionFeatureString;

    public PagerItemBinder(DockActivity dockActivity) {
        super(R.layout.items_lv_bullets);
        this.dockActivity = dockActivity;
    }


    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new PagerItemBinder.ViewHolder(view);
    }

    @Override
    public void bindView(Subscriptionfeature entity, int position, int grpPosition, View view, Activity activity) {
        final PagerItemBinder.ViewHolder viewHolder = (PagerItemBinder.ViewHolder) view.getTag();
        int i;

            subscriptionFeatureString = entity.getFeatureDetail().getTitle();
            viewHolder.txtBullet2.setText(subscriptionFeatureString);
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_bullet_2)
        AnyTextView txtBullet2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
