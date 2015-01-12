package com.anfroidclass;

public class AttractionAndStrategy_AS {
	 Strategy stra=new Strategy();
	 Attraction attra=new Attraction();
	 Attraction_Strategy as=new Attraction_Strategy();
	 User user=new User();
	 String upUserName;
	 String converDateToString;
	 
	 public String getUpUserName() {
		return upUserName;
	}
	public void setUpUserName(String upUserName) {
		this.upUserName = upUserName;
	}
	public String getConverDateToString() {
		return converDateToString;
	}
	public void setConverDateToString(String converDateToString) {
		this.converDateToString = converDateToString;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	int stra_attrId;
	 int attraId;
	 int as_attrId;
	public Strategy getStra() {
		return stra;
	}
	public void setStra(Strategy stra) {
		this.stra = stra;
	}
	public Attraction getAttra() {
		return attra;
	}
	public void setAttra(Attraction attra) {
		this.attra = attra;
	}
	public Attraction_Strategy getAs() {
		return as;
	}
	public void setAs(Attraction_Strategy as) {
		this.as = as;
	}
	public int getStra_attrId() {
		return stra.getAttractions_id();
	}
	public void setStra_attrId(int straAttrId) {
		stra.setAttractions_id(straAttrId);
	}
	public int getAttraId() {
		return attra.getAttractions_id();
	}
	public void setAttraId(int attraId) {
	    attra.setAttractions_id(attraId);
	}
	public int getAs_attrId() {
		return as.getAttra_id();
	}
	public void setAs_attrId(int asAttrId) {
		as.setAttra_id(asAttrId);
	}
	
	public AttractionAndStrategy_AS(Strategy stra, Attraction attra,
			Attraction_Strategy as,User user) {
		super();
		this.stra = stra;
		this.attra = attra;
		this.as = as;
		this.user = user;
	}
	public AttractionAndStrategy_AS() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
}
