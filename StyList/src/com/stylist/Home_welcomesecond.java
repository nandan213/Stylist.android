package com.stylist;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.stylist.core.GlobalData;
import com.stylist.databasemanager.DatabaseHelper;
import com.stylist.dataclasses.User;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

public class Home_welcomesecond extends FragmentActivity implements OnClickListener {
	
	private ImageView img;
	private PopupWindow pv;
	private String currentImagePath;
	public static Uri imageUri;
	private String currentImagePath1=null;
	private InputStream io = null;
	private Button skip,addshop;
	private EditText fname,lname,nickname,phone,mission;
	private GlobalData mGlobalData; 
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_welcomesecond);
		
	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mGlobalData=(GlobalData) getApplicationContext();
		intiliaze();
	}
	
	private void intiliaze()
	{
		dialog=new ProgressDialog(Home_welcomesecond.this);
		img=(ImageView) findViewById(R.id.topAddImageView);
		skip=(Button) findViewById(R.id.skip);
		addshop=(Button) findViewById(R.id.addashop);
		fname=(EditText) findViewById(R.id.firstname);
		lname=(EditText) findViewById(R.id.lastname);
		nickname=(EditText) findViewById(R.id.nickname);
		phone=(EditText) findViewById(R.id.phone);
		mission=(EditText) findViewById(R.id.mission);
		img.setOnClickListener(this);
		skip.setOnClickListener(this);
		addshop.setOnClickListener(this);
		DatabaseHelper.init(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.topAddImageView:
			
			try {
				hideSoftKeyboard(Home_welcomesecond.this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			popup();
			break;
			
		case R.id.skip:
			Intent returnIntent = new Intent();
			returnIntent.putExtra("result","skip");
			
			setResult(RESULT_OK,returnIntent);     
			finish();
			break;
			
		case R.id.addashop:
			try {
				hideSoftKeyboard(Home_welcomesecond.this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//saveData_Db();
			new saveDb().execute();
			
			break;

		default:
			break;
		}
	}
	
	private void popup()
	{
		LayoutInflater li = (LayoutInflater) Home_welcomesecond.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = li.inflate(R.layout.popup_window_layout,
				(ViewGroup) findViewById(R.id.popup_layout));
		Button btnTakePhoto = (Button) popupView.findViewById(R.id.popup_take_photo);
		Button btnUploadPhoto = (Button) popupView.findViewById(R.id.popup_upload_photo);
		Button btnCancel = (Button) popupView.findViewById(R.id.popup_cancle);
		btnCancel = (Button) popupView.findViewById(R.id.popup_cancle);
		
		btnCancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				pv.dismiss();

			}
		});

		btnUploadPhoto = (Button) popupView
				.findViewById(R.id.popup_upload_photo);
		
		btnUploadPhoto.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	System.out.println("uploadPhoto clicked");
				Intent intent = new Intent();
				intent.setType("image/*");

				intent.setAction(Intent.ACTION_GET_CONTENT);
				// setResult(Activity.RESULT_OK);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),	1111);
				pv.dismiss();

			}
		});
		
		btnTakePhoto = (Button) popupView
				.findViewById(R.id.popup_take_photo);
		btnTakePhoto.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
				String fileName = "img_" + sdf.format(new Date())+ ".jpg";
				System.out.println(fileName);
				File myDirectory = new File(Environment.getExternalStorageDirectory()+ "/stylist/");
				myDirectory.mkdirs();
				File file = new File(myDirectory, fileName);
				/*if(!file.exists()){
					
					try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
				
				
				imageUri = Uri.fromFile(file);
				
				currentImagePath1=file.getPath();
			    System.out.println(imageUri);
				Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, 222);
				pv.dismiss();
				
			}
		});


		pv = new PopupWindow(popupView, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT, false);
		pv.showAtLocation(findViewById(R.id.popup_parent),
				Gravity.BOTTOM, 0, 0);
	}
	
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	
	/*
	 * After selecting the image from external hardware as well as from gallery
	 * onactivity result passes result back to the activity 
	 * 
	 * 
	 * 
	 * */
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			// after selecting from device camera
			
			if (requestCode == 222) {
				try
				{
					File imageFile = new File(currentImagePath1);
					//System.out.println("inside onActivityResult");
					//System.out.println(currentImagePath1+"");
					set_Image2(currentImagePath1);
					//set_Image(currentImagePath1);
					
				} catch (OutOfMemoryError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(requestCode == 172)
			{
				String result=data.getStringExtra("result");
				Intent returnIntent = new Intent();
				 returnIntent.putExtra("result",result);
					
					setResult(RESULT_OK,returnIntent);     
					finish();
			}

			// after selecting from gallery 
			if (requestCode == 1111) {
				
				try {
					//System.out.println("With in Take Image");
					Uri imageUri = data.getData();
					//System.out.println(imageUri);
					Cursor c = getContentResolver().query(imageUri, null, null,
							null, null);
					try {
						c.moveToFirst();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String imagePath=null;
					try {
						imagePath = c.getString(c
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					/*System.out
							.println("Before Setting Image:::::::::::::::::::::::::::::::::::::::::"
									+ imagePath);*/
					//set_Image(imagePath);
					if(imagePath!=null)
					{
					set_Image2(imagePath);
					try {
						io = new FileInputStream(new File(imagePath));
						}catch (OutOfMemoryError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					catch (Exception e) {
						e.printStackTrace();
					}
					//image = imagePath;
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		

	}
	
private Bitmap set_Image2(String imagePath) {
		
		currentImagePath = imagePath;
		Log.e("image  path", imagePath);
		  Bitmap imageBitmap = null;
		  BitmapFactory.Options opt = null;
		  byte[] ba=null;
		  FileInputStream in=null;
		  Bitmap map=null;
		  BitmapDrawable bmd=null;
		  try {
		 /*  System.out
		     .println("Image Path::::::::::::::::::::in Set Image::::::::::::::::::::"
		       + imagePath);*/
		    in = new FileInputStream(imagePath);
		  // System.out.println("1..................");
		   opt = new BitmapFactory.Options();
		   opt.inTempStorage = new byte[16 * 1024];
		   opt.inSampleSize = 4;
		   opt.outWidth = 100;

		   imageBitmap = BitmapFactory
		     .decodeStream(in, new Rect(), opt);

		   img.setImageBitmap(imageBitmap);

		 

		  

		
		  } catch (OutOfMemoryError e) {
		   opt.inTempStorage = new byte[50 * 1024];
		   System.out.println(e.getMessage());
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }finally
		  {
			  if (ba!= null) {
					ba = null;
				}
			
			  if (in != null) {
				    try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   /* map.recycle();
					map=null;
					bmd.getBitmap().recycle();*/
				   }
				System.gc();

		  }

		  return imageBitmap;
		 }

	private void saveData_Db() {
		// EditText fname,lname,nickname,phone,mission;
		User u = new User();

		if (TextUtils.isEmpty(currentImagePath)) {

		} else {
			/*byte imageInByte[] = null;
			try {
				Bitmap image = BitmapFactory.decodeFile(currentImagePath);

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				imageInByte = new byte[stream.size()];
				imageInByte = stream.toByteArray();
				u.setImageInByte(imageInByte);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (imageInByte != null) {
					imageInByte = null;
				}
				System.gc();
			}*/
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
	

	
	private class saveDb extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setMessage("Please wait..");
			dialog.setCancelable(false);
			dialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			saveData_Db();
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
			
			Intent shop=new Intent(Home_welcomesecond.this,Home_welcomethird.class);
			//Home_welcomesecond.this.finish();
			startActivityForResult(shop,172);
		}
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}




}
