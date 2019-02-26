package com.app.usertreatzasia.interfaces;

import com.google.android.gms.maps.model.LatLng;

public interface IGetLocation {

    public void onLocationSet(LatLng location, String formattedAddress);
}