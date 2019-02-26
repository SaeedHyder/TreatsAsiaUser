package com.app.usertreatzasia.entities;

/**
 * Created by ahmedsyed on 1/6/2018.
 */

public class LikeCountEntity {

    String remaining_evoucher_count;
    int evoucher_like;

    public String getRemaining_evoucher_count() {
        return remaining_evoucher_count;
    }

    public void setRemaining_evoucher_count(String remaining_evoucher_count) {
        this.remaining_evoucher_count = remaining_evoucher_count;
    }

    public int getEvoucher_like() {
        return evoucher_like;
    }

    public void setEvoucher_like(int evoucher_like) {
        this.evoucher_like = evoucher_like;
    }
}
