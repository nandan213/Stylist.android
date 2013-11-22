package com.stylist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.slidingmenu.lib.SlidingMenu;
import com.stylist.core.GlobalData;
import com.stylist.dataclasses.Appointment;
import com.stylist.dataclasses.CustomMenu;
import com.stylist.dataclasses.CustomMenu.OnMenuItemSelectedListener;
import com.stylist.dataclasses.CustomMenuItem;
import com.stylist.utils.Common;

public class Client extends Common  implements OnClickListener ,OnMenuItemSelectedListener {

	ListView list;
	ArrayList<String> ls;
	private SectionedAdapter adapter;
	@SuppressWarnings("unused")
	private MainActivityAdapter myBaseAdapter;
	ArrayList<Appointment> names;
	SlidingMenu menu;
	private GlobalData mGlobalData;
	private Button menuToggle;
	private ProgressDialog dialog;
	Handler handler;
	public static final int MENU_ITEM_1 = 1;
	public static final int MENU_ITEM_2 = 2;
	public static final int MENU_ITEM_3 = 3;
	public static final int MENU_ITEM_4 = 4;
	public static final int MENU_ITEM_5 = 5;
	private CustomMenu mMenu;
	private boolean isfirstTime;
	@Override
	public void setContentView(int id) {
		super.setContentView(id);
		View view = Client.this.getLayoutInflater().inflate(
				R.layout.left_slide, null);

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffsetRes(R.dimen.sliding_limit);
		// menu.setFadeDegree(0.35f);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setMenu(view);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		Common.inflateMenu(view, this);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clients);

		intializeUIView();

		mGlobalData = (GlobalData) getApplicationContext();
		ls = new ArrayList<String>();

		String[] a = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "k",
				"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z" };
		List<String> newList = Arrays.asList(a);

		ls.addAll(newList);

		for (int i = 0; i < ls.size(); i++) {
			System.out.println("lll" + ls.get(i));
		}

		names = new ArrayList<Appointment>();
		System.out.println("mGlobalData"+ mGlobalData.getServicedetail().size());

		names.addAll(mGlobalData.getServicedetail());
		setToAdapter();


