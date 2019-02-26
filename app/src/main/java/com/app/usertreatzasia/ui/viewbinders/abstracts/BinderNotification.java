package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.entities.NotificationEnt;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BinderNotification extends ViewBinder<NotificationEnt> {

    private Context context;
    private ImageLoader imageLoader;
    private BasePreferenceHelper prefHelper;
    private String timeDate;
    private String splittedDate;
    private static String splittedTime;

    public BinderNotification(Context context, BasePreferenceHelper preferenceHelper) {
        super(R.layout.fragment_notification_item);
        this.context = context;
        imageLoader = ImageLoader.getInstance();
        prefHelper = preferenceHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new BinderNotification.ViewHolder(view);
    }

    @Override
    public void bindView(NotificationEnt entity, int position, int grpPosition, View view, Activity activity) {

        BinderNotification.ViewHolder viewHolder = (BinderNotification.ViewHolder) view.getTag();

        // imageLoader.displayImage(entity.getImagePath(), viewHolder.ivNotification);

        timeDate = DateHelper.getLocalTimeDate(entity.getCreatedAt());

        String[] splited = timeDate.split("\\s");
        splittedDate = splited[0];
        splittedTime = splited[1];

        viewHolder.tv_date.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormat_DMY2,
                entity.getCreatedAt()));

        try {
            String _24HourTime = splittedTime;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            /*System.out.println(_24HourDt);
            System.out.println(_12HourSDF.format(_24HourDt));*/
            viewHolder.tv_time.setText(_12HourSDF.format(_24HourDt));
        } catch (Exception e) {
            e.printStackTrace();
        }



        switch (prefHelper.getSelectedLanguage()) {
            case AppConstants.ENGLISH:
                viewHolder.tv_msg.setText(entity.getMessage());
                break;
            case AppConstants.MALAYSIAN:
                viewHolder.tv_msg.setText(entity.getMaMessage());
                break;
            case AppConstants.INDONESIAN:
                viewHolder.tv_msg.setText(entity.getInMessage());
                break;
            default:
                viewHolder.tv_msg.setText(entity.getMessage());
                break;
        }
        /*if(entity.getCreatedAt() != null && entity.getCreatedAt().length() > 0) {
            viewHolder.tv_date.setText(DateHelper.dateFormat(entity.getCreatedAt(), DateHelper.DATE_FORMAT, DateHelper.DATE_TIME_FORMAT));
            viewHolder.tv_time.setText(DateHelper.dateFormat(entity.getCreatedAt(), DateHelper.TIME_FORMAT, DateHelper.DATE_TIME_FORMAT));
        }*/
    }

    public static class ViewHolder extends BaseViewHolder {

        ImageView ivNotification;
        AnyTextView tv_msg;
        AnyTextView tv_date;
        AnyTextView tv_time;

        public ViewHolder(View view) {

            ivNotification = (ImageView) view.findViewById(R.id.ivNotification);
            tv_msg = (AnyTextView) view.findViewById(R.id.tv_msg);
            tv_date = (AnyTextView) view.findViewById(R.id.tv_date);
            tv_time = (AnyTextView) view.findViewById(R.id.tv_time);
        }
    }
}
