package com.anfroidclass;

import java.sql.Date;

public class User{
	int user_id;
	String user_name;
	String password;
	String personal_sign;
	String email;
	Date register_time;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int userId) {
		user_id = userId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPersonal_sign() {
		return personal_sign;
	}
	public void setPersonal_sign(String personalSign) {
		personal_sign = personalSign;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegister_time() {
		return register_time;
	}
	public void setRegister_time(Date registerTime) {
		register_time = registerTime;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int userId, String userName, String password,
		String email, Date registerTime) {
		super();
		user_id = userId;
		user_name = userName;
		this.password = password;
		this.email = email;
		register_time = registerTime;
	}
	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password
				+ ", personal_sign=" + personal_sign + ", register_time="
				+ register_time + ", user_id=" + user_id + ", user_name="
				+ user_name + "]";
	}
}
