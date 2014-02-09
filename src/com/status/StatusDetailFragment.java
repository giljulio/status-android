package com.status;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.status.dummy.DummyContent;
import com.status.model.Status;

/**
 * A fragment representing a single Status detail screen. This fragment is
 * either contained in a {@link StatusListActivity} in two-pane mode (on
 * tablets) or a {@link StatusDetailActivity} on handsets.
 */
public class StatusDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_SELECTED_ITEM = "selected_item";
	public static final String ARG_ITEM_LIST = "item_list";

	private static final String TAG = StatusDetailFragment.class.getSimpleName();
	
	interface Callbacks {
		
		public void setupMap(MiniMapFragment map);
	}
	
	interface MarkerSelectedCallback {

		public void onMarkerSelected(int index);
	}
	
	private HashMap<String, Marker> mMarker;
	
	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	private Status mSelectedStatus;
	
	private ArrayList<Status> mStatusList;
	
	
	
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public StatusDetailFragment() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_SELECTED_ITEM)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mSelectedStatus = (Status) getArguments().getSerializable(ARG_SELECTED_ITEM);
			mStatusList = (ArrayList<Status>) getArguments().getSerializable(ARG_ITEM_LIST);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_status_detail,
				container, false);


		MiniMapFragment miniMapFragment = MiniMapFragment.newInstance(new Callbacks(){
	
			@Override
			public void setupMap(MiniMapFragment mapFragment) {
			    try {
				    mapFragment.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(mSelectedStatus.mLocation.toLatLng(), 16));
		    		mapFragment.getMap().addMarker(new MarkerOptions()
		    		.title(mSelectedStatus.mUser.mForename)
		    		.snippet(mSelectedStatus.mMessage)
		    		.position(mSelectedStatus.mLocation.toLatLng())
		    		.visible(true));
			    	for(Status status : mStatusList){
			    		Marker marker = mapFragment.getMap().addMarker(new MarkerOptions()
				    		.title(status.mUser.mForename)
				    		.snippet(status.mMessage)
				    		.position(status.mLocation.toLatLng()));
			    		mMarker.put(marker.getId(), marker);
			    	}
			    } catch(Exception e){
			    	e.printStackTrace();
			    }
			}
			
		});
		
	
		getChildFragmentManager().beginTransaction().replace(R.id.detail_item_map_frame, miniMapFragment).commit();

		return rootView;
	}
}
