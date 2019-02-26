package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.interfaces.SideMenuItemClickListener;
import com.app.usertreatzasia.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SidemenuBinder extends RecyclerViewBinder<String> {
    private int selectedPosition = 0;
    private SideMenuItemClickListener listener;

    public SidemenuBinder(SideMenuItemClickListener listener) {
        super(R.layout.row_item_side_menu);
        this.listener = listener;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final String entity, final int position, Object viewHolder, Context context) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if (position == selectedPosition) {
            holder.title.setTextColor(holder.title.getContext().getResources().getColor(R.color.text_color_gold));
            holder.title.setAlpha(1f);
            holder.title.setTypeface(null, Typeface.BOLD);
        } else {
            holder.title.setTextColor(holder.title.getContext().getResources().getColor(R.color.white));
            holder.title.setAlpha(0.7f);
            holder.title.setTypeface(null, Typeface.NORMAL);
        }
        holder.title.setText(entity + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onMenuItemClicked(position, selectedPosition, entity);
                    setSelectedPosition(position);
                }
            }
        });
        if (position == 4) {
            if (holder.notificationCount > 0) {
                holder.txt_badge_notifications.setVisibility(View.VISIBLE);
                holder.txt_badge_notifications.setText("8");
            }
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.title)
        AnyTextView title;

        @BindView(R.id.txt_badge_notifications)
        AnyTextView txt_badge_notifications;

        int notificationCount = 1;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
