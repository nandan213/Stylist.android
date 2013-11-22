package com.stylist.dataclasses;

public class Customer {
	
	private String email="";
	private String pwd="";
	private String cnfpwd="";
	private String stylist="";
	private String referral="";
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCnfpwd() {
		return cnfpwd;
	}
	public void setCnfpwd(String cnfpwd) {
		this.cnfpwd = cnfpwd;
	}
	public String getStylist() {
		return stylist;
	}
	public void setStylist(String stylist) {
		this.stylist = stylist;
	}
	public String getReferral() {
		return referral;
	}
	public void setReferral(String referral) {
		this.referral = referral;
	}

}
