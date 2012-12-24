package com.garamsoft.bubble.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class DefaultLiveManager {
	
	Context mContext;
	WeatherDataManager weatherDataManager;
	private SharedPreferences livePref;
	private SharedPreferences.Editor liveEditor;	
	private static final String DEFAULT_LIVE_SP_KEY = "DEFAULT_LIVE_SP_KEY";
	
	private static String default_live = "서울특별시";
	
	public DefaultLiveManager(Context _Context) {
		livePref = _Context.getSharedPreferences(DEFAULT_LIVE_SP_KEY,Context.MODE_PRIVATE);
		liveEditor = livePref.edit();
		weatherDataManager = WeatherDataManager.getInstance();
	}
	
	public void writeLive(String live){
		weatherDataManager.inputData(null);
		liveEditor.putString(DEFAULT_LIVE_SP_KEY,live);
		liveEditor.commit();
	}
	
	public String readLive(){
		return livePref.getString(DEFAULT_LIVE_SP_KEY, default_live);
	}

}
