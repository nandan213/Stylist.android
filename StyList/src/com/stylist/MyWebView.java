package com.stylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;


public class MyWebView extends FragmentActivity implements OnClickListener{
	
	private WebView webview;
	Intent intent;
	TextView txtHeader;
	private String uRL;
	ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.home_web_view_layout);
		initialize();
	}
	
	private void initializeTopBar()
	{
	 Button backButton, rightButton;
	 TextView headerTextView;
		backButton = (Button) findViewById(R.id.backButton);
		rightButton = (Button) findViewById(R.id.rightButton);
		
		rightButton.setVisibility(View.GONE);
		headerTextView = (TextView) findViewById(R.id.title_header);
		//headerTextView.setText(getString(R.string.news));
		backButton.setOnClickListener(this);
		
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		
		intent = getIntent();
		webview = (WebView) findViewById(R.id.contentWebView);

		if(intent != null && intent.hasExtra("URL"))
		{
			uRL = intent.getStringExtra("URL");
			webview.loadUrl(uRL);       
					
		}else {
			this.finish();
		}
		
		dialog = new ProgressDialog(MyWebView.this);
		dialog.setMessage("Loading page...");
		dialog.setCancelable(true);
		
		
		initializeTopBar();
		
		webview.setInitialScale(1);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setPluginState(PluginState.ON);
		webview.getSettings().setDefaultTextEncodingName("utf-8");
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webview.setScrollbarFadingEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true); 
	   /* webview.getSettings().setPluginsEnabled(true);
        webview.getSettings().setBuiltInZoomControls(false); 
        webview.getSettings().setSupportZoom(false);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);   
        webview.getSettings().setAllowFileAccess(true); 
        webview.getSettings().setDomStorageEnabled(true);
        webview.setWebViewClient(new MyWebViewClient());*/
		
		
		webview.setWebViewClient(new WebViewClient(){
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				
				try {
					dialog.show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				try {
					if(dialog!=null)
					{
					dialog.dismiss();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
        
		
		
	}
	
	 public class MyWebViewClient extends WebViewClient {        
	       
		 @Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			return super.shouldOverrideUrlLoading(view, url);
		}
	 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backButton:
			MyWebView.this.finish();
			break;
		default:
			break;
		}
	}

}
