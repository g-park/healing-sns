package com.garamsoft.bubble.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.weather.data.day.WeatherData;


/**날씨에 관한 정보를 담기위한 리니어 클래스이다.
 * 모양으로는 날씨아이콘 상태 정보 기온등의 정보를 담을 수 있다.*/
public class ViewWeatherInfoLinear extends LinearLayout {

	private Context mContext;
	private TextView
	textViewWeatherInfoCondition,
	textViewWeatherInfoTemper,
	textViewWeatherInfoRainPercent,
	textViewWeatherInfoHumidity,
	textViewRefall,
	TextViewDay,
	TextViewDayTime
	; 
	private ImageView imageViewWeatherInfoIcon;
	
	public ViewWeatherInfoLinear(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_tab2_weather_info, this);

		textViewWeatherInfoCondition = (TextView) layout.findViewById(R.id.textViewWeatherInfoCondition);
		textViewWeatherInfoTemper = (TextView) layout.findViewById(R.id.textViewWeatherInfoTemper);
		textViewWeatherInfoRainPercent = (TextView) layout.findViewById(R.id.textViewWeatherInfoRainPercent);
		textViewWeatherInfoHumidity = (TextView) layout.findViewById(R.id.textViewWeatherInfoHumidity);
		textViewRefall = (TextView) layout.findViewById(R.id.textViewRefall);
		TextViewDay = (TextView)layout.findViewById(R.id.TextViewDay);
		TextViewDayTime = (TextView)layout.findViewById(R.id.TextViewDayTime);


		imageViewWeatherInfoIcon = (ImageView)layout.findViewById(R.id.imageViewWeatherInfoIcon);
	}

	public void setData(WeatherData weatherData,int position){

		if(weatherData.pty.equals("0"))
		switch (Integer.parseInt(weatherData.sky)) {
		case 1:
			imageViewWeatherInfoIcon.setImageResource(R.drawable.sky_code_1);
			break;
		case 2:
			imageViewWeatherInfoIcon.setImageResource(R.drawable.sky_code_2);
			break;
		case 3:
			imageViewWeatherInfoIcon.setImageResource(R.drawable.sky_code_3);
			break;
		case 4:
			imageViewWeatherInfoIcon.setImageResource(R.drawable.sky_code_4);
			break;
		default:
			imageViewWeatherInfoIcon.setImageResource(R.drawable.sky_code_1);
			break;
		}
		else {
			switch (Integer.parseInt(weatherData.pty)) {
			case 1:
				imageViewWeatherInfoIcon.setImageResource(R.drawable.rain_code_1);
				break;
			case 2:
				imageViewWeatherInfoIcon.setImageResource(R.drawable.rain_code_2_3);
				break;
			case 3:
				imageViewWeatherInfoIcon.setImageResource(R.drawable.rain_code_2_3);
				break;
			case 4:
				imageViewWeatherInfoIcon.setImageResource(R.drawable.rain_code_4);
				break;
			}
		}
		
		/**
		 * 1. 현재 오늘 내일 모래 까지
		 * day == 0 오늘
		 * day == 1 내일
		 * day == 2 모레
		 * 
		 * 2. 시간대
		 * Hour == 3 0~3시
		 * Hour == 6 3~6시
		 * Hour == 9 6~9시
		 * Hour == 12 9~12시
		 * Hour == 15 12~15시
		 * Hour == 18 15~18시
		 * Hour == 21 18~21시
		 * Hour == 24 21~24시
		 * */
		
		String day = "";
		String dayTime = " ~ "+((String.valueOf(weatherData.hour).length()==1)?"0"+weatherData.hour:weatherData.hour)+"시"; 
		/**Day*/
		switch (Integer.parseInt(weatherData.day)) {
		case 0:
		if(position == 0){
			day = " 현재 : ";
			break;
		}
		day = " 오늘 : ";
			break;
		case 1:day = " 내일 : ";
			break;
		case 2:day = " 모레 : ";
			break;
		}
		TextViewDay.setText(day);
		
		/*DayTime*/
		switch (Integer.parseInt(weatherData.hour)) {
		case 3:dayTime = "00" +dayTime;
			break;
		case 6:dayTime = "03" +dayTime;
			break;
		case 9:dayTime = "06" +dayTime;
			break;
		case 12:dayTime = "09" +dayTime;
			break;
		case 15:dayTime = "12" +dayTime;
			break;
		case 18:dayTime = "15" +dayTime;
			break;
		case 21:dayTime = "18" +dayTime;
			break;
		case 24:dayTime = "21" +dayTime;
			break;
		}
		
		TextViewDayTime.setText(dayTime);
		
		textViewWeatherInfoCondition.setText(weatherData.wfKor);
		textViewWeatherInfoTemper.setText(weatherData.temp+"℃");
		textViewWeatherInfoRainPercent.setText(weatherData.pop+"%");
		textViewWeatherInfoHumidity.setText(weatherData.reh+"%");
		textViewRefall.setText(weatherData.r12+"mm");
	}
}
