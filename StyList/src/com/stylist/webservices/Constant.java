package com.stylist.webservices;

import android.os.Environment;


public class Constant {

	// database constants
	
	public static final String DATABASE_NAME =  "/data/data/com.stylist/databases/stylist.sqlite";

	public static final int DATABASE_VERSION = 1;

	// API Url's
	public final static String API_URL = "http://www.sphinx-solution.com/stylist/getDataV1.php?";
	public final static String Createaccount="action=create_account";
	public final static String Login="action=auth";
	public final static String Login_Flag="action=update_login_flag";
	public final static String Announcement="announcements?format=json";
	public final static String Specials="specials?format=json";
	public final static String app_dialog="Sty-List";
	//Get vehicle data url
	//public final static String getvehicle_detail=""
	
	
	public final static String REGX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	
	 public final static String REGX_UK_POSTCODE = "^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)$";
	 // public final static String REGX_CODE="^([a-zA-Z]{3}[\w.-])*$";
	 public final static String REGX_NOT_A_NUMBER = "^(?:[a-zA-Z ']+(?:[.'-])?\\s?)+$";
	 public final static String RESPONSE_SUCCESS = "Success";
	public final static String RESPONSE_ERROR = "Error";
	public static final int contact_handler=157; 
	public static final int DogbinResponse=198;
	public final static int SUCCESS=117;
	public final static String[] stylist={"Barber","Beautician","Manicure"};
	public final static String REGX_CHAR ="^[A-Z]{2}[0-9]{4}$";
	public final static String ReGX_NUMBER="^[0-9]{10,12}$";
	
	

}
