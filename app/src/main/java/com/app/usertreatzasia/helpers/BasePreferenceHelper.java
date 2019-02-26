package com.app.usertreatzasia.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.app.usertreatzasia.entities.AdvertisementEntity;
import com.app.usertreatzasia.entities.EnterNumberEntity;
import com.app.usertreatzasia.entities.EvoucherEnt;
import com.app.usertreatzasia.entities.FlashSaleEnt;
import com.app.usertreatzasia.entities.SignupEntities;
import com.app.usertreatzasia.entities.WalletEntity;
import com.app.usertreatzasia.entities.VochersTypes;
import com.app.usertreatzasia.retrofit.GsonFactory;

public class BasePreferenceHelper extends PreferenceHelper {

    private Context context;
    protected static final String KEY_LOGIN_STATUS = "islogin";
    private static final String KEY_USER = "key_user";
    private static final String KEY_SIGNUP_USER = "key_signup_user";
    private static final String KEY_LIKECOUNT_FLASH = "key_flash_count_like";
    private static final String KEY_LIKECOUNT_EVOUCHER = "key_evoucher_count_like";
    private static final String KEY_WALLET = "key_wallet";
    private static final String COUNTRY_CODE = "COUNTRY_CODE";
    private static final String KEY_SUBSCRIPTION_ID = "subscription_id";
    private static final String FILENAME = "preferences";
    protected static final String USERID = "userId";
    protected static final String ConvertedAmount = "ConvertedAmount";
    protected static final String convertedAmountCurrency = "convertedAmountCurrency";
    protected static final String EVENT_LIKE = "event_like";
    protected static final String EVOUCHERID = "evoucherId";
    protected static final String EVENTID = "eventId";
    protected static final String SUBSCRIPTIONSTATUS = "subscriptionStatus";
    protected static final String LOADADD = "LOADADD";
    protected static final String SELECTEDLANGUAGE = "SELECTEDLANGUAGE";
    protected static final String Firebase_TOKEN = "Firebasetoken";
    protected static final String NotificationCount = "NotificationCount";
    protected static final String remaining_amount = "RemainingAmount";
    protected static final String reward_cms = "reward_cms";
    protected static final String reward_contact_us = "reward_contact_us";
    protected static final String advertisement = "advertisement";
    protected static final String side_menu = "side_menu";
    protected static final String redemption_image = "Redemption_Image";
    protected static final String VOUCHERSTYPES = "VouchersTypes";

