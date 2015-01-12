package com.anfroidclass;

public class UserAndStrategy_SC {
	User user=new User();
	Strategy stra=new Strategy();
	Strategy_Collect sc=new Strategy_Collect();
	int user_id;
	int stra_user_id;
	int sc_user_id;
	String converDateToString;
	String attr_Name;
    String username;
    String collectDate;
    String collectCollectDate;
	
	public String getCollectCollectDate() {
		return collectCollectDate;
	}
	public void setCollectCollectDate(String collectCollectDate) {
		this.collectCollectDate = collectCollectDate;
	}
	public String getCollectDate() {
		return collectDate;
	}
	public void setCollectDate(String collectDate) {
		this.collectDate = collectDate;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAttr_Name() {
		return attr_Name;
	}
	public void setAttr_Name(String attrName) {
		attr_Name = attrName;
	}
	public String getConverDateToString() {
		return converDateToString;
	}
	public void setConverDateToString(String converDateToString) {
		this.converDateToString = converDateToString;
	}
	public int getUser_id() {
		
		return user.getUser_id();
	}
	public void setUser_id(int userId) {
		
		user.setUser_id(userId);
	}
	public int getStra_user_id() {

		return stra.getUser_id();
	}
	public void setStra_user_id(int straUserId) {
		
		stra.setUser_id(straUserId);
	}
	public int getSc_user_id() {
		
		return sc.getUser_Id();
	}
	public void setSc_user_id(int scUserId) {
		
		sc.setUser_Id(scUserId);
	}
	public UserAndStrategy_SC() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserAndStrategy_SC(User user, Strategy stra, Strategy_Collect sc) {
		super();
		this.user = user;
		this.stra = stra;
		this.sc = sc;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Strategy getStra() {
		return stra;
	}
	public void setStra(Strategy stra) {
		this.stra = stra;
	}
	public Strategy_Collect getSc() {
		return sc;
	}
	public void setSc(Strategy_Collect sc) {
		this.sc = sc;
	}

}
