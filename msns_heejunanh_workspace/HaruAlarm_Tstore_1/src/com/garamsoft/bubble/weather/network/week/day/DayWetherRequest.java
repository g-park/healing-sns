package com.garamsoft.bubble.weather.network.week.day;

import com.garamsoft.bubble.weather.network.NetworkRequest;


public class DayWetherRequest extends NetworkRequest {

	public DayWetherRequest(String url) {
		this.parser = new DayWetherParser();
		this.urlString = url;
	}
}
