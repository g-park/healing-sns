package com.garamsoft.bubble.weather.network;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.Handler;

public class NetworkRequest {

	public String urlString;
	public Object resultObject;
	public XMLParser parser;
	public OnDownloadCompletedListener listener;
	
	public static final int PROCESS_SUCCESS = 1;
	public static final int PROCESS_DOWNLOAD_FAIL = 2;
	public static final int PROCESS_PARSE_FAIL = 3;
	
	public interface OnDownloadCompletedListener {
		public void onDownloadCompleted(int result, NetworkRequest request);
	}
	
	public void setOnDownloadCompletedListener(OnDownloadCompletedListener listener) {
		this.listener = listener;
	}
	
	public URL getRequestUrl() {
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			
		} 
		return url;
	}
	
	public Object getResult() {
		return resultObject;
	}
	
//	public void setParser(XMLParser parser) {
//		this.parser = parser;
//	}
	
//	public XMLParser getParser() {
//		return parser;
//	}
	
//	public String getMethod() {
//		return "GET";
//	}
	
//	public ArrayList<NetworkHeader> getHeaders() {
//		return null;
//	}
	
//	public int getConnectionTimeout() {
//		return 30000;
//	}
	
	public boolean process(Handler handler,InputStream is) {
		parser.doParse(is);
		resultObject = parser.getResult();
		handler.post(new Runnable() {
//
			@Override
			public void run() {
				if (listener != null) {
					listener.onDownloadCompleted(PROCESS_SUCCESS, NetworkRequest.this);
				}
			}
		});
		return true;
	}
}
