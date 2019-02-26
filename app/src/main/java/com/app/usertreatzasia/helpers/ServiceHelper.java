package com.app.usertreatzasia.helpers;

import android.util.Log;

import com.app.usertreatzasia.activities.DockActivity;
import com.app.usertreatzasia.entities.ResponseWrapper;
import com.app.usertreatzasia.fragments.EnterNumberFragment;
import com.app.usertreatzasia.global.WebServiceConstants;
import com.app.usertreatzasia.interfaces.webServiceResponseLisener;
import com.app.usertreatzasia.retrofit.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 7/17/2017.
 */

public class ServiceHelper<T> {
    private webServiceResponseLisener serviceResponseLisener;
    private DockActivity context;
    private WebService webService;
    private BasePreferenceHelper preferenceHelper;

    public ServiceHelper(webServiceResponseLisener serviceResponseLisener, DockActivity conttext, WebService webService, BasePreferenceHelper preferenceHelper) {
        this.serviceResponseLisener = serviceResponseLisener;
        this.context = conttext;
        this.webService = webService;
        this.preferenceHelper = preferenceHelper;
    }

    public void enqueueCall(Call<ResponseWrapper<T>> call, final String tag) {
        if (InternetHelper.CheckInternetConectivityandShowToast(context)) {
            context.onLoadingStarted();
            call.enqueue(new Callback<ResponseWrapper<T>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<T>> call, Response<ResponseWrapper<T>> response) {
                    context.onLoadingFinished();
                    if (response != null) {
                        if (response.body() != null) {
                            if (response.body().getResponse() != null) {
                                if (response.body().getResponse().equals(WebServiceConstants.TOKEN_EXPIRED_ERROR_CODE)) {
                                    UIHelper.showShortToastInCenter(context, response.body().getMessage());
                                    preferenceHelper.setLoginStatus(false);
                                    preferenceHelper.setFirebase_TOKEN(null);
                                    preferenceHelper.setCountryCode(null);
                                    context.popBackStackTillEntry(0);
                                    context.replaceDockableFragment(EnterNumberFragment.newInstance(), EnterNumberFragment.class.getName());
                                } else {
                                    if (response.body().getResponse().equals(WebServiceConstants.SUCCESS_RESPONSE_CODE)) {
                                        serviceResponseLisener.ResponseSuccess(response.body().getResult(), tag);
                                    } else {
                                        UIHelper.showShortToastInCenter(context, response.body().getMessage());
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    context.onLoadingFinished();
                    t.printStackTrace();
                    Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
                }
            });
        }
    }
}
