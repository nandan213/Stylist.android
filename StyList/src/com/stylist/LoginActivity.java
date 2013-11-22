package com.stylist;

import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.stylist.core.GlobalData;
import com.stylist.dataclasses.Login;
import com.stylist.webservices.Connectivity;
import com.stylist.webservices.Constant;
import com.stylist.webservices.Webservice;

public class LoginActivity extends FragmentActivity implements OnClickListener {

	private EditText email, pwd;
	private RelativeLayout signin;
	private GlobalData mGlobalData;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.login);
		mGlobalData = (GlobalData) getApplicationContext();
		intiliaze();
	}

	private void intiliaze() {
		dialog = new ProgressDialog(LoginActivity.this);
		email = (EditText) findViewById(R.id.email);
		pwd = (EditText) findViewById(R.id.password);
		signin = (RelativeLayout) findViewById(R.id.rl_signin);
		signin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_signin:
			try {
				hideSoftKeyboard(LoginActivity.this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean flag = validInput();
			if (flag) {

				if (Connectivity.hasConnection(LoginActivity.this)) {
					String txt_email = email.getText().toString();
					txt_email = txt_email.trim();
					String txt_pwd = pwd.getText().toString();
					txt_pwd = txt_pwd.trim();

					new MyBackground(txt_email, txt_pwd).execute();

					/*
					 * mGlobalData.setCustomerinfo("customer");
					 * mGlobalData.setLoggedin(true); Intent loginIntent=new
					 * Intent(LoginActivity.this,Home.class);
					 * loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					 * loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 * LoginActivity.this.finish(); startActivity(loginIntent);
					 */

				} else {
					makeToast(getResources().getString(
							R.string.no_internet_message));
				}
			} else {

			}
			break;

		default:
			break;
		}
	}

	private boolean validInput() {
		/*
		 * email=(EditText) findViewById(R.id.email); pwd=(EditText)
		 * findViewById(R.id.password);
		 */

		String txt_email = email.getText().toString();
		txt_email = txt_email.trim();
		String txt_pwd = pwd.getText().toString();
		txt_pwd = txt_pwd.trim();
		boolean isValidemail = false;
		boolean isValidpwd = false;
		if (TextUtils.isEmpty(txt_email)) {
			email.setError("Enter email id.");
			return isValidemail;
		} else {
			Pattern pattern = Pattern.compile(Constant.REGX_EMAIL);
			if (pattern.matcher(txt_email).matches()) {
				isValidemail = true;
			} else {
				email.setError("Enter valid email address");
				return isValidemail;
			}
		}

		if (TextUtils.isEmpty(txt_pwd)) {
			pwd.setError("Enter password.");
			return isValidpwd;
		} else if (txt_pwd.length() < 4) {
			makeToast("Minimum 4 charachers required in password!!");
			return isValidpwd;
		} else {
			isValidpwd = true;
		}

		return (isValidemail && isValidpwd);

	}

	/*
	 * toast method : to Show the toast message whenever is required
	 */

	private void makeToast(String string) {
		// TODO Auto-generated method stub

		Toast.makeText(LoginActivity.this, string, Toast.LENGTH_SHORT).show();

	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);
	}

	private class MyBackground extends AsyncTask<Void, Void, Void> {
		String email_id, pwd;
		Login list = null;

		MyBackground(String email, String psswd) {
			this.email_id = email;
			this.pwd = psswd;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setMessage("Stylist login please wait...");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				list = getloginData(email_id, pwd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog != null) {
				dialog.cancel();
			}
			if (list != null) {
				if (list.getStatus().contains("success")) {
					String email_id = list.getEmail();
					Log.e("email", email_id);
					String userid = list.getUserID();
					Log.e("user id", userid);
					String usertype = list.getUser_type();
					Log.e("user type", usertype);
					String firsttime = list.getFirst_login();
					String flag;
					if (firsttime.contains("Y")) {
						flag = "true";
					} else {
						flag = "false";
					}
					System.out.println("the flag" + flag);
					mGlobalData.setuserid(userid);
					mGlobalData.setCustomerinfo(userid, email_id, usertype,
							flag);
					mGlobalData.setLoggedin("true");
					Intent loginIntent = new Intent(LoginActivity.this,
							Home.class);
					loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					LoginActivity.this.finish();
					startActivity(loginIntent);

				} else {
					showMessageDialog(Constant.app_dialog, list.getMessage());
				}
			}
		}

	}

	private Login getloginData(String email, String pwd) {
		Login list = null;
		Log.e("role id", Integer.toString(mGlobalData.getRole()));
		String role_id = Integer.toString(mGlobalData.getRole());
		String url = Constant.API_URL + Constant.Login + "&useremail=" + email
				+ "&userpass=" + pwd;
		Log.e("URL", url);
		String response = null;

		try {
			response = Webservice.getData(getApplicationContext(), url);
			Log.e("response", response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// response = IoUtils.get(getApplicationContext(), url);

		if (response != null) {
			list = parseJson(response);
			// mGlobalData.setContact(response);
		}
		return list;
	}

	private Login parseJson(String jsonResponse) {

		Login response = new Login();
		Gson gson = new Gson();
		try {
			response = gson.fromJson(jsonResponse, response.getClass());
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	public void showMessageDialog(String title, String message) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(
				LoginActivity.this);
		builder.setMessage(message + ".").setTitle(title);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				// FixturesByRoundActivity.this.finish();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
		dialog.setCancelable(false);

	}

}
