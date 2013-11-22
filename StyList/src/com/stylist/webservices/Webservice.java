package com.stylist.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

public class Webservice {
	
	public static String getData(Context applicationContext, String url_str)
	{
		 URL url;
		 BufferedReader ibn=null;
		 String data="";
		 HttpURLConnection urlConnection = null;
		 try {
			url = new URL(url_str);
			try {
				urlConnection = (HttpURLConnection) url.openConnection();
				/*if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) 
				{ 
					urlConnection.setRequestProperty("Connection", "close"); 
				}else
				{*/
					urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
					urlConnection.setRequestProperty("Accept","*/*");
				//urlConnection.setRequestProperty("Accept-Encoding", "identity");
				//}

				InputStream in = urlConnection.getInputStream();
				InputStreamReader isw = new InputStreamReader(in);
				 try {
						ibn =new BufferedReader(new InputStreamReader(in));
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 
				 StringBuffer sb=new StringBuffer("");
			        String l="";
			        String nl=System.getProperty("line.separator");

			        try {
						while((l=ibn.readLine())!=null){
						    sb.append(l + nl);
						   // return sb.toString();

						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			       
			        data=sb.toString();
			        return data;
				/* int data = isw.read();
				 while (data != -1) {
			            char current = (char) data;
			            data = isw.read();
			            System.out.print(current);
			        }*/
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			urlConnection.disconnect();

		}
		return data;
	}

}
