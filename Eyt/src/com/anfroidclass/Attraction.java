package com.anfroidclass;

import java.sql.Date;

public class Attraction {
	int attractions_id;
	//int strategy_id;
	String attractions_name;
	String image_url;
	String attractions_spots;
	int want_to;
	Date attractions_update_time;
	public int getAttractions_id() {
		return attractions_id;
	}
	public void setAttractions_id(int attractionsId) {
		attractions_id = attractionsId;
	}
	public String getAttractions_name() {
		return attractions_name;
	}
	public void setAttractions_name(String attractionsName) {
		attractions_name = attractionsName;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String imageUrl) {
		image_url = imageUrl;
	}
	public String getAttractions_spots() {
		return attractions_spots;
	}
	public void setAttractions_spots(String attractionsSpots) {
		attractions_spots = attractionsSpots;
	}
	public int getWant_to() {
		return want_to;
	}
	public void setWant_to(int wantTo) {
		want_to = wantTo;
	}
	public Date getAttractions_update_time() {
		return attractions_update_time;
	}
	public void setAttractions_update_time(Date attractionsUpdateTime) {
		attractions_update_time = attractionsUpdateTime;
	}
	public Attraction(int attractionsId, int strategyId,
			String attractionsName, String imageUrl, String attractionsSpots,
			int wantTo, Date attractionsUpdateTime) {
		super();
		attractions_id = attractionsId;
		attractions_name = attractionsName;
		image_url = imageUrl;
		attractions_spots = attractionsSpots;
		want_to = wantTo;
		attractions_update_time = attractionsUpdateTime;
	}
	public Attraction() {
		super();
		// TODO Auto-generated constructor stub
	}

}
