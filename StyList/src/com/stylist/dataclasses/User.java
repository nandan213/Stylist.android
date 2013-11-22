package com.stylist.dataclasses;

public class User {
	
	private  byte imageInByte[]=null;
	private String fname="";
	private String lname="";
	private String nickname="";
	private String phone="";
	private String mission="";
	private String imagepath="";
	
	public String getImagepath() {
		return imagepath;
	}
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}
	public byte[] getImageInByte() {
		return imageInByte;
	}
	public void setImageInByte(byte[] imageInByte) {
		this.imageInByte = imageInByte;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}

}
