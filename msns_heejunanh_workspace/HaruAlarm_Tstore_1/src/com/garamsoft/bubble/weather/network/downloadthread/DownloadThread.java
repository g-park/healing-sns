package com.garamsoft.bubble.weather.network.downloadthread;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.util.Log;

import com.garamsoft.bubble.weather.network.NetworkRequest;

public class DownloadThread extends Thread {

	private Handler mHandler;
	private NetworkRequest request;
	
	
	public DownloadThread(Handler mainHandler,NetworkRequest request) {
		mHandler = mainHandler;
		this.request = request;
	}
	
	@Override
	public void run() {
		try {
			URL url = request.getRequestUrl();
			URLConnection connection;
			connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			httpConnection.setDoOutput(false);
			// httpConnection.setRequestMethod(request.getMethod());
			// ArrayList<NetworkHeader> headers = request.getHeaders();
			// httpConnection.setRequestProperty(key, value);
			// httpConnection.setRequestProperty("user-agent", "xxx");
			// httpConnection.setConnectTimeout(30000);
			// OutputStream out = httpConnection.getOutputStream();
			
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				Log.i("XML", "inputStream 완료;");
				request.process(mHandler, in);
				Log.i("XML", "process 완료;");
			}
		} catch (Exception e) {
			Log.i("XML", "에러??? : "+ e.getMessage());
		}
		
	}
}
