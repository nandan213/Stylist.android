package com.stylist.dbmanager;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.stylist.databasemanager.DatabaseHelper;
import com.stylist.dataclasses.User;
import com.stylist.dataclasses.UserShop;

public class CustomerDataManager {

	private Context mContext;

	public CustomerDataManager(Context context) {
		this.mContext = context;
	}

	public void setCustomerinfo(String userid,String email,String user_type,String flag)
	{
		SQLiteDatabase db = null ;

		if(db ==null){

			DatabaseHelper.init(mContext);
		}
		db = DatabaseHelper.getSqliteDatabase();

		try {
			int updateStatus = 0;
			db = DatabaseHelper.getSqliteDatabase();
			String query = "select * from customerinfo";

			Cursor cursorData = DatabaseHelper.executeSelectQuery(db, query, null);
			String message = null;

			if (cursorData.getCount() == 0)
			{
				System.out.println("printing the value +" +userid + email + user_type + flag);
				String queryIntsert = "INSERT INTO customerinfo (userid,email,user_type,isfirsttime) VALUES (?,?,?,?)";
				SQLiteStatement statement = db.compileStatement(queryIntsert);
				statement.bindString(1, userid);
				statement.bindString(2, email);
				statement.bindString(3, user_type);
				statement.bindString(4, flag);
				statement.execute();
				statement.close();
			}else
			{
				String queryUpdate = "UPDATE customerinfo SET userid =? ,email=?,user_type=?,isfirsttime=?" ;
				SQLiteStatement statement = db.compileStatement(queryUpdate);
				statement.bindString(1, userid);
				statement.bindString(2, email);
				statement.bindString(3, user_type);
				statement.bindString(4, flag);
				statement.execute();
				statement.close();
				System.out.println("setCustomer updateStatus: " + updateStatus);
			}
			cursorData.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//






	public void setUserDetail(User user)
	{
		SQLiteDatabase db= null;

		db = DatabaseHelper.getSqliteDatabase();
		if(db ==null){

			DatabaseHelper.init(mContext);
		}

		byte imageInByte[] = null;
		try {
			int updateStatus = 0;
			//			helper = new DbHelper(mContext);
			//			//helper.openDataBase();
			//			db = helper.getWritableDatabase();
			db = DatabaseHelper.getSqliteDatabase();
			String query = "select * from userdetail";
			Cursor cursorData = com.stylist.databasemanager.DatabaseHelper.executeSelectQuery(db, query, null);

			if (cursorData.getCount() == 0)
			{

				String queryinsert = "INSERT INTO userdetail (imagepath,user_firstname,user_lastname,user_nickname,user_phone,user_mission) VALUES (?,?,?,?,?,?)";
				SQLiteStatement statement = db.compileStatement(queryinsert);
				statement.bindString(1, user.getImagepath());
				statement.bindString(2, user.getFname());
				statement.bindString(3,user.getLname());
				statement.bindString(4, user.getNickname());
				statement.bindString(5, user.getPhone());
				statement.bindString(6,  user.getMission());
				statement.execute();
				statement.close();
				DatabaseHelper.copyDatabaseToSdCard();


			}else
			{
				String queryUpdate = "UPDATE userdetail SET imagepath=? ,user_firstname=?,user_lastname=?,user_nickname =? ,user_phone=?,user_mission=?";
				SQLiteStatement statement = db.compileStatement(queryUpdate);
				statement.bindString(1, user.getImagepath());
				statement.bindString(2, user.getFname());
				statement.bindString(3,user.getLname());
				statement.bindString(4, user.getNickname());
				statement.bindString(5, user.getPhone());
				statement.bindString(6,  user.getMission());
				statement.execute();
				statement.close();
				DatabaseHelper.copyDatabaseToSdCard();
			}
			cursorData.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	public User getUserDetail()
	{
		User use=new User();
		SQLiteDatabase db = null;
		try {
			if(db ==null){
				DatabaseHelper.init(mContext);
			}
			String query;
			query = "SELECT * FROM  userdetail" ;
			db = DatabaseHelper.getSqliteDatabase();


			Cursor cur = DatabaseHelper.executeSelectQuery(db, query, null);
			String json = null;
			while (cur.moveToNext()) {
				Log.e("path", cur.getString(0));
				use.setImagepath(cur.getString(cur.getColumnIndexOrThrow("imagepath")));
				use.setFname(cur.getString(cur.getColumnIndexOrThrow("user_firstname")));
				use.setLname(cur.getString(cur.getColumnIndexOrThrow("user_lastname")));
				use.setNickname(cur.getString(cur.getColumnIndexOrThrow("user_nickname")));
				use.setPhone(cur.getString(cur.getColumnIndexOrThrow("user_phone")));
				use.setMission(cur.getString(cur.getColumnIndexOrThrow("user_mission")));
				System.out.println("user.getImagepath()" +cur.getColumnIndexOrThrow("imagepath"));
				System.out.println("user.getImagepath()" +cur.getColumnIndexOrThrow("user_firstname"));
			}
			cur.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return use; 

	}


	public void setUsershopdetail(UserShop user)
	{
		SQLiteDatabase db = null;
		//DbHelper helper=null;
		try {
			int updateStatus = 0;
			if(db ==null){
				DatabaseHelper.init(mContext);	
			}

			db = DatabaseHelper.getSqliteDatabase();


			String queryinput = "select * from User_Shop_Table";
			Cursor cursorData = com.stylist.databasemanager.DatabaseHelper.executeSelectQuery(db, queryinput, null);


			if (cursorData.getCount() == 0)
			{

				String queryinsert = null;
				queryinsert = "INSERT INTO User_Shop_Table (shopname,shopaddres,shopcity,shopstate,shopzip) VALUES (?,?,?,?,?)";
				SQLiteStatement statement = db.compileStatement(queryinsert);
				statement.bindString(1, user.getShopname());
				statement.bindString(2, user.getShopadd());
				statement.bindString(3,user.getShopcity());
				statement.bindString(4, user.getShopstate());
				statement.bindString(5, user.getShopzip());
				statement.execute();
				statement.close();

			}else
			{
				String queryUpdate = "UPDATE User_Shop_Table SET shopname = ?,shopaddres=?,shopcity=?,shopstate=? ,shopzip = ?";
				SQLiteStatement statement = db.compileStatement(queryUpdate);
				statement.bindString(1, user.getShopname());
				statement.bindString(2, user.getShopadd());
				statement.bindString(3,user.getShopcity());
				statement.bindString(4, user.getShopstate());
				statement.bindString(5, user.getShopzip());
				statement.execute();
				statement.close();


				System.out.println("setusershopdetail updateStatus: " + updateStatus);
			}
			cursorData.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public UserShop getShopDetail()
	{
		UserShop use=new UserShop();
		SQLiteDatabase db = null;

		try {

			//helper.openDataBase();
			if(db ==null){
				DatabaseHelper.init(mContext);	
			}
			String query = "SELECT * FROM  User_Shop_Table" ;
			db = DatabaseHelper.getSqliteDatabase();

			Cursor cur = DatabaseHelper.executeSelectQuery(db, query, null);

			String json = null;

			while (cur.moveToNext()) {
				use.setShopname(cur.getString(cur.getColumnIndexOrThrow("shopname")));
				use.setShopadd(cur.getString(cur.getColumnIndexOrThrow("shopaddres")));
				use.setShopcity(cur.getString(cur.getColumnIndexOrThrow("shopcity")));
				use.setShopstate(cur.getString(cur.getColumnIndexOrThrow("shopstate")));
				use.setShopzip(cur.getString(cur.getColumnIndexOrThrow("shopzip")));
			}
			cur.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return use; 

	}

	public void setisFirstTime(String isLogin) {

		if (isLogin == null) {
			isLogin = "true";
		}
		SQLiteDatabase db = null;
		if(db ==null){
			DatabaseHelper.init(mContext);
		}

		try {

			int updateStatus = 0;
			db = DatabaseHelper.getSqliteDatabase();
			String queryUpdate = "UPDATE customerinfo SET isfirsttime = ?";
			SQLiteStatement statement = db.compileStatement(queryUpdate);
			statement.bindString(1, isLogin);
			statement.execute();
			statement.close();
			System.out.println("Update Status : " + updateStatus);

		} catch (Exception e) {

		}
	}

	public String getisFirstTime() {

		String item = "false";

		SQLiteDatabase db = null;
		try {

			if(db ==null){

				DatabaseHelper.init(mContext);
			}
			String query = "SELECT * FROM  customerinfo" ;
			db = DatabaseHelper.getSqliteDatabase();

			Cursor cur = DatabaseHelper.executeSelectQuery(db, query, null);

			String json = null;
			while (cur.moveToNext()) {
				json = cur.getString(cur.getColumnIndexOrThrow("isfirsttime"));
			}
			cur.close();

			if (json != null && json.length() > 0) {
				System.out.println("the json value is........."+json);
				if(json.equals("1"))
				{
					item="true";
				}else if(json.equals("0"))
				{
					item="fasle";	
				}else
				{
					item = json;// gson.fromJson(json,
				}
				// item.getClass());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 


		return item;
	}

	public void setLoggedin(String isLogin) {

		if (isLogin == null) {
			isLogin = "false";
		}
		SQLiteDatabase db = null;
		if(db ==null){

			DatabaseHelper.init(mContext);
		}
		try {

			int updateStatus = 0;

			db = DatabaseHelper.getSqliteDatabase();
			String queryUpdate = "UPDATE customerinfo SET isloggedin = ?";
			SQLiteStatement statement = db.compileStatement(queryUpdate);
			statement.bindString(1, isLogin);
			statement.execute();
			statement.close();
			System.out.println("Update Status : " + updateStatus);

		} catch (Exception e) {

		}

	}

	public String getloggedin() {

		String item = "false";
		SQLiteDatabase db = null;

		try {
			if(db ==null){

				DatabaseHelper.init(mContext);
			}

			String query = "SELECT * FROM  customerinfo" ;
			db = DatabaseHelper.getSqliteDatabase();
			Cursor cur = DatabaseHelper.executeSelectQuery(db, query, null);

			String json = null;
			while (cur.moveToNext()) {
				json = cur.getString(cur.getColumnIndexOrThrow("isloggedin"));
			}


			if (json != null && json.length() > 0) {
				System.out.println("the json value is........."+json);
				if(json.equals("1"))
				{
					item="true";
				}else if(json.equals("0"))
				{
					item="false";	
				}else
				{
					item = json; 
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return item;
	}

}
