package com.stylist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class EditShop extends Activity implements OnClickListener{

	public  EditText shop_name, shop_add, shop_city, shop_state, shop_zip;
	public RelativeLayout rl_addstore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editshopdetail);
		intiliazeView();
	}
	
	public  void intiliazeView( ) {
		shop_name = (EditText) findViewById(R.id.shopname);
		shop_add = (EditText) findViewById(R.id.shopaddress);
		shop_city = (EditText) findViewById(R.id.shopcity);
		shop_state = (EditText) findViewById(R.id.shopstate);
		shop_zip = (EditText) findViewById(R.id.shopzip);
		rl_addstore = (RelativeLayout) findViewById(R.id.edit_shopdetail);
		rl_addstore.setOnClickListener(this);
	}

	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	switch (v.getId()) {

	default:
		break;
	}
	
	
	}
}
