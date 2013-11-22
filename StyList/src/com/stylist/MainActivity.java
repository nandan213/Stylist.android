package com.stylist;




import com.stylist.core.GlobalData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity implements OnClickListener {

	Button client,stylist;
	int i=0;
	private GlobalData mGlobalData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mGlobalData=(GlobalData) getApplicationContext();
		if(mGlobalData.getLoggedin().contains("true"))
		{
			Intent home=new Intent(MainActivity.this,Home.class);
			MainActivity.this.finish();

        	startActivity(home);
		}
		
		intiliaze();
	}
	
	private void intiliaze()
	{
		client=(Button) findViewById(R.id.client);
		stylist=(Button) findViewById(R.id.stylist);
		client.setOnClickListener(this);
		stylist.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.client:
			i=2;
			mGlobalData.setRole(i);
			
			break;
		case R.id.stylist:
			i=1;
			mGlobalData.setRole(i);
			Intent sty=new Intent(MainActivity.this,WelcomeS.class);
			MainActivity.this.finish();
			startActivity(sty);
			break;

		default:
			break;
		}
		
	}

}
