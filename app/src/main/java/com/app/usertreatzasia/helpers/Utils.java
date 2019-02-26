package com.app.usertreatzasia.helpers;

import android.content.Context;

import com.app.usertreatzasia.R;

/**
 * Created on 10/9/2017.
 */

public class Utils {
    public static String getVoucherType(String type, Context context) {

        String vouchertype = "";

        if(type.equalsIgnoreCase("cash_back")){
            vouchertype = context.getString(R.string.cashback);
        }
        else if(type.equalsIgnoreCase("flat_free")){
            vouchertype = context.getString(R.string.flatfree);
        }
        else if(type.equalsIgnoreCase("discount")){
            vouchertype = context.getString(R.string.discount);
        }
        else if(type.equalsIgnoreCase("promo")){
            vouchertype = context.getString(R.string.promo_code);
        }

        return vouchertype;
    }
}
