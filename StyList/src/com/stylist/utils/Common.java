
package com.stylist.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.slidingmenu.lib.SlidingMenu;
import com.stylist.Acitivities;
import com.stylist.Calender;
import com.stylist.CashFlow;
import com.stylist.Client;
import com.stylist.Home;
import com.stylist.R;
import com.stylist.Servicelist;


public class Common extends FragmentActivity {

	public static EditText  nickname, phone, mission;
	public static ImageView profilepic;
	public static EditText missionEdit ;
	public static Button missionSave;
	protected static RelativeLayout rl_addstore;
	public static RelativeLayout user_save;
	public static EditText shop_name, shop_add, shop_city, shop_state, shop_zip;
	public static SlidingMenu menu;
	public static EditText fname,lname,adult_counter,child_counter,note,schedule ;
	public static Button plus;
	public static Button minus;
	public static Button plus1;
	public static Button minus1;
	public static RelativeLayout rl_save;
	public static EditText clientFirstName,ClientLastName ,ClientPhone;
	public static Button saveClientData;
	public static TextView clientNameText,clientcancelButton,clientWalkingdetail
	,clientWalkingeditStatus,clientDetailStatus,clientDetailNote,clientDetailChild,clientDetailAdult;



	public static void inflateMenu(View view, final Context context) {
		RelativeLayout wall = (RelativeLayout) view.findViewById(R.id.wall);
		RelativeLayout servicesList = (RelativeLayout) view.findViewById(R.id.servicelist);
		RelativeLayout calender = (RelativeLayout) view.findViewById(R.id.calender);
		RelativeLayout clients = (RelativeLayout) view.findViewById(R.id.clients);

		RelativeLayout activities = (RelativeLayout) view.findViewById(R.id.activities);
		//		RelativeLayout schedule = (RelativeLayout) view
		//		.findViewById(R.id.scheduled);
		RelativeLayout cashflow = (RelativeLayout) view.findViewById(R.id.cashflow);
		//RelativeLayout services = (RelativeLayout) view.findViewById(R.id.services);

		wall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Activity)context).finish();
				Intent intent9 = new Intent(context, Home.class);
				context.startActivity(intent9);

				try {
					if(menu.isMenuShowing()){
						menu.toggle();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

		servicesList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Activity)context).finish();
				Intent intent9 = new Intent(context, Servicelist.class);
				context.startActivity(intent9);
				try {
					if(menu.isMenuShowing()){
						menu.toggle();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		calender.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Activity)context).finish();
				Intent intent9 = new Intent(context, Calender.class);
				context.startActivity(intent9);
				try {
					if(menu.isMenuShowing()){
						menu.toggle();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		clients.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Activity)context).finish();
				Intent intent9 = new Intent(context, Client.class);
				context.startActivity(intent9);
				try {
					if(menu.isMenuShowing()){
						menu.toggle();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		activities.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Activity)context).finish();
				Intent intent9 = new Intent(context, Acitivities.class);
				context.startActivity(intent9);
				try {
					if(menu.isMenuShowing()){
						menu.toggle();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

		cashflow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((Activity)context).finish();
				Intent intent9 = new Intent(context, CashFlow.class);
				context.startActivity(intent9);
				try {
					if(menu.isMenuShowing()){
						menu.toggle();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

	}


	public static void inflateRightMenu(int id ,Context context ,String layoutName ) {

		View view1 = ((Activity) context).getLayoutInflater().inflate(
				id, null);


		menu = new SlidingMenu(context);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		menu.setBehindOffsetRes(R.dimen.sliding_limit);
		menu.setMenu(view1);
		menu.attachToActivity((Activity) context, SlidingMenu.SLIDING_CONTENT);
		menu.toggle();

		if(layoutName.equalsIgnoreCase("addappointMent")){
			intiliazeright(view1 ,context);

		}

		if(layoutName.equalsIgnoreCase("userName")){

			intiliazeView1(view1, context);

		}

		if(layoutName.equalsIgnoreCase("userMission")){
			intilizeMissionView(view1, context);
			menu.toggle();
		}
		if(layoutName.equalsIgnoreCase("userShop")){
			intiliazeView2(view1, context);

		}

		if(layoutName.equalsIgnoreCase("editClient")){
			intializeClientEditPanel(view1, context);
		}

		if(layoutName.equalsIgnoreCase("ServiceclientDetail")){
			intializeServicedetail(view1, context);
		}
	}


	public static void intiliazeright(View v ,Context context)
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
		plus1.setOnClickListener((OnClickListener) context);
		minus1.setOnClickListener((OnClickListener) context);
		plus.setOnClickListener((OnClickListener) context);
		minus.setOnClickListener((OnClickListener) context);
		schedule.setOnClickListener((OnClickListener) context);
		rl_save.setOnClickListener((OnClickListener) context);
		try {
			adult_counter.setText("1");
			child_counter.setText("0");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void intiliazeView2(View v ,Context context) {
		shop_name = (EditText) v.findViewById(R.id.shopname);
		shop_add = (EditText) v.findViewById(R.id.shopaddress);
		shop_city = (EditText) v.findViewById(R.id.shopcity);
		shop_state = (EditText) v.findViewById(R.id.shopstate);
		shop_zip = (EditText) v.findViewById(R.id.shopzip);
		rl_addstore = (RelativeLayout) v.findViewById(R.id.edit_shopdetail);
		rl_addstore.setOnClickListener((OnClickListener) context);

	}


	public static void intilizeMissionView(View v ,Context context){
		missionEdit = (EditText) v.findViewById(R.id.missionEditText);
		missionSave = (Button) v.findViewById(R.id.missinSave);
		missionSave.setOnClickListener((OnClickListener) context);
	}


	public static void intiliazeView1(View v ,Context context) {
		profilepic = (ImageView) v.findViewById(R.id.edit_profilepic);
		fname = (EditText) v.findViewById(R.id.firstname);
		lname = (EditText) v.findViewById(R.id.lastname);
		nickname = (EditText) v.findViewById(R.id.nickname);
		phone = (EditText) v.findViewById(R.id.phone);
		mission = (EditText) v.findViewById(R.id.mission);
		user_save = (RelativeLayout) v.findViewById(R.id.edit_save);
		profilepic.setOnClickListener((OnClickListener) context);
		user_save.setOnClickListener((OnClickListener) context);
	}


	public static void intializeClientEditPanel(View v , Context context){
		clientFirstName = (EditText) v.findViewById(R.id.clientFirstName);
		ClientLastName = (EditText) v.findViewById(R.id.clientLatName);
		ClientPhone = (EditText) v.findViewById(R.id.ClientPhone);
		saveClientData = (Button) v.findViewById(R.id.editclientSave);
		saveClientData.setOnClickListener((OnClickListener) context);
	}


	@SuppressWarnings("static-access")
	public static void intializeServicedetail(View v ,Context context ){
		clientNameText = (TextView) v.findViewById(R.id.clientNameText);
		clientcancelButton = (TextView) v.findViewById(R.id.clientcancelButton);
		clientWalkingdetail = (TextView) v.findViewById(R.id.clientWalkingdetail);
		clientWalkingeditStatus = (TextView) v.findViewById(R.id.clientWalkingeditStatus);
		clientDetailStatus =  (TextView) v.findViewById(R.id.clientDetailStatus);
		clientDetailNote =  (TextView) v.findViewById(R.id.clientDetailNote);
		clientDetailChild =  (TextView) v.findViewById(R.id.clientDetailChild);
		clientDetailAdult = (TextView) v.findViewById(R.id.clientDetailChild);

	}



}
