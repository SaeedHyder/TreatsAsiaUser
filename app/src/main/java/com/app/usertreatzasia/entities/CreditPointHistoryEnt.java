package com.app.usertreatzasia.entities;

import com.app.usertreatzasia.ui.views.AnyTextView;

public class CreditPointHistoryEnt {

    String tv_credit_type;
    String tv_credit_amount;
    String tv_msg;
    String tv_date;
    String tv_time;

    public CreditPointHistoryEnt(String tv_credit_type, String tv_credit_amount, String tv_msg, String tv_date, String tv_time) {
        this.tv_credit_type = tv_credit_type;
        this.tv_credit_amount = tv_credit_amount;
        this.tv_msg = tv_msg;
        this.tv_date = tv_date;
        this.tv_time = tv_time;
    }

    public String getTv_credit_type() {
        return tv_credit_type;
    }

    public void setTv_credit_type(String tv_credit_type) {
        this.tv_credit_type = tv_credit_type;
    }

    public String getTv_credit_amount() {
        return tv_credit_amount;
    }

    public void setTv_credit_amount(String tv_credit_amount) {
        this.tv_credit_amount = tv_credit_amount;
    }

    public String getTv_msg() {
        return tv_msg;
    }

    public void setTv_msg(String tv_msg) {
        this.tv_msg = tv_msg;
    }

    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String tv_date) {
        this.tv_date = tv_date;
    }

    public String getTv_time() {
        return tv_time;
    }

    public void setTv_time(String tv_time) {
        this.tv_time = tv_time;
    }
}

