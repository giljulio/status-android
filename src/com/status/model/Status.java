package com.status.model;

public class Status {
	
	public Status(User user, String message){
		mUser = user;
		mMessage = message;
	}

	public User mUser;
	
	public String mMessage;

}
