package com.stylist;

import java.util.ArrayList;

import com.slidingmenu.lib.SlidingMenu;
import com.stylist.core.GlobalData;
import com.stylist.dataclasses.CustomMenu;
import com.stylist.dataclasses.CustomMenu.OnMenuItemSelectedListener;
import com.stylist.dataclasses.CustomMenuItem;
import com.stylist.utils.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Acitivities extends Activity implements OnClickListener ,OnMenuItemSelectedListener {
	public static final int MENU_ITEM_1 = 1;
	public static final int MENU_ITEM_2 = 2;
	public static final int MENU_ITEM_3 = 3;
	public static final int MENU_ITEM_4 = 4;
	public static final int MENU_ITEM_5 = 5;
	private CustomMenu mMenu;
	SlidingMenu menu;
	Button leftMenu;
	private GlobalData mGlobalData;
	

	@Override
	public void setContentView(int id) {
		super.setContentView(id);

		View view = Acitivities.this.getLayoutInflater().inflate(
				R.layout.left_slide, null);

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffsetRes(R.dimen.sliding_limit);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setMenu(view);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		Common.inflateMenu(view, this);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activities);
		mGlobalData = (GlobalData) getApplicationContext();
		intializeUIView();
	}

	public void intializeUIView(){
		leftMenu = (Button) findViewById(R.id.backButton);
		leftMenu.setOnClickListener(this);
		mMenu = new CustomMenu(Acitivities.this, Acitivities.this, getLayoutInflater());
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
			View v = new View(Acitivities.this);
			mMenu.show(v);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

//		try {
//			if(menu.isMenuShowing() || Common.menu.isMenuShowing()){
//				menu.toggle();
//			}else{
//
				this.finish();
				Intent homeIntent = new Intent(this, Home.class);
				startActivity(homeIntent);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backButton:
			menu.toggle();
			break;

		default:
			break;
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
