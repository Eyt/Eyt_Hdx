package com.anfroidclass;

import java.sql.Date;

public  class Strategy_Collect {
	 int strategy_collect_id;
	 Strategy stra=new Strategy();
	 User user=new User();
	 Strategy_Collect sc;
	 int user_id;
	 int strategy_id;
	 Date strategy_collect_update_time;
	
	public Strategy_Collect(Strategy stra, User user) {
		super();
		this.stra = stra;
		this.user = user;
	}
	
	public int getStrategy_Id() {
		// TODO Auto-generated method stub
		
		return stra.getStrategy_id();
	}
	public int getUser_Id() {
	
		return user.getUser_id();
	}
	public void setStrategy_Id(int straId) {
		stra.setStrategy_id(straId);
		
	}
	public void setUser_Id(int userId) {
		user.setUser_id(userId);
		
	}

	public int getStrategy_collect_id() {
		return strategy_collect_id;
	}

	public void setStrategy_collect_id(int strategyCollectId) {
		strategy_collect_id = strategyCollectId;
	}

	public Date getStrategy_collect_update_time() {
		return strategy_collect_update_time;
	}

	public void setStrategy_collect_update_time(Date strategyCollectUpdateTime) {
		strategy_collect_update_time = strategyCollectUpdateTime;
	}

	public Strategy_Collect() {
		super();
		// TODO Auto-generated constructor stub
	}

	 
}
