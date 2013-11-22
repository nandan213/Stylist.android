package com.stylist;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;
import com.stylist.core.GlobalData;
import com.stylist.dataclasses.CustomMenu;
import com.stylist.dataclasses.CustomMenu.OnMenuItemSelectedListener;
import com.stylist.dataclasses.CustomMenuItem;
import com.stylist.utils.Common;

public class Calender extends FragmentActivity implements OnClickListener ,OnMenuItemSelectedListener{

	private GridView mGridView;
	private ProgressBar pb;
	public Calendar month;
	static final int FIRST_DAY_OF_WEEK = 0;
	private NotesCalendarAdapter adapter;
	private View view=null,view1=null;
	private SlidingMenu menu,menu1;
	private RelativeLayout rl_wall,rl_servicelist,rl_calender,rl_client,rl_activities;
	private EditText fname,lname,adult_counter,child_counter,note,schedule;
	private int counter_adult=1,counter_child=0;
	private Button plus,minus,plus1,minus1;
	RelativeLayout rl_save;
	public static final int MENU_ITEM_1 = 1;
	public static final int MENU_ITEM_2 = 2;
	public static final int MENU_ITEM_3 = 3;
	public static final int MENU_ITEM_4 = 4;
	public static final int MENU_ITEM_5 = 5;
	private CustomMenu mMenu;
	private GlobalData mGlobalData;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.calendar_activity);
		mGlobalData = (GlobalData) getApplicationContext();
		
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		intiliazeTop();
		intiliaze();
		view = Calender.this.getLayoutInflater().inflate(R.layout.left_slide, null);
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffsetRes(R.dimen.sliding_limit);
		// menu.setFadeDegree(0.35f);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setMenu(view);
		
		
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		intilazeleft(view);
		
	}
	
	
	
	private void intiliazeTop() {
		Button backButton, rightButton;
		TextView headerTextView;
		backButton = (Button) findViewById(R.id.backButton);
		rightButton = (Button) findViewById(R.id.rightButton);
		backButton.setVisibility(View.GONE);
		//backButton.setText("More");
		 rightButton.setText("add");
		headerTextView = (TextView) findViewById(R.id.title_header);
		 headerTextView.setText(getString(R.string.calender_txt));

		backButton.setOnClickListener(this);
		rightButton.setOnClickListener(this);
	}
	
	
	
	private void intiliaze()
	{
		pb = (ProgressBar) findViewById(R.id.pb);
		mGridView = (GridView) findViewById(R.id.cal_gridview);
		month = Calendar.getInstance();
		adapter = new NotesCalendarAdapter(month);
		mGridView.setAdapter(adapter);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
		ImageView previous = (ImageView) findViewById(R.id.previous);
		ImageView next = (ImageView) findViewById(R.id.next);
		previous.setOnClickListener(this);
		next.setOnClickListener(this);
		
		mMenu = new CustomMenu(Calender.this, Calender.this, getLayoutInflater());
		mMenu.setHideOnSelect(true);
		mMenu.setItemsPerLineInPortraitOrientation(3);
		mMenu.setItemsPerLineInLandscapeOrientation(8);

		loadMenuItems();
		
		
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
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			doMenu();
			return true; // always eat it!
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void doMenu() {
		if (mMenu.isShowing()) {
			mMenu.hide();
		} else {
			// Note it doesn't matter what widget you send the menu as long as
			// it gets view.
			View v = new View(Calender.this);
			mMenu.show(v);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	private void intilazeleft(View v)
	{
		//RelativeLayout rl_wall,rl_servicelist,rl_calender,rl_client,rl_activities;
		rl_wall=(RelativeLayout) v.findViewById(R.id.wall);
		rl_calender=(RelativeLayout) v.findViewById(R.id.calender);
		rl_servicelist=(RelativeLayout) findViewById(R.id.servicelist);
		rl_wall.setOnClickListener(this);
		rl_calender.setOnClickListener(this);
		rl_servicelist.setOnClickListener(this);
			
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.previous:

			if (month.get(Calendar.MONTH) == month
					.getActualMinimum(Calendar.MONTH)) {
				month.set((month.get(Calendar.YEAR) - 1),
						month.getActualMaximum(Calendar.MONTH), 1);
			} else {
				month.set(Calendar.MONTH, month.get(Calendar.MONTH) - 1);
			}
			refreshCalendar();
			break;
			
		case R.id.wall:
			
			if(menu!=null)
			{
				menu.toggle();
			}
			
			Intent i=new Intent(Calender.this,Home.class);
			Calender.this.finish();
			startActivity(i);
			break;
			
		case R.id.servicelist:

			if (menu != null) {
				menu.toggle();
			}

			Intent service = new Intent(Calender.this, Servicelist.class);
			Calender.this.finish();
			startActivity(service);
			break;
			
		case R.id.next:
			
			if (month.get(Calendar.MONTH) == month
					.getActualMaximum(Calendar.MONTH)) {
				month.set((month.get(Calendar.YEAR) + 1),
						month.getActualMinimum(Calendar.MONTH), 1);
			} else {
				month.set(Calendar.MONTH, month.get(Calendar.MONTH) + 1);
			}
			refreshCalendar();
			break;
			
		case R.id.backButton:
			break;
			
		case R.id.rightButton:
			
			if (menu1 == null)
			{
				System.out.println("inside view null");
				view1 = Calender.this.getLayoutInflater().inflate(
						R.layout.addanappointment, null);
				menu1 = new SlidingMenu(this);
				menu1.setMode(SlidingMenu.RIGHT);
				menu1.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
				menu1.setBehindOffsetRes(R.dimen.sliding_limit);
				menu1.setMenu(view1);
				menu1.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
				menu1.toggle();
				intiliazeright(view1);
			}else
			{
				
				System.out.println("inside view not null");
				menu1.toggle();
				try {
					fname.setText("");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					lname.setText("");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					adult_counter.setText("1");
					child_counter.setText("0");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
			
		case R.id.scheduletime:
			
			Calendar calender = Calendar.getInstance();
			DatePickerDialog datePicker = new DatePickerDialog(
					Calender.this, new MyDateListener(), calender
							.get(Calendar.YEAR), calender
							.get(Calendar.MONTH), calender
							.get(Calendar.DAY_OF_MONTH));
			
			datePicker.show();
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
			if(menu1!=null)
			{
				menu1.toggle();
			}
			break;
			

		default:
			break;
		}
	}
	
	private void intiliazeright(View v)
	{
		//private EditText fname,lname,adult_counter,child_counter,note,schedule;
		fname=(EditText) v.findViewById(R.id.firstname);
		try {
			fname.setText("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lname=(EditText) v.findViewById(R.id.lastname);
		try {
			lname.setText("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		adult_counter=(EditText) v.findViewById(R.id.counter_adult);
		child_counter=(EditText) v.findViewById(R.id.counter_child);
		note=(EditText) v.findViewById(R.id.createnew_description);
		schedule=(EditText) v.findViewById(R.id.scheduletime);
		plus=(Button) v.findViewById(R.id.adult_plus);
		minus=(Button) v.findViewById(R.id.adult_minus);
		plus1=(Button) v.findViewById(R.id.child_plus);
		minus1=(Button) v.findViewById(R.id.child_minus);
		rl_save=(RelativeLayout) v.findViewById(R.id.edit_save);
		plus1.setOnClickListener(this);
		minus1.setOnClickListener(this);
		plus.setOnClickListener(this);
		minus.setOnClickListener(this);
		schedule.setOnClickListener(this);
		rl_save.setOnClickListener(this);
		try {
			adult_counter.setText("1");
			child_counter.setText("0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshCalendar()
	{
		TextView title = (TextView) findViewById(R.id.title);

		adapter.setCurrentDate(month);
		adapter.notifyDataSetChanged();
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

	}
	
	
	private class NotesCalendarAdapter extends BaseAdapter
	{
		private static final String TAG = "NotesCalendarAdapter";

		private Calendar currentDate;
		private int mOffset;
		private int mDaysInMonth;
		private LayoutInflater inflater;
		private GridViewRefresherTask gridViewRefresherTask = null;
		private Integer[] mDays = new Integer[]
				{ R.string.sun, R.string.mon, R.string.tue, R.string.wed, R.string.thu,
						R.string.fri, R.string.sat, R.string.one, R.string.two,
						R.string.three, R.string.four, R.string.five, R.string.six,
						R.string.seven, R.string.eight, R.string.nine, R.string.ten,
						R.string.eleven, R.string.twelve, R.string.thirteen,
						R.string.fourteen, R.string.fifteen, R.string.sixteen,
						R.string.seventeen, R.string.eighteen, R.string.nineteen,
						R.string.twenty, R.string.twenty_one, R.string.twenty_two,
						R.string.twenty_three, R.string.twenty_four,
						R.string.twenty_five, R.string.twenty_six,
						R.string.twenty_seven, R.string.twenty_eight,
						R.string.twenty_nine, R.string.thirty, R.string.thitry_one,
						R.string.thitry_one };
		
		public NotesCalendarAdapter(Calendar cal)
		{
			currentDate = cal;
			setCurrentDate(currentDate);
			inflater = (LayoutInflater) Calender.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}
		public void setCurrentDate(final Calendar cal)
		{
			if (gridViewRefresherTask == null)
			{
				gridViewRefresherTask = new GridViewRefresherTask();
				gridViewRefresherTask.execute(cal, null);
			}
		}
		
		private class GridViewRefresherTask extends AsyncTask<Calendar, Void, Void> {

			protected void onPreExecute() {
				pb.setVisibility(View.VISIBLE);
				mGridView.setVisibility(View.GONE);
			}

			@Override
			protected Void doInBackground(Calendar... params) {
				Log.i(TAG, "initialinzing grid view");
				currentDate = (Calendar) params[0].clone();
				dateChanged(currentDate);
				Log.i(TAG, "grid view initialized");
				return null;
			}

			protected void onPostExecute(Void result) {
				Log.i(TAG, "refreshing grid view");
				mGridView.setVisibility(View.VISIBLE);
				pb.setVisibility(View.INVISIBLE);
				notifyDataSetChanged();
				gridViewRefresherTask = null;
			}
		};
		
		private void dateChanged(java.util.Calendar date)
		{
			Calendar cal = (Calendar) date.clone();
			cal.set(java.util.Calendar.DAY_OF_MONTH, 1);
			mOffset = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1;
			mOffset += 7;
			mDaysInMonth = cal
					.getActualMaximum(java.util.Calendar.DAY_OF_MONTH) - 1;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDays.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return  mDays[position];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ContactsViewHolder viewHolder;
			
			
			if (convertView == null)
			{
				convertView = inflater.inflate(R.layout.notes_cal_griditem, null);
				viewHolder = new ContactsViewHolder();
				
				viewHolder.roundNoTextView = (TextView) convertView
						.findViewById(R.id.date_textview);
				viewHolder.img=(ImageView) convertView.findViewById(R.id.todays_date_marker_imageview);
				viewHolder.roundNameAndDateTextView=(TextView) convertView.findViewById(R.id.title_textview);
				viewHolder.roundLayout=(RelativeLayout) convertView.findViewById(R.id.rl_date);
				convertView.setTag(viewHolder);
			}else
			{
				viewHolder = (ContactsViewHolder) convertView.getTag();
			}
			try {
				final int j = position;
				
				if(j<7)
				{
					
					viewHolder.roundNameAndDateTextView.setText(mDays[position]);
					viewHolder.roundLayout.setVisibility(View.GONE);
					if (mDays[position].equals(""))
					{
						viewHolder.roundNameAndDateTextView.setClickable(false);
						viewHolder.roundNameAndDateTextView.setFocusable(false);
					} else
					{

						viewHolder.roundNameAndDateTextView.setClickable(true);
					viewHolder.roundNameAndDateTextView.setFocusable(true);
					}
				}else
				{
					viewHolder.roundLayout.setVisibility(View.VISIBLE);
				final Calendar cal = (Calendar) this.currentDate.clone();
				viewHolder.roundNoTextView.setTextColor(Color.BLACK);
				viewHolder.roundNoTextView.setText(mDays[position]);
				if (position < mOffset || position > (mDaysInMonth + mOffset))
				{
					viewHolder.roundNoTextView.setText("");
					convertView.setBackgroundResource(R.drawable.cal_date_disabled);
					viewHolder.img.setVisibility(View.INVISIBLE);
					convertView.setEnabled(false);
				}else
				{
					convertView.setEnabled(true);
					viewHolder.roundNoTextView.setText(mDays[(position - (mOffset - 7))
							% mDays.length]);

					int dayOfMonth = Integer
							.parseInt(getString(mDays[(position - (mOffset - 7))
									% mDays.length]));
					cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					/*String yyyyMMddDate = Common.df_yyyy_MM_dd.format(cal
							.getTime());*/
					viewHolder.img.setVisibility(View.INVISIBLE);
				}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return convertView;
		}
		
		 class ContactsViewHolder {

			TextView roundNoTextView;
			TextView roundNameAndDateTextView;
			RelativeLayout roundLayout;
			ImageView img;

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
		    	
		    	//edtSignupBday.setText(year + "-" + (monthOfYear + 1) + "-"+ dayOfMonth);
		    	schedule.setText( dayOfMonth+ "-" + (monthOfYear + 1) + "-"+  year);
		    }
			
			
			
		}

	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		
		refreshCalendar();
	}
	
	
	/*toast method :
     * to Show the toast message whenever is required
     * */
    
	private void makeToast(String string) {
		// TODO Auto-generated method stub

		Toast.makeText(Calender.this, string, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		try {
//			if(menu.isMenuShowing() || Common.menu.isMenuShowing()){
//				menu.toggle();
//			}else{
//				
				this.finish();
				Intent homeIntent = new Intent(this, Home.class);
				startActivity(homeIntent);
			//}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}



	@Override
	public void MenuItemSelectedEvent(CustomMenuItem selection) {

		if(selection.getId()== MENU_ITEM_2){
			mGlobalData.setLoggedin("false");
			this.finish();
		} 
		
	}
}
