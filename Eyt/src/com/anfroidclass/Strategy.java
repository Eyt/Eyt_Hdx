package com.anfroidclass;

import java.sql.Date;

public class Strategy {
	int strategy_id;
	String strategy_title;
	Date strategy_time;
    int good_priase_times;
    String strategy_context;
    String image_url;
    int user_id;
    int attractions_id;
    User use;
    Attraction att;
    String converDateToString;
    String username;
    
	public String getConverDateToString() {
		return converDateToString;
	}
	public void setConverDateToString(String converDateToString) {
		this.converDateToString = converDateToString;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getStrategy_id() {
		return strategy_id;
	}
	public void setStrategy_id(int strategyId) {
		strategy_id = strategyId;
	}
	public String getStrategy_title() {
		return strategy_title;
	}
	public void setStrategy_title(String strategyTitle) {
		strategy_title = strategyTitle;
	}
	public Date getStrategy_time() {
		return strategy_time;
	}
	public void setStrategy_time(Date strategyTime) {
		strategy_time = strategyTime;
	}
	public int getGood_priase_times() {
		return good_priase_times;
	}
	public void setGood_priase_times(int goodPriaseTimes) {
		good_priase_times = goodPriaseTimes;
	}
	public String getStrategy_context() {
		return strategy_context;
	}
	public void setStrategy_context(String strategyContext) {
		strategy_context = strategyContext;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String imageUrl) {
		image_url = imageUrl;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public int getAttractions_id() {
		return attractions_id;
	}
	public void setAttractions_id(int attractionsId) {
		attractions_id = attractionsId;
	}
	public User getUse() {
		return use;
	}
	public void setUse(User use) {
		this.use = use;
	}
	public Attraction getAtt() {
		return att;
	}
	public void setAtt(Attraction att) {
		this.att = att;
	}
	public Strategy(int strategyId, String strategyTitle, Date strategyTime,
			int goodPriaseTimes, String strategyContext, String imageUrl,
			int userId, int attractionsId, User use, Attraction att) {
		super();
		strategy_id = strategyId;
		strategy_title = strategyTitle;
		strategy_time = strategyTime;
		good_priase_times = goodPriaseTimes;
		strategy_context = strategyContext;
		image_url = imageUrl;
		user_id = userId;
		attractions_id = attractionsId;
		this.use = use;
		this.att = att;
	}
	public Strategy() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Strategy [att=" + att + ", attractions_id=" + attractions_id
				+ ", good_priase_times=" + good_priase_times + ", image_url="
				+ image_url + ", strategy_context=" + strategy_context
				+ ", strategy_id=" + strategy_id + ", strategy_time="
				+ strategy_time + ", strategy_title=" + strategy_title
				+ ", use=" + use + ", user_id=" + user_id + "]";
	}
	

    
   
}
