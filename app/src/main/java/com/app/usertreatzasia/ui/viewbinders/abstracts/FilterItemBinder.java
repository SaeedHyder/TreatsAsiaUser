package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.FilterEnt;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.interfaces.CheckBoxes;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FilterItemBinder extends ViewBinder<FilterEnt> {

    private DockActivity dockActivity;
    private CheckBoxes checkBoxes;
    private boolean isAllchecked = false;
    private boolean otherCbChecked = false;
    private boolean isClearFromFragment = false;
    private int size = 0;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;

    public FilterItemBinder(DockActivity dockActivity, CheckBoxes checkBoxes, BasePreferenceHelper prefHelper) {

        super(R.layout.row_item_filter);
        this.dockActivity = dockActivity;
        this.checkBoxes = checkBoxes;
        this.prefHelper = prefHelper;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(final FilterEnt entity, final int position, int grpPosition, View view, Activity activity) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (prefHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            viewHolder.cbRestuarants.setText(entity.getTitle() + "");
        } else if (prefHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            viewHolder.cbRestuarants.setText(entity.getInTitle() + "");
        } else {
            viewHolder.cbRestuarants.setText(entity.getMaTitle() + "");
        }

      /*  if(isAllchecked){
            viewHolder.cbRestuarants.setChecked(true);
        }
        else
        { viewHolder.cbRestuarants.setChecked(false);}*/
        viewHolder.cbRestuarants.setOnCheckedChangeListener(null);
        if (isClearFromFragment) { //in case clear button click
            viewHolder.cbRestuarants.setChecked(false);
            entity.setIschecked(false);
            if (position == size)
                isClearFromFragment = false;
        }
        viewHolder.cbRestuarants.setChecked(entity.ischecked());

        /* else { //default case

            Log.e("weAreTeg", "bindView: " + "Here position = " + position + " status " + entity.ischecked());
            *//*if (entity.ischecked())
                viewHolder.cbRestuarants.setChecked(true);
            else
                viewHolder.cbRestuarants.setChecked(false);*//*
        }
*/
       /* if (isAllchecked) {
            viewHolder.cbRestuarants.setChecked(isAllchecked);
        }
        if (position == 0 && !otherCbChecked) {
            viewHolder.cbRestuarants.setChecked(otherCbChecked);
        }*/

       /* if(!otherCbChecked)
        {
            if(position==0)
            viewHolder.cbRestuarants.setChecked(false);
            else
                viewHolder.cbRestuarants.setChecked(true);
        }*/


        viewHolder.cbRestuarants.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
/*                Log.e("weAreTeg", "onCheckedChanged: " + "Here position = " + position + " status " + isChecked);
                if (position == 0) {
                    isAllchecked = isChecked;
                    otherCbChecked = isChecked;
                    checkBoxes.NotifyDataSet();

                } else {
                    otherCbChecked = isChecked;
                    checkBoxes.NotifyDataSet();
                }*/
                entity.setIschecked(isChecked);
            }
        });

    }

    public boolean isClearFromFragment() {
        return isClearFromFragment;
    }

    public void setClearFromFragment(boolean clearFromFragment, int size) {
        isClearFromFragment = clearFromFragment;
        this.size = size;
    }


    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.cb_Restuarants)
        CheckBox cbRestuarants;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
