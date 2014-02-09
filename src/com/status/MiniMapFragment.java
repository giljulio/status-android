package com.status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.status.StatusDetailFragment.Callbacks;

public class MiniMapFragment extends SupportMapFragment {
	
	public MiniMapFragment() {
	    super();
	
	}
	
	Callbacks mCallbacks;
	
	public static MiniMapFragment newInstance(Callbacks callbacks){
	    MiniMapFragment frag = new MiniMapFragment();
	    frag.mCallbacks = callbacks;
	    return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflator, ViewGroup viewGroup, Bundle bundle) {
	    View v = super.onCreateView(inflator, viewGroup, bundle);
	    if(mCallbacks != null)
	    	mCallbacks.setupMap(this);
	    return v;
	}
	
}