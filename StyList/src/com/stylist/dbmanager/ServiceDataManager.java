package com.stylist.dbmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.stylist.databasemanager.DatabaseHelper;
import com.stylist.dataclasses.Appointment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class ServiceDataManager {

	private Context mContext;

	public ServiceDataManager(Context context) {
		this.mContext = context;
	}



	public  void setServiceDatainfo(Appointment app) {
		String query = null;
		SQLiteDatabase db = null;
		query = "INSERT INTO Table_servicelist (Server_id,first_name,last_name,adult_num,child_num,note,status,service_date,userID,phone_no) VALUES (?,?,?,?,?,?,?,?,?,?)";
		if(db== null){
			DatabaseHelper.init(mContext);
		}
		 db = DatabaseHelper.getSqliteDatabase();
		
		SQLiteStatement statement = db.compileStatement(query);
//		statement.bindLong(1, app.getCus_id());
		statement.bindLong(1, app.getServerid());
		statement.bindString(2, app.getFname());
		statement.bindString(3, app.getLname());
		statement.bindString(4, app.getAdult_count());
		statement.bindString(5, app.getChild_count());
		statement.bindString(6, app.getNote());
		statement.bindString(7, app.getStatus());
		statement.bindString(8, app.getScheduledate());
		statement.bindString(9, app.getUserid());
		statement.bindString(10, app.getPhoneNo());
		statement.execute();
		statement.close();
	try {
		DatabaseHelper.copyDatabaseToSdCard();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	public void updateServicedataInfo(Appointment app){
		
		SQLiteDatabase db = null;
		System.out.println("custID" +app.getCus_id());
		String queryUpdate = "UPDATE Table_servicelist SET first_name = ?,last_name=? ,phone_no = ? WHERE custid ='" +app.getCus_id()+"'";
		if(db== null){
			DatabaseHelper.init(mContext);
		}
		db = DatabaseHelper.getSqliteDatabase();
		SQLiteStatement statement = db.compileStatement(queryUpdate);
		statement.bindString(1, app.getFname());
		statement.bindString(2,app.getLname());
		statement.bindString(3,app.getPhoneNo());
		statement.execute();
		statement.close();
	}
	

	public List<Appointment> getServiceDetail()
	{
		List<Appointment> app=new ArrayList<Appointment>();
		
		try {
			String query = "SELECT * FROM  Table_servicelist" ;
			SQLiteDatabase db = DatabaseHelper.getSqliteDatabase();
			Cursor cur = DatabaseHelper.executeSelectQuery(db, query, null);
			String json = null;
			while (cur.moveToNext())
			{
				Appointment appointment=new Appointment();
				appointment.setFname(cur.getString(cur.getColumnIndexOrThrow("first_name")));
				System.out.println("cur.getColumnIndexOrThrow" + cur.getString(cur.getColumnIndexOrThrow("first_name")));
				appointment.setLname(cur.getString(cur.getColumnIndexOrThrow("last_name")));
				appointment.setAdult_count(cur.getString(cur.getColumnIndexOrThrow("adult_num")));
				appointment.setChild_count(cur.getString(cur.getColumnIndexOrThrow("child_num")));
				appointment.setNote(cur.getString(cur.getColumnIndexOrThrow("note")));
				appointment.setStatus(cur.getString(cur.getColumnIndexOrThrow("status")));
				appointment.setScheduledate(cur.getString(cur.getColumnIndexOrThrow("service_date")));
				appointment.setId(cur.getInt(cur.getColumnIndexOrThrow("userID")));
				appointment.setServerid(cur.getInt(cur.getColumnIndexOrThrow("Server_id")));
				appointment.setPhoneNo(cur.getString(cur.getColumnIndexOrThrow("phone_no")));
				appointment.setCus_id(cur.getInt(cur.getColumnIndexOrThrow("custid")));
				System.out.println("cur.getColumnIndexOrThrow"+cur.getInt(cur.getColumnIndexOrThrow("custid")));
				
				app.add(appointment);
				Log.v("", "appointment="+appointment);
			}
			System.out.println("App" +app);
			cur.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return app;
	}

}
