package com.status.model;

import java.io.Serializable;

public class Status implements Serializable {
	
	public Status(int id, User user, String message, Location location){
		mId = id;
		mUser = user;
		mMessage = message;
		mLocation = location;
	}

	public int mId;
	
	public User mUser;
	
	public String mMessage;
	
	public Location mLocation;

}
