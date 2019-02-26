package com.app.usertreatzasia.ui.viewbinders.abstracts;

import android.app.Activity;
import android.view.View;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.CreditHistoryEnt;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.DateHelper;
import com.app.usertreatzasia.ui.views.AnyTextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.app.usertreatzasia.helpers.DateHelper.getFormatedDate;

public class CreditHistoryBinder extends ViewBinder<CreditHistoryEnt> {

    DockActivity dockActivity;
    private String timeDate;
    private String splittedDate;
    private String splittedTime;
    private BasePreferenceHelper prefHelper;

    public CreditHistoryBinder(DockActivity dockActivity, BasePreferenceHelper preferenceHelper) {
        super(R.layout.items_credit_history);
        this.dockActivity = dockActivity;
        prefHelper = preferenceHelper;
    }

    @Override
    public BaseViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(CreditHistoryEnt entity, int position, int grpPosition, View view, Activity activity) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        timeDate = DateHelper.getLocalTimeDate(entity.getCreatedAt());
        String[] splited = timeDate.split("\\s");
        splittedDate = splited[0];
        splittedTime = splited[1];

        splittedDate = getFormatedDate(AppConstants.DateFormat_YMD, AppConstants.DateFormat_DMY, splittedDate);




        viewHolder.tvCreditAmount.setText("(" + "$" + entity.getCredit() + ")" + "");
        viewHolder.tvMsg.setText(entity.getMessage() + "");
        viewHolder.tvDate.setText(splittedDate + "");
        //viewHolder.tvTime.setText(splittedTime + "");

        try {
            String _24HourTime = splittedTime;
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(_24HourTime);
            /*System.out.println(_24HourDt);
            System.out.println(_12HourSDF.format(_24HourDt));*/
            viewHolder.tvTime.setText(_12HourSDF.format(_24HourDt));
        } catch (Exception e) {
            e.printStackTrace();
        }

/*        viewHolder.tvTime.setText(DateHelper.getFormatedDate(AppConstants.DateFormat_YMDHMS, AppConstants.DateFormatlocaltime,
                entity.getCreatedAt()));*/

        if (entity.getStatus().equals(AppConstants.status_in)) {
            viewHolder.tvCreditType.setText("Credits Earned");
        } else {
            viewHolder.tvCreditType.setText("Credits Spent");
        }

        switch (prefHelper.getSelectedLanguage()) {
            case AppConstants.ENGLISH:
                viewHolder.tvMsg.setText(entity.getMessage());
                break;
            case AppConstants.MALAYSIAN:
                viewHolder.tvMsg.setText(entity.getMaMessage());
                break;
            case AppConstants.INDONESIAN:
                viewHolder.tvMsg.setText(entity.getInMessage());
                break;
            default:
                viewHolder.tvMsg.setText(entity.getMessage());
                break;
        }
    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.tv_credit_type)
        AnyTextView tvCreditType;
        @BindView(R.id.tv_credit_amount)
        AnyTextView tvCreditAmount;
        @BindView(R.id.tv_msg)
        AnyTextView tvMsg;
        @BindView(R.id.tv_date)
        AnyTextView tvDate;
        @BindView(R.id.tv_time)
        AnyTextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