		handler=new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==123)
				{
					names.clear();
					names.addAll(mGlobalData.getServicedetail());
					System.out.println("names" +names.get(0).getFname());
					System.out.println("mGlobalData"+ mGlobalData.getServicedetail().size());
					setToAdapter();
				}
			}
		};
	}


	public void intializeUIView(){
		dialog=new ProgressDialog(Client.this);
		list = (ListView) findViewById(R.id.clientListView);
		menuToggle = (Button) findViewById(R.id.backButton);
		menuToggle.setOnClickListener(this);

		mMenu = new CustomMenu(Client.this, Client.this, getLayoutInflater());
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
			View v = new View(Client.this);
			mMenu.show(v);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}


	class MainActivityAdapter extends BaseAdapter {
		Context context;
		LayoutInflater mInflater;
		ArrayList<Appointment> names1;

		public MainActivityAdapter(Context context, ArrayList<Appointment> ls) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this.names1 = ls;
			System.out.println("names" + names1.toString());
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names1.size();
		}

		@Override
		public Appointment getItem(int arg0) {
			// TODO Auto-generated method stub
			return names1.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int arg0, View view, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ClientViewHolder viewHolder ;

			if(view==null)
			{
				view = mInflater.inflate(R.layout.clients_adapter, null);
				viewHolder = new ClientViewHolder();
				viewHolder.nn = (TextView) view.findViewById(R.id.clientName);
				viewHolder.phone = (Button) view.findViewById(R.id.phone);
				viewHolder.editPanel = (Button) view.findViewById(R.id.clientRightSlider); 
				view.setTag(viewHolder);
			}else{
				viewHolder = (ClientViewHolder) view.getTag();
			}

			viewHolder.nn.setText(names1.get(arg0).getFname());
			System.out.println("fvfev"+names1.get(arg0).getFname());
			final int j=arg0;
			final Appointment round = names.get(arg0);
			viewHolder.editPanel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					boolean isClicked = true;
					System.out.println("isClicked" + isClicked);
				
					
					
					inflateRightMenu(R.layout.edit_client, Client.this ,"editClient");

					String fna=clientFirstName.getText().toString();
					fna=fna.trim();
					String lna=ClientLastName.getText().toString();
					lna=lna.trim();
					String phone_no =ClientPhone.getText().toString();
					phone_no=phone_no.trim();

					if(TextUtils.isEmpty(fna))
					{
						clientFirstName.setError("Provide firstname.");
					}

					if(TextUtils.isEmpty(lna))
					{
						ClientLastName.setError("Provide lastname.");
					}

					if(TextUtils.isEmpty(phone_no))
					{
						ClientPhone.setError("provide a schdule date.");
					}
					round.setFname(fna);
					round.setLname(lna);
					round.setPhoneNo(phone_no);
					System.out.println("position" +arg0);
					round.setCus_id(round.getCus_id());
					System.out.println("custID"+names1.get(arg0).getCus_id());

					round.setFname(fna);
					round.setLname(lna);
					round.setAdult_count(round.getAdult_count());
					round.setChild_count(round.getChild_count());
					round.setScheduledate(round.getScheduledate());
					round.setNote(round.getNote());
					round.setUserid(round.getUserid());
					round.setStatus("Ready");
					round.setServerid(0);
					round.setPhoneNo(round.getPhoneNo());
					round.setCus_id(round.getCus_id());
					System.out.println("AppointMent" +round.getFname());


				}
			});

			return view;
		}


	}
	static class ClientViewHolder {
		TextView nn ;
		Button phone ;
		Button editPanel;

	}
	private void setToAdapter() {

		adapter = null;
		myBaseAdapter = null;
		adapter = new SectionedAdapter() {
			LayoutInflater inflater;
			private TextView result;

			protected View getHeaderView(String caption, int index,
					View convertView, ViewGroup parent) {

				result = (TextView) convertView;

				if (convertView == null) {
					result = (TextView) getLayoutInflater().inflate(
							R.layout.client_header_adapter, null);
				}

				result.setText(caption);
				return (result);
			}
		};

		ArrayList<Appointment> newarrayList1;
		myBaseAdapter = new MainActivityAdapter(Client.this, names);
		for (int j = 0; j < ls.size(); j++) {
			newarrayList1 = new ArrayList<Appointment>();
			for (int i = 0; i < names.size(); i++) {
				String region1 = names.get(i).getFname().substring(0, 1);

				if (region1.equalsIgnoreCase(ls.get(j))) {
					newarrayList1.add(names.get(i));
				}
			}

			if (newarrayList1.size() > 0) {
				((SectionedAdapter) adapter).addSection(ls.get(j),
						new MainActivityAdapter(this, newarrayList1));
			}
		}
		list.setAdapter(adapter);
	}


	@Override
	public void onClick(View paramView) {
		// TODO Auto-generated method stub
		switch (paramView.getId()) {
		case R.id.backButton:

			menu.toggle();
			break;

		case R.id.editclientSave:
			Appointment appointment=validateInput();
			if(appointment!=null)
			{
				new InsertClienTEditDetail(appointment).execute();
				//mGlobalData.setServicedetail(appointment);
			}

			break;


		default:
			break;
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			if(menu.isMenuShowing()){
				menu.toggle();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	private Appointment validateInput()
	{
		Appointment appointment=null;

		String fna=clientFirstName.getText().toString();
		fna=fna.trim();
		String lna=ClientLastName.getText().toString();
		lna=lna.trim();
		String phone_no =ClientPhone.getText().toString();
		phone_no=phone_no.trim();

		if(TextUtils.isEmpty(fna))
		{
			clientFirstName.setError("Provide firstname.");
			return appointment;
		}

		if(TextUtils.isEmpty(lna))
		{
			ClientLastName.setError("Provide lastname.");
			return appointment;
		}

		if(TextUtils.isEmpty(phone_no))
		{
			ClientPhone.setError("provide a schdule date.");
			return appointment;
		}


		appointment=new Appointment();
		appointment.setFname(fna);
		appointment.setLname(lna);
		appointment.setPhoneNo(phone_no);
		appointment.setCus_id(1);
		System.out.println("AppointMent" +appointment.getFname());

		return appointment;
	}
	private class InsertClienTEditDetail extends AsyncTask<Void, Void, Void>
	{
		Appointment appointment;
		InsertClienTEditDetail(Appointment app)
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
			mGlobalData.updateservicedetailInfo(appointment);
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
		
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		try {
			//			if(menu.isMenuShowing() || Common.menu.isMenuShowing()){
			//				menu.toggle();
			//			}else{

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
