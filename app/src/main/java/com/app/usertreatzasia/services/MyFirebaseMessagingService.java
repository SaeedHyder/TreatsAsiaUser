package com.app.usertreatzasia.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.usertreatzasia.R;
import com.app.usertreatzasia.activities.MainActivity;
import com.app.usertreatzasia.entities.ResponseWrapper;
import com.app.usertreatzasia.entities.countEnt;
import com.app.usertreatzasia.global.AppConstants;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.helpers.BasePreferenceHelper;
import com.app.usertreatzasia.helpers.NotificationHelper;
import com.app.usertreatzasia.helpers.ServiceHelper;
import com.app.usertreatzasia.retrofit.WebService;
import com.app.usertreatzasia.retrofit.WebServiceFactory;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private WebService webservice;
    private BasePreferenceHelper preferenceHelper;
    private ServiceHelper serviceHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        preferenceHelper = new BasePreferenceHelper(getApplicationContext());

        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            buildNotification(remoteMessage);
        }

        //getNotificationCount();
        /*getNotificaitonCount();*/
    }

    private void getNotificaitonCount() {
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(this, WebServiceConstants.Local_SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(this);
        Call<ResponseWrapper<countEnt>> call = webservice.getNotificationCount(preferenceHelper.getSignUpUser().getToken());
        call.enqueue(new Callback<ResponseWrapper<countEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<countEnt>> call, Response<ResponseWrapper<countEnt>> response) {
                preferenceHelper.setNotificationCount(response.body().getResult().getCount() );
            }

            @Override
            public void onFailure(Call<ResponseWrapper<countEnt>> call, Throwable t) {

            }
        });
    }

/*    private void getNotificationCount() {
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(this, WebServiceConstants.Local_SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(this);
        if (preferenceHelper != null && preferenceHelper.getSignUpUser() != null && preferenceHelper.getSignUpUser().getToken() != null) {
            serviceHelper.enqueueCall(webservice.getNotificationCount(preferenceHelper.getSignUpUser().getToken()),
                    WebServiceConstants.getNotificationCount);
        }*/
    //}

    private void buildNotification(RemoteMessage messageBody) {
        getNotificaitonCount();

        String title = messageBody.getData().get("title");
        String message = messageBody.getData().get("message");

        if (preferenceHelper.getSelectedLanguage().equals(AppConstants.ENGLISH)) {
            title = messageBody.getData().get("title");
            message = messageBody.getData().get("message");
        } else if (preferenceHelper.getSelectedLanguage().equals(AppConstants.INDONESIAN)) {
            title = messageBody.getData().get("in_title");
            message = messageBody.getData().get("in_message");
        } else if (preferenceHelper.getSelectedLanguage().equals(AppConstants.MALAYSIAN)) {
            title = messageBody.getData().get("ma_title");
            message = messageBody.getData().get("ma_message");
        } else {
            title = messageBody.getData().get("title");
            message = messageBody.getData().get("message");
        }

        Log.e(TAG, "message: " + message);
        Intent resultIntent = new Intent(MyFirebaseMessagingService.this, MainActivity.class);
        //resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.putExtra("message", message);
        resultIntent.putExtra("tapped", true);

        Intent pushNotification = new Intent(AppConstants.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);

        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(pushNotification);
        showNotificationMessage(MyFirebaseMessagingService.this, title, message, "", resultIntent);
    }

/*    private void getNotificaitonCount() {
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(this, WebServiceConstants.Local_SERVICE_URL);
        preferenceHelper = new BasePreferenceHelper(this);
        Call<ResponseWrapper<countEnt>> call = webservice.getNotificationCount(preferenceHelper.getSignUpUser().getToken());
        call.enqueue(new Callback<ResponseWrapper<countEnt>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<countEnt>> call, Response<ResponseWrapper<countEnt>> response) {
                preferenceHelper.setNotificationCount(response.body().getResult().getCount() );
            }

            @Override
            public void onFailure(Call<ResponseWrapper<countEnt>> call, Throwable t) {

            }
        });
    }*/

    private void SendNotification(int count, JSONObject json) {
    }
    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        NotificationHelper.getInstance().showNotification(context,
                R.drawable.treatasia,
                title,
                message,
                timeStamp,
                intent);
    }


}
