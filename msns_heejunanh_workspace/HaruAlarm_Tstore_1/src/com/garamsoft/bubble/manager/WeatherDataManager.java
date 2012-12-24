package com.garamsoft.bubble.manager;

import java.util.ArrayList;

import com.garamsoft.bubble.weather.data.day.WeatherData;
import com.garamsoft.bubble.weather.data.day.Wid;

/** ������ ���� ������ �����ϰ� �˷��ִ� �Ŵ��� ��ü */
public class WeatherDataManager {

	private Wid wid;

	private static WeatherDataManager weatherDataManager;

	/** ������ */

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

	/** ����������۷����� ���� �����Ѵ�. �����ϴ� ���� ��ü ������ ������ �� �ν��Ͻ� ��ü�� �Ѱ��־���Ѵ�. */

	public void inputData(Wid wid) {
		this.wid = wid;
	}

}
