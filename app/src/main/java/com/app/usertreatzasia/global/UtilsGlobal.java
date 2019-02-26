package com.app.usertreatzasia.global;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.DockActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsGlobal {
    public static int setColor(Context context, int colorId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

        {
            return context.getColor(colorId);
        } else

        {
            //noinspection deprecation
            return context.getResources().getColor(colorId);
        }
    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toTitleCase(c));
            }
        }

        return builder.toString();
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void HideKeyBoard(DockActivity dockActivity) {
        View view = dockActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) dockActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String getRemainingAmount(int percent, int totalPrice) {

        int discount = totalPrice * percent / 100;
        int remainingAmount = totalPrice - discount;

        return "$" + remainingAmount + "";
    }

    public static String getRemainingAmountWithoutDollar(int percent, int totalPrice) {

        int discount = totalPrice * percent / 100;
        int remainingAmount = totalPrice - discount;

        return remainingAmount + "";
    }

    public static String getVoucherType(String type, Context context) {

        String vouchertype = "";

        if (type.equalsIgnoreCase("cash_voucher")) {
            vouchertype = context.getString(R.string.cashback);
        } else if (type.equalsIgnoreCase("discount_coupon")) {
            vouchertype = context.getString(R.string.flatfree);
        } else if (type.equalsIgnoreCase("promo_code")) {
            vouchertype = context.getString(R.string.promo_code);
        } else if (type.equalsIgnoreCase("paid")) {
            vouchertype = context.getString(R.string.paid);
        } else if (type.equalsIgnoreCase("free")) {
            vouchertype = context.getString(R.string.free);
        } else {
            vouchertype = type;
        }
        return vouchertype;
    }

    public static String getRemainingAmountForRedeemCoupon(int percent, int totalPrice) {

        int discount = totalPrice * percent/100;
        int remainingAmount = totalPrice - discount;

        return remainingAmount + "";
    }

    public static Spannable setSpanString(String text, int startindex, int endindex, Context context) {
        Spannable spanText = new SpannableString(text);
        spanText.setSpan(new ForegroundColorSpan(context.getResources()
                .getColor(R.color.black)), startindex, endindex, 0);
        spanText.setSpan(new StyleSpan(Typeface.BOLD), startindex, endindex, 0);
        return spanText;
    }

    /**
     * @return index of pattern in s or -1, if not found
     */
    public static int startIndexOf(Pattern pattern, String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find() ? matcher.start() : -1;
    }

    /**
     * @return index of pattern in s or -1, if not found
     */
    public static int endIndexOf(Pattern pattern, String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find() ? matcher.end() : -1;
    }
}
