package com.stylist;

import com.stylist.databasemanager.DatabaseHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

public class Home_welcome extends FragmentActivity implements OnClickListener{

	RelativeLayout rl_next;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_welcome);
		intiliaze();
	}
	
	private void intiliaze()
	{
		rl_next=(RelativeLayout) findViewById(R.id.rl_next);
		rl_next.setOnClickListener(this);
		
		DatabaseHelper.init(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_next:
			//Home_welcome.this.finish();
			Intent second=new Intent(Home_welcome.this,Home_welcomesecond.class);
			second.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivityForResult(second,154);
			
			   
			//finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 154)
		{
			 if(resultCode == RESULT_OK)
			 {
				 String result=data.getStringExtra("result");
				 Intent returnIntent = new Intent();
				 returnIntent.putExtra("result",result);
					
					setResult(RESULT_OK,returnIntent);     
					finish();
			 }
		}
	}

}
