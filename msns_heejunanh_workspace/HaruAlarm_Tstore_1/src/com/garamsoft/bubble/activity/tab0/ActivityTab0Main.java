package com.garamsoft.bubble.activity.tab0;

import java.util.GregorianCalendar;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.activity.tab1.ActivityTab1HaruAlarmLIst;
import com.garamsoft.bubble.activity.tab2.ActivityTab2WeatherEventList;
import com.garamsoft.bubble.activity.tab3.ActivityTab3_Setting;
import com.garamsoft.bubble.view.BackGroundWeather_FrameLayout;
import com.garamsoft.bubble.view.ViewTabLinearController;
import com.garamsoft.bubble.view.ViewTabLinearController.BtnTab;

public class ActivityTab0Main extends TabActivity {
	
	public static TabHost tabHost;
	ViewTabLinearController tabLinearController;
	static BackGroundWeather_FrameLayout backGroundWeather;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tab0_main);
		
		tabLinearController = (ViewTabLinearController)findViewById(R.id.viewTabLinearController);
		backGroundWeather = (BackGroundWeather_FrameLayout)findViewById(R.id.backGroundWeather);
        tabHost = getTabHost();
        
        tabHost.addTab(tabHost.newTabSpec("Tab01")
        		.setIndicator("알람", getResources().getDrawable(R.drawable.ic_launcher))
        		.setContent(new Intent(this, ActivityTab1HaruAlarmLIst.class)));

        tabHost.addTab(tabHost.newTabSpec("Tab02")
        		.setIndicator("날씨/일정", getResources().getDrawable(R.drawable.ic_launcher))
        		.setContent(new Intent(this, ActivityTab2WeatherEventList.class)));
        
        tabHost.addTab(tabHost.newTabSpec("Tab03")
        		.setIndicator("설정", getResources().getDrawable(R.drawable.ic_launcher))
        		.setContent(new Intent(this, ActivityTab3_Setting.class)));
        
        tabLinearController.setOnBtnTab(new BtnTab() {
			
			@Override
			public void onBtnTab1Clicked() {
				backGroundWeather.getWeaterData(0);
				tabHost.setCurrentTab(0);
			}

			@Override
			public void onBtnTab2Clicked() {
				backGroundWeather.getWeaterData(0);
				tabHost.setCurrentTab(1);				
			}

			@Override
			public void onBtnTab3Clicked() {
				backGroundWeather.getWeaterData(0);
				tabHost.setCurrentTab(2);
				}
		});
        
	}
	
//	public static void setTabLocation(int location){
//		tabHost.setCurrentTab(location);
//	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		backGroundWeather.getWeaterData(0);
		int i = tabHost.getCurrentTab();
		tabLinearController.setCurrentBtn(i);
	}
	
//	void setDayAndNightBackGround(){
//		GregorianCalendar gregorianCalendar = new GregorianCalendar();
//	    int time = gregorianCalendar.getTime().getHours();
//    	if(6 < time && time < 18){
//    		tabHost.setBackgroundResource(R.drawable.background_day);
//		}
//		else{
//			tabHost.setBackgroundResource(R.drawable.background_night);
//		}	
//    }
	
//	static public void changeWeather(int i){
//		
//		backGroundWeather.getWeaterData(i);
//		
//	}
	
}
