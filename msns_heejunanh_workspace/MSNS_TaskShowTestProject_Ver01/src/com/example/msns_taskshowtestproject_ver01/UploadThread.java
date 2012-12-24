package com.example.msns_taskshowtestproject_ver01;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;


public class UploadThread extends Thread {

	String urlString;
	public UploadThread(String url) {
		
		urlString = url;
	}
	
	@Override
	public void run() {
		try {
			URL url = new URL(urlString);
			URLConnection connection;
			connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			httpConnection.setDoOutput(false);
			// httpConnection.setRequestMethod(request.getMethod());
			// ArrayList<NetworkHeader> headers = request.getHeaders();
//			 httpConnection.setRequestProperty("log", "fdf");
			// httpConnection.setRequestProperty("user-agent", "xxx");
			// httpConnection.setConnectTimeout(30000);
			// OutputStream out = httpConnection.getOutputStream();
			
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
//				InputStream in = httpConnection.getInputStream();
//				Log.i("XML", "inputStream 완료;");
//				request.process(mHandler, in);
				Log.i("FTPTEST", "process 완료;");
			}
		} catch (Exception e) {
			Log.i("FTPTEST", "에러??? : "+ e.getMessage());
		}
		
	}
}
