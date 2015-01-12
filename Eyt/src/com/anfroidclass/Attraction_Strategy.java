package com.anfroidclass;

import java.sql.Date;

public class Attraction_Strategy  {
	 int attractions_strategy_id;
	 Strategy stra=new Strategy() ;
	 Attraction attra=new Attraction();
	 Attraction_Strategy as;
	 int attra_id;
	 int strategy_id;
	 Date update_time;
	public int getAttra_id() {
		// TODO Auto-generated method stub
		return attra.getAttractions_id();
	}
	public int getStrategy_Id() {
		// TODO Auto-generated method stub
		return stra.getStrategy_id();
	}
	public void setAttra_id(int attraId) {
		attra.setAttractions_id(attraId);
		
	}
	public void setStrategy_Id(int straId) {
		stra.setStrategy_id(straId);
		
	}
	public Attraction_Strategy(Strategy stra, Attraction attra) {
		super();
		this.stra = stra;
		this.attra = attra;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date updateTime) {
		update_time = updateTime;
	}
	public int getAttractions_strategy_id() {
		return attractions_strategy_id;
	}
	public void setAttractions_strategy_id(int attractionsStrategyId) {
		attractions_strategy_id = attractionsStrategyId;
	}
	public Attraction_Strategy() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
