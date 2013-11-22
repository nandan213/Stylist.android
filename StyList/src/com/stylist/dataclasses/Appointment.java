package com.stylist.dataclasses;

public class Appointment {
	
	private String fname="";
	private String lname="";
	private String adult_count="";
	private String child_count="";
	private String note="";
	private String scheduledate="";
	private String userid="";
	private String status="";
	private Integer serverid=0;
	private Integer cus_id=0;
	private String phoneNo;
	
	public Integer getServerid() {
		return serverid;
	}
	public void setServerid(Integer serverid) {
		this.serverid = serverid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private Integer id=0;
	
	public Integer getCus_id() {
		return cus_id;
	}
	public void setCus_id(Integer cus_id) {
		this.cus_id = cus_id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getAdult_count() {
		return adult_count;
	}
	public void setAdult_count(String adult_count) {
		this.adult_count = adult_count;
	}
	public String getChild_count() {
		return child_count;
	}
	public void setChild_count(String child_count) {
		this.child_count = child_count;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getScheduledate() {
		return scheduledate;
	}
	public void setScheduledate(String scheduledate) {
		this.scheduledate = scheduledate;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
