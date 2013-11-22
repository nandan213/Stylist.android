package com.stylist;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.slidingmenu.lib.SlidingMenu;
import com.stylist.adapter.ServiceListAdapter;
import com.stylist.core.GlobalData;
import com.stylist.dataclasses.Appointment;
import com.stylist.dataclasses.CustomMenu;
import com.stylist.dataclasses.CustomMenu.OnMenuItemSelectedListener;
import com.stylist.dataclasses.CustomMenuItem;
import com.stylist.dataclasses.User;
import com.stylist.utils.Common;

public class Servicelist extends Common implements OnClickListener ,OnMenuItemSelectedListener {

	private SlidingMenu menu;
	private int counter_adult=1,counter_child=0;
	private RelativeLayout rl_save,layNoItemsFound;
	private ListView list;
	private RelativeLayout rl_wall,rl_servicelist,rl_calender,rl_client,rl_activities;
	private GlobalData mGlobalData;
	private ProgressDialog dialog;
	Handler handler;
	private ServiceListAdapter adapter;
	User user;
	public static final int MENU_ITEM_1 = 1;
	public static final int MENU_ITEM_2 = 2;
	public static final int MENU_ITEM_3 = 3;
	public static final int MENU_ITEM_4 = 4;
	public static final int MENU_ITEM_5 = 5;
	private CustomMenu mMenu;
	boolean ismenuClosed;
	
	
	@Override
	public void setContentView(int id) {
		super.setContentView(id);

		View view = Servicelist.this.getLayoutInflater().inflate(
				R.layout.left_slide, null);

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffsetRes(R.dimen.sliding_limit);
		// menu.setFadeDegree(0.35f);
		//menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setTouchModeAbove(SlidingMenu.ABOVE);
		menu.setMenu(view);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		Common.inflateMenu(view, this);

		user = new User();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.servicelist);
		mGlobalData = (GlobalData) getApplicationContext();
		intiliazeTop();
		intiliaze();

		adapter=new ServiceListAdapter(Servicelist.this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, mGlobalData.getServicedetail());	
		list.setAdapter(adapter);

