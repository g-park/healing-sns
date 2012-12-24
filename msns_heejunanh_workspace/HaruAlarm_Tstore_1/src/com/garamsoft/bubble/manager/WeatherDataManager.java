package com.garamsoft.bubble.manager;

import java.util.ArrayList;

import com.garamsoft.bubble.weather.data.day.WeatherData;
import com.garamsoft.bubble.weather.data.day.Wid;

/** 날씨에 관한 정보를 수집하고 알려주는 매니저 객체 */
public class WeatherDataManager {

	private Wid wid;

	private static WeatherDataManager weatherDataManager;

	/** 생성자 */

	public static WeatherDataManager getInstance() {
		if (weatherDataManager == null) {
			weatherDataManager = new WeatherDataManager();
		}

		return weatherDataManager;
	}

	public Wid popWid() {
		if (wid != null) {
			return wid;
		}

		return null;
	}

	public ArrayList<WeatherData> popDatas() {
		if(wid!=null)
		if (wid.datas != null)
			return wid.datas;

		return null;
	}

	/** 쉐어드프리퍼런스에 값을 저장한다. 저장하는 값은 객체 정보가 완전히 된 인스턴스 객체를 넘겨주어야한다. */

	public void inputData(Wid wid) {
		this.wid = wid;
	}

}