    public BasePreferenceHelper(Context c) {
        this.context = c;
    }

    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILENAME, Activity.MODE_PRIVATE);
    }

    public void setLoginStatus(boolean isLogin) {
        putBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS, isLogin);
    }

    public boolean isLogin() {
        return getBooleanPreference(context, FILENAME, KEY_LOGIN_STATUS);
    }


    public void setCountryCode(String Country) {
        putStringPreference(context, FILENAME, COUNTRY_CODE, Country);
    }

    public String getCountryCode() {
        return getStringPreference(context, FILENAME, COUNTRY_CODE);
    }

    public WalletEntity getWallet() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_WALLET), WalletEntity.class);
    }

    public EnterNumberEntity getUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_USER), EnterNumberEntity.class);
    }

    public void putUser(EnterNumberEntity user) {
        putStringPreference(context, FILENAME, KEY_USER, GsonFactory
                .getConfiguredGson().toJson(user));
    }

    public SignupEntities getSignUpUser() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_SIGNUP_USER), SignupEntities.class);
    }

    public void putSignupUser(SignupEntities signupuser) {
        putStringPreference(context, FILENAME, KEY_SIGNUP_USER, GsonFactory
                .getConfiguredGson().toJson(signupuser));
    }

    public AdvertisementEntity advertisementEntity() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, advertisement), AdvertisementEntity.class);
    }

    public void putAdvertisementEntity(AdvertisementEntity advertisementEntity) {
        putStringPreference(context, FILENAME, advertisement, GsonFactory
                .getConfiguredGson().toJson(advertisementEntity));
    }

    public FlashSaleEnt getLikeCountFlash() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, KEY_LIKECOUNT_FLASH), FlashSaleEnt.class);
    }

    public void putLikeCountFlash(FlashSaleEnt likeCountEntity) {
        putStringPreference(context, FILENAME, KEY_LIKECOUNT_FLASH, GsonFactory
                .getConfiguredGson().toJson(likeCountEntity));
    }

    public void putLikeCountEvoucher(Integer like) {
        putIntegerPreference(context, FILENAME, KEY_LIKECOUNT_FLASH, like);
    }


    public Integer getLikeCountEvoucher() {
        return getIntegerPreference(context, FILENAME, KEY_LIKECOUNT_FLASH);
    }

    public void putWallet(WalletEntity walletEntity) {
        putStringPreference(context, FILENAME, KEY_WALLET, GsonFactory
                .getConfiguredGson().toJson(walletEntity));
    }

    public String getFirebase_TOKEN() {
        return getStringPreference(context, FILENAME, Firebase_TOKEN);
    }
    public int getNotificationCount() {
        return getIntegerPreferenceDefault(context, FILENAME, NotificationCount);
    }

    public void setNotificationCount(int count) {
        putIntegerPreference(context, FILENAME, NotificationCount, count);
    }

    public String getRemainingAmount() {
        return getStringPreference(context, FILENAME, remaining_amount);
    }

    public void putRemainingAmount(String remainingAmount) {
        putStringPreference(context, FILENAME, remaining_amount, remainingAmount);
    }

    public String getRewardCms() {
        return getStringPreference(context, FILENAME, reward_cms);
    }

    public void setRewardCms(String reward) {
        putStringPreference(context, FILENAME, reward_cms, reward);
    }

    public String getContactUsCms() {
        return getStringPreference(context, FILENAME, reward_contact_us);
    }

    public void setContactUsCms(String contact_us) {
        putStringPreference(context, FILENAME, reward_contact_us, contact_us);
    }

    public String getSideMenuName() {
        return getStringPreference(context, FILENAME, side_menu);
    }

    public void putSideMenuName(String name) {
        putStringPreference(context, FILENAME, side_menu, name);
    }


    public void setFirebase_TOKEN(String _token) {
        putStringPreference(context, FILENAME, Firebase_TOKEN, _token);
    }

    public void setUserId(Integer userId) {
        putIntegerPreference(context, FILENAME, USERID, userId);
    }

    public Float getConvertedAmount() {
        return getFloatPreference(context, FILENAME, ConvertedAmount);
    }

    public void setConvertedAmount(Float convertedAmount) {
        putFloatPreference(context, FILENAME, ConvertedAmount, convertedAmount);
    }

    public String getConvertedAmountCurrrency() {
        return getStringPreference(context, FILENAME, convertedAmountCurrency);
    }

    public void setConvertedAmountCurrency(String convertedAmountCurrencys) {
        putStringPreference(context, FILENAME, convertedAmountCurrency, convertedAmountCurrencys);
    }

    public Integer getUserId() {
        return getIntegerPreference(context, FILENAME, USERID);
    }

    public void setEventLike(Integer eventLike) {
        putIntegerPreference(context, FILENAME, EVENT_LIKE, eventLike);
    }

    public Integer getEventLike() {
        return getIntegerPreference(context, FILENAME, EVENT_LIKE);
    }

    public void setEvoucherId(Integer evoucherId) {
        putIntegerPreference(context, FILENAME, EVOUCHERID, evoucherId);
    }

    public Integer getEvoucherId() {
        return getIntegerPreference(context, FILENAME, EVOUCHERID);
    }

    public Integer getEventId() {
        return getIntegerPreference(context, FILENAME, EVENTID);
    }

    public void setEventId(Integer eventId) {
        putIntegerPreference(context, FILENAME, EVENTID, eventId);
    }

    public boolean getSubscriptionStatus() {
        return getBooleanPreference(context, FILENAME, SUBSCRIPTIONSTATUS);
    }

    public void setSubscriptionStatus(boolean subscriptionStatus) {
        putBooleanPreference(context, FILENAME, SUBSCRIPTIONSTATUS, subscriptionStatus);
    }

    public String getSelectedLanguage() {
        return getStringPreference(context,FILENAME,SELECTEDLANGUAGE);
    }

    public void setSelectedlanguage(String selectedlanguage) {
        putStringPreference(context, FILENAME, SELECTEDLANGUAGE, selectedlanguage);
    }

    public VochersTypes getVochersTypes() {
        return GsonFactory.getConfiguredGson().fromJson(
                getStringPreference(context, FILENAME, VOUCHERSTYPES), VochersTypes.class);
    }

    public void putVochersTypes(VochersTypes vochersTypes) {
        putStringPreference(context, FILENAME, VOUCHERSTYPES, GsonFactory
                .getConfiguredGson().toJson(vochersTypes));
    }

    public boolean isloadAdd() {
        return getBooleanPreference(context, FILENAME, LOADADD);
    }

    public void setLoadAdd(boolean loadAdd) {
        putBooleanPreference(context, FILENAME, LOADADD, loadAdd);
    }
}
