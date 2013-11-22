package com.stylist;

import java.util.regex.Pattern;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.stylist.core.GlobalData;
import com.stylist.databasemanager.DatabaseHelper;
import com.stylist.dataclasses.CreateAccount;
import com.stylist.dataclasses.Customer;
import com.stylist.webservices.Connectivity;
import com.stylist.webservices.Constant;
import com.stylist.webservices.Webservice;

public class CreateNew extends FragmentActivity implements OnClickListener {

	private EditText email, password, confirmpassword, stylisttype, referral;
	private int selectedState = 0;
	private TextView terms, policy, headerTextView;
	private Button createacc, backButton;
	private CheckBox cb;
	private GlobalData mGlobalData;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.register);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mGlobalData = (GlobalData) getApplicationContext();
		intiliaze();
	}

	private void intiliaze() {

		backButton = (Button) findViewById(R.id.backButton);
		headerTextView = (TextView) findViewById(R.id.title_header);
		backButton.setOnClickListener(this);
		headerTextView.setText(getResources().getString(R.string.create));

		dialog = new ProgressDialog(CreateNew.this);
		email = (EditText) findViewById(R.id.email);
		password = (EditText) findViewById(R.id.password);
		confirmpassword = (EditText) findViewById(R.id.cnfrmpassword);
		stylisttype = (EditText) findViewById(R.id.selectstylisttype);
		referral = (EditText) findViewById(R.id.referral);
		terms = (TextView) findViewById(R.id.terms);
		policy = (TextView) findViewById(R.id.policy);
		createacc = (Button) findViewById(R.id.rl_createacc);
		cb = (CheckBox) findViewById(R.id.checkBox);
		stylisttype.setOnClickListener(this);
		terms.setOnClickListener(this);
		policy.setOnClickListener(this);
		createacc.setOnClickListener(this);
		DatabaseHelper.init(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.backButton:

			finish();
			break;

		case R.id.selectstylisttype:

			try {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						CreateNew.this);
				builder.setTitle("Select Stylist Type");
				builder.setSingleChoiceItems(Constant.stylist, selectedState,
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();
						selectedState = getIdByPercentage(Constant.stylist[which]);
						stylisttype.setText(Constant.stylist[which]);
					}

				});
				AlertDialog dialog = builder.create();

				dialog.show();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;

		case R.id.terms:

			if (Connectivity.hasConnection(CreateNew.this)) {
				Intent moreIntent = new Intent(CreateNew.this, MyWebView.class);
				moreIntent.putExtra("URL", "www.sty-list.com/terms");
				moreIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(moreIntent);
			} else {
				showMessageDialog(Constant.app_dialog, CreateNew.this
						.getResources().getString(R.string.no_internet_message));
			}
			break;
		case R.id.policy:

			if (Connectivity.hasConnection(CreateNew.this)) {
				Intent moreIntent = new Intent(CreateNew.this, MyWebView.class);
				moreIntent.putExtra("URL", "www.sty-list.com/policy");
				moreIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(moreIntent);
			} else {
				showMessageDialog(Constant.app_dialog, CreateNew.this
						.getResources().getString(R.string.no_internet_message));
			}

			break;

		case R.id.rl_createacc:

			Customer customer = validInput();
			if (customer != null) {
				if (Connectivity.hasConnection(CreateNew.this)) {
					new MyBackground(customer).execute();
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

	class MyBackground extends AsyncTask<Void, Void, Void> {
		Customer customer;
		CreateAccount list = null;

		MyBackground(Customer cust) {
			this.customer = cust;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Creating stylist Account....");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				list = getcontactsData(customer);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (dialog != null) {
				dialog.cancel();
			}
			if (list != null) {
				if (list.getStatus().contains("success")) {
					showMessageDialog_new(Constant.app_dialog,
					"Stylist created");
				} else {
					showMessageDialog(Constant.app_dialog, list.getMessage());
				}
			}
		}

	}

	private CreateAccount getcontactsData(Customer customer) {
		CreateAccount list = null;
		Log.e("role id", Integer.toString(mGlobalData.getRole()));
		String role_id = Integer.toString(mGlobalData.getRole());
		String url = Constant.API_URL + Constant.Createaccount + "&useremail="
		+ customer.getEmail() + "&userpass=" + customer.getPwd()
		+ "&user_type=" + role_id + "&stylist_type="
		+ customer.getStylist();
		Log.e("URL", url);
		String response = null;

		try {
			response = Webservice.getData(getApplicationContext(), url);
			// Log.e("response", response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// response = IoUtils.get(getApplicationContext(), url);

		if (response != null) {
			list = parseJson(response);
			// mGlobalData.setContact(response);
		}
		return list;
	}

	private CreateAccount parseJson(String jsonResponse) {

		CreateAccount response = new CreateAccount();
		Gson gson = new Gson();
		try {
			response = gson.fromJson(jsonResponse, response.getClass());
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}

		return response;
	}

	private Customer validInput() {
		Customer customer = null;

		String txt_email = email.getText().toString();
		txt_email = txt_email.trim();
		String txt_pwd = password.getText().toString();
		txt_pwd = txt_pwd.trim();
		String txt_cnfrm = confirmpassword.getText().toString();
		txt_cnfrm = txt_cnfrm.trim();
		String txt_stylist = stylisttype.getText().toString();
		txt_stylist.trim();
		String txt_ref = referral.getText().toString();
		txt_ref = txt_ref.trim();
		if (TextUtils.isEmpty(txt_email)) {
			email.setError("Enter email id.");
			return customer;
		} else {
			Pattern pattern = Pattern.compile(Constant.REGX_EMAIL);
			if (pattern.matcher(txt_email).matches()) {

			} else {
				email.setError("Enter valid email address");
				return customer;
			}
		}

		if (TextUtils.isEmpty(txt_pwd)) {
			password.setError("Enter password.");
			return customer;
		} else if (txt_pwd.length() < 4) {
			makeToast("Minimum 4 charachers required in password!!");
			return customer;
		} else {

		}

		if (TextUtils.isEmpty(txt_cnfrm)) {
			confirmpassword.setError("Enter confirm password.");
			return customer;
		} else if (txt_cnfrm.length() < 4) {
			makeToast("Minimum 4 charachers required in confirm password!!");
			return customer;
		} else {

		}

		if (txt_pwd.equals(txt_cnfrm) == false) {
			makeToast("Password and Confirm Password does not match.");
			return customer;
		} else {

		}

		if (TextUtils.isEmpty(txt_stylist)) {
			stylisttype.setError("Select a Stylist type.");
			return customer;
		} else {

		}

		if (!cb.isChecked()) {
			makeToast("Accept the terms and condition.");
			return customer;
		}
		/*
		 * String txt_email=email.getText().toString(); String
		 * txt_pwd=password.getText().toString(); String
		 * txt_cnfrm=confirmpassword.getText().toString(); String
		 * txt_stylist=stylisttype.getText().toString(); String
		 * txt_ref=referral.getText().toString();
		 */
		Log.e("email", email.getText().toString());
		Log.e("email", password.getText().toString());
		Log.e("email", confirmpassword.getText().toString());
		Log.e("email", stylisttype.getText().toString());
		Log.e("email", referral.getText().toString());
		customer = new Customer();
		customer.setEmail(email.getText().toString());
		customer.setPwd(password.getText().toString());
		customer.setCnfpwd(confirmpassword.getText().toString());
		String id = getIdBytext(stylisttype.getText().toString());
		Log.e("stylist id", id);
		customer.setStylist(id);
		customer.setReferral(referral.getText().toString());

		return customer;
	}

	private int getIdByPercentage(String gender) {

		// private final CharSequence[] genderList
		// ={"Mr","Mrs","Miss","Dr.","Sir"};

		int id = 0;

		if (gender.equals("Barber")) {
			id = 0;
		} else if (gender.equals("Beautician")) {

			id = 1;
		} else if (gender.equals("Manicure")) {

			id = 2;
		} else {
			id = 0;
		}

		return id;

	}

	public void showMessageDialog(String title, String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(CreateNew.this);
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

	public void showMessageDialog_new(String title, String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(CreateNew.this
		);
		builder.setMessage(message + ".").setTitle(title);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				CreateNew.this.finish();
				// FixturesByRoundActivity.this.finish();
			}
		});

		AlertDialog dialog = builder.create();
		dialog.show();
		dialog.setCancelable(false);

	}

	/*
	 * toast method : to Show the toast message whenever is required
	 */

	private void makeToast(String string) {

		Toast.makeText(CreateNew.this, string, Toast.LENGTH_SHORT).show();

	}

	private String getIdBytext(String gender) {

		// private final CharSequence[] genderList
		// ={"Mr","Mrs","Miss","Dr.","Sir"};

		String id = "";

		if (gender.equals("Barber")) {
			id = "1";
		} else if (gender.equals("Beautician")) {

			id = "2";
		} else if (gender.equals("Manicure")) {

			id = "3";
		} else {
			id = "1";
		}

		return id;

	}

}
