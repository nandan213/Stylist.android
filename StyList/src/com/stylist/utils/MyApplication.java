package com.stylist.utils;

import java.util.HashMap;
import android.app.Application;
import android.graphics.Typeface;

public class MyApplication extends Application {

	private static final String TAG = "MyApplication";
	public static final String LOCATION_UPDATED = "com.sphinx.tinder.LOCATION_UPDATED";
	public static final String LOCATION_TIMED_OUT = "com.sphinx.tinder.LOCATION_TIMED_OUT";
	public static final String EXTRA_LOCATION = "extra_location";
	private static MyApplication singleton;
	private HashMap<String, Typeface> fonts = new HashMap<String, Typeface>();

	public static MyApplication getInstance() {
		return singleton;
	}

	public void onCreate() {
		super.onCreate();
		singleton = this;
		// if (!Log.isInDebugMode()) {
		// Log.setUncaughtExceptionHandler(getApplicationContext());
		// }
		// Log.sendCrashLogIfAvailable(getApplicationContext());
		// lm = new CustomLocationManager(getApplicationContext());
		// loc = lm.getLastBestLocation(Constant.MIN_DISTANCE_METERS,
		// Constant.LOCATION_UPDATE_INTERVAL_MILLIS);
		//
		// try {
		// Log.i("", "copyDatabaseToSdCard");
		// DatabaseHelper.init(getApplicationContext());
		// DatabaseHelper.copyDatabaseToSdCard();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public void addCustomFont(String name, Typeface f) {
		fonts.put(name, f);
	}

	public Typeface getCustomFont(String ttfName) {
		Typeface f = fonts.get(ttfName);
		if (f == null) {
			f = Typeface.createFromAsset(getAssets(), ttfName);
			if (f == null) {
				throw new RuntimeException("Font not found!");
			}
			fonts.put(ttfName, f);
		}
		return f;
	}

	// public void onLocationChanged(Location l) {
	// if (l != null) {
	// Log.i("MyApplication", "Received location from some provider: " + l);
	// if (!isBetterLocation(l, loc)) {
	// l = loc;
	// }
	// }
	// Log.i(TAG,
	// "Current time: "
	// + Constant.dfYYYY_MM_DD_HH_mm_ss.format(Calendar
	// .getInstance().getTime()));
	// Log.i(TAG,
	// "Location Obtained at: "
	// + Constant.dfYYYY_MM_DD_HH_mm_ss.format(new Date(l
	// .getTime())));
	// String text = "Received new location!!!";
	// text += "\n\nLoc                : " + l.getLatitude() + ", "
	// + l.getLongitude();
	// text += "\nAccuracy       : " + l.getAccuracy() + " meters";
	// text += "\nObtained At  : "
	// + Constant.dfYYYY_MM_DD_HH_mm_ss_SSS.format(new Date(l
	// .getTime()));
	// text += "\nThis msg updated At: "
	// + Constant.dfYYYY_MM_DD_HH_mm_ss_SSS.format(Calendar
	// .getInstance().getTime());
	//
	// Log.i(TAG, "Accuracy: " + l.getAccuracy());
	// try {
	// lm.setGotLocation(true);
	// } catch (Exception e) {
	// }
	// loc = l;
	// Intent intent = new Intent(LOCATION_UPDATED);
	// intent.putExtra(EXTRA_LOCATION, loc);
	// LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	// Log.i(TAG, "Accepting location fix: " + l);
	// lm.removeLocationUpdates(this);
	// }
	//
	// public void onProviderDisabled(String provider) {
	// }
	//
	// @Override
	// public void onProviderEnabled(String provider) {
	// }
	//
	// @Override
	// public void onStatusChanged(String provider, int status, Bundle extras) {
	// }
	//
	// @Override
	// public void locationTimedOut() {
	// Log.v(TAG, "Location timed out...");
	// lm.removeLocationUpdates(this);
	// Intent intent = new Intent(LOCATION_TIMED_OUT);
	// LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	// }
	//
	// public void setLocation(Location l) {
	// loc = l;
	// }
	//
	// public Location getMyLocation() {
	// return loc;
	// }
	//
	// /** Checks whether two providers are the same */
	// private boolean isSameProvider(String provider1, String provider2) {
	// if (provider1 == null) {
	// return provider2 == null;
	// }
	// return provider1.equals(provider2);
	// }
	//
	// /**
	// * Determines whether one Location reading is better than the current
	// * Location fix
	// *
	// * @param location
	// * The new Location that you want to evaluate
	// * @param currentBestLocation
	// * The current Location fix, to which you want to compare the new
	// * one
	// */
	// protected boolean isBetterLocation(Location location,
	// Location currentBestLocation) {
	// if (currentBestLocation == null) {
	// // A new location is always better than no location
	// return true;
	// }
	//
	// // Check whether the new location fix is newer or older
	// long timeDelta = location.getTime() - currentBestLocation.getTime();
	// boolean isSignificantlyNewer = timeDelta >
	// (Constant.LOCATION_UPDATE_INTERVAL_MILLIS * 4);
	// boolean isSignificantlyOlder = timeDelta <
	// -(Constant.LOCATION_UPDATE_INTERVAL_MILLIS * 4);
	// boolean isNewer = timeDelta > 0;
	//
	// // If it's been more than two minutes since the current location, use
	// // the new location
	// // because the user has likely moved
	// if (isSignificantlyNewer) {
	// return true;
	// // If the new location is more than two minutes older, it must be
	// // worse
	// } else if (isSignificantlyOlder) {
	// return false;
	// }
	//
	// // Check whether the new location fix is more or less accurate
	// int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
	// .getAccuracy());
	// boolean isLessAccurate = accuracyDelta > 0;
	// boolean isMoreAccurate = accuracyDelta < 0;
	// boolean isSignificantlyLessAccurate = accuracyDelta > 200;
	//
	// // Check if the old and new location are from the same provider
	// boolean isFromSameProvider = isSameProvider(location.getProvider(),
	// currentBestLocation.getProvider());
	//
	// // Determine location quality using a combination of timeliness and
	// // accuracy
	// if (isMoreAccurate) {
	// return true;
	// } else if (isNewer && !isLessAccurate) {
	// return true;
	// } else if (isNewer && !isSignificantlyLessAccurate
	// && isFromSameProvider) {
	// return true;
	// }
	// return false;
	// }
	//
	// public CustomLocationManager getCustomLocationManager() {
	// return lm;
	// }

}
