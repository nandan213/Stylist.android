package com.stylist.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import android.database.sqlite.SQLiteDatabase;

public class Constant {

	public static String TAG = "Flinx";
	public static String App_ID = "154655124717170";
	public static String Google_DeviceRegId = "255202665512";
	// public static String Base_Url =
	// "http://sphinx-solution.com/tinder/getDataV1.php?";
	public static String Base_Url = "https://flinxapp.com/api/getDataV1.2.php?";
	public static String MY_KEY = "Sph!nxR0ck";
	// public static String Base_Url1 =
	// "http://sphinx-solution.com/tinder/getDataV1.1.php?";
	// public static String Base_Url1 =
	// "https://flinxapp.com/api/getDataV1.2.php?";

	public static final int MIN_DISTANCE_METERS = 100;// meters
	public static final int LOCATION_UPDATE_INTERVAL_MILLIS = 30 * 1000; // 30

	public static String USER_ID = "UserId";
	public static String USER_INFO_OBJECT = "User Object";
	public static String MATCH_FRIEND_OBJECT = "Match Friend Object";

	public static String EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"
			+ "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
			+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+";

	public static String ARRAYLIST = "ArrayList";
	public static String HEADERTEXT = "HeaderText";
	public static String KEY = "Sph!nxRock";
	public static SimpleDateFormat dfYYYY_MM_DD_HH_mm_ss = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	public static SimpleDateFormat dfYYYY_MM_DD_HH_mm_ss_SSS = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);

	public static String FRIENDS_ID = "friendsId";
	public static String FRIENDS_NAME = "friendName";
	public static String FRIENDS_IMAGE = "friendImage";
	public static String FRIENDS_VISIBLE = "visible";
	public static String ROW_ID = "rowid";

	public static String CHAT_FREND_OBJECT = "ChatFriendObject";

	public static SimpleDateFormat df_EEE_MMM_dd_yyyy = new SimpleDateFormat();

	// Category table columns
	public static String TABLE_USER_CHAT_MESSAGE = "UserChatMessage";
	public static String COL_SENDER_ID = "senderId";
	public static String COL_SENDER_NAME = "senderName";
	public static String COL_RECEIVER_ID = "receiverId";
	public static String COL_RECEIVER_NAME = "receiverName";
	public static String COL_CHAT_MESSAGE = "message";
	public static String COL_MESSAGE_STATUS = "status";
	public static String COL_MESSAGE_TIME = "sendTime";

	com.stylist.databasemanager.DatabaseHelper helper;
	public SQLiteDatabase database = com.stylist.databasemanager.DatabaseHelper.getSqliteDatabase();
	public static String DB_NAME = "flinx.sqlite";
	public static int VERSION = 1;

	public static String ACTION_SETTINGS_RECEIVER = "com.sphinx.tinderapp.action_settings_receiver";
	public static String SHOW_IMAGE_URL = "https://flinxapp.com/api/showImage.php?";
	public static String ACTION_MATCH_RECEIVER = "com.sphinx.tinderapp.action_match_receiver";
	public static String ACTION_SUGGEST_MATCH_RECEIVER = "com.sphinx.tinderapp.action_suggest_match_receiver";
	public static String ACTION_UNREADMSG_RECEIVER = "com.sphinx.tinderapp.action_unreadmsg_receiver";
	public static String ACTION_CHATFRND_RECEIVER = "com.sphinx.tinderapp.action_chatfrnd_receiver";
	public static String ACTION_DOWNLOAD_RECEIVER = "com.sphinx.tinderapp.action_download_receiver";
}
