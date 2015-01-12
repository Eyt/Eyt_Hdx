package com.anfroidclass;

import java.sql.Date;

public class City {
	int city_id;
	String city_name;
	Date city_update_time;
	int user_id;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int userId) {
		user_id = userId;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int cityId) {
		city_id = cityId;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String cityName) {
		city_name = cityName;
	}

	public Date getCity_update_time() {
		return city_update_time;
	}

	public void setCity_update_time(Date cityUpdateTime) {
		city_update_time = cityUpdateTime;
	}

	public City() {
		super();
		// TODO Auto-generated constructor stub
	}

	public City(int cityId, String cityName, Date cityUpdateTime) {
		super();
		city_id = cityId;
		city_name = cityName;
		city_update_time = cityUpdateTime;
	}

}
