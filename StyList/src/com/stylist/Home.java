package com.stylist;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.slidingmenu.lib.SlidingMenu;
import com.stylist.core.GlobalData;
import com.stylist.databasemanager.DatabaseHelper;
import com.stylist.dataclasses.CustomMenu;
import com.stylist.dataclasses.CustomMenu.OnMenuItemSelectedListener;
import com.stylist.dataclasses.CustomMenuItem;
import com.stylist.dataclasses.UpdateFlagCl;
import com.stylist.dataclasses.User;
import com.stylist.dataclasses.UserShop;
import com.stylist.utils.Common;
import com.stylist.webservices.Connectivity;
import com.stylist.webservices.Constant;
import com.stylist.webservices.Webservice;

public class Home extends Common implements OnClickListener,
OnMenuItemSelectedListener {

	private SlidingMenu menu, menu1, menu2,menu3;
	private GlobalData mGlobalData;
	private ImageView img;
	private TextView user_name, user_nickname, user_phone, user_mission,
	user_shopname, user_shopaddress;
	private CustomMenu mMenu;
	private View view1 = null, view2 = null, view = null ,view3=null;;
	private PopupWindow pv;
	private String currentImagePath;
	public static Uri imageUri;
	private String currentImagePath1 = null;
	private InputStream io = null;
	private RelativeLayout user_detail, user_shop,user_missionRel;
	private ProgressDialog dialog;
	public static final int MENU_ITEM_1 = 1;
	public static final int MENU_ITEM_2 = 2;
	public static final int MENU_ITEM_3 = 3;
	public static final int MENU_ITEM_4 = 4;
	public static final int MENU_ITEM_5 = 5;
	private RelativeLayout rl_wall,rl_servicelist,rl_calender,rl_client,rl_activities;
	public static boolean isLogout;

	@Override
	public void setContentView(int id) {
		super.setContentView(id);

		View view = Home.this.getLayoutInflater().inflate(
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
	protected void onResume() {
		super.onResume();
		System.out.println("on resume");

	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.home);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mGlobalData = (GlobalData) getApplicationContext();
		intiliazeTop();
		intiliaze();

		if (mGlobalData.getisFirstTime().equalsIgnoreCase("true")) {
			Intent h_welcome = new Intent(Home.this, Home_welcome.class);
			startActivityForResult(h_welcome, 145);
			DatabaseHelper.init(this);
		}else
		{
			new LoadData().execute();
		}

	}

	private void intiliaze() {
		// private TextView
		// user_name,user_nickname,user_phone,user_mission,user_shopname,user_shopaddress;
		dialog = new ProgressDialog(Home.this);
		img = (ImageView) findViewById(R.id.user_pic);
		user_name = (TextView) findViewById(R.id.username);
		user_nickname = (TextView) findViewById(R.id.user_add);
		user_phone = (TextView) findViewById(R.id.user_phno);
		user_mission = (TextView) findViewById(R.id.user_Mission);
		user_shopname = (TextView) findViewById(R.id.user_shopname);
		user_shopaddress = (TextView) findViewById(R.id.user_shopaddress);
		user_detail = (RelativeLayout) findViewById(R.id.layoutone);
		user_shop = (RelativeLayout) findViewById(R.id.layoutthree);
		user_detail.setOnClickListener(this);
		user_shop.setOnClickListener(this);

		user_missionRel = (RelativeLayout) findViewById(R.id.layouttwo); 
		user_missionRel.setOnClickListener(this);
		mMenu = new CustomMenu(Home.this, Home.this, getLayoutInflater());
		mMenu.setHideOnSelect(true);
		mMenu.setItemsPerLineInPortraitOrientation(3);
		mMenu.setItemsPerLineInLandscapeOrientation(8);

		loadMenuItems();
		DatabaseHelper.init(this);
	}

	private void intiliazeTop() {
		Button backButton, rightButton;
		TextView headerTextView;
		backButton = (Button) findViewById(R.id.backButton);
		rightButton = (Button) findViewById(R.id.rightButton);
		rightButton.setOnClickListener(this);
		headerTextView = (TextView) findViewById(R.id.title_header);

		backButton.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.backButton:
			menu.toggle();
			break;

		case R.id.layoutone:

			//Common.inflateRightMenu(R.layout.editprofile, this ,"userName");

			Intent editName = new Intent(Home.this, EditName.class);
			startActivity(editName);

			break;


		case R.id.layouttwo:

			//Common.inflateRightMenu(R.layout.mission_home, this ,"userMission");
			Intent editMission = new Intent(Home.this, EditMission.class);
			startActivity(editMission);
			break;

		case R.id.layoutthree:

			//Common.inflateRightMenu(R.layout.editshopdetail, this ,"userShop");
						 Intent editShop = new Intent(Home.this, EditShop.class);
						 startActivity(editShop);
			break;






		case R.id.edit_profilepic:
			try {
				hideSoftKeyboard(Home.this);
			} catch (Exception e) {

				e.printStackTrace();
			}
			popup();
			break;


		case R.id.missinSave:
			try {
				hideSoftKeyboard(Home.this);
			} catch (Exception e) {

				e.printStackTrace();
			}

			new SaveDataMission().execute();

			break;

		case R.id.edit_save:
			try {
				hideSoftKeyboard(Home.this);
			} catch (Exception e) {

				e.printStackTrace();
			}

			new SaveData().execute();
			break;

		case R.id.edit_shopdetail:
			try {
				hideSoftKeyboard(Home.this);
			} catch (Exception e) {

				e.printStackTrace();
			}

			new SaveShopDetail().execute();
			break;




		case R.id.calender:

			if(menu!=null)
			{
				menu.toggle();
			}

			Intent i=new Intent(Home.this,Calender.class);
			Home.this.finish();
			startActivity(i);
			break;

		case R.id.servicelist:

			if(menu!=null)
			{
				menu.toggle();
			}

			Intent service=new Intent(Home.this,Servicelist.class);
			Home.this.finish();
			startActivity(service);
			break;


		case R.id.clients:
			if(menu!=null)
			{
				menu.toggle();
			}
			Intent client =new Intent(Home.this,Client.class);
			Home.this.finish();
			startActivity(client);
			break;

		case R.id.rightButton:


			break;

		default:
			break;

		}


	}


	private class LoadData extends AsyncTask<Void, Void, Void>
	{
		User user=null;
		UserShop ushop=null;
		byte imageInByte[] = null;
		Bitmap image = null,bitmap=null;
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			dialog.setMessage("Loading data.");
			dialog.setCancelable(false);
			dialog.show();
			DatabaseHelper.init(Home.this);
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				user = mGlobalData.getUserDetail();
			} catch (Exception e) {

				e.printStackTrace();
			}
			if (user != null) {
				String path = "";
				try {
					path = user.getImagepath();
					System.out.println("path" + user.getImagepath());
					Log.e("path", path);
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				if (TextUtils.isEmpty(path)) {
					System.out.println("empty image");
				}

				byte imageInByte[] = null;
				Bitmap image = null;
				try {
					image = BitmapFactory.decodeFile(path);

					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

					imageInByte = new byte[stream.size()];
					imageInByte = stream.toByteArray();
					bitmap = BitmapFactory.decodeByteArray(imageInByte,
							0, imageInByte.length);

				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				} catch (Exception e) {

					e.printStackTrace();
				} finally {
					if (imageInByte != null) {
						imageInByte = null;
					}
					if (image != null) {
						image.recycle();
					}
					System.gc();
				}
			}


			try {
				ushop = mGlobalData.getUserShopDetail();
			} catch (Exception e) {

				e.printStackTrace();
			}


			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);

			if(user!=null)
			{
				setData1(user,bitmap);
			}
			if(dialog!=null)
			{
				dialog.cancel();
			}

			if(ushop!=null)
			{
				setuserShopdetail(ushop);
			}

		}

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {


		if (resultCode == RESULT_OK) {
			// after selecting from device camera

			if (requestCode == 222) {
				try {
					File imageFile = new File(currentImagePath1);
					// System.out.println("inside onActivityResult");
					// System.out.println(currentImagePath1+"");
					set_Image2(currentImagePath1);
					// set_Image(currentImagePath1);

				} catch (OutOfMemoryError e) {

					e.printStackTrace();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

			if (requestCode == 145) {
				String result = data.getStringExtra("result");
				System.out.println("the result of data" + result);
				if (result.equals("skip")) {

				} else if (result.equals("done")) {
					// private TextView
					// user_name,user_nickname,user_phone,user_mission,user_shopname,user_shopaddress;

					User user = mGlobalData.getUserDetail();
					setData(user);
					UserShop ushop = mGlobalData.getUserShopDetail();
					setuserShopdetail(ushop);

				} else {

				}

				// updating the flag of user .

				String u_id = mGlobalData.getuserid();
				if (TextUtils.isEmpty(u_id)) {
					makeToast("Invalid user id.");
				} else {
					if (Connectivity.hasConnection(Home.this)) {
						new UpdateFlag(u_id).execute();
					} else {

						makeToast(getResources().getString(
								R.string.no_internet_message));
					}
				}
			}

			// after selecting from gallery
			if (requestCode == 1111) {

				try {
					// System.out.println("With in Take Image");
					Uri imageUri = data.getData();
					// System.out.println(imageUri);
					Cursor c = getContentResolver().query(imageUri, null, null,
							null, null);
					try {
						c.moveToFirst();
					} catch (Exception e1) {

						e1.printStackTrace();
					}
					String imagePath = null;
					try {
						imagePath = c
						.getString(c
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
					} catch (Exception e1) {

						e1.printStackTrace();
					}
					/*
					 * System.out .println(
					 * "Before Setting Image:::::::::::::::::::::::::::::::::::::::::"
					 * + imagePath);
					 */
					// set_Image(imagePath);
					if (imagePath != null) {
						set_Image2(imagePath);
						try {
							io = new FileInputStream(new File(imagePath));
						} catch (OutOfMemoryError e) {

							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						// image = imagePath;
					}
				} catch (IllegalArgumentException e) {

					e.printStackTrace();
				}

			}
		}
		/*
		 * if (requestCode == 145) { if (resultCode == RESULT_OK) {
		 * 
		 * 
		 * } }
		 */

	}

	/*
	 * fetch user shop detail from db
	 */

	private void setuserShopdetail(UserShop ushop) {
		if (ushop != null) {
			String add = "", city = "", state = "", zip = "";
			try {
				user_shopname.setText(ushop.getShopname());
			} catch (Exception e) {

				e.printStackTrace();
			}
			try {
				add = ushop.getShopadd();
			} catch (Exception e) {

				e.printStackTrace();
			}
			try {
				city = ushop.getShopcity();
			} catch (Exception e) {

				e.printStackTrace();
			}
			try {
				state = ushop.getShopstate();
			} catch (Exception e) {

				e.printStackTrace();
			}
			try {
				zip = ushop.getShopzip();
			} catch (Exception e) {

				e.printStackTrace();
			}
			user_shopaddress
			.setText(add + "," + city + "," + state + "," + zip);

		} else {
			System.out.println("null shop object");
		}
	}

	/*
	 * user detail Image store in db fetch by set Data method
	 */
	private void setData(User user) {
		if (user != null) {
			String path = "";
			try {
				path = user.getImagepath();
				Log.e("path", path);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			if (TextUtils.isEmpty(path)) {
				System.out.println("empty image");
			} else {
				byte imageInByte[] = null;
				Bitmap image = null;
				try {
					image = BitmapFactory.decodeFile(path);

					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					imageInByte = new byte[stream.size()];
					imageInByte = stream.toByteArray();
					Bitmap bitmap = BitmapFactory.decodeByteArray(imageInByte,
							0, imageInByte.length);
					Drawable toRecycle= img.getDrawable();
					if (toRecycle != null) {
						((BitmapDrawable)img.getDrawable()).getBitmap().recycle();
					}
					img.setImageBitmap(bitmap);
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				} catch (Exception e) {

					e.printStackTrace();
				} finally {
					if (imageInByte != null) {
						imageInByte = null;
					}
					if (image != null) {
						image.recycle();
					}
					System.gc();
				}
			}

			try {
				user_name.setText(user.getFname() + "," + user.getLname());
			} catch (Exception e) {

				e.printStackTrace();
			}

			try {
				user_nickname.setText(user.getNickname());
			} catch (Exception e) {

				e.printStackTrace();
			}

			try {
				user_phone.setText(user.getPhone());
			} catch (Exception e) {

				e.printStackTrace();
			}

			try {
				user_mission.setText(user.getMission());
			} catch (Exception e) {

				e.printStackTrace();
			}

		} else {
			System.out.println("null user object");
		}

	}


	private void setData1(User user,Bitmap bitmap) {
		if (user != null) {
			if(bitmap!=null)
			{
				Drawable toRecycle= img.getDrawable();
				if (toRecycle != null) {
					((BitmapDrawable)img.getDrawable()).getBitmap().recycle();
				}	
				img.setImageBitmap(bitmap);
			}
			/*String path = "";
			try {
				path = user.getImagepath();
				Log.e("path", path);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			if (TextUtils.isEmpty(path)) {
				System.out.println("empty image");
			} else {
				byte imageInByte[] = null;
				Bitmap image = null;
				try {
					image = BitmapFactory.decodeFile(path);

					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
					imageInByte = new byte[stream.size()];
					imageInByte = stream.toByteArray();
					Bitmap bitmap = BitmapFactory.decodeByteArray(imageInByte,
							0, imageInByte.length);
					Drawable toRecycle= img.getDrawable();
					if (toRecycle != null) {
						((BitmapDrawable)img.getDrawable()).getBitmap().recycle();
					}
					img.setImageBitmap(bitmap);
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				} catch (Exception e) {

					e.printStackTrace();
				} finally {
					if (imageInByte != null) {
						imageInByte = null;
					}
					if (image != null) {
						image.recycle();
					}
					System.gc();
				}
			}*/

			try {
				user_name.setText(user.getFname() + "," + user.getLname());
			} catch (Exception e) {

				e.printStackTrace();
			}

			try {
				user_nickname.setText(user.getNickname());
			} catch (Exception e) {

				e.printStackTrace();
			}

			try {
				user_phone.setText(user.getPhone());
			} catch (Exception e) {

				e.printStackTrace();
			}

			try {
				user_mission.setText(user.getMission());
			} catch (Exception e) {

				e.printStackTrace();
			}

		} else {
			System.out.println("null user object");
		}

	}

	/*
	 * set image to the image view right slide menu image view
	 */

	private Bitmap set_Image2(String imagePath) {

		currentImagePath = imagePath;
		Log.e("image  path", imagePath);
		Bitmap imageBitmap = null;
		BitmapFactory.Options opt = null;
		byte[] ba = null;
		FileInputStream in = null;
		Bitmap map = null;
		BitmapDrawable bmd = null;
		try {
			/*
			 * System.out .println(
			 * "Image Path::::::::::::::::::::in Set Image::::::::::::::::::::"
			 * + imagePath);
			 */
			in = new FileInputStream(imagePath);
			// System.out.println("1..................");
			opt = new BitmapFactory.Options();
			opt.inTempStorage = new byte[16 * 1024];
			opt.inSampleSize = 4;
			opt.outWidth = 100;

			imageBitmap = BitmapFactory.decodeStream(in, new Rect(), opt);

			Drawable toRecycle=null;
			try {
				toRecycle = profilepic.getDrawable();
			} catch (Exception e) {

				e.printStackTrace();
			}
			if (toRecycle != null) {
				((BitmapDrawable)profilepic.getDrawable()).getBitmap().recycle();
			}
			profilepic.setImageBitmap(imageBitmap);

		} catch (OutOfMemoryError e) {
			opt.inTempStorage = new byte[50 * 1024];
			System.out.println(e.getMessage());
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (ba != null) {
				ba = null;
			}

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
				/*
				 * map.recycle(); map=null; bmd.getBitmap().recycle();
				 */
			}
			System.gc();

		}

		return imageBitmap;
	}

	/*
	 * loading customize menu
	 * 
	 * with CustomMenu class
	 */

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

	private void doMenu() {
		if (mMenu.isShowing()) {
			mMenu.hide();

		} else {
			// Note it doesn't matter what widget you send the menu as long as
			// it gets view.
			View v = new View(Home.this);
			mMenu.show(v);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			doMenu();
			return true; // always eat it!
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void MenuItemSelectedEvent(CustomMenuItem selection) {

		if(selection.getId()== MENU_ITEM_2){
			mGlobalData.setLoggedin("false");
			this.finish();
		} 

	}

	private class UpdateFlag extends AsyncTask<Void, Void, Void> {
		String u_id = "";
		UpdateFlagCl uflag = null;

		UpdateFlag(String userid) {
			this.u_id = userid;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {

			uflag = setflag(u_id);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			if (uflag != null) {
				Log.e("status", uflag.getStatus());
				if (uflag.getStatus().contains("success")) {
					mGlobalData.setIsFirstTime("false");
				} else {
					mGlobalData.setIsFirstTime("true");
				}
			}

		}

	}

	private UpdateFlagCl setflag(String user_id) {
		UpdateFlagCl uflag = null;
		String url = Constant.API_URL + Constant.Login_Flag + "&userID="
		+ user_id;
		Log.e("URL", url);
		String response = null;

		try {
			response = Webservice.getData(getApplicationContext(), url);
			Log.e("response", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response != null) {
			Log.e("response", response);
			uflag = parseJson(response);
		}

		return uflag;
	}

	private UpdateFlagCl parseJson(String jsonResponse) {

		UpdateFlagCl response = new UpdateFlagCl();
		Gson gson = new Gson();
		try {
			response = gson.fromJson(jsonResponse, response.getClass());
		} catch (JsonSyntaxException e) {

			e.printStackTrace();
		}

		return response;
	}

	/*
	 * toast method : to Show the toast message whenever is required
	 */

	private void makeToast(String string) {


		Toast.makeText(Home.this, string, Toast.LENGTH_SHORT).show();

	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
		.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}

	private void popup() {
		LayoutInflater li = (LayoutInflater) Home.this
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = li.inflate(R.layout.popup_window_layout,
				(ViewGroup) findViewById(R.id.popup_layout));
		Button btnTakePhoto = (Button) popupView
		.findViewById(R.id.popup_take_photo);
		Button btnUploadPhoto = (Button) popupView
		.findViewById(R.id.popup_upload_photo);
		Button btnCancel = (Button) popupView.findViewById(R.id.popup_cancle);
		btnCancel = (Button) popupView.findViewById(R.id.popup_cancle);

		btnCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				pv.dismiss();

			}
		});

		btnUploadPhoto = (Button) popupView
		.findViewById(R.id.popup_upload_photo);

		btnUploadPhoto.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// System.out.println("uploadPhoto clicked");
				Intent intent = new Intent();
				intent.setType("image/*");

				intent.setAction(Intent.ACTION_GET_CONTENT);
				// setResult(Activity.RESULT_OK);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"), 1111);
				pv.dismiss();

			}
		});

		btnTakePhoto = (Button) popupView.findViewById(R.id.popup_take_photo);
		btnTakePhoto.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
				String fileName = "img_" + sdf.format(new Date()) + ".jpg";
				System.out.println(fileName);
				File myDirectory = new File(Environment
						.getExternalStorageDirectory() + "/stylist/");
				myDirectory.mkdirs();
				File file = new File(myDirectory, fileName);
				/*
				 * if(!file.exists()){
				 * 
				 * try { file.createNewFile(); } catch (IOException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); } }
				 */

				imageUri = Uri.fromFile(file);

				currentImagePath1 = file.getPath();
				System.out.println(imageUri);
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, 222);
				pv.dismiss();

			}
		});

		pv = new PopupWindow(popupView, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT, false);
		pv.showAtLocation(findViewById(R.id.popup_parent), Gravity.BOTTOM, 0, 0);
	}

	private class SaveData extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			dialog.setMessage("Please wait..");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				saveData_Db();

			} catch (Exception e) {
				// TODO: handle exception
			}



			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			if (dialog != null) {
				dialog.cancel();
			}
			if (Common.menu != null) {
				Common.menu.toggle();
			}
			try {
				User user = mGlobalData.getUserDetail();
				setData(user);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

	private class SaveDataMission extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			dialog.setMessage("Please wait..");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			try {
				saveData_Dbformission();

			} catch (Exception e) {
				// TODO: handle exception
				e.getMessage();
			}



			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			if (dialog != null) {
				dialog.cancel();
			}
			if (Common.menu != null) {
				Common.menu.toggle();
			}
			try {
				User user = mGlobalData.getUserDetail();
				setData(user);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}




	private class SaveShopDetail extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			dialog.setMessage("Savind data.");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			saveDb();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			if (dialog != null)
				;
			{
				dialog.cancel();
			}
			if (Common.menu != null) {
				Common.menu.toggle();
			}

			try {
				UserShop ushop = mGlobalData.getUserShopDetail();
				setuserShopdetail(ushop);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

	}



	/*
	 * save user detail to db
	 */
	private void saveData_Dbformission() {
		// EditText fname,lname,nickname,phone,mission;
		User u = new User();

		if (TextUtils.isEmpty(currentImagePath)) {

		} else {

			Log.e("image path", currentImagePath);
			u.setImagepath(currentImagePath);
		}
		String fna = fname.getText().toString();
		fna = fna.trim();
		u.setFname(fna);
		String lna = lname.getText().toString();
		lna = lna.trim();
		u.setLname(lna);
		String nck = nickname.getText().toString();
		nck = nck.trim();
		u.setNickname(nck);
		String ph = phone.getText().toString();
		ph = ph.trim();
		u.setPhone(ph);
		String miss = missionEdit.getText().toString();
		System.out.println("missinEdit" +miss );
		miss = miss.trim();
		u.setMission(miss);
		mGlobalData.setUserDetail(u);

	}



	/*
	 * save user detail to db
	 */
	private void saveData_Db() {
		// EditText fname,lname,nickname,phone,mission;
		User u = new User();

		if (TextUtils.isEmpty(currentImagePath)) {

		} else {

			Log.e("image path", currentImagePath);
			u.setImagepath(currentImagePath);
		}
		String fna = fname.getText().toString();
		fna = fna.trim();
		u.setFname(fna);
		String lna = lname.getText().toString();
		lna = lna.trim();
		u.setLname(lna);
		String nck = nickname.getText().toString();
		nck = nck.trim();
		u.setNickname(nck);
		String ph = phone.getText().toString();
		ph = ph.trim();
		u.setPhone(ph);
		String miss = mission.getText().toString();
		miss = miss.trim();
		u.setMission(miss);
		mGlobalData.setUserDetail(u);

	}

	/*
	 * save user shop detail to db
	 */

	private void saveDb() {
		UserShop user = new UserShop();
		// EditText shop_name,shop_add,shop_city,shop_state,shop_zip;
		String sname = shop_name.getText().toString();
		sname = sname.trim();
		String sadd = shop_add.getText().toString();
		sadd = sadd.trim();
		String scity = shop_city.getText().toString();
		scity = scity.trim();
		String sstate = shop_state.getText().toString();
		sstate = sstate.trim();
		String szip = shop_zip.getText().toString();
		szip = szip.trim();

		user.setShopname(sname);
		user.setShopadd(sadd);
		user.setShopcity(scity);
		user.setShopstate(sstate);
		user.setShopzip(szip);
		mGlobalData.setUserShopDetail(user);
	}

	//on back pressed overide...

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		this.finish();
		try {


			//			if(menu.isMenuShowing()|| Common.menu.isMenuShowing() ){
			//				menu.toggle();
			//				 Common.menu.toggle();
			//			}		
		} catch (Exception e) {
			// TODO: handle exception
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



}