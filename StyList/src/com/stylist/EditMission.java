package com.stylist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditMission extends Activity implements OnClickListener{

	public EditText missionEdit;
	public Button missionSave;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mission_home);
	}
	
	
	public  void intilizeMissionView(){
		missionEdit = (EditText) findViewById(R.id.missionEditText);
		missionSave = (Button) findViewById(R.id.missinSave);
		missionSave.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
}
