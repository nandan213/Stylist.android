package com.stylist;

import com.stylist.adapter.TestFragmentAdapter;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class WelcomeS extends FragmentActivity implements OnClickListener {
	TestFragmentAdapter mAdapter;
	ViewPager mPager;
	PageIndicator mIndicator;
	RelativeLayout  createnew;
	private Button login;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.welcomes);
		mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		intiliaze();
		
	}

	private void intiliaze() {
		login = (Button) findViewById(R.id.rl_loginin);
		createnew = (RelativeLayout) findViewById(R.id.rl_createnew);
		login.setOnClickListener(this);
		createnew.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_loginin:
			this.finish();
			Intent login = new Intent(WelcomeS.this, LoginActivity.class);
			startActivity(login);
			break;

		case R.id.rl_createnew:
			Intent createAcc = new Intent(WelcomeS.this, CreateNew.class);
			startActivity(createAcc);
			break;

		default:
			break;
		}
	}

}
