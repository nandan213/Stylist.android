package com.stylist;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class EditName extends Activity implements OnClickListener
{
	public  EditText  nickname, phone, mission;
	public  ImageView profilepic;
	public  EditText missionEdit ;
	public  Button missionSave;
	public  EditText fname;
	public  EditText lname;
	public RelativeLayout user_save;
	private PopupWindow pv;
	public String currentImagePath;
	public String currentImagePath1;
	public static Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editprofile);
		intiliazeView1();
	}


	public  void intiliazeView1() {
		profilepic = (ImageView) findViewById(R.id.edit_profilepic);
		fname = (EditText) findViewById(R.id.firstname);
		lname = (EditText) findViewById(R.id.lastname);
		nickname = (EditText) findViewById(R.id.nickname);
		phone = (EditText) findViewById(R.id.phone);
		mission = (EditText) findViewById(R.id.mission);
		user_save = (RelativeLayout) findViewById(R.id.edit_save);
		profilepic.setOnClickListener(this );
		user_save.setOnClickListener(this) ;
	}



	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
		.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_profilepic:
			try {
				hideSoftKeyboard(EditName.this);
			} catch (Exception e) {

				e.printStackTrace();
			}
			popup();
			break;

		default:
			break;
		}
	}
	private void popup() {
		LayoutInflater li = (LayoutInflater) EditName.this
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

}
