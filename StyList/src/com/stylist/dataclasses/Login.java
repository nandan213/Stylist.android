package com.stylist.dataclasses;

public class Login {
	
	private String status="";
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getFirst_login() {
		return first_login;
	}
	public void setFirst_login(String first_login) {
		this.first_login = first_login;
	}
	private String message="";
	private String userID="";
	private String email="";
	private String user_type="";
	private String first_login="";

}
