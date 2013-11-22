package com.stylist.core;




import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.stylist.dataclasses.Appointment;
import com.stylist.dataclasses.User;
import com.stylist.dataclasses.UserShop;
import com.stylist.dbmanager.CustomerDataManager;
import com.stylist.dbmanager.ServiceDataManager;

public class GlobalData extends Application {
	
	public void setRole(int post) {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("role", post);
		editor.commit();

	}
	
	public int getRole() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		int user_id=prefs.getInt("role", 0);
		return user_id;
	}
	
	public void setuserid(String userid) {

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("user_id", userid);
		
		editor.commit();

	}
	public String getuserid() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String user_id=prefs.getString("user_id", "");
		//Float flag = prefs.getFloat("rotationangle", 0.f);
		return user_id;
	}
	public void setCustomerinfo(String userid,String email,String usertype,String flag) {
		// this.user = user;
		
		CustomerDataManager gm=null;
		try {
			gm = new CustomerDataManager(getApplicationContext());
			gm.setCustomerinfo(userid,email,usertype,flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			gm=null;
			System.gc();
		}
		
	}
	
	public void setUserDetail(User user)
	{
		CustomerDataManager gm=null;
		try {
			gm = new CustomerDataManager(getApplicationContext());
			gm.setUserDetail(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			gm=null;
			System.gc();
		}
	}
	
	public User getUserDetail()
	{
		CustomerDataManager gm=null;
		User user=null;
		try {
			gm = new CustomerDataManager(getApplicationContext());
			user=gm.getUserDetail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			gm=null;
			System.gc();
		}
		
		return user;
	}
	
	public void setUserShopDetail(UserShop user)
	{
		CustomerDataManager gm=null;
		try {
			gm = new CustomerDataManager(getApplicationContext());
			gm.setUsershopdetail(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			gm=null;
			System.gc();
		}
	}
	
	public UserShop getUserShopDetail()
	{
		CustomerDataManager gm=null;
		UserShop user=null;
		try {
			gm = new CustomerDataManager(getApplicationContext());
			user=gm.getShopDetail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			gm=null;
			System.gc();
		}
		return user;
	}
	
	public String getisFirstTime()
	{
		CustomerDataManager gm=null;
		String isSync = "false";
		try {
			gm = new CustomerDataManager(getApplicationContext());
			isSync=gm.getisFirstTime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isSync!=null)
		{
			
		}else
		{
			isSync="false";
		}
		return isSync;
	}
	
	public void setIsFirstTime(String flag)
	{
		CustomerDataManager gm=null;
		try {
			gm = new CustomerDataManager(getApplicationContext());
			gm.setisFirstTime(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getLoggedin()
	{
		CustomerDataManager gm=null;
		String isSync = "false";
		try {
			gm = new CustomerDataManager(getApplicationContext());
			isSync=gm.getloggedin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isSync!=null)
		{
			
		}else
		{
			isSync="false";
		}
		return isSync;
	}
	
	public void setLoggedin(String flag)
	{
		CustomerDataManager gm=null;
		try {
			gm = new CustomerDataManager(getApplicationContext());
			gm.setLoggedin(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setServicedetail(Appointment app)
	{
		ServiceDataManager service=null;
		try {
			service=new ServiceDataManager(getApplicationContext());
			service.setServiceDatainfo(app);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			service=null;
			System.gc();
		}
	}
	
	
	public void updateservicedetailInfo(Appointment app){
		ServiceDataManager service=null;
		try {
			service=new ServiceDataManager(getApplicationContext());
			service.updateServicedataInfo(app);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			service=null;
			System.gc();
		}
	}
	
	
	public List<Appointment> getServicedetail()
	{
		ServiceDataManager service=null;
		List<Appointment> app=null;
		try {
			service=new ServiceDataManager(getApplicationContext());
			app=service.getServiceDetail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			service=null;
			System.gc();
		}
		return app;
	}
	

}