		handler=new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==123)
				{
					System.out.println("mGlobalData.getServicedetail()"+mGlobalData.getServicedetail());
					adapter=new ServiceListAdapter(Servicelist.this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, mGlobalData.getServicedetail());	
					list.setAdapter(adapter);
				}
			}
		};

	}

	private void intiliazeTop() {
		Button backButton, rightButton ,addppointment;
		TextView headerTextView;

		backButton = (Button) findViewById(R.id.backButton);
		rightButton = (Button) findViewById(R.id.rightButton);
		headerTextView = (TextView) findViewById(R.id.title_header);
		backButton.setOnClickListener(this);
		rightButton.setOnClickListener(this);
		addppointment = (Button) findViewById(R.id.addAppointment);
		addppointment.setOnClickListener(this);
		if(addppointment.getVisibility()==View.GONE){
			addppointment.setVisibility(View.VISIBLE);
		}


	}

	private void intiliaze()
	{
		dialog=new ProgressDialog(Servicelist.this);
		list=(ListView) findViewById(R.id.list_servicelist);
		layNoItemsFound = (RelativeLayout) findViewById(R.id.UseMyTokens);
		com.stylist.databasemanager.DatabaseHelper.init(this);
		mMenu = new CustomMenu(Servicelist.this, Servicelist.this, getLayoutInflater());
		mMenu.setHideOnSelect(true);
		mMenu.setItemsPerLineInPortraitOrientation(3);
		mMenu.setItemsPerLineInLandscapeOrientation(8);

		loadMenuItems();
	}
	private void doMenu() {
		if (mMenu.isShowing()) {
			mMenu.hide();
		} else {
			// Note it doesn't matter what widget you send the menu as long as
			// it gets view.
			View v = new View(Servicelist.this);
			mMenu.show(v);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backButton:
			menu.toggle();


			break;
		case R.id.addAppointment:
			Common.inflateRightMenu(R.layout.addanappointment, this ,"addappointMent");
			break;
		case R.id.adult_plus:
			counter_adult=counter_adult+1;
			Log.e("add counter", Integer.toString(counter_adult));
			adult_counter.setText(Integer.toString(counter_adult));
			break;

		case R.id.adult_minus:
			if(counter_adult==0)
			{
				//adult_counter.setText(Integer.toString(counter_adult));
			}else
			{
				counter_adult=counter_adult-1;
				Log.e("add counter", Integer.toString(counter_adult));
				adult_counter.setText(Integer.toString(counter_adult));
			}
			break;

		case R.id.child_plus:
			counter_child=counter_child+1;
			child_counter.setText(Integer.toString(counter_child));
			break;

		case R.id.child_minus:
			if(counter_child==0)
			{

			}else
			{
				counter_child=counter_child-1;
				child_counter.setText(Integer.toString(counter_child));
			}
			break;

		case R.id.edit_save:


			Appointment appointment=validateInput();
			if(appointment!=null)
			{
				new InsertDetail(appointment).execute();
				//mGlobalData.setServicedetail(appointment);

			}else
			{

			}
			/*if(menu1!=null)
			{
				menu1.toggle();
			}*/


			break;

		case R.id.calender:

			if (menu != null) {
				menu.toggle();
			}

			Intent calender = new Intent(Servicelist.this, Calender.class);
			Servicelist.this.finish();
			startActivity(calender);
			break;

		case R.id.wall:

			if (menu != null) {
				menu.toggle();
			}

			Intent home = new Intent(Servicelist.this, Home.class);
			Servicelist.this.finish();
			startActivity(home);
			break;

		case R.id.scheduletime:
			Calendar calender1 = Calendar.getInstance();
			DatePickerDialog datePicker = new DatePickerDialog(
					Servicelist.this, new MyDateListener(), calender1
					.get(Calendar.YEAR), calender1
					.get(Calendar.MONTH), calender1
					.get(Calendar.DAY_OF_MONTH));

			datePicker.show();
			break;

		default:
			break;
		}
	}

	

	class MyDateListener implements OnDateSetListener {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub


			Calendar today = Calendar.getInstance();
			//today.set(Calendar.HOUR, 0);
			//today.set(Calendar.MINUTE, 0);
			//today.set(Calendar.SECOND ,0);
			Calendar setDate = Calendar.getInstance();
			setDate.set(Calendar.YEAR, year);
			setDate.set(Calendar.MONTH, monthOfYear);
			setDate.set(Calendar.DATE, dayOfMonth);


			if (setDate.before(today)) {

				makeToast("Schedule date should be greater or equal to the present date.");
				return;

			}
			else{
				String time_test = "";
				try {
					time_test = time_format();
					time_test.toLowerCase();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//edtSignupBday.setText(year + "-" + (monthOfYear + 1) + "-"+ dayOfMonth);
				schedule.setText(  (monthOfYear + 1) + "/"+ dayOfMonth+ "/" +  year+" "+time_test);
			}
		}

	}

	private Appointment validateInput()
	{
		Appointment appointment=null;

		String fna=fname.getText().toString();
		fna=fna.trim();
		String lna=lname.getText().toString();
		lna=lna.trim();
		String sch=schedule.getText().toString();
		sch=sch.trim();
		String acoun=adult_counter.getText().toString();
		acoun=acoun.trim();
		String ccount=child_counter.getText().toString();
		ccount=ccount.trim();
		String not=note.getText().toString();
		not=not.trim();
		if(TextUtils.isEmpty(fna))
		{
			fname.setError("Provide firstname.");
			return appointment;
		}

		if(TextUtils.isEmpty(lna))
		{
			lname.setError("Provide lastname.");
			return appointment;
		}

		if(TextUtils.isEmpty(sch))
		{
			schedule.setError("provide a schdule date.");
			return appointment;
		}
		String uid=mGlobalData.getuserid();
		if(TextUtils.isEmpty(uid))
		{
			makeToast("Invalid user id.");
			return appointment;
		}

		appointment=new Appointment();
		appointment.setFname(fna);
		appointment.setLname(lna);
		appointment.setAdult_count(acoun);
		appointment.setChild_count(ccount);
		appointment.setScheduledate(sch);
		appointment.setNote(not);
		appointment.setUserid(uid);
		appointment.setStatus("Ready");
		appointment.setServerid(0);
		appointment.setPhoneNo(user.getPhone());
		System.out.println("UserPhone" +user.getPhone());
		return appointment;
	}

	/*toast method :
	 * to Show the toast message whenever is required
	 * */

	private void makeToast(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(Servicelist.this, string, Toast.LENGTH_SHORT).show();
	}

	private String time_format()
	{

		SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm aa");
		Calendar c=Calendar.getInstance();
		String target = "";
		try {
			target = sdf1.format(c.getTime());
			target.toLowerCase();
			System.out.println("data"+target.toLowerCase());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return target.toLowerCase(); 
	}


	private class InsertDetail extends AsyncTask<Void, Void, Void>
	{
		Appointment appointment;
		InsertDetail(Appointment app)
		{
			this.appointment=app;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setMessage("Please wait...");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			mGlobalData.setServicedetail(appointment);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(dialog!=null)
			{
				dialog.cancel();
			}
			if(Common.menu!=null){
				Common.menu.toggle();
			}
			handler.sendEmptyMessage(123);
		}
	}
	private void loadMenuItems() {
		ArrayList<CustomMenuItem> menuItems = new ArrayList<CustomMenuItem>();
		CustomMenuItem cmi = new CustomMenuItem();
		cmi.setCaption("Settings");
		cmi.setImageResourceId(R.drawable.userpicsmall);
		cmi.setId(MENU_ITEM_1);
		menuItems.add(cmi);
		cmi = new CustomMenuItem();
		cmi.setCaption("Log Out");
		cmi.setImageResourceId(R.drawable.userpicsmall);
		cmi.setId(MENU_ITEM_2);
		menuItems.add(cmi);
		/*
		 * cmi = new CustomMenuItem(); cmi.setCaption("Report an Incident");
		 * cmi.setImageResourceId(R.drawable.sk_bbar_complaint_ico_d);
		 * cmi.setId(MENU_ITEM_3); menuItems.add(cmi); cmi = new
		 * CustomMenuItem(); cmi.setCaption("View Councils");
		 * cmi.setImageResourceId(R.drawable.sk_bbar_council_ico_d);
		 * cmi.setId(MENU_ITEM_4); menuItems.add(cmi); cmi = new
		 * CustomMenuItem(); cmi.setCaption("Settings");
		 * cmi.setImageResourceId(R.drawable.sk_bbar_settings_ico_d);
		 * cmi.setId(MENU_ITEM_5); menuItems.add(cmi);
		 */
		if (!mMenu.isShowing())
			try {
				mMenu.setMenuItems(menuItems);
			} catch (Exception e) {
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Egads!");
				alert.setMessage(e.getMessage());
				alert.show();
			}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	
		try {
//			if( menu!=null || Common.menu!=null){
//				if(menu.isMenuShowing() || Common.menu.isMenuShowing()){
//					ismenuClosed = true;
//					if(menu !=null){
//						menu.toggle();
//					}
//					if(Common.menu !=null){
//						Common.menu.toggle();
//					}
//				}  
//			}else{
				this.finish();
				Intent homeIntent = new Intent(this, Home.class);
				startActivity(homeIntent);
				
		//	}
		
				
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	//	super.onBackPressed();
	}

	@Override
	public void MenuItemSelectedEvent(CustomMenuItem selection) {

		if(selection.getId()== MENU_ITEM_2){
			mGlobalData.setLoggedin("false");
			this.finish();
		} 
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			doMenu();
			return true; // always eat it!
		}
		return super.onKeyDown(keyCode, event);
	}
}
