package com.garamsoft.bubble.view;

import java.util.GregorianCalendar;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.manager.DefaultLiveManager;
import com.garamsoft.bubble.manager.WeatherDataManager;
import com.garamsoft.bubble.weather.data.day.WeatherData;
import com.garamsoft.bubble.weather.data.day.Wid;
import com.garamsoft.bubble.weather.data.day.local_forecast;
import com.garamsoft.bubble.weather.network.NetworkRequest;
import com.garamsoft.bubble.weather.network.NetworkRequest.OnDownloadCompletedListener;
import com.garamsoft.bubble.weather.network.downloadthread.DownloadThread;
import com.garamsoft.bubble.weather.network.week.day.DayWetherRequest;

public class BackGroundWeather_FrameLayout extends FrameLayout {
	
	private TextView textViewWeatherInfo;
	private ImageView 
	imageViewSun_moon,
	imageViewCloud,
	imageViewRain_snow;
	private FrameLayout frameLayoutBg_day_night;
	
	WeatherDataManager weatherDataManager;
	private NetworkRequest networkRequest;
	
	Handler mHandler =new Handler(){};
	private DefaultLiveManager defaultLiveManager;
	
	public BackGroundWeather_FrameLayout(Context context){
		super(context);
	}
	public BackGroundWeather_FrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_tab0_background_weather_animated, this);
		
		
		textViewWeatherInfo = (TextView)layout.findViewById(R.id.textViewWeatherInfo);
		textViewWeatherInfo.setVisibility(View.GONE);
		imageViewSun_moon = (ImageView)layout.findViewById(R.id.imageViewSun_moon);
		imageViewCloud= (ImageView)layout.findViewById(R.id.imageViewCloud);
		imageViewRain_snow= (ImageView)layout.findViewById(R.id.imageViewRain_snow);
		
		frameLayoutBg_day_night =(FrameLayout)layout.findViewById(R.id.frameLayoutBg_day_night);
		
		weatherDataManager = WeatherDataManager.getInstance();
		defaultLiveManager = new DefaultLiveManager(context);
		setDayAndNight();
		getWeaterData(0);	
	}
	
	public void getWeaterData(final int position) {
		
		if (weatherDataManager.popWid() != null) {
//			textViewWeatherInfo.setText(weatherDataManager.popDatas().get(position).wfKor);
			/*setData*/
			setWeather(weatherDataManager.popDatas().get(position));
		} else {
			networkRequest = new DayWetherRequest(local_forecast.getLocationURL(defaultLiveManager.readLive()));
			DownloadThread th = new DownloadThread(mHandler, networkRequest);
			networkRequest.setOnDownloadCompletedListener(new OnDownloadCompletedListener() {
						@Override
						public void onDownloadCompleted(int result,NetworkRequest request) {
							weatherDataManager.inputData(((Wid) request.getResult()));
							if(weatherDataManager.popDatas()!=null)
							setWeather(weatherDataManager.popDatas().get(position));}
					});
			th.start();
		}
	}

/**
 * 날씨를 세팅하는 함수?
 * sky 	① 1 : 맑음 ② 2 : 구름조금 ③ 3 : 구름많음 ④ 4 : 흐림
 * pty  ① 0 : 없음 ② 1 : 비	③ 2 : 비/눈 ④ 3 : 눈/비 ⑤ 4 : 눈*/
	private void setWeather(WeatherData weatherData){
		int pty = Integer.parseInt(weatherData.pty);
		int sky = Integer.parseInt(weatherData.sky);
		if(weatherData.pty.equals("0")){
			setCloud(sky);
			imageViewRain_snow.setVisibility(View.INVISIBLE);
			switch (Integer.parseInt(weatherData.sky)) {
			case 1:
				textViewWeatherInfo.setText("맑음");
				break;
			case 2:
				textViewWeatherInfo.setText("구름조금");
				break;
			case 3:
				textViewWeatherInfo.setText("구름많음");
				break;
			case 4:
				textViewWeatherInfo.setText("흐림");
				break;
			default:
				textViewWeatherInfo.setText("맑음");
				break;
			}
		}
			else {
				setRainAndSnow(pty);
				
				switch (Integer.parseInt(weatherData.pty)) {
				case 1:
					textViewWeatherInfo.setText("비");
					break;
				case 2:
					textViewWeatherInfo.setText("비/눈");
					break;
				case 3:
					textViewWeatherInfo.setText("눈/비");
					break;
				case 4:
					textViewWeatherInfo.setText("눈");
					break;
				}
			}
	}
	
	/**낮과밤 2*/
	/**해와달 3*/
	/**구름상태 4*/
	/**강수상태 4*/
	
	/**낮인지 밤을 세팅하는 함수*/
	private boolean setDayAndNight(){

		GregorianCalendar gregorianCalendar = new GregorianCalendar();
	    int time = gregorianCalendar.getTime().getHours();
    	if(6 < time && time < 18){
    		frameLayoutBg_day_night.setBackgroundResource(R.drawable.background_1_day);
    		imageViewSun_moon.setBackgroundResource(R.drawable.background_2_sun);
    		return true;
		}
		else{
			frameLayoutBg_day_night.setBackgroundResource(R.drawable.background_1_night);
			imageViewSun_moon.setBackgroundResource(R.drawable.background_2_moon);
			return false;
		}	
	}
	
	/**해와 달을 세팅하는 함수*/
//	private void setSunAndMoon(boolean b){
//		
//	}
	
	/**구름 상태를 나타내는 함수
	 * 1 맑음
	 * 2.구름조금
	 * 3 구름많음
	 * 4 흐림
	 * */
	private void setCloud(int i){
		imageViewSun_moon.setVisibility(View.VISIBLE);
		switch (i) {
		case 1:imageViewCloud.setVisibility(View.INVISIBLE);
			break;
		case 2:imageViewCloud.setBackgroundResource(R.drawable.background_3_cloud1);
		imageViewCloud.setVisibility(View.VISIBLE);
			break;
		case 3:imageViewCloud.setBackgroundResource(R.drawable.background_3_cloud2);
		imageViewCloud.setVisibility(View.VISIBLE);
			break;
		case 4:imageViewCloud.setBackgroundResource(R.drawable.background_3_cloud3);
				imageViewSun_moon.setVisibility(View.INVISIBLE);
			break;
		default :imageViewCloud.setVisibility(View.INVISIBLE);
			break;
		}
	}
	
	/**강수 상태를 나타내는 함수
	 * 
	 * ① 0 : 없음
	 * ② 1 : 비	
	 * ③ 2 : 비/눈
	 * ④ 3 : 눈/비
	 * ⑤ 4 : 눈
	 * */
	private void setRainAndSnow(int i ){
		imageViewSun_moon.setVisibility(View.INVISIBLE);
		imageViewRain_snow.setVisibility(View.VISIBLE);
		imageViewCloud.setBackgroundResource(R.drawable.background_3_cloud3);
		switch (i) {
		case 0:
			imageViewRain_snow.setVisibility(View.INVISIBLE);
			break;
		case 1:
			imageViewRain_snow.setBackgroundResource(R.drawable.background_4_rain);
			break;
		case 2:
			imageViewRain_snow.setBackgroundResource(R.drawable.background_4_snow_and_rain);
			break;
		case 3:
			imageViewRain_snow.setBackgroundResource(R.drawable.background_4_snow_and_rain);
			break;
		case 4:
			imageViewRain_snow.setBackgroundResource(R.drawable.background_4_snow);
			break;
		default:imageViewRain_snow.setVisibility(View.INVISIBLE);
			break;

		}
		
	}
	
}
