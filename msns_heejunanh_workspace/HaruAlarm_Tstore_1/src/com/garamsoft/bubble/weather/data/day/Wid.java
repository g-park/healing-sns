package com.garamsoft.bubble.weather.data.day;

import java.util.ArrayList;

public class Wid {
	/**파싱할 것들의 목록임.
	 * 동네예보 열기*/
	public Header header;
	public ArrayList<WeatherData> datas; //15~18개가 나올 수 있음.
}
