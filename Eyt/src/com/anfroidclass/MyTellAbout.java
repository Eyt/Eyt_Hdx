package com.anfroidclass;

import java.sql.Date;

public class MyTellAbout {
	int tell_id;
	Date tell_time;
	String tell_context;
	int user_id;
	User use;
	String my_tell_about_image_url;
    String username;
    String dateToString;
    String imageUrl;
    
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getDateToString() {
		return dateToString;
	}
	public void setDateToString(String dateToString) {
		this.dateToString = dateToString;
	}
	
	public String getUsername() {//发说说的用户名
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getTell_id() {
		return tell_id;
	}
	public void setTell_id(int tellId) {
		tell_id = tellId;
	}
	public Date getTell_time() {
		return tell_time;
	}
	public void setTell_time(Date tellTime) {
		tell_time = tellTime;
	}
	public String getTell_context() {
		return tell_context;
	}
	public void setTell_context(String tellContext) {
		tell_context = tellContext;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public User getUse() {
		return use;
	}
	public void setUse(User use) {
		this.use = use;
	}
	public String getMy_tell_about_image_url() {
		return my_tell_about_image_url;
	}
	public void setMy_tell_about_image_url(String myTellAboutImageUrl) {
		my_tell_about_image_url = myTellAboutImageUrl;
	}
	public MyTellAbout(int tellId, Date tellTime, String tellContext,
			int userId, User use, String myTellAboutImageUrl) {
		super();
		tell_id = tellId;
		tell_time = tellTime;
		tell_context = tellContext;
		user_id = userId;
		this.use = use;
		my_tell_about_image_url = myTellAboutImageUrl;
	}
	public MyTellAbout() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MyTellAbout [my_tell_about_image_url="
				+ my_tell_about_image_url + ", tell_context=" + tell_context
				+ ", tell_id=" + tell_id + ", tell_time=" + tell_time
				+ ", use=" + use + ", user_id=" + user_id + "]";
	}
	
	
}
