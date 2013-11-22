package com.stylist;

import com.stylist.core.GlobalData;
import com.stylist.databasemanager.DatabaseHelper;
import com.stylist.dataclasses.UserShop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class Home_welcomethird extends FragmentActivity implements OnClickListener {

	private EditText shop_name,shop_add,shop_city,shop_state,shop_zip;
	private RelativeLayout rl_addstore;
	private GlobalData mGlobalData; 
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shop);
		mGlobalData=(GlobalData) getApplicationContext();
		intiliaze();
	}
	
	private void intiliaze()
	{
		// EditText shop_name,shop_add,shop_city,shop_state,shop_zip;
		dialog=new ProgressDialog(Home_welcomethird.this);
		shop_name=(EditText) findViewById(R.id.shopname);
		shop_add=(EditText) findViewById(R.id.shopaddress);
		shop_city=(EditText) findViewById(R.id.shopcity);
		shop_state=(EditText) findViewById(R.id.shopstate);
		shop_zip=(EditText) findViewById(R.id.shopzip);
		rl_addstore=(RelativeLayout) findViewById(R.id.rl_next);
		rl_addstore.setOnClickListener(this);
		DatabaseHelper.init(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_next:
			
			new SavetoDb().execute();
			
			break;

		default:
			break;
		}
	}
	
	private void saveDb()
	{
		UserShop user=new UserShop();
		//EditText shop_name,shop_add,shop_city,shop_state,shop_zip;
		String sname=shop_name.getText().toString();
		sname=sname.trim();
		String sadd=shop_add.getText().toString();
		sadd=sadd.trim();
		String scity=shop_city.getText().toString();
		scity=scity.trim();
		String sstate=shop_state.getText().toString();
		sstate=sstate.trim();
		String szip=shop_zip.getText().toString();
		szip=szip.trim();
		
		user.setShopname(sname);
		user.setShopadd(sadd);
		user.setShopcity(scity);
		user.setShopstate(sstate);
		user.setShopzip(szip);
		mGlobalData.setUserShopDetail(user);
	}
	
	private class SavetoDb extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(dialog!=null);
			{
				dialog.cancel();
			}
			Intent returnIntent = new Intent();
			returnIntent.putExtra("result","done");
			
			setResult(RESULT_OK,returnIntent);     
			finish();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			saveDb();
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.setMessage("Savind data.");
			dialog.setCancelable(false);
			dialog.show();
		}
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}

}
