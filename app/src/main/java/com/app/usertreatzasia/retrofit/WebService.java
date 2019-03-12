package com.app.usertreatzasia.retrofit;


import com.app.usertreatzasia.entities.AddCmsEntity;
import com.app.usertreatzasia.entities.AdvertisementEntity;
import com.app.usertreatzasia.entities.BuyEventPointCreditEnt;
import com.app.usertreatzasia.entities.BuyEvoucherEntity;
import com.app.usertreatzasia.entities.BuyFlashSaleCreditEnt;
import com.app.usertreatzasia.entities.BuyRewardPointEntity;
import com.app.usertreatzasia.entities.BuySubscriptionCashEntity;
import com.app.usertreatzasia.entities.BuyTicketEnt;
import com.app.usertreatzasia.entities.BuyVoucherEnt;
import com.app.usertreatzasia.entities.CancelEvoucherEventsEntity;
import com.app.usertreatzasia.entities.CmsEntity;
import com.app.usertreatzasia.entities.CreditHistoryEnt;
import com.app.usertreatzasia.entities.CurrencyRateEntity;
import com.app.usertreatzasia.entities.EnterNumberEntity;
import com.app.usertreatzasia.entities.EntityTierAmount;
import com.app.usertreatzasia.entities.EntityTierRecord;
import com.app.usertreatzasia.entities.EntityTierRecordParent;
import com.app.usertreatzasia.entities.EventDetailGift;
import com.app.usertreatzasia.entities.EventsEnt;
import com.app.usertreatzasia.entities.EvoucherEnt;
import com.app.usertreatzasia.entities.FilterEnt;
import com.app.usertreatzasia.entities.FlashSaleEnt;
import com.app.usertreatzasia.entities.GetMerchantsEnt;
import com.app.usertreatzasia.entities.GetUsersEnt;
import com.app.usertreatzasia.entities.GiftReceivedEvent;
import com.app.usertreatzasia.entities.GoogleGeoCodeResponse;
import com.app.usertreatzasia.entities.GoogleServiceResponse;
import com.app.usertreatzasia.entities.NewEventDetailEnt;
import com.app.usertreatzasia.entities.NewUserSubscriptionEntity;
import com.app.usertreatzasia.entities.NotificationEnt;
import com.app.usertreatzasia.entities.PointHistoryEnt;
import com.app.usertreatzasia.entities.PromoCodeEnt;
import com.app.usertreatzasia.entities.PurchaseEnt;
import com.app.usertreatzasia.entities.RedeemEnt;
import com.app.usertreatzasia.entities.RedemptionEnt;
import com.app.usertreatzasia.entities.ReferalCodeEntity;
import com.app.usertreatzasia.entities.ResponseWrapper;
import com.app.usertreatzasia.entities.RewardsEnt;
import com.app.usertreatzasia.entities.RewardsWalletEntity;
import com.app.usertreatzasia.entities.RewardsWonEnt;
import com.app.usertreatzasia.entities.SignupEntities;
import com.app.usertreatzasia.entities.SignupEntitiesChanged;
import com.app.usertreatzasia.entities.SubscriptionEntity;
import com.app.usertreatzasia.entities.SubscriptionPostEntity;
import com.app.usertreatzasia.entities.TopupCashoutTermsConditionEntity;
import com.app.usertreatzasia.entities.WalletEntity;
import com.app.usertreatzasia.entities.WalletEventDetail;
import com.app.usertreatzasia.entities.WalletEventDetailEnt;
import com.app.usertreatzasia.entities.WalletEventEntity;
import com.app.usertreatzasia.entities.WalletEvoucherDetailEnt;
import com.app.usertreatzasia.entities.WalletEvoucherEnt;
import com.app.usertreatzasia.entities.WalletGiftEnt;
import com.app.usertreatzasia.entities.countEnt;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebService {

    @FormUrlEncoded
    @POST("user/sendcode")
    Call<ResponseWrapper<EnterNumberEntity>> enterNumber(
            @Field("mobile_no") String mobile_no
    );

    @FormUrlEncoded
    @POST("user/verifyCode")
    Call<ResponseWrapper<SignupEntities>> verifyCode(
            @Field("user_id") int user_id,
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseWrapper<SignupEntities>> userSignup(
            @Field("user_id") int user_id,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("device_token") String device_token,
            @Field("email") String email,
            @Field("referral_code") String referral_code,
            @Field("device_type") String device_type,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation
    );

    @FormUrlEncoded
    @POST("user/changepassword")
    Call<ResponseWrapper<SignupEntities>> changePassword(
            @Field("old_password") String old_password,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Header("token") String token
    );

    @GET("user/getUserProfile")
    Call<ResponseWrapper<SignupEntities>> getUserProfile(
            @Header("token") String token
            );

    //changed
    @GET("user/getUserProfile")
    Call<ResponseWrapper<SignupEntitiesChanged>> getUserProfileUpdated(
            @Header("token") String token
            );

    @GET("user/getUserProfile")
    Call<ResponseWrapper<ReferalCodeEntity>> getReferalCode(
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("user/updateProfile")
    Call<ResponseWrapper<SignupEntities>> updateUserProfile(

            @Field("first_name") String first_name,
            @Field("email") String email,
            @Field("mobile_no") String mobile_no,
            @Field("location") String location,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("user/updateDeviceToken")
    Call<ResponseWrapper> updateToken(
            @Field("device_token") String device_token,
            @Field("device_type") String device_type,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("cms/contactus")
    Call<ResponseWrapper> contactUs(
            @Field("text") String text,
            @Header("token") String token
    );

    @GET("cms/getCms")
    Call<ResponseWrapper<CmsEntity>> getCms(
            @Query("type") String type,
            @Header("token") String token
    );

    @GET("getSubscriptions")
    Call<ResponseWrapper<List<SubscriptionEntity>>> getSubscription(
            @Header ("token") String token
    );

    @GET("getWalletGiftEvoucher")
    Call<ResponseWrapper<List<WalletGiftEnt>>> getWalletGift(
            @Header ("token") String token
    );

    @GET("getWalletGiftEvent")
    Call<ResponseWrapper<List<GiftReceivedEvent>>> getWalletGiftEvent(
            @Header ("token") String token
    );

    @FormUrlEncoded
    @POST("userSubscription")
    Call<ResponseWrapper<SubscriptionPostEntity>> userSubscription(
            @Field("user_id") int user_id,
            @Field("subscription_id") int subscription_id,
            @Field("credit_amount") String credit_amount,
            @Field("amount_currency") String amount_currency
    );

    @FormUrlEncoded
    @POST("buyevoucher")
    Call<ResponseWrapper<RedeemEnt>> buyCoupon(
            @Field("evoucher_id") int evoucher_id,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("buyflashsale")
    Call<ResponseWrapper<RedeemEnt>> buySale(
            @Field("evoucher_id") int evoucher_id,
            @Header("token") String token
    );

    @GET("getEvoucherDetail")
    Call<ResponseWrapper<EvoucherEnt>> getCouponDetail(
            @Query("evoucher_id") String evoucher_id,
            @Header("token") String token
    );

    @GET("getEvoucherDetail")
    Call<ResponseWrapper<EvoucherEnt>> getSaleDetail(
            @Query("user_id") String user_id,
            @Query("evoucher_id") String evoucher_id,
            @Header("token") String token
    );

    @GET("walletEvoucherDetail")
    Call<ResponseWrapper<WalletEvoucherDetailEnt>> getWalletVoucherDetail(
            @Query("evoucher_id") String evoucher_id,
            @Header("token") String token
    );

    @GET("getEvoucher")
    Call<ResponseWrapper<ArrayList<EvoucherEnt>>> eVoucherHome(
            @Header("token") String token
    );

    @GET("getEvoucher")
    Call<ResponseWrapper<ArrayList<EvoucherEnt>>> eVoucherSearch(
            @Query("search") String search,
            @Header("token") String token
    );

    @GET("getEvoucher")
    Call<ResponseWrapper<ArrayList<EvoucherEnt>>> eVoucherSearchMerchant(
            @Query("merchant_id") String merchant_id,
            @Header("token") String token
    );

    @GET("getEvoucher")
    Call<ResponseWrapper<ArrayList<EvoucherEnt>>> eVoucherSearchFilter(
            @Query("category_ids") String category_ids,
            @Query("type_select") String type_select,
            @Header("token") String token
    );

    @GET("getEvoucher")
    Call<ResponseWrapper<ArrayList<EvoucherEnt>>> eVoucherSearchMap(
            @Query("lat") String latitude,
            @Query("long") String longitude,
            @Header("token") String token
    );

    @GET("getFlashSale")
    Call<ResponseWrapper<ArrayList<FlashSaleEnt>>> flashSale(
            @Header("token") String token
    );

    @GET("getFlashSale")
    Call<ResponseWrapper<ArrayList<FlashSaleEnt>>> flashSaleFilter(
            @Query("category_ids") String category_ids,
            @Header("token") String token
    );

    @GET("getFlashSale")
    Call<ResponseWrapper<ArrayList<FlashSaleEnt>>> flashSaleMerchant(
            @Query("merchant_id") String merchant_id,
            @Header("token") String token
    );

    @GET("getFlashSale")
    Call<ResponseWrapper<ArrayList<FlashSaleEnt>>> flashSaleSearch(
            @Query("search") String search,
            @Header("token") String token
    );

    @GET("getFlashSale")
    Call<ResponseWrapper<ArrayList<FlashSaleEnt>>> flashSaleMap(
            @Query("lat") String latitude,
            @Query("long") String longitude,
            @Header("token") String token
    );

    @GET("getFlashsaleDetail")
    Call<ResponseWrapper<FlashSaleEnt>> flashSaleDetail(
            @Query("flashsale_id") String flashsale_id,
            @Header("token") String token
    );

    @GET("getReward")
    Call<ResponseWrapper<ArrayList<RewardsEnt>>> rewards(
            @Header("token") String token
    );

    @GET("getReward")
    Call<ResponseWrapper<ArrayList<RewardsEnt>>> rewardsSearch(
            @Query("search") String search,
            @Header("token") String token
    );

    @GET("getWonReward")
    Call<ResponseWrapper<ArrayList<RewardsWonEnt>>> rewardsWon(
            @Header("token") String token
    );

    @GET("getWalletReward")
    Call<ResponseWrapper<ArrayList<RewardsWalletEntity>>> rewardsWallet(
            @Header("token") String token
    );

    @GET("getPurchaseHistory")
    Call<ResponseWrapper<ArrayList<PurchaseEnt>>> purchaseHistory(
            @Header("token") String token
    );

    @GET("getRedemptionHistory")
    Call<ResponseWrapper<ArrayList<RedemptionEnt>>> redemptionHistory(
            @Header("token") String token
    );

    @GET("getEvent")
    Call<ResponseWrapper<ArrayList<EventsEnt>>> events(
            @Header("token") String token
    );

    @GET("getEvent")
    Call<ResponseWrapper<ArrayList<EventsEnt>>> eventSearch(
            @Query("search") String search,
            @Header("token") String token
    );

    @GET("getEventDetail")
    Call<ResponseWrapper<EventsEnt>> eventDetail(
            @Query("event_id") int event_id,
            @Header("token") String token
    );

    @GET("getWalletEvent")
    Call<ResponseWrapper<ArrayList<WalletEventEntity>>> eventsWallet(
            @Header("token") String token
    );

    @GET("getWalletEvent")
    Call<ResponseWrapper<ArrayList<WalletEventEntity>>> eventsWalletSearch(
            @Query("search") String search,
            @Header("token") String token
    );

    @GET("walletEventDetail")
    Call<ResponseWrapper<NewEventDetailEnt>> eventDetailWallet(
            @Query("event_id") String event_id,
            @Header("token") String token
    );

    @GET("getcategories")
    Call<ResponseWrapper<ArrayList<FilterEnt>>> filterData(
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("buyEvent")
    Call<ResponseWrapper<BuyTicketEnt>> buyTicket(
            @Field("event_id") int event_id,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("gift_redeem_event")
    Call<ResponseWrapper<BuyTicketEnt>> redeemTicket(
            @Field("event_id") String event_id,
            @Field("type_select") String type_select,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("gift_redeem_event")
    Call<ResponseWrapper<BuyTicketEnt>> giftTicket(
            @Field("event_id") int event_id,
            @Field("type_select") String type_select,
            @Field("gift_user_id") String gift_user_id,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("gift_redeem_evoucher")
    Call<ResponseWrapper<BuyVoucherEnt>> redeemEvoucher(
            @Field("evoucher_id") int evoucher_id,
            @Field("type") String type_select,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("gift_redeem_evoucher")
    Call<ResponseWrapper<BuyVoucherEnt>> giftEvoucher(
            @Field("evoucher_id") int evoucher_id,
            @Field("type") String type_select,
            @Field("gift_user_id") String gift_user_id,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("flashsaleLike")
    Call<ResponseWrapper> flashSaleLike(
            @Field("flashsale_id") int flashsale_id,
            @Field("status") int status,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("eventLike")
    Call<ResponseWrapper> eventLike(
            @Field("event_id") int event_id,
            @Field("status") int status,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("evoucherLike")
    Call<ResponseWrapper> evoucherLike(
            @Field("evoucher_id") int evoucher_id,
            @Field("status") int status,
            @Header("token") String token
    );

    @GET("getUnreadNotificationsCount")
    Call<ResponseWrapper<countEnt>> getNotificationCount(
            @Header("token") String token
    );


    @GET("getnotifications")
    Call<ResponseWrapper<ArrayList<NotificationEnt>>> getNotification(
            @Header("token") String token
    );

    @GET("getMerchant")
    Call<ResponseWrapper<ArrayList<GetMerchantsEnt>>> getMerchants(
            @Header("token") String token
    );

    @GET("getUser")
    Call<ResponseWrapper<ArrayList<GetUsersEnt>>> getGiftUsers(
            @Header("token") String token
    );

    @GET("getWalletCount")
    Call<ResponseWrapper<WalletEntity>> getWalletData(
            @Header("token") String token
    );

    @GET("getUserPoint")
    Call<ResponseWrapper<ArrayList<PointHistoryEnt>>> getPointHistory(
            @Query("point_type") String point_type,
            @Header("token") String token
    );

    @GET("getCreditPoint")
    Call<ResponseWrapper<ArrayList<CreditHistoryEnt>>> getCreditHistory(
            @Header("token") String token
    );

    @GET("getWalletEvoucher")
    Call<ResponseWrapper<ArrayList<WalletEvoucherEnt>>> getWalletEvoucher(
            @Header("token") String token
    );

    @GET("getWalletEvoucher")
    Call<ResponseWrapper<ArrayList<WalletEvoucherEnt>>> getWalletEvoucherFilter(
            @Query("category_ids") String category_ids,
            @Header("token") String token
    );

    @GET("getWalletEvoucher")
    Call<ResponseWrapper<ArrayList<WalletEvoucherEnt>>> getWalletEvoucherSearch(
            @Query("search") String search,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("checkPromoCode")
    Call<ResponseWrapper<PromoCodeEnt>> checkPromoCode(
            @Field("subscription_id") int subscription_id,
            @Field("promo_code") String promo_code,
            @Header("token") String token
    );

    @GET("user/logoutandroid")
    Call<ResponseWrapper> logout(
            @Header("token") String token
    );

    @GET("/maps/api/geocode/json")
    Call<GoogleServiceResponse<List<GoogleGeoCodeResponse>>> getLatLongInfo(

            @Query("address") String address,
            @Query("sensor") String sensor);

    @FormUrlEncoded
    @POST("userSubscription")
    Call<ResponseWrapper<BuySubscriptionCashEntity>> SuscribePromoCodeCash(
            @Field("subscription_id") int subscription_id,
            @Field("amount") String amount,
            @Field("nonce") String nonce,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("addToWalletFlashSale")
    Call<ResponseWrapper<BuyFlashSaleCreditEnt>> BuyFlashSalePointCredit(
            @Field("flashsale_id") int flashsale_id,
            @Field("credit_amount") String credit_amount,
            @Field("point") String point,
            @Field("purchase_date") String purchase_date,
            @Field("payment_type") String payment_type,
            @Field("selected_type") String selected_type,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("cms/addBrainTreeAmount")
    Call<ResponseWrapper> BuyFlashSaleCash(
            @Field("flashsale_id") int flashsale_id,
            @Field("type") String type,
            @Field("amount") String amount,
            @Field("nonce") String nonce,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("addToWalletEvoucher")
    Call<ResponseWrapper<BuyEvoucherEntity>> BuyEvoucherPoint(
            @Field("evoucher_id") int evoucher_id,
            @Field("credit_amount") String credit_amount,
            @Field("point") String point,
            @Field("purchase_date") String purchase_date,
            @Field("payment_type") String payment_type,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("addToWalletBuyTicketEvent")
    Call<ResponseWrapper<BuyEventPointCreditEnt>> BuyEventsPointCredit(
            @Field("event_id") int event_id,
            @Field("credit_amount") String credit_amount,
            @Field("point") String point,
            @Field("purchase_date") String purchase_date,
            @Field("selected_type") String selected_type,
            @Field("type") String type,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("cms/addBrainTreeAmount")
    Call<ResponseWrapper> BuyEventCash(
            @Field("event_id") int event_id,
            @Field("type") String type,
            @Field("amount") String amount,
            @Field("nonce") String nonce,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("buyReward")
    Call<ResponseWrapper<BuyRewardPointEntity>> BuyRewardPoint(
            @Field("reward_id") int reward_id,
            @Field("quantity") String quantity,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("cms/CashoutCredit")
    Call<ResponseWrapper> cashoutCredit(
            @Field("credit") String credit,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("cms/TopupCredit")
    Call<ResponseWrapper> topUpCreditCash(
            @Field("amount") String amount,
            @Field("nonce") String nonce,
            @Header("token") String token
    );

    @GET("cms/getSetting")
    Call<ResponseWrapper<TopupCashoutTermsConditionEntity>> termsConditionTopupCashout(
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("CancelRedeemVoucher")
    Call<ResponseWrapper<CancelEvoucherEventsEntity>> cancelVoucherEvents(
            @Field("record_id") int record_id,
            @Header("token") String token
    );

    @GET("currencyRate")
    Call<ResponseWrapper<CurrencyRateEntity>> getCurrencyRate(
            @Query("from") String from,
            @Query("to") String to
    );

    @GET("getUrlAdvertisement")
    Call<ResponseWrapper<AddCmsEntity>> getAdCms(
            @Header("token") String token
    );

    @GET("getuserSubscription")
    Call<ResponseWrapper<NewUserSubscriptionEntity>> getNewUserSubscription(
            @Header("token") String token
    );

    @GET("gettiertotal")
    Call<ResponseWrapper<EntityTierAmount>> getTierAmountData(
            @Header("token") String token
    );

    @GET("gettierrecord")
    Call<ResponseWrapper<EntityTierRecordParent>> getTierRecord(
            @Query("tier") String tier,
            @Query("month") String month,
            @Query("year") String year,
            @Header("token") String token
    );

    @FormUrlEncoded
    @POST("usermerchantreferral")
    Call<ResponseWrapper> referRestaurant(
            @Field("merchant_name") String merchant_name,
            @Field("merchant_url") String nonce,
            @Field("message") String message,
            @Header("token") String token
    );
}