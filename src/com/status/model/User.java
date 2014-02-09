package com.status.model;

import java.io.Serializable;

public class User implements Serializable {
	
	public User(String forename, String surname, int imageRes){
		mForename = forename;
		mSurname = surname;
		mImageRes = imageRes;
	}

	public String mForename;
	
	public String mSurname;
	
	public String mImageUrl;
	
	public int mImageRes;

}
