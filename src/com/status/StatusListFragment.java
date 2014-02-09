package com.status;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.status.model.Location;
import com.status.model.Status;
import com.status.model.User;

/**
 * A list fragment representing a list of Statuses. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link StatusDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class StatusListFragment extends ListFragment {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;

	private StatusAdapter mStatusAdapter;
	
	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(Status selectedStatus, ArrayList<Status> status);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(Status selectedStatus, ArrayList<Status> status) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public StatusListFragment() {
	}
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO: replace with a real list adapter.
		mStatusAdapter = new StatusAdapter(getActivity());
		mStatusAdapter.add(new Status(1, new User("Gil", "Julio", R.drawable.example_thumbnail), "Help is needed at #lagos #medical", new Location(-0.674750d, 112.343750d)));
		mStatusAdapter.add(new Status(2, new User("Pim", "Witte", R.drawable.example_thumbnail_pim), "Requesting medical supplies #lagos #medical", new Location(-0.883471d, 111.190186d)));
		mStatusAdapter.add(new Status(3, new User("Matt", "", R.drawable.example_thumbnail_matt), "Water is needed at #mandalay #water", new Location(21.9403598,96.0676948)));
		mStatusAdapter.add(new Status(4, new User("Maili", "", R.drawable.example_thumbnail_maili), "Second landslide in #cambodia", new Location(11.9834935,104.9806145)));
		mStatusAdapter.add(new Status(5, new User("Pim", "", R.drawable.example_thumbnail_pim), "Requesting medical supplies #lagos #medical", new Location(-0.883471d, 111.190186d)));
		mStatusAdapter.add(new Status(6, new User("Matt", "", R.drawable.example_thumbnail_matt), "Water is needed at #mandalay #water", new Location(21.9403598,96.0676948)));

		setListAdapter(mStatusAdapter);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
	    registerForContextMenu(getListView());

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.popup_actions, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.popup_contact:
			return true;
		case R.id.popup_forward:
			return true;
		default:
			return false;			
		}
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	
	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);

		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
		//view.setSelected(true);
		
		//To array list
		ArrayList<Status> arrayList = new ArrayList<Status>();
		for(int i = 0; i < mStatusAdapter.getCount(); i++)
			arrayList.add(mStatusAdapter.getItem(i));
		

        for (int j = 0; j < listView.getChildCount(); j++)
        	listView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

        // change the background color of the selected element
        view.setBackgroundColor(Color.LTGRAY);
		
		mCallbacks.onItemSelected(mStatusAdapter.getItem(position), arrayList);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
	
	class StatusAdapter extends ArrayAdapter<Status> {

		public StatusAdapter(Context context) {
			super(context, 0);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null)
				convertView = LayoutInflater.from(getActivity()).inflate(R.layout.list_item_status, null);


			
			TextView name = (TextView) convertView.findViewById(R.id.list_item_status_user_name);
			TextView message = (TextView) convertView.findViewById(R.id.list_item_status_message);
			TextView subtitle = (TextView) convertView.findViewById(R.id.list_item_status_meta);
			ImageView thumbnail = (ImageView) convertView.findViewById(R.id.list_item_status_thumbnail);
			thumbnail.setImageDrawable(getResources().getDrawable(getItem(position).mUser.mImageRes));
			subtitle.setText(DateFormat.getDateTimeInstance().format(new Date()));
			/*final ImageView imageButton = (ImageView) convertView.findViewById(R.id.list_item_popup);
			imageButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

				    PopupMenu popup = new PopupMenu(getContext(), imageButton);

				    // This activity implements OnMenuItemClickListener
				    popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
						
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							return false;
						}
					});
				    popup.inflate(R.menu.popup_actions);
				    popup.show();
				}
			});*/

			name.setText(getItem(position).mUser.mForename);
			message.setText(getItem(position).mMessage);
			
			return convertView;
		}
		
	}
}
