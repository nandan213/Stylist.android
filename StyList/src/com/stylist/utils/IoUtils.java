package com.stylist.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.stylist.R;

public class IoUtils {

	private static final String TAG = "Utils";
	private static final int CONNECTION_TIMEOUT_IN_MILLIS = 5500000;
	private static final int REQUEST_TIMEOUT_IN_MILLIS = 300000;
	public static final int CHUNK_SIZE = 4096;
	private static org.apache.http.conn.ssl.SSLSocketFactory sslSocketFactory = null;
	private static HttpClient httpClient = null;
	private static ThreadSafeClientConnManager cm;
	public static final String NO_INTERNET = "Internet not available";
	public static final String ERROR = "Error";

	public static String getMd5For(String s) {
		try {

			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String hexValue = Integer.toHexString(0xFF & messageDigest[i]);
				if (hexValue.length() == 1) {
					hexValue = "0" + hexValue;
				}
				hexString.append(hexValue);
			}
			Log.v(TAG, "MD5 for '" + s + "'  is: " + hexString);
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void disableConnectionReuseIfNecessary() {

		if (hasHttpConnectionBug()) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	public static boolean hasHttpConnectionBug() {

		return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
	}

	public static String get(Context applicationContext, String url)
			throws ClientProtocolException, IOException {
		InputStream is = getStream(applicationContext, url);
		String response = slurp(is);
		if (is != null) {
			is.close();
		}
		Log.v(TAG, "Response:\n" + response);
		return response;
	}

	public static InputStream getStream(Context applicationContext, String url)
			throws ClientProtocolException, IOException {
		HttpClient httpClient = getHttpClient(applicationContext);
		HttpContext localContext = new BasicHttpContext();
		url = url.replaceAll(" ", "%20");
		Log.v(TAG, "Url : " + url);
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Accept-Encoding", "gzip");
		HttpResponse response = httpClient.execute(httpGet, localContext);
		InputStream is = response.getEntity().getContent();
		Header contentEncoding = response.getFirstHeader("Content-Encoding");
		if (contentEncoding != null
				&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			is = new GZIPInputStream(is);
		}
		return is;
	}

	public static String post(Context applicationCntext, String url,
			List<NameValuePair> nameValuePairs) throws ClientProtocolException,
			IOException {
		return post(applicationCntext, url, nameValuePairs, new String[0]);
	}

	public static String post(Context applicationCntext, String url,
			List<NameValuePair> nameValuePairs, String[] fileParameterNames)
			throws ClientProtocolException, IOException {
		InputStream is = getStream(applicationCntext, url, nameValuePairs,
				fileParameterNames);
		String response = slurp(is);
		Log.v(TAG, "Response:\n" + response);
		if (is != null) {
			is.close();
		}
		return response;
	}

	public static InputStream getStream(Context applicationCntext, String url,
			List<NameValuePair> nameValuePairs) throws ClientProtocolException,
			IOException {
		return getStream(applicationCntext, url, nameValuePairs, new String[0]);
	}

	public static InputStream getStream(Context applicationCntext, String url,
			List<NameValuePair> nameValuePairs, String[] fileParameterNames)
			throws ClientProtocolException, IOException {
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);

		Log.v(TAG, "Querying: " + url + " with following parameters");
		for (int index = 0; index < nameValuePairs.size(); index++) {
			Log.v(TAG, nameValuePairs.get(index).getName()
					+ "="
					+ (nameValuePairs.get(index).getValue() == null ? ""
							: nameValuePairs.get(index).getValue()));
			boolean isImage = false;
			if (fileParameterNames != null && fileParameterNames.length > 0) {
				for (String p : fileParameterNames) {
					if (nameValuePairs.get(index).getName().equalsIgnoreCase(p)) {
						isImage = true;
						break;
					}
				}
			}
			if (isImage) {

				entity.addPart(nameValuePairs.get(index).getName(),
						new FileBody(new File(nameValuePairs.get(index)
								.getValue())));
			} else {

				entity.addPart(
						nameValuePairs.get(index).getName(),
						new StringBody(
								nameValuePairs.get(index).getValue() == null ? ""
										: nameValuePairs.get(index).getValue()));
			}
		}

		HttpClient httpClient = getHttpClient(applicationCntext);
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept-Encoding", "gzip");
		HttpResponse response = httpClient.execute(httpPost, localContext);
		InputStream is = response.getEntity().getContent();
		Header contentEncoding = response.getFirstHeader("Content-Encoding");
		if (contentEncoding != null
				&& contentEncoding.getValue().equalsIgnoreCase("gzip")) {
			is = new GZIPInputStream(is);
		}
		return is;
	}

	public static boolean isOnline(Context applicationContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	public static void showAlert(Activity activityContext, String title,
			String message) {
		showAlert(activityContext, title, message, null);
	}

	public static void showAlert(Activity activityContext, String title,
			String message, OnDismissListener onDismissListener) {

		AlertDialog.Builder builder = new Builder(activityContext);
		builder.setTitle(title);
		builder.setView(getLinkifiedTextViewWithText(activityContext, message));
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
		AlertDialog alert = builder.create();
		alert.setOnDismissListener(onDismissListener);
		alert.show();
	}

	private static final TextView getLinkifiedTextViewWithText(Context c,
			String message) {
		final TextView messageTextView = new TextView(c);
		final SpannableString s = new SpannableString(Html.fromHtml(message));
		Linkify.addLinks(s, Linkify.WEB_URLS);
		messageTextView.setTextColor(Color.WHITE);
		messageTextView.setText(s);
		messageTextView.setMovementMethod(LinkMovementMethod.getInstance());
		int padding = c.getResources().getDimensionPixelSize(
				R.dimen.padding_medium);
		messageTextView.setPadding(padding, padding, padding, padding);
		return messageTextView;
	}

	public static String slurp(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	public static HttpClient getHttpClient(Context applicationContext) {
		disableConnectionReuseIfNecessary();
		HttpParams httpParameters = new BasicHttpParams();

		int timeoutConnection = CONNECTION_TIMEOUT_IN_MILLIS;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);

		int timeoutSocket = REQUEST_TIMEOUT_IN_MILLIS;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		final SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		if (sslSocketFactory == null) {
			sslSocketFactory = createAdditionalCertsSSLSocketFactory(applicationContext);
		}
		schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
		try {

			if (cm == null) {
				cm = new ThreadSafeClientConnManager(httpParameters,
						schemeRegistry);
				cm.closeExpiredConnections();
			}
			if (httpClient == null) {
				httpClient = new DefaultHttpClient(cm, httpParameters);
			} else {
				httpClient.getConnectionManager().closeExpiredConnections();
			}
		} catch (Exception e) {
			e.printStackTrace();
			cm = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);
			cm.closeExpiredConnections();
			httpClient = new DefaultHttpClient(cm, httpParameters);
		}
		return httpClient;
	}

	public static void closeHttpConnection() {
		try {
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		} catch (Exception e) {
		}
	}

	public static org.apache.http.conn.ssl.SSLSocketFactory createAdditionalCertsSSLSocketFactory(
			Context context) {

		try {

			return new AdditionalKeyStoresSSLSocketFactory();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// (upload image to server to use this method )
	// -----------------------------------------------------------------
	public static String postImgData(String url,
			List<NameValuePair> nameValuePairs, String[] fileParameterNames)
			throws ClientProtocolException, IOException {
		String newUrl = url.replaceAll(" ", "%20");
		Log.v(TAG, "Response:\n" + newUrl);
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(newUrl);
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE);
		for (int index = 0; index < nameValuePairs.size(); index++) {
			boolean isImage = false;
			if (fileParameterNames != null && fileParameterNames.length > 0) {
				for (String p : fileParameterNames) {
					if (nameValuePairs.get(index).getName().equalsIgnoreCase(p)) {
						isImage = true;
						break;
					}
				}
			}
			if (isImage) {
				// We use FileBody to transfer the file data
				entity.addPart(nameValuePairs.get(index).getName(),
						new FileBody(new File(nameValuePairs.get(index)
								.getValue())));
			} else {
				// Normal string data
				entity.addPart(nameValuePairs.get(index).getName(),
						new StringBody(nameValuePairs.get(index).getValue()));
			}
		}
		httpPost.setEntity(entity);
		HttpResponse response = httpClient.execute(httpPost, localContext);
		InputStream is = response.getEntity().getContent();
		BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
		String strLine;
		String resp = "";
		while ((strLine = br1.readLine()) != null) {
			resp += strLine;
		}
		return resp;
	}
}
