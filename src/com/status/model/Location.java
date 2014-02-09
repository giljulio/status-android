package com.status.model;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class Location implements Serializable {

	public Location(double latitude, double longitude){
		mLatitude = latitude;
		mLongitude = longitude;
	}
	
	public double mLatitude;
	
	public double mLongitude;
	
	public LatLng toLatLng(){
		return new LatLng(mLatitude, mLongitude);
	}

}
