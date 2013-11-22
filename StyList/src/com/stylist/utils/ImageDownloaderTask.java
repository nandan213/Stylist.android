package com.stylist.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

public class ImageDownloaderTask {
	private File cacheDir;
	PhotosLoader photoLoaderThread = new PhotosLoader();
	private boolean destroyCahe = false;
	private PhotosToLoad photosToLoad = new PhotosToLoad();
	private WeakReference<Activity> weakActivity;
	Context c;

	public ImageDownloaderTask(Context ac, File cacheDir) {
		Log.v("", "cacheDir=" + cacheDir);
		// weakActivity = new WeakReference<Activity>(ac);
		c = ac;
		this.cacheDir = cacheDir;
		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
	}

	public void queuePhoto(String url) {
		// This ImageView may be used for other images before. So there may be
		// some old tasks in the queue. We need to discard them.
		if (photosToLoad.contains(url)) {
			// Log.v(Common.TAG, "Image Downloader url already in queue: "+
			// url);
			return;
		}
		if (photosToLoad.size() >= 0) {
			synchronized (photosToLoad) {
				photosToLoad.add(url);
				photosToLoad.notifyAll();
			}
		}
	}

	public void startPhotoDownloadThread() {
		// start thread if it's not started yet
		if (photoLoaderThread != null
				&& photoLoaderThread.getState() == Thread.State.NEW)
			photoLoaderThread.start();
	}

	public void queuePhotos(ArrayList<String> urls) {
		// This ImageView may be used for other images before. So there may be
		// some old tasks in the queue. We need to discard them.

		Log.v("", "All URLS-" + urls);
		for (int u = 0; u < urls.size(); u++) {
			String url = urls.get(u);
			if (photosToLoad.contains(url))
				continue;
			if (photosToLoad.size() >= 0) {
				synchronized (photosToLoad) {
					photosToLoad.add(url);
					photosToLoad.notifyAll();
				}
			}
		}
		// start thread if it's not started yet
		if (photoLoaderThread != null
				&& photoLoaderThread.getState() == Thread.State.NEW)
			photoLoaderThread.start();
	}

	public class updateProgress implements Runnable {

		public void run() {
			Log.e("", "Current Thread-" + Thread.currentThread());
			try {
				Thread.sleep(1000);
				Message msg = new Message();
				msg.arg1 = 0;
				progressHandler.sendMessage(msg);
				// progressHandler.handleMessage(msg);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (Exception e) {
			}
		}
	}

	public Handler progressHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == 0) {
				// adapter.notifyDataSetChanged();
			}
		}
	};

	class PhotosLoader extends Thread {
		public void run() {

			try {
				while (true) {
					if (destroyCahe) {
						Log.v(Constant.TAG,
								"Destoried cache. let's stop thread");
						break;
					}
					// thread waits until there are any images to load in the
					// queue
					if (photosToLoad.size() == 0)
						synchronized (photosToLoad) {
							Log.v(Constant.TAG, "Download stops");
							// Runnable runnable = new updateProgress();
							// photosToLoad.wait();

							Intent intent = new Intent(
									Constant.ACTION_DOWNLOAD_RECEIVER);
							LocalBroadcastManager.getInstance(c).sendBroadcast(
									intent);
							// intent.setAction(Constant.ACTION_DOWNLOAD_RECEIVER);
							// weakActivity.get().sendBroadcast(intent);
							// adapter.notifyDataSetChanged();
							Log.v(Constant.TAG, "Sending broadcast");
							photosToLoad.wait();
						}
					if (photosToLoad.size() != 0) {
						String photoToLoadUrl = "";
						synchronized (photosToLoad) {
							photoToLoadUrl = photosToLoad.pop();
							if (TextUtils.isEmpty(photoToLoadUrl))
								continue;
							// int photoid = photoToLoadUrl.lastIndexOf("=");
							// String
							// newFileName=photoToLoadUrl.substring(photoid,
							// photoToLoadUrl.length());

							String downloadedimageUrl = photoToLoadUrl
									.toString();

							String array[] = downloadedimageUrl.toString()
									.split("\\&");
							String newFileName = array[0];
							int index = newFileName.indexOf("=");
							newFileName = downloadedimageUrl.substring(
									index + 1, newFileName.length());
							Log.v("", "Loader task newFileName=" + newFileName);
							String filename = IoUtils.getMd5For(photoToLoadUrl);
							filename = newFileName + ".png";
							File file = new File(cacheDir, filename);
							Log.v("", "filename=" + filename);
							if (file.exists()) {
								Log.v("",
										"Image Downloader file already exists: "
												+ photoToLoadUrl);
								continue;
							}
							try {
								filename = newFileName + ".png";
								file = new File(cacheDir, filename);
								file.delete();
								URL url = new URL(photoToLoadUrl);
								URLConnection conexion = url.openConnection();
								conexion.connect();
								InputStream input = new BufferedInputStream(
										url.openStream());
								Log.v("",
										"file.getAbsolutePath()="
												+ file.getAbsolutePath());
								OutputStream output = new FileOutputStream(
										file.getAbsolutePath());
								byte data[] = new byte[1024];
								int count;
								while ((count = input.read(data)) != -1)
									output.write(data, 0, count);
								output.flush();
								output.close();
								input.close();
								File temp = new File(cacheDir, filename);
								file.renameTo(temp);

								Log.v("", "Image Downloader Load finish: "
										+ photoToLoadUrl);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
					if (Thread.interrupted())
						break;
				}
			} catch (Exception e) {
				// allow thread to exit
			}
		}
	}

	class PhotosToLoad {
		private ArrayList<String> urls = new ArrayList<String>();

		public boolean contains(String url) {
			return urls.contains(url);
		}

		public int size() {
			return urls.size();
		}

		public void add(String url) {
			urls.add(url);
		}

		public String pop() {
			String url = "";
			if (urls.size() > 0) {
				url = urls.get(0);
				urls.remove(0);
			}
			return url;
		}
	}

}
